package com.savkin

class DiaryTagLib {
	static namespace = "diary"
	
	def autoresizedTextArea = {attrs, body->
		def pathToJsLib = createLinkTo(dir:'js/jquery',file:'autoresize.jquery.min.js')
		out << "<script type=\"text/javascript\" src=\"${pathToJsLib}\"></script>"
		out << jq.jquery([:], {'''
			$('#text').autoResize({
				onResize : function() {$(this).css({opacity:0.8});},
				animateCallback : function() {$(this).css({opacity:1});},
				animateDuration : 100,
				extraSpace : 40,
				animate: true
			});'''})
		out << g.textArea(name: attrs.name, value: attrs.value)
	}
}
