package br.com.cvm.bd.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the empresa database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="empresa")
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int cvm;

	@Column(name="nome_pregao", nullable=false, length=50)
	private String nomePregao;

	@Column(name="raiz_ativo", length=8)
	private String raizAtivo;

	@Column(name="razao_social", nullable=false, length=255)
	private String razaoSocial;

	@Column(length=10)
	private String segmento;

	@Column(length=255)
	private String segmentosetorial;

	@Column(length=255)
	private String setor;

	private int situacao;

	@Column(length=255)
	private String subsetor;
	@JsonIgnore
	//bi-directional many-to-one association to Demonstrativo
	@OneToMany(mappedBy="empresa")
	private List<Demonstrativo> demonstrativos;

	public Empresa() {
	}

	public int getCvm() {
		return this.cvm;
	}

	public void setCvm(int cvm) {
		this.cvm = cvm;
	}

	public String getNomePregao() {
		return this.nomePregao;
	}

	public void setNomePregao(String nomePregao) {
		this.nomePregao = nomePregao;
	}

	public String getRaizAtivo() {
		return this.raizAtivo;
	}

	public void setRaizAtivo(String raizAtivo) {
		this.raizAtivo = raizAtivo;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getSegmento() {
		return this.segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getSegmentosetorial() {
		return this.segmentosetorial;
	}

	public void setSegmentosetorial(String segmentosetorial) {
		this.segmentosetorial = segmentosetorial;
	}

	public String getSetor() {
		return this.setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public int getSituacao() {
		return this.situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public String getSubsetor() {
		return this.subsetor;
	}

	public void setSubsetor(String subsetor) {
		this.subsetor = subsetor;
	}
	
	public List<Demonstrativo> getDemonstrativos() {
		return this.demonstrativos;
	}

	public void setDemonstrativos(List<Demonstrativo> demonstrativos) {
		this.demonstrativos = demonstrativos;
	}

	public Demonstrativo addDemonstrativo(Demonstrativo demonstrativo) {
		getDemonstrativos().add(demonstrativo);
		demonstrativo.setEmpresa(this);

		return demonstrativo;
	}

	public Demonstrativo removeDemonstrativo(Demonstrativo demonstrativo) {
		getDemonstrativos().remove(demonstrativo);
		demonstrativo.setEmpresa(null);

		return demonstrativo;
	}

}