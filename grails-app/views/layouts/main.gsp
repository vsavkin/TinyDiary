<html>
	<head>
		<title><g:layoutTitle default="TinyDiary" /></title>
		<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
		<link rel="stylesheet" href="${resource(dir:'css/ui-lightness',file:'jquery-ui-1.8.9.custom.css')}" />
		<link rel="shortcut icon"
			href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
		<g:layoutHead />
		
		<g:javascript library="application" />		
		<g:javascript library="jquery" plugin="jquery"/>
		<g:javascript library="jquery-ui" src="jquery/jquery-ui-1.8.9.custom.min.js"/>
	</head>
<body>

<div id="logo">
	TinyDiary
	<div id="comment">Keep Track of Your Productive Days</div>
</div>

<div id="nav">
	<sec:ifLoggedIn>
		Hello <sec:username/>! <g:link controller="logout">Sign out</g:link> 
		<g:link controller="posts">Your Posts</g:link>
		<g:link controller="post" action="create">Create New Post</g:link>
	</sec:ifLoggedIn>
	<sec:ifNotLoggedIn>
		<g:link controller="login" action="auth">Sign in</g:link> or 	
		<g:link controller="registration">Create new account</g:link>	
	</sec:ifNotLoggedIn>
</div>

<div id="content">
	<g:layoutBody />
</div>

</body>
</html>