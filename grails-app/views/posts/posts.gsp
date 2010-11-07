<head>
	<meta name='layout' content='main' />
	<title>Your Diary</title>
</head>
<body>
	<h1><g:link controller="post" action="create">Create New Post</g:link></h1>
	<h1>Your Posts:</h1>
	
	<div>
		<g:link action="index">10 Latest</g:link>
		Or
		<g:form action="between">
			From <g:datePicker name="from" value="${from}" precision="day"/>
			To <g:datePicker name="to" value="${to}" precision="day"/>
			<g:submitButton name="Submit" value="Find"/>
		</g:form>
	</div>
	
	
	<g:render template="shortPost" collection="${posts}" var="post"/>
</body>