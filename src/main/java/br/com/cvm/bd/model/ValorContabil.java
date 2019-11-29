package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the valor_contabil database table.
 * 
 */
@Entity
@Table(name="valor_contabil")
@NamedQuery(name="ValorContabil.findAll", query="SELECT v FROM ValorContabil v")
public class ValorContabil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_valor_contabil", unique=true, nullable=false)
	private int idValorContabil;

	@Column(nullable=false)
	private double valor;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="id_conta_contabil", nullable=false)
	private ContaContabil contaContabil;

	public ValorContabil() {
	}

	public int getIdValorContabil() {
		return this.idValorContabil;
	}

	public void setIdValorContabil(int idValorContabil) {
		this.idValorContabil = idValorContabil;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public ContaContabil getContaContabil() {
		return this.contaContabil;
	}

	public void setContaContabil(ContaContabil contaContabil) {
		this.contaContabil = contaContabil;
	}

}