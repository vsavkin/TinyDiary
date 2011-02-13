package com.savkin.synonyms

class SynonymProviderImpl implements SynonymProvider{

	private requester
	int maxItems = 3

	SynonymProviderImpl(SynonymRequesterInternal requester) {
		this.requester = requester
	}

	SynonymProviderImpl(String url, String devCode) {
		this.requester = new SynonymRequesterInternal(url: url, devCode: devCode)
	}

	List<SynonymResultItem> getSynonymsFor(String word) {
		try {
			def root = new XmlParser().parseText(requester.request(word))
			def items = root.result.collect {
				new SynonymResultItem(
					term: it.term.text(),
					partOfSpeech: it.partofspeech.text(),
					definition: it.definition.text(),
					synonyms: addSpaces(it.synonyms.text()),
					antonyms: addSpaces(it.antonyms.text()),
					example: it.example.text()
				)
			}
			items.size() > maxItems ? items[0..<maxItems] : items
		} catch (Exception e) {
			throw new SynonymException(e)
		}
	}

	private addSpaces(String s){
		s.replaceAll(/\,\b/, ', ')
	}
}
