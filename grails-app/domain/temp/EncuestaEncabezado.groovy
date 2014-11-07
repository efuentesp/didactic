package temp

class EncuestaEncabezado {
  Long surveyMonkeyId
  Long recolectorId
  String fechaInicio
  String fechaFin
  String direccionIp
  Long profesorId

  static mapping = {
    table 'YEncuestaEncabezado'
    surveyMonkeyId column: 'SurveyMonkeyID '
    recolectorId column: '  Recolectores_RecolectorID '
    fechaInicio column: '  FechaInicio '
    fechaFin column: '  FechaFin '
    direccionIp column: '  DireccionIP '
    profesorId column: '  Profesores_ProfesorID '
  }
}