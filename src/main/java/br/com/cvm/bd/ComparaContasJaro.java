package br.com.cvm.bd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.utils.JaroWinklerStrategy;
import entities.ContaCandidata;
import entities.ContaComparada;
import entities.Divergencia;

public class ComparaContasJaro {
	public ComparaContasJaro() {

	}
	private JaroWinklerStrategy jw = new JaroWinklerStrategy(0.05);
	private Divergencia divergencia=new Divergencia();
	private EntityManager	em = JPAUtil.INSTANCE.getEntityManager();
public  String possivelCorrespondente2(List<ContaContabil> cbd, List<ContaContabil> cbd2) {
	StringBuilder sb = new StringBuilder();
	boolean encontrou = false;
	/* for(Object key :p.keySet()) {
		 String key1 = (String)key;
		 String value1 = p.getProperty(key1.trim());
		 if(value2.equals(value1.split(";")[0])) {
			 encontrou = true;
			 sb.append("\t\tmatch 100%: "+key1 +" "+ value1.split(";")[0]+"\n");
		 }
	 }*/
	ArrayList<String> conta1= new ArrayList<String>();
	for(ContaContabil cnovo: cbd2) {

		ContaComparada ccomp = new ContaComparada();
		ccomp.setComparado(cnovo);
		ccomp.setTipoComparacao("N");
		String[] niveisConta = cnovo.getContaContabil().trim().split("\\.");
		String contaInicial = niveisConta[0];
		String contasPai[] = new String[niveisConta.length-1];
		String sinicial = cnovo.getTipoDemonstrativo().getSiglaTipo();
		
		for(int i=1;i<niveisConta.length-1;i++) {
			
			contaInicial +="."+niveisConta[i];
			 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial+"\' and e.demonstrativo.idDemonstrativo="+cnovo.getDemonstrativo().getIdDemonstrativo());
	    	 ContaContabil  tpai = (ContaContabil) query1.getSingleResult();
			String estaConta = (String)tpai.getDescricao();
			sinicial+=" > "+estaConta.split(";")[0];
			
		}
		
		ccomp.setRaiz(sinicial);
		
		
		ArrayList<ContaCandidata> ccands= new ArrayList<ContaCandidata>();
	int count=0;
	 for(ContaContabil cc:cbd) {
		 String key1 = (String)cc.getContaContabil().trim();
		 if(key1.equals("6.01.01.02")) {
			 int kk=0;
		 }
		 String value1 = cc.getDescricao().trim().toLowerCase().replaceAll(" de "," ").replaceAll(" da "," ").replaceAll(" do "," ").replaceAll(" e "," ").replaceAll(",", "").replaceAll("\\.", "");
		 String valuecnovo = cnovo.getDescricao().trim().toLowerCase().replaceAll(" de "," ").replaceAll(" da "," ").replaceAll(" do "," ").replaceAll(" e "," ").replaceAll(",", "").replaceAll("\\.", "");
		 
		 double jaroscore = jw.score(valuecnovo, value1.split(";")[0]);
		 
			 if(jaroscore>0.20) {
				
				 encontrou = true;
				 double perc = Math.round((jaroscore*100.0) * 100.0) / 100.0;
				 
			//	 System.out.println("\t\tmatch "+perc+"%: "+key1 +" "+ value1.split(";")[0]+"\n");
				
				 ContaCandidata ccand = new ContaCandidata();
				 
				 ccand.setConta(cc);
					String[] niveisConta1 = cc.getContaContabil().split("\\.");
					String contaInicial1 = niveisConta1[0];
					String contasPai1[] = new String[niveisConta1.length-1];
					String sinicial1 = cc.getTipoDemonstrativo().getSiglaTipo();
					for(int i=1;i<niveisConta1.length-1;i++) {
						
						contaInicial1 +="."+niveisConta1[i];
						//encontra no valor contabil fixando o plano para este considerado a conta superior, pois é la que tem o plano fixo
						 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial1+"\' and e.demonstrativo.idDemonstrativo="+cc.getDemonstrativo().getIdDemonstrativo());
				    	 ContaContabil  tpai = (ContaContabil) query1.getSingleResult();
						String estaConta = (String)tpai.getDescricao();
						sinicial1+=" > "+estaConta.split(";")[0];
						
					}
					double valordescontado = 10.0;
					double diminuicaoDoDesconto=3;
					boolean encontrouDiferente=false;
					//sistema de penalizacao
					for(int i = 0 ; i< niveisConta1.length && i< niveisConta.length;i++) {
						if(niveisConta1[i].equals(niveisConta[i]) && !encontrouDiferente) {
							if(valordescontado>2) { //limita a 2
							valordescontado-=diminuicaoDoDesconto; //quanto mais antecessor é, maior o desconto e quando achar um diferente desconta todo o resto
							}
						}else {
							encontrouDiferente=true;
							if(valordescontado>=0) {
								perc-=valordescontado;
							}
						}
					}
					//desconta se houver remanescentes
					for(int i = 0 ; i< Math.abs(niveisConta1.length - niveisConta.length);i++) {
						perc-=valordescontado;
					}
					
					if(perc>98) {
						
						
					//	em.getTransaction()
				     //   .begin();
					//	cnovo.setAnalise(1);
					//	cnovo.setRefConta(cc);
					
				//		em.getTransaction()
				 //       .commit();
					//	continue;
					}
					ccand.setSimilaridade(perc);
					ccand.setRaiz(sinicial1);
			
					if(perc>0) {
					ccands.add(ccand);
					}
					
					
				 
			 
			
		 }
		
	 }
	 Collections.sort(ccands,Collections.reverseOrder());
	 if(ccands!=null && ccands.size()>0 && ccands.get(0).getSimilaridade()<70) {
			
		//	em.getTransaction()
	      //  .begin();
			//cnovo.setAnalise(1);
		
		
		//	em.getTransaction()
	      //  .commit();
			continue;
	 }
	 if(ccands!=null && ccands.size()>0  ) {
	 for(ContaCandidata ccand : ccands) {
		 if(count<5) {
			 count++;
			 if(ccand.getSimilaridade()>=70) {
			 ccomp.addCandidato(ccand);
			 }
		 }
	 }
	 }
	 if(ccomp.getCandidatos()!=null) {
		divergencia.addDiferente(ccomp);
	 }
	}
	if(encontrou) sb.append("\n");	
	return sb.toString();
	
}

