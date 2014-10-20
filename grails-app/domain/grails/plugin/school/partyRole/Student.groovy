package grails.plugin.school.partyRole

import grails.plugin.core.party.PartyRole

class Student extends PartyRole {

  String registration

  static constrains = {
    registration (nullable: false, blank: false)
  }

  String toString() {
    "[Student] ${party}"
  }

}