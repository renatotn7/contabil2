package br.com.cvm.miner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Expressao;
//import br.com.cvm.bd.modelBD.ContaContabilIndic;
import br.com.cvm.bd.modelBD.FundIndicador;
//import br.com.cvm.bd.modelBD.IndicadorRelac;
import br.com.cvm.bd.modelBD.PropstaConfIndicDetalhe;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;

public class LinkaIndicador {
	
	public static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
public static void main(String[] args) {

	Query query = em.createQuery("SELECT e FROM PropstaConfIndicHeader e where  e.qtdInicial = qtdEncontrada"	);
	
	

	
	List<PropstaConfIndicHeader> cc1 = (List<PropstaConfIndicHeader>) query.getResultList();
	ArrayList<String> strs = new ArrayList<String>();
	for(PropstaConfIndicHeader pcih : cc1) {
	
		List<Calculo> calculo =null;
		
		
	
		
		
		int qtdColunas = pcih.getQtdColunas();
		for(PropstaConfIndicDetalhe pcid : pcih.getPropstaConfIndicDetalhes()) {
			String concatenacao="";
			if(qtdColunas >= 1) {
				ContaContabil cb = pcid.getContaContabil1Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 2) {
				ContaContabil cb = pcid.getContaContabil2Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 3) {
				ContaContabil cb = pcid.getContaContabil3Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 4) {
				ContaContabil cb = pcid.getContaContabil4Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(strs.contains(concatenacao)) {
				continue;
			}else {
				strs.add(concatenacao);
			}
			if(qtdColunas >= 1) {
				int coluna = 1;
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil1Bean();
				
				criaCalculo(pcih, coluna, cb);
			
			}
			if(qtdColunas >= 2) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil2Bean();
				criaCalculo(pcih, 2, cb);

			}
			if(qtdColunas >= 3) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil3Bean();
				criaCalculo(pcih, 3, cb);

			}
			if(qtdColunas >= 4) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil4Bean();
				criaCalculo(pcih, 4, cb);
			}
		}

	
	} 
		/*try {
		Query query2 = em.createQuery("SELECT e FROM IndicadorRelac e where e.expressao=\'" + pcih.getExpressao()
				+"\' and e.indicador.idIndicador = "+ pcih.getIndicador().getIdIndicador() +" and e.colunas = "+ pcih.getQtdColunas()	);
		indicadorRelac = (IndicadorRelac) query2.getSingleResult();
		}catch(Exception e) {
			
		}
		if(indicadorRelac==null) {
			indicadorRelac = new IndicadorRelac();
			indicadorRelac.setExpressao(pcih.getExpressao());
			indicadorRelac.setColunas(pcih.getQtdColunas());
			indicadorRelac.setIndicador(pcih.getIndicador());
			
			Query query3 = em.createQuery("SELECT max (e.numero) FROM IndicadorRelac e where "
			+" e.indicador.idIndicador = "+ pcih.getIndicador().getIdIndicador() 	);
			Integer maxnumero = (Integer) query3.getSingleResult();
			if(maxnumero ==null) {
				indicadorRelac.setNumero(1);
			}else {
				maxnumero++;
				indicadorRelac.setNumero(maxnumero);
			}
			em.getTransaction().begin();
			em.persist(indicadorRelac);
			em.getTransaction().commit();
			
			//persiste o novo indicadorRelac se não houver
		}
		
		// query4 = em.createQuery("SELECT e FROM ContaContabilIndic e where e.expressao=\'" + pcih.getExpressao()
	//	+"\' and e.indicador.id_indicador = "+ pcih.getIndicador().getIdIndicador() +" and e.colunas = "+ pcih.getQtdColunas()	);
	//	IndicadorRelac ContaContabilIndic = (ContaContabilIndic) query4.getSingleResult();
		
		System.out.println(pcih.getQtdColunas() );
		int qtdColunas = pcih.getQtdColunas();
	
		for(PropstaConfIndicDetalhe pcid : pcih.getPropstaConfIndicDetalhes()) {
			String concatenacao="";
			if(qtdColunas >= 1) {
				ContaContabil cb = pcid.getContaContabil1Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 2) {
				ContaContabil cb = pcid.getContaContabil2Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 3) {
				ContaContabil cb = pcid.getContaContabil3Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(qtdColunas >= 4) {
				ContaContabil cb = pcid.getContaContabil4Bean();
				concatenacao+="|"+cb.getIdContaContabil();
			}
			if(strs.contains(concatenacao)) {
				continue;
			}else {
				strs.add(concatenacao);
			}
			if(qtdColunas >= 1) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil1Bean();
				ContaContabilIndic cci = new ContaContabilIndic();
				cci.setIndicadorRelac(indicadorRelac);
				
				cci.setContaContabil(cb);
				cci.setPosicao(1);
				em.persist(cci);
				em.getTransaction().commit();
				em.getTransaction().begin();
				cb.setContaContabilIndic(cci); 
				em.getTransaction().commit();
			}
			if(qtdColunas >= 2) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil2Bean();
				ContaContabilIndic cci = new ContaContabilIndic();
				cci.setIndicadorRelac(indicadorRelac);
				
				cci.setContaContabil(cb);
				cci.setPosicao(2);
				em.persist(cci);
				em.getTransaction().commit();
				em.getTransaction().begin();
				cb.setContaContabilIndic(cci); 
				em.getTransaction().commit();

			}
			if(qtdColunas >= 3) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil3Bean();
				ContaContabilIndic cci = new ContaContabilIndic();
				cci.setIndicadorRelac(indicadorRelac);
			
				cci.setContaContabil(cb);
				cci.setPosicao(3);
				em.persist(cci);
				em.getTransaction().commit();
				em.getTransaction().begin();
				cb.setContaContabilIndic(cci); 
				em.getTransaction().commit();

			}
			if(qtdColunas >= 4) {
				em.getTransaction().begin();
				ContaContabil cb = pcid.getContaContabil4Bean();
				ContaContabilIndic cci = new ContaContabilIndic();
				cci.setIndicadorRelac(indicadorRelac);
			
				cci.setContaContabil(cb);
				cci.setPosicao(4);
				em.persist(cci);
				em.getTransaction().commit();
				em.getTransaction().begin();
				cb.setContaContabilIndic(cci); 
				em.getTransaction().commit();

			}
		}
	//	em.getTransaction().commit();
		
		
	}*/
}
private static void criaCalculo(PropstaConfIndicHeader pcih, int coluna, ContaContabil cb) {
	boolean encontrou=false;
	if(cb.listCalculos()!=null) {
	for(Calculo c : cb.listCalculos()) {
		if(c.getExpressao().getExpressao().equals(pcih.getExpressao())&& c.getIndicador().getIdIndicador() == pcih.getIndicador().getIdIndicador() && c.getPosicao() == coluna) {
			encontrou=true;
		}
	}
	}
	
	if(!encontrou) {
		Calculo c = new Calculo();
		Query query2 = em.createQuery("SELECT e FROM Expressao e where e.expressao=\'" + pcih.getExpressao()+"\'"	);

		Expressao expressao = (Expressao) query2.getSingleResult();
		if(cb.getIdCalculo()==null  || cb.getIdCalculo()==0 ) {
				Query query3 = em.createQuery("SELECT max(e.idCalculo)  FROM Calculo e "	);
				
				Integer idcalculo = (Integer) query3.getSingleResult();
				if(idcalculo ==null) {
					idcalculo = 1;
				}else
				idcalculo++;
				
				c.setIdCalculo(idcalculo);
				cb.setIdCalculo(idcalculo);
				
		}else {
			c.setIdCalculo(cb.getIdCalculo());
		}
		c.setExpressao(expressao);
		
		c.setIndicador(pcih.getIndicador());
		c.setPosicao(coluna);
		c.setPreferencia(pcih.getQtdColunas());
		//em.getTransaction().begin();
		em.persist(c);
	
		
	}
	em.getTransaction().commit();
}
}
