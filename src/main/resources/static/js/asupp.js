/*
	Sticky menu script
 */

(function(w,d,undefined){
	var el_html = d.documentElement,
	el_body = d.getElementsByTagName('body')[0],
	header = d.getElementById('header'),
	menuIsStuck = function(triggerElement) {
		var _scrollTop	= w.pageYOffset || el_body.scrollTop,
		regexp = /(nav\-is\-stuck)/i,
		regexp2 = /(nav\-is\-remonte)/i,
		classFound	= el_html.className.match( regexp ),
		classFound2	= el_html.className.match( regexp2 ),
		navHeight	= header.offsetHeight,
		bodyRect	= el_body.getBoundingClientRect(),
		scrollValue	= triggerElement ? triggerElement.getBoundingClientRect().top - bodyRect.top - navHeight  : 0,
		scrollValFix = classFound ? scrollValue : scrollValue + navHeight;

		if ( _scrollTop > scrollValFix && !classFound && !classFound2 ) {
			el_html.className = el_html.className + ' nav-is-stuck';
		}
		if ( _scrollTop > scrollValFix && classFound2 ) {
			el_html.className = el_html.className.replace( regexp2, ' nav-is-stuck' );
		}	
		if ( _scrollTop <= 50 && classFound ) {
			el_html.className = el_html.className.replace( regexp, ' nav-is-remonte');
		}
	},
	onScrolling = function() {
		menuIsStuck( d.getElementById('main') );
	};

	el_html.className = el_html.className + ' js';

	w.addEventListener('scroll', function(){
		w.requestAnimationFrame( onScrolling );
	});

}(window, document));