package br.com.cvm.leitor.usoemservicos;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Periodo;
import br.com.cvm.bd.modelBD.TipoDemonstrativo;
import br.com.cvm.leitor.origemBDeProperties.PeriodoToProperties;
import br.com.cvm.utils.JaroWinklerStrategy;
import entities.ContaCandidata;
import entities.ContaComparada;
import entities.Divergencia;
import entities.RelatorioDiferenca;

public class ExtratorDeDiferencasToObject {
public static Double getDouble(String value) {

String valor = value.split(";")[1];
valor = valor.replaceAll("\\.", "");
return Double.parseDouble(valor);
}
static JaroWinklerStrategy jw = new JaroWinklerStrategy(0.05);
public static String possivelCorrespondente(Properties p, String key2, String value2,ContaComparada contaComparada) {
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
	int count=0;
	 for(Object key :p.keySet()) {
		 String key1 = (String)key;
		 String value1 = p.getProperty(key1.trim());
		 double jaroscore = jw.score(value2, value1.split(";")[0]);
		 if(count<3) {
			 if(jaroscore>0.70) {
				 count++;
				 encontrou = true;
				 double perc = Math.round((jaroscore*100.0) * 100.0) / 100.0;
				 sb.append("\t\tmatch "+perc+"%: "+key1 +" "+ value1.split(";")[0]+"\n");
				 ContaContabil cc1 = getBdAccount(key1.trim());
				 ContaCandidata ccand = new ContaCandidata();
				 ccand.setSimilaridade(perc);
				 ccand.setConta(cc1);
					String[] niveisConta = cc1.getContaContabil().split("\\.");
					String contaInicial = niveisConta[0];
					String contasPai[] = new String[niveisConta.length-1];
					String sinicial = "";
					for(int i=1;i<niveisConta.length-1;i++) {
						
						contaInicial +="."+niveisConta[i];
						String estaConta = (String)p.getProperty(contaInicial);
						sinicial+=" > "+estaConta.split(";")[0];
						
					}
					ccand.setRaiz(sinicial);
				 contaComparada.addCandidato(ccand);
				 
			 }
		 }
	 }
	if(encontrou) sb.append("\n");	
	return sb.toString();
	
}
public static ContaContabil getBdAccount(String account) {
	for(ContaContabil c: cc1) {
		if(c.getContaContabil().equals(account)) {
			return c;
		}
	}
	return null;
}
public static String comparaExistencia(Properties existente ,Properties novo,String sigla, Integer data, String cvm) {
	int count = 0;
	StringBuilder sb = new StringBuilder(); 
	
	
	Demonstrativo dm = new Demonstrativo();
  	Empresa e = em.find(Empresa.class, Integer.parseInt(cvm));
	dm.setEmpresa(e);
	Periodo p = em.find(Periodo.class, 2);
	dm.setPeriodo(p);
	dm.setEstadoCriacao(0);
	dm.setVersao(1);
	dm.setData(data);
	
	
	
	 for(Object key :novo.keySet()) {
		 String keynovo = (String)key;
		if( existente.getProperty(keynovo.trim())==null) { //ve se encontra  a conta no bd
			//caso n�o encontre no bd
		//	ContaContabil c1 = getBdAccount(keynovo.trim());
			
		//	cnovo.setDescricao(descricao);
			//sera mesmo que � assim? eu n�o deveria ver ao contrario? se o novo existe no antigo?
			count++;
			sb.append("\t"+count+"- "+keynovo + " "+ novo.getProperty(keynovo)+"\n");
			String valuep1 =(String) novo.getProperty(keynovo);
			ContaContabil cnovo = new ContaContabil();
			cnovo.setContaContabil(keynovo.trim());
			cnovo.setDescricao(valuep1.split(";")[0]);
			cnovo.setDemonstrativo(dm);
			
	    	 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where e.siglaTipo=\'"+sigla+"\'");
	    	 TipoDemonstrativo  tp = (TipoDemonstrativo) query.getSingleResult();
	    
	    	cnovo.setTipoDemonstrativo(tp);
	    
			ContaComparada ccomp = new ContaComparada();
			ccomp.setComparado(cnovo);
			ccomp.setTipoComparacao("N");
			String[] niveisConta = keynovo.trim().split("\\.");
			String contaInicial = niveisConta[0];
			String contasPai[] = new String[niveisConta.length-1];
			String sinicial = sigla;
			for(int i=1;i<niveisConta.length-1;i++) {
				
				contaInicial +="."+niveisConta[i];
				String estaConta = (String)novo.getProperty(contaInicial);
				sinicial+=" > "+estaConta.split(";")[0];
				
			}
			
			ccomp.setRaiz(sinicial);
			 divergencia.addNaoExistencia(ccomp);
			
			sb.append(possivelCorrespondente(existente,keynovo.trim(),valuep1.split(";")[0],ccomp)); 
			System.out.println("");
		}
	 }
	
	 return sb.toString();
}
public static String dif(Properties existentes, Properties novas
		,String sigla, Integer data, String cvm, String desc1, String desc2) {
	
	
	Demonstrativo dm = new Demonstrativo();
  	Empresa e = em.find(Empresa.class, Integer.parseInt(cvm));
	dm.setEmpresa(e);
	Periodo p = em.find(Periodo.class, 2);
	dm.setPeriodo(p);
	dm.setEstadoCriacao(0);
	dm.setVersao(1);
	dm.setData(data);
	
	
	int count = 0;
	StringBuilder sb = new StringBuilder(); 
	for(Object key :existentes.keySet()) {
		 String keynovo = (String)key;
		if( novas.getProperty(keynovo)!=null ) {
			if(!existentes.getProperty(keynovo).split(";")[0].equals(novas.getProperty(keynovo).split(";")[0])){
				count++;
				sb.append("\t"+count+"- "+desc1 + " " +  keynovo + " "+ existentes.getProperty(keynovo).split(";")[0]+"\n");
				
				String valuep1 =(String) existentes.getProperty(keynovo);
			//	sb.append(possivelCorrespondente(novas,keynovo.trim(),valuep1.split(";")[0],null));
				
				sb.append("\t   "+desc2 + " " +keynovo + " "+ novas.getProperty(keynovo).split(";")[0]+"\n");		
				String valuep2 =(String) novas.getProperty(keynovo);
				
				
				ContaContabil cnovo = new ContaContabil();
				cnovo.setContaContabil(keynovo.trim());
				cnovo.setDescricao(valuep2.split(";")[0]);
				
			
		    
		    	 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where e.siglaTipo=\'"+sigla+"\'");
		    	 TipoDemonstrativo  tp = (TipoDemonstrativo) query.getSingleResult();
		    
		    	cnovo.setTipoDemonstrativo(tp);
		    	cnovo.setDemonstrativo(dm);
				ContaComparada ccomp = new ContaComparada();
				ccomp.setComparado(cnovo);
				ccomp.setTipoComparacao("D");
				String[] niveisConta = keynovo.trim().split("\\.");
				String contaInicial = niveisConta[0];
				String contasPai[] = new String[niveisConta.length-1];
				String sinicial = sigla;
				for(int i=1;i<niveisConta.length-1;i++) {
					
					contaInicial +="."+niveisConta[i];
					String estaConta = (String)novas.getProperty(contaInicial);
					sinicial+=" > "+estaConta.split(";")[0];
					
				}
				
				ccomp.setRaiz(sinicial);
				divergencia.addDiferente(ccomp);
				
				
				sb.append(possivelCorrespondente(existentes,keynovo.trim(),valuep2.split(";")[0],ccomp));
				sb.append("-\n");
				
			}
		
		}
	 }
	return sb.toString();
}
public static String compara(Properties p1, Properties p2, String sigla, Integer data, String cvm,  String desc1, String desc2) {

	 StringBuilder sb = new StringBuilder();
	 	String retorno1 = comparaExistencia(p1,p2,sigla,data,cvm);
	 	if(retorno1.length()>0) {
	 	sb.append(desc1 +" x " +desc2 +"\n");
	 	sb.append(retorno1+"\n");
		}
	 	
	 /*	String retorno2 = comparaExistencia(p2,p1);
	 	if(retorno2.length()>0) {
	 		sb.append(desc2 +" x " +desc1 +"\n");
		 	sb.append(retorno2+"\n");
		}*/
	
	 	
	 	
	 	
//	 System.out.println("\ntem nos dois mas se trata de outro tipo de conta");
	 String retorno3 = dif(p1,p2,sigla,data,cvm, desc1, desc2);
	 if(retorno3.length()>0) {
		 	sb.append("\ntem nos dois mas se trata de outro tipo de conta\n");
		 	sb.append(retorno3+"\n");
		}
	 return sb.toString();
}
static List<ContaContabil> cc1;
static Empresa e1;
static EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
static Divergencia divergencia;
public RelatorioDiferenca rdif = new RelatorioDiferenca();
public ExtratorDeDiferencasToObject(String cvmbd, String cvmProp, String databd, String dataProp,String perbd, String perProp ){

	
	String cvm1=cvmbd;
	String cvm2=cvmProp ; //"5258";
	
		e1 = em.find(Empresa.class, Integer.parseInt(cvm1));
		Empresa e2 =null;
		cc1=null;
		List<ContaContabil> cc2=null;
		
		String dp1=databd;
		String dp2=dataProp;
		String parte1 = dp1.substring(0, 2);
    	String parte2 = dp1.substring(2,6);
    	Integer dp1db = Integer.parseInt(parte2+parte1);
    	
    	 parte1 = dp2.substring(0, 2);
    	 parte2 = dp2.substring(2,6);
    	Integer dp2db = Integer.parseInt(parte2+parte1);
    	
   	 Query query = em.createQuery("SELECT e FROM ContaContabil e where e.empresa.cvm=\'"+cvm1+"\' and e.data="+dp1db);
	   cc1 = (List<ContaContabil>) query.getResultList();
	   /*Query query2 = em.createQuery("SELECT e FROM ContaContabil e where e.empresa.cvm=\'"+cvm2+"\' and e.data="+dp2db);
	   cc2 = (List<ContaContabil>) query2.getResultList();*/
		if(cvm1.equals(cvm2)) {
		
			 e2 = e1;
			 
		}else
		{
			e2 = em.find(Empresa.class, Integer.parseInt(cvm2));

				
		}
		
		
		
	
	String per1=perbd;
	String per2=perProp;
	String desc1= cvm1 + "_" + dp1+ "_"+ per1;
	String desc2= cvm2 + "_" + dp2+ "_"+ per2;
	 PeriodoToProperties ptp = new PeriodoToProperties(e1,cc1);
	 PeriodoToProperties ptp2 = new PeriodoToProperties(dp2,cvm2,per2);
	 
	 Properties dr1 = ptp.getEdp().getDr();
	 Properties dr2=ptp2.getEdp().getDr();

	 System.out.println("Resultado Abrangente");
	  dr1 = ptp.getEdp().getDra();
	  dr2=ptp2.getEdp().getDra();
	  divergencia = new Divergencia();
	 	
	  	String retorno1 =  compara(dr1,dr2,"DRA",dp2db,cvm2, "DRA "+ desc1 , desc2);
	  	rdif.setDra(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  
	  
	  System.out.println("Resultados");
	  dr1 = ptp.getEdp().getDr();
	  dr2=ptp2.getEdp().getDr();
	  
	  //compara(dr1,dr2,"DR "+ desc1 ,desc2);
	  divergencia = new Divergencia();
		retorno1 =  compara(dr1,dr2,"DR",dp2db,cvm2,"DR "+ desc1 , desc2);
		rdif.setDr(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  System.out.println("Ativo");
	  dr1 = ptp.getEdp().getBpa();
	  dr2=ptp2.getEdp().getBpa();
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr1,dr2,"BPA",dp2db,cvm2,"BPA "+ desc1 , desc2);
	  rdif.setBpa(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	 	
	  System.out.println("Passivo");
	  dr1 = ptp.getEdp().getBpp();
	  dr2=ptp2.getEdp().getBpp();
	  

	  divergencia = new Divergencia();
	  retorno1 =  compara(dr1,dr2,"BPP",dp2db,cvm2,"BPP "+ desc1 , desc2);
	  rdif.setBpp(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  System.out.println("Fluxo de caixa");
	  dr1 = ptp.getEdp().getFc();
	  dr2=ptp2.getEdp().getFc();
	  
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr1,dr2,"FC",dp2db,cvm2,"FC "+ desc1 , desc2);
	  rdif.setFc(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	 	
	 	
	  System.out.println("Valor adicionado");
	  dr1 = ptp.getEdp().getDva();
	  dr2=ptp2.getEdp().getDva();
	  
	  
	  
	  divergencia = new Divergencia();
	  retorno1 =  compara(dr1,dr2,"DVA",dp2db,cvm2,"DVA "+ desc1 , desc2);
	  rdif.setDva(divergencia);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	 //	em.close();
}
public static void main(String []args) {
	new ExtratorDeDiferencasToObject("5258","5258","122011","122012","A", "A");
	// dif(dr2,dr1);
}
}
