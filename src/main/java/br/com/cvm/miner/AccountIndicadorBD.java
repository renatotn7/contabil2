
package br.com.cvm.miner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.cvm.bd.helper.JPAUtil;
import br.com.cvm.bd.modelBD.ContaContabil;
import br.com.cvm.bd.modelBD.FundIndicador;
import br.com.cvm.bd.modelBD.Indicador;
import br.com.cvm.bd.modelBD.PropstaConfIndicDetalhe;
import br.com.cvm.bd.modelBD.PropstaConfIndicHeader;
import br.com.cvm.bd.modelBD.ValorContabil;
import br.com.cvm.leitor.origemBDeProperties.PeriodoToProperties;

public class AccountIndicadorBD {
	public static EntityManager em = JPAUtil.INSTANCE.getEntityManager();
	public static void miner(String cvm2, 	ArrayList<Long> valores, ArrayList<Integer> datas,String titulo) {
		

		
		
		

		// 683
		// 629
		// 556
		boolean encontrou = false;
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<ValorContabil> set = new HashSet<ValorContabil>();
		List<List<ValorContabil> > aalistvalores=new 	ArrayList<List<ValorContabil> >();
		
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			aalistvalores.add( getListValorContabil(em, cvm2, datas, ientrada));
		}
		StringBuffer sb = new StringBuffer();
		System.out.println(titulo);
		System.out.println("p1");
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC.add(listContas.get(iconta));
				if(comparaValores(valores, map, sb, ientrada, listContas, iconta,0.0,"",valoresC)) {
					if(encontrou == false) {
					encontrou=true;
					}
					
				}
				valoresC=new ArrayList<ValorContabil>();
			}
		}
		if(encontrou) {
			expoeEncontrado(datas, map,"::1");
			
		}
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		System.out.println("p2");
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC =new ArrayList<ValorContabil>();
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					if(set.contains(listContas.get(iconta)) && set.contains(listContas.get(iconta2)) ) {
						continue;
					}
				
					if(listContas.get(iconta2).equals(listContas.get(iconta))) {
						continue;
					}
					
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				//	valoresC =new ArrayList<ValorContabil>();
					valoresC.add(listContas.get(iconta));
					valoresC.add(vc);
					String chave=vc.getContaContabil().getDescricao()+":"+ getRaiz( vc);
					if(valor!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor," + "+chave,valoresC)) {
						set.add(listContas.get(iconta));
						set.add(listContas.get(iconta2));
						expoeEncontrado(datas, map,"::1+::2");
					//	map.clear();
					//	mapvalores.clear();
					//	valoresC =new ArrayList<ValorContabil>();
						//valoresC.add(vc);
					}
					
					valoresC =new ArrayList<ValorContabil>();
				}
			}
		}
		
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		System.out.println("p2.1");
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC =new ArrayList<ValorContabil>();
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					if(set.contains(listContas.get(iconta)) && set.contains(listContas.get(iconta2)) ) {
						continue;
					}
				
					if(listContas.get(iconta2).equals(listContas.get(iconta))) {
						continue;
					}
					
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				//	valoresC =new ArrayList<ValorContabil>();
					valoresC.add(listContas.get(iconta));
					valoresC.add(vc);
					String chave=vc.getContaContabil().getDescricao()+":"+getRaiz( vc);
					if(valor!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor*-1," - "+chave,valoresC)){
						set.add(listContas.get(iconta));
						set.add(listContas.get(iconta2));
						expoeEncontrado(datas, map,"::1-::2");
					//	map.clear();
					//	mapvalores.clear();
					//	valoresC =new ArrayList<ValorContabil>();
						//valoresC.add(vc);
					}
					valoresC =new ArrayList<ValorContabil>();
				}
			}
		}
		
		
		
		System.out.println("p3");
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				
				
					for (int iconta3 = 0; iconta3 < listContas.size(); iconta3++) {//?
						
						if(set.contains(listContas.get(iconta)) || set.contains(listContas.get(iconta2))|| set.contains(listContas.get(iconta3)) ) {
							continue;
						}
					
						ValorContabil vc2 = 	listContas.get(iconta3);
						Double valor2=vc2.getValor();
						if(listContas.get(iconta2).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta2))) {
							continue;
						}
						//set.add(listContas.get(iconta));
					//	set.add(listContas.get(iconta2));
					//	set.add(listContas.get(iconta3));
						//valoresC =new ArrayList<ValorContabil>();
					//	valoresC.add(vc);
						valoresC =new ArrayList<ValorContabil>();//o problema � que pode apagar para o proximo ano
						valoresC.add(listContas.get(iconta));
						valoresC.add(vc);//talvez possa ser solucionado se eu eliminar quando for negativo
						valoresC.add(vc2);
						String chave=vc.getContaContabil().getDescricao()+":"+getRaiz( vc);
						String chave2=vc2.getContaContabil().getDescricao()+":"+getRaiz( vc2);
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor+valor2," + "+ chave +" + "+ chave2,valoresC)){
							expoeEncontrado(datas, map,"::1+::2+::3");
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							//map.clear();
						//	mapvalores.clear();
						//	valoresC =new ArrayList<ValorContabil>();
							//valoresC.add(vc);
							//valoresC.add(vc2);
						}
						
						//valoresC =new ArrayList<ValorContabil>();
					}
				}
			}
		}
		
		System.out.println("p3.1");
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC =new ArrayList<ValorContabil>();
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				
				
					for (int iconta3 = 0; iconta3 < listContas.size(); iconta3++) {//?
						
						if(set.contains(listContas.get(iconta)) || set.contains(listContas.get(iconta2))|| set.contains(listContas.get(iconta3)) ) {
							continue;
						}
					
						ValorContabil vc2 = 	listContas.get(iconta3);
						Double valor2=vc2.getValor();
						if(listContas.get(iconta2).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta2))) {
							continue;
						}
						//set.add(listContas.get(iconta));
					//	set.add(listContas.get(iconta2));
					//	set.add(listContas.get(iconta3));
						//valoresC =new ArrayList<ValorContabil>();
					//	valoresC.add(vc);
						valoresC.add(listContas.get(iconta));
						valoresC.add(vc);
						valoresC.add(vc2);
						String chave=vc.getContaContabil().getDescricao()+":"+getRaiz( vc);
						String chave2=vc2.getContaContabil().getDescricao()+":"+getRaiz( vc2);
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor*-1)+valor2," - "+chave +" + "+ chave2,valoresC)) {
							expoeEncontrado(datas, map,"::1-::2+::3");
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
						//	map.clear();
						//	mapvalores.clear();
						//	valoresC =new ArrayList<ValorContabil>();
							//valoresC.add(vc);
							//valoresC.add(vc2);
						}
						
						valoresC =new ArrayList<ValorContabil>();
					}
				}
			}
		}
		
		System.out.println("p3.2");
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC =new ArrayList<ValorContabil>();
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				
				
					for (int iconta3 = 0; iconta3 < listContas.size(); iconta3++) {//?
						
						if(set.contains(listContas.get(iconta)) || set.contains(listContas.get(iconta2))|| set.contains(listContas.get(iconta3)) ) {
							continue;
						}
					
						ValorContabil vc2 = 	listContas.get(iconta3);
						Double valor2=vc2.getValor();
						if(listContas.get(iconta2).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta2))) {
							continue;
						}
						//set.add(listContas.get(iconta));
					//	set.add(listContas.get(iconta2));
					//	set.add(listContas.get(iconta3));
						//valoresC =new ArrayList<ValorContabil>();
					//	valoresC.add(vc);
						valoresC.add(listContas.get(iconta));
						valoresC.add(vc);
						valoresC.add(vc2);
						String chave=vc.getContaContabil().getDescricao()+":"+getRaiz( vc);
						String chave2=vc2.getContaContabil().getDescricao()+":"+getRaiz( vc2);
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor2*-1)+(valor*-1)," - "+chave+" - "+ chave2,valoresC)) {
							expoeEncontrado(datas, map,"::1-::2-::3");
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							map.clear();
							//mapvalores.clear();
						//	valoresC =new ArrayList<ValorContabil>();
							//valoresC.add(vc);
						//	valoresC.add(vc2);
						}
						
						valoresC =new ArrayList<ValorContabil>();
					}
				}
			}
		}
		
		System.out.println("p3.3");
		map.clear();
		mapvalores.clear();
		valoresC=new ArrayList<ValorContabil>();
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				valoresC =new ArrayList<ValorContabil>();
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
				
				
					for (int iconta3 = 0; iconta3 < listContas.size(); iconta3++) {//?
						
						if(set.contains(listContas.get(iconta)) || set.contains(listContas.get(iconta2))|| set.contains(listContas.get(iconta3)) ) {
							continue;
						}
					
						ValorContabil vc2 = 	listContas.get(iconta3);
						Double valor2=vc2.getValor();
						if(listContas.get(iconta2).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta)) || listContas.get(iconta3).equals(listContas.get(iconta2))) {
							continue;
						}
						//set.add(listContas.get(iconta));
					//	set.add(listContas.get(iconta2));
					//	set.add(listContas.get(iconta3));
						//valoresC =new ArrayList<ValorContabil>();
					//	valoresC.add(vc);
						valoresC.add(listContas.get(iconta));
						valoresC.add(vc);
						valoresC.add(vc2);
						String chave=vc.getContaContabil().getDescricao()+":"+getRaiz( vc);
						String chave2=vc2.getContaContabil().getDescricao()+":"+getRaiz( vc2);
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor2*-1)+valor," + "+ chave +" - "+ chave2,valoresC)) {
							expoeEncontrado(datas, map,"::1+::2-::3");
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
						//	map.clear();
						//	mapvalores.clear();
						//	valoresC =new ArrayList<ValorContabil>();
							//valoresC.add(vc);
							//valoresC.add(vc2);
						}
						valoresC =new ArrayList<ValorContabil>();
					}
				}
			}
		}
		
	
		
		System.out.println("......................................................................");
	//	System.out.println(sb.toString());

	//	for (String key : map.keySet()) {
	//		System.out.println(key + " - " + map.get(key) + " de " + datas.size());
	//	}
		
	}
	public static Indicador indic;


