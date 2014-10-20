<g:if test="${radio == 'true'}">      
     <label class="radio inline">    
         <g:radio name="${property}" value="true" checked="${value }"/>&nbsp; <g:message code="nimble.label.true" />
     </label>
     <label class="radio inline">    
         <g:radio name="${property}" value="false" checked="${!value}"/>&nbsp; <g:message code="nimble.label.false" />
     </label>                            
</g:if>
<g:else>
  <label>
    <input type="checkbox" name="${property}" checked="${value}">
  </label>
</g:else>
