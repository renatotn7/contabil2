package br.com.cvm.leitor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SetorialRefinadoToProperties {
	public static String learquivo(String file) throws IOException {
		// TODO Stub de m�todo gerado automaticamente
				StringBuilder sb = new StringBuilder();
			    String linha = null;  
				
			         // instancia do arquivo que vou ler  
			         FileReader reader = new FileReader(file);  
			         BufferedReader leitor = new BufferedReader(reader);  
			  
			         // loop que percorrer� todas as  linhas do arquivo.txt que eu quero ler  
			         while ((linha = leitor.readLine()) != null) {  
			                      //No metodo StringTokenizer passo os parametros que quero ler, em seguida o que eu quero descartar no meu caso ( - ) e ( ; ).   
			          sb.append(linha+"\n");
			         }  
			  
			         leitor.close();  
			         reader.close();  
			         return sb.toString();
	
	}
	public static void main(String[] args) throws IOException {
		String setorialRefinado = learquivo("SetorialRefinado.csv");
		String[] linhas=setorialRefinado.split("\n");
		Properties p1 = new Properties();
		Properties p2 = new Properties();
		Properties p3 = new Properties();
		
		for(String linha: linhas) {
			if(linha.split(";").length<5) {
				
				continue;
			}
			String value1= linha.split(";")[0].trim()+"_"+linha.split(";")[1].trim()+"_"+linha.split(";")[2].trim();
			String key=linha.split(";")[3].trim();
			p1.put(key, value1);
			String key2= linha.split(";")[3];
			String value2=linha.split(";")[4];
			p2.put(key2, value2);
			String key3= linha.split(";")[3];
			String value3=null;
			try {
			value3 =linha.split(";")[5];
			p3.put(key3, value3);
			}catch(Exception e) {
				int j=0;
				j=j;
				p3.put(key3, "");
			}
			
		}
		File file = new File("setor_codigob3");
		FileOutputStream fileOut = new FileOutputStream(file);
		p1.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		
		file = new File("codigob3_ativo");
		fileOut = new FileOutputStream(file);
		p2.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		
		file = new File("codigob3_segmentomercado");
		 fileOut = new FileOutputStream(file);
		p3.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		
		
		
		
		
		String empresas_vs_pregao = learquivo("Empresas_vs_pregao.csv");
		linhas=empresas_vs_pregao.split("\n");
		 p1 = new Properties();
		for(String linha: linhas) {
			String key= linha.split(";")[1];
			String value1=linha.split(";")[0];
			p1.put(key,value1);
		}
		
		file = new File("codigob3_razaosocial");
		 fileOut = new FileOutputStream(file);
		p1.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		
		
		String infCadastralCiaAberta = learquivo("inf_cadastral_cia_aberta.csv");
		linhas=infCadastralCiaAberta.split("\n");
		 p1 = new Properties();
		 p2 = new Properties();
		for(String linha: linhas) {
			String key= linha.split(";")[1];
			String value1=linha.split(";")[9];
			p1.put(key, value1);
			String value2=linha.split(";")[7];
			p2.put(key, value2);
		}
		
		file = new File("razaosocial_codcvm");
		 fileOut = new FileOutputStream(file);
		p1.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		
		file = new File("razaosocial_situacaob3");
		 fileOut = new FileOutputStream(file);
		p2.storeToXML(fileOut, "Favorite Things");
		fileOut.close();
		int z = 0;
		z=1;
	}

}
