<h1>Party Loaded</h1>
<br>
<h2>Genders</h2>
<table>
  <tbody>
    <tr>
      <th>code</th>
      <th>description</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.genders}" var="gender">
      <tr>
        <td>${gender.code}</td>
        <td>${gender.description}</td>
        <td>${gender.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="gender" action="index">Gender List</g:link>

<br>
<h2>Organizations</h2>
<table>
  <tbody>
    <tr>
      <th>name</th>
      <th>type</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.organizations}" var="organization">
      <tr>
        <td>${organization.name}</td>
        <td>${organization.type}</td>
        <td>${organization.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="organization" action="index">Organization List</g:link>

<br>
<h2>Persons</h2>
<table>
  <tbody>
    <tr>
      <th>firstName</th>
      <th>lastName</th>
    </tr>
    <g:each in="${json.persons}" var="person">
      <tr>
        <td>${person.firstName}</td>
        <td>${person.lastName}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="person" action="index">Person List</g:link>

<br>
<h2>Party Roles</h2>
<table>
  <tbody>
    <tr>
      <th>entity</th>
      <th>type</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.partyRoles}" var="partyRole">
      <tr>
        <td>${partyRole.entity}</td>
        <td>${partyRole.type}</td>
        <td>${partyRole.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="partyRole" action="index">Party Role List</g:link>

<br>
<h2>Party Relationships</h2>
<table>
  <tbody>
    <tr>
      <th>fromPartyRole</th>
      <th>toPartyRole</th>
      <th>fromDate</th>
      <th>thruDate</th>
      <th>type</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.partyRelationships}" var="partyRelationship">
      <tr>
        <td>${partyRelationship.fromPartyRole}</td>
        <td>${partyRelationship.toPartyRole}</td>
        <td>${partyRelationship.fromDate}</td>
        <td>${partyRelationship.thruDate}</td>
        <td>${partyRelationship.type}</td>
        <td>${partyRelationship.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="partyRelationship" action="index">Party Relationship List</g:link>