public static void main(String args[]) {
	String cvm2 = "8133";
	discoveryIndic( "5258");
	discoveryIndic( "8133");
	discoveryIndic( "20257");
	discoveryIndic( "5410");
	em.close();
	System.exit(0);
	//5258 8133 20257 5410
}
	public static void discoveryIndic(String cvm2) {
		
	
	 	//5258 8133 20257 5410
		
		Query query = em.createQuery("SELECT e FROM FundIndicador e where e.demonstrativo.empresa.cvm=\'" + cvm2
			+"\'"	);
		
		
		List<FundIndicador> cc1 = (List<FundIndicador>) query.getResultList();
		ArrayList<Long> valores = new ArrayList<Long>();
		//valores.add(121L);
		//valores.add(195L);
		///valores.add(203L);
	//	valores.add(210L);
		ArrayList<Integer> datas = new ArrayList<Integer>();
	//	datas.add(201512);
	//	datas.add(201612);
	//	datas.add(201712);
	//	datas.add(201812);
		for(FundIndicador fi : cc1) {
	//		valores.add((long)fi.getPat_Liq());
			datas.add(fi.getDemonstrativo().getData());
		}
		String nomeindic="Pat_Liq";
		try {
		query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
		 indic= (Indicador) query.getSingleResult();
		}catch(Exception e) {
			indic = null;
		}
		 if(indic ==null) {
			 indic= new Indicador();
				indic.setNomeIndicador(nomeindic);
				indic.setNomeIndicador("patrimonio liquido consolidado");
				 em.getTransaction()
			        .begin();
					em.persist(indic);
					em.getTransaction()
			        .commit();
		 }
		 
		//indic.setNomeIndicador("patrimonio liquido consolidado");
		System.out.println("patrimonio liquido:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getPat_Liq());
			//datas.add(fi.getDemonstrativo().getData());
		}
		miner(cvm2,valores,datas,"patrimonio liquido:");
		 nomeindic="EBITDA";
		try {
		query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
		 indic= (Indicador) query.getSingleResult();
		}catch(Exception e) {
			indic = null;
		}
		
		System.out.println("Ebitda:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getEbitda());
			
		}
		miner(cvm2,valores,datas,"Ebitda:");
		
		 nomeindic="Divida";
			try {
			query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
			 indic= (Indicador) query.getSingleResult();
			}catch(Exception e) {
				indic = null;
			}
			
		valores.clear();
		System.out.println("Divida:");
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getDivida());
			
		}
		miner(cvm2,valores,datas,"Divida:");
		 nomeindic="CAPEX";
			try {
			query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
			 indic= (Indicador) query.getSingleResult();
			}catch(Exception e) {
				indic = null;
			}
			
		System.out.println("Capex:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getCapex());
			
		}
		miner(cvm2,valores,datas,"Capex:");
		
		 nomeindic="FCO";
			try {
			query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
			 indic= (Indicador) query.getSingleResult();
			}catch(Exception e) {
				indic = null;
			}
			
		System.out.println("Fco:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getFco());
			
		}
		miner(cvm2,valores,datas,"Fco:");
		
		 nomeindic="FCF";
			try {
			query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
			 indic= (Indicador) query.getSingleResult();
			}catch(Exception e) {
				indic = null;
			}
		System.out.println("FCF:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getFcf());
			
		}
		miner(cvm2,valores,datas,"FCF:");
		 nomeindic="Prov";
			try {
			query = em.createQuery("SELECT e FROM Indicador e where e.nomeIndicador=\'" + nomeindic+"\'");
			 indic= (Indicador) query.getSingleResult();
			}catch(Exception e) {
				indic = null;
			}
		System.out.println("Proventos:");
		valores.clear();
		for(FundIndicador fi : cc1) {
			
			valores.add((long)fi.getProv());
			
		}
		miner(cvm2,valores,datas,"Proventos:");
	
		
		
	//	miner(cvm2,valores,datas);
		
	//	miner(cvm2,valores,datas);
	
 }


