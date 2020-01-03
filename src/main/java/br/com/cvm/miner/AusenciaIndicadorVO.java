package br.com.cvm.miner;

import java.util.ArrayList;

import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Expressao;
import br.com.cvm.bd.modelBD.Indicador;

public class AusenciaIndicadorVO {
	@Deprecated
	ArrayList<ArrayList<ContaContabilCalculo>> listlistcontaCalculos;
	ArrayList<ContaContabilCalculo> listcontaCalculos;
	private Expressao expressao;
	Demonstrativo demonstrativo;
	public Demonstrativo getDemonstrativo() {
		return demonstrativo;
	}
	public void setDemonstrativo(Demonstrativo demonstrativo) {
		this.demonstrativo = demonstrativo;
	}
	@Deprecated
	public ArrayList<ArrayList<ContaContabilCalculo>> getContaCalculos() {
	
		return listlistcontaCalculos;
	}
	@Deprecated
	public void setContaCalculos(ArrayList<ArrayList<ContaContabilCalculo>> contaCalculos) {
		this.listlistcontaCalculos = contaCalculos;
	}
	
	@Deprecated
	public void addDemonstrativo(Demonstrativo demonstrativo) {
		if(demonstrativos==null) {
			demonstrativos = new ArrayList<Demonstrativo>();
		}
		demonstrativos.add(demonstrativo);
	}
	@Deprecated
	public void addListContaCalculo(ArrayList<ContaContabilCalculo> listcontaCalculos) {
		if(listlistcontaCalculos ==null) {
			listlistcontaCalculos=	new ArrayList<ArrayList<ContaContabilCalculo>>(); 
		}
		listlistcontaCalculos.add(listcontaCalculos);
	}
	public ArrayList<ContaContabilCalculo> getListcontaCalculos() {
		return listcontaCalculos;
	}
	public void addContaCalculo(ContaContabilCalculo contaCalculo) {
		if(listcontaCalculos ==null) {
			listcontaCalculos=	new ArrayList<ContaContabilCalculo>(); 
		}
		listcontaCalculos.add(contaCalculo);
	}
	public Indicador getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	
	Indicador indicador;
	@Deprecated
	ArrayList<Demonstrativo> demonstrativos;
	@Deprecated
	public ArrayList<Demonstrativo> getDemonstrativos() {
		if(this.demonstrativos==null) {
			this.demonstrativos = new ArrayList<Demonstrativo>();
		}
		return demonstrativos;
	}
	@Deprecated
	public void setDemonstrativos(ArrayList<Demonstrativo> demonstrativos) {
		
		this.demonstrativos = demonstrativos;
	}
	Expressao getExpressao() {
		return expressao;
	}
	void setExpressao(Expressao expressao) {
		this.expressao = expressao;
	}

	
	
	
}
