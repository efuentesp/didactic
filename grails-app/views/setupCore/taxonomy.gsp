<g:link controller="setupCore" action="index">Setup Core</g:link>

<br>
<h1>Taxonomy Loaded</h1>

<g:if test="${json.vocabularies}">
  <br>
  <g:link controller="vocabulary" action="index">Vocabulary List (total=${json.vocabularies.size()})</g:link>
</g:if>

<g:if test="${json.terms}">
  <br>
  <g:link controller="term" action="index">Term List (total=${json.terms.size()})</g:link>
</g:if>