<head>
<meta name="layout" content="main"/>
<title>Your Posts</title>
</head>
<body>
	<h1>Post at <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></h1>
		
	<g:each in="${post.parts}" var="part">
		<h2>${part.type}</h2>
		<div>${part.text}</div>
	</g:each>
</body>