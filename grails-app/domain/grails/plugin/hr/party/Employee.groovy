package grails.plugin.hr.party

import grails.plugin.core.party.PartyRole

class Employee extends PartyRole {

  String code

  static constraints = {
    code (nullable: false, blank: false, unique: true)
  }
}
