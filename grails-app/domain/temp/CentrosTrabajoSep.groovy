package temp

class CentrosTrabajoSep {
  String cct
  String localidad
  String ambito
  String control
  String servicio
  String centroEducativo
  String domicilio
  Long municipio

  static mapping = {
    table 'YCentrosTrabajoSEP'
    cct column: 'CCT'
    localidad column: 'Localidad'
    ambito column: 'Ambito'
    control column: 'Control'
    servicio column: 'Servicio'
    centroEducativo column: 'CentroEducativo'
    domicilio column: 'Domicilio'
    municipio column: 'Municipios_MunicipioID'
  }
}