package grails.plugin.core.party

class Person extends Party {

  String uuid
  String firstName
  String lastName

  Gender gender
  Date birthDate

  static mapping = {
    sort firstName: 'desc' 
  }

  static constrains = {
    uuid (nullable: false, blank: false, unique: true)
    firstName nullable: false, blank: false
    lastName nullable: false, blank: false
    gender nullable: true
    birthDate nullable: true
  }

  String toString() {
    "${firstName} ${lastName}"
  }

}