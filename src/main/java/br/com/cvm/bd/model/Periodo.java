package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@Table(name="periodo")
@NamedQuery(name="Periodo.findAll", query="SELECT p FROM Periodo p")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_periodo", unique=true, nullable=false)
	private int idPeriodo;

	@Lob
	@Column(nullable=false)
	private String descricao;

	@Column(name="sigla_periodo", nullable=false, length=10)
	private String siglaPeriodo;

	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="periodo", fetch=FetchType.LAZY)
	private List<ContaContabil> contaContabils;

	public Periodo() {
	}

	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSiglaPeriodo() {
		return this.siglaPeriodo;
	}

	public void setSiglaPeriodo(String siglaPeriodo) {
		this.siglaPeriodo = siglaPeriodo;
	}
	@JsonIgnore
	public List<ContaContabil> getContaContabils() {
		return this.contaContabils;
	}

	public void setContaContabils(List<ContaContabil> contaContabils) {
		this.contaContabils = contaContabils;
	}

	public ContaContabil addContaContabil(ContaContabil contaContabil) {
		getContaContabils().add(contaContabil);
		contaContabil.setPeriodo(this);

		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		contaContabil.setPeriodo(null);

		return contaContabil;
	}

}