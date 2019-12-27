package br.com.cvm.miner;

import br.com.cvm.bd.modelBD.Calculo;
import br.com.cvm.bd.modelBD.ContaContabil;

public class ContaContabilCalculo {
ContaContabil contaContabil;
private Calculo calculo;
public Calculo getCalculo() {
	return calculo;
}
public void setCalculo(Calculo calculo) {
	this.calculo = calculo;
}
public ContaContabil getContaContabil() {
	return contaContabil;
}
public void setContaContabil(ContaContabil contaContabil) {
	this.contaContabil = contaContabil;
}
}
