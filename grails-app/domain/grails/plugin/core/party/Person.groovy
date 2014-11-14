package grails.plugin.core.party

class Person extends Party {

  String firstName
  String lastName

  Gender gender
  Date birthDate

  static mapping = {
    sort firstName: 'desc' 
  }

  static constrains = {
    firstName nullable: false, blank: false
    lastName nullable: false, blank: false
    gender nullable: true
    birthDate nullable: true
  }

  def fullName() {
    "${firstName} ${lastName}"
  }

  String toString() {
    "${firstName} ${lastName}"
  }

}