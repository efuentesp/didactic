package grails.plugin.core.party

class Email extends ContactMechanism {

  String email

  static constraints = {
    email (email: true, blank: false)
  }

}