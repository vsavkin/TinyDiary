<head>
<meta name="layout" content="main"/>
<title>Your Posts</title>
</head>
<body>
	<h1>Post on <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></h1>
		
	<g:each in="${post.parts}" var="part">
		<div class="postPart">
			<div class="type">${part.type}</div>
			<div class="text">${part.textAsHtml}</div>
		</div>
	</g:each>

	<div class="formRowButton">
		<g:link action="edit" id="${post.id}" class="buttonLike">Edit</g:link>

		<span style='float:right;'>
			<g:if test="${hasPrev}">
				<g:link action="prevPost" id="${post.id}" class="buttonLike">Prev</g:link>
			</g:if>
			<g:if test="${hasNext}">
				<g:link action="nextPost" id="${post.id}" class="buttonLike">Next</g:link>
			</g:if>
		</span>
	</div>
</body>