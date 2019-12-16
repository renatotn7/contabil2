
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

import br.com.cvm.bd.helper.PersistenceManager;
import br.com.cvm.bd.model.ContaContabil;
import br.com.cvm.bd.model.ValorContabil;
import br.com.cvm.leitor.PeriodoToProperties;

public class AccountIndicadorBD {
 public static void main(String args[]) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

		String cvm2 = "5258";

		ArrayList<Long> valores = new ArrayList<Long>();
		valores.add(121L);
		valores.add(195L);
		valores.add(203L);
		valores.add(210L);
		ArrayList<Integer> datas = new ArrayList<Integer>();
		datas.add(201512);
		datas.add(201612);
		datas.add(201712);
		datas.add(201812);
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
		System.out.println("p1");
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				
				if(comparaValores(valores, map, sb, ientrada, listContas, iconta,0.0,"")) {
					if(encontrou == false) {
					encontrou=true;
					}
				
				}
			}
		}
		if(encontrou) {
			expoeEncontrado(datas, map);
			
		}
		map.clear();
		System.out.println("p2");
		set.clear();
		for (int ientrada = 0; ientrada < valores.size(); ientrada++) {
			List<ValorContabil> listContas =aalistvalores.get(ientrada);
			for (int iconta = 0; iconta < listContas.size(); iconta++) {
				for (int iconta2 = 0; iconta2 < listContas.size(); iconta2++) {//?
					if(set.contains(listContas.get(iconta)) && set.contains(listContas.get(iconta2)) ) {
						continue;
					}
				
					if(listContas.get(iconta2).equals(listContas.get(iconta))) {
						continue;
					}
					
					ValorContabil vc = 	listContas.get(iconta2);
					Double valor=vc.getValor();
					
					if(valor!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor," + "+vc.getContaContabil().getDescricao())) {
						set.add(listContas.get(iconta));
						set.add(listContas.get(iconta2));
						expoeEncontrado(datas, map);
						map.clear();
					}
					if(valor!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor*-1," - "+vc.getContaContabil().getDescricao())){
						set.add(listContas.get(iconta));
						set.add(listContas.get(iconta2));
						expoeEncontrado(datas, map);
						map.clear();
					}
				}
			}
		}
		System.out.println("p3");
		map.clear();
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
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,valor+valor2," + "+ vc.getContaContabil().getDescricao() +" + "+ vc2.getContaContabil().getDescricao())){
							expoeEncontrado(datas, map);
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							map.clear();
						}
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor*-1)+valor2," - "+ vc.getContaContabil().getDescricao() +" + "+ vc2.getContaContabil().getDescricao())) {
							expoeEncontrado(datas, map);
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							map.clear();
						}
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor2*-1)+(valor*-1)," - "+ vc.getContaContabil().getDescricao() +" - "+ vc2.getContaContabil().getDescricao())) {
							expoeEncontrado(datas, map);
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							map.clear();
						}
						if(valor!=null && valor2!=null && comparaValores(valores, map, sb, ientrada, listContas, iconta,(valor2*-1)+valor," + "+ vc.getContaContabil().getDescricao() +" - "+ vc2.getContaContabil().getDescricao())) {
							expoeEncontrado(datas, map);
							set.add(listContas.get(iconta));
							set.add(listContas.get(iconta2));
							set.add(listContas.get(iconta3));
							map.clear();
						}
					}
				}
			}
		}
		
	
		
		System.out.println("p4");
	//	System.out.println(sb.toString());

	//	for (String key : map.keySet()) {
	//		System.out.println(key + " - " + map.get(key) + " de " + datas.size());
	//	}
		em.close();
		System.exit(0);
 }


private static void expoeEncontrado(ArrayList<Integer> datas, Map<String, Integer> map) {
	Set<String> sets = new HashSet<String>();
	for (String key : map.keySet()) {
		if( datas.size()- map.get(key) <=1) {
			if(sets.contains(key)) {
				continue;
			}
			sets.add(key);
			System.out.println(key + " - " + map.get(key) + " de " + datas.size());
		}
	}
	//System.exit(0);
}


private static List<ValorContabil> getListValorContabil(EntityManager em, String cvm2, ArrayList<Integer> datas,
		int ientrada) {
	Query query = em.createQuery("SELECT e FROM ValorContabil e where e.demonstrativo.empresa.cvm=\'" + cvm2
			+ "\' and e.demonstrativo.data=" + datas.get(ientrada));
	List<ValorContabil> cc1 = (List<ValorContabil>) query.getResultList();
	return cc1;
}


private static boolean comparaValores(ArrayList<Long> valores, Map<String, Integer> map, StringBuffer sb, int ientrada,
		List<ValorContabil> cc1, int iconta,Double soma,String addInMap) {
	ValorContabil vc = 	cc1.get(iconta);
	Double valor=vc.getValor();
	
	for(int idivisor=1;idivisor<=6;idivisor++) {
		if(vc.getValor()!=null && vc.getValor()>0) {	
			valor = valor/Math.pow(10, idivisor);
			soma = soma/Math.pow(10, idivisor);
			Long valorf = (Long)Math.round(valor);
			Long valors = (Long)Math.round(soma);
					Long v  = (Long)(valores.get(ientrada));
					Long v1 = (valorf+valors);
					
					if(v.compareTo(v1)==0) {
					Integer valuemap=	map.getOrDefault(vc.getContaContabil().getDescricao() + " "+addInMap, 0);
					map.put(vc.getContaContabil().getDescricao()+ " "+addInMap,++valuemap);
					if(valores.size()==valuemap) {
						return true;
					}
						sb.append("\n"+vc.getContaContabil().getContaContabil() +"   "+vc.getContaContabil().getDescricao()+"   "+ vc.getDemonstrativo().getData() +"   "+vc.getValor());
						break;
					}
				
			}else {
				break;
			}
	}
	return false;
}
 
 
 public static void teste1() {
	 
 }
}

