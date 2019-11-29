package br.com.cvm.bd;

import java.util.Properties;

import javax.persistence.EntityManager;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.Account;
import br.com.cvm.leitor.PeriodoToProperties;

public class PersisteAccountsOld {
	static EntityManager em;
	public static void persiste(Account account) {
		em.getTransaction()
        .begin();
		  em.persist(account);
		  em.getTransaction()
	        .commit();
	}
	public static  void close(){
		
	    em.close();
	    PersistenceManager.INSTANCE.close();
	}
	
	public static void persisteAccount(Properties dr1, String cvm, String data, String tipo,String periodo) {
		
		 for(Object key:dr1.keySet()) {
			 	
			 String skey = (String) key;
			String conteudo=(String) dr1.get(skey);
			 try {
				Account account = new Account();
			    account.setAccount(skey);
			    account.setCvm(cvm);
			    account.setData(data);
			    String[] conteudos=conteudo.split(";");
			    account.setDescricao(conteudos[0]);
			    account.setTipo(0);
			    account.setPeriodo(periodo);
			    Double valor = null;
			   
			    if(conteudos.length>1) {
			    	String svalor = conteudo.split(";")[1];
			    	valor = Double.parseDouble(svalor);
			    }
			 
			    account.setValor(valor);
			    persiste(account);
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 
	}
	public static void main(String[] args) {
		
		// TODO Stub de método gerado automaticamente
		em = PersistenceManager.INSTANCE.getEntityManager();
		
		String cvm = "5258";
		String data = "122018";
		
		String periodo = "A";
		 PeriodoToProperties ptp = new PeriodoToProperties(data,cvm,periodo);
		 
		 System.out.println("DRA");
		 Properties dr1 = ptp.getEdp().getDra();
		 persisteAccount(dr1,cvm,data,"DRA",periodo);
		 
		  System.out.println("DR");
		  dr1 = ptp.getEdp().getDr();
		  persisteAccount(dr1,cvm,data,"DR",periodo);
		  
		
		  System.out.println("BPA");
		  dr1 = ptp.getEdp().getBpa();
		  persisteAccount(dr1,cvm,data,"BPA",periodo);
		  
		  System.out.println("BPP");
		  dr1 = ptp.getEdp().getBpp();
		  persisteAccount(dr1,cvm,data,"BPP",periodo);
		  
		  System.out.println("FC");
		  dr1 = ptp.getEdp().getFc();
		  persisteAccount(dr1,cvm,data,"FC",periodo);
		
		  
		
		  System.out.println("DVA");
		  dr1 = ptp.getEdp().getDva();
		  persisteAccount(dr1,cvm,data,"DVA",periodo);
		/*Account account = new Account();
	    account.setAccount("1");
	    account.setCvm("1");
	    account.setData("122008");
	    account.setDescricao("ola");
	    account.setTipo("DRM");
	    account.setValor(3.1);
	    
	    persiste(account);*/

		  close();
	   
	  
	  }
	}


