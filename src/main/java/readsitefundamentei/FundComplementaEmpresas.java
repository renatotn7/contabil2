package readsitefundamentei;


import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.utils.ApacheHttpGet;

public class FundComplementaEmpresas {
	
	public static void atualizaNotaEDescricao() {
		EntityManager	em = JPAUtil.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Empresa e where e.descricao is null  ");

				
				 ArrayList<Empresa> empresas= (ArrayList<Empresa>) query.getResultList();
		ApacheHttpGet ahg = new ApacheHttpGet();
		
		for(Empresa emp: empresas) {
		boolean npassou=true;
				String descricao =null;
				String nota =null;
				String json =null;
		while(npassou) {
			try {
				
				String response=	ahg.getContent("https://fundamentei.com/br/"+emp.getRaizAtivo());
				if(response.contains("tentando acessar")) {
					npassou=false;
					continue;
				}
				System.out.println(response);
				descricao  = response.split("<meta name=\"description\" content=\"")[1].split("\"/>")[0].trim();
				nota  = response.split("\"overallRating\"\\:")[1].split(",")[0].trim();
				
				json =  response.split("\"application/json\">")[1].split("</script>")[0].trim(); 
				System.out.println(json);
				System.out.println(response);
				System.out.println(descricao);
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
		if(descricao !=null) {
		emp.setDescricao(descricao);
		emp.setNota(Double.parseDouble(nota));
		emp.setFundJson(json);
		}else {
			emp.setDescricao("-");
			
			emp.setFundJson("-");
		}
		em.getTransaction()
	    .commit();
		
		}
	}
	
public static void main(String args[]) {
	
	
	atualizaNotaEDescricao();
	
	
}
}
