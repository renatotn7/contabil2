package br.com.cvm.bd.cargainicial.leitor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.ContaContabil;

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
		

	   	 Query queryanalisar = em.createQuery("Select c from ValorContabil c where c.contaContabil. = (SELECT min(e.demonstrativo.idDemonstrativo) FROM ContaContabil e where e.analise=0) and  c.analise=0");
	
		 List<ContaContabil>  contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();
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