private static void expoeEncontrado(ArrayList<Integer> datas, Map<String, Integer> map,String expressao) {
	
	
	Set<String> sets = new HashSet<String>();
	StringBuilder sb = new StringBuilder();
	
	for (String key : map.keySet()) {
		if( datas.size()- map.get(key) <=1) {
			if(sets.contains(key)) {
				continue;
			}
			sets.add(key);
		
			sb.append(key + " - " + map.get(key) + " de " + datas.size()+"\n");
			 em.getTransaction()
		        .begin();
			PropstaConfIndicHeader pcih = new PropstaConfIndicHeader();
			pcih.setQtdInicial(datas.size());
			pcih.setQtdEncontrada(map.get(key));
			pcih.setDescricao(key);
			
			pcih.setExpressao(expressao);
			pcih.setIndicador(indic);
		//	pcih.setPropstaConfIndicDetalhes(new ArrayList<PropstaConfIndicDetalhe>());
			em.persist(pcih);
			
			for(Integer data:datas) {
				ArrayList<ValorContabil> vcs = mapvalores.get(data+":"+key); //201512:Patrim�nio L�quido Consolidado:2 
			
				PropstaConfIndicDetalhe pcid = new PropstaConfIndicDetalhe();
				
				if(vcs!=null) {
					pcih.setQtdColunas(vcs.size());
					pcih.setCvm(vcs.get(0).getDemonstrativo().getEmpresa().getCvm());
					
					sb.append("\n\t"+data+"\n");
					for(ValorContabil vc: vcs) {
						sb.append("\t\t"+getRaiz(vc)+"\n");
						sb.append("\t\t\t"+vc.getDemonstrativo().getData()+" "+vc.getDemonstrativo().getEmpresa().getCvm()+" "+vc.getContaContabil().getContaContabil()+" "+ vc.getContaContabil().getDescricao()+" "+ vc.getContaContabil().getIdContaContabil()+" "+ vc.getValor()+"\n");
					
					}
				//	pcih.setDemonstrativo(vcs.get(0).getDemonstrativo());
					//pcih.addPropstaConfIndicDetalhe(pcid);
					pcid.setDemonstrativo(vcs.get(0).getDemonstrativo());
					pcih.getIdPropstaConfIndicHeader();
					pcid.setPropstaConfIndicHeader(pcih);
					if(vcs.size()>0) {
						pcid.setContaContabil1Bean(vcs.get(0).getContaContabil());
					}
					if(vcs.size()>1) {
						pcid.setContaContabil2Bean(vcs.get(1).getContaContabil());
					}
					if(vcs.size()>2) {
						pcid.setContaContabil3Bean(vcs.get(2).getContaContabil());
					}
					if(vcs.size()>3) {
						pcid.setContaContabil4Bean(vcs.get(3).getContaContabil());
					}
					if(vcs.size()>4) {
						pcid.setContaContabil5Bean(vcs.get(4).getContaContabil());
					}
					if(vcs.size()>5) {
						pcid.setContaContabil6Bean(vcs.get(5).getContaContabil());
					}
					if(vcs.size()>6) {
						pcid.setContaContabil7Bean(vcs.get(6).getContaContabil());
					}
					if(vcs.size()>7) {
						pcid.setContaContabil8Bean(vcs.get(7).getContaContabil());
					} 
					if(vcs.size()>8) {
						pcid.setContaContabil9Bean(vcs.get(8).getContaContabil());
					}
					if(vcs.size()>9) {
						pcid.setContaContabil10Bean(vcs.get(9).getContaContabil());
					}
					
						em.persist(pcid);
						
				}
			
				
				//SSystem.out.println("");
			}
			em.getTransaction()
	        .commit();
		}
	}
	System.out.println(sb.toString());
}


