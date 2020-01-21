package br.com.cvm.bd.modelBD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="indicador_trademap")
@NamedQuery(name="IndicadorTrademap.findAll", query="SELECT i FROM IndicadorTrademap i")
public class IndicadorTrademap implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_indicador_tradem",unique=true, nullable=false)
	int idIndicadorTrademap;
	
	Integer cvm;
	Integer data;
	String indicador;
	String valor;
	public int getIdIndicadorTrademap() {
		return idIndicadorTrademap;
	}
	public void setIdIndicadorTrademap(int idIndicadorTrademap) {
		this.idIndicadorTrademap = idIndicadorTrademap;
	}
	public Integer getCvm() {
		return cvm;
	}
	public void setCvm(Integer cvm) {
		this.cvm = cvm;
	}
	public Integer getData() {
		return data;
	}
	public void setData(Integer data) {
		this.data = data;
	}
	public String getIndicador() {
		return indicador;
	}
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
}
