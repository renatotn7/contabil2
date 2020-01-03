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

public class PersisteProtocol {
public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
	EntityManager em = JPAUtil.INSTANCE.getEntityManager();
;
		Query queryanalisar = em.createQuery("Select c from Demonstrativo c where c.protocolo is null");

		List<Demonstrativo> demonstrativos = (List<Demonstrativo>) queryanalisar.getResultList();
		
		for(Demonstrativo dem:demonstrativos) {
			File f1 = new File(dem.getEmpresa().getCvm()+"/"+dem.getEmpresa().getCvm()+"_T");
			Properties pcheckCodigo=new Properties();
			if(f1.exists()) {
		
			  InputStream input = new FileInputStream(dem.getEmpresa().getCvm()+"/"+dem.getEmpresa().getCvm()+"_T");
			  		System.out.println(dem.getEmpresa().getCvm()+"/"+dem.getEmpresa().getCvm()+"_T");
					Properties prop = new Properties();
					prop.loadFromXML(input);
					String sdata = dem.getData()+"";
					String ano= sdata.substring(0, 4);
					String mes= sdata.substring(4, 6);
					
					System.out.println(mes+ano+" "+prop.get(mes+ano));
					if(prop.get(mes+ano)!=null) {
						em.getTransaction().begin();
						dem.setProtocolo((String)prop.get(mes+ano));
						em.getTransaction().commit();
					}
					
			}
		}
}
}
