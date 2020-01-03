package br.com.readsitecvm.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.ValorContabil;

public class DepurandoOBanco {
 public static void main(String args[]) {
	 int idDemonstrativo = 1294;
		EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		;
				Query queryanalisar1 = em.createQuery("Select c from Demonstrativo c where c.idDemonstrativo ="+idDemonstrativo);

				Demonstrativo dem = (Demonstrativo) queryanalisar1.getSingleResult();
		
	
		String squery  = "Select c from ValorContabil c where c.demonstrativo.idDemonstrativo = "+dem.getIdDemonstrativo()+"  and c.contaContabil.tipoDemonstrativo.idTipo in ("+5+") order by c.contaContabil.contaContabil "  ;
		 Query queryanalisar = em.createQuery(squery);
	
		 List<ValorContabil>  contasAnalisar = (List<ValorContabil>) queryanalisar.getResultList();
			System.out.println(dem.getData()+" "+dem.getEmpresa().getCvm());
		 for(ValorContabil cc : contasAnalisar) {
			 System.out.println(cc.getContaContabil().getContaContabil() + " "+ cc.getContaContabil().getDescricao()+ " "+cc.getValor()+" "+cc.getContaContabil().getIdContaContabil() );
		 }
 }
}
