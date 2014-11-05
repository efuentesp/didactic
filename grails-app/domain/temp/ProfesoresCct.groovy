package temp

class ProfesoresCct {
  String cct

  static mapping = {
    table 'YProfesoresCCT'
    id column: 'Profesores_ProfesorID'
    cct column: 'CentrosTrabajoSEP_CCT'
  }
}