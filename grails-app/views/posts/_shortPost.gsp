<g:each var="post" in="${posts}">
	<div class="shortPost">
		<div class="upperLine">
			<g:formatDate format="yyyy-MM-dd" date="${post.dateCreated}"/>
			<span class="rightButtons">
				<g:link controller="post" action="show" id="${post.id}">Read</g:link>
				<g:link controller="post" action="edit" id="${post.id}">Edit</g:link>
			</span>
		</div>

		<g:if test="${post.parts}">
			<g:each var="part" in="${post.parts}">
				<g:if test="${selectedType == null || selectedType == part.type}">
					<div class="postPart">
						<div class="type">${part.type}</div>
						<div class="text">${part.textAsHtml}</div>
					</div>
				</g:if>
			</g:each>
		</g:if>
		<g:else>
			Empty
		</g:else>
	</div>
</g:each>