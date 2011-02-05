package com.savkin.synonyms

import spock.lang.Specification

class SynonymProviderSpec extends Specification {

	def 'should return synonyms for a passed word'(){
		setup:
		def requester = Mock(SynonymRequesterInternal)
		def provider = new SynonymProvider(requester)

		when:
		def res = provider.getSynonymsFor(WORD)

		then:
		requester.request(WORD) >> RESPONSE
		res.size() == 2
		res[0].term == 'dog'
		res[0].partOfSpeech == 'noun'
		res[0].definition == 'definition'
		res[0].synonyms == 'syn1, syn2'
		res[0].antonyms == 'ant1, ant2'
		res[0].example == 'example'

		res[1].term == 'cat'

		where:
		WORD = 'dog'
		RESPONSE = '''
		<?xml version="1.0" encoding="UTF-8"?>
		<results>
		  <result>
			<term>dog</term>
			<definition>definition</definition>
			<partofspeech>noun</partofspeech>
			<synonyms>syn1, syn2</synonyms>
			<antonyms>ant1, ant2</antonyms>
			<example>example</example>
		  </result>
		  <result>
			<term>cat</term>
			<definition>definition2</definition>
			<partofspeech>adj</partofspeech>
			<synonyms>syn1, syn2</synonyms>
			<antonyms>ant1, ant2</antonyms>
		  </result>
		</results>
		'''.trim()
	}

	def 'should throw an exception if something an exception occurred'(){
		setup:
		def requester = Mock(SynonymRequesterInternal)
		def provider = new SynonymProvider(requester)

		when:
		provider.getSynonymsFor(WORD)

		then:
		requester.request(WORD) >>  'INVALID'
		thrown(SynonymException)

		where:
		WORD = 'word'
	}
}
