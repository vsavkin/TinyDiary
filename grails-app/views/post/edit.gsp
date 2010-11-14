<head>
<meta name="layout" content="main"/>
<title>Edit Post</title>
</head>
<body>
	<script type="text/javascript" src="/GrailsDiary/js/jquery/autoresize.jquery.min.js"></script>
	
	<jq:jquery>
		$('#text').autoResize({
		    onResize : function() {$(this).css({opacity:0.8});},
		    animateCallback : function() {$(this).css({opacity:1});},
		    animateDuration : 100,
		    extraSpace : 40,
		    animate: true
		});
	</jq:jquery>

	<h1>Create Post on <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></h1>
	
	<g:hasErrors bean="${post}">
    	<g:renderErrors bean="${post}" as="list" />
	</g:hasErrors>
			
	<g:form action="update">
		<g:hiddenField name="id" value="${post.id}"/>
		<g:hiddenField name="dateCreated" value="${post.dateCreated}"/>
		<g:textArea name="text" value="${post?.text}"/>
		<g:submitButton name="submit" value="Save"/>
	</g:form>
</body>