package br.com.readsitecvm.utils;

import br.com.cvm.miner.DiagnosticoIndicadores;

public class TestaIndicador {
	public static void main(String[] args) {

		DiagnosticoIndicadores di = new DiagnosticoIndicadores();
	String resp=	di.getGraficoIndicadores(5258, 13, 2);
	String[] arr = resp.split("\n");

	//	 Object[][] retorno = new Object[valoresContabeis.size()][6];  
		 Object[][] retorno = new Object[arr.length][6];
			 for(int i = 0 ; i < arr.length; i++) {
				String val = arr[i].split(";")[1];
				 String sdata =  arr[i].split(";")[0].substring(0,4)+"-"+(arr[i].split(";")[0]+"").substring(4,6)+"-"+31;
				 retorno[i][0] = sdata;
				 retorno[i][1] = Double.parseDouble(val);
				 retorno[i][2] =  Double.parseDouble(val);
				 retorno[i][3] =  Double.parseDouble(val);
				 retorno[i][4] =  Double.parseDouble(val);
				 retorno[i][5] = 1;
						
			 }
			 

	}
}
