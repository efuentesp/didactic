package grails.plugin.core.party

class Party implements Serializable {

  String uuid
  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [
    partyRoles: PartyRole,
    partyPostalAddresses: PartyPostalAddress,
    partyContactMechanisms: PartyContactMechanism
  ]

  static constrains = {
    uuid (nullable: false, blank: false, unique: true)
    restricted (nullable: false)
    partyRoles (nullable: true)
    partyPostalAddresses (nullable: true)
    partyContactMechanisms (nullable: true)
    dateCreated (nullable: false)
    lastUpdated (nullable: true)
  }

  String toString() {
    uuid
  }

}