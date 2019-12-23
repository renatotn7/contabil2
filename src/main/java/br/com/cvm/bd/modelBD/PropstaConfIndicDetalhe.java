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

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil1")
	private ValorContabil valorContabil1Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil2")
	private ValorContabil valorContabil2Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil3")
	private ValorContabil valorContabil3Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil4")
	private ValorContabil valorContabil4Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil5")
	private ValorContabil valorContabil5Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil6")
	private ValorContabil valorContabil6Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil7")
	private ValorContabil valorContabil7Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil8")
	private ValorContabil valorContabil8Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil9")
	private ValorContabil valorContabil9Bean;

	//bi-directional many-to-one association to ValorContabil
	@ManyToOne
	@JoinColumn(name="valor_contabil10")
	private ValorContabil valorContabil10Bean;

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

	public ValorContabil getValorContabil1Bean() {
		return this.valorContabil1Bean;
	}

	public void setValorContabil1Bean(ValorContabil valorContabil1Bean) {
		this.valorContabil1Bean = valorContabil1Bean;
	}

	public ValorContabil getValorContabil2Bean() {
		return this.valorContabil2Bean;
	}

	public void setValorContabil2Bean(ValorContabil valorContabil2Bean) {
		this.valorContabil2Bean = valorContabil2Bean;
	}

	public ValorContabil getValorContabil3Bean() {
		return this.valorContabil3Bean;
	}

	public void setValorContabil3Bean(ValorContabil valorContabil3Bean) {
		this.valorContabil3Bean = valorContabil3Bean;
	}

	public ValorContabil getValorContabil4Bean() {
		return this.valorContabil4Bean;
	}

	public void setValorContabil4Bean(ValorContabil valorContabil4Bean) {
		this.valorContabil4Bean = valorContabil4Bean;
	}

	public ValorContabil getValorContabil5Bean() {
		return this.valorContabil5Bean;
	}

	public void setValorContabil5Bean(ValorContabil valorContabil5Bean) {
		this.valorContabil5Bean = valorContabil5Bean;
	}

	public ValorContabil getValorContabil6Bean() {
		return this.valorContabil6Bean;
	}

	public void setValorContabil6Bean(ValorContabil valorContabil6Bean) {
		this.valorContabil6Bean = valorContabil6Bean;
	}

	public ValorContabil getValorContabil7Bean() {
		return this.valorContabil7Bean;
	}

	public void setValorContabil7Bean(ValorContabil valorContabil7Bean) {
		this.valorContabil7Bean = valorContabil7Bean;
	}

	public ValorContabil getValorContabil8Bean() {
		return this.valorContabil8Bean;
	}

	public void setValorContabil8Bean(ValorContabil valorContabil8Bean) {
		this.valorContabil8Bean = valorContabil8Bean;
	}

	public ValorContabil getValorContabil9Bean() {
		return this.valorContabil9Bean;
	}

	public void setValorContabil9Bean(ValorContabil valorContabil9Bean) {
		this.valorContabil9Bean = valorContabil9Bean;
	}

	public ValorContabil getValorContabil10Bean() {
		return this.valorContabil10Bean;
	}

	public void setValorContabil10Bean(ValorContabil valorContabil10Bean) {
		this.valorContabil10Bean = valorContabil10Bean;
	}

}