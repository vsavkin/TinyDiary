<head>
<meta name="layout" content="main"/>
<title>Registration</title>
</head>
<body>
	<h1>Register New User</h1>
	<g:hasErrors bean="${urc}">
    	<g:renderErrors bean="${urc}" as="list" />
	</g:hasErrors>
	
	<g:form>
		<div>
			Username <g:textField name="username" value="${urc?.username}"/>
		</div>
		<div>
			Password <g:passwordField name="password"/>
		</div>
		<div>
			Password Repeat <g:passwordField name="passwordRepeat"/>
		</div>
		<g:submitButton name="Submit" value="Register!"/>
	</g:form>
</body>