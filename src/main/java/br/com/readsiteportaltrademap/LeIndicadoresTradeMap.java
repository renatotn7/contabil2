package br.com.readsiteportaltrademap;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.IndicadorTrademap;
import br.com.readsitecvm.entidades.EntidadeCVM;
import br.com.readsitecvm.utils.ConsultaManualDemonstrativoCVM;

public class LeIndicadoresTradeMap {

	public static void main(String[] args) throws IOException {
		// TODO Stub de método gerado automaticamente
			(new LeIndicadoresTradeMap()).consultaCvm(186);
	}
	
	public void login(WebDriver driver) {

		//	String json= "{\"username\":\"05626933786\",\"password\":\"Abc,1234\",\"id_contract\":\"2020\",\"channel\":\"13\",\"info\":{\"os_version\":\"\",\"version\":\"\",\"os_name\":\"\"}}";
			String json= "{username:'05626933786',password:'Abc,1234',id_contract:'2020',channel:'13',info:{os_version:'',version:'',os_name:''}}";
			String url="http://portal.trademap.com.br/api/portal/v1/authentication/login";
			System.out.println(postJson(driver,json,url));
		
		
	}
	Integer[] datas = {20030331,20030630,20030930,20031231 ,20040331,20040630,20040930,20041231 ,20050331,20050630,20050930,20051231 ,20060331,20060630,20060930,20061231 ,20070331,20070630,20070930,20071231 ,20080331,20080630,20080930,20081231 ,  20090331,20090630,20090930,20091231,20100331,20100630,20100930,20101231 ,20110331,20110630,20110930,20111231 ,20120331,20120630,20120930,20121231 ,20130331,20130630,20130930,20131231 ,20140331,20140630,20140930,20141231 ,20150331,20150630,20150930,20151231 ,20160331,20160630,20160930,20161231 ,20170331,20170630,20170930,20171231 ,20180331,20180630,20180930,20181231 ,20190331,20190630,20190930,20191231};
	public String getIndicadores(WebDriver driver,Integer data, Integer id,Integer companytype) {

		//	String json= "{\"username\":\"05626933786\",\"password\":\"Abc,1234\",\"id_contract\":\"2020\",\"channel\":\"13\",\"info\":{\"os_version\":\"\",\"version\":\"\",\"os_name\":\"\"}}";
		String json= "{id_cvm_company:"+id+",dt_entry:["+data+"],id_company_type:"+companytype+"}";
		String url="http://portal.trademap.com.br/api/trademap/v2/fundamental/company-indices";
			System.out.println(postJson(driver,json,url));
		
			return postJson(driver,json,url) ;
		
	}
	
	public String postJson(WebDriver driver , String json, String url) {
		
		//	String json= "{\"username\":\"05626933786\",\"password\":\"Abc,1234\",\"id_contract\":\"2020\",\"channel\":\"13\",\"info\":{\"os_version\":\"\",\"version\":\"\",\"os_name\":\"\"}}";
		//	String json= "{username:'05626933786',password:'Abc,1234',id_contract:'2020',channel:'13',info:{os_version:'',version:'',os_name:''}}";
			//String url="https://portal.trademap.com.br/api/trademap/v2/fundamental/company-indices";
		
			String js = "var xhr = new XMLHttpRequest(); "
					+ "	xhr.open('POST', '"+url+"',false);  "
					+ "xhr.setRequestHeader('content-type', 'application/json');  "
					+ "xhr.setRequestHeader('accept-language', 'pt-BR,pt;q=0.9,en;q=0.8'); "
					+ "xhr.send(JSON.stringify("+json+")); "
							+ "return xhr.response;";
			if (driver instanceof JavascriptExecutor) {
				Object response =  ((JavascriptExecutor) driver).executeScript(	js);
			    return(String) response;
			}
			return null;
	}
	int entityCount = 50;
	int batchSize = 25;
    

