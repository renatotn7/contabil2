package br.com.cvm.bd;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.ContaContabil;
import br.com.cvm.bd.model.ValorContabil;
import br.com.cvm.leitor.PeriodoToProperties;

public class AccountBDReader {
 public static void main(String args[]) {
	 EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		String cvm1="5258";
		String cvm2="5258";
		String dp1="122012";
		String dp2="122011";
		String per1="A";
		String per2="A";
		String desc1= cvm1 + "_" + dp1+ "_"+ per1;
		String desc2= cvm2 + "_" + dp2+ "_"+ per2;
	
		String parte1 = dp1.substring(0, 2);
    	String parte2 = dp1.substring(2,6);
    	Integer dp1db = Integer.parseInt(parte2+parte1);
    	
    	 parte1 = dp2.substring(0, 2);
    	 parte2 = dp2.substring(2,6);
    	Integer dp2db = Integer.parseInt(parte2+parte1);
		 Query query = em.createQuery("SELECT e FROM ContaContabil e where e.empresa.cvm=\'"+cvm2+"\' and e.data<="+dp2db +"  and idrefconta is null" );
		List<ContaContabil>   cc1 = (List<ContaContabil>) query.getResultList();
		
		Query query2 = em.createQuery("SELECT e FROM ContaContabil e where e.empresa.cvm=\'"+cvm1+"\' and e.data<="+dp1db );
			List<ContaContabil>   cc2 = (List<ContaContabil>) query2.getResultList();
			Properties p = new Properties();
		//@TODO : VER
			for(ContaContabil c : cc1) {
				 p.put(c.getContaContabil(), c);
			}
			for(ContaContabil c : cc2) {
				 p.put(c.getContaContabil(),c );
			}
			
			 PeriodoToProperties ptp2 = new PeriodoToProperties(dp2,cvm2,per2);
			 Properties pdra = ptp2.getEdp().getBpa();
			 Properties pbpa = ptp2.getEdp().getBpa();
			 Properties pbpp = ptp2.getEdp().getBpa();
			 Properties pfc = ptp2.getEdp().getBpa();
			 Properties pdr = ptp2.getEdp().getBpa();
			 Properties pdva = ptp2.getEdp().getBpa();
		
			 for(Object key: p.keySet()) {
				 String skey = (String) key;
				 ContaContabil c =(ContaContabil) p.get(skey);
				 ValorContabil vc = new ValorContabil();
				 String valor="0.0";
				  if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DRA")) {
					  
					   valor = pdra.getProperty(skey).split(";")[1];
					 
					   
				   }
				   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "BPA")) {
					   valor =pbpa.getProperty(skey).split(";")[1];
					
				   }
				   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "BPP")) {
					   valor = pbpp.getProperty(skey).split(";")[1];
				   }
				   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "FC")) {
					   valor =  pfc.getProperty(skey).split(";")[1];
				   }
				   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DR")) {
					   valor =  pdr.getProperty(skey).split(";")[1];
				   }
				   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DVA")) {
					    valor =  pdva.getProperty(skey).split(";")[1];
				   }
				   if(valor==null) {
					   vc.setValor(0.0); //não tem nullo?
				   }else {
					   vc.setValor(Double.parseDouble(valor));
				   }
				   vc.setContaContabil(c);
				 
					em = PersistenceManager.INSTANCE.getEntityManager();
					em.getTransaction()
			        .begin();
					  em.persist(vc);
					em.getTransaction()
			        .commit();
					
			 }
			 em.close();
 }
}
