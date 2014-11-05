package grails.plugin.core.party

class Email extends ContactMechanism {

  String email

  static constraints = {
    //email (email: true, nullable: false, blank: false)
    email (nullable: false, blank: false)
  }

  String toString() {
    email
  }

}