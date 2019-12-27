package br.com.cvm.miner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import br.com.cvm.bd.ComparaContasJaro;
import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Expressao;
import br.com.cvm.bd.modelBD.Indicador;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;
import br.com.cvm.bd.modelBD.ValorContabil;
import entities.Divergencia;

public class DiagnosticoIndicadores {

	public static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

	public ArrayList<AusenciaIndicadorVO> getAusencias(){
		 EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
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
			ArrayList<AusenciaIndicadorVO> ausencias = new ArrayList<AusenciaIndicadorVO>();
			
			 AusenciaIndicadorVO aiv=null;
			for (Indicador indic : indicadores) {
				sberro.append(indic.getNomeIndicador() + "\n");
				 ierro=0.0;
				 iacerto=0.0;
				//sb.append(indic.getNomeIndicador() + "\n");
				// ArrayList<AusenciaIndicadorVO> lausencias = new  ArrayList<AusenciaIndicadorVO>();
					aiv=	new AusenciaIndicadorVO();
				for (Empresa e1 : empresas) {
					
					ArrayList<String> vals= new ArrayList<String>();
					 ArrayList< ArrayList<Object[]>> valorescorr = new ArrayList<ArrayList<Object[]>> ();
					double ierroemp=0;
					double iacertoemp=0;
					
					String cvm = e1.getCvm() + "";
					int idindicador = indic.getIdIndicador();
				;
					
					ScriptEngineManager mgr = new ScriptEngineManager();
					ScriptEngine engine = mgr.getEngineByName("JavaScript");

					
					Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = " + cvm
							+ " and   b.versao =1 order by  b.data");
					List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryA.getResultList();
					List<Double> valoresfinais = new ArrayList<Double>();
					
				
					aiv.setIndicador(indic);
					
					boolean temerro = false;
					Demonstrativo demant= new Demonstrativo();
					for (Demonstrativo dem : demonstrativos) {
						if(temerro == true) {
							aiv.addDemonstrativo(demant);
						//		ausencias.add(aiv);
							//	 aiv=	new AusenciaIndicadorVO();//verr porque se eu setar um novo aqui e o proximo vir com erro eu não terei uma sugestão
								 //tenho que fazer um tratamento
								temerro=false;
						}
						demant = dem;
					//	valorescorr.add(valores);
						if (dem.getIdDemonstrativo() == 354) {
							System.out.println("aqui");
						}
						temerro = false;
						// Query query = em.createQuery("SELECT b,c FROM ValorContabil b,Calculo c where
						// b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador =
						// "+idindicador+" and c.preferencia = 1 and b.demonstrativo.idDemonstrativo =
						// "+dem.getIdDemonstrativo()+" order by c.posicao");
						Query query = em.createQuery(
								"SELECT b,c FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
										+ idindicador + " and   b.demonstrativo.idDemonstrativo = "
										+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" order by  c.posicao");
						ArrayList<Object[]> valores = (ArrayList<Object[]>)query.getResultList();

						Calculo c = null;
						try {
							c = (Calculo) valores.get(0)[1];
						} catch (Exception e) {
							temerro = true;
							sberro.append("nada encontrado no demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
									+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
									+ dem.getData() + "\n");
							ierro++;
							ierroemp++;
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
										//ierro++;
									}
								} catch (Exception e) {
									sberro.append("erro inespecifico em demonstrativo:  " + dem.getIdDemonstrativo()
											+ " cvm: " + dem.getEmpresa().getCvm() + " ativo "
											+ dem.getEmpresa().getRaizAtivo() + " data: " + dem.getData() + "\n");
								//	ierro++;
									temerro = true;
								}
							}
							if (!temerro) {
								sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
										+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo()
										+ " data: " + dem.getData() + "\n");
								iacerto++;
								
								
								iacertoemp++;
							}else {
								ierroemp++;
								ierro++;
								continue;
							}
						} else {
							sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
									+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
									+ dem.getData() + "\n");
									iacertoemp++;
									iacerto++;
									
									String conjcontas=new String();
							ArrayList<ContaContabilCalculo> cccalculos=new ArrayList<ContaContabilCalculo>();
							for (int i = 0; i < valores.size(); i++) {
								ContaContabilCalculo cccalculo = new ContaContabilCalculo();
								cccalculo.setCalculo( (Calculo)valores.get(i)[1]);
								ValorContabil vc = (ValorContabil) valores.get(i)[0];
								ContaContabil cc =vc.getContaContabil();
								cccalculo.setContaContabil(cc);
								cccalculos.add(cccalculo);
							
								conjcontas+="|"+vc.getContaContabil().getIdContaContabil();
								expressao = expressao.replace("::" + (i + 1), vc.getValor() + "");
							}
							if(!vals.contains(conjcontas)) {
								valorescorr.add(valores);
								aiv.addListContaCalculo(cccalculos);
							
								vals.add(conjcontas);
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
					if(temerro == true) {
						aiv.addDemonstrativo(demant);
						//	ausencias.add(aiv);
							temerro=false;
						//	 aiv=	new AusenciaIndicadorVO();//verr porque se eu setar um novo aqui e o proximo vir com erro eu não terei uma sugestão
							 //tenho que fazer um tratamento
					}
					perc.append("nomeIndicador: "+ indic.getNomeIndicador() +">"+ e1.getRazaoSocial()+" erro: "+ ierroemp +" iacerto: " +iacertoemp +"\n");

				}
				perc.append("nomeIndicador: "+ indic.getNomeIndicador() +" erro: "+ ierro +" iacerto: " +iacerto +"\n\n");
				if(aiv!=null && aiv.getDemonstrativos().size()>0) {
					if(!ausencias.contains(aiv)) {
						ausencias.add(aiv);
					}
				
				}
			}
			return ausencias;
