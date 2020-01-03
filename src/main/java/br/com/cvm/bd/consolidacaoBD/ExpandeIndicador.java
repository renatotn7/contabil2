package br.com.cvm.bd.consolidacaoBD;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.ContaContabil;

public class ExpandeIndicador {
	
	public void expande() {
		boolean achou = true;
		while (achou) {
			achou = false;
			 EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
			Query queryanalisar = em.createQuery("Select c from ContaContabil c where c.idCalculo is not null");

			List<ContaContabil> contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();

			em.getTransaction().begin();
			for (ContaContabil cc : contaAnalisar) {
				List<ContaContabil> refcontas = cc.getRefContas();
				for (ContaContabil refs : refcontas) {
					if(refs.getIdCalculo()==null) {
						achou = true;
					}
					System.out.println(1);
					refs.setIdCalculo(cc.getIdCalculo());
				}
			
			
				
			}
			em.getTransaction().commit();
		}
		// TODO Stub de método gerado automaticamente
		// String[] niveisConta1 = cc.getContaContabil().split("\\.");
		// String contaInicial1 = niveisConta1[0];
		// String contasPai1[] = new String[niveisConta1.length-1];
		// String sinicial1 = cc.getTipoDemonstrativo().getSiglaTipo();
		// for(int i=1;i<niveisConta1.length-1;i++) {

		// contaInicial1 +="."+niveisConta1[i];
		/// //encontra no valor contabil fixando o plano para este considerado a conta
		// superior, pois é la que tem o plano fixo
		// Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e
		// where e.contaContabil.contaContabil=\'"+contaInicial1+"\' and
		// e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
	
	}
	public static void main(String[] args) {

		boolean achou = true;
		while (achou) {
			achou = false;
			 EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
			Query queryanalisar = em.createQuery("Select c from ContaContabil c where c.idCalculo is not null");

			List<ContaContabil> contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();

			em.getTransaction().begin();
			for (ContaContabil cc : contaAnalisar) {
				List<ContaContabil> refcontas = cc.getRefContas();
				for (ContaContabil refs : refcontas) {
					if(refs.getIdCalculo()==null) {
						achou = true;
					}
					System.out.println(1);
					refs.setIdCalculo(cc.getIdCalculo());
				}
			
			
				
			}
			em.getTransaction().commit();
		}
		// TODO Stub de método gerado automaticamente
		// String[] niveisConta1 = cc.getContaContabil().split("\\.");
		// String contaInicial1 = niveisConta1[0];
		// String contasPai1[] = new String[niveisConta1.length-1];
		// String sinicial1 = cc.getTipoDemonstrativo().getSiglaTipo();
		// for(int i=1;i<niveisConta1.length-1;i++) {

		// contaInicial1 +="."+niveisConta1[i];
		/// //encontra no valor contabil fixando o plano para este considerado a conta
		// superior, pois é la que tem o plano fixo
		// Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e
		// where e.contaContabil.contaContabil=\'"+contaInicial1+"\' and
		// e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
	}

}
