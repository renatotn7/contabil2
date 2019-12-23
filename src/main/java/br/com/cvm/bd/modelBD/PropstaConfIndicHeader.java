package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the propsta_conf_indic_header database table.
 * 
 */
@Entity
@Table(name="propsta_conf_indic_header")
@NamedQuery(name="PropstaConfIndicHeader.findAll", query="SELECT p FROM PropstaConfIndicHeader p")
public class PropstaConfIndicHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_propsta_conf_indic_header")
	private int idPropstaConfIndicHeader;

	private String expressao;
	private String descricao;
	private int cvm;
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCvm() {
		return cvm;
	}

	public void setCvm(int cvm) {
		this.cvm = cvm;
	}

	@Column(name="qtd_colunas")
	private int qtdColunas;

	@Column(name="qtd_encontrada")
	private int qtdEncontrada;

	@Column(name="qtd_inicial")
	private int qtdInicial;

	//bi-directional many-to-one association to PropstaConfIndicDetalhe
	@OneToMany(mappedBy="propstaConfIndicHeader")
	private List<PropstaConfIndicDetalhe> propstaConfIndicDetalhes;

	//bi-directional many-to-one association to Demonstrativo
	@ManyToOne
	@JoinColumn(name="ID_DEMONSTRATIVO")
	private Demonstrativo demonstrativo;

	//bi-directional many-to-one association to Indicador
	@ManyToOne
	@JoinColumn(name="id_indicador")
	private Indicador indicador;

	public PropstaConfIndicHeader() {
	}

	public int getIdPropstaConfIndicHeader() {
		return this.idPropstaConfIndicHeader;
	}

	public void setIdPropstaConfIndicHeader(int idPropstaConfIndicHeader) {
		this.idPropstaConfIndicHeader = idPropstaConfIndicHeader;
	}

	public String getExpressao() {
		return this.expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public int getQtdColunas() {
		return this.qtdColunas;
	}

	public void setQtdColunas(int qtdColunas) {
		this.qtdColunas = qtdColunas;
	}

	public int getQtdEncontrada() {
		return this.qtdEncontrada;
	}

	public void setQtdEncontrada(int qtdEncontrada) {
		this.qtdEncontrada = qtdEncontrada;
	}

	public int getQtdInicial() {
		return this.qtdInicial;
	}

	public void setQtdInicial(int qtdInicial) {
		this.qtdInicial = qtdInicial;
	}

	public List<PropstaConfIndicDetalhe> getPropstaConfIndicDetalhes() {
		return this.propstaConfIndicDetalhes;
	}

	public void setPropstaConfIndicDetalhes(List<PropstaConfIndicDetalhe> propstaConfIndicDetalhes) {
		this.propstaConfIndicDetalhes = propstaConfIndicDetalhes;
	}

	public PropstaConfIndicDetalhe addPropstaConfIndicDetalhe(PropstaConfIndicDetalhe propstaConfIndicDetalhe) {
		getPropstaConfIndicDetalhes().add(propstaConfIndicDetalhe);
		propstaConfIndicDetalhe.setPropstaConfIndicHeader(this);

		return propstaConfIndicDetalhe;
	}

	public PropstaConfIndicDetalhe removePropstaConfIndicDetalhe(PropstaConfIndicDetalhe propstaConfIndicDetalhe) {
		getPropstaConfIndicDetalhes().remove(propstaConfIndicDetalhe);
		propstaConfIndicDetalhe.setPropstaConfIndicHeader(null);

		return propstaConfIndicDetalhe;
	} 

	public Demonstrativo getDemonstrativo() {
		return this.demonstrativo;
	}

	public void setDemonstrativo(Demonstrativo demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

}