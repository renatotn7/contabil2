package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the tipo_demonstrativo database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="tipo_demonstrativo")
@NamedQuery(name="TipoDemonstrativo.findAll", query="SELECT t FROM TipoDemonstrativo t")
public class TipoDemonstrativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_tipo", unique=true, nullable=false)
	private int idTipo;

	@Column(name="descricao_tipo", nullable=false, length=256)
	private String descricaoTipo;

	@Column(name="sigla_tipo", nullable=false, length=30)
	private String siglaTipo;
	@JsonIgnore
	//bi-directional many-to-one association to ContaContabil
	@OneToMany(mappedBy="tipoDemonstrativo")
	private List<ContaContabil> contaContabils;

	//bi-directional many-to-one association to Abrangencia
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="abrangencia_id", nullable=false)
	private Abrangencia abrangencia;

	public TipoDemonstrativo() {
	}

	public int getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescricaoTipo() {
		return this.descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

	public String getSiglaTipo() {
		return this.siglaTipo;
	}

	public void setSiglaTipo(String siglaTipo) {
		this.siglaTipo = siglaTipo;
	}
	
	public List<ContaContabil> getContaContabils() {
		return this.contaContabils;
	}

	public void setContaContabils(List<ContaContabil> contaContabils) {
		this.contaContabils = contaContabils;
	}

	public ContaContabil addContaContabil(ContaContabil contaContabil) {
		getContaContabils().add(contaContabil);
		contaContabil.setTipoDemonstrativo(this);

		return contaContabil;
	}

	public ContaContabil removeContaContabil(ContaContabil contaContabil) {
		getContaContabils().remove(contaContabil);
		contaContabil.setTipoDemonstrativo(null);

		return contaContabil;
	}

	public Abrangencia getAbrangencia() {
		return this.abrangencia;
	}

	public void setAbrangencia(Abrangencia abrangencia) {
		this.abrangencia = abrangencia;
	}

}