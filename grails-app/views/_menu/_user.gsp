<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="grails.plugin.core.auth.User"%>

<g:set var="currentUser" value="${User.findByUsername(SecurityUtils.subject.principal)}" />

<li class="dropdown">
  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
    <i class="fa fa-user"> ${currentUser.username}</i>
    <b class="caret"></b>
  </a>
  <ul class="dropdown-menu" role="menu">
    <li>
      <div class="navbar-content">
        <div class="row">
          <div class="col-md-5">
            <img src="http://api.randomuser.me/portraits/men/49.jpg" alt="" class="img-responsive img-circle">
            <p class="text-center small">
              <a href="#">
                <g:message code="auth.button.photo.edit.label"/>
              </a>
            </p>
          </div>
          <div class="col-md-7">
            <span>${currentUser.name}</span>
            <p class="text-muted small">${currentUser.email}</p>
            <div class="divider"></div>
            <a href="#" class="btn btn-primary btn-sm active">
              <g:message code="auth.button.profile.show.label"/>
            </a>
          </div>
        </div>
      </div>
      <div class="navbar-footer">
        <div class="navbar-footer-content">
          <div class="row">
            <div class="col-md-6">
              <a href="#" class="btn btn-default btn-sm">
                <g:message code="auth.button.password.edit.label"/>
              </a>
            </div>
          </div>
        </div>
      </div>
    </li>
  </ul>
</li>