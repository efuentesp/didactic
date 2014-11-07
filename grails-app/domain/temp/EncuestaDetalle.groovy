package temp

class EncuestaDetalle {
  Long surveyMonkeyId
  Long estandarId
  Long escalaId

  static mapping = {
    table 'YEncuestaDetalle'
    surveyMonkeyId column: 'EncuestaEncabezado_SurveyMonkeyID '
    estandarId column: 'Estandares_EstandarID'
    escalaId column: 'Escalas_EscalaID'
  }
}