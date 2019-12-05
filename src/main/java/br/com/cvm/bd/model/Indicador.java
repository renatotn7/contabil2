package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the indicador database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="indicador")
@NamedQuery(name="Indicador.findAll", query="SELECT i FROM Indicador i")
public class Indicador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_indicador", unique=true, nullable=false)
	private int idIndicador;

	@Column(nullable=false, length=255)
	private String descricao;

	@Column(name="nome_indicador", nullable=false, length=100)
	private String nomeIndicador;
	@JsonIgnore
	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="indicador")
	private List<ContaContabil> contaContabils;

	public Indicador() {
	}

	public int getIdIndicador() {
		return this.idIndicador;
	}

	public void setIdIndicador(int idIndicador) {
		this.idIndicador = idIndicador;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeIndicador() {
		return this.nomeIndicador;
	}

	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	
	public List<ContaContabil> getContaContabils() {
		return this.contaContabils;
	}

	public void setContaContabils(List<ContaContabil> contaContabils) {
		this.contaContabils = contaContabils;
	}

	public ContaContabil addContaContabil(ContaContabil contaContabil) {
		getContaContabils().add(contaContabil);
		contaContabil.setIndicador(this);

		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		contaContabil.setIndicador(null);

		return contaContabil;
	}

}