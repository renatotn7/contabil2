package entities;

import br.com.cvm.bd.modelBD.ContaContabil;

public class ContaCandidata implements Comparable<ContaCandidata>{
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

    public int compareTo(ContaCandidata outraConta) {
        if (this.similaridade < outraConta.similaridade) {
            return -1;
        }
        if (this.similaridade > outraConta.similaridade) {
            return 1;
        }
        return 0;
    }
}
