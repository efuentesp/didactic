package temp

class Profesores {
  String nombre
  String grado
  String servidorPublicoClave
  Integer experiencia
  String correoElectronico
  String telefono
  String escolaridad
  String escolaridadOtros

  static mapping = {
    table 'YProfesores'
    id column: 'ProfesorID'
    nombre column: 'Nombre'
    grado column: 'GradoMateria'
    servidorPublicoClave column: 'ServidorPublicoClave'
    experiencia column: 'ExperienciaLaboralAños'
    correoElectronico column: 'CorreoElectronico'
    telefono column: 'Telefono'
    escolaridad column: 'Escolaridad'
    escolaridadOtros column: 'EscolaridadOtros'
  }
}