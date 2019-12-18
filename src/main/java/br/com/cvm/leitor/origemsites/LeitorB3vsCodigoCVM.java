package br.com.cvm.leitor.origemsites;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LeitorB3vsCodigoCVM {

	public static void main(String[] args) throws IOException {
		// TODO Stub de método gerado automaticamente
		// depois posso ver dados cadastrais: Formul�rios Cadastrais (FCA)
		 Properties p =  new Properties();
	       
	       
		System.setProperty("webdriver.chrome.driver",
				"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		// C:\Users\renat\Downloads\chromedriver_win32
		//String cvm="20257";]
		String cvm = "20257";
		
	    String tipo = "A";
		//driver.get("http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra=A&idioma=pt-br");
	    char c = 'A';
	    Properties prop=new Properties();
	    for(int i = 0 ; i <26;i++) {
	    	
	    	try {
	    	javaB3CVMTable jb3=new javaB3CVMTable(driver,"http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="+c+"&idioma=pt-br",c+"b3cvm");
	    	prop.putAll(jb3.resultado);
	    	}catch(Exception e) {}
	    	c++;
	    }
	    for(int i = 0 ; i <10;i++) {
	    	try {
	    		javaB3CVMTable jb3=new javaB3CVMTable(driver,"http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?Letra="+i+"&idioma=pt-br",i+"b3cvm");
	    		prop.putAll(jb3.resultado);
	    	}catch(Exception e) {}
	    	}
	    OutputStream 	os = new FileOutputStream("b3cvm");
	    prop.storeToXML(os, "");
	}

}
