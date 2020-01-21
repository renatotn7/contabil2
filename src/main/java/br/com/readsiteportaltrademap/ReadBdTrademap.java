package br.com.readsiteportaltrademap;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.IndicadorTrademap;

public class ReadBdTrademap {

	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
		///	{id_cvm_company:5489,dt_entry:[20050331,20050630,20050930,20051231 ,20060331,20060630,20060930,20061231 ,20070331,20070630,20070930,20071231 ,20080331,20080630,20080930,20081231 ,  20090331,20090630,20090930,20091231,20100331,20100630,20100930,20101231 ,20110331,20110630,20110930,20111231 ,20120331,20120630,20120930,20121231 ,20130331,20130630,20130930,20131231 ,20140331,20140630,20140930,20141231 ,20150331,20150630,20150930,20151231 ,20160331,20160630,20160930,20161231 ,20170331,20170630,20170930,20171231 ,20180331,20180630,20180930,20181231 ,20190331,20190630,20190930,20191231],id_company_type:2}
		EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		;
		EntityTransaction entityTransaction = em
				   .getTransaction();
		TypedQuery<IndicadorTrademap> queryanalisar2 = em.createQuery("Select c from IndicadorTrademap c where c.indicador = 'ebitda' and c.cvm = 	" + 5258 + " order by data" ,IndicadorTrademap.class);
		
		
			List<IndicadorTrademap>  indictrademap = queryanalisar2.getResultList();
			for(IndicadorTrademap ind:indictrademap) {
				Double d = new Double(ind.getValor());
				
				System.out.println(ind.getData() + " "+ d);
			}
	}

}
