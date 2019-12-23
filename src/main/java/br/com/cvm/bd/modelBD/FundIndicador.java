package br.com.cvm.bd.modelBD;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fund_indicador database table.
 *  
 */  
@Entity
@Table(name="fund_indicador")
@NamedQuery(name="FundIndicador.findAll", query="SELECT f FROM FundIndicador f")
public class FundIndicador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_fund_indicador")
	private int idFundIndicador;

	private double caixa;

	private double capex;

	private double d_L_div_EBITDA;

	private double divida;

	private double ebitda;

	private double fcf;

	@Column(name="FCL_CAPEX")
	private double fclCapex;

	private double fco;

	private double lucro_Liq;

	private double mrg_Liq;

	private double pat_Liq;

	private double payout;

	private double prov;

	private double receita_Liq;

	private double res_Fin;

	private double roe;

	//bi-directional many-to-one association to Demonstrativo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DEMONSTRATIVO")
	private Demonstrativo demonstrativo;

	public FundIndicador() {
	}

	public int getIdFundIndicador() {
		return this.idFundIndicador;
	}

	public void setIdFundIndicador(int idFundIndicador) {
		this.idFundIndicador = idFundIndicador;
	}

	public double getCaixa() {
		return this.caixa;
	}

	public void setCaixa(double caixa) {
		this.caixa = caixa;
	}

	public double getCapex() {
		return this.capex;
	}

	public void setCapex(double capex) {
		this.capex = capex;
	}

	public double getD_L_div_EBITDA() {
		return this.d_L_div_EBITDA;
	}

	public void setD_L_div_EBITDA(double d_L_div_EBITDA) {
		this.d_L_div_EBITDA = d_L_div_EBITDA;
	}

	public double getDivida() {
		return this.divida;
	}

	public void setDivida(double divida) {
		this.divida = divida;
	}

	public double getEbitda() {
		return this.ebitda;
	}

	public void setEbitda(double ebitda) {
		this.ebitda = ebitda;
	}

	public double getFcf() {
		return this.fcf;
	}

	public void setFcf(double fcf) {
		this.fcf = fcf;
	}

	public double getFclCapex() {
		return this.fclCapex;
	}

	public void setFclCapex(double fclCapex) {
		this.fclCapex = fclCapex;
	}

	public double getFco() {
		return this.fco;
	}

	public void setFco(double fco) {
		this.fco = fco;
	}

	public double getLucro_Liq() {
		return this.lucro_Liq;
	}

	public void setLucro_Liq(double lucro_Liq) {
		this.lucro_Liq = lucro_Liq;
	}

	public double getMrg_Liq() {
		return this.mrg_Liq;
	}

	public void setMrg_Liq(double mrg_Liq) {
		this.mrg_Liq = mrg_Liq;
	}

	public double getPat_Liq() {
		return this.pat_Liq;
	}

	public void setPat_Liq(double pat_Liq) {
		this.pat_Liq = pat_Liq;
	}

	public double getPayout() {
		return this.payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public double getProv() {
		return this.prov;
	}

	public void setProv(double prov) {
		this.prov = prov;
	}

	public double getReceita_Liq() {
		return this.receita_Liq;
	}

	public void setReceita_Liq(double receita_Liq) {
		this.receita_Liq = receita_Liq;
	}

	public double getRes_Fin() {
		return this.res_Fin;
	}

	public void setRes_Fin(double res_Fin) {
		this.res_Fin = res_Fin;
	}

	public double getRoe() {
		return this.roe;
	}

	public void setRoe(double roe) {
		this.roe = roe;
	}

	public Demonstrativo getDemonstrativo() {
		return this.demonstrativo;
	}

	public void setDemonstrativo(Demonstrativo demonstrativo) {
		this.demonstrativo = demonstrativo;
	}

}