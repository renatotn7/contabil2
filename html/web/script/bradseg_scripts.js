/*!
 * Bootstrap v3.3.7 (http://getbootstrap.com)
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under the MIT license
 */

if (typeof jQuery === 'undefined') {
  throw new Error('Bootstrap\'s JavaScript requires jQuery')
}

+function ($) {
  'use strict';
  var version = $.fn.jquery.split(' ')[0].split('.')
  if ((version[0] < 2 && version[1] < 9) || (version[0] == 1 && version[1] == 9 && version[2] < 1) || (version[0] > 3)) {
    throw new Error('Bootstrap\'s JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4')
  }
}(jQuery);

/* ========================================================================
 * Bootstrap: transition.js v3.3.7
 * http://getbootstrap.com/javascript/#transitions
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // CSS TRANSITION SUPPORT (Shoutout: http://www.modernizr.com/)
  // ============================================================

  function transitionEnd() {
    var el = document.createElement('bootstrap')

    var transEndEventNames = {
      WebkitTransition : 'webkitTransitionEnd',
      MozTransition    : 'transitionend',
      OTransition      : 'oTransitionEnd otransitionend',
      transition       : 'transitionend'
    }

    for (var name in transEndEventNames) {
      if (el.style[name] !== undefined) {
        return { end: transEndEventNames[name] }
      }
    }

    return false // explicit for ie8 (  ._.)
  }

  // http://blog.alexmaccaw.com/css-transitions
  $.fn.emulateTransitionEnd = function (duration) {
    var called = false
    var $el = this
    $(this).one('bsTransitionEnd', function () { called = true })
    var callback = function () { if (!called) $($el).trigger($.support.transition.end) }
    setTimeout(callback, duration)
    return this
  }

  $(function () {
    $.support.transition = transitionEnd()

    if (!$.support.transition) return

    $.event.special.bsTransitionEnd = {
      bindType: $.support.transition.end,
      delegateType: $.support.transition.end,
      handle: function (e) {
        if ($(e.target).is(this)) return e.handleObj.handler.apply(this, arguments)
      }
    }
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: alert.js v3.3.7
 * http://getbootstrap.com/javascript/#alerts
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // ALERT CLASS DEFINITION
  // ======================

  var dismiss = '[data-dismiss="alert"]'
  var Alert   = function (el) {
    $(el).on('click', dismiss, this.close)
  }

  Alert.VERSION = '3.3.7'

  Alert.TRANSITION_DURATION = 150

  Alert.prototype.close = function (e) {
    var $this    = $(this)
    var selector = $this.attr('data-target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    var $parent = $(selector === '#' ? [] : selector)

    if (e) e.preventDefault()

    if (!$parent.length) {
      $parent = $this.closest('.alert')
    }

    $parent.trigger(e = $.Event('close.bs.alert'))

    if (e.isDefaultPrevented()) return

    $parent.removeClass('in')

    function removeElement() {
      // detach from parent, fire event then clean up data
      $parent.detach().trigger('closed.bs.alert').remove()
    }

    $.support.transition && $parent.hasClass('fade') ?
      $parent
        .one('bsTransitionEnd', removeElement)
        .emulateTransitionEnd(Alert.TRANSITION_DURATION) :
      removeElement()
  }


  // ALERT PLUGIN DEFINITION
  // =======================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.alert')

      if (!data) $this.data('bs.alert', (data = new Alert(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }

  var old = $.fn.alert

  $.fn.alert             = Plugin
  $.fn.alert.Constructor = Alert


  // ALERT NO CONFLICT
  // =================

  $.fn.alert.noConflict = function () {
    $.fn.alert = old
    return this
  }


  // ALERT DATA-API
  // ==============

  $(document).on('click.bs.alert.data-api', dismiss, Alert.prototype.close)

}(jQuery);

/* ========================================================================
 * Bootstrap: button.js v3.3.7
 * http://getbootstrap.com/javascript/#buttons
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // BUTTON PUBLIC CLASS DEFINITION
  // ==============================

  var Button = function (element, options) {
    this.$element  = $(element)
    this.options   = $.extend({}, Button.DEFAULTS, options)
    this.isLoading = false
  }

  Button.VERSION  = '3.3.7'

  Button.DEFAULTS = {
    loadingText: 'loading...'
  }

  Button.prototype.setState = function (state) {
    var d    = 'disabled'
    var $el  = this.$element
    var val  = $el.is('input') ? 'val' : 'html'
    var data = $el.data()

    state += 'Text'

    if (data.resetText == null) $el.data('resetText', $el[val]())

    // push to event loop to allow forms to submit
    setTimeout($.proxy(function () {
      $el[val](data[state] == null ? this.options[state] : data[state])

      if (state == 'loadingText') {
        this.isLoading = true
        $el.addClass(d).attr(d, d).prop(d, true)
      } else if (this.isLoading) {
        this.isLoading = false
        $el.removeClass(d).removeAttr(d).prop(d, false)
      }
    }, this), 0)
  }

  Button.prototype.toggle = function () {
    var changed = true
    var $parent = this.$element.closest('[data-toggle="buttons"]')

    if ($parent.length) {
      var $input = this.$element.find('input')
      if ($input.prop('type') == 'radio') {
        if ($input.prop('checked')) changed = false
        $parent.find('.active').removeClass('active')
        this.$element.addClass('active')
      } else if ($input.prop('type') == 'checkbox') {
        if (($input.prop('checked')) !== this.$element.hasClass('active')) changed = false
        this.$element.toggleClass('active')
      }
      $input.prop('checked', this.$element.hasClass('active'))
      if (changed) $input.trigger('change')
    } else {
      this.$element.attr('aria-pressed', !this.$element.hasClass('active'))
      this.$element.toggleClass('active')
    }
  }


  // BUTTON PLUGIN DEFINITION
  // ========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.button')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.button', (data = new Button(this, options)))

      if (option == 'toggle') data.toggle()
      else if (option) data.setState(option)
    })
  }

  var old = $.fn.button

  $.fn.button             = Plugin
  $.fn.button.Constructor = Button


  // BUTTON NO CONFLICT
  // ==================

  $.fn.button.noConflict = function () {
    $.fn.button = old
    return this
  }


  // BUTTON DATA-API
  // ===============

  $(document)
    .on('click.bs.button.data-api', '[data-toggle^="button"]', function (e) {
      var $btn = $(e.target).closest('.btn')
      Plugin.call($btn, 'toggle')
      if (!($(e.target).is('input[type="radio"], input[type="checkbox"]'))) {
        // Prevent double click on radios, and the double selections (so cancellation) on checkboxes
        e.preventDefault()
        // The target component still receive the focus
        if ($btn.is('input,button')) $btn.trigger('focus')
        else $btn.find('input:visible,button:visible').first().trigger('focus')
      }
    })
    .on('focus.bs.button.data-api blur.bs.button.data-api', '[data-toggle^="button"]', function (e) {
      $(e.target).closest('.btn').toggleClass('focus', /^focus(in)?$/.test(e.type))
    })

}(jQuery);

/* ========================================================================
 * Bootstrap: carousel.js v3.3.7
 * http://getbootstrap.com/javascript/#carousel
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // CAROUSEL CLASS DEFINITION
  // =========================

  var Carousel = function (element, options) {
    this.$element    = $(element)
    this.$indicators = this.$element.find('.carousel-indicators')
    this.options     = options
    this.paused      = null
    this.sliding     = null
    this.interval    = null
    this.$active     = null
    this.$items      = null

    this.options.keyboard && this.$element.on('keydown.bs.carousel', $.proxy(this.keydown, this))

    this.options.pause == 'hover' && !('ontouchstart' in document.documentElement) && this.$element
      .on('mouseenter.bs.carousel', $.proxy(this.pause, this))
      .on('mouseleave.bs.carousel', $.proxy(this.cycle, this))
  }

  Carousel.VERSION  = '3.3.7'

  Carousel.TRANSITION_DURATION = 600

  Carousel.DEFAULTS = {
    interval: 5000,
    pause: 'hover',
    wrap: true,
    keyboard: true
  }

  Carousel.prototype.keydown = function (e) {
    if (/input|textarea/i.test(e.target.tagName)) return
    switch (e.which) {
      case 37: this.prev(); break
      case 39: this.next(); break
      default: return
    }

    e.preventDefault()
  }

  Carousel.prototype.cycle = function (e) {
    e || (this.paused = false)

    this.interval && clearInterval(this.interval)

    this.options.interval
      && !this.paused
      && (this.interval = setInterval($.proxy(this.next, this), this.options.interval))

    return this
  }

  Carousel.prototype.getItemIndex = function (item) {
    this.$items = item.parent().children('.item')
    return this.$items.index(item || this.$active)
  }

  Carousel.prototype.getItemForDirection = function (direction, active) {
    var activeIndex = this.getItemIndex(active)
    var willWrap = (direction == 'prev' && activeIndex === 0)
                || (direction == 'next' && activeIndex == (this.$items.length - 1))
    if (willWrap && !this.options.wrap) return active
    var delta = direction == 'prev' ? -1 : 1
    var itemIndex = (activeIndex + delta) % this.$items.length
    return this.$items.eq(itemIndex)
  }

  Carousel.prototype.to = function (pos) {
    var that        = this
    var activeIndex = this.getItemIndex(this.$active = this.$element.find('.item.active'))

    if (pos > (this.$items.length - 1) || pos < 0) return

    if (this.sliding)       return this.$element.one('slid.bs.carousel', function () { that.to(pos) }) // yes, "slid"
    if (activeIndex == pos) return this.pause().cycle()

    return this.slide(pos > activeIndex ? 'next' : 'prev', this.$items.eq(pos))
  }

  Carousel.prototype.pause = function (e) {
    e || (this.paused = true)

    if (this.$element.find('.next, .prev').length && $.support.transition) {
      this.$element.trigger($.support.transition.end)
      this.cycle(true)
    }

    this.interval = clearInterval(this.interval)

    return this
  }

  Carousel.prototype.next = function () {
    if (this.sliding) return
    return this.slide('next')
  }

  Carousel.prototype.prev = function () {
    if (this.sliding) return
    return this.slide('prev')
  }

  Carousel.prototype.slide = function (type, next) {
    var $active   = this.$element.find('.item.active')
    var $next     = next || this.getItemForDirection(type, $active)
    var isCycling = this.interval
    var direction = type == 'next' ? 'left' : 'right'
    var that      = this

    if ($next.hasClass('active')) return (this.sliding = false)

    var relatedTarget = $next[0]
    var slideEvent = $.Event('slide.bs.carousel', {
      relatedTarget: relatedTarget,
      direction: direction
    })
    this.$element.trigger(slideEvent)
    if (slideEvent.isDefaultPrevented()) return

    this.sliding = true

    isCycling && this.pause()

    if (this.$indicators.length) {
      this.$indicators.find('.active').removeClass('active')
      var $nextIndicator = $(this.$indicators.children()[this.getItemIndex($next)])
      $nextIndicator && $nextIndicator.addClass('active')
    }

    var slidEvent = $.Event('slid.bs.carousel', { relatedTarget: relatedTarget, direction: direction }) // yes, "slid"
    if ($.support.transition && this.$element.hasClass('slide')) {
      $next.addClass(type)
      $next[0].offsetWidth // force reflow
      $active.addClass(direction)
      $next.addClass(direction)
      $active
        .one('bsTransitionEnd', function () {
          $next.removeClass([type, direction].join(' ')).addClass('active')
          $active.removeClass(['active', direction].join(' '))
          that.sliding = false
          setTimeout(function () {
            that.$element.trigger(slidEvent)
          }, 0)
        })
        .emulateTransitionEnd(Carousel.TRANSITION_DURATION)
    } else {
      $active.removeClass('active')
      $next.addClass('active')
      this.sliding = false
      this.$element.trigger(slidEvent)
    }

    isCycling && this.cycle()

    return this
  }


  // CAROUSEL PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.carousel')
      var options = $.extend({}, Carousel.DEFAULTS, $this.data(), typeof option == 'object' && option)
      var action  = typeof option == 'string' ? option : options.slide

      if (!data) $this.data('bs.carousel', (data = new Carousel(this, options)))
      if (typeof option == 'number') data.to(option)
      else if (action) data[action]()
      else if (options.interval) data.pause().cycle()
    })
  }

  var old = $.fn.carousel

  $.fn.carousel             = Plugin
  $.fn.carousel.Constructor = Carousel


  // CAROUSEL NO CONFLICT
  // ====================

  $.fn.carousel.noConflict = function () {
    $.fn.carousel = old
    return this
  }


  // CAROUSEL DATA-API
  // =================

  var clickHandler = function (e) {
    var href
    var $this   = $(this)
    var $target = $($this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '')) // strip for ie7
    if (!$target.hasClass('carousel')) return
    var options = $.extend({}, $target.data(), $this.data())
    var slideIndex = $this.attr('data-slide-to')
    if (slideIndex) options.interval = false

    Plugin.call($target, options)

    if (slideIndex) {
      $target.data('bs.carousel').to(slideIndex)
    }

    e.preventDefault()
  }

  $(document)
    .on('click.bs.carousel.data-api', '[data-slide]', clickHandler)
    .on('click.bs.carousel.data-api', '[data-slide-to]', clickHandler)

  $(window).on('load', function () {
    $('[data-ride="carousel"]').each(function () {
      var $carousel = $(this)
      Plugin.call($carousel, $carousel.data())
    })
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: collapse.js v3.3.7
 * http://getbootstrap.com/javascript/#collapse
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */

/* jshint latedef: false */

