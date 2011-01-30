<g:if test="${types}">
	<g:remoteLink action="showAllParts" update="posts">all</g:remoteLink>
	<g:each var="partType" in="${types}">
		<g:remoteLink action="showParts" params="[selectedType: partType]" update="posts">${partType}</g:remoteLink>
	</g:each>
</g:if>