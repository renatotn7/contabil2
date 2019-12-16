/* --- MAIN --- */

const main = (($) => {
  const events = () =>
    $(() => {
      utils.setLocation("[data-href]", "data-href")

      utils.resetInputsEvent("[data-reset]", "data-reset")

      utils.setClassEvent({elements: "[data-tab-open]", removeSelf: false, theObjectAttr: {
        attrElement: "data-tab-open",
        attrObj: "data-tab",
        attrParent: "data-tab-group"
      }})

      range.init()
    })

  return {
    init() {
      events()
    }
  }
})(jQuery)

main.init()

/* --- */

/* --- UTILS --- */

const utils = (($) => {
  return {
    resetInputsEvent(element, attr, theEvent = 'click') {
      $(element).on(theEvent, e => {
        const resetParam = $(e.currentTarget).attr(attr)
          callResetInputs = !resetParam || this.resetInputs(resetParam)
      })
    },

    resetInputs(parameters) {
      const arrParameters = parameters.split(',');


      arrParameters.forEach(parameter => {
        const inputsName = `[name="${parameter.trim()}"]`,
          inputs = $(inputsName)

        if(inputs == undefined) return



        const arrChecks = $(`${inputsName}[type="checkbox"]`),
          arrRadios = $(`${inputsName}[type="radio"]`)


        const arrInputs = arrChecks.length > 0 ? arrChecks : arrRadios
        console.log(`[name="${parameter.trim()}"]`)



        arrInputs.prop( "checked", false )
      })
    },
    setClass(parameters) {
      const [ element, theClass, theEvent, removeSelf, openMultiples ] = parameters

      $(element).on(theEvent, e => {
        e.stopPropagation()

        const elActive = $(e.currentTarget).hasClass(theClass)
          , setClass = () => !elActive ? $(e.currentTarget).addClass(theClass) : null
          , removeClass = () => removeSelf ? $(e.currentTarget).removeClass(theClass) : null
          , removeClassAll = () => openMultiples ? $(e.currentTarget).siblings(element).removeClass(theClass) : null

        removeClassAll()
        removeClass()
        setClass()

        let clickElement = $(e.currentTarget);

        clickElement.find('input').length > 0 ? clickElement.find('input').prop('checked',true) : null;
        if(clickElement.hasClass('select-item')){ clickElement.trigger('select-change'); }

        $(document).ready(function(){
          var resizeEvent = window.document.createEvent('UIEvents');
          resizeEvent.initUIEvent('resize', true, false, window, 0);
          window.dispatchEvent(resizeEvent);
        })

      })
    },
    setClassObjectAttr(parameters) {
      const [ element, object, theClass, theEvent ] = parameters

      $(element).on(theEvent, e => {
        e.stopPropagation()

        const attrElement = object.attrElement
          , attrObj = object.attrObj
          , attrParent = object.attrParent
          , obj = $(e.currentTarget).attr(attrElement)
          , status = $(`[${attrElement}='${obj}']`).hasClass(theClass)

        $(`[${attrObj}='${obj}']`).closest(`[${attrParent}]`).find(`[${attrObj}]`).removeClass(theClass)
        $(`[${attrObj}='${obj}']`).addClass(theClass)
      })
    },
    setClassParent(parameters) {
      const [ element, parent, theClass, theEvent ] = parameters
      $(element).on(theEvent, e => {
        e.stopPropagation()

        const prActive = $(e.currentTarget).closest(parent).hasClass(theClass)
          , setClass = () => !prActive ? $(e.currentTarget).closest(parent).addClass(theClass) : null
        $(e.currentTarget).closest(parent).removeClass(theClass)

        setClass()
      })
    },
    setPropagation(parameters) {
      const [ element, theClass, theEvent ] = parameters

      $(document).on(theEvent, e => {
        $(element).removeClass(theClass)
      })
    },
    setClassEvent({elements, clickAway = false, theParent = false, theObjectAttr = false, removeSelf = true, theClass = 'active', theEvent = 'click', openMultiples = true}) {
      elements = typeof elements === 'array' ? elements : [elements]

      const setPropagation = parameters => clickAway ? this.setPropagation(parameters) : null
        , setClassParent = parameters => !theParent ? null : this.setClassParent(parameters)
        , setClassObjectAttr = parameters => !theObjectAttr ? null : this.setClassObjectAttr(parameters)
        , setClassEvent = (parameters, theParent) => {
            elements.forEach(element => {
              this.setClass([element, ...parameters, removeSelf, openMultiples])
              setClassParent([element, theParent, ...parameters])
              setClassObjectAttr([element, theObjectAttr, ...parameters])
              setPropagation([element, ...parameters])
            })
          }

        setClassEvent([theClass, theEvent], theParent)
    },
    setLocation(element, attr, theEvent = 'click') {
      $(element).on(theEvent, e => {
        const href = $(e.currentTarget).attr(attr)
          , setLocation = () => href == null ? null : window.location.href = href

        setLocation()
      })
    },
    checkForMissingImage(element, callback, onload = true) {
      let setCallback = el => callback(el)

      $(element).each((i, image) => {
        const $image = $(image)
          , src = $image.attr('src')
          , img = new Image()
          , callAction = () => !onload ?
            img.onerror = () => setCallback($image) :
            img.onload = () => setCallback($image)
          , checkImage = url => {
            callAction()
            img.src = url
          }
        checkImage(src)
      })
    }
  }
})(jQuery)

/* --- */
