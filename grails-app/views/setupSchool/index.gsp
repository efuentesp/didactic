<g:if test="${flash.message}">
  <div class="alert alert-block alert-info">
    <a class="close" data-dismiss="alert">&times;</a>
    ${flash.message}
  </div>
</g:if>

<fieldset>
  <g:uploadForm action="school" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Schools:</label>
      <div class="controls">
        <input type="file" id="jsonSchoolUpload" name="jsonSchoolUpload" value="" required />
      </div>
    </div>
    
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">
        <i class="icon-ok icon-white"></i>
        Upload
      </button>           
    </div>
    
  </g:uploadForm>
</fieldset>