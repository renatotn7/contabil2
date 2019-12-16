var strContent = '';

window.onload = function(){
    var arrButtons = document.querySelectorAll('.btn-list.horizontal .item-list .btn');
    for(i = 0; i < arrButtons.length; i++){
        arrButtons[i].onclick = function(event){
            event.preventDefault();
            var btnSelected = event.target;
            selectComponent(btnSelected);
        }
    }
}

function updateCount(){
  var count = document.querySelector('.count-component');
  var arrContent = document.querySelectorAll('.btn-list.horizontal .selected');
  arrContent.length == 0 ? count.innerHTML = '' : arrContent.length == 1 ? count.innerHTML = arrContent.length + ' componente selecionado, ' : count.innerHTML = arrContent.length+' componentes selecionados, ';
}

function updateContent(){
    var DOMButtonDL = document.querySelector('.btn-download');
    var arrContent = document.querySelectorAll('.btn-list.horizontal .selected');
    strContent = '';    
    for(i = 0; i < arrContent.length; i++) { strContent += i != 0 ? " " + oContent[arrContent[i].getAttribute('data-value')] : oContent[arrContent[i].getAttribute('data-value')]; }

    if(strContent == ''){
        DOMButtonDL.setAttribute('href', 'javascript:void(0);');
        DOMButtonDL.removeAttribute('download');
        return;
    }

    DOMButtonDL.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(strContent));
    DOMButtonDL.setAttribute('download', 'bundle.js');
}

function selectComponent(p_comp){
    var strAttribute = p_comp.getAttribute('data-value');
    var strOperation = '';

    p_comp.classList.contains('selected') ? strOperation = 'remove' : strOperation = 'add';

    switch(strOperation){
        case 'remove':
          var htmlRemove = document.querySelector('.'+strAttribute);
          if(htmlRemove) htmlRemove.remove();
          p_comp.classList.remove('selected');
          var arrContent = document.querySelectorAll('.btn-list.horizontal .btn.selected');
          updateCount();
          updateContent();
        break;
        case 'add':
          var htmlImage = new Image();
          htmlImage.src = oDescription[strAttribute].path;
          htmlImage.onload = function(){
              var appendHTML = '<div class="description-container '+strAttribute+'"><p class="description-text">'+oDescription[strAttribute].text+'</p><img class="description-image" src="'+oDescription[strAttribute].path+'"><hr class="break-line"></div>'
              document.querySelector('.chosen').innerHTML += appendHTML;
              var arrContent = document.querySelectorAll('.btn.selected');
              updateCount();
          }
          p_comp.classList.add('selected');
          updateContent();
        break;
    }
}
