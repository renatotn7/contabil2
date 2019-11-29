package entities;

import java.util.ArrayList;
import java.util.List;

import br.com.cvm.bd.model.ContaContabil;

public class Divergencia {
 List<ContaComparada> naoExistencias;
 List<ContaComparada> diferentes;
 
 
 public List<ContaComparada> getNaoExistencia() {
		return this.naoExistencias;
	}

	public void setNaoExistencias(List<ContaComparada> naoExistencias) {
		this.naoExistencias = naoExistencias;
	}

	public ContaComparada addNaoExistencia(ContaComparada naoExistencia) {
		if(naoExistencias == null) {
			naoExistencias	= new ArrayList<ContaComparada> ();
		}
		getNaoExistencia().add(naoExistencia);
	

		return naoExistencia;
	}

	public ContaComparada removeNaoExistencia(ContaComparada naoExistencia) {
		if(naoExistencias == null) {
			naoExistencias	= new ArrayList<ContaComparada> ();
		}
		getNaoExistencia().remove(naoExistencia);
		

		return naoExistencia;
	}

	
	
	
	

	 public List<ContaComparada> getDiferentes() {
			return this.diferentes;
		}

		public void setDiferentes(List<ContaComparada> diferentes) {
			this.diferentes = diferentes;
		}

		public ContaComparada addDiferente(ContaComparada diferente) {
			if(diferentes == null) {
				diferentes	= new ArrayList<ContaComparada> ();
			}
			getDiferentes().add(diferente);
		

			return diferente;
		}

		public ContaComparada removeDiferente(ContaComparada diferente) {
			if(diferentes == null) {
				diferentes	= new ArrayList<ContaComparada> ();
			}
			getDiferentes().remove(diferente);
			

			return diferente;
		}
	

}
