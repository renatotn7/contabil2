$(document).ready(function(){

	// Menu Navigation
	function htmlEntities(str) {
	    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
	}
	$.each($("pre"), function() {
        var $this = $(this);
        $this.html(htmlEntities($this.html()));
    })
	

	// Menu Lateral 
	$("#btn-sub-1 a").addClass("selected");

	$(".submenu").parent().children("a").click(function() {
        $("html, body").animate({ scrollTop: $("body").offset().top }, 500);
    })

	$.each($("#submenu li"), function(i) {
		$("#btn-sub-"+(i+1)).click(function() {
	        $("html, body").animate({ scrollTop: $("#cnt-sub-"+(i+1)).offset().top -100 }, 500);
	    })
    })

	$(window).on('scroll', function() {
		$.each($("#submenu li"), function(i) {
			if (i==0) {
				if($(window).scrollTop() >= $("#cnt-sub-"+(i+1)).offset().top -300) {
					$.each($("#submenu .selected"), function() {
				        $(this).removeClass("selected")
				    })
					$("#btn-sub-"+(i+1)+" a").addClass("selected");
				}
			} else {
				if($(window).scrollTop() >= $("#cnt-sub-"+(i+1)).offset().top -102) {
					$.each($("#submenu .selected"), function() {
				        $(this).removeClass("selected")
				    })
					$("#btn-sub-"+(i+1)+" a").addClass("selected");
				}
			}
	    })
    });

    // Adicionar Abas
	// $("#tab-2").hide();
    $(".btn-add-tab").click(function() {
    	if ($(this).hasClass("added")) {
    		$(this).removeClass("added");
			$(this).text("Adicionar Aba")
			$("#tab-1").show();
			$("#tab-2").hide();
    	} else {
    		$(this).addClass("added");
			$(this).text("Remover Aba")
			$("#tab-1").hide();
			$("#tab-2").show();
    	}
    })

	// Desabilitar botões
	$(".code-btn-disabled").hide();
	$(".btn-disable-btn").click(function() {
		if ($(this).parent().parent().find(".bradseg-btn").is(":disabled")) {
			$(this).text("Desabilitar botão")
			$(this).parent().parent().find(".bradseg-btn").prop("disabled",false);
			$(this).parent().parent().find(".code-btn-enabled").show();
			$(this).parent().parent().find(".code-btn-disabled").hide();
			$(".btn-prev .bradseg-btn").parent().find("span").css("color", "#0b67bd");
			$(".btn-next .bradseg-btn").parent().find("span").css("color", "#ffffff");
		} else {
			$(this).text("Habilitar botão")
			$(this).parent().parent().find(".bradseg-btn").prop("disabled",true);
			$(this).parent().parent().find(".code-btn-enabled").hide();
			$(this).parent().parent().find(".code-btn-disabled").show();
			$(".btn-prev .bradseg-btn:disabled").parent().find("span").css("color", "#a5aab6");
			$(".btn-next .bradseg-btn:disabled").parent().find("span").css("color", "#a5aab6");
		}
	})

	// Simular erros
	$(".pre-code-error").hide();
	$(".btn-show-error").click(function() {
		if ($(this).text() == "Simular erro") {
			$(this).text("Remover erro")
			$(this).parent().parent().find(".pre-code-default").hide();
			$(this).parent().parent().find(".pre-code-error").show();
		} else {
			$(this).text("Simular erro")
			$(this).parent().parent().find(".pre-code-default").show();
			$(this).parent().parent().find(".pre-code-error").hide();
		}
		$(this).parent().parent().find(".form-group").toggleClass("error");


	})

	// Copy to clipboard
	new Clipboard('.copy-code');
})