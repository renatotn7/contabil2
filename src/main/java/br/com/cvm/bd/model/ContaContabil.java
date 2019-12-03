package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the conta_contabil database table.
 * 
 */
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

	@Column(name="id_refconta")
	private int idRefconta;
	
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

	public int getIdRefconta() {
		return this.idRefconta;
	}

	public void setIdRefconta(int idRefconta) {
		this.idRefconta = idRefconta;
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
	@JsonIgnore
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

}