package entities;

import java.util.ArrayList;
import java.util.List;

import br.com.cvm.bd.model.ContaContabil;

public class ContaComparada {
	ContaContabil comparado;
	List<ContaCandidata> candidatos ;
	String tipoComparacao;
	String raiz;
	public String getRaiz() {
		return raiz;
	}

	public void setRaiz(String raiz) {
		this.raiz = raiz;
	}

	public String getTipoComparacao() {
		return tipoComparacao;
	}

	public void setTipoComparacao(String tipoComparacao) {
		this.tipoComparacao = tipoComparacao;
	}

	public List<ContaCandidata> getCandidatos() {
		return this.candidatos;
	}

	public void setCandidatos(List<ContaCandidata> candidatos) {
		this.candidatos = candidatos;
	}

	public ContaCandidata addCandidato(ContaCandidata candidato) {
		if(candidatos == null) {
			candidatos	= new ArrayList<ContaCandidata> ();
		}
		getCandidatos().add(candidato);
	

		return candidato;
	}

	public ContaCandidata removeCandidato(ContaCandidata candidato) {
		if(candidatos == null) {
			candidatos	= new ArrayList<ContaCandidata> ();
		}
		getCandidatos().remove(candidato);
		

		return candidato;
	}

	public ContaContabil getComparado() {
		return comparado;
	}

	public void setComparado(ContaContabil comparado) {
		this.comparado = comparado;
	}
	
}
