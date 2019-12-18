package br.com.cvm.bd.consolidacaoBD;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.ContaContabil;

public class AjustaPaiConta {
	static EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
public static void persiste(Object obj) {

	  em.persist(obj);
	 
}
public static  void close(){
	
    em.close();
    PersistenceManager.INSTANCE.close();
}
	public static void main(String[] args) {
		

	   	 Query queryanalisar = em.createQuery("Select c from ContaContabil c where c.contaPai is null");
	
		 List<ContaContabil>  contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();
		 
		 
		 for(ContaContabil cc :contaAnalisar ) {
			 try {
			String[] niveisConta1 = cc.getContaContabil().split("\\.");
				String contaInicial1 = niveisConta1[0];
				String contasPai1[] = new String[niveisConta1.length-1];
				String sinicial1 = cc.getTipoDemonstrativo().getSiglaTipo();
				String scPai = contaInicial1;
				for(int i=1;i<niveisConta1.length-1;i++) {
					scPai+="."+niveisConta1[i];
				}
				 Query querypai = em.createQuery("Select c.contaContabil from ValorContabil c where c.contaContabil.contaContabil ='"+scPai+"' and c.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo() );
					
				 ContaContabil  contaPai = (ContaContabil) querypai.getSingleResult();
				 cc.setContaPai(contaPai);
				 em.getTransaction()
			        .begin();
					persiste(cc);
					em.getTransaction()
			        .commit();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
				
		 }
		// TODO Stub de método gerado automaticamente
		//String[] niveisConta1 = cc.getContaContabil().split("\\.");
		//String contaInicial1 = niveisConta1[0];
	//	String contasPai1[] = new String[niveisConta1.length-1];
		//String sinicial1 = cc.getTipoDemonstrativo().getSiglaTipo();
	//	for(int i=1;i<niveisConta1.length-1;i++) {
			
		//	contaInicial1 +="."+niveisConta1[i];
		///	//encontra no valor contabil fixando o plano para este considerado a conta superior, pois é la que tem o plano fixo
		//	 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial1+"\' and e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
	}

}