public Divergencia analisar() {

	
//	cc.getDemonstrativo().getData();
	//cc.getDemonstrativo().getEmpresa()
//	cc.getDemonstrativo().getPeriodo()
	//cc.getDemonstrativo().getVersao()
	
   	 Query queryanalisar = em.createQuery("Select c from ContaContabil c where c.demonstrativo.idDemonstrativo = (SELECT min(e.demonstrativo.idDemonstrativo) FROM ContaContabil e where e.analise=0) and  c.analise=0");
	 Query queryanalisadas = em.createQuery("Select c from ContaContabil c where  c.analise=1)");
	 List<ContaContabil>  contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();
	 List<ContaContabil>  contaAnalisadas = (List<ContaContabil>) queryanalisadas.getResultList();
	 possivelCorrespondente2(contaAnalisadas,contaAnalisar);
	 //em.close();
	 return divergencia;
}


public Divergencia analisar(List<ContaContabil> contasAnalisadas,Demonstrativo dem ) {

	
//	cc.getDemonstrativo().getData();
	//cc.getDemonstrativo().getEmpresa()
//	cc.getDemonstrativo().getPeriodo()
	//cc.getDemonstrativo().getVersao()
	String notin = "(";
	for(int i=0;i< contasAnalisadas.size()-1;i++) {
		ContaContabil cc = contasAnalisadas.get(i);
		notin+=cc.getDemonstrativo().getIdDemonstrativo()+",";
	}
	notin+=contasAnalisadas.get(contasAnalisadas.size()-1).getDemonstrativo().getIdDemonstrativo()+")";
	
	String in = "(";
	for(int i=0;i< contasAnalisadas.size()-1;i++) {
		ContaContabil cc = contasAnalisadas.get(i);
		in+=cc.getTipoDemonstrativo().getIdTipo()+",";
	}
	in+=contasAnalisadas.get(contasAnalisadas.size()-1).getTipoDemonstrativo().getIdTipo()+")";
	String squery  = "Select c.contaContabil from ValorContabil c where c.demonstrativo.idDemonstrativo = "+dem.getIdDemonstrativo()+" and c.contaContabil.demonstrativo.idDemonstrativo not in "+notin +" and c.contaContabil.tipoDemonstrativo.idTipo in "+in+" order by c.contaContabil.contaContabil "  ;
	 Query queryanalisar = em.createQuery(squery);
	
	 List<ContaContabil>  contasAnalisar = (List<ContaContabil>) queryanalisar.getResultList();
	 for(ContaContabil cc : contasAnalisar) {
		 System.out.println(cc.getContaContabil() + " "+ cc.getDescricao()+ " "+cc.getIdContaContabil() );
	 }
	 possivelCorrespondente2(contasAnalisar , contasAnalisadas);
	 //em.close();
	 return divergencia;
}
	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
	//	String cvm="";
	//	Integer data=0;
		
	//	ContaContabil cc =new ContaContabil();
		
	//	cc.getDemonstrativo().getData();
		//cc.getDemonstrativo().getEmpresa()
	//	cc.getDemonstrativo().getPeriodo()
		//cc.getDemonstrativo().getVersao()
		
	//   	 Query queryanalisar = em.createQuery("Select c from ContaContabil c where c.demonstrativo.idDemonstrativo = (SELECT min(e.demonstrativo.idDemonstrativo) FROM ContaContabil e where e.analise=0)");
	////	 Query queryanalisadas = em.createQuery("Select c from ContaContabil c where  c.analise=1)");
	//	 List<ContaContabil>  contaAnalisar = (List<ContaContabil>) queryanalisar.getResultList();
	//	 List<ContaContabil>  contaAnalisadas = (List<ContaContabil>) queryanalisadas.getResultList();
	//	 possivelCorrespondente2(contaAnalisadas,contaAnalisar);
		// divergencia=divergencia;
	}

}
