package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the propsta_conf_indic_detalhe database table.
 * 
 */
@Entity
@Table(name="propsta_conf_indic_detalhe")
@NamedQuery(name="PropstaConfIndicDetalhe.findAll", query="SELECT p FROM PropstaConfIndicDetalhe p")
public class PropstaConfIndicDetalhe implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_propsta_conf_indic_detalhe")
	private int idPropstaConfIndicDetalhe;

	//bi-directional many-to-one association to PropstaConfIndicHeader
	@ManyToOne
	@JoinColumn(name="id_propsta_conf_indic_header")
	private PropstaConfIndicHeader propstaConfIndicHeader;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil1")
	private ContaContabil contaContabil1Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil2")
	private ContaContabil contaContabil2Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil3")
	private ContaContabil contaContabil3Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil4")
	private ContaContabil contaContabil4Bean;
	
	//bi-directional many-to-one association to Demonstrativo
		@ManyToOne
		@JoinColumn(name="ID_DEMONSTRATIVO")
		private Demonstrativo demonstrativo;
	public Demonstrativo getDemonstrativo() {
			return demonstrativo;
		}

		public void setDemonstrativo(Demonstrativo demonstrativo) {
			this.demonstrativo = demonstrativo;
		}

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil5")
	private ContaContabil contaContabil5Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil6")
	private ContaContabil contaContabil6Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil7")
	private ContaContabil contaContabil7Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil8")
	private ContaContabil contaContabil8Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil9")
	private ContaContabil contaContabil9Bean;

	//bi-directional many-to-one association to ContaContabil
	@ManyToOne
	@JoinColumn(name="conta_contabil10")
	private ContaContabil contaContabil10Bean;

	public PropstaConfIndicDetalhe() {
	}

	public int getIdPropstaConfIndicDetalhe() {
		return this.idPropstaConfIndicDetalhe;
	}

	public void setIdPropstaConfIndicDetalhe(int idPropstaConfIndicDetalhe) {
		this.idPropstaConfIndicDetalhe = idPropstaConfIndicDetalhe;
	}

	public PropstaConfIndicHeader getPropstaConfIndicHeader() {
		return this.propstaConfIndicHeader;
	}

	public void setPropstaConfIndicHeader(PropstaConfIndicHeader propstaConfIndicHeader) {
		this.propstaConfIndicHeader = propstaConfIndicHeader;
	}

	public ContaContabil getContaContabil1Bean() {
		return this.contaContabil1Bean;
	}

	public void setContaContabil1Bean(ContaContabil contaContabil1Bean) {
		this.contaContabil1Bean = contaContabil1Bean;
	}

	public ContaContabil getContaContabil2Bean() {
		return this.contaContabil2Bean;
	}

	public void setContaContabil2Bean(ContaContabil contaContabil2Bean) {
		this.contaContabil2Bean = contaContabil2Bean;
	}

	public ContaContabil getContaContabil3Bean() {
		return this.contaContabil3Bean;
	}

	public void setContaContabil3Bean(ContaContabil contaContabil3Bean) {
		this.contaContabil3Bean = contaContabil3Bean;
	}

	public ContaContabil getContaContabil4Bean() {
		return this.contaContabil4Bean;
	}

	public void setContaContabil4Bean(ContaContabil contaContabil4Bean) {
		this.contaContabil4Bean = contaContabil4Bean;
	}

	public ContaContabil getContaContabil5Bean() {
		return this.contaContabil5Bean;
	}

	public void setContaContabil5Bean(ContaContabil contaContabil5Bean) {
		this.contaContabil5Bean = contaContabil5Bean;
	}

	public ContaContabil getContaContabil6Bean() {
		return this.contaContabil6Bean;
	}

	public void setContaContabil6Bean(ContaContabil contaContabil6Bean) {
		this.contaContabil6Bean = contaContabil6Bean;
	}

	public ContaContabil getContaContabil7Bean() {
		return this.contaContabil7Bean;
	}

	public void setContaContabil7Bean(ContaContabil contaContabil7Bean) {
		this.contaContabil7Bean = contaContabil7Bean;
	}

	public ContaContabil getContaContabil8Bean() {
		return this.contaContabil8Bean;
	}

	public void setContaContabil8Bean(ContaContabil contaContabil8Bean) {
		this.contaContabil8Bean = contaContabil8Bean;
	}

	public ContaContabil getContaContabil9Bean() {
		return this.contaContabil9Bean;
	}

	public void setContaContabil9Bean(ContaContabil contaContabil9Bean) {
		this.contaContabil9Bean = contaContabil9Bean;
	}

	public ContaContabil getContaContabil10Bean() {
		return this.contaContabil10Bean;
	}

	public void setContaContabil10Bean(ContaContabil contaContabil10Bean) {
		this.contaContabil10Bean = contaContabil10Bean;
	}

}