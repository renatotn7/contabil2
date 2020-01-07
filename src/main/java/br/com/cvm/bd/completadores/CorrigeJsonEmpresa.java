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
import javax.persistence.TypedQuery;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;

public class CorrigeJsonEmpresa {
public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
	EntityManager em = JPAUtil.INSTANCE.getEntityManager();
;
		TypedQuery<Empresa> queryanalisar = em.createQuery("Select c from Empresa c where c.fundJson is not null",Empresa.class);

		List<Empresa> empresas = queryanalisar.getResultList();
		
		for(Empresa emp:empresas) {
		
						em.getTransaction().begin();
						emp.setFundJson(emp.getFundJson().replaceAll("\"\\_\\_typename\"\\:\"BrazilianCompany", "\"__typename\":\""));
						em.getTransaction().commit();
					
					
			}
		}
}

