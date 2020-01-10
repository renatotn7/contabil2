package br.com.readsitecvm.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.readsitecvm.entidades.EntidadeCVM;

public class ConsultaManualDemonstrativoCVM {

	public static void main(String[] args) throws IOException {
		// TODO Stub de método gerado automaticamente
			(new ConsultaManualDemonstrativoCVM()).consultaCvm(186);
	}
	public void consultaCvm(int idDemonstrativo) throws IOException {
		System.setProperty("webdriver.chrome.driver",
				"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		;
				Query queryanalisar = em.createQuery("Select c from Demonstrativo c where c.idDemonstrativo ="+idDemonstrativo);

				Demonstrativo dem = (Demonstrativo) queryanalisar.getSingleResult();
		
		String cod = dem.getProtocolo();
		EntidadeCVM ecvm = new EntidadeCVM(cod);
		String codcvm = dem.getEmpresa().getCvm()+"";
		String cvm = codcvm;
		int ninfo=2;
		String docto = "";
		String tipo="";
		if((dem.getData()+"").endsWith("12")) {
			docto="DFP";
			tipo="A";
		}else {
			docto="ITR";
			tipo="T";
		}
		 Properties p =  new Properties();
		 Properties pcheckCodigo=new Properties();
		
				
		String codDocumento = ecvm.codigoDocumento;
		String url= "https://www.rad.cvm.gov.br/enetconsulta/frmGerenciaPaginaFRE.aspx?CodigoTipoInstituicao=1&NumeroSequencialDocumento="
				+ codDocumento;
		driver.get(url);

	}
}
