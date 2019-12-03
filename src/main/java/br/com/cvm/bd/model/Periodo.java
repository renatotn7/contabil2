package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@Table(name="periodo")
@NamedQuery(name="Periodo.findAll", query="SELECT p FROM Periodo p")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_periodo", unique=true, nullable=false)
	private int idPeriodo;

	@Lob
	@Column(nullable=false)
	private String descricao;

	@Column(name="sigla_periodo", nullable=false, length=10)
	private String siglaPeriodo;

	//bi-directional many-to-one association to Demonstrativo
	@OneToMany(mappedBy="periodo")
	private List<Demonstrativo> demonstrativos;

	public Periodo() {
	}

	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSiglaPeriodo() {
		return this.siglaPeriodo;
	}

	public void setSiglaPeriodo(String siglaPeriodo) {
		this.siglaPeriodo = siglaPeriodo;
	}
	@JsonIgnore
	public List<Demonstrativo> getDemonstrativos() {
		return this.demonstrativos;
	}

	public void setDemonstrativos(List<Demonstrativo> demonstrativos) {
		this.demonstrativos = demonstrativos;
	}

	public Demonstrativo addDemonstrativo(Demonstrativo demonstrativo) {
		getDemonstrativos().add(demonstrativo);
		demonstrativo.setPeriodo(this);

		return demonstrativo;
	}

	public Demonstrativo removeDemonstrativo(Demonstrativo demonstrativo) {
		getDemonstrativos().remove(demonstrativo);
		demonstrativo.setPeriodo(null);

		return demonstrativo;
	}

}