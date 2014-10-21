<g:link controller="setupCore" action="index">Setup Core</g:link>

<br>
<h1>Party Loaded</h1>

<g:if test="${json.genders}">
  <br>
  <g:link controller="gender" action="index">Gender List (total=${json.genders.size()})</g:link>
</g:if>

<g:if test="${json.geographicBoundaries}">
  <br>
  <g:link controller="geographicBoundary" action="index">Geographic Boundary List (total=${json.geographicBoundaries.size()})</g:link>
</g:if>

<g:if test="${json.organizations}">
  <br>
  <g:link controller="organization" action="index">Organization List (total=${json.organizations.size()})</g:link>
</g:if>

<g:if test="${json.persons}">
  <br>
  <g:link controller="person" action="index">Person List (total=${json.persons.size()})</g:link>
</g:if>

<g:if test="${json.partyRoles}">
  <br>
  <g:link controller="partyRole" action="index">Party Role List (total=${json.partyRoles.size()})</g:link>
</g:if>

<g:if test="${json.partyRelationships}">
  <br>
  <g:link controller="partyRelationship" action="index">Party Relationship List (total=${json.partyRelationships.size()})</g:link>
</g:if>