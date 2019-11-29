package br.com.cvm.bd;
import javax.persistence.EntityManager;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.Account;
public class Main {

	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
		 Account account = new Account();
		    account.setAccount("1");
		    account.setCvm("1");
		    account.setData("122008");
		    account.setDescricao("ola");
		    account.setTipo(0);
		    account.setValor(3.1);
		    EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		    em.getTransaction()
		        .begin();
		    em.persist(account);
		    em.getTransaction()
		        .commit();
		    em.close();
		    PersistenceManager.INSTANCE.close();
		  }
	}


