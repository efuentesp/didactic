package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class PartyRelationship implements Serializable {

  static belongsTo = [fromPartyRole: PartyRole, toPartyRole: PartyRole]

  Date fromDate = new Date()
  Date thruDate = new Date()

  Term type

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static constrains = {
    type (nullable: false)
    fromPartyRole (nullable: false)
    toPartyRole (nullable: false)
    fromDate (nullable: true)
    thruDate (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    "${type} : ${fromPartyRole} : ${toPartyRole}"
  }

}