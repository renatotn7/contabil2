package br.com.cvm.miner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import br.com.cvm.bd.ComparaContasJaro;
import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.Abrangencia;
import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Expressao;
import br.com.cvm.bd.modelBD.Indicador;
import br.com.cvm.bd.modelBD.Periodo;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;
import br.com.cvm.bd.modelBD.TipoDemonstrativo;
import br.com.cvm.bd.modelBD.ValorContabil;
import entities.Divergencia;

public class DiagnosticoIndicadores {

	public static EntityManager em = JPAUtil.INSTANCE.getEntityManager();
/**
 * 
 * @param preferencia
 * @return 	HashMap<String,Integer> onde	String key = idExpressao+"|"+indic.getIdIndicador() + "|" +cc.getIdContaContabil()+"|"+cccalculo.getCalculo().getPosicao();

 */
	
	
	public HashMap<String,Integer> getEleicao(int preferencia){
		HashMap<String,Integer> phash= new  HashMap<String,Integer>();
		 EntityManager em = JPAUtil.INSTANCE.getEntityManager();
			
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
		
			for (Indicador indic : indicadores ) {
				if(indic.getIdIndicador() == 11) {
					int j=1; 
				}
				sberro.append(indic.getNomeIndicador() + "\n");
				 ierro=0.0;
				 iacerto=0.0;
				//sb.append(indic.getNomeIndicador() + "\n");
				// ArrayList<AusenciaIndicadorVO> lausencias = new  ArrayList<AusenciaIndicadorVO>();
				
					int idindicador = indic.getIdIndicador();
					Query queryExpr = em.createQuery(
							"SELECT distinct c.expressao.idExpressao FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
									+ idindicador + "  and c.preferencia = "+preferencia+" order by  c.posicao");
					ArrayList<Integer> idsExpressao = (ArrayList<Integer> )queryExpr.getResultList();
					boolean temerro = false;
					
					for(Integer idExpressao :idsExpressao) {
						 
				for (Empresa e1 : empresas) {
					
					ArrayList<String> vals= new ArrayList<String>();
					 ArrayList< ArrayList<Object[]>> valorescorr = new ArrayList<ArrayList<Object[]>> ();
					double ierroemp=0;
					double iacertoemp=0;
					
					String cvm = e1.getCvm() + "";
				
				;
					
					ScriptEngineManager mgr = new ScriptEngineManager();
					

					
					Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = " + cvm
							+ " and   b.versao =1 order by  b.data");
					List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryA.getResultList();
					List<Double> valoresfinais = new ArrayList<Double>();
					
				
				
					
					
					Demonstrativo demant= new Demonstrativo();
					Integer IdExpressaoAnt=0;
				
					for (Demonstrativo dem : demonstrativos) {
						
							//pcontas1.put(idExpressao+"|"+dem.getIdDemonstrativo()+"|"+e1.getCvm()+"|"+indic.getIdIndicador(), value)
						
						
						IdExpressaoAnt=idExpressao;
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
										+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" and c.expressao.idExpressao = "+idExpressao+" order by  c.posicao");
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
								String skey1 = idExpressao+"|"+indic.getIdIndicador() + "|" +cc.getIdContaContabil()+"|"+cccalculo.getCalculo().getPosicao();
								if(phash.containsKey(skey1)) {
									phash.put(skey1, phash.get(skey1)+1);
								}else {
									phash.put(skey1, 0);
								}
								
								
								cccalculo.setContaContabil(cc);
								cccalculos.add(cccalculo);
							
								conjcontas+="|"+vc.getContaContabil().getIdContaContabil();
								expressao = expressao.replace("::" + (i + 1), vc.getValor() + "");
							}
							if(!vals.contains(conjcontas)) {
								valorescorr.add(valores);
							
							
								vals.add(conjcontas);
							}
							sb.append(expressao + "\n");
							sb.append(expressao + "\n");
							
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
						
							//	 aiv=	new AusenciaIndicadorVO();//verr porque se eu setar um novo aqui e o proximo vir com erro eu não terei uma sugestão
								 //tenho que fazer um tratamento
						}
						
					}
					
				}
			
				
			
			System.out.println(sberro.toString());
			System.out.println(perc);
			// System.out.println("ok:");
			return phash;
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
	
	public ArrayList<AusenciaIndicadorVO> getAusencias(){
		
		 EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		
			
		
			Query queryE = em.createQuery(
					"SELECT b FROM Empresa b where  exists ( select 1 from Demonstrativo c where  b.cvm = c.empresa.cvm)");
			@SuppressWarnings("unchecked")
			List<Empresa> empresas = (List<Empresa>) queryE.getResultList();
			ArrayList<AusenciaIndicadorVO> ausencias = new ArrayList<AusenciaIndicadorVO>();
			for (int z=1;z<=3;z++) {
			int preferencia=z;
			
			HashMap<String,Integer> phashs = getEleicao(preferencia);
			Query queryI = em.createQuery(
					"SELECT b FROM Indicador b where exists(select 1 from Calculo c where c.indicador.idIndicador = b.idIndicador and c.preferencia = "+preferencia+") ");
			@SuppressWarnings("unchecked")
			List<Indicador> indicadores = (List<Indicador>) queryI.getResultList();
			
			
		
			for (Indicador indic : indicadores ) {
			
			
				
				
				
				for (Empresa e1 : empresas) {
					
					
					String cvm = e1.getCvm() + "";
					int idindicador = indic.getIdIndicador();
				
					

					
					Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = " + cvm
							+ " and   b.versao =1 order by  b.data");
					@SuppressWarnings("unchecked")
					List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryA.getResultList();
					
					
				
					
					
					
				
					for (Demonstrativo dem : demonstrativos) {
					 
						Query queryExpr = em.createQuery(
								"SELECT distinct c.expressao FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
										+ idindicador + " and   b.demonstrativo.idDemonstrativo = "
										+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" order by  c.posicao");
						@SuppressWarnings("unchecked")
						ArrayList<Expressao> idsExpressao = (ArrayList<Expressao> )queryExpr.getResultList();
					
						
						for(Expressao exp :idsExpressao) {
							
							if(indic.getIdIndicador() == 5) {
								if(dem.getIdDemonstrativo()==1288 || dem.getIdDemonstrativo()==1306) {
								int j=1; 
								}
							}
							Integer idExpressao = exp.getIdExpressao();
					
							
								int numColumns=preferencia;
								
								AusenciaIndicadorVO aiv = new AusenciaIndicadorVO();
								aiv.setIndicador(indic);
								aiv.setDemonstrativo(dem);
								aiv.setExpressao(exp);
								boolean erro=false;
								for(int i=1;i<=numColumns;i++) {
								Query query = em.createQuery(
										"SELECT b,c FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.idIndicador = "
												+ idindicador + " and   b.demonstrativo.idDemonstrativo = "
												+ dem.getIdDemonstrativo() + " and c.preferencia = "+preferencia+" and c.expressao.idExpressao = "+idExpressao+" and c.posicao="+i+" order by  c.posicao");
								@SuppressWarnings("unchecked")
								ArrayList<Object[]> valores = (ArrayList<Object[]>)query.getResultList();
								if(valores.size()>1) {
									//COLUNA DUPLICADA
								}
								if(valores==null || valores.size()==0) {
									erro=true;
									Integer idContaEscolhidaPos1=elegeContaPos(phashs, indic, idExpressao,  i);
									Query query2 = em.createQuery(
											"SELECT b,c FROM ContaContabil b,Calculo c where b.idCalculo = c.idCalculo and b.idContaContabil="+idContaEscolhidaPos1+" and c.expressao.idExpressao="+idExpressao);
									Object[] contasCalculo = (Object[])query2.getSingleResult();
								
									
									ContaContabilCalculo cc = new ContaContabilCalculo();
									cc.setCalculo((Calculo)contasCalculo[1]);
									cc.setContaContabil((ContaContabil)contasCalculo[0]);
									aiv.addContaCalculo(cc);
									
									
								}
								
								}
								if(erro ==true) {
								ausencias.add(aiv);
								erro =false;
								}
							
							//String key = idExpressao+"|"+indic.getIdIndicador() + "|" +cc.getIdContaContabil()+"|"+cccalculo.getCalculo().getPosicao();
							
						}
					
					}
					
				}
						
			}
			}
			
			return ausencias;
