package grails.plugin.school.partyRole

import grails.plugin.core.party.PartyRole

class School extends PartyRole {

  static constrains = {
  }

  String toString() {
    "[School] ${party}"
  }

}