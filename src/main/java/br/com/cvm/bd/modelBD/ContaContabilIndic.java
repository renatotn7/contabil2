package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the conta_contabil_indic database table.
 * 
 */
@Entity
@Table(name="conta_contabil_indic")
@NamedQuery(name="ContaContabilIndic.findAll", query="SELECT c FROM ContaContabilIndic c")
public class ContaContabilIndic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_conta_contabil_indic")
	private int idContaContabilIndic;

	private int posicao;

	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="contaContabilIndic")
	private List<ContaContabil> contaContabils;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_conta_contabil")
	private ContaContabil contaContabil;

	//bi-directional many-to-one association to IndicadorRelac
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_indicador_relac")
	private IndicadorRelac indicadorRelac;

	public ContaContabilIndic() {
	}

	public int getIdContaContabilIndic() {
		return this.idContaContabilIndic;
	}

	public void setIdContaContabilIndic(int idContaContabilIndic) {
		this.idContaContabilIndic = idContaContabilIndic;
	}

	public int getPosicao() {
		return this.posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public List<ContaContabil> getContaContabils() {
		return this.contaContabils;
	}

	public void setContaContabils(List<ContaContabil> contaContabils) {
		this.contaContabils = contaContabils;
	}

	public ContaContabil addContaContabil(ContaContabil contaContabil) {
		getContaContabils().add(contaContabil);


		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		

		return contaContabil;
	}

	public ContaContabil getContaContabil() {
		return this.contaContabil;
	}

	public void setContaContabil(ContaContabil contaContabil) {
		this.contaContabil = contaContabil;
	}

	public IndicadorRelac getIndicadorRelac() {
		return this.indicadorRelac;
	}

	public void setIndicadorRelac(IndicadorRelac indicadorRelac) {
		this.indicadorRelac = indicadorRelac;
	}

}