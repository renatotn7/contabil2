package br.com.readsitecvm.ingestao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IngestaoEspecifico {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try  {
				System.setProperty("webdriver.chrome.driver",
						"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
				// TODO Auto-generated method stub
				WebDriver driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				// C:\Users\renat\Downloads\chromedriver_win32
				//String cvm="20257";]
			
		            String cvm="9342";
		            new IngestaoDemonstrativos(cvm,"T",driver);
	            
	            
	            driver.quit();
	          //  System.out.println(prop.getProperty("3.09")); //lucro liquido
	          
	        } catch (IOException ex) {
	            ex.printStackTrace();	
	        }
	}

}
