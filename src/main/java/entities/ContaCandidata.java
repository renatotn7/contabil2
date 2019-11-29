package entities;

import br.com.cvm.bd.model.ContaContabil;

public class ContaCandidata {
	ContaContabil conta;
	Double similaridade ;
	String raiz;
	public String getRaiz() {
		return raiz;
	}
	public void setRaiz(String raiz) {
		this.raiz = raiz;
	}
	public ContaContabil getConta() {
		return conta;
	}
	public void setConta(ContaContabil conta) {
		this.conta = conta;
	}
	public Double getSimilaridade() {
		return similaridade;
	}
	public void setSimilaridade(Double similaridade) {
		this.similaridade = similaridade;
	}
}
