package br.com.cvm.miner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Expressao;
import br.com.cvm.bd.modelBD.Indicador;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;
import br.com.cvm.bd.modelBD.ValorContabil;

public class te {

	public static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

	public static void main(String[] args) {
		int preferencia=2;
		Query queryE = em.createQuery(
				"SELECT b FROM Empresa b where  exists ( select 1 from Demonstrativo c where  b.cvm = c.empresa.cvm)");
		List<Empresa> empresas = (List<Empresa>) queryE.getResultList();
		StringBuilder sberro = new StringBuilder();
		double ierro=0;
		double iacerto=0;
		StringBuilder sb = new StringBuilder();
		StringBuilder perc = new StringBuilder();
		Query queryI = em.createQuery(
				"SELECT b FROM Indicador b where exists(select 1 from Calculo c where c.indicador.idIndicador = b.idIndicador and c.preferencia = "+preferencia+") ");
		List<Indicador> indicadores = (List<Indicador>) queryI.getResultList();
		for (Indicador indic : indicadores) {
			sberro.append(indic.getNomeIndicador() + "\n");
			 ierro=0.0;
			 iacerto=0.0;
			//sb.append(indic.getNomeIndicador() + "\n");
			for (Empresa e1 : empresas) {
				String cvm = e1.getCvm() + "";
				int idindicador = indic.getIdIndicador();
				EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
				
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");

				
				Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = " + cvm
						+ " and   b.versao =1 order by  b.data");
				List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryA.getResultList();
				List<Double> valoresfinais = new ArrayList<Double>();
				for (Demonstrativo dem : demonstrativos) {
					if (dem.getIdDemonstrativo() == 354) {
						System.out.println("aqui");
					}
					boolean temerro = false;
					// Query query = em.createQuery("SELECT b,c FROM ValorContabil b,Calculo c where
					// b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador =
					// "+idindicador+" and c.preferencia = 1 and b.demonstrativo.idDemonstrativo =
					// "+dem.getIdDemonstrativo()+" order by c.posicao");
					Query query = em.createQuery(
							"SELECT b,c FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
									+ idindicador + " and   b.demonstrativo.idDemonstrativo = "
									+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" order by  c.posicao");
					List<Object[]> valores = query.getResultList();

					Calculo c = null;
					try {
						c = (Calculo) valores.get(0)[1];
					} catch (Exception e) {
						temerro = true;
						sberro.append("nada encontrado no demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
								+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
								+ dem.getData() + "\n");
						ierro++;
						continue;
					}
					String expressao = c.getExpressao().getExpressao();
					int numcolumns = c.getExpressao().getNumColunas();
					if (valores.size() != numcolumns) {
						// problema: não encontrou o numero necessário de colunas para este
						// demonstrativo
						for (int i = 1; i <= numcolumns; i++) {
							try {
								Calculo c1 = (Calculo) valores.get(i - 1)[1];
								if (c1.getPosicao() != i) {
									temerro = true;
									sberro.append("não encontrou coluna: " + i + " demonstrativo:  "
											+ dem.getIdDemonstrativo() + " cvm: " + dem.getEmpresa().getCvm()
											+ " ativo " + dem.getEmpresa().getRaizAtivo() + " data: " + dem.getData()
											+ "\n");
									ierro++;
								}
							} catch (Exception e) {
								sberro.append("erro inespecifico em demonstrativo:  " + dem.getIdDemonstrativo()
										+ " cvm: " + dem.getEmpresa().getCvm() + " ativo "
										+ dem.getEmpresa().getRaizAtivo() + " data: " + dem.getData() + "\n");
								ierro++;
							}
						}
						if (!temerro) {
							sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
									+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo()
									+ " data: " + dem.getData() + "\n");
							iacerto++;
						}
					} else {
						sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
								+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
								+ dem.getData() + "\n");
								iacerto++;
						for (int i = 0; i < valores.size(); i++) {
							ValorContabil vc = (ValorContabil) valores.get(i)[0];
							expressao = expressao.replace("::" + (i + 1), vc.getValor() + "");
						}
						sb.append(expressao + "\n");
						sb.append(expressao + "\n");
						try {
							sb.append(engine.eval(expressao) + "\n");
						} catch (ScriptException e) {
							// TODO Bloco catch gerado automaticamente
							// e.printStackTrace();
						}

					}

					// Query querye = em.createQuery("SELECT distinct c.expressao.expressao FROM
					// ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and
					// c.indicador.id_indicador = "+idindicador+" and c.preferencia = 1 and
					// b.demonstrativo.idDemonstrativo = "+dem.getIdDemonstrativo()+" order by
					// b.posicao");
					// String expressao= (String) querye.getSingleResult();

					/*
					 * for(int i = 0 ;i<valores.size();i++) { expressao=
					 * expressao.replace("::"+(i+1),valores.get(i)+""); } try { Double valorfinal =
					 * (Double)engine.eval(expressao); valoresfinais.add(valorfinal);
					 * 
					 * } catch (ScriptException e) { // TODO Bloco catch gerado automaticamente
					 * e.printStackTrace(); }
					 */
				}
			}
			perc.append("nomeIndicador: "+ indic.getNomeIndicador() +" erro: "+ ierro +" iacerto: " +iacerto +"\n");
		}
		System.out.println("erros:");

		System.out.println(sberro.toString());
		System.out.println(perc);
		// System.out.println("ok:");

		// System.out.println(sb.toString());

	}

}
