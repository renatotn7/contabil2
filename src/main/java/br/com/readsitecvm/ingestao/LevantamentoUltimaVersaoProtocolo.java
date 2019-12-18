package br.com.readsitecvm.ingestao;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.readsitecvm.entidades.EntidadeCVM;

public class LevantamentoUltimaVersaoProtocolo {
	public List<String> getListCodigos(WebDriver driver,String tipo){
		ArrayList<String> codigos = new ArrayList<String>();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		if(tipo.equals("T")) {
			js.executeScript("javascript:fVisualizaDocumentos('C','IDI1')","");
		}
		if(tipo.equals("A")) {
			js.executeScript("javascript:fVisualizaDocumentos('C','IDI2')","");
		}
	       List<WebElement> linkElements = driver.findElements(By.tagName("u"));

	        String[] linkTexts = new String[linkElements.size()];
	        boolean finded=false;
	        String last="";
	        for(int i =0;i<linkTexts.length;i++) {
	        	String codigo = linkElements.get(i).getText();
	        	EntidadeCVM ecvm2 = new EntidadeCVM(codigo);
	        	
	        	if(!ecvm2.dataDocto.equals(last)) {
	        		last = ecvm2.dataDocto;
	        		System.out.println(codigo);
	        		codigos.add(codigo);
	        	}
	        	
	        	
	        }
	        return codigos;
			}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\renat\\Downloads\\chromedriver_win32v2\\chromedriver.exe");
		// TODO Auto-generated method stub
		WebDriver driver=new ChromeDriver();
		 //C:\Users\renat\Downloads\chromedriver_win32
		driver.manage().window().maximize();
		EntidadeCVM ecvm = new EntidadeCVM("020257ITR300920190100089483-70");
		String codcvm=	ecvm.codigoCVM;
		driver.get("http://siteempresas.bovespa.com.br/consbov/ExibeTodosDocumentosCVM.asp?CNPJ=&CCVM="+codcvm+"&TipoDoc=C&QtLinks=100");
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("javascript:fVisualizaDocumentos('C','IDI1')","");
		
	       List<WebElement> linkElements = driver.findElements(By.tagName("u"));

	        String[] linkTexts = new String[linkElements.size()];
	        boolean finded=false;
	        String last="";
	        for(int i =0;i<linkTexts.length;i++) {
	        	String codigo = linkElements.get(i).getText();
	        	EntidadeCVM ecvm2 = new EntidadeCVM(codigo);
	        	
	        	if(!ecvm2.dataDocto.equals(last)) {
	        		last = ecvm2.dataDocto;
	        		System.out.println(codigo);
	        	}
	        	
	        	
	        }
	        
	        driver.quit();
	}

}
