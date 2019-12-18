package br.com.cvm.bd.consolidacaoBD;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Periodo;
import br.com.cvm.bd.modelBD.TipoDemonstrativo;
import br.com.cvm.bd.modelBD.ValorContabil;
import br.com.cvm.leitor.origemBDeProperties.PeriodoToProperties;
import br.com.cvm.utils.JaroWinklerStrategy;
import entities.ContaCandidata;
import entities.ContaComparada;
import entities.Divergencia;
import entities.RelatorioDiferenca;

public class PersisteContasUnificando {
public static Double getDouble(String value) {

String valor = value.split(";")[1];
valor = valor.replaceAll("\\.", "");
return Double.parseDouble(valor);
}

public static void persiste(Object obj) {

	  em.persist(obj);
	 
}
public static  void close(){
	
    em.close();
    PersistenceManager.INSTANCE.close();
}
static JaroWinklerStrategy jw = new JaroWinklerStrategy(0.05);
public static String possivelCorrespondente(List<ContaContabil> cbd, String key2, String value2,ContaComparada contaComparada) {
	StringBuilder sb = new StringBuilder();
	boolean encontrou = false;
	/* for(Object key :p.keySet()) {
		 String key1 = (String)key;
		 String value1 = p.getProperty(key1.trim());
		 if(value2.equals(value1.split(";")[0])) {
			 encontrou = true;
			 sb.append("\t\tmatch 100%: "+key1 +" "+ value1.split(";")[0]+"\n");
		 }
	 }*/
	int count=0;
	 for(ContaContabil cc:cbd) {
		 String key1 = (String)cc.getContaContabil().trim();
		 String value1 = cc.getDescricao().trim();
		 double jaroscore = jw.score(value2, value1.split(";")[0]);
		 if(count<3) {
			 if(jaroscore>0.70) {
				 count++;
				 encontrou = true;
				 double perc = Math.round((jaroscore*100.0) * 100.0) / 100.0;
				 sb.append("\t\tmatch "+perc+"%: "+key1 +" "+ value1.split(";")[0]+"\n");
				
				 ContaCandidata ccand = new ContaCandidata();
				 ccand.setSimilaridade(perc);
				 ccand.setConta(cc);
					String[] niveisConta = cc.getContaContabil().split("\\.");
					String contaInicial = niveisConta[0];
					String contasPai[] = new String[niveisConta.length-1];
					String sinicial = "";
					for(int i=1;i<niveisConta.length-1;i++) {
						
						contaInicial +="."+niveisConta[i];
						//encontra no valor contabil fixando o plano para este considerado a conta superior, pois È la que tem o plano fixo
						 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial+"\' and e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
				    	 ContaContabil  tpai = (ContaContabil) query1.getSingleResult();
						String estaConta = (String)tpai.getDescricao();
						sinicial+=" > "+estaConta.split(";")[0];
						
					}
					ccand.setRaiz(sinicial);
				 contaComparada.addCandidato(ccand);
				 
			 }
		 }
	 }
	if(encontrou) sb.append("\n");	
	return sb.toString();
	
}

public static String possivelCorrespondente2(List<ContaContabil> cbd, List<ContaContabil> cbd2) {
	StringBuilder sb = new StringBuilder();
	boolean encontrou = false;
	/* for(Object key :p.keySet()) {
		 String key1 = (String)key;
		 String value1 = p.getProperty(key1.trim());
		 if(value2.equals(value1.split(";")[0])) {
			 encontrou = true;
			 sb.append("\t\tmatch 100%: "+key1 +" "+ value1.split(";")[0]+"\n");
		 }
	 }*/
	for(ContaContabil cnovo: cbd2) {

	 
	 
	int count=0;
	 for(ContaContabil cc:cbd) {
		 String key1 = (String)cc.getContaContabil().trim();
		 String value1 = cc.getDescricao().trim();
		 double jaroscore = jw.score(cnovo.getDescricao().trim(), value1.split(";")[0]);
		 if(count<3) {
			 if(jaroscore>0.70) {
				 count++;
				 encontrou = true;
				 double perc = Math.round((jaroscore*100.0) * 100.0) / 100.0;
				 sb.append("\t\tmatch "+perc+"%: "+key1 +" "+ value1.split(";")[0]+"\n");
				
				 ContaCandidata ccand = new ContaCandidata();
				 ccand.setSimilaridade(perc);
				 ccand.setConta(cc);
					String[] niveisConta1 = cc.getContaContabil().split("\\.");
					String contaInicial1 = niveisConta1[0];
					String contasPai1[] = new String[niveisConta1.length-1];
					String sinicial1 = "";
					for(int i=1;i<niveisConta1.length-1;i++) {
						
						contaInicial1 +="."+niveisConta1[i];
						//encontra no valor contabil fixando o plano para este considerado a conta superior, pois È la que tem o plano fixo
						 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial1+"\' and e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
				    	 ContaContabil  tpai = (ContaContabil) query1.getSingleResult();
						String estaConta = (String)tpai.getDescricao();
						sinicial1+=" > "+estaConta.split(";")[0];
						
					}
					ccand.setRaiz(sinicial1);
			
					ContaComparada ccomp = new ContaComparada();
					ccomp.setComparado(cnovo);
					ccomp.setTipoComparacao("N");
					ccomp.addCandidato(ccand);
					String[] niveisConta = cnovo.getContaContabil().trim().split("\\.");
					String contaInicial = niveisConta[0];
					String contasPai[] = new String[niveisConta.length-1];
					String sinicial = cnovo.getTipoDemonstrativo().getSiglaTipo();
					for(int i=1;i<niveisConta.length-1;i++) {
						
						contaInicial +="."+niveisConta[i];
						String estaConta = (String)cnovo.getDescricao().trim();
						sinicial+=" > "+estaConta.split(";")[0];
						
					}
					
					ccomp.setRaiz(sinicial);
					 divergencia.addNaoExistencia(ccomp);
				 
			 }
		 }
	 }
	}
	if(encontrou) sb.append("\n");	
	return sb.toString();
	
}

public static String comparaExistencia(Properties novo,String sigla, Integer data, String cvm) {
	int count = 0;
	StringBuilder sb1 = new StringBuilder(); 
	StringBuilder sb2 = new StringBuilder(); 
	StringBuilder sb = new StringBuilder(); 
	em.getTransaction()
    .begin();
	Demonstrativo dm = new Demonstrativo();
	Empresa e = em.find(Empresa.class, Integer.parseInt(cvm));
	Periodo p = em.find(Periodo.class, 2);
	try {
	
  
	dm.setEmpresa(e);
	//Periodo p = em.find(Periodo.class, 2);
	dm.setPeriodo(p);
	dm.setEstadoCriacao(0);
	dm.setVersao(1);
	dm.setData(data);
	
	persiste(dm);
	 em.getTransaction()
	    .commit();
	}catch(Exception e1) {
		
		 em.getTransaction()
		    .rollback();
	
		 Query query = em.createQuery("SELECT e FROM Demonstrativo e where e.empresa.cvm="+Integer.parseInt(cvm)+" and e.periodo.idPeriodo ="+p.getIdPeriodo()+" and e.versao = "+dm.getVersao()+" and e.data ="+dm.getData());
		 dm= (Demonstrativo) query.getSingleResult();
	}
	
	/*
	CriteriaBuilder cb = em.getCriteriaBuilder();
	CriteriaQuery<Demonstrativo> cq = cb.createQuery(Demonstrativo.class);
	Root<Demonstrativo> pet = cq.from(Demonstrativo.class);
	cq.select(pet);
	TypedQuery<Demonstrativo> q = em.createQuery(cq);
	Metamodel m = em.getMetamodel();
	EntityType<Demonstrativo> edemo = m.entity(Demonstrativo.class);
	EntityType<Empresa> eemp = m.entity(Empresa.class);
	EntityType<Periodo> eperiodo = m.entity(Periodo.class);
	
	
	List<Demonstrativo> allPets = q.getResultList();
	*/
	
	//Criteria criteria = em.getCriteriaBuilder()..createCriteria(Demonstrativo.class);
	//Demonstrativo yourObject =(Demonstrativo) criteria.add(Restrictions.eq("yourField", Integer.parseInt(cvm)))
	             //                .uniqueResult();
	
	Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where e.siglaTipo=\'"+sigla+"\'");
	 TipoDemonstrativo  tp1 = (TipoDemonstrativo) query.getSingleResult();
	
	List<ContaContabil>   cctipo = (List<ContaContabil>) tp1.getContaContabils();

	 for(Object key :novo.keySet()) {
		 String keynovo = (String)key;
		 String valuep1 =(String) novo.getProperty(keynovo);
		 boolean encontrou1conta=false;
		 boolean encontrouCMatchDescricao=false;
		boolean encontrouCMatchTotal=false;
		 for(ContaContabil cc:cctipo) {
				// encontrou1conta=false;
				// encontrouCMatchDescricao=false;
				// encontrouCMatchTotal=false;
			 if(keynovo.trim().equals(cc.getContaContabil().trim()) && valuep1.split(";")[0].trim().equals(cc.getDescricao().trim())) {
				 ValorContabil vc = new ValorContabil();
				 vc.setDemonstrativo(dm);
				 vc.setContaContabil(cc);
				 String[] par=  valuep1.split(";");
			    	String conta = valuep1.split(";")[0];
			    	String value=null;
			    	Double dvalor = null;
			    	if(par.length>1) {
			    		
				    	 value = par[1].replaceAll("\\.", "").replaceAll(",", ".");
				
				    	 dvalor = Double.parseDouble(value);
			
			    	}
			    vc.setValor(dvalor);
			    encontrou1conta=true;
			    encontrouCMatchDescricao = true;
			    encontrouCMatchTotal=true;
			    em.getTransaction()
			    .begin();
			  	try {
		    	persiste(vc);
		    	 em.flush();
		    	 em.clear();
		    	 em.getTransaction()
		            .commit();
			  	}
		    	catch(Exception e1) {
		    	//	 em.flush();
			    //	 em.clear();
		    		em.getTransaction().rollback();
		    	}
			 }else if(keynovo.trim().equals(cc.getContaContabil().trim()) ){
				 //encontrou um match de conta
				 encontrou1conta=true;
					 //ele pode vir a encontrar depois
				 
			 }else if (valuep1.split(";")[0].trim().equals(cc.getDescricao().trim())) {
				 encontrouCMatchDescricao = true;
			 }
			
			 
		 }
		 if(!encontrouCMatchTotal) {
			 try {
				   em.getTransaction()
				    .begin();
				String valuen =(String) novo.getProperty(keynovo);
				ContaContabil cnovo = new ContaContabil();
		
				cnovo.setContaContabil(keynovo.trim());
				cnovo.setDescricao(valuen.split(";")[0]);
				cnovo.setDemonstrativo(dm);
		    	cnovo.setTipoDemonstrativo(tp1);
		    	cnovo.setAnalise(0);
		    	
		    	
		   	 ValorContabil vc = new ValorContabil();
			 vc.setDemonstrativo(dm);
			 vc.setContaContabil(cnovo);
			 String[] par=  valuen.split(";");
		    	String conta = par[0];
		    	String value=null;
		    	Double dvalor = null;
		    	if(par.length>1) {
		    		
			    	 value = par[1].replaceAll("\\.", "").replaceAll(",", ".");
			
			    	 dvalor = Double.parseDouble(value);
		
		    	}
		    vc.setValor(dvalor);
		   
		 
		  
		  	persiste(cnovo);
	    	persiste(vc);
	    	 em.flush();
	    	 em.clear();
	    	 em.getTransaction()
	            .commit();
		  	}
	    	catch(Exception e1) {
	    	//	 em.flush();
		    //	 em.clear();
	    		em.getTransaction().rollback();
	    	}
		    	
			
		 }
		/* if(!encontrou1conta ) {//&& encontrouCMatchDescricao
			//encontrou descricao mas n„o encontrou conta 
			 count++;
				sb1.append("\t"+count+"- "+keynovo + " "+ novo.getProperty(keynovo)+"\n");
				String value =(String) novo.getProperty(keynovo);
				ContaContabil cnovo = new ContaContabil();
				cnovo.setContaContabil(keynovo.trim());
				cnovo.setDescricao(value.split(";")[0]);
				cnovo.setDemonstrativo(dm);
				
		    	
		    
		    	cnovo.setTipoDemonstrativo(tp1);
		    
				ContaComparada ccomp = new ContaComparada();
				ccomp.setComparado(cnovo);
				ccomp.setTipoComparacao("N");
				String[] niveisConta = keynovo.trim().split("\\.");
				String contaInicial = niveisConta[0];
				String contasPai[] = new String[niveisConta.length-1];
				String sinicial = sigla;
				for(int i=1;i<niveisConta.length-1;i++) {
					
					contaInicial +="."+niveisConta[i];
					String estaConta = (String)novo.getProperty(contaInicial);
					sinicial+=" > "+estaConta.split(";")[0];
					
				}
				
				ccomp.setRaiz(sinicial);
				 divergencia.addNaoExistencia(ccomp);
				//ver sobre os possiveis correspondntes
				sb1.append(possivelCorrespondente(cctipo,keynovo.trim(),value.split(";")[0],ccomp)); 
				System.out.println("");
			 
		 }
		 if(encontrou1conta && !encontrouCMatchDescricao) {
			 //encontrou conta mas n„o encontrou descricao
			 
			 
			// sb.append("\t"+count+" - "+  keynovo + " "+ existentes.getProperty(keynovo).split(";")[0]+"\n");
				
			//	String value =(String) existentes.getProperty(keynovo);
			//	sb.append(possivelCorrespondente(novas,keynovo.trim(),valuep1.split(";")[0],null));
				
				sb2.append("\t"+count+" - " +keynovo + " "+ novo.getProperty(keynovo).split(";")[0]+"\n");		
				String valuep2 =(String) novo.getProperty(keynovo);
				
				
				ContaContabil cnovo = new ContaContabil();
				cnovo.setContaContabil(keynovo.trim());
				cnovo.setDescricao(valuep2.split(";")[0]);
				
			
		    
		    	// Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where e.siglaTipo=\'"+sigla+"\'");
		    	// TipoDemonstrativo  tp = (TipoDemonstrativo) query.getSingleResult();
		    
		    	cnovo.setTipoDemonstrativo(tp1);
		    	cnovo.setDemonstrativo(dm);
				ContaComparada ccomp = new ContaComparada();
				ccomp.setComparado(cnovo);
				ccomp.setTipoComparacao("D");
				String[] niveisConta = keynovo.trim().split("\\.");
				String contaInicial = niveisConta[0];
				String contasPai[] = new String[niveisConta.length-1];
				String sinicial = sigla;
				for(int i=1;i<niveisConta.length-1;i++) {
					
					contaInicial +="."+niveisConta[i];
					String estaConta = (String)novo.getProperty(contaInicial);
					sinicial+=" > "+estaConta.split(";")[0];
					
				}
				
				ccomp.setRaiz(sinicial);
				divergencia.addDiferente(ccomp);
				
				
				sb2.append(possivelCorrespondente(cctipo,keynovo.trim(),valuep2.split(";")[0],ccomp));
				sb2.append("-\n");
			 
			 
		 }
		 if(sb1.toString().length()>0) {
			 	sb.append("\nconta Contabil n„o encontrada\n");
			 	sb.append(sb1+"\n");
			}
		 if(sb2.toString().length()>0) {
			 	sb.append("\ntem nos dois mas se trata de outro tipo de conta\n");
			 	sb.append(sb2+"\n");
			}
		 
	*/
		// if(!encontrou1conta && !encontrouCMatchDescricao) {
			 //n„o encontrou nenhum dos dois
		// }
		 
	 }
//	 em.flush();
	// em.clear();
	// em.getTransaction()
  //      .commit();
	
	
	 return sb.toString();
}

public static String compara( Properties p2, String sigla, Integer data, String cvm) {

	 StringBuilder sb = new StringBuilder();
	 	String retorno1 = comparaExistencia(p2,sigla,data,cvm);
	 	if(retorno1.length()>0) {
	 	//sb.append(desc1 +" x " +desc2 +"\n");
	 	sb.append(retorno1+"\n");
		}
	 	
	 /*	String retorno2 = comparaExistencia(p2,p1);
	 	if(retorno2.length()>0) {
	 		sb.append(desc2 +" x " +desc1 +"\n");
		 	sb.append(retorno2+"\n");
		}*/
	
	 	
	 	
	 	
//	 System.out.println("\ntem nos dois mas se trata de outro tipo de conta");
	 //tring retorno3 = dif(p1,p2,sigla,data,cvm, desc1, desc2);
	
	 return sb.toString();
}
static List<ContaContabil> cc1;
static Empresa e1;
static EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
static Divergencia divergencia;
public RelatorioDiferenca rdif = new RelatorioDiferenca();
public PersisteContasUnificando( String cvmProp,  String dataProp, String perProp ){

	
//	String cvm1=cvmbd;
	String cvm2=cvmProp ; //"5258";
	
		//e1 = em.find(Empresa.class, Integer.parseInt(cvm1));
		Empresa e2 =null;
		cc1=null;
		List<ContaContabil> cc2=null;
		
		//String dp1=databd;
		String dp2=dataProp;
		//String parte1 = dp1.substring(0, 2);
    	//String parte2 = dp1.substring(2,6);
    //	Integer dp1db = Integer.parseInt(parte2+parte1);
    	
		String parte1 = dp2.substring(0, 2);
		String parte2 = dp2.substring(2,6);
    	Integer dp2db = Integer.parseInt(parte2+parte1);
    	
  // 	 Query query = em.createQuery("SELECT e FROM ContaContabil e where e.demonstrativo=\'"+cvm1+"\' and e.data="+dp1db);
	//   cc1 = (List<ContaContabil>) query.getResultList();
	   /*Query query2 = em.createQuery("SELECT e FROM ContaContabil e where e.empresa.cvm=\'"+cvm2+"\' and e.data="+dp2db);
	   cc2 = (List<ContaContabil>) query2.getResultList();*/
		//if(cvm1.equals(cvm2)) {
		
		//	 e2 = e1;
			 
		//}else
		//{
			e2 = em.find(Empresa.class, Integer.parseInt(cvm2));

				
		//}
		
		
		
	
//	String per1=perbd;
	String per2=perProp;
	//String desc1= cvm1 + "_" + dp1+ "_"+ per1;
	String desc2= cvm2 + "_" + dp2+ "_"+ per2;
//	 PeriodoToProperties ptp = new PeriodoToProperties(dp1,cvm1,per1);
	 PeriodoToProperties ptp2 = new PeriodoToProperties(dp2,cvm2,per2);
	 
	// Properties dr1 = ptp.getEdp().getDr();
	 Properties dr2=ptp2.getEdp().getDr();

	 System.out.println("Resultado Abrangente");
	//  dr1 = ptp.getEdp().getDra();
	  dr2=ptp2.getEdp().getDra();
	  divergencia = new Divergencia();
	 	
	  	String retorno1 =  compara(dr2,"DRA",dp2db,cvm2);
	  	rdif.setDra(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	  
	  
	  
	  System.out.println("Resultados");
	//  dr1 = ptp.getEdp().getDr();
	  dr2=ptp2.getEdp().getDr();
	  
	  //compara(dr1,dr2,"DR "+ desc1 ,desc2);
	  divergencia = new Divergencia();
		retorno1 =  compara(dr2,"DR",dp2db,cvm2);
		rdif.setDr(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	  
	  System.out.println("Ativo");
//	  dr1 = ptp.getEdp().getBpa();
	  dr2=ptp2.getEdp().getBpa();
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr2,"BPA",dp2db,cvm2);
	  rdif.setBpa(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	 	
	  System.out.println("Passivo");
	//  dr1 = ptp.getEdp().getBpp();
	  dr2=ptp2.getEdp().getBpp();
	  

	  divergencia = new Divergencia();
	  retorno1 =  compara(dr2,"BPP",dp2db,cvm2);
	  rdif.setBpp(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	  
	  System.out.println("Fluxo de caixa");
	///  dr1 = ptp.getEdp().getFc();
	  dr2=ptp2.getEdp().getFc();
	  
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr2,"FC",dp2db,cvm2);
	  rdif.setFc(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	 	
	 	
	  System.out.println("Valor adicionado");
	//  dr1 = ptp.getEdp().getDva();
	  dr2=ptp2.getEdp().getDva();
	  
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr2,"DVA",dp2db,cvm2);
	  rdif.setDva(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferen√ßas");
	 	}
	 //	em.close();
}
public static void main(String []args) {
	String cvm ="5258";
//	new PersisteIgualdes("5258","5258","122011","122012","A", "A");
	//new PersisteIgualdes(cvm,"122015", "A");
//	new PersisteIgualdes(cvm,"122016", "A");
//	new PersisteIgualdes(cvm,"122017", "A");
//	new PersisteIgualdes(cvm,"122018", "A");
	new PersisteContasUnificando(cvm,"032011", "T");
	new PersisteContasUnificando(cvm,"062011", "T");
	new PersisteContasUnificando(cvm,"092011", "T");
	
	new PersisteContasUnificando(cvm,"032012", "T");
	new PersisteContasUnificando(cvm,"062012", "T");
	new PersisteContasUnificando(cvm,"092012", "T");
	
	
	new PersisteContasUnificando(cvm,"032013", "T");
	new PersisteContasUnificando(cvm,"062013", "T");
	new PersisteContasUnificando(cvm,"092013", "T");
	
	new PersisteContasUnificando(cvm,"032014", "T");
	new PersisteContasUnificando(cvm,"062014", "T");
	new PersisteContasUnificando(cvm,"092014", "T");
	
	new PersisteContasUnificando(cvm,"032015", "T");
	new PersisteContasUnificando(cvm,"062015", "T");
	new PersisteContasUnificando(cvm,"092015", "T");
	
	new PersisteContasUnificando(cvm,"032016", "T");
	new PersisteContasUnificando(cvm,"062016", "T");
	new PersisteContasUnificando(cvm,"092016", "T");
	
	new PersisteContasUnificando(cvm,"032017", "T");
	new PersisteContasUnificando(cvm,"062017", "T");
	new PersisteContasUnificando(cvm,"092017", "T");
	
	
	
	new PersisteContasUnificando(cvm,"032018", "T");
	new PersisteContasUnificando(cvm,"062018", "T");
	new PersisteContasUnificando(cvm,"092018", "T");
	

	new PersisteContasUnificando(cvm,"032019", "T");
	new PersisteContasUnificando(cvm,"062019", "T");
	new PersisteContasUnificando(cvm,"092019", "T");
//	new PersisteIgualdes(cvm,"122016", "A");
//	new PersisteIgualdes(cvm,"122017", "A");
//	new PersisteIgualdes(cvm,"122018", "A");
	//new PersisteIgualdes("9342","122019", "A");
	// dif(dr2,dr1);
}
}
