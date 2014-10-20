<nav id="Navbar" class="navbar navbar-fixed-top navbar-inverse" role="navigation">
	<div class="container">
	
	    <div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        		<span class="sr-only">Toggle navigation</span>
        		<span class="icon-bar"></span>
	           	<span class="icon-bar"></span>
	           	<span class="icon-bar"></span>
			</button>
	
			<a class="navbar-brand" href="${createLink(uri: '/')}">
				<img class="logo" src="${assetPath(src: 'logo.png')}" alt="${meta(name:'app.name')}" width="16px" height="16px"/> 
				${meta(name:'app.name')}
				<small> v${meta(name:'app.version')}</small>
			</a>
		</div>

		<div class="collapse navbar-collapse navbar-ex1-collapse" role="navigation">

			<ul class="nav navbar-nav">
				<g:render template="/_menu/mainmenu"/>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<shiro:isLoggedIn>
					<g:render template="/_menu/user"/>
					<g:render template="/_menu/logout"/>
				</shiro:isLoggedIn>
			</ul>			

		</div>
	</div>
</nav>