<h1>Menu Loaded</h1>
<br>
<h2>Menus</h2>
<table>
  <tbody>
    <tr>
      <th>module</th>
      <th>name</th>
      <th>title</th>
      <th>description</th>
      <th>icon</th>
      <th>permission</th>
      <th>parent</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.menus}" var="menu">
      <tr>
        <td>${menu.module}</td>
        <td>${menu.name}</td>
        <td>${menu.title}</td>
        <td>${menu.description}</td>
        <td>${menu.icon}</td>
        <td>${menu.permission}</td>
        <td>${menu.parent}</td>
        <td>${menu.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="menu" action="index">Menu List</g:link>

<br>
<h2>Menu Items</h2>
<table>
  <tbody>
    <tr>
      <th>weight</th>
      <th>name</th>
      <th>title</th>
      <th>description</th>
      <th>icon</th>
      <th>permission</th>
      <th>linkController</th>
      <th>linkAction</th>
      <th>menu</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.items}" var="item">
      <tr>
        <td>${item.weight}</td>
        <td>${item.name}</td>
        <td>${item.title}</td>
        <td>${item.description}</td>
        <td>${item.icon}</td>
        <td>${item.permission}</td>
        <td>${item.linkController}</td>
        <td>${item.linkAction}</td>
        <td>${item.menu}</td>
        <td>${item.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="menuitem" action="index">Menu Item List</g:link>
