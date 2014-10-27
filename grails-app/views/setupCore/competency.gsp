<g:link controller="setupCore" action="index">Setup Core</g:link>

<br>
<h1>Competencies Loaded</h1>

<g:if test="${json.models}">
  <br>
  <g:link controller="competencyModel" action="index">Competency Model List (total=${json.models.size()})</g:link>
</g:if>

<g:if test="${json.levels}">
  <br>
  <g:link controller="competencyLevel" action="index">Competency Level List (total=${json.levels.size()})</g:link>
</g:if>

<g:if test="${json.competencies}">
  <br>
  <g:link controller="competency" action="index">Competency List (total=${json.competencies.size()})</g:link>
</g:if>
