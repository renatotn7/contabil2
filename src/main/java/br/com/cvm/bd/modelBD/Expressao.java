package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the expressao database table.
 * 
 */
@Entity
@NamedQuery(name="Expressao.findAll", query="SELECT e FROM Expressao e")
public class Expressao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_expressao")
	private int idExpressao;

	private String expressao;

	@Column(name="num_colunas")
	private int numColunas;

	//bi-directional many-to-one association to Calculo
	@OneToMany(mappedBy="expressao")
	private List<Calculo> calculos;

	public Expressao() {
	}

	public int getIdExpressao() {
		return this.idExpressao;
	}

	public void setIdExpressao(int idExpressao) {
		this.idExpressao = idExpressao;
	}

	public String getExpressao() {
		return this.expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public int getNumColunas() {
		return this.numColunas;
	}

	public void setNumColunas(int numColunas) {
		this.numColunas = numColunas;
	}

	public List<Calculo> getCalculos() {
		return this.calculos;
	}

	public void setCalculos(List<Calculo> calculos) {
		this.calculos = calculos;
	}

	public Calculo addCalculo(Calculo calculo) {
		getCalculos().add(calculo);
		calculo.setExpressao(this);

		return calculo;
	}

	public Calculo removeCalculo(Calculo calculo) {
		getCalculos().remove(calculo);
		calculo.setExpressao(null);

		return calculo;
	}

}