+function ($) {
  'use strict';

  // COLLAPSE PUBLIC CLASS DEFINITION
  // ================================

  var Collapse = function (element, options) {
    this.$element      = $(element)
    this.options       = $.extend({}, Collapse.DEFAULTS, options)
    this.$trigger      = $('[data-toggle="collapse"][href="#' + element.id + '"],' +
                           '[data-toggle="collapse"][data-target="#' + element.id + '"]')
    this.transitioning = null

    if (this.options.parent) {
      this.$parent = this.getParent()
    } else {
      this.addAriaAndCollapsedClass(this.$element, this.$trigger)
    }

    if (this.options.toggle) this.toggle()
  }

  Collapse.VERSION  = '3.3.7'

  Collapse.TRANSITION_DURATION = 350

  Collapse.DEFAULTS = {
    toggle: true
  }

  Collapse.prototype.dimension = function () {
    var hasWidth = this.$element.hasClass('width')
    return hasWidth ? 'width' : 'height'
  }

  Collapse.prototype.show = function () {
    if (this.transitioning || this.$element.hasClass('in')) return

    var activesData
    var actives = this.$parent && this.$parent.children('.panel').children('.in, .collapsing')

    if (actives && actives.length) {
      activesData = actives.data('bs.collapse')
      if (activesData && activesData.transitioning) return
    }

    var startEvent = $.Event('show.bs.collapse')
    this.$element.trigger(startEvent)
    if (startEvent.isDefaultPrevented()) return

    if (actives && actives.length) {
      Plugin.call(actives, 'hide')
      activesData || actives.data('bs.collapse', null)
    }

    var dimension = this.dimension()

    this.$element
      .removeClass('collapse')
      .addClass('collapsing')[dimension](0)
      .attr('aria-expanded', true)

    this.$trigger
      .removeClass('collapsed')
      .attr('aria-expanded', true)

    this.transitioning = 1

    var complete = function () {
      this.$element
        .removeClass('collapsing')
        .addClass('collapse in')[dimension]('')
      this.transitioning = 0
      this.$element
        .trigger('shown.bs.collapse')
    }

    if (!$.support.transition) return complete.call(this)

    var scrollSize = $.camelCase(['scroll', dimension].join('-'))

    this.$element
      .one('bsTransitionEnd', $.proxy(complete, this))
      .emulateTransitionEnd(Collapse.TRANSITION_DURATION)[dimension](this.$element[0][scrollSize])
  }

  Collapse.prototype.hide = function () {
    if (this.transitioning || !this.$element.hasClass('in')) return

    var startEvent = $.Event('hide.bs.collapse')
    this.$element.trigger(startEvent)
    if (startEvent.isDefaultPrevented()) return

    var dimension = this.dimension()

    this.$element[dimension](this.$element[dimension]())[0].offsetHeight

    this.$element
      .addClass('collapsing')
      .removeClass('collapse in')
      .attr('aria-expanded', false)

    this.$trigger
      .addClass('collapsed')
      .attr('aria-expanded', false)

    this.transitioning = 1

    var complete = function () {
      this.transitioning = 0
      this.$element
        .removeClass('collapsing')
        .addClass('collapse')
        .trigger('hidden.bs.collapse')
    }

    if (!$.support.transition) return complete.call(this)

    this.$element
      [dimension](0)
      .one('bsTransitionEnd', $.proxy(complete, this))
      .emulateTransitionEnd(Collapse.TRANSITION_DURATION)
  }

  Collapse.prototype.toggle = function () {
    this[this.$element.hasClass('in') ? 'hide' : 'show']()
  }

  Collapse.prototype.getParent = function () {
    return $(this.options.parent)
      .find('[data-toggle="collapse"][data-parent="' + this.options.parent + '"]')
      .each($.proxy(function (i, element) {
        var $element = $(element)
        this.addAriaAndCollapsedClass(getTargetFromTrigger($element), $element)
      }, this))
      .end()
  }

  Collapse.prototype.addAriaAndCollapsedClass = function ($element, $trigger) {
    var isOpen = $element.hasClass('in')

    $element.attr('aria-expanded', isOpen)
    $trigger
      .toggleClass('collapsed', !isOpen)
      .attr('aria-expanded', isOpen)
  }

  function getTargetFromTrigger($trigger) {
    var href
    var target = $trigger.attr('data-target')
      || (href = $trigger.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '') // strip for ie7

    return $(target)
  }


  // COLLAPSE PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.collapse')
      var options = $.extend({}, Collapse.DEFAULTS, $this.data(), typeof option == 'object' && option)

      if (!data && options.toggle && /show|hide/.test(option)) options.toggle = false
      if (!data) $this.data('bs.collapse', (data = new Collapse(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.collapse

  $.fn.collapse             = Plugin
  $.fn.collapse.Constructor = Collapse


  // COLLAPSE NO CONFLICT
  // ====================

  $.fn.collapse.noConflict = function () {
    $.fn.collapse = old
    return this
  }


  // COLLAPSE DATA-API
  // =================

  $(document).on('click.bs.collapse.data-api', '[data-toggle="collapse"]', function (e) {
    var $this   = $(this)

    if (!$this.attr('data-target')) e.preventDefault()

    var $target = getTargetFromTrigger($this)
    var data    = $target.data('bs.collapse')
    var option  = data ? 'toggle' : $this.data()

    Plugin.call($target, option)
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: dropdown.js v3.3.7
 * http://getbootstrap.com/javascript/#dropdowns
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // DROPDOWN CLASS DEFINITION
  // =========================

  var backdrop = '.dropdown-backdrop'
  var toggle   = '[data-toggle="dropdown"]'
  var Dropdown = function (element) {
    $(element).on('click.bs.dropdown', this.toggle)
  }

  Dropdown.VERSION = '3.3.7'

  function getParent($this) {
    var selector = $this.attr('data-target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && /#[A-Za-z]/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    var $parent = selector && $(selector)

    return $parent && $parent.length ? $parent : $this.parent()
  }

  function clearMenus(e) {
    if (e && e.which === 3) return
    $(backdrop).remove()
    $(toggle).each(function () {
      var $this         = $(this)
      var $parent       = getParent($this)
      var relatedTarget = { relatedTarget: this }

      if (!$parent.hasClass('open')) return

      if (e && e.type == 'click' && /input|textarea/i.test(e.target.tagName) && $.contains($parent[0], e.target)) return

      $parent.trigger(e = $.Event('hide.bs.dropdown', relatedTarget))

      if (e.isDefaultPrevented()) return

      $this.attr('aria-expanded', 'false')
      $parent.removeClass('open').trigger($.Event('hidden.bs.dropdown', relatedTarget))
    })
  }

  Dropdown.prototype.toggle = function (e) {
    var $this = $(this)

    if ($this.is('.disabled, :disabled')) return

    var $parent  = getParent($this)
    var isActive = $parent.hasClass('open')

    clearMenus()

    if (!isActive) {
      if ('ontouchstart' in document.documentElement && !$parent.closest('.navbar-nav').length) {
        // if mobile we use a backdrop because click events don't delegate
        $(document.createElement('div'))
          .addClass('dropdown-backdrop')
          .insertAfter($(this))
          .on('click', clearMenus)
      }

      var relatedTarget = { relatedTarget: this }
      $parent.trigger(e = $.Event('show.bs.dropdown', relatedTarget))

      if (e.isDefaultPrevented()) return

      $this
        .trigger('focus')
        .attr('aria-expanded', 'true')

      $parent
        .toggleClass('open')
        .trigger($.Event('shown.bs.dropdown', relatedTarget))
    }

    return false
  }

  Dropdown.prototype.keydown = function (e) {
    if (!/(38|40|27|32)/.test(e.which) || /input|textarea/i.test(e.target.tagName)) return

    var $this = $(this)

    e.preventDefault()
    e.stopPropagation()

    if ($this.is('.disabled, :disabled')) return

    var $parent  = getParent($this)
    var isActive = $parent.hasClass('open')

    if (!isActive && e.which != 27 || isActive && e.which == 27) {
      if (e.which == 27) $parent.find(toggle).trigger('focus')
      return $this.trigger('click')
    }

    var desc = ' li:not(.disabled):visible a'
    var $items = $parent.find('.dropdown-menu' + desc)

    if (!$items.length) return

    var index = $items.index(e.target)

    if (e.which == 38 && index > 0)                 index--         // up
    if (e.which == 40 && index < $items.length - 1) index++         // down
    if (!~index)                                    index = 0

    $items.eq(index).trigger('focus')
  }


  // DROPDOWN PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.dropdown')

      if (!data) $this.data('bs.dropdown', (data = new Dropdown(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }

  var old = $.fn.dropdown

  $.fn.dropdown             = Plugin
  $.fn.dropdown.Constructor = Dropdown


  // DROPDOWN NO CONFLICT
  // ====================

  $.fn.dropdown.noConflict = function () {
    $.fn.dropdown = old
    return this
  }


  // APPLY TO STANDARD DROPDOWN ELEMENTS
  // ===================================

  $(document)
    .on('click.bs.dropdown.data-api', clearMenus)
    .on('click.bs.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
    .on('click.bs.dropdown.data-api', toggle, Dropdown.prototype.toggle)
    .on('keydown.bs.dropdown.data-api', toggle, Dropdown.prototype.keydown)
    .on('keydown.bs.dropdown.data-api', '.dropdown-menu', Dropdown.prototype.keydown)

}(jQuery);

/* ========================================================================
 * Bootstrap: modal.js v3.3.7
 * http://getbootstrap.com/javascript/#modals
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // MODAL CLASS DEFINITION
  // ======================

  var Modal = function (element, options) {
    this.options             = options
    this.$body               = $(document.body)
    this.$element            = $(element)
    this.$dialog             = this.$element.find('.modal-dialog')
    this.$backdrop           = null
    this.isShown             = null
    this.originalBodyPad     = null
    this.scrollbarWidth      = 0
    this.ignoreBackdropClick = false

    if (this.options.remote) {
      this.$element
        .find('.modal-content')
        .load(this.options.remote, $.proxy(function () {
          this.$element.trigger('loaded.bs.modal')
        }, this))
    }
  }

  Modal.VERSION  = '3.3.7'

  Modal.TRANSITION_DURATION = 300
  Modal.BACKDROP_TRANSITION_DURATION = 150

  Modal.DEFAULTS = {
    backdrop: true,
    keyboard: true,
    show: true
  }

  Modal.prototype.toggle = function (_relatedTarget) {
    return this.isShown ? this.hide() : this.show(_relatedTarget)
  }

  Modal.prototype.show = function (_relatedTarget) {
    var that = this
    var e    = $.Event('show.bs.modal', { relatedTarget: _relatedTarget })

    this.$element.trigger(e)

    if (this.isShown || e.isDefaultPrevented()) return

    this.isShown = true

    this.checkScrollbar()
    this.setScrollbar()
    this.$body.addClass('modal-open')

    this.escape()
    this.resize()

    this.$element.on('click.dismiss.bs.modal', '[data-dismiss="modal"]', $.proxy(this.hide, this))

    this.$dialog.on('mousedown.dismiss.bs.modal', function () {
      that.$element.one('mouseup.dismiss.bs.modal', function (e) {
        if ($(e.target).is(that.$element)) that.ignoreBackdropClick = true
      })
    })

    this.backdrop(function () {
      var transition = $.support.transition && that.$element.hasClass('fade')

      if (!that.$element.parent().length) {
        that.$element.appendTo(that.$body) // don't move modals dom position
      }

      that.$element
        .show()
        .scrollTop(0)

      that.adjustDialog()

      if (transition) {
        that.$element[0].offsetWidth // force reflow
      }

      that.$element.addClass('in')

      that.enforceFocus()

      var e = $.Event('shown.bs.modal', { relatedTarget: _relatedTarget })

      transition ?
        that.$dialog // wait for modal to slide in
          .one('bsTransitionEnd', function () {
            that.$element.trigger('focus').trigger(e)
          })
          .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
        that.$element.trigger('focus').trigger(e)
    })
  }

  Modal.prototype.hide = function (e) {
    if (e) e.preventDefault()

    e = $.Event('hide.bs.modal')

    this.$element.trigger(e)

    if (!this.isShown || e.isDefaultPrevented()) return

    this.isShown = false

    this.escape()
    this.resize()

    $(document).off('focusin.bs.modal')

    this.$element
      .removeClass('in')
      .off('click.dismiss.bs.modal')
      .off('mouseup.dismiss.bs.modal')

    this.$dialog.off('mousedown.dismiss.bs.modal')

    $.support.transition && this.$element.hasClass('fade') ?
      this.$element
        .one('bsTransitionEnd', $.proxy(this.hideModal, this))
        .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
      this.hideModal()
  }

  Modal.prototype.enforceFocus = function () {
    $(document)
      .off('focusin.bs.modal') // guard against infinite focus loop
      .on('focusin.bs.modal', $.proxy(function (e) {
        if (document !== e.target &&
            this.$element[0] !== e.target &&
            !this.$element.has(e.target).length) {
          this.$element.trigger('focus')
        }
      }, this))
  }

  Modal.prototype.escape = function () {
    if (this.isShown && this.options.keyboard) {
      this.$element.on('keydown.dismiss.bs.modal', $.proxy(function (e) {
        e.which == 27 && this.hide()
      }, this))
    } else if (!this.isShown) {
      this.$element.off('keydown.dismiss.bs.modal')
    }
  }

  Modal.prototype.resize = function () {
    if (this.isShown) {
      $(window).on('resize.bs.modal', $.proxy(this.handleUpdate, this))
    } else {
      $(window).off('resize.bs.modal')
    }
  }

  Modal.prototype.hideModal = function () {
    var that = this
    this.$element.hide()
    this.backdrop(function () {
      that.$body.removeClass('modal-open')
      that.resetAdjustments()
      that.resetScrollbar()
      that.$element.trigger('hidden.bs.modal')
    })
  }

  Modal.prototype.removeBackdrop = function () {
    this.$backdrop && this.$backdrop.remove()
    this.$backdrop = null
  }

  Modal.prototype.backdrop = function (callback) {
    var that = this
    var animate = this.$element.hasClass('fade') ? 'fade' : ''

    if (this.isShown && this.options.backdrop) {
      var doAnimate = $.support.transition && animate

      this.$backdrop = $(document.createElement('div'))
        .addClass('modal-backdrop ' + animate)
        .appendTo(this.$body)

      this.$element.on('click.dismiss.bs.modal', $.proxy(function (e) {
        if (this.ignoreBackdropClick) {
          this.ignoreBackdropClick = false
          return
        }
        if (e.target !== e.currentTarget) return
        this.options.backdrop == 'static'
          ? this.$element[0].focus()
          : this.hide()
      }, this))

      if (doAnimate) this.$backdrop[0].offsetWidth // force reflow

      this.$backdrop.addClass('in')

      if (!callback) return

      doAnimate ?
        this.$backdrop
          .one('bsTransitionEnd', callback)
          .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
        callback()

    } else if (!this.isShown && this.$backdrop) {
      this.$backdrop.removeClass('in')

      var callbackRemove = function () {
        that.removeBackdrop()
        callback && callback()
      }
      $.support.transition && this.$element.hasClass('fade') ?
        this.$backdrop
          .one('bsTransitionEnd', callbackRemove)
          .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
        callbackRemove()

    } else if (callback) {
      callback()
    }
  }

  // these following methods are used to handle overflowing modals

  Modal.prototype.handleUpdate = function () {
    this.adjustDialog()
  }

  Modal.prototype.adjustDialog = function () {
    var modalIsOverflowing = this.$element[0].scrollHeight > document.documentElement.clientHeight

    this.$element.css({
      paddingLeft:  !this.bodyIsOverflowing && modalIsOverflowing ? this.scrollbarWidth : '',
      paddingRight: this.bodyIsOverflowing && !modalIsOverflowing ? this.scrollbarWidth : ''
    })
  }

  Modal.prototype.resetAdjustments = function () {
    this.$element.css({
      paddingLeft: '',
      paddingRight: ''
    })
  }

  Modal.prototype.checkScrollbar = function () {
    var fullWindowWidth = window.innerWidth
    if (!fullWindowWidth) { // workaround for missing window.innerWidth in IE8
      var documentElementRect = document.documentElement.getBoundingClientRect()
      fullWindowWidth = documentElementRect.right - Math.abs(documentElementRect.left)
    }
    this.bodyIsOverflowing = document.body.clientWidth < fullWindowWidth
    this.scrollbarWidth = this.measureScrollbar()
  }

  Modal.prototype.setScrollbar = function () {
    var bodyPad = parseInt((this.$body.css('padding-right') || 0), 10)
    this.originalBodyPad = document.body.style.paddingRight || ''
    if (this.bodyIsOverflowing) this.$body.css('padding-right', bodyPad + this.scrollbarWidth)
  }

  Modal.prototype.resetScrollbar = function () {
    this.$body.css('padding-right', this.originalBodyPad)
  }

  Modal.prototype.measureScrollbar = function () { // thx walsh
    var scrollDiv = document.createElement('div')
    scrollDiv.className = 'modal-scrollbar-measure'
    this.$body.append(scrollDiv)
    var scrollbarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth
    this.$body[0].removeChild(scrollDiv)
    return scrollbarWidth
  }


  // MODAL PLUGIN DEFINITION
  // =======================

  function Plugin(option, _relatedTarget) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.modal')
      var options = $.extend({}, Modal.DEFAULTS, $this.data(), typeof option == 'object' && option)

      if (!data) $this.data('bs.modal', (data = new Modal(this, options)))
      if (typeof option == 'string') data[option](_relatedTarget)
      else if (options.show) data.show(_relatedTarget)
    })
  }

  var old = $.fn.modal

  $.fn.modal             = Plugin
  $.fn.modal.Constructor = Modal


  // MODAL NO CONFLICT
  // =================

  $.fn.modal.noConflict = function () {
    $.fn.modal = old
    return this
  }


  // MODAL DATA-API
  // ==============

  $(document).on('click.bs.modal.data-api', '[data-toggle="modal"]', function (e) {
    var $this   = $(this)
    var href    = $this.attr('href')
    var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))) // strip for ie7
    var option  = $target.data('bs.modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data())

    if ($this.is('a')) e.preventDefault()

    $target.one('show.bs.modal', function (showEvent) {
      if (showEvent.isDefaultPrevented()) return // only register focus restorer if modal will actually get shown
      $target.one('hidden.bs.modal', function () {
        $this.is(':visible') && $this.trigger('focus')
      })
    })
    Plugin.call($target, option, this)
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: tooltip.js v3.3.7
 * http://getbootstrap.com/javascript/#tooltip
 * Inspired by the original jQuery.tipsy by Jason Frame
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // TOOLTIP PUBLIC CLASS DEFINITION
  // ===============================

  var Tooltip = function (element, options) {
    this.type       = null
    this.options    = null
    this.enabled    = null
    this.timeout    = null
    this.hoverState = null
    this.$element   = null
    this.inState    = null

    this.init('tooltip', element, options)
  }

  Tooltip.VERSION  = '3.3.7'

  Tooltip.TRANSITION_DURATION = 150

  Tooltip.DEFAULTS = {
    animation: true,
    placement: 'top',
    selector: false,
    template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
    trigger: 'hover focus',
    title: '',
    delay: 0,
    html: false,
    container: false,
    viewport: {
      selector: 'body',
      padding: 0
    }
  }

  Tooltip.prototype.init = function (type, element, options) {
    this.enabled   = true
    this.type      = type
    this.$element  = $(element)
    this.options   = this.getOptions(options)
    this.$viewport = this.options.viewport && $($.isFunction(this.options.viewport) ? this.options.viewport.call(this, this.$element) : (this.options.viewport.selector || this.options.viewport))
    this.inState   = { click: false, hover: false, focus: false }

    if (this.$element[0] instanceof document.constructor && !this.options.selector) {
      throw new Error('`selector` option must be specified when initializing ' + this.type + ' on the window.document object!')
    }

    var triggers = this.options.trigger.split(' ')

    for (var i = triggers.length; i--;) {
      var trigger = triggers[i]

      if (trigger == 'click') {
        this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
      } else if (trigger != 'manual') {
        var eventIn  = trigger == 'hover' ? 'mouseenter' : 'focusin'
        var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout'

        this.$element.on(eventIn  + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
        this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
      }
    }

    this.options.selector ?
      (this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
      this.fixTitle()
  }

  Tooltip.prototype.getDefaults = function () {
    return Tooltip.DEFAULTS
  }

  Tooltip.prototype.getOptions = function (options) {
    options = $.extend({}, this.getDefaults(), this.$element.data(), options)

    if (options.delay && typeof options.delay == 'number') {
      options.delay = {
        show: options.delay,
        hide: options.delay
      }
    }

    return options
  }

  Tooltip.prototype.getDelegateOptions = function () {
    var options  = {}
    var defaults = this.getDefaults()

    this._options && $.each(this._options, function (key, value) {
      if (defaults[key] != value) options[key] = value
    })

    return options
  }

  Tooltip.prototype.enter = function (obj) {
    var self = obj instanceof this.constructor ?
      obj : $(obj.currentTarget).data('bs.' + this.type)

    if (!self) {
      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
      $(obj.currentTarget).data('bs.' + this.type, self)
    }

    if (obj instanceof $.Event) {
      self.inState[obj.type == 'focusin' ? 'focus' : 'hover'] = true
    }

    if (self.tip().hasClass('in') || self.hoverState == 'in') {
      self.hoverState = 'in'
      return
    }

    clearTimeout(self.timeout)

    self.hoverState = 'in'

    if (!self.options.delay || !self.options.delay.show) return self.show()

    self.timeout = setTimeout(function () {
      if (self.hoverState == 'in') self.show()
    }, self.options.delay.show)
  }

  Tooltip.prototype.isInStateTrue = function () {
    for (var key in this.inState) {
      if (this.inState[key]) return true
    }

    return false
  }

  Tooltip.prototype.leave = function (obj) {
    var self = obj instanceof this.constructor ?
      obj : $(obj.currentTarget).data('bs.' + this.type)

    if (!self) {
      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
      $(obj.currentTarget).data('bs.' + this.type, self)
    }

    if (obj instanceof $.Event) {
      self.inState[obj.type == 'focusout' ? 'focus' : 'hover'] = false
    }

    if (self.isInStateTrue()) return

    clearTimeout(self.timeout)

    self.hoverState = 'out'

    if (!self.options.delay || !self.options.delay.hide) return self.hide()

    self.timeout = setTimeout(function () {
      if (self.hoverState == 'out') self.hide()
    }, self.options.delay.hide)
  }

  Tooltip.prototype.show = function () {
    var e = $.Event('show.bs.' + this.type)

    if (this.hasContent() && this.enabled) {
      this.$element.trigger(e)

      var inDom = $.contains(this.$element[0].ownerDocument.documentElement, this.$element[0])
      if (e.isDefaultPrevented() || !inDom) return
      var that = this

      var $tip = this.tip()

      var tipId = this.getUID(this.type)

      this.setContent()
      $tip.attr('id', tipId)
      this.$element.attr('aria-describedby', tipId)

      if (this.options.animation) $tip.addClass('fade')

      var placement = typeof this.options.placement == 'function' ?
        this.options.placement.call(this, $tip[0], this.$element[0]) :
        this.options.placement

      var autoToken = /\s?auto?\s?/i
      var autoPlace = autoToken.test(placement)
      if (autoPlace) placement = placement.replace(autoToken, '') || 'top'

      $tip
        .detach()
        .css({ top: 0, left: 0, display: 'block' })
        .addClass(placement)
        .data('bs.' + this.type, this)

      this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element)
      this.$element.trigger('inserted.bs.' + this.type)

      var pos          = this.getPosition()
      var actualWidth  = $tip[0].offsetWidth
      var actualHeight = $tip[0].offsetHeight

      if (autoPlace) {
        var orgPlacement = placement
        var viewportDim = this.getPosition(this.$viewport)

        placement = placement == 'bottom' && pos.bottom + actualHeight > viewportDim.bottom ? 'top'    :
                    placement == 'top'    && pos.top    - actualHeight < viewportDim.top    ? 'bottom' :
                    placement == 'right'  && pos.right  + actualWidth  > viewportDim.width  ? 'left'   :
                    placement == 'left'   && pos.left   - actualWidth  < viewportDim.left   ? 'right'  :
                    placement

        $tip
          .removeClass(orgPlacement)
          .addClass(placement)
      }

      var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight)

      this.applyPlacement(calculatedOffset, placement)

      var complete = function () {
        var prevHoverState = that.hoverState
        that.$element.trigger('shown.bs.' + that.type)
        that.hoverState = null

        if (prevHoverState == 'out') that.leave(that)
      }

      $.support.transition && this.$tip.hasClass('fade') ?
        $tip
          .one('bsTransitionEnd', complete)
          .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
        complete()
    }
  }

  Tooltip.prototype.applyPlacement = function (offset, placement) {
    var $tip   = this.tip()
    var width  = $tip[0].offsetWidth
    var height = $tip[0].offsetHeight

    // manually read margins because getBoundingClientRect includes difference
    var marginTop = parseInt($tip.css('margin-top'), 10)
    var marginLeft = parseInt($tip.css('margin-left'), 10)

    // we must check for NaN for ie 8/9
    if (isNaN(marginTop))  marginTop  = 0
    if (isNaN(marginLeft)) marginLeft = 0

    offset.top  += marginTop
    offset.left += marginLeft

    // $.fn.offset doesn't round pixel values
    // so we use setOffset directly with our own function B-0
    $.offset.setOffset($tip[0], $.extend({
      using: function (props) {
        $tip.css({
          top: Math.round(props.top),
          left: Math.round(props.left)
        })
      }
    }, offset), 0)

    $tip.addClass('in')

    // check to see if placing tip in new offset caused the tip to resize itself
    var actualWidth  = $tip[0].offsetWidth
    var actualHeight = $tip[0].offsetHeight

    if (placement == 'top' && actualHeight != height) {
      offset.top = offset.top + height - actualHeight
    }

    var delta = this.getViewportAdjustedDelta(placement, offset, actualWidth, actualHeight)

    if (delta.left) offset.left += delta.left
    else offset.top += delta.top

    var isVertical          = /top|bottom/.test(placement)
    var arrowDelta          = isVertical ? delta.left * 2 - width + actualWidth : delta.top * 2 - height + actualHeight
    var arrowOffsetPosition = isVertical ? 'offsetWidth' : 'offsetHeight'

    $tip.offset(offset)
    this.replaceArrow(arrowDelta, $tip[0][arrowOffsetPosition], isVertical)
  }

  Tooltip.prototype.replaceArrow = function (delta, dimension, isVertical) {
    this.arrow()
      .css(isVertical ? 'left' : 'top', 50 * (1 - delta / dimension) + '%')
      .css(isVertical ? 'top' : 'left', '')
  }

  Tooltip.prototype.setContent = function () {
    var $tip  = this.tip()
    var title = this.getTitle()

    $tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
    $tip.removeClass('fade in top bottom left right')
  }

  Tooltip.prototype.hide = function (callback) {
    var that = this
    var $tip = $(this.$tip)
    var e    = $.Event('hide.bs.' + this.type)

    function complete() {
      if (that.hoverState != 'in') $tip.detach()
      if (that.$element) { // TODO: Check whether guarding this code with this `if` is really necessary.
        that.$element
          .removeAttr('aria-describedby')
          .trigger('hidden.bs.' + that.type)
      }
      callback && callback()
    }

    this.$element.trigger(e)

    if (e.isDefaultPrevented()) return

    $tip.removeClass('in')

    $.support.transition && $tip.hasClass('fade') ?
      $tip
        .one('bsTransitionEnd', complete)
        .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
      complete()

    this.hoverState = null

    return this
  }

  Tooltip.prototype.fixTitle = function () {
    var $e = this.$element
    if ($e.attr('title') || typeof $e.attr('data-original-title') != 'string') {
      $e.attr('data-original-title', $e.attr('title') || '').attr('title', '')
    }
  }

  Tooltip.prototype.hasContent = function () {
    return this.getTitle()
  }

  Tooltip.prototype.getPosition = function ($element) {
    $element   = $element || this.$element

    var el     = $element[0]
    var isBody = el.tagName == 'BODY'

    var elRect    = el.getBoundingClientRect()
    if (elRect.width == null) {
      // width and height are missing in IE8, so compute them manually; see https://github.com/twbs/bootstrap/issues/14093
      elRect = $.extend({}, elRect, { width: elRect.right - elRect.left, height: elRect.bottom - elRect.top })
    }
    var isSvg = window.SVGElement && el instanceof window.SVGElement
    // Avoid using $.offset() on SVGs since it gives incorrect results in jQuery 3.
    // See https://github.com/twbs/bootstrap/issues/20280
    var elOffset  = isBody ? { top: 0, left: 0 } : (isSvg ? null : $element.offset())
    var scroll    = { scroll: isBody ? document.documentElement.scrollTop || document.body.scrollTop : $element.scrollTop() }
    var outerDims = isBody ? { width: $(window).width(), height: $(window).height() } : null

    return $.extend({}, elRect, scroll, outerDims, elOffset)
  }

  Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
    return placement == 'bottom' ? { top: pos.top + pos.height,   left: pos.left + pos.width / 2 - actualWidth / 2 } :
           placement == 'top'    ? { top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2 } :
           placement == 'left'   ? { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth } :
        /* placement == 'right' */ { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width }

  }

  Tooltip.prototype.getViewportAdjustedDelta = function (placement, pos, actualWidth, actualHeight) {
    var delta = { top: 0, left: 0 }
    if (!this.$viewport) return delta

    var viewportPadding = this.options.viewport && this.options.viewport.padding || 0
    var viewportDimensions = this.getPosition(this.$viewport)

    if (/right|left/.test(placement)) {
      var topEdgeOffset    = pos.top - viewportPadding - viewportDimensions.scroll
      var bottomEdgeOffset = pos.top + viewportPadding - viewportDimensions.scroll + actualHeight
      if (topEdgeOffset < viewportDimensions.top) { // top overflow
        delta.top = viewportDimensions.top - topEdgeOffset
      } else if (bottomEdgeOffset > viewportDimensions.top + viewportDimensions.height) { // bottom overflow
        delta.top = viewportDimensions.top + viewportDimensions.height - bottomEdgeOffset
      }
    } else {
      var leftEdgeOffset  = pos.left - viewportPadding
      var rightEdgeOffset = pos.left + viewportPadding + actualWidth
      if (leftEdgeOffset < viewportDimensions.left) { // left overflow
        delta.left = viewportDimensions.left - leftEdgeOffset
      } else if (rightEdgeOffset > viewportDimensions.right) { // right overflow
        delta.left = viewportDimensions.left + viewportDimensions.width - rightEdgeOffset
      }
    }

    return delta
  }

  Tooltip.prototype.getTitle = function () {
    var title
    var $e = this.$element
    var o  = this.options

    title = $e.attr('data-original-title')
      || (typeof o.title == 'function' ? o.title.call($e[0]) :  o.title)

    return title
  }

  Tooltip.prototype.getUID = function (prefix) {
    do prefix += ~~(Math.random() * 1000000)
    while (document.getElementById(prefix))
    return prefix
  }

  Tooltip.prototype.tip = function () {
    if (!this.$tip) {
      this.$tip = $(this.options.template)
      if (this.$tip.length != 1) {
        throw new Error(this.type + ' `template` option must consist of exactly 1 top-level element!')
      }
    }
    return this.$tip
  }

  Tooltip.prototype.arrow = function () {
    return (this.$arrow = this.$arrow || this.tip().find('.tooltip-arrow'))
  }

  Tooltip.prototype.enable = function () {
    this.enabled = true
  }

  Tooltip.prototype.disable = function () {
    this.enabled = false
  }

  Tooltip.prototype.toggleEnabled = function () {
    this.enabled = !this.enabled
  }

  Tooltip.prototype.toggle = function (e) {
    var self = this
    if (e) {
      self = $(e.currentTarget).data('bs.' + this.type)
      if (!self) {
        self = new this.constructor(e.currentTarget, this.getDelegateOptions())
        $(e.currentTarget).data('bs.' + this.type, self)
      }
    }

    if (e) {
      self.inState.click = !self.inState.click
      if (self.isInStateTrue()) self.enter(self)
      else self.leave(self)
    } else {
      self.tip().hasClass('in') ? self.leave(self) : self.enter(self)
    }
  }

  Tooltip.prototype.destroy = function () {
    var that = this
    clearTimeout(this.timeout)
    this.hide(function () {
      that.$element.off('.' + that.type).removeData('bs.' + that.type)
      if (that.$tip) {
        that.$tip.detach()
      }
      that.$tip = null
      that.$arrow = null
      that.$viewport = null
      that.$element = null
    })
  }


  // TOOLTIP PLUGIN DEFINITION
  // =========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.tooltip')
      var options = typeof option == 'object' && option

      if (!data && /destroy|hide/.test(option)) return
      if (!data) $this.data('bs.tooltip', (data = new Tooltip(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.tooltip

  $.fn.tooltip             = Plugin
  $.fn.tooltip.Constructor = Tooltip


  // TOOLTIP NO CONFLICT
  // ===================

  $.fn.tooltip.noConflict = function () {
    $.fn.tooltip = old
    return this
  }

}(jQuery);

/* ========================================================================
 * Bootstrap: popover.js v3.3.7
 * http://getbootstrap.com/javascript/#popovers
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // POPOVER PUBLIC CLASS DEFINITION
  // ===============================

  var Popover = function (element, options) {
    this.init('popover', element, options)
  }

  if (!$.fn.tooltip) throw new Error('Popover requires tooltip.js')

  Popover.VERSION  = '3.3.7'

  Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
    placement: 'right',
    trigger: 'click',
    content: '',
    template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
  })


  // NOTE: POPOVER EXTENDS tooltip.js
  // ================================

  Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype)

  Popover.prototype.constructor = Popover

  Popover.prototype.getDefaults = function () {
    return Popover.DEFAULTS
  }

  Popover.prototype.setContent = function () {
    var $tip    = this.tip()
    var title   = this.getTitle()
    var content = this.getContent()

    $tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
    $tip.find('.popover-content').children().detach().end()[ // we use append for html objects to maintain js events
      this.options.html ? (typeof content == 'string' ? 'html' : 'append') : 'text'
    ](content)

    $tip.removeClass('fade top bottom left right in')

    // IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
    // this manually by checking the contents.
    if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide()
  }

  Popover.prototype.hasContent = function () {
    return this.getTitle() || this.getContent()
  }

  Popover.prototype.getContent = function () {
    var $e = this.$element
    var o  = this.options

    return $e.attr('data-content')
      || (typeof o.content == 'function' ?
            o.content.call($e[0]) :
            o.content)
  }

  Popover.prototype.arrow = function () {
    return (this.$arrow = this.$arrow || this.tip().find('.arrow'))
  }


  // POPOVER PLUGIN DEFINITION
  // =========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.popover')
      var options = typeof option == 'object' && option

      if (!data && /destroy|hide/.test(option)) return
      if (!data) $this.data('bs.popover', (data = new Popover(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.popover

  $.fn.popover             = Plugin
  $.fn.popover.Constructor = Popover


  // POPOVER NO CONFLICT
  // ===================

  $.fn.popover.noConflict = function () {
    $.fn.popover = old
    return this
  }

}(jQuery);

/* ========================================================================
 * Bootstrap: scrollspy.js v3.3.7
 * http://getbootstrap.com/javascript/#scrollspy
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // SCROLLSPY CLASS DEFINITION
  // ==========================

  function ScrollSpy(element, options) {
    this.$body          = $(document.body)
    this.$scrollElement = $(element).is(document.body) ? $(window) : $(element)
    this.options        = $.extend({}, ScrollSpy.DEFAULTS, options)
    this.selector       = (this.options.target || '') + ' .nav li > a'
    this.offsets        = []
    this.targets        = []
    this.activeTarget   = null
    this.scrollHeight   = 0

    this.$scrollElement.on('scroll.bs.scrollspy', $.proxy(this.process, this))
    this.refresh()
    this.process()
  }

  ScrollSpy.VERSION  = '3.3.7'

  ScrollSpy.DEFAULTS = {
    offset: 10
  }

  ScrollSpy.prototype.getScrollHeight = function () {
    return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight)
  }

  ScrollSpy.prototype.refresh = function () {
    var that          = this
    var offsetMethod  = 'offset'
    var offsetBase    = 0

    this.offsets      = []
    this.targets      = []
    this.scrollHeight = this.getScrollHeight()

    if (!$.isWindow(this.$scrollElement[0])) {
      offsetMethod = 'position'
      offsetBase   = this.$scrollElement.scrollTop()
    }

    this.$body
      .find(this.selector)
      .map(function () {
        var $el   = $(this)
        var href  = $el.data('target') || $el.attr('href')
        var $href = /^#./.test(href) && $(href)

        return ($href
          && $href.length
          && $href.is(':visible')
          && [[$href[offsetMethod]().top + offsetBase, href]]) || null
      })
      .sort(function (a, b) { return a[0] - b[0] })
      .each(function () {
        that.offsets.push(this[0])
        that.targets.push(this[1])
      })
  }

  ScrollSpy.prototype.process = function () {
    var scrollTop    = this.$scrollElement.scrollTop() + this.options.offset
    var scrollHeight = this.getScrollHeight()
    var maxScroll    = this.options.offset + scrollHeight - this.$scrollElement.height()
    var offsets      = this.offsets
    var targets      = this.targets
    var activeTarget = this.activeTarget
    var i

    if (this.scrollHeight != scrollHeight) {
      this.refresh()
    }

    if (scrollTop >= maxScroll) {
      return activeTarget != (i = targets[targets.length - 1]) && this.activate(i)
    }

    if (activeTarget && scrollTop < offsets[0]) {
      this.activeTarget = null
      return this.clear()
    }

    for (i = offsets.length; i--;) {
      activeTarget != targets[i]
        && scrollTop >= offsets[i]
        && (offsets[i + 1] === undefined || scrollTop < offsets[i + 1])
        && this.activate(targets[i])
    }
  }

  ScrollSpy.prototype.activate = function (target) {
    this.activeTarget = target

    this.clear()

    var selector = this.selector +
      '[data-target="' + target + '"],' +
      this.selector + '[href="' + target + '"]'

    var active = $(selector)
      .parents('li')
      .addClass('active')

    if (active.parent('.dropdown-menu').length) {
      active = active
        .closest('li.dropdown')
        .addClass('active')
    }

    active.trigger('activate.bs.scrollspy')
  }

  ScrollSpy.prototype.clear = function () {
    $(this.selector)
      .parentsUntil(this.options.target, '.active')
      .removeClass('active')
  }


  // SCROLLSPY PLUGIN DEFINITION
  // ===========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.scrollspy')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.scrollspy', (data = new ScrollSpy(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.scrollspy

  $.fn.scrollspy             = Plugin
  $.fn.scrollspy.Constructor = ScrollSpy


  // SCROLLSPY NO CONFLICT
  // =====================

  $.fn.scrollspy.noConflict = function () {
    $.fn.scrollspy = old
    return this
  }


  // SCROLLSPY DATA-API
  // ==================

  $(window).on('load.bs.scrollspy.data-api', function () {
    $('[data-spy="scroll"]').each(function () {
      var $spy = $(this)
      Plugin.call($spy, $spy.data())
    })
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: tab.js v3.3.7
 * http://getbootstrap.com/javascript/#tabs
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // TAB CLASS DEFINITION
  // ====================

  var Tab = function (element) {
    // jscs:disable requireDollarBeforejQueryAssignment
    this.element = $(element)
    // jscs:enable requireDollarBeforejQueryAssignment
  }

  Tab.VERSION = '3.3.7'

  Tab.TRANSITION_DURATION = 150

  Tab.prototype.show = function () {
    var $this    = this.element
    var $ul      = $this.closest('ul:not(.dropdown-menu)')
    var selector = $this.data('target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    if ($this.parent('li').hasClass('active')) return

    var $previous = $ul.find('.active:last a')
    var hideEvent = $.Event('hide.bs.tab', {
      relatedTarget: $this[0]
    })
    var showEvent = $.Event('show.bs.tab', {
      relatedTarget: $previous[0]
    })

    $previous.trigger(hideEvent)
    $this.trigger(showEvent)

    if (showEvent.isDefaultPrevented() || hideEvent.isDefaultPrevented()) return

    var $target = $(selector)

    this.activate($this.closest('li'), $ul)
    this.activate($target, $target.parent(), function () {
      $previous.trigger({
        type: 'hidden.bs.tab',
        relatedTarget: $this[0]
      })
      $this.trigger({
        type: 'shown.bs.tab',
        relatedTarget: $previous[0]
      })
    })
  }

  Tab.prototype.activate = function (element, container, callback) {
    var $active    = container.find('> .active')
    var transition = callback
      && $.support.transition
      && ($active.length && $active.hasClass('fade') || !!container.find('> .fade').length)

    function next() {
      $active
        .removeClass('active')
        .find('> .dropdown-menu > .active')
          .removeClass('active')
        .end()
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', false)

      element
        .addClass('active')
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', true)

      if (transition) {
        element[0].offsetWidth // reflow for transition
        element.addClass('in')
      } else {
        element.removeClass('fade')
      }

      if (element.parent('.dropdown-menu').length) {
        element
          .closest('li.dropdown')
            .addClass('active')
          .end()
          .find('[data-toggle="tab"]')
            .attr('aria-expanded', true)
      }

      callback && callback()
    }

    $active.length && transition ?
      $active
        .one('bsTransitionEnd', next)
        .emulateTransitionEnd(Tab.TRANSITION_DURATION) :
      next()

    $active.removeClass('in')
  }


  // TAB PLUGIN DEFINITION
  // =====================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.tab')

      if (!data) $this.data('bs.tab', (data = new Tab(this)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.tab

  $.fn.tab             = Plugin
  $.fn.tab.Constructor = Tab


  // TAB NO CONFLICT
  // ===============

  $.fn.tab.noConflict = function () {
    $.fn.tab = old
    return this
  }


  // TAB DATA-API
  // ============

  var clickHandler = function (e) {
    e.preventDefault()
    Plugin.call($(this), 'show')
  }

  $(document)
    .on('click.bs.tab.data-api', '[data-toggle="tab"]', clickHandler)
    .on('click.bs.tab.data-api', '[data-toggle="pill"]', clickHandler)

}(jQuery);

/* ========================================================================
 * Bootstrap: affix.js v3.3.7
 * http://getbootstrap.com/javascript/#affix
 * ========================================================================
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // AFFIX CLASS DEFINITION
  // ======================

  var Affix = function (element, options) {
    this.options = $.extend({}, Affix.DEFAULTS, options)

    this.$target = $(this.options.target)
      .on('scroll.bs.affix.data-api', $.proxy(this.checkPosition, this))
      .on('click.bs.affix.data-api',  $.proxy(this.checkPositionWithEventLoop, this))

    this.$element     = $(element)
    this.affixed      = null
    this.unpin        = null
    this.pinnedOffset = null

    this.checkPosition()
  }

  Affix.VERSION  = '3.3.7'

  Affix.RESET    = 'affix affix-top affix-bottom'

  Affix.DEFAULTS = {
    offset: 0,
    target: window
  }

  Affix.prototype.getState = function (scrollHeight, height, offsetTop, offsetBottom) {
    var scrollTop    = this.$target.scrollTop()
    var position     = this.$element.offset()
    var targetHeight = this.$target.height()

    if (offsetTop != null && this.affixed == 'top') return scrollTop < offsetTop ? 'top' : false

    if (this.affixed == 'bottom') {
      if (offsetTop != null) return (scrollTop + this.unpin <= position.top) ? false : 'bottom'
      return (scrollTop + targetHeight <= scrollHeight - offsetBottom) ? false : 'bottom'
    }

    var initializing   = this.affixed == null
    var colliderTop    = initializing ? scrollTop : position.top
    var colliderHeight = initializing ? targetHeight : height

    if (offsetTop != null && scrollTop <= offsetTop) return 'top'
    if (offsetBottom != null && (colliderTop + colliderHeight >= scrollHeight - offsetBottom)) return 'bottom'

    return false
  }

  Affix.prototype.getPinnedOffset = function () {
    if (this.pinnedOffset) return this.pinnedOffset
    this.$element.removeClass(Affix.RESET).addClass('affix')
    var scrollTop = this.$target.scrollTop()
    var position  = this.$element.offset()
    return (this.pinnedOffset = position.top - scrollTop)
  }

  Affix.prototype.checkPositionWithEventLoop = function () {
    setTimeout($.proxy(this.checkPosition, this), 1)
  }

  Affix.prototype.checkPosition = function () {
    if (!this.$element.is(':visible')) return

    var height       = this.$element.height()
    var offset       = this.options.offset
    var offsetTop    = offset.top
    var offsetBottom = offset.bottom
    var scrollHeight = Math.max($(document).height(), $(document.body).height())

    if (typeof offset != 'object')         offsetBottom = offsetTop = offset
    if (typeof offsetTop == 'function')    offsetTop    = offset.top(this.$element)
    if (typeof offsetBottom == 'function') offsetBottom = offset.bottom(this.$element)

    var affix = this.getState(scrollHeight, height, offsetTop, offsetBottom)

    if (this.affixed != affix) {
      if (this.unpin != null) this.$element.css('top', '')

      var affixType = 'affix' + (affix ? '-' + affix : '')
      var e         = $.Event(affixType + '.bs.affix')

      this.$element.trigger(e)

      if (e.isDefaultPrevented()) return

      this.affixed = affix
      this.unpin = affix == 'bottom' ? this.getPinnedOffset() : null

      this.$element
        .removeClass(Affix.RESET)
        .addClass(affixType)
        .trigger(affixType.replace('affix', 'affixed') + '.bs.affix')
    }

    if (affix == 'bottom') {
      this.$element.offset({
        top: scrollHeight - height - offsetBottom
      })
    }
  }


  // AFFIX PLUGIN DEFINITION
  // =======================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.affix')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.affix', (data = new Affix(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.affix

  $.fn.affix             = Plugin
  $.fn.affix.Constructor = Affix


  // AFFIX NO CONFLICT
  // =================

  $.fn.affix.noConflict = function () {
    $.fn.affix = old
    return this
  }


  // AFFIX DATA-API
  // ==============

  $(window).on('load', function () {
    $('[data-spy="affix"]').each(function () {
      var $spy = $(this)
      var data = $spy.data()

      data.offset = data.offset || {}

      if (data.offsetBottom != null) data.offset.bottom = data.offsetBottom
      if (data.offsetTop    != null) data.offset.top    = data.offsetTop

      Plugin.call($spy, data)
    })
  })

}(jQuery);


// jQuery Mask Plugin v1.13.4
// github.com/igorescobar/jQuery-Mask-Plugin
(function(b){"function"===typeof define&&define.amd?define(["jquery"],b):"object"===typeof exports?module.exports=b(require("jquery")):b(jQuery||Zepto)})(function(b){var y=function(a,c,d){a=b(a);var g=this,k=a.val(),l;c="function"===typeof c?c(a.val(),void 0,a,d):c;var e={invalid:[],getCaret:function(){try{var q,b=0,e=a.get(0),f=document.selection,c=e.selectionStart;if(f&&-1===navigator.appVersion.indexOf("MSIE 10"))q=f.createRange(),q.moveStart("character",a.is("input")?-a.val().length:-a.text().length),
b=q.text.length;else if(c||"0"===c)b=c;return b}catch(d){}},setCaret:function(q){try{if(a.is(":focus")){var b,c=a.get(0);c.setSelectionRange?c.setSelectionRange(q,q):c.createTextRange&&(b=c.createTextRange(),b.collapse(!0),b.moveEnd("character",q),b.moveStart("character",q),b.select())}}catch(f){}},events:function(){a.on("input.mask keyup.mask",e.behaviour).on("paste.mask drop.mask",function(){setTimeout(function(){a.keydown().keyup()},100)}).on("change.mask",function(){a.data("changed",!0)}).on("blur.mask",
function(){k===a.val()||a.data("changed")||a.triggerHandler("change");a.data("changed",!1)}).on("blur.mask",function(){k=a.val()}).on("focus.mask",function(a){!0===d.selectOnFocus&&b(a.target).select()}).on("focusout.mask",function(){d.clearIfNotMatch&&!l.test(e.val())&&e.val("")})},getRegexMask:function(){for(var a=[],b,e,f,d,h=0;h<c.length;h++)(b=g.translation[c.charAt(h)])?(e=b.pattern.toString().replace(/.{1}$|^.{1}/g,""),f=b.optional,(b=b.recursive)?(a.push(c.charAt(h)),d={digit:c.charAt(h),
pattern:e}):a.push(f||b?e+"?":e)):a.push(c.charAt(h).replace(/[-\/\\^$*+?.()|[\]{}]/g,"\\$&"));a=a.join("");d&&(a=a.replace(new RegExp("("+d.digit+"(.*"+d.digit+")?)"),"($1)?").replace(new RegExp(d.digit,"g"),d.pattern));return new RegExp(a)},destroyEvents:function(){a.off("input keydown keyup paste drop blur focusout ".split(" ").join(".mask "))},val:function(b){var c=a.is("input")?"val":"text";if(0<arguments.length){if(a[c]()!==b)a[c](b);c=a}else c=a[c]();return c},getMCharsBeforeCount:function(a,
b){for(var e=0,f=0,d=c.length;f<d&&f<a;f++)g.translation[c.charAt(f)]||(a=b?a+1:a,e++);return e},caretPos:function(a,b,d,f){return g.translation[c.charAt(Math.min(a-1,c.length-1))]?Math.min(a+d-b-f,d):e.caretPos(a+1,b,d,f)},behaviour:function(a){a=a||window.event;e.invalid=[];var c=a.keyCode||a.which;if(-1===b.inArray(c,g.byPassKeys)){var d=e.getCaret(),f=e.val().length,n=d<f,h=e.getMasked(),k=h.length,m=e.getMCharsBeforeCount(k-1)-e.getMCharsBeforeCount(f-1);e.val(h);!n||65===c&&a.ctrlKey||(8!==
c&&46!==c&&(d=e.caretPos(d,f,k,m)),e.setCaret(d));return e.callbacks(a)}},getMasked:function(a){var b=[],k=e.val(),f=0,n=c.length,h=0,l=k.length,m=1,p="push",u=-1,t,w;d.reverse?(p="unshift",m=-1,t=0,f=n-1,h=l-1,w=function(){return-1<f&&-1<h}):(t=n-1,w=function(){return f<n&&h<l});for(;w();){var x=c.charAt(f),v=k.charAt(h),r=g.translation[x];if(r)v.match(r.pattern)?(b[p](v),r.recursive&&(-1===u?u=f:f===t&&(f=u-m),t===u&&(f-=m)),f+=m):r.optional?(f+=m,h-=m):r.fallback?(b[p](r.fallback),f+=m,h-=m):e.invalid.push({p:h,
v:v,e:r.pattern}),h+=m;else{if(!a)b[p](x);v===x&&(h+=m);f+=m}}a=c.charAt(t);n!==l+1||g.translation[a]||b.push(a);return b.join("")},callbacks:function(b){var g=e.val(),l=g!==k,f=[g,b,a,d],n=function(a,b,c){"function"===typeof d[a]&&b&&d[a].apply(this,c)};n("onChange",!0===l,f);n("onKeyPress",!0===l,f);n("onComplete",g.length===c.length,f);n("onInvalid",0<e.invalid.length,[g,b,a,e.invalid,d])}};g.mask=c;g.options=d;g.remove=function(){var b=e.getCaret();e.destroyEvents();e.val(g.getCleanVal());e.setCaret(b-
e.getMCharsBeforeCount(b));return a};g.getCleanVal=function(){return e.getMasked(!0)};g.init=function(c){c=c||!1;d=d||{};g.byPassKeys=b.jMaskGlobals.byPassKeys;g.translation=b.jMaskGlobals.translation;g.translation=b.extend({},g.translation,d.translation);g=b.extend(!0,{},g,d);l=e.getRegexMask();!1===c?(d.placeholder&&a.attr("placeholder",d.placeholder),b("input").length&&!1==="oninput"in b("input")[0]&&"on"===a.attr("autocomplete")&&a.attr("autocomplete","off"),e.destroyEvents(),e.events(),c=e.getCaret(),
e.val(e.getMasked()),e.setCaret(c+e.getMCharsBeforeCount(c,!0))):(e.events(),e.val(e.getMasked()))};g.init(!a.is("input"))};b.maskWatchers={};var A=function(){var a=b(this),c={},d=a.attr("data-mask");a.attr("data-mask-reverse")&&(c.reverse=!0);a.attr("data-mask-clearifnotmatch")&&(c.clearIfNotMatch=!0);"true"===a.attr("data-mask-selectonfocus")&&(c.selectOnFocus=!0);if(z(a,d,c))return a.data("mask",new y(this,d,c))},z=function(a,c,d){d=d||{};var g=b(a).data("mask"),k=JSON.stringify;a=b(a).val()||
b(a).text();try{return"function"===typeof c&&(c=c(a)),"object"!==typeof g||k(g.options)!==k(d)||g.mask!==c}catch(l){}};b.fn.mask=function(a,c){c=c||{};var d=this.selector,g=b.jMaskGlobals,k=b.jMaskGlobals.watchInterval,l=function(){if(z(this,a,c))return b(this).data("mask",new y(this,a,c))};b(this).each(l);d&&""!==d&&g.watchInputs&&(clearInterval(b.maskWatchers[d]),b.maskWatchers[d]=setInterval(function(){b(document).find(d).each(l)},k));return this};b.fn.unmask=function(){clearInterval(b.maskWatchers[this.selector]);
delete b.maskWatchers[this.selector];return this.each(function(){var a=b(this).data("mask");a&&a.remove().removeData("mask")})};b.fn.cleanVal=function(){return this.data("mask").getCleanVal()};b.applyDataMask=function(a){a=a||b.jMaskGlobals.maskElements;(a instanceof b?a:b(a)).filter(b.jMaskGlobals.dataMaskAttr).each(A)};var p={maskElements:"input,td,span,div",dataMaskAttr:"*[data-mask]",dataMask:!0,watchInterval:300,watchInputs:!0,watchDataMask:!1,byPassKeys:[9,16,17,18,36,37,38,39,40,91],translation:{0:{pattern:/\d/},
9:{pattern:/\d/,optional:!0},"#":{pattern:/\d/,recursive:!0},A:{pattern:/[a-zA-Z0-9]/},S:{pattern:/[a-zA-Z]/}}};b.jMaskGlobals=b.jMaskGlobals||{};p=b.jMaskGlobals=b.extend(!0,{},p,b.jMaskGlobals);p.dataMask&&b.applyDataMask();setInterval(function(){b.jMaskGlobals.watchDataMask&&b.applyDataMask()},p.watchInterval)});


/* =========================================================
 * bootstrap-datepicker.js 
 * http://www.eyecon.ro/bootstrap-datepicker
 * =========================================================
 * Copyright 2012 Stefan Petre
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================= */
 
!function( $ ) {
  
  // Picker object
  
  var Datepicker = function(element, options){
    this.element = $(element);
    this.format = DPGlobal.parseFormat(options.format||this.element.data('date-format')||'mm/dd/yyyy');
    this.picker = $(DPGlobal.template)
              .appendTo('body')
              .on({
                click: $.proxy(this.click, this)//,
                //mousedown: $.proxy(this.mousedown, this)
              });
    this.isInput = this.element.is('input');
    this.component = this.element.is('.date') ? this.element.find('.add-on') : false;
    
    if (this.isInput) {
      this.element.on({
        focus: $.proxy(this.show, this),
        //blur: $.proxy(this.hide, this),
        keyup: $.proxy(this.update, this)
      });
    } else {
      if (this.component){
        this.component.on('click', $.proxy(this.show, this));
      } else {
        this.element.on('click', $.proxy(this.show, this));
      }
    }
  
    this.minViewMode = options.minViewMode||this.element.data('date-minviewmode')||0;
    if (typeof this.minViewMode === 'string') {
      switch (this.minViewMode) {
        case 'months':
          this.minViewMode = 1;
          break;
        case 'years':
          this.minViewMode = 2;
          break;
        default:
          this.minViewMode = 0;
          break;
      }
    }
    this.viewMode = options.viewMode||this.element.data('date-viewmode')||0;
    if (typeof this.viewMode === 'string') {
      switch (this.viewMode) {
        case 'months':
          this.viewMode = 1;
          break;
        case 'years':
          this.viewMode = 2;
          break;
        default:
          this.viewMode = 0;
          break;
      }
    }
    this.startViewMode = this.viewMode;
    this.weekStart = options.weekStart||this.element.data('date-weekstart')||0;
    this.weekEnd = this.weekStart === 0 ? 6 : this.weekStart - 1;
    this.onRender = options.onRender;
    this.fillDow();
    this.fillMonths();
    this.update();
    this.showMode();
  };
  
  Datepicker.prototype = {
    constructor: Datepicker,
    
    show: function(e) {
      this.picker.show();
      this.height = this.component ? this.component.outerHeight() : this.element.outerHeight();
      this.place();
      $(window).on('resize', $.proxy(this.place, this));
      if (e ) {
        e.stopPropagation();
        e.preventDefault();
      }
      if (!this.isInput) {
      }
      var that = this;
      $(document).on('mousedown', function(ev){
        if ($(ev.target).closest('.datepicker').length == 0) {
          that.hide();
        }
      });
      this.element.trigger({
        type: 'show',
        date: this.date
      });
    },
    
    hide: function(){
      this.picker.hide();
      $(window).off('resize', this.place);
      this.viewMode = this.startViewMode;
      this.showMode();
      if (!this.isInput) {
        $(document).off('mousedown', this.hide);
      }
      //this.set();
      this.element.trigger({
        type: 'hide',
        date: this.date
      });
    },
    
    set: function() {
      var formated = DPGlobal.formatDate(this.date, this.format);
      if (!this.isInput) {
        if (this.component){
          this.element.find('input').prop('value', formated);
        }
        this.element.data('date', formated);
      } else {
        this.element.prop('value', formated);
      }
    },
    
    setValue: function(newDate) {
      if (typeof newDate === 'string') {
        this.date = DPGlobal.parseDate(newDate, this.format);
      } else {
        this.date = new Date(newDate);
      }
      this.set();
      this.viewDate = new Date(this.date.getFullYear(), this.date.getMonth(), 1, 0, 0, 0, 0);
      this.fill();
    },
    
    place: function(){
      var offset = this.component ? this.component.offset() : this.element.offset();
      this.picker.css({
        top: offset.top + this.height,
        left: offset.left
      });
    },
    
    update: function(newDate){
      this.date = DPGlobal.parseDate(
        typeof newDate === 'string' ? newDate : (this.isInput ? this.element.prop('value') : this.element.data('date')),
        this.format
      );
      this.viewDate = new Date(this.date.getFullYear(), this.date.getMonth(), 1, 0, 0, 0, 0);
      this.fill();
    },
    
    fillDow: function(){
      var dowCnt = this.weekStart;
      var html = '<tr>';
      while (dowCnt < this.weekStart + 7) {
        html += '<th class="dow">'+DPGlobal.dates.daysMin[(dowCnt++)%7]+'</th>';
      }
      html += '</tr>';
      this.picker.find('.datepicker-days thead').append(html);
    },
    
    fillMonths: function(){
      var html = '';
      var i = 0
      while (i < 12) {
        html += '<span class="month">'+DPGlobal.dates.monthsShort[i++]+'</span>';
      }
      this.picker.find('.datepicker-months td').append(html);
    },
    
    fill: function() {
      var d = new Date(this.viewDate),
        year = d.getFullYear(),
        month = d.getMonth(),
        currentDate = this.date.valueOf();
      this.picker.find('.datepicker-days th:eq(1)')
            .text(DPGlobal.dates.months[month]+' '+year);
      var prevMonth = new Date(year, month-1, 28,0,0,0,0),
        day = DPGlobal.getDaysInMonth(prevMonth.getFullYear(), prevMonth.getMonth());
      prevMonth.setDate(day);
      prevMonth.setDate(day - (prevMonth.getDay() - this.weekStart + 7)%7);
      var nextMonth = new Date(prevMonth);
      nextMonth.setDate(nextMonth.getDate() + 42);
      nextMonth = nextMonth.valueOf();
      var html = [];
      var clsName,
        prevY,
        prevM;
      while(prevMonth.valueOf() < nextMonth) {
        if (prevMonth.getDay() === this.weekStart) {
          html.push('<tr>');
        }
        clsName = this.onRender(prevMonth);
        prevY = prevMonth.getFullYear();
        prevM = prevMonth.getMonth();
        if ((prevM < month &&  prevY === year) ||  prevY < year) {
          clsName += ' old';
        } else if ((prevM > month && prevY === year) || prevY > year) {
          clsName += ' new';
        }
        if (prevMonth.valueOf() === currentDate) {
          clsName += ' active';
        }
        html.push('<td class="day '+clsName+'">'+prevMonth.getDate() + '</td>');
        if (prevMonth.getDay() === this.weekEnd) {
          html.push('</tr>');
        }
        prevMonth.setDate(prevMonth.getDate()+1);
      }
      this.picker.find('.datepicker-days tbody').empty().append(html.join(''));
      var currentYear = this.date.getFullYear();
      
      var months = this.picker.find('.datepicker-months')
            .find('th:eq(1)')
              .text(year)
              .end()
            .find('span').removeClass('active');
      if (currentYear === year) {
        months.eq(this.date.getMonth()).addClass('active');
      }
      
      html = '';
      year = parseInt(year/10, 10) * 10;
      var yearCont = this.picker.find('.datepicker-years')
                .find('th:eq(1)')
                  .text(year + '-' + (year + 9))
                  .end()
                .find('td');
      year -= 1;
      for (var i = -1; i < 11; i++) {
        html += '<span class="year'+(i === -1 || i === 10 ? ' old' : '')+(currentYear === year ? ' active' : '')+'">'+year+'</span>';
        year += 1;
      }
      yearCont.html(html);
    },
    
    click: function(e) {
      e.stopPropagation();
      e.preventDefault();
      var target = $(e.target).closest('span, td, th');
      if (target.length === 1) {
        switch(target[0].nodeName.toLowerCase()) {
          case 'th':
            switch(target[0].className) {
              case 'switch':
                this.showMode(1);
                break;
              case 'prev':
              case 'next':
                this.viewDate['set'+DPGlobal.modes[this.viewMode].navFnc].call(
                  this.viewDate,
                  this.viewDate['get'+DPGlobal.modes[this.viewMode].navFnc].call(this.viewDate) + 
                  DPGlobal.modes[this.viewMode].navStep * (target[0].className === 'prev' ? -1 : 1)
                );
                this.fill();
                this.set();
                break;
            }
            break;
          case 'span':
            if (target.is('.month')) {
              var month = target.parent().find('span').index(target);
              this.viewDate.setMonth(month);
            } else {
              var year = parseInt(target.text(), 10)||0;
              this.viewDate.setFullYear(year);
            }
            if (this.viewMode !== 0) {
              this.date = new Date(this.viewDate);
              this.element.trigger({
                type: 'changeDate',
                date: this.date,
                viewMode: DPGlobal.modes[this.viewMode].clsName
              });
            }
            this.showMode(-1);
            this.fill();
            this.set();
            break;
          case 'td':
            if (target.is('.day') && !target.is('.disabled')){
              var day = parseInt(target.text(), 10)||1;
              var month = this.viewDate.getMonth();
              if (target.is('.old')) {
                month -= 1;
              } else if (target.is('.new')) {
                month += 1;
              }
              var year = this.viewDate.getFullYear();
              this.date = new Date(year, month, day,0,0,0,0);
              this.viewDate = new Date(year, month, Math.min(28, day),0,0,0,0);
              this.fill();
              this.set();
              this.element.trigger({
                type: 'changeDate',
                date: this.date,
                viewMode: DPGlobal.modes[this.viewMode].clsName
              });
            }
            break;
        }
      }
    },
    
    mousedown: function(e){
      e.stopPropagation();
      e.preventDefault();
    },
    
    showMode: function(dir) {
      if (dir) {
        this.viewMode = Math.max(this.minViewMode, Math.min(2, this.viewMode + dir));
      }
      this.picker.find('>div').hide().filter('.datepicker-'+DPGlobal.modes[this.viewMode].clsName).show();
    }
  };
  
  $.fn.datepicker = function ( option, val ) {
    return this.each(function () {
      var $this = $(this),
        data = $this.data('datepicker'),
        options = typeof option === 'object' && option;
      if (!data) {
        $this.data('datepicker', (data = new Datepicker(this, $.extend({}, $.fn.datepicker.defaults,options))));
      }
      if (typeof option === 'string') data[option](val);
    });
  };

  $.fn.datepicker.defaults = {
    onRender: function(date) {
      return '';
    }
  };
  $.fn.datepicker.Constructor = Datepicker;
  
  var DPGlobal = {
    modes: [
      {
        clsName: 'days',
        navFnc: 'Month',
        navStep: 1
      },
      {
        clsName: 'months',
        navFnc: 'FullYear',
        navStep: 1
      },
      {
        clsName: 'years',
        navFnc: 'FullYear',
        navStep: 10
    }],
    dates:{
      days: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"],
      daysShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom"],
      daysMin: ["D", "S", "T", "Q", "Q", "S", "S", "D"],
      months: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
      monthsShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]
    },
    isLeapYear: function (year) {
      return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0))
    },
    getDaysInMonth: function (year, month) {
      return [31, (DPGlobal.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month]
    },
    parseFormat: function(format){
      var separator = format.match(/[.\/\-\s].*?/),
        parts = format.split(/\W+/);
      if (!separator || !parts || parts.length === 0){
        throw new Error("Invalid date format.");
      }
      return {separator: separator, parts: parts};
    },
    parseDate: function(date, format) {
      var parts = date.split(format.separator),
        date = new Date(),
        val;
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);
      date.setMilliseconds(0);
      if (parts.length === format.parts.length) {
        var year = date.getFullYear(), day = date.getDate(), month = date.getMonth();
        for (var i=0, cnt = format.parts.length; i < cnt; i++) {
          val = parseInt(parts[i], 10)||1;
          switch(format.parts[i]) {
            case 'dd':
            case 'd':
              day = val;
              date.setDate(val);
              break;
            case 'mm':
            case 'm':
              month = val - 1;
              date.setMonth(val - 1);
              break;
            case 'yy':
              year = 2000 + val;
              date.setFullYear(2000 + val);
              break;
            case 'yyyy':
              year = val;
              date.setFullYear(val);
              break;
          }
        }
        date = new Date(year, month, day, 0 ,0 ,0);
      }
      return date;
    },
    formatDate: function(date, format){
      var val = {
        d: date.getDate(),
        m: date.getMonth() + 1,
        yy: date.getFullYear().toString().substring(2),
        yyyy: date.getFullYear()
      };
      val.dd = (val.d < 10 ? '0' : '') + val.d;
      val.mm = (val.m < 10 ? '0' : '') + val.m;
      var date = [];
      for (var i=0, cnt = format.parts.length; i < cnt; i++) {
        date.push(val[format.parts[i]]);
      }
      return date.join(format.separator);
    },
    headTemplate: '<thead>'+
              '<tr>'+
                '<th class="prev">&lsaquo;</th>'+
                '<th colspan="5" class="switch"></th>'+
                '<th class="next">&rsaquo;</th>'+
              '</tr>'+
            '</thead>',
    contTemplate: '<tbody><tr><td colspan="7"></td></tr></tbody>'
  };
  DPGlobal.template = '<div class="datepicker dropdown-menu">'+
              '<div class="datepicker-days">'+
                '<table class=" table-condensed">'+
                  DPGlobal.headTemplate+
                  '<tbody></tbody>'+
                '</table>'+
              '</div>'+
              '<div class="datepicker-months">'+
                '<table class="table-condensed">'+
                  DPGlobal.headTemplate+
                  DPGlobal.contTemplate+
                '</table>'+
              '</div>'+
              '<div class="datepicker-years">'+
                '<table class="table-condensed">'+
                  DPGlobal.headTemplate+
                  DPGlobal.contTemplate+
                '</table>'+
              '</div>'+
            '</div>';

}( window.jQuery );



/*! =======================================================
                      VERSION  6.0.4              
========================================================= */
"use strict";

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

/*! =========================================================
 * bootstrap-slider.js
 *
 * Maintainers:
 *    Kyle Kemp
 *      - Twitter: @seiyria
 *      - Github:  seiyria
 *    Rohit Kalkur
 *      - Twitter: @Rovolutionary
 *      - Github:  rovolution
 *
 * =========================================================
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================= */

/**
 * Bridget makes jQuery widgets
 * v1.0.1
 * MIT license
 */

(function (factory) {
  if (typeof define === "function" && define.amd) {
    define(["jquery"], factory);
  } else if ((typeof module === "undefined" ? "undefined" : _typeof(module)) === "object" && module.exports) {
    var jQuery;
    try {
      jQuery = require("jquery");
    } catch (err) {
      jQuery = null;
    }
    module.exports = factory(jQuery);
  } else if (window) {
    window.Slider = factory(window.jQuery);
  }
})(function ($) {
  // Reference to Slider constructor
  var Slider;

  (function ($) {

    'use strict';

    // -------------------------- utils -------------------------- //

    var slice = Array.prototype.slice;

    function noop() {}

    // -------------------------- definition -------------------------- //

    function defineBridget($) {

      // bail if no jQuery
      if (!$) {
        return;
      }

      // -------------------------- addOptionMethod -------------------------- //

      /**
    * adds option method -> $().plugin('option', {...})
    * @param {Function} PluginClass - constructor class
    */
      function addOptionMethod(PluginClass) {
        // don't overwrite original option method
        if (PluginClass.prototype.option) {
          return;
        }

        // option setter
        PluginClass.prototype.option = function (opts) {
          // bail out if not an object
          if (!$.isPlainObject(opts)) {
            return;
          }
          this.options = $.extend(true, this.options, opts);
        };
      }

      // -------------------------- plugin bridge -------------------------- //

      // helper function for logging errors
      // $.error breaks jQuery chaining
      var logError = typeof console === 'undefined' ? noop : function (message) {
        console.error(message);
      };

      /**
    * jQuery plugin bridge, access methods like $elem.plugin('method')
    * @param {String} namespace - plugin name
    * @param {Function} PluginClass - constructor class
    */
      function bridge(namespace, PluginClass) {
        // add to jQuery fn namespace
        $.fn[namespace] = function (options) {
          if (typeof options === 'string') {
            // call plugin method when first argument is a string
            // get arguments for method
            var args = slice.call(arguments, 1);

            for (var i = 0, len = this.length; i < len; i++) {
              var elem = this[i];
              var instance = $.data(elem, namespace);
              if (!instance) {
                logError("cannot call methods on " + namespace + " prior to initialization; " + "attempted to call '" + options + "'");
                continue;
              }
              if (!$.isFunction(instance[options]) || options.charAt(0) === '_') {
                logError("no such method '" + options + "' for " + namespace + " instance");
                continue;
              }

              // trigger method with arguments
              var returnValue = instance[options].apply(instance, args);

              // break look and return first value if provided
              if (returnValue !== undefined && returnValue !== instance) {
                return returnValue;
              }
            }
            // return this if no return value
            return this;
          } else {
            var objects = this.map(function () {
              var instance = $.data(this, namespace);
              if (instance) {
                // apply options & init
                instance.option(options);
                instance._init();
              } else {
                // initialize new instance
                instance = new PluginClass(this, options);
                $.data(this, namespace, instance);
              }
              return $(this);
            });

            if (!objects || objects.length > 1) {
              return objects;
            } else {
              return objects[0];
            }
          }
        };
      }

      // -------------------------- bridget -------------------------- //

      /**
    * converts a Prototypical class into a proper jQuery plugin
    *   the class must have a ._init method
    * @param {String} namespace - plugin name, used in $().pluginName
    * @param {Function} PluginClass - constructor class
    */
      $.bridget = function (namespace, PluginClass) {
        addOptionMethod(PluginClass);
        bridge(namespace, PluginClass);
      };

      return $.bridget;
    }

    // get jquery from browser global
    defineBridget($);
  })($);

  /*************************************************
      BOOTSTRAP-SLIDER SOURCE CODE
  **************************************************/

  (function ($) {

    var ErrorMsgs = {
      formatInvalidInputErrorMsg: function formatInvalidInputErrorMsg(input) {
        return "Invalid input value '" + input + "' passed in";
      },
      callingContextNotSliderInstance: "Calling context element does not have instance of Slider bound to it. Check your code to make sure the JQuery object returned from the call to the slider() initializer is calling the method"
    };

    var SliderScale = {
      linear: {
        toValue: function toValue(percentage) {
          var rawValue = percentage / 100 * (this.options.max - this.options.min);
          var shouldAdjustWithBase = true;
          if (this.options.ticks_positions.length > 0) {
            var minv,
                maxv,
                minp,
                maxp = 0;
            for (var i = 1; i < this.options.ticks_positions.length; i++) {
              if (percentage <= this.options.ticks_positions[i]) {
                minv = this.options.ticks[i - 1];
                minp = this.options.ticks_positions[i - 1];
                maxv = this.options.ticks[i];
                maxp = this.options.ticks_positions[i];

                break;
              }
            }
            var partialPercentage = (percentage - minp) / (maxp - minp);
            rawValue = minv + partialPercentage * (maxv - minv);
            shouldAdjustWithBase = false;
          }

          var adjustment = shouldAdjustWithBase ? this.options.min : 0;
          var value = adjustment + Math.round(rawValue / this.options.step) * this.options.step;
          if (value < this.options.min) {
            return this.options.min;
          } else if (value > this.options.max) {
            return this.options.max;
          } else {
            return value;
          }
        },
        toPercentage: function toPercentage(value) {
          if (this.options.max === this.options.min) {
            return 0;
          }

          if (this.options.ticks_positions.length > 0) {
            var minv,
                maxv,
                minp,
                maxp = 0;
            for (var i = 0; i < this.options.ticks.length; i++) {
              if (value <= this.options.ticks[i]) {
                minv = i > 0 ? this.options.ticks[i - 1] : 0;
                minp = i > 0 ? this.options.ticks_positions[i - 1] : 0;
                maxv = this.options.ticks[i];
                maxp = this.options.ticks_positions[i];

                break;
              }
            }
            if (i > 0) {
              var partialPercentage = (value - minv) / (maxv - minv);
              return minp + partialPercentage * (maxp - minp);
            }
          }

          return 100 * (value - this.options.min) / (this.options.max - this.options.min);
        }
      },

      logarithmic: {
        /* Based on http://stackoverflow.com/questions/846221/logarithmic-slider */
        toValue: function toValue(percentage) {
          var min = this.options.min === 0 ? 0 : Math.log(this.options.min);
          var max = Math.log(this.options.max);
          var value = Math.exp(min + (max - min) * percentage / 100);
          value = this.options.min + Math.round((value - this.options.min) / this.options.step) * this.options.step;
          /* Rounding to the nearest step could exceed the min or
      * max, so clip to those values. */
          if (value < this.options.min) {
            return this.options.min;
          } else if (value > this.options.max) {
            return this.options.max;
          } else {
            return value;
          }
        },
        toPercentage: function toPercentage(value) {
          if (this.options.max === this.options.min) {
            return 0;
          } else {
            var max = Math.log(this.options.max);
            var min = this.options.min === 0 ? 0 : Math.log(this.options.min);
            var v = value === 0 ? 0 : Math.log(value);
            return 100 * (v - min) / (max - min);
          }
        }
      }
    };

    /*************************************************
              CONSTRUCTOR
    **************************************************/
    Slider = function (element, options) {
      createNewSlider.call(this, element, options);
      return this;
    };

    function createNewSlider(element, options) {

      /*
    The internal state object is used to store data about the current 'state' of slider.
      This includes values such as the `value`, `enabled`, etc...
   */
      this._state = {
        value: null,
        enabled: null,
        offset: null,
        size: null,
        percentage: null,
        inDrag: false,
        over: false
      };

      if (typeof element === "string") {
        this.element = document.querySelector(element);
      } else if (element instanceof HTMLElement) {
        this.element = element;
      }

      /*************************************************
            Process Options
    **************************************************/
      options = options ? options : {};
      var optionTypes = Object.keys(this.defaultOptions);

      for (var i = 0; i < optionTypes.length; i++) {
        var optName = optionTypes[i];

        // First check if an option was passed in via the constructor
        var val = options[optName];
        // If no data attrib, then check data atrributes
        val = typeof val !== 'undefined' ? val : getDataAttrib(this.element, optName);
        // Finally, if nothing was specified, use the defaults
        val = val !== null ? val : this.defaultOptions[optName];

        // Set all options on the instance of the Slider
        if (!this.options) {
          this.options = {};
        }
        this.options[optName] = val;
      }

      /*
    Validate `tooltip_position` against 'orientation`
    - if `tooltip_position` is incompatible with orientation, swith it to a default compatible with specified `orientation`
      -- default for "vertical" -> "right"
      -- default for "horizontal" -> "left"
   */
      if (this.options.orientation === "vertical" && (this.options.tooltip_position === "top" || this.options.tooltip_position === "bottom")) {

        this.options.tooltip_position = "right";
      } else if (this.options.orientation === "horizontal" && (this.options.tooltip_position === "left" || this.options.tooltip_position === "right")) {

        this.options.tooltip_position = "top";
      }

      function getDataAttrib(element, optName) {
        var dataName = "data-slider-" + optName.replace(/_/g, '-');
        var dataValString = element.getAttribute(dataName);

        try {
          return JSON.parse(dataValString);
        } catch (err) {
          return dataValString;
        }
      }

      /*************************************************
            Create Markup
    **************************************************/

      var origWidth = this.element.style.width;
      var updateSlider = false;
      var parent = this.element.parentNode;
      var sliderTrackSelection;
      var sliderTrackLow, sliderTrackHigh;
      var sliderMinHandle;
      var sliderMaxHandle;

      if (this.sliderElem) {
        updateSlider = true;
      } else {
        /* Create elements needed for slider */
        this.sliderElem = document.createElement("div");
        this.sliderElem.className = "slider";

        /* Create slider track elements */
        var sliderTrack = document.createElement("div");
        sliderTrack.className = "slider-track";

        sliderTrackLow = document.createElement("div");
        sliderTrackLow.className = "slider-track-low";

        sliderTrackSelection = document.createElement("div");
        sliderTrackSelection.className = "slider-selection";

        sliderTrackHigh = document.createElement("div");
        sliderTrackHigh.className = "slider-track-high";

        sliderMinHandle = document.createElement("div");
        sliderMinHandle.className = "slider-handle min-slider-handle";
        sliderMinHandle.setAttribute('role', 'slider');
        sliderMinHandle.setAttribute('aria-valuemin', this.options.min);
        sliderMinHandle.setAttribute('aria-valuemax', this.options.max);

        sliderMaxHandle = document.createElement("div");
        sliderMaxHandle.className = "slider-handle max-slider-handle";
        sliderMaxHandle.setAttribute('role', 'slider');
        sliderMaxHandle.setAttribute('aria-valuemin', this.options.min);
        sliderMaxHandle.setAttribute('aria-valuemax', this.options.max);

        sliderTrack.appendChild(sliderTrackLow);
        sliderTrack.appendChild(sliderTrackSelection);
        sliderTrack.appendChild(sliderTrackHigh);

        /* Add aria-labelledby to handle's */
        var isLabelledbyArray = Array.isArray(this.options.labelledby);
        if (isLabelledbyArray && this.options.labelledby[0]) {
          sliderMinHandle.setAttribute('aria-labelledby', this.options.labelledby[0]);
        }
        if (isLabelledbyArray && this.options.labelledby[1]) {
          sliderMaxHandle.setAttribute('aria-labelledby', this.options.labelledby[1]);
        }
        if (!isLabelledbyArray && this.options.labelledby) {
          sliderMinHandle.setAttribute('aria-labelledby', this.options.labelledby);
          sliderMaxHandle.setAttribute('aria-labelledby', this.options.labelledby);
        }

        /* Create ticks */
        this.ticks = [];
        if (Array.isArray(this.options.ticks) && this.options.ticks.length > 0) {
          for (i = 0; i < this.options.ticks.length; i++) {
            var tick = document.createElement('div');
            tick.className = 'slider-tick';

            this.ticks.push(tick);
            sliderTrack.appendChild(tick);
          }

          sliderTrackSelection.className += " tick-slider-selection";
        }

        sliderTrack.appendChild(sliderMinHandle);
        sliderTrack.appendChild(sliderMaxHandle);

        this.tickLabels = [];
        if (Array.isArray(this.options.ticks_labels) && this.options.ticks_labels.length > 0) {
          this.tickLabelContainer = document.createElement('div');
          this.tickLabelContainer.className = 'slider-tick-label-container';

          for (i = 0; i < this.options.ticks_labels.length; i++) {
            var label = document.createElement('div');
            var noTickPositionsSpecified = this.options.ticks_positions.length === 0;
            var tickLabelsIndex = this.options.reversed && noTickPositionsSpecified ? this.options.ticks_labels.length - (i + 1) : i;
            label.className = 'slider-tick-label';
            label.innerHTML = this.options.ticks_labels[tickLabelsIndex];

            this.tickLabels.push(label);
            this.tickLabelContainer.appendChild(label);
          }
        }

        var createAndAppendTooltipSubElements = function createAndAppendTooltipSubElements(tooltipElem) {
          var arrow = document.createElement("div");
          arrow.className = "tooltip-arrow";

          var inner = document.createElement("div");
          inner.className = "tooltip-inner";

          tooltipElem.appendChild(arrow);
          tooltipElem.appendChild(inner);
        };

        /* Create tooltip elements */
        var sliderTooltip = document.createElement("div");
        sliderTooltip.className = "tooltip tooltip-main";
        sliderTooltip.setAttribute('role', 'presentation');
        createAndAppendTooltipSubElements(sliderTooltip);

        var sliderTooltipMin = document.createElement("div");
        sliderTooltipMin.className = "tooltip tooltip-min";
        sliderTooltipMin.setAttribute('role', 'presentation');
        createAndAppendTooltipSubElements(sliderTooltipMin);

        var sliderTooltipMax = document.createElement("div");
        sliderTooltipMax.className = "tooltip tooltip-max";
        sliderTooltipMax.setAttribute('role', 'presentation');
        createAndAppendTooltipSubElements(sliderTooltipMax);

        /* Append components to sliderElem */
        this.sliderElem.appendChild(sliderTrack);
        this.sliderElem.appendChild(sliderTooltip);
        this.sliderElem.appendChild(sliderTooltipMin);
        this.sliderElem.appendChild(sliderTooltipMax);

        if (this.tickLabelContainer) {
          this.sliderElem.appendChild(this.tickLabelContainer);
        }

        /* Append slider element to parent container, right before the original <input> element */
        parent.insertBefore(this.sliderElem, this.element);

        /* Hide original <input> element */
        this.element.style.display = "none";
      }
      /* If JQuery exists, cache JQ references */
      if ($) {
        this.$element = $(this.element);
        this.$sliderElem = $(this.sliderElem);
      }

      /*************************************************
              Setup
    **************************************************/
      this.eventToCallbackMap = {};
      this.sliderElem.id = this.options.id;

      this.touchCapable = 'ontouchstart' in window || window.DocumentTouch && document instanceof window.DocumentTouch;

      this.tooltip = this.sliderElem.querySelector('.tooltip-main');
      this.tooltipInner = this.tooltip.querySelector('.tooltip-inner');

      this.tooltip_min = this.sliderElem.querySelector('.tooltip-min');
      this.tooltipInner_min = this.tooltip_min.querySelector('.tooltip-inner');

      this.tooltip_max = this.sliderElem.querySelector('.tooltip-max');
      this.tooltipInner_max = this.tooltip_max.querySelector('.tooltip-inner');

      if (SliderScale[this.options.scale]) {
        this.options.scale = SliderScale[this.options.scale];
      }

      if (updateSlider === true) {
        // Reset classes
        this._removeClass(this.sliderElem, 'slider-horizontal');
        this._removeClass(this.sliderElem, 'slider-vertical');
        this._removeClass(this.tooltip, 'hide');
        this._removeClass(this.tooltip_min, 'hide');
        this._removeClass(this.tooltip_max, 'hide');

        // Undo existing inline styles for track
        ["left", "top", "width", "height"].forEach(function (prop) {
          this._removeProperty(this.trackLow, prop);
          this._removeProperty(this.trackSelection, prop);
          this._removeProperty(this.trackHigh, prop);
        }, this);

        // Undo inline styles on handles
        [this.handle1, this.handle2].forEach(function (handle) {
          this._removeProperty(handle, 'left');
          this._removeProperty(handle, 'top');
        }, this);

        // Undo inline styles and classes on tooltips
        [this.tooltip, this.tooltip_min, this.tooltip_max].forEach(function (tooltip) {
          this._removeProperty(tooltip, 'left');
          this._removeProperty(tooltip, 'top');
          this._removeProperty(tooltip, 'margin-left');
          this._removeProperty(tooltip, 'margin-top');

          this._removeClass(tooltip, 'right');
          this._removeClass(tooltip, 'top');
        }, this);
      }

      if (this.options.orientation === 'vertical') {
        this._addClass(this.sliderElem, 'slider-vertical');
        this.stylePos = 'top';
        this.mousePos = 'pageY';
        this.sizePos = 'offsetHeight';
      } else {
        this._addClass(this.sliderElem, 'slider-horizontal');
        this.sliderElem.style.width = origWidth;
        this.options.orientation = 'horizontal';
        this.stylePos = 'left';
        this.mousePos = 'pageX';
        this.sizePos = 'offsetWidth';
      }
      this._setTooltipPosition();
      /* In case ticks are specified, overwrite the min and max bounds */
      if (Array.isArray(this.options.ticks) && this.options.ticks.length > 0) {
        this.options.max = Math.max.apply(Math, this.options.ticks);
        this.options.min = Math.min.apply(Math, this.options.ticks);
      }

      if (Array.isArray(this.options.value)) {
        this.options.range = true;
        this._state.value = this.options.value;
      } else if (this.options.range) {
        // User wants a range, but value is not an array
        this._state.value = [this.options.value, this.options.max];
      } else {
        this._state.value = this.options.value;
      }

      this.trackLow = sliderTrackLow || this.trackLow;
      this.trackSelection = sliderTrackSelection || this.trackSelection;
      this.trackHigh = sliderTrackHigh || this.trackHigh;

      if (this.options.selection === 'none') {
        this._addClass(this.trackLow, 'hide');
        this._addClass(this.trackSelection, 'hide');
        this._addClass(this.trackHigh, 'hide');
      }

      this.handle1 = sliderMinHandle || this.handle1;
      this.handle2 = sliderMaxHandle || this.handle2;

      if (updateSlider === true) {
        // Reset classes
        this._removeClass(this.handle1, 'round triangle');
        this._removeClass(this.handle2, 'round triangle hide');

        for (i = 0; i < this.ticks.length; i++) {
          this._removeClass(this.ticks[i], 'round triangle hide');
        }
      }

      var availableHandleModifiers = ['round', 'triangle', 'custom'];
      var isValidHandleType = availableHandleModifiers.indexOf(this.options.handle) !== -1;
      if (isValidHandleType) {
        this._addClass(this.handle1, this.options.handle);
        this._addClass(this.handle2, this.options.handle);

        for (i = 0; i < this.ticks.length; i++) {
          this._addClass(this.ticks[i], this.options.handle);
        }
      }

      this._state.offset = this._offset(this.sliderElem);
      this._state.size = this.sliderElem[this.sizePos];
      this.setValue(this._state.value);

      /******************************************
          Bind Event Listeners
    ******************************************/

      // Bind keyboard handlers
      this.handle1Keydown = this._keydown.bind(this, 0);
      this.handle1.addEventListener("keydown", this.handle1Keydown, false);

      this.handle2Keydown = this._keydown.bind(this, 1);
      this.handle2.addEventListener("keydown", this.handle2Keydown, false);

      this.mousedown = this._mousedown.bind(this);
      if (this.touchCapable) {
        // Bind touch handlers
        this.sliderElem.addEventListener("touchstart", this.mousedown, false);
      }
      this.sliderElem.addEventListener("mousedown", this.mousedown, false);

      // Bind window handlers
      this.resize = this._resize.bind(this);
      window.addEventListener("resize", this.resize, false);

      // Bind tooltip-related handlers
      if (this.options.tooltip === 'hide') {
        this._addClass(this.tooltip, 'hide');
        this._addClass(this.tooltip_min, 'hide');
        this._addClass(this.tooltip_max, 'hide');
      } else if (this.options.tooltip === 'always') {
        this._showTooltip();
        this._alwaysShowTooltip = true;
      } else {
        this.showTooltip = this._showTooltip.bind(this);
        this.hideTooltip = this._hideTooltip.bind(this);

        this.sliderElem.addEventListener("mouseenter", this.showTooltip, false);
        this.sliderElem.addEventListener("mouseleave", this.hideTooltip, false);

        this.handle1.addEventListener("focus", this.showTooltip, false);
        this.handle1.addEventListener("blur", this.hideTooltip, false);

        this.handle2.addEventListener("focus", this.showTooltip, false);
        this.handle2.addEventListener("blur", this.hideTooltip, false);
      }

      if (this.options.enabled) {
        this.enable();
      } else {
        this.disable();
      }
    }

    /*************************************************
          INSTANCE PROPERTIES/METHODS
    - Any methods bound to the prototype are considered
  part of the plugin's `public` interface
    **************************************************/
    Slider.prototype = {
      _init: function _init() {}, // NOTE: Must exist to support bridget

      constructor: Slider,

      defaultOptions: {
        id: "",
        min: 0,
        max: 10,
        step: 1,
        precision: 0,
        orientation: 'horizontal',
        value: 5,
        range: false,
        selection: 'before',
        tooltip: 'show',
        tooltip_split: false,
        handle: 'round',
        reversed: false,
        enabled: true,
        formatter: function formatter(val) {
          if (Array.isArray(val)) {
            return val[0] + " : " + val[1];
          } else {
            return val;
          }
        },
        natural_arrow_keys: false,
        ticks: [],
        ticks_positions: [],
        ticks_labels: [],
        ticks_snap_bounds: 0,
        scale: 'linear',
        focus: false,
        tooltip_position: null,
        labelledby: null
      },

      getElement: function getElement() {
        return this.sliderElem;
      },

      getValue: function getValue() {
        if (this.options.range) {
          return this._state.value;
        } else {
          return this._state.value[0];
        }
      },

      setValue: function setValue(val, triggerSlideEvent, triggerChangeEvent) {
        if (!val) {
          val = 0;
        }
        var oldValue = this.getValue();
        this._state.value = this._validateInputValue(val);
        var applyPrecision = this._applyPrecision.bind(this);

        if (this.options.range) {
          this._state.value[0] = applyPrecision(this._state.value[0]);
          this._state.value[1] = applyPrecision(this._state.value[1]);

          this._state.value[0] = Math.max(this.options.min, Math.min(this.options.max, this._state.value[0]));
          this._state.value[1] = Math.max(this.options.min, Math.min(this.options.max, this._state.value[1]));
        } else {
          this._state.value = applyPrecision(this._state.value);
          this._state.value = [Math.max(this.options.min, Math.min(this.options.max, this._state.value))];
          this._addClass(this.handle2, 'hide');
          if (this.options.selection === 'after') {
            this._state.value[1] = this.options.max;
          } else {
            this._state.value[1] = this.options.min;
          }
        }

        if (this.options.max > this.options.min) {
          this._state.percentage = [this._toPercentage(this._state.value[0]), this._toPercentage(this._state.value[1]), this.options.step * 100 / (this.options.max - this.options.min)];
        } else {
          this._state.percentage = [0, 0, 100];
        }

        this._layout();
        var newValue = this.options.range ? this._state.value : this._state.value[0];

        if (triggerSlideEvent === true) {
          this._trigger('slide', newValue);
        }
        if (oldValue !== newValue && triggerChangeEvent === true) {
          this._trigger('change', {
            oldValue: oldValue,
            newValue: newValue
          });
        }
        this._setDataVal(newValue);

        return this;
      },

      destroy: function destroy() {
        // Remove event handlers on slider elements
        this._removeSliderEventHandlers();

        // Remove the slider from the DOM
        this.sliderElem.parentNode.removeChild(this.sliderElem);
        /* Show original <input> element */
        this.element.style.display = "";

        // Clear out custom event bindings
        this._cleanUpEventCallbacksMap();

        // Remove data values
        this.element.removeAttribute("data");

        // Remove JQuery handlers/data
        if ($) {
          this._unbindJQueryEventHandlers();
          this.$element.removeData('slider');
        }
      },

      disable: function disable() {
        this._state.enabled = false;
        this.handle1.removeAttribute("tabindex");
        this.handle2.removeAttribute("tabindex");
        this._addClass(this.sliderElem, 'slider-disabled');
        this._trigger('slideDisabled');

        return this;
      },

      enable: function enable() {
        this._state.enabled = true;
        this.handle1.setAttribute("tabindex", 0);
        this.handle2.setAttribute("tabindex", 0);
        this._removeClass(this.sliderElem, 'slider-disabled');
        this._trigger('slideEnabled');

        return this;
      },

      toggle: function toggle() {
        if (this._state.enabled) {
          this.disable();
        } else {
          this.enable();
        }
        return this;
      },

      isEnabled: function isEnabled() {
        return this._state.enabled;
      },

      on: function on(evt, callback) {
        this._bindNonQueryEventHandler(evt, callback);
        return this;
      },

      off: function off(evt, callback) {
        if ($) {
          this.$element.off(evt, callback);
          this.$sliderElem.off(evt, callback);
        } else {
          this._unbindNonQueryEventHandler(evt, callback);
        }
      },

      getAttribute: function getAttribute(attribute) {
        if (attribute) {
          return this.options[attribute];
        } else {
          return this.options;
        }
      },

      setAttribute: function setAttribute(attribute, value) {
        this.options[attribute] = value;
        return this;
      },

      refresh: function refresh() {
        this._removeSliderEventHandlers();
        createNewSlider.call(this, this.element, this.options);
        if ($) {
          // Bind new instance of slider to the element
          $.data(this.element, 'slider', this);
        }
        return this;
      },

      relayout: function relayout() {
        this._layout();
        return this;
      },

      /******************************+
          HELPERS
    - Any method that is not part of the public interface.
   - Place it underneath this comment block and write its signature like so:
                _fnName : function() {...}
    ********************************/
      _removeSliderEventHandlers: function _removeSliderEventHandlers() {
        // Remove keydown event listeners
        this.handle1.removeEventListener("keydown", this.handle1Keydown, false);
        this.handle2.removeEventListener("keydown", this.handle2Keydown, false);

        if (this.showTooltip) {
          this.handle1.removeEventListener("focus", this.showTooltip, false);
          this.handle2.removeEventListener("focus", this.showTooltip, false);
        }
        if (this.hideTooltip) {
          this.handle1.removeEventListener("blur", this.hideTooltip, false);
          this.handle2.removeEventListener("blur", this.hideTooltip, false);
        }

        // Remove event listeners from sliderElem
        if (this.showTooltip) {
          this.sliderElem.removeEventListener("mouseenter", this.showTooltip, false);
        }
        if (this.hideTooltip) {
          this.sliderElem.removeEventListener("mouseleave", this.hideTooltip, false);
        }
        this.sliderElem.removeEventListener("touchstart", this.mousedown, false);
        this.sliderElem.removeEventListener("mousedown", this.mousedown, false);

        // Remove window event listener
        window.removeEventListener("resize", this.resize, false);
      },
      _bindNonQueryEventHandler: function _bindNonQueryEventHandler(evt, callback) {
        if (this.eventToCallbackMap[evt] === undefined) {
          this.eventToCallbackMap[evt] = [];
        }
        this.eventToCallbackMap[evt].push(callback);
      },
      _unbindNonQueryEventHandler: function _unbindNonQueryEventHandler(evt, callback) {
        var callbacks = this.eventToCallbackMap[evt];
        if (callbacks !== undefined) {
          for (var i = 0; i < callbacks.length; i++) {
            if (callbacks[i] === callback) {
              callbacks.splice(i, 1);
              break;
            }
          }
        }
      },
      _cleanUpEventCallbacksMap: function _cleanUpEventCallbacksMap() {
        var eventNames = Object.keys(this.eventToCallbackMap);
        for (var i = 0; i < eventNames.length; i++) {
          var eventName = eventNames[i];
          this.eventToCallbackMap[eventName] = null;
        }
      },
      _showTooltip: function _showTooltip() {
        if (this.options.tooltip_split === false) {
          this._addClass(this.tooltip, 'in');
          this.tooltip_min.style.display = 'none';
          this.tooltip_max.style.display = 'none';
        } else {
          this._addClass(this.tooltip_min, 'in');
          this._addClass(this.tooltip_max, 'in');
          this.tooltip.style.display = 'none';
        }
        this._state.over = true;
      },
      _hideTooltip: function _hideTooltip() {
        if (this._state.inDrag === false && this.alwaysShowTooltip !== true) {
          this._removeClass(this.tooltip, 'in');
          this._removeClass(this.tooltip_min, 'in');
          this._removeClass(this.tooltip_max, 'in');
        }
        this._state.over = false;
      },
      _layout: function _layout() {
        var positionPercentages;

        if (this.options.reversed) {
          positionPercentages = [100 - this._state.percentage[0], this.options.range ? 100 - this._state.percentage[1] : this._state.percentage[1]];
        } else {
          positionPercentages = [this._state.percentage[0], this._state.percentage[1]];
        }

        this.handle1.style[this.stylePos] = positionPercentages[0] + '%';
        this.handle1.setAttribute('aria-valuenow', this._state.value[0]);

        this.handle2.style[this.stylePos] = positionPercentages[1] + '%';
        this.handle2.setAttribute('aria-valuenow', this._state.value[1]);

        /* Position ticks and labels */
        if (Array.isArray(this.options.ticks) && this.options.ticks.length > 0) {

          var styleSize = this.options.orientation === 'vertical' ? 'height' : 'width';
          var styleMargin = this.options.orientation === 'vertical' ? 'marginTop' : 'marginLeft';
          var labelSize = this._state.size / (this.options.ticks.length - 1);

          if (this.tickLabelContainer) {
            var extraMargin = 0;
            if (this.options.ticks_positions.length === 0) {
              if (this.options.orientation !== 'vertical') {
                this.tickLabelContainer.style[styleMargin] = -labelSize / 2 + 'px';
              }

              extraMargin = this.tickLabelContainer.offsetHeight;
            } else {
              /* Chidren are position absolute, calculate height by finding the max offsetHeight of a child */
              for (i = 0; i < this.tickLabelContainer.childNodes.length; i++) {
                if (this.tickLabelContainer.childNodes[i].offsetHeight > extraMargin) {
                  extraMargin = this.tickLabelContainer.childNodes[i].offsetHeight;
                }
              }
            }
            if (this.options.orientation === 'horizontal') {
              this.sliderElem.style.marginBottom = extraMargin + 'px';
            }
          }
          for (var i = 0; i < this.options.ticks.length; i++) {

            var percentage = this.options.ticks_positions[i] || this._toPercentage(this.options.ticks[i]);

            if (this.options.reversed) {
              percentage = 100 - percentage;
            }

            this.ticks[i].style[this.stylePos] = percentage + '%';

            /* Set class labels to denote whether ticks are in the selection */
            this._removeClass(this.ticks[i], 'in-selection');
            if (!this.options.range) {
              if (this.options.selection === 'after' && percentage >= positionPercentages[0]) {
                this._addClass(this.ticks[i], 'in-selection');
              } else if (this.options.selection === 'before' && percentage <= positionPercentages[0]) {
                this._addClass(this.ticks[i], 'in-selection');
              }
            } else if (percentage >= positionPercentages[0] && percentage <= positionPercentages[1]) {
              this._addClass(this.ticks[i], 'in-selection');
            }

            if (this.tickLabels[i]) {
              this.tickLabels[i].style[styleSize] = labelSize + 'px';

              if (this.options.orientation !== 'vertical' && this.options.ticks_positions[i] !== undefined) {
                this.tickLabels[i].style.position = 'absolute';
                this.tickLabels[i].style[this.stylePos] = percentage + '%';
                this.tickLabels[i].style[styleMargin] = -labelSize / 2 + 'px';
              } else if (this.options.orientation === 'vertical') {
                this.tickLabels[i].style['marginLeft'] = this.sliderElem.offsetWidth + 'px';
                this.tickLabelContainer.style['marginTop'] = this.sliderElem.offsetWidth / 2 * -1 + 'px';
              }
            }
          }
        }

        var formattedTooltipVal;

        if (this.options.range) {
          formattedTooltipVal = this.options.formatter(this._state.value);
          this._setText(this.tooltipInner, formattedTooltipVal);
          this.tooltip.style[this.stylePos] = (positionPercentages[1] + positionPercentages[0]) / 2 + '%';

          if (this.options.orientation === 'vertical') {
            this._css(this.tooltip, 'margin-top', -this.tooltip.offsetHeight / 2 + 'px');
          } else {
            this._css(this.tooltip, 'margin-left', -this.tooltip.offsetWidth / 2 + 'px');
          }

          if (this.options.orientation === 'vertical') {
            this._css(this.tooltip, 'margin-top', -this.tooltip.offsetHeight / 2 + 'px');
          } else {
            this._css(this.tooltip, 'margin-left', -this.tooltip.offsetWidth / 2 + 'px');
          }

          var innerTooltipMinText = this.options.formatter(this._state.value[0]);
          this._setText(this.tooltipInner_min, innerTooltipMinText);

          var innerTooltipMaxText = this.options.formatter(this._state.value[1]);
          this._setText(this.tooltipInner_max, innerTooltipMaxText);

          this.tooltip_min.style[this.stylePos] = positionPercentages[0] + '%';

          if (this.options.orientation === 'vertical') {
            this._css(this.tooltip_min, 'margin-top', -this.tooltip_min.offsetHeight / 2 + 'px');
          } else {
            this._css(this.tooltip_min, 'margin-left', -this.tooltip_min.offsetWidth / 2 + 'px');
          }

          this.tooltip_max.style[this.stylePos] = positionPercentages[1] + '%';

          if (this.options.orientation === 'vertical') {
            this._css(this.tooltip_max, 'margin-top', -this.tooltip_max.offsetHeight / 2 + 'px');
          } else {
            this._css(this.tooltip_max, 'margin-left', -this.tooltip_max.offsetWidth / 2 + 'px');
          }
        } else {
          formattedTooltipVal = this.options.formatter(this._state.value[0]);
          this._setText(this.tooltipInner, formattedTooltipVal);

          this.tooltip.style[this.stylePos] = positionPercentages[0] + '%';
          if (this.options.orientation === 'vertical') {
            this._css(this.tooltip, 'margin-top', -this.tooltip.offsetHeight / 2 + 'px');
          } else {
            this._css(this.tooltip, 'margin-left', -this.tooltip.offsetWidth / 2 + 'px');
          }
        }

        if (this.options.orientation === 'vertical') {
          this.trackLow.style.top = '0';
          this.trackLow.style.height = Math.min(positionPercentages[0], positionPercentages[1]) + '%';

          this.trackSelection.style.top = Math.min(positionPercentages[0], positionPercentages[1]) + '%';
          this.trackSelection.style.height = Math.abs(positionPercentages[0] - positionPercentages[1]) + '%';

          this.trackHigh.style.bottom = '0';
          this.trackHigh.style.height = 100 - Math.min(positionPercentages[0], positionPercentages[1]) - Math.abs(positionPercentages[0] - positionPercentages[1]) + '%';
        } else {
          this.trackLow.style.left = '0';
          this.trackLow.style.width = Math.min(positionPercentages[0], positionPercentages[1]) + '%';

          this.trackSelection.style.left = Math.min(positionPercentages[0], positionPercentages[1]) + '%';
          this.trackSelection.style.width = Math.abs(positionPercentages[0] - positionPercentages[1]) + '%';

          this.trackHigh.style.right = '0';
          this.trackHigh.style.width = 100 - Math.min(positionPercentages[0], positionPercentages[1]) - Math.abs(positionPercentages[0] - positionPercentages[1]) + '%';

          var offset_min = this.tooltip_min.getBoundingClientRect();
          var offset_max = this.tooltip_max.getBoundingClientRect();

          if (offset_min.right > offset_max.left) {
            this._removeClass(this.tooltip_max, 'top');
            this._addClass(this.tooltip_max, 'bottom');
            this.tooltip_max.style.top = 18 + 'px';
          } else {
            this._removeClass(this.tooltip_max, 'bottom');
            this._addClass(this.tooltip_max, 'top');
            this.tooltip_max.style.top = this.tooltip_min.style.top;
          }
        }
      },
      _resize: function _resize(ev) {
        /*jshint unused:false*/
        this._state.offset = this._offset(this.sliderElem);
        this._state.size = this.sliderElem[this.sizePos];
        this._layout();
      },
      _removeProperty: function _removeProperty(element, prop) {
        if (element.style.removeProperty) {
          element.style.removeProperty(prop);
        } else {
          element.style.removeAttribute(prop);
        }
      },
      _mousedown: function _mousedown(ev) {
        if (!this._state.enabled) {
          return false;
        }

        this._state.offset = this._offset(this.sliderElem);
        this._state.size = this.sliderElem[this.sizePos];

        var percentage = this._getPercentage(ev);

        if (this.options.range) {
          var diff1 = Math.abs(this._state.percentage[0] - percentage);
          var diff2 = Math.abs(this._state.percentage[1] - percentage);
          this._state.dragged = diff1 < diff2 ? 0 : 1;
        } else {
          this._state.dragged = 0;
        }

        this._state.percentage[this._state.dragged] = percentage;
        this._layout();

        if (this.touchCapable) {
          document.removeEventListener("touchmove", this.mousemove, false);
          document.removeEventListener("touchend", this.mouseup, false);
        }

        if (this.mousemove) {
          document.removeEventListener("mousemove", this.mousemove, false);
        }
        if (this.mouseup) {
          document.removeEventListener("mouseup", this.mouseup, false);
        }

        this.mousemove = this._mousemove.bind(this);
        this.mouseup = this._mouseup.bind(this);

        if (this.touchCapable) {
          // Touch: Bind touch events:
          document.addEventListener("touchmove", this.mousemove, false);
          document.addEventListener("touchend", this.mouseup, false);
        }
        // Bind mouse events:
        document.addEventListener("mousemove", this.mousemove, false);
        document.addEventListener("mouseup", this.mouseup, false);

        this._state.inDrag = true;
        var newValue = this._calculateValue();

        this._trigger('slideStart', newValue);

        this._setDataVal(newValue);
        this.setValue(newValue, false, true);

        this._pauseEvent(ev);

        if (this.options.focus) {
          this._triggerFocusOnHandle(this._state.dragged);
        }

        return true;
      },
      _triggerFocusOnHandle: function _triggerFocusOnHandle(handleIdx) {
        if (handleIdx === 0) {
          this.handle1.focus();
        }
        if (handleIdx === 1) {
          this.handle2.focus();
        }
      },
      _keydown: function _keydown(handleIdx, ev) {
        if (!this._state.enabled) {
          return false;
        }

        var dir;
        switch (ev.keyCode) {
          case 37: // left
          case 40:
            // down
            dir = -1;
            break;
          case 39: // right
          case 38:
            // up
            dir = 1;
            break;
        }
        if (!dir) {
          return;
        }

        // use natural arrow keys instead of from min to max
        if (this.options.natural_arrow_keys) {
          var ifVerticalAndNotReversed = this.options.orientation === 'vertical' && !this.options.reversed;
          var ifHorizontalAndReversed = this.options.orientation === 'horizontal' && this.options.reversed;

          if (ifVerticalAndNotReversed || ifHorizontalAndReversed) {
            dir = -dir;
          }
        }

        var val = this._state.value[handleIdx] + dir * this.options.step;
        if (this.options.range) {
          val = [!handleIdx ? val : this._state.value[0], handleIdx ? val : this._state.value[1]];
        }

        this._trigger('slideStart', val);
        this._setDataVal(val);
        this.setValue(val, true, true);

        this._setDataVal(val);
        this._trigger('slideStop', val);
        this._layout();

        this._pauseEvent(ev);

        return false;
      },
      _pauseEvent: function _pauseEvent(ev) {
        if (ev.stopPropagation) {
          ev.stopPropagation();
        }
        if (ev.preventDefault) {
          ev.preventDefault();
        }
        ev.cancelBubble = true;
        ev.returnValue = false;
      },
      _mousemove: function _mousemove(ev) {
        if (!this._state.enabled) {
          return false;
        }

        var percentage = this._getPercentage(ev);
        this._adjustPercentageForRangeSliders(percentage);
        this._state.percentage[this._state.dragged] = percentage;
        this._layout();

        var val = this._calculateValue(true);
        this.setValue(val, true, true);

        return false;
      },
      _adjustPercentageForRangeSliders: function _adjustPercentageForRangeSliders(percentage) {
        if (this.options.range) {
          var precision = this._getNumDigitsAfterDecimalPlace(percentage);
          precision = precision ? precision - 1 : 0;
          var percentageWithAdjustedPrecision = this._applyToFixedAndParseFloat(percentage, precision);
          if (this._state.dragged === 0 && this._applyToFixedAndParseFloat(this._state.percentage[1], precision) < percentageWithAdjustedPrecision) {
            this._state.percentage[0] = this._state.percentage[1];
            this._state.dragged = 1;
          } else if (this._state.dragged === 1 && this._applyToFixedAndParseFloat(this._state.percentage[0], precision) > percentageWithAdjustedPrecision) {
            this._state.percentage[1] = this._state.percentage[0];
            this._state.dragged = 0;
          }
        }
      },
      _mouseup: function _mouseup() {
        if (!this._state.enabled) {
          return false;
        }
        if (this.touchCapable) {
          // Touch: Unbind touch event handlers:
          document.removeEventListener("touchmove", this.mousemove, false);
          document.removeEventListener("touchend", this.mouseup, false);
        }
        // Unbind mouse event handlers:
        document.removeEventListener("mousemove", this.mousemove, false);
        document.removeEventListener("mouseup", this.mouseup, false);

        this._state.inDrag = false;
        if (this._state.over === false) {
          this._hideTooltip();
        }
        var val = this._calculateValue(true);

        this._layout();
        this._setDataVal(val);
        this._trigger('slideStop', val);

        return false;
      },
      _calculateValue: function _calculateValue(snapToClosestTick) {
        var val;
        if (this.options.range) {
          val = [this.options.min, this.options.max];
          if (this._state.percentage[0] !== 0) {
            val[0] = this._toValue(this._state.percentage[0]);
            val[0] = this._applyPrecision(val[0]);
          }
          if (this._state.percentage[1] !== 100) {
            val[1] = this._toValue(this._state.percentage[1]);
            val[1] = this._applyPrecision(val[1]);
          }
        } else {
          val = this._toValue(this._state.percentage[0]);
          val = parseFloat(val);
          val = this._applyPrecision(val);
        }

        if (snapToClosestTick) {
          var min = [val, Infinity];
          for (var i = 0; i < this.options.ticks.length; i++) {
            var diff = Math.abs(this.options.ticks[i] - val);
            if (diff <= min[1]) {
              min = [this.options.ticks[i], diff];
            }
          }
          if (min[1] <= this.options.ticks_snap_bounds) {
            return min[0];
          }
        }

        return val;
      },
      _applyPrecision: function _applyPrecision(val) {
        var precision = this.options.precision || this._getNumDigitsAfterDecimalPlace(this.options.step);
        return this._applyToFixedAndParseFloat(val, precision);
      },
      _getNumDigitsAfterDecimalPlace: function _getNumDigitsAfterDecimalPlace(num) {
        var match = ('' + num).match(/(?:\.(\d+))?(?:[eE]([+-]?\d+))?$/);
        if (!match) {
          return 0;
        }
        return Math.max(0, (match[1] ? match[1].length : 0) - (match[2] ? +match[2] : 0));
      },
      _applyToFixedAndParseFloat: function _applyToFixedAndParseFloat(num, toFixedInput) {
        var truncatedNum = num.toFixed(toFixedInput);
        return parseFloat(truncatedNum);
      },
      /*
    Credits to Mike Samuel for the following method!
    Source: http://stackoverflow.com/questions/10454518/javascript-how-to-retrieve-the-number-of-decimals-of-a-string-number
   */
      _getPercentage: function _getPercentage(ev) {
        if (this.touchCapable && (ev.type === 'touchstart' || ev.type === 'touchmove')) {
          ev = ev.touches[0];
        }

        var eventPosition = ev[this.mousePos];
        var sliderOffset = this._state.offset[this.stylePos];
        var distanceToSlide = eventPosition - sliderOffset;
        // Calculate what percent of the length the slider handle has slid
        var percentage = distanceToSlide / this._state.size * 100;
        percentage = Math.round(percentage / this._state.percentage[2]) * this._state.percentage[2];
        if (this.options.reversed) {
          percentage = 100 - percentage;
        }

        // Make sure the percent is within the bounds of the slider.
        // 0% corresponds to the 'min' value of the slide
        // 100% corresponds to the 'max' value of the slide
        return Math.max(0, Math.min(100, percentage));
      },
      _validateInputValue: function _validateInputValue(val) {
        if (typeof val === 'number') {
          return val;
        } else if (Array.isArray(val)) {
          this._validateArray(val);
          return val;
        } else {
          throw new Error(ErrorMsgs.formatInvalidInputErrorMsg(val));
        }
      },
      _validateArray: function _validateArray(val) {
        for (var i = 0; i < val.length; i++) {
          var input = val[i];
          if (typeof input !== 'number') {
            throw new Error(ErrorMsgs.formatInvalidInputErrorMsg(input));
          }
        }
      },
      _setDataVal: function _setDataVal(val) {
        this.element.setAttribute('data-value', val);
        this.element.setAttribute('value', val);
        this.element.value = val;
      },
      _trigger: function _trigger(evt, val) {
        val = val || val === 0 ? val : undefined;

        var callbackFnArray = this.eventToCallbackMap[evt];
        if (callbackFnArray && callbackFnArray.length) {
          for (var i = 0; i < callbackFnArray.length; i++) {
            var callbackFn = callbackFnArray[i];
            callbackFn(val);
          }
        }

        /* If JQuery exists, trigger JQuery events */
        if ($) {
          this._triggerJQueryEvent(evt, val);
        }
      },
      _triggerJQueryEvent: function _triggerJQueryEvent(evt, val) {
        var eventData = {
          type: evt,
          value: val
        };
        this.$element.trigger(eventData);
        this.$sliderElem.trigger(eventData);
      },
      _unbindJQueryEventHandlers: function _unbindJQueryEventHandlers() {
        this.$element.off();
        this.$sliderElem.off();
      },
      _setText: function _setText(element, text) {
        if (typeof element.innerText !== "undefined") {
          element.innerText = text;
        } else if (typeof element.textContent !== "undefined") {
          element.textContent = text;
        }
      },
      _removeClass: function _removeClass(element, classString) {
        var classes = classString.split(" ");
        var newClasses = element.className;

        for (var i = 0; i < classes.length; i++) {
          var classTag = classes[i];
          var regex = new RegExp("(?:\\s|^)" + classTag + "(?:\\s|$)");
          newClasses = newClasses.replace(regex, " ");
        }

        element.className = newClasses.trim();
      },
      _addClass: function _addClass(element, classString) {
        var classes = classString.split(" ");
        var newClasses = element.className;

        for (var i = 0; i < classes.length; i++) {
          var classTag = classes[i];
          var regex = new RegExp("(?:\\s|^)" + classTag + "(?:\\s|$)");
          var ifClassExists = regex.test(newClasses);

          if (!ifClassExists) {
            newClasses += " " + classTag;
          }
        }

        element.className = newClasses.trim();
      },
      _offsetLeft: function _offsetLeft(obj) {
        return obj.getBoundingClientRect().left;
      },
      _offsetTop: function _offsetTop(obj) {
        var offsetTop = obj.offsetTop;
        while ((obj = obj.offsetParent) && !isNaN(obj.offsetTop)) {
          offsetTop += obj.offsetTop;
        }
        return offsetTop;
      },
      _offset: function _offset(obj) {
        return {
          left: this._offsetLeft(obj),
          top: this._offsetTop(obj)
        };
      },
      _css: function _css(elementRef, styleName, value) {
        if ($) {
          $.style(elementRef, styleName, value);
        } else {
          var style = styleName.replace(/^-ms-/, "ms-").replace(/-([\da-z])/gi, function (all, letter) {
            return letter.toUpperCase();
          });
          elementRef.style[style] = value;
        }
      },
      _toValue: function _toValue(percentage) {
        return this.options.scale.toValue.apply(this, [percentage]);
      },
      _toPercentage: function _toPercentage(value) {
        return this.options.scale.toPercentage.apply(this, [value]);
      },
      _setTooltipPosition: function _setTooltipPosition() {
        var tooltips = [this.tooltip, this.tooltip_min, this.tooltip_max];
        if (this.options.orientation === 'vertical') {
          var tooltipPos = this.options.tooltip_position || 'right';
          var oppositeSide = tooltipPos === 'left' ? 'right' : 'left';
          tooltips.forEach((function (tooltip) {
            this._addClass(tooltip, tooltipPos);
            tooltip.style[oppositeSide] = '100%';
          }).bind(this));
        } else if (this.options.tooltip_position === 'bottom') {
          tooltips.forEach((function (tooltip) {
            this._addClass(tooltip, 'bottom');
            tooltip.style.top = 22 + 'px';
          }).bind(this));
        } else {
          tooltips.forEach((function (tooltip) {
            this._addClass(tooltip, 'top');
            tooltip.style.top = -this.tooltip.outerHeight - 14 + 'px';
          }).bind(this));
        }
      }
    };

    /*********************************
      Attach to global namespace
    *********************************/
    if ($) {
      var namespace = $.fn.slider ? 'bootstrapSlider' : 'slider';
      $.bridget(namespace, Slider);
    }
  })($);

  return Slider;
});





// Bradseg Scripts
$(document).ready(function() {
	
    // --- Menu Principal 
      var main_menu_dd_size;

      $.each($(".nav-main-menu .dropdown"), function() {
        main_menu_dd_size = $(this).width();
        $(this).children(".dropdown-menu").width(main_menu_dd_size + 40);
      })

      $("#search-box").focus(function() {
        $(this).parent().addClass("search-focused");
        $(".nav-main-menu .navbar-item").css("display", "none");
      })

      $("#search-box").focusout(function() {
        $(this).parent().removeClass("search-focused");
        $(".nav-main-menu .navbar-item").css("display", "table-cell");
      })

      var nav_inst_inner = $("#nav-institucional").html();
      $("#nav-institucional-inner ul").append(nav_inst_inner);


    // --- Menu âncora
      var nav_menu_items = $(".nav-anchor").children(".content").children(".section-all-items").html();
      // if (!($(".nav-anchor .content .navbar-item .dropdown-menu").parent("pre"))) {
      $(".nav-anchor .content .navbar-item .dropdown-menu").append(nav_menu_items);
      // }

      $(".nav-anchor li.section-items a").click(function() {
        $(this).parent().parent().find(".active").removeClass("active");
        $(this).addClass("active");
      })

      navAnchorBehaviour();

    // --- Banners
      setBannerHeight();

      

    // Abas 
    $(".nav-abas a").click(function() {
        $(this).parent().children(".selected").removeClass("selected");
        $(this).addClass("selected");
    })
    $.each($(".nav-abas"), function() {
        $.each($(".nav-abas a"), function(i) {
            $(this).attr("id", "nav-btn-"+(i+1));
            $("#nav-btn-"+(i+1)).click(function() {
                $(".nav-abas-cnt div").hide();
                $("#nav-ctn-"+(i+1)).show();
            })
        })
        $.each($(".nav-abas-cnt div"), function(i) {
            $(this).attr("id", "nav-ctn-"+(i+1));
            if (i==0) {
                $(this).show();
            }
        })
    })

    // Lista Selecionável
    $(".bradseg-select-list li").click(function() {
        $(this).toggleClass("selected");
    })

	// Botão fluxo desabilitado
	if (($(".btn-prev .bradseg-btn:disabled")) && !($(".btn-prev .bradseg-btn").parent("pre"))) {
		$(".btn-prev .bradseg-btn:disabled").parent().find("span").css("color", "#a5aab6");
	}
	if (($(".btn-next .bradseg-btn:disabled")) && !($(".btn-next .bradseg-btn").parent("pre"))) {
		$(".btn-next .bradseg-btn:disabled").parent().find("span").css("color", "#a5aab6");
	}

	// Inicializaçao dos datepickers
	$('.datepicker').datepicker({
        format: "dd/mm/yyyy",
        todayHighlight: true
    }).on('changeDate', function(ev){
        $(this).datepicker('hide');
    });;

    // Máscaras de campos
    // $('.msg-error').hide();
    var validate =  {
      onComplete: function(val, event, thisField) {
        thisField.parent().removeClass('errorTextBox');
        // thisField.parent().find('.msg-error').hide();
      },
      onKeyPress: function(val, event, thisField, options){
        thisField.parent().addClass('errorTextBox');
        // thisField.parent().find('.msg-error').show();
      },
      onChange: function(val, event, thisField){

      },
      onInvalid: function(val, event, thisField, invalid, options){

      }
    };
    $('.mask-altura').mask('0,00')
    $('.mask-semcharespecial').mask('A',{'translation': {'A': {pattern: /[a-zA-Z0-9]/, recursive: true}}});
    $('.mask-bin').mask('0000 00');
    $('.mask-cep').mask('00.000-000', validate);
    $('.mask-cnpj').mask('00.000.000/0000-00', validate);
    $('.mask-cpf').mask('000.000.000-00', validate);
    $('.mask-cartao').mask('0000 0000 0000 0000', validate);
    $('.mask-data').mask('00/00/0000', validate);
    $('.mask-diaMes').mask('00');
    $('.mask-ano').mask('0000');
    $('.mask-dinheiro').mask('000.000.000.000.000,00', {reverse: true});
    $('.mask-numero').mask('#');
    $('.mask-porcentagem').mask('000%');
    var pesoBehaviour = function (val) {
      return val.replace(/\D/g, '').length === 5 ? '000,00' : val.replace(/\D/g, '').length === 4 ? '00,009' : '0,0099';
    },
    pesoOption = {
        onKeyPress: function(val, e, thisField, options) {
            thisField.mask(pesoBehaviour.apply({}, arguments), pesoOption);
        }
    };
    $('.mask-peso').mask(pesoBehaviour, pesoOption);
    $('.mask-placacarro').mask('AAA-0000', validate);
    var BRMaskBehavior = function (val) {
      return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
    },
    brOptions = {
        onKeyPress: function(val, e, thisField, options) {
            thisField.mask(BRMaskBehavior.apply({}, arguments), brOptions);
        }
    };
    $('.mask-ddd').mask('00');
    $('.mask-dddtelefone').mask(BRMaskBehavior, brOptions);
    var BRMaskOnlyPhoneBehavior = function (val) {
      return val.replace(/\D/g, '').length === 9 ? '00000-0000' : '0000-00009';
    },
    brOnlyPhoneOptions = {
        onKeyPress: function(val, e, thisField, options) {
            thisField.mask(BRMaskOnlyPhoneBehavior.apply({}, arguments), brOnlyPhoneOptions);
        }
    };
    $('.mask-telefone').mask(BRMaskOnlyPhoneBehavior, brOnlyPhoneOptions);

    // Tooltip
    $('[data-toggle="tooltip"]').tooltip(); 


    // Acessibilidade
    var smallCount = 0;
    var tallCount = 0;
    var fontCount = 0;
    var colorBlindCount = 0;

    $(".btn_smallFont").click(function() {
        if (fontCount > 0) {
            fontCount = fontCount - 1;
            $('body').css({'font-size': (10  + fontCount - 0.5) + 'px'});
            $('.dropdownProdutos').css({'font-size': (10  + fontCount - 0.5) + 'px'});
            $('.dropdownAtendimento').css({'font-size': (10  + fontCount - 0.5) + 'px'});
        }
    });
    $(".btn_tallFont").click(function() {
        if (fontCount < 3) {
            fontCount = fontCount + 1;
            $('body').css({'font-size': (10  + fontCount - 0.5) + 'px'});
            $('.dropdownProdutos').css({'font-size': (10  + fontCount - 0.5) + 'px'});
            $('.dropdownAtendimento').css({'font-size': (10  + fontCount - 0.5) + 'px'});
        }
    });

    $(".btn_colorBlind").click(function() {
        var blackwhite_href = $("#bradsegBlackWhite").attr("data-href");
        var highcontrast_href = $("#bradsegHighContrast").attr("data-href");

        if (colorBlindCount < 2) {
            if (colorBlindCount == 0) {

                document.getElementById('bradsegBlackWhite').href=blackwhite_href;
                document.getElementById('bradsegHighContrast').href='';

                $(".logo-bradseg").each(function() {
                    if ($(this).is("img")) {
                        var bradseg_logo_default = $(this).attr('src');
                        var bradseg_logo_grayscale = $(this).attr('data-grayscale');
                        var bradseg_logo_highcontrast = $(this).attr('data-highcontrast');

                        $(this).attr("src", bradseg_logo_grayscale);
                        $(this).attr("data-grayscale", bradseg_logo_default);

                    }
                })

                // $('[data-gray!=""]').each(function() {
                //   console.log("data-gray");
                //   if ($(this).is("div") || $(this).is("span")) {
                //     var attr = $(this).attr('data-gray');
                //     var original = $(this).css('background-image');

                //     if (typeof attr !== typeof undefined && attr !== false) {
                //       $(this).attr('data-gray', original);
                //       $(this).css('background-image', attr);
                //     }
                //     console.log(original);
                //   }
                //   if ($(this).is("img")) {
                //     var attr = $(this).attr('data-gray');
                //     var original = $(this).attr('src');

                //     if (typeof attr !== typeof undefined && attr !== false) {
                //       $(this).attr('data-gray', original);
                //       $(this).attr('src', attr);
                //     }
                //   }
                //   if ($(this).hasClass('slideContainer')) {
                //     var attr = $(this).attr('data-preview-gray');
                //     var original = $(this).attr('data-preview-thumbnail');

                //     if (typeof attr !== typeof undefined && attr !== false) {
                //       $(this).attr('data-preview-gray', original);
                //       $(this).attr('data-preview-thumbnail', attr);
                //     }
                //   }
                // });

                colorBlindCount = 1;
            } else if (colorBlindCount == 1) {
                document.getElementById('bradsegHighContrast').href=highcontrast_href;
                document.getElementById('bradsegBlackWhite').href='';

                $(".logo-bradseg").each(function() {
                    if ($(this).is("img")) {
                        var bradseg_logo_default = $(this).attr('src');
                        var bradseg_logo_grayscale = $(this).attr('data-grayscale');
                        var bradseg_logo_highcontrast = $(this).attr('data-highcontrast');

                        $(this).attr("src", bradseg_logo_highcontrast);
                        $(this).attr("data-grayscale", bradseg_logo_default);
                        $(this).attr("data-highcontrast", bradseg_logo_grayscale);

                    }
                })


                colorBlindCount = 2;
            }
        } else {
            document.getElementById('bradsegBlackWhite').href='';
            document.getElementById('bradsegHighContrast').href='';

            $(".logo-bradseg").each(function() {
                if ($(this).is("img")) {
                    var bradseg_logo_default = $(this).attr('src');
                    var bradseg_logo_grayscale = $(this).attr('data-grayscale');
                    var bradseg_logo_highcontrast = $(this).attr('data-highcontrast');

                    $(this).attr("src", bradseg_logo_highcontrast);
                    $(this).attr("data-highcontrast", bradseg_logo_default);

                }
            })
            // $('[data-gray!=""]').each(function() {
            //     if ($(this).is("div") || $(this).is("span")) {
            //         var attr = $(this).attr('data-gray');
            //         var original = $(this).css('background-image');

            //         if (typeof attr !== typeof undefined && attr !== false) {
            //             $(this).attr('data-gray', original);
            //             $(this).css('background-image', attr);
            //         }
            //     }
            //     if ($(this).is("img")) {
            //         var attr = $(this).attr('data-gray');
            //         var original = $(this).attr('src');

            //         if (typeof attr !== typeof undefined && attr !== false) {
            //             $(this).attr('data-gray', original);
            //             $(this).attr('src', attr);
            //         }
            //     }
            //     if ($(this).hasClass('slideContainer')) {
            //         var attr = $(this).attr('data-preview-gray');
            //         var original = $(this).attr('data-preview-thumbnail');

            //         if (typeof attr !== typeof undefined && attr !== false) {
            //             $(this).attr('data-preview-gray', original);
            //             $(this).attr('data-preview-thumbnail', attr);
            //         }
            //     }
            // });

            colorBlindCount = 0;
        }
    })
})

$(document).scroll(function () {
  navAnchorFixed();
});

$(window).resize(function() {
  setBannerHeight();
  navAnchorBehaviour();
  navAnchorFixed();
})

function navAnchorFixed() {
  if ($(window).width() > 769) {
    if (!($("body").hasClass("styleguide"))) {
      var y = $(this).scrollTop();
      var nav_ancora_position = $("#nav-anchor").offset().top;
      var nav_main_position = $(".nav-main-menu").offset().top + 70;

      if (y > nav_ancora_position) {
        $(".nav-anchor").addClass("fixed");
      } else {
        $(".nav-anchor").removeClass("fixed");
      }

      if (y > nav_main_position) {
        $(".nav-anchor .logo-bradseg").fadeIn();
      } else {
        $(".nav-anchor .logo-bradseg").fadeOut();
      }
    }
  } else {
    $(".nav-anchor").removeClass("fixed");  
  }
  
}

function navAnchorBehaviour() {
    var anchor_size = $(".nav-anchor .content").width() - 40;
    var anchor_logo_size = $(".nav-anchor .content .logo-bradseg").width() + 32;
    var anchor_vertical_size = $(".nav-anchor .item-vertical").width() + 34;
    var anchor_session_size = $(".nav-anchor .section-all-items").width() + 32;
    var total_elem_size;

    total_elem_size = anchor_logo_size + anchor_vertical_size + anchor_session_size;

    if (anchor_size > total_elem_size) {
        $(".nav-anchor .content .section-all-items").css("display", "inline-block")
        $(".nav-anchor .content>.dropdown").css("display", "none")
    } else {
        $(".nav-anchor .content .section-all-items").css("display", "none")
        $(".nav-anchor .content>.dropdown").css("display", "inline-block")
    }
}

function setBannerHeight() {
  window_height = $(window).height();
  $(".bradseg-img-banner").height(window_height - 68);
}