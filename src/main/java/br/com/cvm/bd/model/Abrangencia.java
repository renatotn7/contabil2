package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the abrangencia database table.
 * 
 */

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="abrangencia")
@NamedQuery(name="Abrangencia.findAll", query="SELECT a FROM Abrangencia a")
public class Abrangencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="abrangencia_id", unique=true, nullable=false)
	private int abrangenciaId;

	@Column(nullable=false, length=255)
	private String descricao;

	@Column(name="sigla_abrangencia", nullable=false, length=3)
	private String siglaAbrangencia;
/*	@JsonIgnore
	//bi-directional many-to-one association to TipoDemonstrativo
	@OneToMany(mappedBy="abrangencia")
	private List<TipoDemonstrativo> tipoDemonstrativos;
*/
	public Abrangencia() {
	}

	public int getAbrangenciaId() {
		return this.abrangenciaId;
	}

	public void setAbrangenciaId(int abrangenciaId) {
		this.abrangenciaId = abrangenciaId;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSiglaAbrangencia() {
		return this.siglaAbrangencia;
	}

	public void setSiglaAbrangencia(String siglaAbrangencia) {
		this.siglaAbrangencia = siglaAbrangencia;
	}
/*
	public List<TipoDemonstrativo> getTipoDemonstrativos() {
		return this.tipoDemonstrativos;
	}

	public void setTipoDemonstrativos(List<TipoDemonstrativo> tipoDemonstrativos) {
		this.tipoDemonstrativos = tipoDemonstrativos;
	}
	public TipoDemonstrativo addTipoDemonstrativo(TipoDemonstrativo tipoDemonstrativo) {
		getTipoDemonstrativos().add(tipoDemonstrativo);
		tipoDemonstrativo.setAbrangencia(this);

		return tipoDemonstrativo;
	}

	public TipoDemonstrativo removeTipoDemonstrativo(TipoDemonstrativo tipoDemonstrativo) {
		getTipoDemonstrativos().remove(tipoDemonstrativo);
		tipoDemonstrativo.setAbrangencia(null);

		return tipoDemonstrativo;
	}
*/

}