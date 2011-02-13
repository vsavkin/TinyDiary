package com.savkin

class DiaryTagLib {
	static namespace = "diary"
	
	def autoresizedTextArea = {attrs, body->

		def synonymsUrl = createLink(controller: "synonym", action: "synonymsFor");

		def synonyms = """
				function getSelectedText(){
					if(window.getSelection){
						return window.getSelection().toString();
					}else if(document.getSelection){
						return document.getSelection();
					}else if(document.selection){
						return document.selection.createRange().text;
					}
				}

				function isWord(text){
					return text.length >= 3 && text.match(/^[a-zA-Z]+\$/);
				}

				function makeAjaxCall(word){
					jQuery.get('$synonymsUrl/' + word, function(data) {
						jQuery('<div></div>').html(data)
							.dialog({
								autoOpen: true,
								width: 650,
								title: word
							});
					});
				}
			"""

		def pathToJsLib = createLinkTo(dir:'js/jquery',file:'autoresize.jquery.min.js')
		out << "<script type=\"text/javascript\" src=\"${pathToJsLib}\"></script>"
		
		def elementId = "#${attrs.name}"
		def js = """
			\$('$elementId').autoResize({
				onResize : function() {\$(this).css({opacity:0.8});},
				animateCallback : function() {\$(this).css({opacity:1});},
				animateDuration : 100,
				extraSpace : 40,
				animate: true
			});

			\$('$elementId').click(function() {
				var text = getSelectedText();
				if(isWord(text)){
					makeAjaxCall(text);
				}
			});
		"""

		out << jq.jquery([:], {synonyms + js})
		out << g.textArea(name: attrs.name, value: attrs.value)
	}
}
