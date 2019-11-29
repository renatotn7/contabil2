package br.com.cvm.bd.cargainicial.leitor;

import java.util.Properties;

import javax.persistence.EntityManager;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.Abrangencia;
import br.com.cvm.bd.model.Account;
import br.com.cvm.bd.model.ContaContabil;
import br.com.cvm.bd.model.Empresa;
import br.com.cvm.bd.model.Periodo;
import br.com.cvm.bd.model.TipoDemonstrativo;
import br.com.cvm.leitor.PeriodoToProperties;

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
		try {
		    for(Object key: prop.keySet()) {
		    
			    	String skey= (String) key;
			    	System.out.println(skey+ "   "  + prop.getProperty(skey));
			    	String value = prop.getProperty(skey).split(";")[0];
			    	ContaContabil cc = new ContaContabil();
			    	cc.setContaContabil(skey);
			    	cc.setDescricao(value); 
			    	String p1 = dp1.substring(0, 2);
			    	String p2 = dp1.substring(2,6);
			    	cc.setData(Integer.parseInt(p2+p1));
			    	Empresa e = em.find(Empresa.class, Integer.parseInt(cvm1));
			    	cc.setEmpresa(e);
			    	Periodo p = em.find(Periodo.class, 2);
			    	cc.setPeriodo(p);
			    	TipoDemonstrativo td = em.find(TipoDemonstrativo.class, tipo);
			    	cc.setTipoDemonstrativo(td);
			    	cc.setVersao(1);
			    	persiste(cc);
			    	
		    }    	
		
	}catch(Exception e) {
		
	}
	}
	public static void persisteAbrangencia() {
		em = PersistenceManager.INSTANCE.getEntityManager();
	
		
					String cvm1="5258";
				
					String dp1="122011";
				
					String per1="A";
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
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
			 close();
		 
	}
	public static void main(String[] args) {
		
		persisteAbrangencia();
		 
	   
	  
	  }
	public static void persisteAccount(ContaContabil cc) {
		em = PersistenceManager.INSTANCE.getEntityManager();
		em.getTransaction()
        .begin();
		persiste(cc);
		em.getTransaction()
        .commit();
		close();
	}
	}


