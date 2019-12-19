package br.com.readsitecvm.ingestao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.cvm.bd.consolidacaoBD.PersisteContasUnificando;
import br.com.readsitecvm.entidades.EntidadeCVM;
import br.com.readsitecvm.ingestao.subsistema.javaCVMTable;

public class IngestaoDemonstrativos {
	public IngestaoDemonstrativos(String cvm, String tipo,WebDriver driver) throws IOException {
		// depois posso ver dados cadastrais: Formul�rios Cadastrais (FCA)
				 Properties p =  new Properties();
			       
			       
			
			
				driver.get("http://siteempresas.bovespa.com.br/consbov/ExibeTodosDocumentosCVM.asp?CNPJ=&CCVM="+cvm+"&TipoDoc=C&QtLinks=1000");
				//driver.manage().window().maximize();
				LevantamentoUltimaVersaoProtocolo pu = new LevantamentoUltimaVersaoProtocolo();
				List<String> codigos = pu.getListCodigos( driver,tipo);
				boolean sumary_exists=false;
				String docto = "";
				if(tipo.equals("A")) {
					docto="DFP";
				}else {
					docto="ITR";
				}
				
				
				File f1 = new File(cvm+"/"+cvm+"_"+tipo);
				Properties pcheckCodigo=new Properties();
				if(f1.exists()) {
					sumary_exists=true;
				  InputStream input = new FileInputStream(cvm+"/"+cvm+"_"+tipo);

						Properties prop = new Properties();
						prop.loadFromXML(input);
						for(Object key:prop.keySet()) {
							String skey=(String) key;
							pcheckCodigo.put(prop.get(skey), skey);
						}
						
						
						
				}
				
				
					
					
				String codcvm ="";
				
			
					int ninfo=2;
				for (int i=0;i<codigos.size();i++) {
				
					String cod = codigos.get(i);
					EntidadeCVM ecvm = new EntidadeCVM(cod);
					 codcvm = ecvm.codigoCVM;
			
					if(pcheckCodigo.get(cod)!=null) {
						p.put(ecvm.dataDocto, cod);
						continue;
					}
					System.out.println("novo codigo" + cod+" "+((double)((double)i/(double)codigos.size())*100)+"%");
					
					String codDocumento = ecvm.codigoDocumento;
					driver.get(
							"https://www.rad.cvm.gov.br/enetconsulta/frmGerenciaPaginaFRE.aspx?CodigoTipoInstituicao=1&NumeroSequencialDocumento="
									+ codDocumento);

					ArrayList<String> valores = new ArrayList<String>();
					valores.add("1.01");
					valores.add("1.02");
					//String prefixo = ecvm.codigoCVM + "_" + ecvm.dataDocto + "_" + ecvm.codigoDocumento + "_";
				      
					String prefixo = cvm + "/" + ecvm.dataDocto  ;
					   File f = new File(prefixo);
					   try {
					   f.mkdirs();
					   }catch(Exception e) {
						   
					   }
					p.put(ecvm.dataDocto, cod);
					
					try {
						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=2&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/BalancoPatrimonialAtivo");
						//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=1&Demonstracao=4&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento=82498&NumeroSequencialRegistroCvm=5258&CodigoTipoInstituicao=
						//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=4&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=ITR&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=3&NumeroSequencialDocumento=82498&NumeroSequencialRegistroCvm=1898&CodigoTipoInstituicao=1	
						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=3&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/BalancoPatrimonialPassivo");

						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=4&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/DemonstracaoResultado");
						try {
						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=5&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/DemonstracaoResultadoAbrangente");
						}catch(Exception e) {}
						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=99&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/FluxoDeCaixa");
						new javaCVMTable(driver,
								"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao="+ninfo+"&Demonstracao=9&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento="+docto+"&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
										+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
								prefixo + "/DemValorAdicionado");
						}
						catch(Exception e) {
							
						}
					try {
					  InputStream input = new FileInputStream(prefixo + "/BalancoPatrimonialAtivo");

						Properties prop = new Properties();
						prop.loadFromXML(input);
						Double d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}
						//algo
						 input = new FileInputStream(prefixo + "/BalancoPatrimonialPassivo");

						 prop = new Properties();
						prop.loadFromXML(input);
						d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}
						//algo
						 input = new FileInputStream(prefixo + "/DemonstracaoResultado");

						 prop = new Properties();
						prop.loadFromXML(input);
						 d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}
						//algo
						 /*input = new FileInputStream(prefixo + "/DemonstracaoResultadoAbrangente");

						 prop = new Properties();
						prop.loadFromXML(input);
						 d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}*/
						//algo
						 input = new FileInputStream(prefixo + "/FluxoDeCaixa");

