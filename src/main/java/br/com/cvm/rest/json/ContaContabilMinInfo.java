package br.com.cvm.rest.json;

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
