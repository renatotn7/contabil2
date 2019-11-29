package br.com.cvm.leitor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.cvm.utils.JaroWinklerStrategy;

public class ExtratorDeDiferencas {
public static Double getDouble(String value) {

String valor = value.split(";")[1];
valor = valor.replaceAll("\\.", "");
return Double.parseDouble(valor);
}
static JaroWinklerStrategy jw = new JaroWinklerStrategy(0.05);
public static String possivelCorrespondente(Properties p, String key2, String value2) {
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
			 }
		 }
	 }
	if(encontrou) sb.append("\n");	
	return sb.toString();
	
}
public static String comparaExistencia(Properties p1, Properties p2) {
	int count = 0;
	StringBuilder sb = new StringBuilder(); 
	 for(Object key :p1.keySet()) {
		 String key1 = (String)key;
		if( p2.getProperty(key1.trim())==null) {
			count++;
			sb.append("\t"+count+"- "+key1 + " "+ p1.getProperty(key1)+"\n");
			String valuep1 =(String) p1.getProperty(key1);
			sb.append(possivelCorrespondente(p2,key1.trim(),valuep1.split(";")[0]));
		}
	 }
	 return sb.toString();
}
public static String dif(Properties p1, Properties p2, String desc1, String desc2) {
	int count = 0;
	StringBuilder sb = new StringBuilder(); 
	for(Object key :p1.keySet()) {
		 String key1 = (String)key;
		if( p2.getProperty(key1)!=null ) {
			if(!p1.getProperty(key1).split(";")[0].equals(p2.getProperty(key1).split(";")[0])){
				count++;
				sb.append("\t"+count+"- "+desc1 + " " +  key1 + " "+ p1.getProperty(key1).split(";")[0]+"\n");
				
				String valuep1 =(String) p1.getProperty(key1);
				sb.append(possivelCorrespondente(p2,key1.trim(),valuep1.split(";")[0]));
				
				sb.append("\t   "+desc2 + " " +key1 + " "+ p2.getProperty(key1).split(";")[0]+"\n");		
				String valuep2 =(String) p2.getProperty(key1);
				sb.append(possivelCorrespondente(p1,key1.trim(),valuep2.split(";")[0]));
				sb.append("-\n");
				
			}
		
		}
	 }
	return sb.toString();
}
public static String compara(Properties p1, Properties p2, String desc1, String desc2) {

	 StringBuilder sb = new StringBuilder();
	 	String retorno1 = comparaExistencia(p1,p2);
	 	if(retorno1.length()>0) {
	 	sb.append(desc1 +" x " +desc2 +"\n");
	 	sb.append(retorno1+"\n");
		}
	 	
	 	String retorno2 = comparaExistencia(p2,p1);
	 	if(retorno2.length()>0) {
	 		sb.append(desc2 +" x " +desc1 +"\n");
		 	sb.append(retorno2+"\n");
		}
	
	 	
	 	
	 	
//	 System.out.println("\ntem nos dois mas se trata de outro tipo de conta");
	 String retorno3 = dif(p1,p2, desc1, desc2);
	 if(retorno3.length()>0) {
		 	sb.append("\ntem nos dois mas se trata de outro tipo de conta\n");
		 	sb.append(retorno3+"\n");
		}
	 return sb.toString();
}
public static void main(String []args) {
	String cvm1="5258";
	String cvm2="5258";
	String dp1="122012";
	String dp2="122011";
	String per1="A";
	String per2="A";
	String desc1= cvm1 + "_" + dp1+ "_"+ per1;
	String desc2= cvm2 + "_" + dp2+ "_"+ per2;
	 PeriodoToProperties ptp = new PeriodoToProperties(dp1,cvm1,per1);
	 PeriodoToProperties ptp2 = new PeriodoToProperties(dp2,cvm2,per2);
	 
	 Properties dr1 = ptp.getEdp().getDr();
	 Properties dr2=ptp2.getEdp().getDr();

	 System.out.println("Resultado Abrangente");
	  dr1 = ptp.getEdp().getDra();
	  dr2=ptp2.getEdp().getDra();
	 
	 	
	  	String retorno1 =  compara(dr1,dr2,"DRA "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  
	  
	  System.out.println("Resultados");
	  dr1 = ptp.getEdp().getDr();
	  dr2=ptp2.getEdp().getDr();
	  
	  //compara(dr1,dr2,"DR "+ desc1 ,desc2);
	  
		retorno1 =  compara(dr1,dr2,"DR "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  System.out.println("Ativo");
	  dr1 = ptp.getEdp().getBpa();
	  dr2=ptp2.getEdp().getBpa();
	  retorno1 =  compara(dr1,dr2,"BPA "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	 	
	  System.out.println("Passivo");
	  dr1 = ptp.getEdp().getBpp();
	  dr2=ptp2.getEdp().getBpp();
	  

	  
	  retorno1 =  compara(dr1,dr2,"BPP "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	  System.out.println("Fluxo de caixa");
	  dr1 = ptp.getEdp().getFc();
	  dr2=ptp2.getEdp().getFc();
	  
	  retorno1 =  compara(dr1,dr2,"FC "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	 	
	 	
	  System.out.println("Valor adicionado");
	  dr1 = ptp.getEdp().getDva();
	  dr2=ptp2.getEdp().getDva();
	  
	  retorno1 =  compara(dr1,dr2,"DVA "+ desc1 , desc2);
	 	if(retorno1.length()>0) {
	 		System.out.println(retorno1);
	 		
	 	}else {
	 		System.out.println("\tSem diferenças");
	 	}
	  
	// dif(dr2,dr1);
}
}
