package br.com.cvm.bd.origemSites;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.Empresa;

public class ReaderToDB_B3vsCodigoCVM {

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
	    
	    InputStream input = new FileInputStream("setor_codigob3");

		Properties propsetor = new Properties();
		propsetor.loadFromXML(input);
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
	    EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
	    for(Object key: prop.keySet()) {
	    	try {
		    	String skey= (String) key;
		    	System.out.println(skey+ "   "  + prop.getProperty(skey));
		    	String value = prop.getProperty(skey);
	
			    
		    	 Empresa empresa = new Empresa();
				 empresa.setCvm(Integer.parseInt(value.split(";")[0].trim()));
				 empresa.setNomePregao(skey.trim());
				 empresa.setRazaoSocial(value.split(";")[1]);
				 if(value.split(";").length>2) {
				 empresa.setSegmento(value.split(";")[2]);
				 }
				 String valorsetor = propsetor.getProperty(skey.trim());
				 if(valorsetor!=null) {
				 empresa.setSetor(valorsetor.split("_")[0].trim());
				 empresa.setSubsetor(valorsetor.split("_")[1].trim());
				 empresa.setSegmentosetorial(valorsetor.split("_")[2].trim());
				 }
				 em.getTransaction()
			        .begin();
				    em.persist(empresa);
				    em.getTransaction()
				        .commit();
	    	}catch(Exception e) {
	    		
	    	}
			    
	    }
	    em.close();
	    PersistenceManager.INSTANCE.close();
	  //  prop.storeToXML(os, "");
	}
	
	
	private static class 
	javaB3CVMTable {

		public Properties resultado;
		
	public Properties getValue(WebElement table_element) {
		String valorobj="";
		  List<WebElement> tr_collection=table_element.findElements(By.xpath("id('ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01')/tbody/tr"));
		  Properties prop = new Properties();
		  
	    //  System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	      int row_num,col_num;
	      row_num=1;
	      boolean inline=false;
	      for(WebElement trElement : tr_collection)
	      {
	          List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
	    //      System.out.println("NUMBER OF COLUMNS="+td_collection.size());
	          col_num=1;
	          inline=false;
	          for(int i = 0;i< td_collection.size();i++)
	          {
	          	WebElement tdElement=td_collection.get(i);
	          	//tdElement.getText().trim().contentEquals(key) && 
	          	if(inline ==false) {
	          		inline=true;
	          		
	          		//valorobj=	td_collection.get(1).getText().trim()+ td_collection.get(2).getText().trim();
	          		prop.put(td_collection.get(1).getText().trim(), td_collection.get(1).findElement(By.xpath("a")).getAttribute("href").split("codigoCvm=")[1]+";"+td_collection.get(0).getText().trim()+";"+td_collection.get(2).getText().trim());
	          	
	          	}
	            //  System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	              col_num++;
	          }
	          row_num++;
	      } 
		        //System.out.println(driver.getPageSource());
	      resultado = prop;
	    return prop;
	}
	WebElement table_element;


	public	javaB3CVMTable(WebDriver driver,String url,String filename) throws IOException {
			String valorobj="";
				
			driver.get(url);
			
			WebElement table_element = driver.findElement(By.id("ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01"));
	        this.table_element=table_element;
	       Properties p =   getValue(table_element);
	    //   OutputStream 	os = new FileOutputStream(filename);
	      // p.storeToXML(os, "teste1");
	       
	       
		}

	}
}
