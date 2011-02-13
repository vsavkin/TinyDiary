// Place your Spring DSL code here
beans = {
	synonymProvider (
		com.savkin.synonyms.SynonymProviderImpl,
		'http://www.abbreviations.com/services/v1/syno.aspx',
		'tk1421'
	)
}