	public void consultaCvm(int idDemonstrativo) throws IOException {
		System.setProperty("webdriver.chrome.driver",
				"D:\\Downloads_old\\chromedriver_win32\\chromedriver.exe");
		// TODO Auto-generated method stub
		 
		
		  DesiredCapabilities capabilities = new DesiredCapabilities();
		   ChromeOptions options = new ChromeOptions();
       
          options.addArguments("--test-type");
          options.addArguments("--start-maximized");
          options.addArguments("--disable-web-security");
          options.addArguments("--user-data-dir=D:\\chrome");
          options.addArguments("--allow-file-access-from-files");
          options.addArguments("--allow-running-insecure-content");
          options.addArguments("--allow-cross-origin-auth-prompt");
          options.addArguments("--allow-file-access");
          options.addArguments("--headless");
          
          capabilities.setCapability(ChromeOptions.CAPABILITY, options);
          
          ChromeDriver driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	//	EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		//;
	
		
		login(driver);
		
		
		//this.getIndicadores(driver);
	///	{id_cvm_company:5489,dt_entry:[20050331,20050630,20050930,20051231 ,20060331,20060630,20060930,20061231 ,20070331,20070630,20070930,20071231 ,20080331,20080630,20080930,20081231 ,  20090331,20090630,20090930,20091231,20100331,20100630,20100930,20101231 ,20110331,20110630,20110930,20111231 ,20120331,20120630,20120930,20121231 ,20130331,20130630,20130930,20131231 ,20140331,20140630,20140930,20141231 ,20150331,20150630,20150930,20151231 ,20160331,20160630,20160930,20161231 ,20170331,20170630,20170930,20171231 ,20180331,20180630,20180930,20181231 ,20190331,20190630,20190930,20191231],id_company_type:2}
		EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		;
		EntityTransaction entityTransaction = em
				   .getTransaction();

				TypedQuery<Empresa> queryanalisar = em.createQuery("Select distinct c from Empresa c where c.idCvmTrademap is not  null  order by c.nota desc	",Empresa.class);

				List<Empresa> empresas = queryanalisar.getResultList();
				
				for(Empresa emp:empresas) {
					login(driver);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e3) {
					// TODO Bloco catch gerado automaticamente
					e3.printStackTrace();
				}
							//	em.getTransaction().begin();
							//	emp.setFundJson(emp.getFundJson().replaceAll("\"\\_\\_typename\"\\:\"BrazilianCompany", "\"__typename\":\""));
							//	em.getTransaction().commit();
				if(emp.getCvm()==1228) {
					continue;
				}
				Integer indictrademap =null;
					TypedQuery<Integer> queryanalisar2 = em.createQuery("Select max(c.data) from IndicadorTrademap c where c.cvm = 	" + emp.getCvm() ,Integer.class);
					
					try {
					 indictrademap = queryanalisar2.getSingleResult();
				
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			for(Integer data:datas) {
				try {
			
				if(data<indictrademap || indictrademap==20190930) {
					//continue;
				}
				}catch(Exception e) {
					
				}
				
				
			try {
				em.getTransaction().begin();
				String jsonindics=getIndicadores(driver,data,emp.getIdCvmTrademap(),2);
				String parte1=jsonindics.split("\\{")[3];
				String finals=parte1.split("\\}")[0];
				if(finals.length()==0) {
					 jsonindics=getIndicadores(driver,data,emp.getIdCvmTrademap(),1);
						 parte1=jsonindics.split("\\{")[3];
						finals=parte1.split("\\}")[0];
						if(finals.length()==0) {
							em.getTransaction().rollback();
							
						
							continue;
						}
				}
				System.out.println(finals.replaceAll("\"", ""));
				String[] pares = finals.replaceAll("\"", "").split("\\,");
				
			
				for(int i = 0 ; i< pares.length;i++) {
					if(pares[i].length()==0) {
						continue;
					}
					String par=pares[i];
				 
					if(!par.contains("_annual") && !par.contains("_five") && !par.contains("_three")&& !par.contains("id_cvm_company")&& !par.contains("id_full_info")&& !par.contains("dh_insert")&& !par.contains("id_document_type"))
					{
						IndicadorTrademap it = new IndicadorTrademap();
						it.setIndicador(par.split("\\:")[0]);
						it.setValor(par.split("\\:")[1]);
						it.setCvm(emp.getCvm());
						it.setData(data);
							//em.getTransaction().begin();
						//try {
							//if(indictrademap !=null && data==indictrademap) {
					//		entityTransaction.begin();
						//	}
							em.persist(it);
						//	if(indictrademap !=null && data==indictrademap) {
						//		entityTransaction.commit();
						//		}
							//entityTransaction.commit();
						//	}catch(Exception e) {
								//entityTransaction.commit();
						//		entityTransaction.rollback();
							//	  em.clear();
						//	}
							//em.getTransaction().commit();
					}
					
				}
				//if(indictrademap ==null || data>indictrademap) {
					entityTransaction.commit();
				//}
		
			    em.clear();
			}catch(Exception e) {
				//entityTransaction.commit();
				entityTransaction.rollback();
			
			    em.clear();
			}
			}
		
				/*				
			try {
				// 4376
				String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"3',id_exchange:1}";
				String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
				System.out.println(postJson(driver, json1, url1));

				JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
				JsonObject jo = tree.getAsJsonObject();
				JsonElement jeresult = jo.get("result");
				JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
				JsonArray array = jedocs.getAsJsonArray();
				JsonElement firstDocument = array.get(0);

				System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
				em.getTransaction().begin();
				emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
				em.getTransaction().commit();
			} catch (Exception e) {

				try {
					// 4376
					String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"4',id_exchange:1}";
					String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
					System.out.println(postJson(driver, json1, url1));

					JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
					JsonObject jo = tree.getAsJsonObject();
					JsonElement jeresult = jo.get("result");
					JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
					JsonArray array = jedocs.getAsJsonArray();
					JsonElement firstDocument = array.get(0);

					System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
					em.getTransaction().begin();
					emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
					em.getTransaction().commit();
				} catch (Exception e1) {

					try {
						// 4376
						String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"11',id_exchange:1}";
						String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
						System.out.println(postJson(driver, json1, url1));

						JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
						JsonObject jo = tree.getAsJsonObject();
						JsonElement jeresult = jo.get("result");
						JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
						JsonArray array = jedocs.getAsJsonArray();
						JsonElement firstDocument = array.get(0);

						System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
						em.getTransaction().begin();
						emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
						em.getTransaction().commit();
					} catch (Exception e2) {
						try {
							// 4376
							String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"5',id_exchange:1}";
							String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
							System.out.println(postJson(driver, json1, url1));

							JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
							JsonObject jo = tree.getAsJsonObject();
							JsonElement jeresult = jo.get("result");
							JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
							JsonArray array = jedocs.getAsJsonArray();
							JsonElement firstDocument = array.get(0);

							System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
							em.getTransaction().begin();
							emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
							em.getTransaction().commit();
						} catch (Exception e3) {
							try {
								// 4376
								String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"6',id_exchange:1}";
								String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
								System.out.println(postJson(driver, json1, url1));

								JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
								JsonObject jo = tree.getAsJsonObject();
								JsonElement jeresult = jo.get("result");
								JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
								JsonArray array = jedocs.getAsJsonArray();
								JsonElement firstDocument = array.get(0);

								System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
								em.getTransaction().begin();
								emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
								em.getTransaction().commit();
							} catch (Exception e4) {
								try {
									// 4376
									String json1 = "{period:'oneyear',cd_stock:'"+emp.getRaizAtivo()+"7',id_exchange:1}";
									String url1 = "https://portal.trademap.com.br/api/trademap/v2/fundamental/list-provents";
									System.out.println(postJson(driver, json1, url1));

									JsonElement tree = JsonParser.parseString(postJson(driver, json1, url1));
									JsonObject jo = tree.getAsJsonObject();
									JsonElement jeresult = jo.get("result");
									JsonElement jedocs = jeresult.getAsJsonObject().get("documentos");
									JsonArray array = jedocs.getAsJsonArray();
									JsonElement firstDocument = array.get(0);

									System.out.println(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString());
									em.getTransaction().begin();
									emp.setIdCvmTrademap(Integer.parseInt(firstDocument.getAsJsonObject().get("id_cvm_company").getAsString()));
									em.getTransaction().commit();
								} catch (Exception e5) {

								}
							}
						}
					}
				}
			}
        
        //	String json= "{\"username\":\"05626933786\",\"password\":\"Abc,1234\",\"id_contract\":\"2020\",\"channel\":\"13\",\"info\":{\"os_version\":\"\",\"version\":\"\",\"os_name\":\"\"}}";
	//	String json= "{id_contract:'2020',id_asset:'97',id_portfolio:819794,dt_start:20191113,dt_end:20200113}";
	//	String url="https://portal.trademap.com.br/api/portal/v1/portfolio/get-indexes-info";
	//	System.out.println(postJson(driver,json,url));
		*/
				}
				em.close();
				driver.close();
				System.exit(1);
		
		
		/*
		 * 
        String json_string = "{\"firstName\":\"Tom\", \"lastName\": \"Broody\"}";

        Gson gson = new Gson();
        User user = gson.fromJson(json_string, User.class);
		 */
		/*		Query queryanalisar = em.createQuery("Select c from Demonstrativo c where c.idDemonstrativo ="+idDemonstrativo);

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
		*/
	}
}
