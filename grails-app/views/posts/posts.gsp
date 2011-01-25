<head>
	<meta name='layout' content='main' />
	<title>Your Diary</title>
</head>
<body>
	<h1>Your Posts</h1>
	
	<div class="postSelector">
		<div class="formRow">
			<g:form action="between">
				All posts from <g:datePicker name="from" value="${from}" precision="day"/>
				to <g:datePicker name="to" value="${to}" precision="day"/>
				<g:submitButton name="Submit" value="Find"/>
			</g:form>
		</div>
		
		<div class="formRow">
			or <g:link action="index">10 Latest Posts</g:link>
		</div>
	</div>
	
	
	<g:render template="shortPost" collection="${posts}" var="post"/>
</body>