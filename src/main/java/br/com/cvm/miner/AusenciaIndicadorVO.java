package br.com.cvm.miner;

import java.util.ArrayList;

import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Indicador;

public class AusenciaIndicadorVO {
	ArrayList<ArrayList<ContaContabilCalculo>> listlistcontaCalculos;
	
	public ArrayList<ArrayList<ContaContabilCalculo>> getContaCalculos() {
	
		return listlistcontaCalculos;
	}
	public void setContaCalculos(ArrayList<ArrayList<ContaContabilCalculo>> contaCalculos) {
		this.listlistcontaCalculos = contaCalculos;
	}
	
	public void addDemonstrativo(Demonstrativo demonstrativo) {
		if(demonstrativos==null) {
			demonstrativos = new ArrayList<Demonstrativo>();
		}
		demonstrativos.add(demonstrativo);
	}
	public void addListContaCalculo(ArrayList<ContaContabilCalculo> listcontaCalculos) {
		if(listlistcontaCalculos ==null) {
			listlistcontaCalculos=	new ArrayList<ArrayList<ContaContabilCalculo>>(); 
		}
		listlistcontaCalculos.add(listcontaCalculos);
	}
	public Indicador getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	
	Indicador indicador;
	ArrayList<Demonstrativo> demonstrativos;

	public ArrayList<Demonstrativo> getDemonstrativos() {
		if(this.demonstrativos==null) {
			this.demonstrativos = new ArrayList<Demonstrativo>();
		}
		return demonstrativos;
	}
	public void setDemonstrativos(ArrayList<Demonstrativo> demonstrativos) {
		
		this.demonstrativos = demonstrativos;
	}

	
	
	
}
