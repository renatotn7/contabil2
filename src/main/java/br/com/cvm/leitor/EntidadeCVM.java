package br.com.cvm.leitor;
public class EntidadeCVM {
 String codigoCVM;
 String tipoDocumento;
 String dataDocto;
 String versaoDocto;
 String codigoDocumento;
 String protocolo;
 
 public EntidadeCVM(String protocolo) {
this.protocolo=protocolo;
codigoCVM= protocolo.substring(0, 6);
tipoDocumento = protocolo.substring(6, 9);
dataDocto=protocolo.substring(11, 17);
versaoDocto=protocolo.substring(17, 19);
codigoDocumento = Integer.parseInt(protocolo.substring(21, 27))+"";
 }
}