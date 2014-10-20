<%@page import="grails.plugin.core.menu.*"%>

<g:set var="menu" value="${Menu.findByName('menu.core.main')}" />

<g:each var="submenu" in="${menu.submenus}">
<shiro:hasPermission permission="${submenu.permission}">
  <li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
      <i class="fa ${submenu.icon}"></i>
      <g:message code="${submenu.name}" default='${submenu.title}'/> <span class="caret"></span>
    </a>
    <ul class="dropdown-menu" role="menu">
      <g:each var="menugroup" in="${submenu.submenus}">
      <shiro:hasPermission permission="${menugroup.permission}">
        <g:each var="menuitem" in="${menugroup.items}">
        <shiro:hasPermission permission="${menuitem.permission}">
          <li>
            <g:link controller="${menuitem.linkController}" action="${menuitem.linkAction}">
              <i class="fa ${menuitem.icon}"></i>
              <g:message code="${menuitem.name}" default='${menuitem.title}'/>
            </g:link>
          </li>
        </shiro:hasPermission>
        </g:each>
        <li class="divider"></li>
      </shiro:hasPermission>
      </g:each>
      <g:each var="menuitem" in="${submenu.items}">
      <shiro:hasPermission permission="${menuitem.permission}">
        <li>
          <g:link controller="${menuitem.linkController}" action="${menuitem.linkAction}">
            <i class="fa ${menuitem.icon}"></i>
            <g:message code="${menuitem.name}" default='${menuitem.title}'/>
          </g:link>
        </li>
      </shiro:hasPermission>
      </g:each>
    </ul>
  </li>
</shiro:hasPermission>
</g:each>

<g:each var="menuitem" in="${menu.items}">
<shiro:hasPermission permission="${menuitem.permission}">
  <li>
    <g:link controller="${menuitem.linkController}" action="${menuitem.linkAction}">
      <i class="fa ${menuitem.icon}"></i>
      <g:message code="${menuitem.name}" default='${menuitem.title}'/>
    </g:link>
  </li>
</shiro:hasPermission>
</g:each>