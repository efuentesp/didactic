<%@page defaultCodec="none" %>
 <div class="form-group ${invalid ? 'has-error' : ''}">
    <label class="col-sm-2 control-label" for="${property}">${required ? '*' : '' } ${label}</label>
    <div class="col-sm-10">
        ${widget}
        <g:if test="${help}">        
					<span class="help-block">${help}</span>
        </g:if>
    </div>
 </div>