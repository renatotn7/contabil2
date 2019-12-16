<<<<<<< HEAD
  function diff1( texto1, texto2){
     var str = "";
      var dmp = new diff_match_patch();
      var diff = dmp.diff_main( texto1, texto2);
      
      dmp.diff_cleanupSemantic(diff);
     
     var i=0;
     
      for(i = 0 ; i < diff.length;i++){
      if(diff[i][0]==0){
    	
    		   str+="<span class = 'rounded' style='background-color: #a1ff9c;  border-radius:5px 5px 5px 5px; padding-left:3px'>"	+ diff[i][1] + "</span>";
      } 
       if(diff[i][0]==-1){
    	
    		     //str+="<font color='#ff6961'>"	+ diff[i][1] + "</font>";
    	   str+="<span style='background-color: #f5ed8e;border-radius:5px 5px 5px 5px; padding-left:3px'>"	+ diff[i][1] + "</span>";
      } 
      if(diff[i][0]==1){
    	  		if(i==diff.length-1 ||i==diff.length-2 ){
    	  		   str+="<span style='background-color: #fac7cc;border-radius:5px 5px 5px 5px; padding-left:3px'>"	+ diff[i][1] + "</span>";

    	  		}
    		  //  str+="[<strike>"+diff[i][1]+"</strike>]" ;
      } 
      
      
      
     
      }
      return str;
      }
=======
/**
 * 
 */ 
(function() {
  var url = "http://localhost/cogg/includes/services/service_get.php";
  $.getJSON( url, {
    tags: "mount rainier",
    tagmode: "any",
    format: "json"
  })
    .done(function( data ) {
	//alert("data");
	var contacontabil = data.comparado;
	 $(  "#contaComparada").append( "<input type='hidden' id='contacomparada' name='contacomparada' data-jsondata='"+JSON.stringify(contacontabil)+"'>")
	  $(  "#contaComparada").append( "<br/><strong>raiz: </strong>"+data.raiz );
	  $(  "#contaComparada").append( "<br/><strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao  );
	
		  $(  "#contaComparada").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
		 $(  "#contaComparada").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
		 
		 var existehided = false;
		
		
      $.each( data.candidatos, function( i, item ) {
	//   $(  "#contaComparada").append( "#opcao"+i);
	 	 var contacontabil = item.conta;
	 
		 if(item.similaridade>70){
				 $(  "#opcoes").append( "<br/><br/><input type='radio' name='chooseofaccount' data-jsondata='"+JSON.stringify(contacontabil)+"' />");
		 		$(  "#opcoes").append( "<br/><strong>raiz: </strong>"+item.raiz +"<br/>"+diff1(item.raiz,data.raiz)+"<br/>");	 
				$(  "#opcoes").append( "<strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao + " - <br/>"+  diff1(contacontabil.descricao ,  data.comparado.descricao) );
			 
				  $( "#opcoes").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
				 $( "#opcoes").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
		       $( "#opcoes").append( "<br/><strong>similaridade: </strong>"+item.similaridade );
      }else{
      	 	$(  "#opcoeshided").append( "<br/><br/><input type='radio' name='chooseofaccount' data-jsondata='"+JSON.stringify(contacontabil)+"' />");
 			$(  "#opcoeshided").append( "<br/><strong>raiz: </strong>"+item.raiz +"<br/>");	 
	 		$(  "#opcoeshided").append( "<strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao  );

			  $( "#opcoeshided").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
			 $( "#opcoeshided").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
      		 $( "#opcoeshided").append( "<br/><strong>similaridade: </strong>"+item.similaridade );
      		 existehided=true;
      }
    
      });
       if(existehided){
         $("#opcoeshided").hide( "fold", {horizFirst: true }, 2000 );
          $( "#buttonshow").append( " <button onclick='trogglehided()' id='showhidedclick'>show</button> ");
      }
		 $(  "#nda").append( "<br/><br/><input type='radio' name='chooseofaccount'  />NDA<br/>");
	       $( "#button").append( " <button onclick='myFunction()'>Click me</button> ");
	       
	 
    }).then(data => {
    // Work with JSON data here
    console.log(data)
  });
})();
/*


(function() {
  var url = "http://localhost:8081/getMelhorCandidato";
  $.getJSON( url, {
    tags: "mount rainier",
    tagmode: "any",
    format: "json"
  })
    .done(function( data ) {
	//alert("data");
	var contacontabil = data.comparado;
	 $(  "#contaComparada").append( "<input type='hidden' id='contacomparada' name='contacomparada' data-jsondata='"+JSON.stringify(contacontabil)+"'>")
	  $(  "#contaComparada").append( "<br/><strong>raiz: </strong>"+data.raiz );
	  $(  "#contaComparada").append( "<br/><strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao  );
	
		  $(  "#contaComparada").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
		 $(  "#contaComparada").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
		 
		 var existehided = false;
		
		
      $.each( data.candidatos, function( i, item ) {
	//   $(  "#contaComparada").append( "#opcao"+i);
	 	 var contacontabil = item.conta;
	 
		 if(item.similaridade>70){
				 $(  "#opcoes").append( "<br/><br/><input type='radio' name='chooseofaccount' data-jsondata='"+JSON.stringify(contacontabil)+"' />");
		 		$(  "#opcoes").append( "<br/><strong>raiz: </strong>"+item.raiz +"<br/>"+diff1(item.raiz,data.raiz)+"<br/>");	 
				$(  "#opcoes").append( "<strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao + " - <br/>"+  diff1(contacontabil.descricao ,  data.comparado.descricao) );
			 
				  $( "#opcoes").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
				 $( "#opcoes").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
		       $( "#opcoes").append( "<br/><strong>similaridade: </strong>"+item.similaridade );
      }else{
      	 	$(  "#opcoeshided").append( "<br/><br/><input type='radio' name='chooseofaccount' data-jsondata='"+JSON.stringify(contacontabil)+"' />");
 			$(  "#opcoeshided").append( "<br/><strong>raiz: </strong>"+item.raiz +"<br/>");	 
	 		$(  "#opcoeshided").append( "<strong>Conta Contabil: </strong>"+contacontabil.contaContabil+": "+contacontabil.descricao  );

			  $( "#opcoeshided").append( "<br/><strong>Tipo Balanco: </strong>"+contacontabil.tipoDemonstrativo.descricaoTipo );
			 $( "#opcoeshided").append( "<br/><strong>Data: </strong>"+contacontabil.demonstrativo.data );
      		 $( "#opcoeshided").append( "<br/><strong>similaridade: </strong>"+item.similaridade );
      		 existehided=true;
      }
    
      });
       if(existehided){
         $("#opcoeshided").hide( "fold", {horizFirst: true }, 2000 );
          $( "#buttonshow").append( " <button onclick='trogglehided()' id='showhidedclick'>show</button> ");
      }
		 $(  "#nda").append( "<br/><br/><input type='radio' name='chooseofaccount'  />NDA<br/>");
	       $( "#button").append( " <button onclick='myFunction()'>Click me</button> ");
	       
	 
    }).then(data => {
    // Work with JSON data here
    console.log(data)
  });
})();*/
>>>>>>> 1b3cc95ac6ed742efad545ed04881aa5cb465025
