package readsiteb3;


import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.utils.ApacheHttpGet;

public class b3ComplementaEmpresas {
	
	public static void atualizaRaizAtivo() {
		EntityManager	em = JPAUtil.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Empresa e where e.raizAtivo is null");

				
				 ArrayList<Empresa> empresas= (ArrayList<Empresa>) query.getResultList();
		ApacheHttpGet ahg = new ApacheHttpGet();
		
		for(Empresa emp: empresas) {
		boolean npassou=true;
				String codigoAtivo =null;
		while(npassou) {
			try {
				
				String response=	ahg.getContent("http://bvmf.bmfbovespa.com.br/pt-br/mercados/acoes/empresas/ExecutaAcaoConsultaInfoEmp.asp?CodCVM="+emp.getCvm());
				if(response.contains("Dados indisponiveis")) {
					npassou=true;
					continue;
				}
				codigoAtivo  = response.split("javascript\\:VerificaCotacao\\('")[1].split("','0'\\)\\;>")[0].substring(0,4);
				System.out.println(response);
				System.out.println(codigoAtivo);
			}catch(Exception e) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Bloco catch gerado automaticamente
					e1.printStackTrace();
				}
			}
			npassou=false;
		}
		 em.getTransaction()
	     .begin();
		if(codigoAtivo !=null) {
		emp.setRaizAtivo(codigoAtivo);
		}else {
			emp.setRaizAtivo("-");
		}
		em.getTransaction()
	    .commit();
		
		}
	}
	
public static void main(String args[]) {
	
	
	atualizaRaizAtivo();
	
	
}
}
