package br.com.readsitecvm.entidades;

public class EntidadeCVM {
 public String codigoCVM;
 public String tipoDocumento;
 public String dataDocto;
 public String versaoDocto;
 public String codigoDocumento;
 public String protocolo;
 
 public EntidadeCVM(String protocolo) {
	 this.protocolo=protocolo;
	 codigoCVM= protocolo.substring(0, 6);
	 tipoDocumento = protocolo.substring(6, 9);
	 dataDocto=protocolo.substring(11, 17);
	 versaoDocto=protocolo.substring(17, 19);
	 codigoDocumento = Integer.parseInt(protocolo.substring(21, 27))+"";
 }
}
