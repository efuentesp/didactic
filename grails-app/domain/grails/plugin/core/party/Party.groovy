package grails.plugin.core.party

class Party implements Serializable {

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [
    partyRoles: PartyRole,
    partyPostalAddresses: PartyPostalAddress
  ]

  static constrains = {
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}