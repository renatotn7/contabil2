package br.com.cvm.miner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;

public class te {


public static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
public static void main(String[] args) {

	Query query = em.createQuery("SELECT e FROM ContaContabil e where idCalculo is not null"	);
	
	
	List<ContaContabil> cc1 = (List<ContaContabil>) query.getResultList();
	for(ContaContabil c1:cc1) {
		List<Calculo> calcs =c1.listCalculos();
		c1.getAnalise();
		
	}

	}

}
