package br.com.cvm.rest.json;

import br.com.cvm.bd.modelBD.Demonstrativo;
import br.com.cvm.bd.modelBD.Empresa;

public class ContaContabilMinInfo {
	Integer idContaContabil;
	public Integer getIdContaContabil() {
		return idContaContabil;
	}
	public void setIdContaContabil(Integer idContaContabil) {
		this.idContaContabil = idContaContabil;
	}
	String contaContabil;
	String descricao;
	Long countfilhos;
	String valorContabil;
	Empresa empresa;
	Demonstrativo demonstrativo;
	public String getRaiz() {
		return raiz;
	}
	public void setRaiz(String raiz) {
		this.raiz = raiz;
	}
	String raiz;
	public String getValorContabil() {
		return valorContabil;
	}
	public void setValorContabil(String valorContabil) {
		this.valorContabil = valorContabil;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Demonstrativo getDemonstrativo() {
		return demonstrativo;
	}
	public void setDemonstrativo(Demonstrativo demonstrativo) {
		this.demonstrativo = demonstrativo;
	}
	public Long getCountfilhos() {
		return countfilhos;
	}
	public void setCountfilhos(Long countfilhos) {
		this.countfilhos = countfilhos;
	}
	public String getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(String contaContabil) {
		this.contaContabil = contaContabil;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