						 prop = new Properties();
						prop.loadFromXML(input);
						d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}
						//algo
						 input = new FileInputStream(prefixo + "/DemValorAdicionado");

						 prop = new Properties();
						prop.loadFromXML(input);
						 d= 0.0;
						for(Object key : prop.keySet()) {
							String skey=(String) key;
							String value = (String)prop.get(skey);
						
							try {
							d += Double.parseDouble(value.split(";")[1]);
							}catch(Exception e) {
								
							}
						}
						if(d==0) {
							throw new Exception();
						}
						//algo
						ninfo=2;
					}catch(Exception e) {
						i--;
						ninfo=1;
						continue;
					}
					new PersisteContasUnificando(cvm,ecvm.dataDocto, tipo,cod);
					
					// new
					// javaCVMMutacoes(driver,"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=200&Demonstracao=8&Periodo=1&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1","MutacoesNoPatrimonio.xml");

					// so faltou patrimonio que � diferente
					
					// js.executeScript("javascript:fVisualizaArquivo_ENET('82904','CONSULTA')","");
					// System.out.println(driver.getPageSource());
				}
				OutputStream 	os = new FileOutputStream(cvm+"/"+cvm+"_"+tipo);
				p.storeToXML(os, "teste1");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	public static void main(String[] args) throws IOException {

		// depois posso ver dados cadastrais: Formul�rios Cadastrais (FCA)
		 Properties p =  new Properties();
	       
	       
		System.setProperty("webdriver.chrome.driver",
				"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		// C:\Users\renat\Downloads\chromedriver_win32
		//String cvm="20257";]
		String cvm = "9342";
		
	    String tipo = "A";
		driver.get("http://siteempresas.bovespa.com.br/consbov/ExibeTodosDocumentosCVM.asp?CNPJ=&CCVM="+cvm+"&TipoDoc=C&QtLinks=1000");
		//driver.manage().window().maximize();
		LevantamentoUltimaVersaoProtocolo pu = new LevantamentoUltimaVersaoProtocolo();
		List<String> codigos = pu.getListCodigos( driver,tipo);
		
		for (int i=0;i<codigos.size();i++) {
			String cod = codigos.get(i);
			
			System.out.println("novo codigo" + cod+" "+((int)(i/codigos.size())*100)+"%");
			EntidadeCVM ecvm = new EntidadeCVM(cod);
			String codcvm = ecvm.codigoCVM;

			String codDocumento = ecvm.codigoDocumento;
			driver.get(
					"https://www.rad.cvm.gov.br/enetconsulta/frmGerenciaPaginaFRE.aspx?CodigoTipoInstituicao=1&NumeroSequencialDocumento="
							+ codDocumento);

			ArrayList<String> valores = new ArrayList<String>();
			valores.add("1.01");
			valores.add("1.02");
			
		         
		         // create
		        
			String prefixo = ecvm.codigoCVM + "/" + ecvm.dataDocto  ;
			   File f = new File(prefixo);
			   try {
			   f.mkdirs();
			   }catch(Exception e) {
				   
			   }
			  
			p.put(ecvm.dataDocto, cod);
			
			try {
			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=2&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/BalancoPatrimonialAtivo");
			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=3&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/BalancoPatrimonialPassivo");

			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=4&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/DemonstracaoResultado");
			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=5&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/DemonstracaoResultadoAbrangente");
			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=99&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/FluxoDeCaixa");
			new javaCVMTable(driver,
					"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=9&Periodo=0&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=&CodTipoDocumento=4&NumeroSequencialDocumento="
							+ codDocumento + "&NumeroSequencialRegistroCvm=" + codcvm + "&CodigoTipoInstituicao=",
					prefixo + "/DemValorAdicionado");
			}
			catch(Exception e) {
				
			}
			// new
			// javaCVMMutacoes(driver,"https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=200&Demonstracao=8&Periodo=1&Grupo=&Quadro=&NomeTipoDocumento=DFP&Empresa=&DataReferencia=&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1","MutacoesNoPatrimonio.xml");

			// so faltou patrimonio que � diferente
			
			// js.executeScript("javascript:fVisualizaArquivo_ENET('82904','CONSULTA')","");
			// System.out.println(driver.getPageSource());
		}
		OutputStream 	os = new FileOutputStream(cvm+"/"+tipo);
		p.storeToXML(os, "teste1");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}

}

//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=2&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Balan%c3%a7o+Patrimonial+Ativo&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=3&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Balan%c3%a7o+Patrimonial+Passivo&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=4&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=5&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado+Abrangente&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=99&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Demonstra%c3%a7%c3%a3o+do+Fluxo+de+Caixa&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=200&Demonstracao=8&Periodo=1|2|3&Grupo=DFs+Consolidadas&Quadro=Demonstra%c3%a7%c3%a3o+das+Muta%c3%a7%c3%b5es+do+Patrim%c3%b4nio+L%c3%adquido&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
//https://www.rad.cvm.gov.br/enetconsulta/frmDemonstracaoFinanceiraITR.aspx?Informacao=2&Demonstracao=9&Periodo=0&Grupo=DFs+Consolidadas&Quadro=Demonstra%c3%a7%c3%a3o+de+Valor+Adicionado&NomeTipoDocumento=DFP&Empresa=BANESTES%20SA%20BANCO%20DO%20ESTADO%20DO%20ESPIRITO%20SANTO&DataReferencia=2018-12-31&Versao=5&CodTipoDocumento=4&NumeroSequencialDocumento=82904&NumeroSequencialRegistroCvm=1749&CodigoTipoInstituicao=1
/*
 * <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=2&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Balan%c3%a7o+Patrimonial+Ativo&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Balan&#231;o Patrimonial Ativo</option> <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=3&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Balan%c3%a7o+Patrimonial+Passivo&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Balan&#231;o Patrimonial Passivo</option> <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=4&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Demonstra&#231;&#227;o do Resultado</option> <option selected="selected"
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=5&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado+Abrangente&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Demonstra&#231;&#227;o do Resultado Abrangente</option> <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=99&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Fluxo+de+Caixa&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Demonstra&#231;&#227;o do Fluxo de Caixa</option> <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=200&amp;Demonstracao=8&amp;Periodo=1|2|3&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+das+Muta%c3%a7%c3%b5es+do+Patrim%c3%b4nio+L%c3%adquido&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Demonstra&#231;&#227;o das Muta&#231;&#245;es do Patrim&#244;nio
 * L&#237;quido</option> <option
 * value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=9&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+de+Valor+Adicionado&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5"
 * >Demonstra&#231;&#227;o de Valor Adicionado</option>
 * 
 */