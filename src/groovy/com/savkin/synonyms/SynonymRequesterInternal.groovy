package com.savkin.synonyms

class SynonymRequesterInternal {
	String url
	String devCode

	String request(String word) {
		def thisUrl = new URL("${url}?tokenid=${devCode}&word=${word}")
		def connection = thisUrl.openConnection()

		if (connection.responseCode == 200) {
			return connection.content.text
		} else {
			throw new RuntimeException("Invalid response code: ${connection.responseCode}")
		}
	}
}
