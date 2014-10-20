<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>

  <head>
    <meta name="layout" content="admin">
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
    <title><g:message code="default.delete.label" args="[entityName]" /></title>
  </head>

  <body>

    <div class="row-fluid">
      <div id="table_div" class="col-md-12">
        <ol class="breadcrumb">
          <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
          <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
          <li class="active"><g:message code="default.delete.label" args="[entityName]" /></li>
        </ol>
      </div>
    </div>

    <div class="row-fluid">
      <div id="table_div" class="col-md-12">
        <div id="delete-${domainClass.propertyName}" class="content scaffold-delete" role="main">

          <h1><g:message code="default.delete.label" args="[entityName]" /></h1>
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

          <g:form class="form-horizontal" role="form" url="[resource:${propertyName}, action:'delete']" method="DELETE">

            <g:hiddenField name="id" value="\${${propertyName}?.id}" />
            <g:hiddenField name="version" value="\${${propertyName}?.version}" />

            <%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
              allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
              props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && (domainClass.constrainedProperties[it.name] ? domainClass.constrainedProperties[it.name].display : true) }
              Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
              props.each { p -> %>
              <g:if test="\${${propertyName}?.${p.name}}">
                <div class="form-group">
                  <label for="${p.name}-label" class="col-sm-2 control-label">
                    <g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" />
                  </label>
                  <div class="col-sm-10" style="padding-top: 7px">
                    <f:display bean="\${${propertyName}}" property="${p.name}" class="form-control"/>
                  </div>
                </div>
              </g:if>
            <%  } %>

            <fieldset class="buttons">
              <button type="submit" class="btn btn-danger" name="_action_destroy" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                <i class="fa fa-trash"></i>  <g:message code="default.button.delete.label" default='Delete' />
              </button>
            </fieldset>

          </g:form>

        </div>
      </div>
    </div>

  </body>

</html>
