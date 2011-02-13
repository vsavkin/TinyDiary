<g:each in="${items}" var="${item}">
	<div class="synItem">
		<div class="synHeader">
			${item.term} - ${item.definition}
		</div>
		<div class="synExample">
			${item.example}
		</div>
		<div class="synBody">
			${item.synonyms}
		</div>
		<div class="synBody">
			${item.antonyms}
		</div>
	</div>
</g:each>