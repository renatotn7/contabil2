package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the demonstrativo database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="demonstrativo")
@NamedQuery(name="Demonstrativo.findAll", query="SELECT d FROM Demonstrativo d")
public class Demonstrativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_demonstrativo", unique=true, nullable=false)
	private int idDemonstrativo;

	@Column(nullable=false)
	private int data;

	@Column(name="estado_criacao", nullable=false)
	private int estadoCriacao;

	@Column(nullable=false)
	private int versao;
	@JsonIgnore
	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="demonstrativo")
	private List<ContaContabil> contaContabils;

	//bi-directional many-to-one association to Empresa
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cvm", nullable=false)
	private Empresa empresa;

	//bi-directional many-to-one association to Periodo
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_periodo", nullable=false)
	private Periodo periodo;
	@JsonIgnore
	//bi-directional many-to-one association to ValorContabil
	@OneToMany(mappedBy="demonstrativo")
	private List<ValorContabil> valorContabils;

	public Demonstrativo() {
	}

	public int getIdDemonstrativo() {
		return this.idDemonstrativo;
	}

	public void setIdDemonstrativo(int idDemonstrativo) {
		this.idDemonstrativo = idDemonstrativo;
	}

	public int getData() {
		return this.data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getEstadoCriacao() {
		return this.estadoCriacao;
	}

	public void setEstadoCriacao(int estadoCriacao) {
		this.estadoCriacao = estadoCriacao;
	}

	public int getVersao() {
		return this.versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}

	public List<ContaContabil> getContaContabils() {
		return this.contaContabils;
	}

	public void setContaContabils(List<ContaContabil> contaContabils) {
		this.contaContabils = contaContabils;
	}

	public ContaContabil addContaContabil(ContaContabil contaContabil) {
		getContaContabils().add(contaContabil);
		contaContabil.setDemonstrativo(this);

		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		contaContabil.setDemonstrativo(null);

		return contaContabil;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<ValorContabil> getValorContabils() {
		return this.valorContabils;
	}

	public void setValorContabils(List<ValorContabil> valorContabils) {
		this.valorContabils = valorContabils;
	}

	public ValorContabil addValorContabil(ValorContabil valorContabil) {
		getValorContabils().add(valorContabil);
		valorContabil.setDemonstrativo(this);

		return valorContabil;
	}

	public ValorContabil removeValorContabil(ValorContabil valorContabil) {
		getValorContabils().remove(valorContabil);
		valorContabil.setDemonstrativo(null);

		return valorContabil;
	}

}