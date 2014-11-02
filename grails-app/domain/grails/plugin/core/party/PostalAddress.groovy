package grails.plugin.core.party

class PostalAddress implements Serializable {

  String address1
  String address2
  String postalCode
  String directions

  GeographicBoundary geographicBoundary

  static hasMany = [
    partyPostalAddresses: PartyPostalAddress
  ]

  static constraints = {
    address1 (nullable: false, blank: false)
    address2 (nullable: true)
    postalCode (nullable: true)
    directions (nullable: true)
    partyPostalAddresses (nullable: true)
  }

  String toString() {
    address1
  }

}