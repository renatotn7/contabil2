package br.com.readsitecvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class java2 {
	public static Double getDouble(String value) {
		
		String valor = value.split(";")[1];
		valor = valor.replaceAll("\\.", "");
		return Double.parseDouble(valor);
	}
	public static void main(String []args) {
		Double lucroliquido=0.0;
		Double ebit = 0.0;
		Double ebitda = 0.0;
		Double receita = 0.0;
		Double dividac=0.0;
	    Double dividanc=0.0;
	   EntidadeCVM ecvm= new EntidadeCVM("005258DFP311220180500080909-78");
	    String prefixo = ecvm.codigoCVM + "_" + ecvm.dataDocto + "_" + ecvm.codigoDocumento + "_";
		 try (InputStream input = new FileInputStream(prefixo+"DemonstracaoResultado.xml")) {
			 
			   Properties prop = new Properties();

	            // load a properties file
	            prop.loadFromXML(input);

	            // get the property value and print it out
	            System.out.println(prop.getProperty("3.09")); //lucro liquido
	            System.out.println(prop.getProperty("3.05"));//ebit
	            System.out.println(prop.getProperty("3.01")); // Receita bruta
	            System.out.println(prop.getProperty("3.02.04")); //depreciação nas vendas
	            System.out.println(prop.getProperty("3.04.02.04")); //depreciação relacionadas a operação
	            System.out.println(prop.getProperty("3.06")); //resultado financeiro
	            //Double lucroliquido = getDouble(prop.getProperty("3.09"));
	           
	             lucroliquido = getDouble(prop.getProperty("3.09"));
	            ebit = getDouble(prop.getProperty("3.01"));
	            receita = getDouble(prop.getProperty("3.05"));
	            Double dep1 = getDouble(prop.getProperty("3.02.04"));
	            Double dep2 = getDouble(prop.getProperty("3.04.02.04"));
	            ebitda = ebit+dep1+dep2;
	            System.out.println("ebitda: "+ ebitda);
	        } catch (IOException ex) {
	            ex.printStackTrace();	
	        }
		 Double patrimonio=0.0;
		 try (InputStream input = new FileInputStream(prefixo+"BalancoPatrimonialPassivo.xml")) {
			 
			   Properties prop = new Properties();

	            // load a properties file
	            prop.loadFromXML(input);

	            // get the property value and print it out
	            System.out.println(prop.getProperty("2.03")); //patrimonio
	            System.out.println(prop.getProperty("2.01.04")); //circulante
	            System.out.println(prop.getProperty("2.02.01")); //não circulante
	            patrimonio = getDouble(prop.getProperty("2.03"));
	            dividac = getDouble(prop.getProperty("2.01.04"));
	            dividanc = getDouble(prop.getProperty("2.02.01"));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		 Double margem = lucroliquido /receita;
		 Double ROE = lucroliquido/patrimonio;
		 System.out.println("margem liquida"+ margem);
		 System.out.println("ROE"+ ROE);
		 System.out.println("divida"+ (dividac+dividanc));
		 System.out.println("divida/ebitda"+ (dividac+dividanc)/ebitda);
		 System.out.println("dividac/ebitda"+ (dividac)/ebitda); //>1 o ebitda não paga
		 
	}
}
