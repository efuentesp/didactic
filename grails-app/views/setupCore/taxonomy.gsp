<h1>Taxonomy Loaded</h1>
<br>
<h2>Vocabularies</h2>
<table>
  <tbody>
    <tr>
      <th>code</th>
      <th>name</th>
      <th>description</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.vocabularies}" var="vocabulary">
      <tr>
        <td>${vocabulary.code}</td>
        <td>${vocabulary.name}</td>
        <td>${vocabulary.description}</td>
        <td>${vocabulary.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="vocabulary" action="index">Vocabulary List</g:link>

<br>
<h2>Terms</h2>
<table>
  <tbody>
    <tr>
      <th>vocabulary</th>
      <th>parent</th>
      <th>code</th>
      <th>name</th>
      <th>description</th>
      <th>weight</th>
      <th>restricted</th>
    </tr>
    <g:each in="${json.terms}" var="term">
      <tr>
        <td>${term.vocabulary}</td>
        <td>${term.parent}</td>
        <td>${term.code}</td>
        <td>${term.name}</td>
        <td>${term.description}</td>
        <td>${term.weight}</td>
        <td>${term.restricted}</td>
      </tr>
    </g:each>
  </tbody>
</table>

<g:link controller="term" action="index">Term List</g:link>