//			ComparaContasJaro ccj = new ComparaContasJaro();
//			AusenciaIndicadorVO aiv1 =ausencias.get(0);
//			ArrayList<ContaContabil > contasencontradas=new ArrayList<ContaContabil > ();
//			for(ContaContabilCalculo ccalculo1: aiv1.getContaCalculos().get(0)) {
//				contasencontradas.add(ccalculo1.getContaContabil());
//			}
//			
//			Divergencia div = ccj.analisar(contasencontradas, aiv.getDemonstrativos().get(0));
//			
//			System.out.println("erros:");
//
//			System.out.println(sberro.toString());
//			System.out.println(perc);
	}
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
		ArrayList<AusenciaIndicadorVO> ausencias = new ArrayList<AusenciaIndicadorVO>();
		
		 AusenciaIndicadorVO aiv=null;
		for (Indicador indic : indicadores) {
			sberro.append(indic.getNomeIndicador() + "\n");
			 ierro=0.0;
			 iacerto=0.0;
			//sb.append(indic.getNomeIndicador() + "\n");
			// ArrayList<AusenciaIndicadorVO> lausencias = new  ArrayList<AusenciaIndicadorVO>();
				aiv=	new AusenciaIndicadorVO();
			for (Empresa e1 : empresas) {
				
				ArrayList<String> vals= new ArrayList<String>();
				 ArrayList< ArrayList<Object[]>> valorescorr = new ArrayList<ArrayList<Object[]>> ();
				double ierroemp=0;
				double iacertoemp=0;
				
				String cvm = e1.getCvm() + "";
				int idindicador = indic.getIdIndicador();
				EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
				
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");

				
				Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = " + cvm
						+ " and   b.versao =1 order by  b.data");
				List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryA.getResultList();
				List<Double> valoresfinais = new ArrayList<Double>();
				
			
				aiv.setIndicador(indic);
				
				boolean temerro = false;
				Demonstrativo demant= new Demonstrativo();
				for (Demonstrativo dem : demonstrativos) {
					if(temerro == true) {
						aiv.addDemonstrativo(demant);
					//		ausencias.add(aiv);
						//	 aiv=	new AusenciaIndicadorVO();//verr porque se eu setar um novo aqui e o proximo vir com erro eu não terei uma sugestão
							 //tenho que fazer um tratamento
							temerro=false;
					}
					demant = dem;
				//	valorescorr.add(valores);
					if (dem.getIdDemonstrativo() == 354) {
						System.out.println("aqui");
					}
					temerro = false;
					// Query query = em.createQuery("SELECT b,c FROM ValorContabil b,Calculo c where
					// b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador =
					// "+idindicador+" and c.preferencia = 1 and b.demonstrativo.idDemonstrativo =
					// "+dem.getIdDemonstrativo()+" order by c.posicao");
					Query query = em.createQuery(
							"SELECT b,c FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
									+ idindicador + " and   b.demonstrativo.idDemonstrativo = "
									+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" order by  c.posicao");
					ArrayList<Object[]> valores = (ArrayList<Object[]>)query.getResultList();

					Calculo c = null;
					try {
						c = (Calculo) valores.get(0)[1];
					} catch (Exception e) {
						temerro = true;
						sberro.append("nada encontrado no demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
								+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
								+ dem.getData() + "\n");
						ierro++;
						ierroemp++;
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
									//ierro++;
								}
							} catch (Exception e) {
								sberro.append("erro inespecifico em demonstrativo:  " + dem.getIdDemonstrativo()
										+ " cvm: " + dem.getEmpresa().getCvm() + " ativo "
										+ dem.getEmpresa().getRaizAtivo() + " data: " + dem.getData() + "\n");
							//	ierro++;
								temerro = true;
							}
						}
						if (!temerro) {
							sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
									+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo()
									+ " data: " + dem.getData() + "\n");
							iacerto++;
							
							
							iacertoemp++;
						}else {
							ierroemp++;
							ierro++;
							continue;
						}
					} else {
						sb.append("não encontrou erro: demonstrativo:  " + dem.getIdDemonstrativo() + " cvm: "
								+ dem.getEmpresa().getCvm() + " ativo " + dem.getEmpresa().getRaizAtivo() + " data: "
								+ dem.getData() + "\n");
								iacertoemp++;
								iacerto++;
								
								String conjcontas=new String();
						ArrayList<ContaContabilCalculo> cccalculos=new ArrayList<ContaContabilCalculo>();
						for (int i = 0; i < valores.size(); i++) {
							ContaContabilCalculo cccalculo = new ContaContabilCalculo();
							cccalculo.setCalculo( (Calculo)valores.get(i)[1]);
							ValorContabil vc = (ValorContabil) valores.get(i)[0];
							ContaContabil cc =vc.getContaContabil();
							cccalculo.setContaContabil(cc);
							cccalculos.add(cccalculo);
						
							conjcontas+="|"+vc.getContaContabil().getIdContaContabil();
							expressao = expressao.replace("::" + (i + 1), vc.getValor() + "");
						}
						if(!vals.contains(conjcontas)) {
							valorescorr.add(valores);
							aiv.addListContaCalculo(cccalculos);
						
							vals.add(conjcontas);
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
				if(temerro == true) {
					aiv.addDemonstrativo(demant);
					//	ausencias.add(aiv);
						temerro=false;
					//	 aiv=	new AusenciaIndicadorVO();//verr porque se eu setar um novo aqui e o proximo vir com erro eu não terei uma sugestão
						 //tenho que fazer um tratamento
				}
				perc.append("nomeIndicador: "+ indic.getNomeIndicador() +">"+ e1.getRazaoSocial()+" erro: "+ ierroemp +" iacerto: " +iacertoemp +"\n");

			}
			perc.append("nomeIndicador: "+ indic.getNomeIndicador() +" erro: "+ ierro +" iacerto: " +iacerto +"\n\n");
			if(aiv!=null && aiv.getDemonstrativos().size()>0) {
				if(!ausencias.contains(aiv)) {
					ausencias.add(aiv);
				}
			
			}
		}
		
		ComparaContasJaro ccj = new ComparaContasJaro();
		AusenciaIndicadorVO aiv1 =ausencias.get(0);
		ArrayList<ContaContabil > contasencontradas=new ArrayList<ContaContabil > ();
		for(ContaContabilCalculo ccalculo1: aiv1.getContaCalculos().get(0)) {
			contasencontradas.add(ccalculo1.getContaContabil());
		}
		
		Divergencia div = ccj.analisar(contasencontradas, aiv.getDemonstrativos().get(0));
		
		System.out.println("erros:");

		System.out.println(sberro.toString());
		System.out.println(perc);
		// System.out.println("ok:");

		// System.out.println(sb.toString());

	}

}
