package br.com.cvm.rest.json;

import br.com.cvm.bd.modelBD.ContaContabil;

public class RespostaComparacao {
 public ContaContabil contacomparada;
 public ContaContabil contaescolhida;
public ContaContabil getContaescolhida() {
	return contaescolhida;
}
public ContaContabil getContacomparada() {
	return contacomparada;
}
public void setContacomparada(ContaContabil contacomparada) {
	this.contacomparada = contacomparada;
}
public void setContaescolhida(ContaContabil contaescolhida) {
	this.contaescolhida = contaescolhida;
}
}
