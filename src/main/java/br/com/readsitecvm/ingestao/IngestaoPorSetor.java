package br.com.readsitecvm.ingestao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IngestaoPorSetor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try  {
				System.setProperty("webdriver.chrome.driver",
						"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
				// TODO Auto-generated method stub
				WebDriver driver = new ChromeDriver();
				// C:\Users\renat\Downloads\chromedriver_win32
				//String cvm="20257";]
			 InputStream input = new FileInputStream("setor_codigob3");
			   Properties prop = new Properties();
	            prop.loadFromXML(input);
	            
	            InputStream input2 = new FileInputStream("b3cvm");
				   Properties prop2 = new Properties();

		            // load a properties file
		            prop2.loadFromXML(input2);
	            
	            
	            
	            for(Object key:prop.keySet()) {
	            	
	            	String skey = (String) key;
	            	String value1 = (String)prop.get(skey);
	            	if(value1.equals("Saude_Comercio e Distribuicao_Medicamentos e Outros Produtos")&&prop2.get(skey)!=null) {
	            		System.out.println(skey);
	            		System.out.println(prop2.get(skey));
	            		new IngestaoDemonstrativos((String)prop2.get(skey),"A",driver);
	            	}
	            //	System.out.println(prop.get(skey));
	            //	System.out.println(prop2.get(value1));
	            }
	            driver.quit();
	          //  System.out.println(prop.getProperty("3.09")); //lucro liquido
	          
	        } catch (IOException ex) {
	            ex.printStackTrace();	
	        }
	}

}
