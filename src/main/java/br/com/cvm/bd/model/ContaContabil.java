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

	@Column(nullable=false)
	private int data;

	@Column(nullable=false)
	private int versao;
	@Column(nullable=false)
	private String descricao;
	
	@Column(name="idrefconta")
	private Integer idrefconta;
	


	public Integer getIdrefconta() {
		return idrefconta;
	}

	public void setIdrefconta(Integer idrefconta) {
		this.idrefconta = idrefconta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="cvm", nullable=false)
	private Empresa empresa;

	//bi-directional many-to-one association to Indicador
	@ManyToOne
	@JoinColumn(name="id_indicador")
	private Indicador indicador;

	//bi-directional many-to-one association to Periodo
	@ManyToOne
	@JoinColumn(name="id_periodo", nullable=false)
	private Periodo periodo;

	//bi-directional many-to-one association to TipoDemonstrativo
	@ManyToOne
	@JoinColumn(name="id_tipo", nullable=false)
	private TipoDemonstrativo tipoDemonstrativo;

	//bi-directional many-to-one association to ValorContabil
	@OneToMany(mappedBy="contaContabil", fetch=FetchType.LAZY)
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

	public int getData() {
		return this.data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getVersao() {
		return this.versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
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