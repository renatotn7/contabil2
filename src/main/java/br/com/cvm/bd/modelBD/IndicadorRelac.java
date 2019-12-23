package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the indicador_relac database table.
 * 
 */
@Entity
@Table(name="indicador_relac")
@NamedQuery(name="IndicadorRelac.findAll", query="SELECT i FROM IndicadorRelac i")
public class IndicadorRelac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_indicador_relac")
	private int idIndicadorRelac;

	private int colunas;

	private String expressao;

	private int numero;

	//bi-directional many-to-one association to ContaContabilIndic
	@OneToMany(mappedBy="indicadorRelac")
	private List<ContaContabilIndic> contaContabilIndics;

	//bi-directional many-to-one association to Indicador
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador")
	private Indicador indicador;

	public IndicadorRelac() {
	}

	public int getIdIndicadorRelac() {
		return this.idIndicadorRelac;
	}

	public void setIdIndicadorRelac(int idIndicadorRelac) {
		this.idIndicadorRelac = idIndicadorRelac;
	}

	public int getColunas() {
		return this.colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	public String getExpressao() {
		return this.expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<ContaContabilIndic> getContaContabilIndics() {
		return this.contaContabilIndics;
	}

	public void setContaContabilIndics(List<ContaContabilIndic> contaContabilIndics) {
		this.contaContabilIndics = contaContabilIndics;
	}

	public ContaContabilIndic addContaContabilIndic(ContaContabilIndic contaContabilIndic) {
		getContaContabilIndics().add(contaContabilIndic);
		contaContabilIndic.setIndicadorRelac(this);

		return contaContabilIndic;
	}

	public ContaContabilIndic removeContaContabilIndic(ContaContabilIndic contaContabilIndic) {
		getContaContabilIndics().remove(contaContabilIndic);
		contaContabilIndic.setIndicadorRelac(null);

		return contaContabilIndic;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

}