package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the calculo database table.
 * 
 */
@Entity
@NamedQuery(name="Calculo.findAll", query="SELECT c FROM Calculo c")
public class Calculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_pk_calculo")
	private int idPkCalculo;

	@Column(name="id_calculo")
	private int idCalculo;

	private int posicao;

	private int preferencia;

	//bi-directional many-to-one association to Expressao
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_expressao")
	private Expressao expressao;

	//bi-directional many-to-one association to Indicador
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador")
	private Indicador indicador;

	public Calculo() {
	}

	public int getIdPkCalculo() {
		return this.idPkCalculo;
	}

	public void setIdPkCalculo(int idPkCalculo) {
		this.idPkCalculo = idPkCalculo;
	}

	public int getIdCalculo() {
		return this.idCalculo;
	}

	public void setIdCalculo(int idCalculo) {
		this.idCalculo = idCalculo;
	}

	public int getPosicao() {
		return this.posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public int getPreferencia() {
		return this.preferencia;
	}

	public void setPreferencia(int preferencia) {
		this.preferencia = preferencia;
	}

	public Expressao getExpressao() {
		return this.expressao;
	}

	public void setExpressao(Expressao expressao) {
		this.expressao = expressao;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

}