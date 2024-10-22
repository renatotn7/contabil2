package br.com.cvm.leitor.origemBDeProperties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.leitor.entidades.EntidadeCVM;
import br.com.cvm.leitor.entidades.EntidadeDemonstracaoProperties;

public class PeriodoToProperties {
	public EntidadeDemonstracaoProperties getEdp() {
		return edp;
	}

	EntidadeDemonstracaoProperties edp;
	public PeriodoToProperties(String periodo,String cvm,String tipo,String protocolo) {
		   Properties p  = null;
		
		   edp =new EntidadeDemonstracaoProperties();
		   EntidadeCVM ecvm=null;
		 try {
			 if(protocolo ==null) {
		 InputStream inputc = new FileInputStream(cvm+"/"+cvm+"_"+tipo) ;
			 p = new Properties();
		     p.loadFromXML(inputc);
			 p.get(periodo);
		     
			 ecvm= new EntidadeCVM((String) p.get(periodo));
			 
			 }else
			 {
				 ecvm= new EntidadeCVM(protocolo);
			 }
			   String prefixo = cvm + "/" + ecvm.dataDocto ;
			  
			   InputStream input = new FileInputStream(prefixo+"/DemonstracaoResultado");

				Properties prop = new Properties();
				prop.loadFromXML(input);
				edp.setDr(prop);
				
				input = new FileInputStream(prefixo+"/BalancoPatrimonialAtivo");

				prop = new Properties();
				prop.loadFromXML(input);
				edp.setBpa(prop);
				
				input = new FileInputStream(prefixo+"/BalancoPatrimonialPassivo");

				prop = new Properties();
				prop.loadFromXML(input);
				edp.setBpp(prop);
			 try {
				input = new FileInputStream(prefixo+"/DemonstracaoResultadoAbrangente");

				prop = new Properties();
				prop.loadFromXML(input);
				edp.setDra(prop);
			 }catch(Exception e) {
				 edp.setDra(new Properties());
			 }
				input = new FileInputStream(prefixo+"/DemValorAdicionado");

				prop = new Properties();
				prop.loadFromXML(input);
				edp.setDva(prop);
				
				input = new FileInputStream(prefixo+"/FluxoDeCaixa");

				prop = new Properties();
				prop.loadFromXML(input);
				edp.setFc(prop);
			 
			 
			 
			 
		   } catch (FileNotFoundException e) {
			// TODO Bloco catch gerado automaticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloco catch gerado automaticamente
			e.printStackTrace();
		}
		 
	}
	
	
	public PeriodoToProperties(Empresa emp, List<ContaContabil> planoContas) {
		   edp =new EntidadeDemonstracaoProperties();
			Properties pdra=new Properties();
			Properties pbpa=new Properties();
			Properties pbpp=new Properties();
			Properties pfc=new Properties();
			Properties pdr=new Properties();
			Properties pdva=new Properties();
			
			
		   for(ContaContabil c:planoContas) {
		
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DRA")) {
				  
				   pdra.put(c.getContaContabil(), c.getDescricao());
			   }
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "BPA")) {
				   pbpa.put(c.getContaContabil(), c.getDescricao());
			   }
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "BPP")) {
				   pbpp.put(c.getContaContabil(), c.getDescricao());
			   }
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "FC")) {
				   pfc.put(c.getContaContabil(), c.getDescricao());
			   }
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DR")) {
				   pdr.put(c.getContaContabil(), c.getDescricao());
			   }
			   if(c.getTipoDemonstrativo().getSiglaTipo().equals( "DVA")) {
				   pdva.put(c.getContaContabil(), c.getDescricao());
			   }
		   }
		   
		   edp.setDra(pdra);
		   edp.setBpa(pbpa);
		   edp.setBpp(pbpp);
		   edp.setDr(pdr);
		   edp.setFc(pfc);
		   edp.setDva(pdva);
		   
		   
		 
	}
}
