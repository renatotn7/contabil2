package br.com.readsitecvm.ingestao.subsistema;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class javaCVMMutacoes {

	
	
public Properties getValue(WebElement table_element) {
	String valorobj="";
	  List<WebElement> tr_collection=table_element.findElements(By.xpath("id('ctl00_cphPopUp_tbDados')/tbody/tr"));
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
          		prop.put(td_collection.get(0).getText().trim(), td_collection.get(1).getText().trim() +";"+td_collection.get(2).getText().trim()+";"+td_collection.get(3).getText().trim()+";"+td_collection.get(4).getText().trim()+";"+td_collection.get(5).getText().trim()+";"+td_collection.get(6).getText().trim()+";"+td_collection.get(7).getText().trim()+";"+td_collection.get(8).getText().trim()+";"+td_collection.get(9).getText().trim());
          	}
            //  System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
              col_num++;
          }
          row_num++;
      } 
	        //System.out.println(driver.getPageSource());
    return prop;
}
WebElement table_element;


public	javaCVMMutacoes(WebDriver driver,String url,String filename) throws IOException {
		String valorobj="";
			
		driver.get(url);
		
		WebElement table_element = driver.findElement(By.id("ctl00_cphPopUp_tbDados"));
        this.table_element=table_element;
       Properties p =   getValue(table_element);
       OutputStream 	os = new FileOutputStream(filename);
       p.storeToXML(os, "teste1");
       
       
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
<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=2&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Balan%c3%a7o+Patrimonial+Ativo&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Balan&#231;o Patrimonial Ativo</option>
	<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=3&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Balan%c3%a7o+Patrimonial+Passivo&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Balan&#231;o Patrimonial Passivo</option>
	<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=4&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Demonstra&#231;&#227;o do Resultado</option>
	<option selected="selected" value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=5&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Resultado+Abrangente&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Demonstra&#231;&#227;o do Resultado Abrangente</option>
	<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=99&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+do+Fluxo+de+Caixa&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Demonstra&#231;&#227;o do Fluxo de Caixa</option>
	<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=200&amp;Demonstracao=8&amp;Periodo=1|2|3&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+das+Muta%c3%a7%c3%b5es+do+Patrim%c3%b4nio+L%c3%adquido&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Demonstra&#231;&#227;o das Muta&#231;&#245;es do Patrim&#244;nio L&#237;quido</option>
	<option value="frmDemonstracaoFinanceiraITR.aspx?Informacao=2&amp;Demonstracao=9&amp;Periodo=0&amp;Grupo=DFs+Consolidadas&amp;Quadro=Demonstra%c3%a7%c3%a3o+de+Valor+Adicionado&amp;NomeTipoDocumento=DFP&amp;Empresa=BANESTES SA BANCO DO ESTADO DO ESPIRITO SANTO&amp;DataReferencia=2018-12-31&amp;Versao=5">Demonstra&#231;&#227;o de Valor Adicionado</option>

*/