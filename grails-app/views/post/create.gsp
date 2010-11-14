<head>
<meta name="layout" content="main"/>
<title>Create New Post</title>
</head>
<body>
	<h1>Create Post on <g:formatDate format="yyyy-MM-dd" date="${new Date()}"/></h1>
	
	<g:hasErrors bean="${post}">
    	<g:renderErrors bean="${post}" as="list" />
	</g:hasErrors>
			
	<g:form action="save">
		<diary:autoresizedTextArea name="text"/>
		<g:submitButton name="submit" value="Create!"/>
	</g:form>
</body>