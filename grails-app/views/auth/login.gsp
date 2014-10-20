<!doctype html>
<html>

<head>
  <meta name="layout" content="basic">
  <title><g:message code="auth.login.label"/></title>
</head>

<body>

  <div class="row-fluid">
    <div id="table_div" class="col-md-12">
      <g:if test="${flash.message}">
        <div class="alert alert-warning alert-dismissible" role="alert">
          <button type="button" class="close" data-dismiss="alert">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <div class="message" role="status">${flash.message}</div>
        </div>
      </g:if>
    </div>
  </div>

  <div class="row-fluid">
    <div id="table_div" class="col-md-4 col-md-offset-4">
      <h1 class="text-center login-title">
        <g:message code="auth.login.label" />
      </h1>
      <div class="account-wall">
        <img src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png" alt="" class="profile-img">
        <g:form action="signIn" class="form-signin">
          <input type="hidden" name="targetUri" value="${targetUri}" />
          <input type="text" name="username" value="${username}" class="form-control" placeholder="<g:message code="auth.username.label"/>" required="true" autofocus="true" autocomplete='off'>
          <input type="password" name="password" value="" class="form-control" placeholder="<g:message code="auth.password.label"/>" required="true">
          <button class="btn btn-lg btn-primary btn-block" type="submit">
            <g:message code="auth.button.login.label"/>
          </button>
        </g:form>
      </div>
    </div>
  </div>

</body>

</html>
