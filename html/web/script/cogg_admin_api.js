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