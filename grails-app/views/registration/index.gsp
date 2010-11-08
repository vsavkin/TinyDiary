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
		<div class="formRow">
			<label for="username">User name</label> <g:textField name="username" id="username" value="${urc?.username}"/>
		</div>
		<div class="formRow">
			<label for="password">Password</label>  <g:passwordField name="password" id="password"/>
		</div>
		<div class="formRow">
			<label for="passwordRepeat">Password Repeat</label>  <g:passwordField name="passwordRepeat" id="passwordRepeat"/>
		</div>
		<div class="formRow">
			<g:submitButton name="Submit" value="Register!"/>
		</div>
	</g:form>
</body>