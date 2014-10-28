<g:link controller="setupCore" action="index">Setup Core</g:link>

<br>
<h1>Survey Responses Uploaded</h1>

<g:if test="${json.surveyQuestionResonses}">
  <br>
  <g:link controller="surveyResponses" action="index">Survey Responses List (total=${json.surveyQuestionResonses.size()})</g:link>
</g:if>