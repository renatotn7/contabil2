package br.com.cvm.bd.origemProperties.deprecated;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Abrangencia;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Periodo;
import br.com.cvm.bd.modelBD.TipoDemonstrativo;
import br.com.cvm.bd.modelBD.ValorContabil;
import br.com.cvm.leitor.origemBDeProperties.PeriodoToProperties;

public class PersisteAccounts {
	static EntityManager em;
	public static void persiste(Object obj) {
	
		  em.persist(obj);
		 
	}
	public static  void close(){
		
	    em.close();
	    PersistenceManager.INSTANCE.close();
	}
	public static void persisteDemonstrativo(Properties prop, int tipo,String cvm1, String dp1, String periodo) {
		
			
		    for(Object key: prop.keySet()) {
		    	try {
			    	String skey= (String) key;
			    	System.out.println(skey+ "   "  + prop.getProperty(skey));
			    	String[] par=  prop.getProperty(skey).split(";");
			    	String conta = prop.getProperty(skey).split(";")[0];
			    	String value=null;
			    	Double dvalor = null;
			    	if(par.length>1) {
			    		
			    	 value = par[1].replaceAll("\\.", "").replaceAll(",", ".");
			  //  try {
			    	 dvalor = Double.parseDouble(value);
			//    }catch(Exception ex) {
			    //	ex.printStackTrace();
			  //  }
			    	}
			   
			    	ValorContabil vc  = new ValorContabil();
			  
			    	ContaContabil cc = new ContaContabil();
			    	cc.setContaContabil(skey);
			    	cc.setDescricao(conta); 
			    	 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where e.idTipo="+tipo+"");
			    	 TipoDemonstrativo  tp = (TipoDemonstrativo) query.getSingleResult();
			    		cc.setAnalise(1);
			    	cc.setTipoDemonstrativo(tp);
			    	String p1 = dp1.substring(0, 2);
			    	String p2 = dp1.substring(2,6);
			    			    	persiste(cc);
			    	
			    	cc.setDemonstrativo(dm);
			      	vc.setContaContabil(cc);
			      	vc.setDemonstrativo(dm);
			      	vc.setValor(dvalor);
			      	
			    	
			    	persiste(vc);
		    	}catch(Exception e) {
		    		String skey= (String) key;
		    		String[] par=  prop.getProperty(skey).split(";");
		    	
		    		String conta = prop.getProperty(skey).split(";")[0];
			    	String value=null;
			    	if(par.length>1) {
			    	 value = prop.getProperty(skey).split(";")[1].replace("\\.", "").replace(",", ".");
			    	 
			    	}
			    	System.err.println(conta+ " - "+value);
		    		e.printStackTrace();
		    	}
		    }    	
		    
		

	}
	static Demonstrativo dm = new Demonstrativo();
	public static void persisteAbrangencia() {
					em = PersistenceManager.INSTANCE.getEntityManager();
					String cvm1="5258";
					em.getTransaction()
			        .begin();
				  	Empresa e = em.find(Empresa.class, Integer.parseInt(cvm1));
					dm.setEmpresa(e);
					Periodo p = em.find(Periodo.class, 2);
					dm.setPeriodo(p);
					dm.setEstadoCriacao(0);
					dm.setVersao(1);
					dm.setData(201411);
				//	try {
					String dp1="122014";
					String per1="A";
					em.persist(dm);
					
					 em.flush();
					 em.clear();
					 em.getTransaction()
				        .commit();
				//	}catch(Exception e1) {
						// Query query = em.createQuery("SELECT e FROM Demonstrativo e where e.Empresa="+e.getCvm()+" and e.");
				    	// TipoDemonstrativo  tp = (TipoDemonstrativo) query.getSingleResult();
				//	}
				//	
					
					
					
				
					
					try {
					em.getTransaction()
			        .begin();
					 PeriodoToProperties ptp = new PeriodoToProperties(dp1,cvm1,per1);
					 Properties prop =ptp.getEdp().getDr();
					 persisteDemonstrativo(prop, 2, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 prop =ptp.getEdp().getDra();
					 persisteDemonstrativo(prop, 1, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 prop =ptp.getEdp().getBpa();
					 persisteDemonstrativo(prop, 3, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 
					 prop =ptp.getEdp().getBpp();
					 persisteDemonstrativo(prop, 4, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 prop =ptp.getEdp().getFc();
					 persisteDemonstrativo(prop, 5, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 prop =ptp.getEdp().getDva();
					 persisteDemonstrativo(prop,6, cvm1,dp1,per1);
					 em.flush();
					 em.clear();
					 em.getTransaction()
				        .commit();
				/*Abrangencia abrangencia = new Abrangencia();
			    abrangencia.setDescricao("Ano até Aqui(Full)");
			    abrangencia.setSiglaAbrangencia("F");
			  
			    persiste(abrangencia);*/
			    /*abrangencia = new Abrangencia();
			    abrangencia.setDescricao("Trimestre");
			    abrangencia.setSiglaAbrangencia("T");
			  
			    persiste(abrangencia);*/
			 }catch(Exception e1) {
				 e1.printStackTrace();
				 
			 }
			 close();
		 
	}
	public static void main(String[] args) {
		
		persisteAbrangencia();
		 
	   
	  
	  }
	public static void persisteAccount(ContaContabil cc) {
		em = PersistenceManager.INSTANCE.getEntityManager();
		if(cc.getIdContaContabil()>0) {
			em.getTransaction()
	        .begin();
			ContaContabil e = em.find(ContaContabil.class, cc.getIdContaContabil());
			e.setRefConta(cc);
			e.setAnalise(cc.getAnalise());
			em.getTransaction()
	        .commit();
	//		close();
		}else {
		
		em.getTransaction()
        .begin();
		persiste(cc);
		em.getTransaction()
        .commit();
		close();
		}
	}
	}


