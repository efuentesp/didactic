package grails.plugin.core.party

class PartyPostalAddress implements Serializable {

  static belongsTo = [party: Party, postalAddress: PostalAddress]

  Date fromDate
  Date thruDate

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static constrains = {
    fromDate (nullable: true)
    thruDate (nullable: true)
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}