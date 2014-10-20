h1>Auth Loaded</h1>
<br>
<h2>Permissions</h2>
<table>
  <tbody>
    <tr>
      <th>module</th>
      <th>code</th>
      <th>title</th>
      <th>description</th>
      <th>domain</th>
      <th>actions</th>
      <th>instances</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.permissions}" var="permission">
      <tr>
        <td>${permission.module}</td>
        <td>${permission.code}</td>
        <td>${permission.title}</td>
        <td>${permission.description}</td>
        <td>${permission.domain}</td>
        <td>${permission.actions}</td>
        <td>${permission.instances}</td>
        <td>${permission.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="permission" action="index">Permission List</g:link>

<br>
<h2>Roles</h2>
<table>
  <tbody>
    <tr>
      <th>description</th>
      <th>name</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.roles}" var="role">
      <tr>
        <td>${role.description}</td>
        <td>${role.name}</td>
        <td>${role.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="role" action="index">Role List</g:link>

<br>
<h2>Users</h2>
<table>
  <tbody>
    <tr>
      <th>username</th>
      <th>passwordHash</th>
      <th>name</th>
      <th>email</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.roles}" var="role">
      <tr>
        <td>${role.username}</td>
        <td>${role.passwordHash}</td>
        <td>${role.name}</td>
        <td>${role.email}</td>
        <td>${role.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="user" action="index">User List</g:link>