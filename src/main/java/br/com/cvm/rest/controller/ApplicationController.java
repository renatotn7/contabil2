
package br.com.cvm.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.cvm.bd.ComparaContasJaro;
import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;
import br.com.cvm.bd.modelBD.Periodo;
import br.com.cvm.bd.modelBD.TipoDemonstrativo;
import br.com.cvm.bd.modelBD.ValorContabil;
import br.com.cvm.bd.origemProperties.deprecated.PersisteAccounts;
import br.com.cvm.leitor.usoemservicos.ExtratorDeDiferencasToObject;
import br.com.cvm.rest.json.ContaContabilMinInfo;
import entities.ContaCandidata;
import entities.ContaComparada;
import entities.Divergencia;
import entities.RelatorioDiferenca;


@RestController
public class ApplicationController {
	@GetMapping(path="/getContas")
	  public ContaContabil getContas(@RequestParam(value="name", defaultValue="World") String name
			  							) {
		return new ContaContabil();
	 
	  }
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(path="/getempresas/{cvm}")
	
	  public Object getEmpresa(@PathVariable String cvm
				) {
		Object result = null;
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		if(cvm.equals("all")) {
			 Query query = em.createQuery("SELECT e FROM Empresa e");
			   result= (List<Empresa>) query.getResultList();
		}else {
			Empresa e = em.find(Empresa.class, Integer.parseInt(cvm));
			result= e;
		                                       
		}
		if(result ==null) {
			return "Erro";
		}
		return result;
		//e.setContaContabils(null);
			

	}
	/*@GetMapping(path="/getempresa/all")
	
	  public List<Empresa> getEmpresas() {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Empresa e");
		    return (List<Empresa>) query.getResultList();
		

	}*/
	@GetMapping(path="/getsetores/all")
	
	  public List<String> getSetores() {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT distinct e.setor FROM Empresa e");
		    return (List<String>) query.getResultList();

	}
	
	@GetMapping(path="/getsubsetores/{setor}/subsetores/all")
	
