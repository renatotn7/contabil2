package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;


/**
 * The persistent class for the conta_contabil database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="conta_contabil")
@NamedQuery(name="ContaContabil.findAll", query="SELECT c FROM ContaContabil c")
public class ContaContabil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_conta_contabil", unique=true, nullable=false)
	private int idContaContabil;

	@Column(name="conta_contabil", nullable=false, length=100)
	private String contaContabil;

	@Column(nullable=false, length=255)
	private String descricao;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_conta_pai")
	private ContaContabil contaPai;
	
	@JsonIgnore
	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="contaPai")
	private List<ContaContabil> contasFilhos;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_refconta")
	private ContaContabil refConta;
	@JsonIgnore
	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="refConta")
	private List<ContaContabil> refContas;
	
	
	
	
	@Column(name="analise")
	private int analise; 

	public int getAnalise() {
		return analise;
	}

	public void setAnalise(int analise) {
		this.analise = analise;
	}

	//bi-directional many-to-one association to Demonstrativo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_demonstrativo")
	private Demonstrativo demonstrativo;

	//bi-directional many-to-one association to Indicador
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador")
	private Indicador indicador;

	//bi-directional many-to-one association to TipoDemonstrativo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo", nullable=false)
	private TipoDemonstrativo tipoDemonstrativo;
	@JsonIgnore
	//bi-directional many-to-one association to ValorContabil
	@OneToMany(mappedBy="contaContabil")
	private List<ValorContabil> valorContabils;

	public ContaContabil() {
	}

	public int getIdContaContabil() {
		return this.idContaContabil;
	}

	public void setIdContaContabil(int idContaContabil) {
		this.idContaContabil = idContaContabil;
	}

	public String getContaContabil() {
		return this.contaContabil;
	}

	public void setContaContabil(String contaContabil) {
		this.contaContabil = contaContabil;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Demonstrativo getDemonstrativo() {
		return this.demonstrativo;
	}

	public void setDemonstrativo(Demonstrativo demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public TipoDemonstrativo getTipoDemonstrativo() {
		return this.tipoDemonstrativo;
	}

	public void setTipoDemonstrativo(TipoDemonstrativo tipoDemonstrativo) {
		this.tipoDemonstrativo = tipoDemonstrativo;
	}
//	@JsonIgnore
	public List<ValorContabil> getValorContabils() {
		return this.valorContabils;
	}

	public void setValorContabils(List<ValorContabil> valorContabils) {
		this.valorContabils = valorContabils;
	}

	public ValorContabil addValorContabil(ValorContabil valorContabil) {
		getValorContabils().add(valorContabil);
		valorContabil.setContaContabil(this);

		return valorContabil;
	}

	public ValorContabil removeValorContabil(ValorContabil valorContabil) {
		getValorContabils().remove(valorContabil);
		valorContabil.setContaContabil(null);

		return valorContabil;
	}
	

	public ContaContabil getContaPai() {
		return this.contaPai;
	}

	public void setContaPai(ContaContabil contaContabil1) {
		this.contaPai = contaContabil1;
	}

	public List<ContaContabil> getContasFilhos() {
		return this.contasFilhos;
	}

	public void setContasFilhos(List<ContaContabil> contaContabils1) {
		this.contasFilhos = contaContabils1;
	}

	public ContaContabil addContasFilhos(ContaContabil contaContabils1) {
		getContasFilhos().add(contaContabils1);
		contaContabils1.setContaPai(this);

		return contaContabils1;
	}

	public ContaContabil removeContaContabils1(ContaContabil contaContabils1) {
		getContasFilhos().remove(contaContabils1);
		contaContabils1.setContaPai(null);

		return contaContabils1;
	}

	public ContaContabil getRefConta() {
		return this.refConta;
	}

	public void setRefConta(ContaContabil contaContabil2) {
		this.refConta = contaContabil2;
	}

	public List<ContaContabil> getRefContas() {
		return this.refContas;
	}

	public void setRefContas(List<ContaContabil> contaContabils2) {
		this.refContas = contaContabils2;
	}

	public ContaContabil addRefContas(ContaContabil contaContabils2) {
		getRefContas().add(contaContabils2);
		contaContabils2.setRefConta(this);

		return contaContabils2;
	}

	public ContaContabil removeRefContas(ContaContabil contaContabils2) {
		getRefContas().remove(contaContabils2);
		contaContabils2.setRefConta(null);

		return contaContabils2;
	}
}