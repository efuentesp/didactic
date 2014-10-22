package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class ContactMechanism implements Serializable {

  Term type

  static hasMany = [
    partyContactMechanisms: PartyContactMechanism
  ]

  static constraints = {
    type (nullable: false)
  }

}