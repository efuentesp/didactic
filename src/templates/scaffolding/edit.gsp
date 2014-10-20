<%=packageName%>
<!doctype html>
<html>

  <head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
  </head>

  <body>

    <div class="row-fluid">
      <div id="table_div" class="col-md-12">
        <ol class="breadcrumb">
          <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
          <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
          <li class="active"><g:message code="default.edit.label" args="[entityName]" /></li>
        </ol>
      </div>
    </div>

    <div class="row-fluid">
      <div id="table_div" class="col-md-12">
        <div id="edit-${domainClass.propertyName}" class="content scaffold-edit" role="main">

          <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
          <hr>

          <g:if test="\${flash.message}">
            <div class="alert alert-warning alert-dismissible" role="alert">
              <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
              </button>
              <div class="message" role="status">\${flash.message}</div>
            </div>
          </g:if>

          <g:hasErrors bean="\${${propertyName}}">
            <div class="alert alert-danger alert-dismissible" role="alert">
              <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
              </button>
              <ul class="errors" role="alert">
                <g:eachError bean="\${${propertyName}}" var="error">
                  <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
                </g:eachError>
              </ul>
            </div>
          </g:hasErrors>

          <g:form class="form-horizontal" role="form" method="put" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
            <g:hiddenField name="id" value="\${${propertyName}?.id}" />
            <g:hiddenField name="version" value="\${${propertyName}?.version}" />
            <fieldset class="form">
              <f:all bean="${propertyName}"/>
            </fieldset>
            <fieldset class="buttons">
              <button type="submit" class="btn btn-success" name="_action_update">
                <i class="fa fa-save"></i>  <g:message code="default.button.update.label" default='Update' />
              </button>
            </fieldset>
          </g:form>

        </div>
      </div>
    </div>

  </body>

</html>
