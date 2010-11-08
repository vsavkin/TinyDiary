<head>
<meta name="layout" content="main"/>
<title>Your Posts</title>
</head>
<body>
	<h1>Post on <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></h1>
		
	<g:each in="${post.parts}" var="part">
		<div class="postPart">
			<div class="type">${part.type}</div>
			<div class="text">${part.text}</div>
		</div>
	</g:each>
</body>