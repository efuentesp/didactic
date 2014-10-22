package grails.plugin.core.party

class PartyContactMechanism implements Serializable {

  static belongsTo = [party: Party, contactMechanism: ContactMechanism]

  Date fromDate
  Date thruDate
  String comment

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static constrains = {
    fromDate (nullable: true)
    thruDate (nullable: true)
    comment (nullable: true)
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}