package la.didactic.core.party

import grails.plugin.core.party.PartyRole
import grails.plugin.core.taxonomy.Term

class School extends PartyRole {
  String code
  Term area
  Term control
  Term service

  static constraints = {
    code (nullable: false, blank: false, unique: true)
    area (nullable: false)
    control (nullable: false)
    service (nullable: false)
  }

  String toString() {
    "[${code}] ${party} (${type})"
  }

}