package br.com.cvm.bd.completadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Periodo;

public class CompletaPeriodo {
public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
	EntityManager em = JPAUtil.INSTANCE.getEntityManager();
;
		Query queryanalisar = em.createQuery("Select c from Demonstrativo c ");

		List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryanalisar.getResultList();
		
		for(Demonstrativo dem:demonstrativos) {
			
						em.getTransaction().begin();
						if((dem.getData()+"").endsWith("12")) {
							 Query query = em.createQuery("SELECT e FROM Periodo e where e.idPeriodo ="+2);
							 
							Periodo per= (Periodo) query.getSingleResult();
							dem.setPeriodo(per);
						}else {
							 Query query = em.createQuery("SELECT e FROM Periodo e where e.idPeriodo ="+1);
							 
								Periodo per= (Periodo) query.getSingleResult();
								dem.setPeriodo(per);
						}
						em.getTransaction().commit();
					
					
			}
		}
}

