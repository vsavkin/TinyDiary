<div>
	<div>Created <g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/></div>
	
	<g:if test="${post.parts}">
		<g:each var="part" in="${post.parts}">
			<div>
				<span>${part.type}</span>
				<span>${part.text}</span>
			</div>
		</g:each>
		<g:link controller="post" action="show" id="${post.id}">Read</g:link>|
		<g:link controller="post" action="edit" id="${post.id}">Edit</g:link>
	</g:if>
	<g:else>
		Empty
	</g:else>
</div>