//			
	}

	private Integer elegeContaPos(HashMap<String, Integer> phashs, Indicador indic, Integer idExpressao, 
			int pos) {
		String idContaEscolhidaPos1="";
		int maxVotos=-1;
		
		for(String skey:phashs.keySet()) {
		if(skey.startsWith(idExpressao+"|"+indic.getIdIndicador() + "|")&& skey.endsWith("|"+pos)) {
			if(phashs.get(skey) >maxVotos) {
				idContaEscolhidaPos1=skey.split("\\|")[2];
			}
		}
		}
		return Integer.parseInt(idContaEscolhidaPos1);
	}
	public static void main(String[] args) {
		int preferencia=2;
		int icvm=8133;
		int indicador =5;
		Query queryE = em.createQuery(
				"SELECT b FROM Empresa b where  exists ( select 1 from Demonstrativo c where  b.cvm = c.empresa.cvm and b.cvm = "+icvm+")");
		List<Empresa> empresas = (List<Empresa>) queryE.getResultList();
		StringBuilder sberro = new StringBuilder();
		double ierro=0;
		double iacerto=0;
		StringBuilder sb = new StringBuilder();
		StringBuilder perc = new StringBuilder();
		Query queryI = em.createQuery(
				"SELECT b FROM Indicador b where  b.idIndicador = "+indicador+"  ");
		List<Indicador> indicadores = (List<Indicador>) queryI.getResultList();
		ArrayList<AusenciaIndicadorVO> ausencias = new ArrayList<AusenciaIndicadorVO>();
		StringBuilder resultados=new StringBuilder();
		
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
				EntityManager em = JPAUtil.INSTANCE.getEntityManager();
				
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

					
					  for(int i = 0 ;i<valores.size();i++) { 
						  expressao=  expressao.replace("::"+(i+1),valores.get(i)+""); 
					  } 
					  try { Double valorfinal =
							  (Double)engine.eval(expressao); 
					  resultados.append(e1.getCvm()+" " + dem.getData() + " "+ indic.getDescricao()+ " " + valorfinal +"\n" );
					  			valoresfinais.add(valorfinal);

					  } catch (ScriptException e) { // TODO Bloco catch gerado automaticamente
					  e.printStackTrace(); }
					 
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
		//AusenciaIndicadorVO aiv1 =ausencias.get(0);
		ArrayList<ContaContabil > contasencontradas=new ArrayList<ContaContabil > ();
	//	for(ContaContabilCalculo ccalculo1: aiv1.getContaCalculos().get(0)) {
		//	contasencontradas.add(ccalculo1.getContaContabil());
		//}
		
		//Divergencia div = ccj.analisar(contasencontradas, aiv.getDemonstrativos().get(0));
		
		System.out.println("erros:");

		System.out.println(sberro.toString());
		
		System.out.println(perc);
		
		System.out.println(resultados);
		// System.out.println("ok:");

		// System.out.println(sb.toString());

	}

	public String getGraficoIndicadores(int icvm,int indicador, int preferencia) {
		//int preferencia=2;
		//int icvm=8133;
		//int indicador =5;
		Query queryE = em.createQuery(
				"SELECT b FROM Empresa b where  exists ( select 1 from Demonstrativo c where  b.cvm = c.empresa.cvm and b.cvm = "+icvm+")");
		List<Empresa> empresas = (List<Empresa>) queryE.getResultList();
		StringBuilder sberro = new StringBuilder();
		double ierro=0;
		double iacerto=0;
		StringBuilder sb = new StringBuilder();
		StringBuilder perc = new StringBuilder();
		Query queryI = em.createQuery(
				"SELECT b FROM Indicador b where  b.idIndicador = "+indicador+"  ");
		List<Indicador> indicadores = (List<Indicador>) queryI.getResultList();
		ArrayList<AusenciaIndicadorVO> ausencias = new ArrayList<AusenciaIndicadorVO>();
		StringBuilder resultados=new StringBuilder();
		StringBuilder depresultados = new StringBuilder();
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
				EntityManager em = JPAUtil.INSTANCE.getEntityManager();
				
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
									+ dem.getIdDemonstrativo() + " and c.preferencia = "+indic.getNumpref()+" order by  c.posicao");
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
					String expressao = c.getExpressao().getExpressao()+"";
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
						//	expressao = expressao.replace("::" + (i + 1), vc.getValor() + "");
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

					String expressao2="";
					 expressao2=expressao+"";
					String expressao3=expressao+"";
					  for(int i = 0 ;i<valores.size();i++) { 
						  
							ValorContabil vc = (ValorContabil) valores.get(i)[0];
							if(vc.getDemonstrativo().getData() == 201709 || vc.getDemonstrativo().getData() == 201712) {
								int j = 0;
							}
							Abrangencia abrang = vc.getContaContabil().getTipoDemonstrativo().getAbrangencia();
							TipoDemonstrativo tpdem = vc.getContaContabil().getTipoDemonstrativo();
							int tpdemid= tpdem.getIdTipo();
							Periodo periodo = vc.getDemonstrativo().getPeriodo();
							Double valor = vc.getValor()*1.0;
							
							if(abrang.getSiglaAbrangencia().equals("M")) {
								valor=valor;
							}else
							if(abrang.getSiglaAbrangencia().equals("T") ){
								if(periodo.getSiglaPeriodo().equals("A")) {
								valor=valor;
								}else
								if(periodo.getSiglaPeriodo().equals("T")) {
									
									
									valor=(valor/3.0)*12.0;//aqui
								}
							}else
							if(abrang.getSiglaAbrangencia().equals("F") ){
								if(periodo.getSiglaPeriodo().equals("A")) {
								valor=valor;
								}else
								if(periodo.getSiglaPeriodo().equals("T")) {
									String sdata = vc.getContaContabil().getDemonstrativo().getData()+"";
									String smes = sdata.substring(4);
									Double dmes = Double.parseDouble(smes+".0");
									valor=(valor/dmes)*12.0;
									
									
								}
							}
							
						  expressao=  expressao.replace("::"+(i+1),valor+""); 
						  expressao2=  expressao2.replace("::"+(i+1),vc.getValor()+""); 
						  expressao3=  expressao3.replace("::"+(i+1),vc.getContaContabil().getContaContabil()+""); 
					  } 
					  try { Double valorfinal =
							  (Double)engine.eval(expressao); 
					  depresultados.append(dem.getIdDemonstrativo()+" "+ dem.getData()+": " + valorfinal+ " - "+expressao+" - "+expressao2+" - "+expressao3+"\n" );
					  resultados.append( dem.getData()+";"+ valorfinal +"\n" );
					  			valoresfinais.add(valorfinal);

					  } catch (ScriptException e) { // TODO Bloco catch gerado automaticamente
					  e.printStackTrace(); }
					 
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
		//AusenciaIndicadorVO aiv1 =ausencias.get(0);
		ArrayList<ContaContabil > contasencontradas=new ArrayList<ContaContabil > ();
	//	for(ContaContabilCalculo ccalculo1: aiv1.getContaCalculos().get(0)) {
		//	contasencontradas.add(ccalculo1.getContaContabil());
		//}
		
		//Divergencia div = ccj.analisar(contasencontradas, aiv.getDemonstrativos().get(0));
		
		System.out.println("erros:");

		System.out.println(sberro.toString());
		
		System.out.println(perc);
		
		System.out.println(depresultados);
		// System.out.println("ok:");
		return resultados.toString();
		// System.out.println(sb.toString());

	}

	
	
}
