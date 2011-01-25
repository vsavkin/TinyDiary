<head>
<meta name='layout' content='main' />
<title>Login</title>
</head>

<body>
<h1>Please Login..</h1>
<g:if test='${flash.message}'>
	<div class='login_message'>
	${flash.message}
	</div>
</g:if>
<form action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
	<div class="formRow">
		<label for='username'>Login ID</label> 
		<g:textField name="j_username" id="username"/>
	</div>
	<div class="formRow">
		<label for='password'>Password</label> 
		<g:passwordField name='j_password' id='password'/>
	</div>
	
	<div class="formRow">
		<label for='remember_me'>Remember me</label> 
		<input type='checkbox' name='${rememberMeParameter}'
		id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if> />
	</div>
	
	<div class="formRowButton">
		<input type='submit' value='Login' />
	</div>
</form>
</div>
</div>

<script type='text/javascript'>
<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
//-->
</script>
</body>
