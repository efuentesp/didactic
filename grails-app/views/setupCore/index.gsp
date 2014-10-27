<g:if test="${flash.message}">
  <div class="alert alert-block alert-info">
    <a class="close" data-dismiss="alert">&times;</a>
    ${flash.message}
  </div>
</g:if>

<fieldset>
  <g:uploadForm action="auth" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Auth:</label>
      <div class="controls">
        <input type="file" id="jsonAuthUpload" name="jsonAuthUpload" value="" required />
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

<fieldset>
  <g:uploadForm action="taxonomy" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Taxonomy:</label>
      <div class="controls">
        <input type="file" id="jsonTaxonomyUpload" name="jsonTaxonomyUpload" value="" required />
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

<fieldset>
  <g:uploadForm action="party" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Party:</label>
      <div class="controls">
        <input type="file" id="jsonPartyUpload" name="jsonPartyUpload" value="" required />
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

<fieldset>
  <g:uploadForm action="menu" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Menu:</label>
      <div class="controls">
        <input type="file" id="jsonMenuUpload" name="jsonMenuUpload" value="" required />
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

<fieldset>
  <g:uploadForm action="competency" method="post" class="form-horizontal">
  
    <div class="control-group">
      <label class="control-label">Competency:</label>
      <div class="controls">
        <input type="file" id="jsonCompetencyUpload" name="jsonCompetencyUpload" value="" required />
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