	  public List<String> getSubSetores(@PathVariable String setor) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT distinct e.subsetor FROM Empresa e where e.setor=\'"+setor+"\'");
		    return (List<String>) query.getResultList();

	}
	@GetMapping(path="/getsegmentossetoriais/{setor}/subsetores/{subsetor}/all")
	
	  public List<String> getSubSetores(@PathVariable String setor,@PathVariable String subsetor) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT distinct e.segmentosetorial FROM Empresa e where e.setor=\'"+setor+"\' and e.subsetor=\'"+subsetor+"\'");
		    return (List<String>) query.getResultList();

	}
	
	
	@GetMapping(path="/gettipodemonstrativo/all")
	
	  public List<TipoDemonstrativo> getTipoDemonstrativos() {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e");
		    return (List<TipoDemonstrativo>) query.getResultList();

	}
	
	@GetMapping(path="/gettipodemonstrativo/{idtipo}")
	
	  public TipoDemonstrativo getTipoDemonstrativo(@PathVariable String idtipo) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where idTipo="+idtipo);
		    return (TipoDemonstrativo) query.getSingleResult();

	}
	@GetMapping(path="/gettipodemonstrativo/sigla/{sigla}")
	
	  public TipoDemonstrativo getTipoDemonstrativoSigla(@PathVariable String sigla) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM TipoDemonstrativo e where siglaTipo=\'"+sigla +"\'");
		    return (TipoDemonstrativo) query.getSingleResult();

	}
	@GetMapping(path="/getperiodo/all")
	
	  public List<Periodo> getPeriodos() {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Periodo e");
		    return (List<Periodo>) query.getResultList();

	}
	@GetMapping(path="/getperiodo/{idperiodo}")
	
	  public Periodo getPeriodo(@PathVariable String idperiodo) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Periodo e where idPeriodo="+idperiodo);
		    return (Periodo) query.getSingleResult();

	}
	@GetMapping(path="/getperiodo/sigla/{sigla}")
	
	  public Periodo getPeriodoSigla(@PathVariable String sigla) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Periodo e where siglaPeriodo=\'"+sigla+"\'");
		    return (Periodo) query.getSingleResult();

	}
	@GetMapping(path="/getRelatorioDiferencas")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public RelatorioDiferenca getRelDiferenca(@RequestParam(value="cvmbd", defaultValue="5258") String cvmbd,
			  @RequestParam(value="cvmprop", defaultValue="5258") String cvmprop,
			  @RequestParam(value="databd", defaultValue="5258") String databd,
			  @RequestParam(value="dataprop", defaultValue="5258") String dataprop,
			  @RequestParam(value="perbd", defaultValue="5258") String perbd,
			  @RequestParam(value="perprop", defaultValue="5258") String perprop
			  
			  ) {
		ExtratorDeDiferencasToObject edto = new ExtratorDeDiferencasToObject(cvmbd,cvmprop,databd,dataprop,perbd,perprop);
		return edto.rdif;

	}
	
	public ContaComparada escolheMelhorContaComparada(List<ContaComparada > ccomps) {
		ContaComparada ccompescolhida=null;
		double maxsimilaridade=-1;
		if(ccomps ==null) {
			return null;
		}
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT distinct e.idContaContabil FROM ContaContabil e where e.analise=1");
		  List<Integer> contasjainseridas= (List<Integer>) query.getResultList();
		  Properties p =new Properties();
		   for(Integer c:contasjainseridas) {
			   p.put(c+"", "ok");
		   }
		for(ContaComparada ccomp : ccomps) {
			
		if(p.getProperty(""+ccomp.getComparado().getIdContaContabil())!=null) {
			continue;
		}
			
			if(ccomp.getCandidatos()!=null) {
				for(ContaCandidata ccand :ccomp.getCandidatos()) {
					if(ccand.getSimilaridade()>maxsimilaridade) {
						ccompescolhida=ccomp;
						maxsimilaridade=ccand.getSimilaridade();
					}
				}
			}
	
	
		}
		return ccompescolhida;
	}
	
	  
	  
	@CrossOrigin(origins = "*")
	@GetMapping(path="/getMelhorCandidatoDif")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public ContaComparada getMelhorCandDif(@RequestParam(value="cvmbd", defaultValue="5258") String cvmbd,
			  @RequestParam(value="cvmprop", defaultValue="5258") String cvmprop,
			  @RequestParam(value="databd", defaultValue="5258") String databd,
			  @RequestParam(value="dataprop", defaultValue="5258") String dataprop,
			  @RequestParam(value="perbd", defaultValue="5258") String perbd,
			  @RequestParam(value="perprop", defaultValue="5258") String perprop
			  
			  ) {
		
	
		ExtratorDeDiferencasToObject edto = new ExtratorDeDiferencasToObject(cvmbd,cvmprop,databd,dataprop,perbd,perprop);
		ArrayList<ContaComparada> ccompsEscolhidos =  new ArrayList<ContaComparada>();
		ContaComparada ccompescolhida;
		double maxsimilaridade=-1;
		if(edto.rdif.bpa!=null) {
		 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.bpa.getDiferentes());
		 if(ccompescolhida!=null)
		 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.bpp!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.bpp.getDiferentes());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dr!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dr.getDiferentes());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dra!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dra.getDiferentes());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dva!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dva.getDiferentes());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.fc!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.fc.getDiferentes());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		 ccompescolhida	=	escolheMelhorContaComparada(ccompsEscolhidos);
		 if(ccompescolhida==null) {
			 System.out.println("acabou");
			 ccompescolhida =getMelhorCandNEx(cvmbd,cvmprop,databd,dataprop,perbd,perprop);
		 }
		return ccompescolhida;

	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(path="/getMelhorCandidatoNEx")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public ContaComparada getMelhorCandNEx(@RequestParam(value="cvmbd", defaultValue="5258") String cvmbd,
			  @RequestParam(value="cvmprop", defaultValue="5258") String cvmprop,
			  @RequestParam(value="databd", defaultValue="5258") String databd,
			  @RequestParam(value="dataprop", defaultValue="5258") String dataprop,
			  @RequestParam(value="perbd", defaultValue="5258") String perbd,
			  @RequestParam(value="perprop", defaultValue="5258") String perprop
			  
			  ) {
		
		
		ExtratorDeDiferencasToObject edto = new ExtratorDeDiferencasToObject(cvmbd,cvmprop,databd,dataprop,perbd,perprop);
		ArrayList<ContaComparada> ccompsEscolhidos =  new ArrayList<ContaComparada>();
		ContaComparada ccompescolhida;
		double maxsimilaridade=-1;
		if(edto.rdif.bpa!=null) {
		 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.bpa.getNaoExistencia());
		 if(ccompescolhida!=null)
		 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.bpp!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.bpp.getNaoExistencia());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dr!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dr.getNaoExistencia());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dra!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dra.getNaoExistencia());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.dva!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.dva.getNaoExistencia());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		if(edto.rdif.fc!=null) {
			 ccompescolhida	=	escolheMelhorContaComparada(edto.rdif.fc.getNaoExistencia());
			 if(ccompescolhida!=null)
			 ccompsEscolhidos.add(ccompescolhida)	;
		}
		 ccompescolhida	=	escolheMelhorContaComparada(ccompsEscolhidos);
		 if(ccompescolhida==null) {
			 System.out.println("acabou");
		 }
		return ccompescolhida;

	}
	@CrossOrigin(origins = "*")
	@PostMapping(path="/postComparada1",consumes = "application/json")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	@ResponseBody  
	public void getReceiveComparada(@RequestBody String resposta) throws JSONException {
		Gson gson=new Gson();
		
		JSONObject obj = new JSONObject(resposta);
		System.out.println(obj.get("contacomparada"));
		ContaContabil ccomparada=gson.fromJson((String)obj.get("contacomparada"),ContaContabil.class);
		System.out.println("***"+(String)obj.get("contaescolhida"));
		if(((String)obj.get("contaescolhida")).length()>0) {
			ContaContabil contaescolhida=gson.fromJson((String)obj.get("contaescolhida"),ContaContabil.class);
			//ContaContabil cc = (ContaContabil) obj.get("contacomparada");
			EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
			ContaContabil c = em.find(ContaContabil.class, contaescolhida.getIdContaContabil());
			
			ccomparada.setRefConta(c);
			
		}
		ccomparada.setAnalise(1);
		PersisteAccounts.persisteAccount(ccomparada);
		System.out.println(ccomparada.getRefConta().getIdContaContabil());
	}
	@CrossOrigin(origins = "*")
	@GetMapping(path="/getMelhorCandidato")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public ContaComparada getMelhorCand() {
		ComparaContasJaro ccj = new ComparaContasJaro();
		Divergencia dv = ccj.analisar();
		
		ContaComparada	 ccompescolhida	=	escolheMelhorContaComparada(dv.getDiferentes());
	//com problemas aqui
		return ccompescolhida;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(path="/getirmaos")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public List<ContaContabilMinInfo> getIrmaos(@RequestParam(value="idconta") Integer idConta) throws JSONException {
			
			
				
				EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
				ContaContabil c = em.find(ContaContabil.class, idConta);
				ContaContabil cpai = c.getContaPai();
				String squery = "select c from ValorContabil a ,ContaContabil  c where a.demonstrativo.idDemonstrativo = "+c.getDemonstrativo().getIdDemonstrativo()+ " and a.contaContabil.idContaContabil=c.idContaContabil and c.contaPai="+cpai.getIdContaContabil();
				
				  Query query = em.createQuery(squery);
				  List<ContaContabil> contasirmas= (List<ContaContabil>) query.getResultList();
				List<ContaContabil> c1=contasirmas;
				List<ContaContabilMinInfo> lista = new ArrayList<ContaContabilMinInfo> ();
				for(ContaContabil cfilho :c1) {
					ContaContabilMinInfo ccmi = new ContaContabilMinInfo();
					ccmi.setContaContabil(cfilho.getContaContabil());
					ccmi.setDescricao(cfilho.getDescricao());
					ccmi.setIdContaContabil(cfilho.getIdContaContabil());
					lista.add(ccmi);
					System.out.println(cfilho.getContaContabil()+ cfilho.getDescricao());
				}
					return	lista;
				//ccomparada.setRefConta(c);
				
			}
		
	@CrossOrigin(origins = "*")
	@GetMapping(path="/getirmaospai")
	//String cvmbd, String cvmprop, String databd, String dataProp,String perbd, String perprop
	//http://localhost:8080/getRelatorioDiferencas?cvmbd=5258&cvmprop=5258&databd=122011&dataprop=122012&perbd=A&perprop=A
	  public List<ContaContabilMinInfo> getIrmaosPai(@RequestParam(value="idconta") Integer idConta) throws JSONException {
			
			
				
				EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
				ContaContabil c = em.find(ContaContabil.class, idConta);
				ContaContabil cpai = c.getContaPai();
				String squery = "select a from ValorContabil a ,ContaContabil  c where a.demonstrativo.idDemonstrativo = "+c.getDemonstrativo().getIdDemonstrativo()+ " and a.contaContabil.idContaContabil=c.idContaContabil and c.contaPai="+cpai.getIdContaContabil();
				
				  Query query = em.createQuery(squery);
				  List<ValorContabil> valorcontabil= (List<ValorContabil>) query.getResultList();
				  
				List<ValorContabil> c1=valorcontabil;
				List<ContaContabilMinInfo> lista = new ArrayList<ContaContabilMinInfo> ();
				for(ValorContabil cfilho :c1) {
					ContaContabilMinInfo ccmi = new ContaContabilMinInfo();
					ccmi.setContaContabil(cfilho.getContaContabil().getContaContabil());
					ccmi.setDescricao(cfilho.getContaContabil().getDescricao());
					ccmi.setIdContaContabil(cfilho.getContaContabil().getIdContaContabil());
					ccmi.setDemonstrativo(cfilho.getDemonstrativo());
					ccmi.setValorContabil(cfilho.getValor()+"");
					
					squery = "select count(1) from ValorContabil a ,ContaContabil  c where a.demonstrativo.idDemonstrativo = "+c.getDemonstrativo().getIdDemonstrativo()+ " and a.contaContabil.idContaContabil=c.idContaContabil and c.contaPai="+cfilho.getContaContabil().getIdContaContabil();
					
					   query = em.createQuery(squery);
					Long nfilhos= (Long) query.getSingleResult();
					
					
					
					
					ccmi.setCountfilhos(nfilhos);
					
					String[] niveisConta = cfilho.getContaContabil().getContaContabil().trim().split("\\.");
					String contaInicial = niveisConta[0];
					String contasPai[] = new String[niveisConta.length-1];
					String sinicial = cfilho.getContaContabil().getTipoDemonstrativo().getSiglaTipo();
					
					for(int i=1;i<niveisConta.length-1;i++) {
						
						contaInicial +="."+niveisConta[i];
						 Query query1 = em.createQuery("SELECT e.contaContabil FROM ValorContabil e where e.contaContabil.contaContabil=\'"+contaInicial+"\' and e.demonstrativo.idDemonstrativo="+cfilho.getContaContabil().getDemonstrativo().getIdDemonstrativo());
				    	 ContaContabil  tpai = (ContaContabil) query1.getSingleResult();
						String estaConta = (String)tpai.getDescricao();
						sinicial+=" > "+estaConta.split(";")[0];
						
					}
					ccmi.setRaiz(sinicial);
					lista.add(ccmi);
				//	System.out.println(cfilho.getContaContabil()+ cfilho.getDescricao());
					
					
				}
					return	lista;
				//ccomparada.setRefConta(c);
				
			}
		
	
	@GetMapping(path="/getdemonstrativo/all")
	
	  public List<Demonstrativo> getAllDemonstrativos() {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		 Query query = em.createQuery("SELECT e FROM Demonstrativo e");
		    return (List<Demonstrativo>) query.getResultList();

	}
	
	@GetMapping(path="/getjsongraphics")
	
	  public Object[][] getAllDemonstrativos(@RequestParam(value="idindicador") Integer idindicador,@RequestParam(value="cvm") Integer cvm) {
		EntityManager	em = PersistenceManager.INSTANCE.getEntityManager();
		//fazer a query de demonstrativos, fazer o parse a partir dos demosntrativos, setando data a data, fazendo loop e pegando especifico na query abaixo
		//por exemplo a partir de entao para a data 03/12 teria 3 valores na unica data para um preferencia 3 
		   ScriptEngineManager mgr = new ScriptEngineManager();
		    ScriptEngine engine = mgr.getEngineByName("JavaScript");
		    String foo = "40+2";
		    try {
				System.out.println(engine.eval(foo));
			} catch (ScriptException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}
		    
			 Query queryA = em.createQuery("SELECT b FROM Demonstrativo b where  b.empresa.cvm = "+cvm+" and   b.versao =1 order by  b.data");
			 List<Demonstrativo> demonstrativos= (List<Demonstrativo>) queryA.getResultList();
			 List<Double> valoresfinais = new ArrayList<Double>();
			 for(Demonstrativo dem:demonstrativos) {
				 Query query = em.createQuery("SELECT b.valor FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.id_indicador = "+idindicador+" and  c.preferencia = 1 and b.demonstrativo.idDemonstrativo = "+dem.getIdDemonstrativo()+" order by  b.posicao");
				 List<Double> valores= (List<Double>) query.getResultList();
				 Query querye = em.createQuery("SELECT distinct c.expressao.expressao FROM ValorContabil b,Calculo c where b.contaContabil.idCalculo = c.idCalculo and c.indicador.id_indicador = "+idindicador+" and  c.preferencia = 1 and b.demonstrativo.idDemonstrativo = "+dem.getIdDemonstrativo()+" order by  b.posicao");
				 String expressao= (String) querye.getSingleResult();
				 
				 for(int i = 0 ;i<valores.size();i++) {
					expressao= expressao.replace("::"+(i+1),valores.get(i)+"");
				 }
				 try {
						Double valorfinal = (Double)engine.eval(expressao);
						valoresfinais.add(valorfinal);
						
					} catch (ScriptException e) {
						// TODO Bloco catch gerado automaticamente
						e.printStackTrace();
					}
			 }
			 
			 Object[][] retorno = new Object[valoresContabeis.size()][6];  
				 for(int i = 0 ; i < valoresContabeis.size(); i++) {
					 ValorContabil vc = valoresContabeis.get(i);
					 String sdata = (vc.getDemonstrativo().getData()+"").substring(0,4)+"-"+(vc.getDemonstrativo().getData()+"").substring(4,6)+"-"+31;
					 retorno[i][0] = sdata;
					 retorno[i][1] = vc.getValor();
					 retorno[i][2] = vc.getValor();
					 retorno[i][3] = vc.getValor();
					 retorno[i][4] = vc.getValor();
					 retorno[i][5] = 1;
							
				 }
				 
			 
		 return retorno;

	}
}
