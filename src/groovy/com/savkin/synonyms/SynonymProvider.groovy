package com.savkin.synonyms

interface SynonymProvider {
	List<SynonymResultItem> getSynonymsFor(String word)
}