private static List<ValorContabil> getListValorContabil(EntityManager em, String cvm2, ArrayList<Integer> datas,
		int ientrada) {
	Query query = em.createQuery("SELECT e FROM ValorContabil e where e.demonstrativo.empresa.cvm=\'" + cvm2
			+ "\' and e.demonstrativo.data=" + datas.get(ientrada));
	List<ValorContabil> cc1 = (List<ValorContabil>) query.getResultList();
	return cc1;
}

static ArrayList< ValorContabil> valoresC = new ArrayList<ValorContabil>();
static Map< String,ArrayList<ValorContabil>> mapvalores = new HashMap<String, ArrayList<ValorContabil>>();
private static boolean comparaValores(ArrayList<Long> valores, Map<String, Integer> map, StringBuffer sb, int ientrada,
		List<ValorContabil> cc1, int iconta,Double soma,String addInMap, ArrayList< ValorContabil> valoresContabeis ) {
	ValorContabil vc = 	cc1.get(iconta);
	Double valor=vc.getValor();
	
	for(int idivisor=3;idivisor<=3;idivisor++) {
		
		if(vc.getValor()!=null && (Long)Math.round(valor/Math.pow(10, idivisor))>0) {	
			valor = valor/Math.pow(10, idivisor);
			Double soman = soma/Math.pow(10, idivisor);
			Long valorf = (Long)Math.round(valor);
			Long valors = (Long)Math.round(soman);
					Long v  = (Long)(valores.get(ientrada));
					Long v1 = (valorf+valors);
				
					if(v.compareTo(v1)==0) { 	
					if(valorf.compareTo(valorf+valors)==0 && soma >0) {
							break;
						}
					
						String chave="";
						int nivel_relax = 0;
						if(nivel_relax==0) {
								chave  = vc.getContaContabil().getDescricao()+":"+getRaiz(vc);
						}
						if(nivel_relax==1) {
							chave  = vc.getContaContabil().getContaPai().getDescricao()+":"+getRaiz(vc);
					}
								Integer valuemap=	map.getOrDefault(chave + " "+addInMap, 0);
					
					
					//valoresC.add(vc);
					for( ValorContabil vc1:valoresC) {
						Double valor1 = vc1.getValor()/Math.pow(10, idivisor);
						Long valorf1 = (Long)Math.round(valor1);
					
						if(vc1.getValor()<Math.pow(10, idivisor) ||(valorf1.compareTo(v)==0 && valoresC.size()>1)) {
							valoresC=new ArrayList<ValorContabil>();
							return false;
						}
					}
					map.put(chave+ " "+addInMap,++valuemap);
					//System.out.println("idivisor:"+idivisor);
				//	valoresC.addAll(valoresContabeis);
					mapvalores.put(vc.getDemonstrativo().getData()+":"+chave+ " "+addInMap,valoresC);
				
					if(valores.size()-valuemap<=1) {
						return true;
					}
						//sb.append("\n"+vc.getContaContabil().getContaContabil() +"   "+vc.getContaContabil().getDescricao()+"   "+ vc.getDemonstrativo().getData() +"   "+vc.getValor());
						break;
					}
				
			}else {
				break;
			}
	}
	return false;
}


private static String getRaiz(ValorContabil vc) {
	String contaContabil = vc.getContaContabil().getContaContabil();
	String raiz="";
	try {
	int index =contaContabil.lastIndexOf(".");
	if(index !=-1) {
	 raiz =contaContabil.substring(0,index);
	}else {
		raiz = contaContabil;
	}
	 return raiz;
	 
	}catch(Exception e) {
		e.printStackTrace();
	}
	return null;
}
 
}

