<div class="shortPost">
	<div class="upperLine">
		The post created on <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/>
		<g:link controller="post" action="show" id="${post.id}">Read</g:link>
		<g:link controller="post" action="edit" id="${post.id}">Edit</g:link>
	</div>
		
	<g:if test="${post.parts}">
		<g:each var="part" in="${post.parts}">
			<div class="postPart">
				<div class="type">${part.type}</div>
				<div class="text">${part.textAsHtml}</div>
			</div>
		</g:each>
	</g:if>
	<g:else>
		Empty
	</g:else>
</div>