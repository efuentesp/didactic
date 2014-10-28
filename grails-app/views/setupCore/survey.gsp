<g:link controller="setupCore" action="index">Setup Core</g:link>

<br>
<h1>Survey Loaded</h1>

<g:if test="${json.surveys}">
  <br>
  <g:link controller="survey" action="index">Survey List (total=${json.surveys.size()})</g:link>
</g:if>