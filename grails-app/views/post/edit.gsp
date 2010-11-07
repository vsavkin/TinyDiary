<head>
<meta name="layout" content="main"/>
<title>Edit Post</title>
</head>
<body>
	<h1>Create Post at <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></h1>
	
	<g:hasErrors bean="${post}">
    	<g:renderErrors bean="${post}" as="list" />
	</g:hasErrors>
			
	<g:form action="update">
		<g:hiddenField name="id" value="${post.id}"/>
		<g:textArea name="text" value="${post?.text}"/>
		<g:submitButton name="submit" value="Save"/>
	</g:form>
</body>