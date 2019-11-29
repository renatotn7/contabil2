package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name="empresa")
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	@Column(unique=true, nullable=false)
	private int cvm;

	@Column(name="nome_pregao", nullable=false, length=50)
	private String nomePregao;

	@Column(name="raiz_ativo", nullable=true, length=8)
	private String raizAtivo;

	@Column(name="razao_social", nullable=false, length=255)
	private String razaoSocial;

	@Column(nullable=true)
	private int situacao;
	@Column(nullable=true)
	private String segmento;
	
	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getSubsetor() {
		return subsetor;
	}

	public void setSubsetor(String subsetor) {
		this.subsetor = subsetor;
	}


	@Column(nullable=true)
	private String setor;
	@Column(nullable=true)
	private String subsetor;
	@Column(nullable=true)
	private String segmentosetorial;
	
	public String getSegmentosetorial() {
		return segmentosetorial;
	}

	public void setSegmentosetorial(String segmentosetorial) {
		this.segmentosetorial = segmentosetorial;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
	private List<ContaContabil> contaContabils;

	public Empresa() {
	}

	public int getCvm() {
		return this.cvm;
	}

	public void setCvm(int cvm) {
		this.cvm = cvm;
	}

	public String getNomePregao() {
		return this.nomePregao;
	}

	public void setNomePregao(String nomePregao) {
		this.nomePregao = nomePregao;
	}

	public String getRaizAtivo() {
		return this.raizAtivo;
	}

	public void setRaizAtivo(String raizAtivo) {
		this.raizAtivo = raizAtivo;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public int getSituacao() {
		return this.situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
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
		contaContabil.setEmpresa(this);

		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		contaContabil.setEmpresa(null);

		return contaContabil;
	}

}