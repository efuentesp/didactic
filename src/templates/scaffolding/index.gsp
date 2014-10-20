<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>

	<body>

		<div class="row-fluid">
			<div id="table_div" class="col-md-12">
				<ol class="breadcrumb">
					<li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				  <li class="active"><g:message code="default.list.label" args="[entityName]" /></li>
				</ol>
			</div>
		</div>

		<div class="row-fluid">
		  <div id="table_div" class="col-md-12">
				<div>
				    <h1 class="pull-left"><g:message code="default.list.label" args="[entityName]" /></h1>
				    <div class="pull-right">
				        <g:link class="btn btn-primary" action="create" style="margin-right:10px">
				        	<i class="fa fa-plus-circle"></i>  <g:message code="default.new.label" args="[entityName]" />
				        </g:link>

				        <div id="quicksearch" class="search-box">
				        </div>
				    </div>
				</div>
			</div>
		</div>

		<div class="row-fluid">
		    <div id="table_div" class="col-md-12">

					<g:if test="\${flash.message}">
						<div class="alert alert-warning alert-dismissible" role="alert">
						  <button type="button" class="close" data-dismiss="alert">
						  	<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						  </button>
						  <div class="message" role="status">\${flash.message}</div>
						</div>
					</g:if>

					<table class="table table-striped table-condensed sortable">
						<thead>
							<tr>
							<%  excludedProps = Event.allEvents.toList() << 'id' << 'version' << 'dateCreated' << 'lastUpdated'
								allowedNames = domainClass.persistentProperties*.name
								props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) && (domainClass.constrainedProperties[it.name] ? domainClass.constrainedProperties[it.name].display : true) }
								Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
								props.eachWithIndex { p, i ->
									if (i < 6) {
										if (p.isAssociation()) { %>
								<th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
							<%      } else { %>
								<g:sortableColumn property="${p.name}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" />
							<%  }   }   } %>
							<th></th>
							</tr>
						</thead>
						<tbody>
						<g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
							<tr class="\${(i % 2) == 0 ? 'even' : 'odd'}">
							<%  props.eachWithIndex { p, i ->
									if (i == 0) { %>
								<td><g:link action="show" id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
							<%      } else if (i < 6) {
										if (p.type == Boolean || p.type == boolean) { %>
								<td><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
							<%          } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
								<td><g:formatDate date="\${${propertyName}.${p.name}}" /></td>
							<%          } else { %>
								<td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
							<%  }   }   } %>
							<td>
				        <g:link action="edit" id="\${${propertyName}.id}" data-toggle="tooltip" data-placement="left" title="\${message(code: 'default.button.edit.label', default: 'Edit')}">
				        	<i class="fa fa-edit"></i>
				        </g:link>
				        <g:link action="delete" id="\${${propertyName}.id}" data-toggle="tooltip" data-placement="left" title="\${message(code: 'default.button.delete.label', default: 'Delete')}">
				        	<i class="fa fa-close"></i>
				        </g:link>
							</td>
							</tr>
						</g:each>
						</tbody>
				    <tfoot>
				        <tr>
				            <td colspan="7">
				            	<g:pagination total="\${${propertyName}Count ?: 0}" />
				            </td>
				        </tr>
				    </tfoot>
					</table>
		    </div>
		</div>

	</body>
</html>
