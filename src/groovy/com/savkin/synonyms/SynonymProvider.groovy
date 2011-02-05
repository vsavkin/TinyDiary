package com.savkin.synonyms

class SynonymProvider {

	private requester

	SynonymProvider(SynonymRequesterInternal requester) {
		this.requester = requester
	}

	List<SynonymResultItem> getSynonymsFor(String word) {
		try {
			def root = new XmlParser().parseText(requester.request(word))
			return root.result.collect {
				new SynonymResultItem(
					term: it.term.text(),
					partOfSpeech: it.partofspeech.text(),
					definition: it.definition.text(),
					synonyms: it.synonyms.text(),
					antonyms: it.antonyms.text(),
					example: it.example.text()
				)
			}
		} catch (Exception e) {
			throw new SynonymException(e)
		}
	}
}
