package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=20)
	private String account;

	@Column(nullable=false, length=8)
	private String cvm;

	@Column(nullable=false, length=6)
	private String data;

	@Column(nullable=false, length=255)
	private String descricao;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_account", insertable=false, updatable=false, nullable=false)
	private int idAccount;

	@Column(nullable=false, length=1)
	private String periodo;

	@Column(nullable=false)
	private int tipo;

	private double valor;

	public Account() {
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCvm() {
		return this.cvm;
	}

	public void setCvm(String cvm) {
		this.cvm = cvm;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdAccount() {
		return this.idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public String getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}