package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class PartyRole implements Serializable {
  
  static belongsTo = [party: Party]

/*  static hasMany = [
    partyRelationships: PartyRelationship
  ]*/

  Date fromDate
  Date thruDate

  Term type

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static constrains = {
    party (nullable: false)
    fromDate (nullable: true)
    thruDate (nullable: true)
    type (nullable: false)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    "${party}"
  }

}