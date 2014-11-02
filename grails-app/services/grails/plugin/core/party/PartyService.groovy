package grails.plugin.core.party

import grails.plugin.core.taxonomy.Term

class PartyService {

  public static final String GEOGRAPHIC_BOUNDARY_TYPE = 'GEOGRAPHIC_BOUNDARY_TYPE'
  
  public static final String ORGANIZATION_TYPE = 'ORGANIZATION_TYPE'
    public static final String LEGAL_ORGANIZATION = 'LEGAL_ORGANIZATION'
      public static final String CORPORATION = 'CORPORATION'
      public static final String GOVERMENT_AGENCY = 'GOVERMENT_AGENCY'
    public static final String INFORMAL_ORGANIZATION = 'INFORMAL_ORGANIZATION'

  public static final String PARTY_ROLE_TYPE = 'PARTY_ROLE_TYPE'
    public static final String PERSON_ROLE = 'PERSON_ROLE'
    public static final String ORGANIZATION_ROLE = 'ORGANIZATION_ROLE'
      public static final String INTERNAL_ORGANIZATION = 'INTERNAL_ORGANIZATION'
      public static final String ORGANIZATION_UNIT = 'ORGANIZATION_UNIT'
    public static final String CUSTOMER = 'CUSTOMER'

  public static final String PARTY_RELATIONSHIP_TYPE = 'PARTY_RELATIONSHIP_TYPE'
    public static final String CUSTOMER_RELATIONSHIP = 'CUSTOMER_RELATIONSHIP'
    public static final String ORGANIZATION_ROLLUP = 'ORGANIZATION_ROLLUP'

  public static final String CONTACT_MECHANISM_TYPE = 'CONTACT_MECHANISM_TYPE'
    public static final String EMAIL_TYPE = 'EMAIL_TYPE'
      public static final String EMAIL_PERSONAL = 'EMAIL_PERSONAL'
      public static final String EMAIL_WORK = 'EMAIL_WORK'

  Party createPerson() {

  }

  Organization createOrganization( String name, String typeCode, PostalAddress postalAddress = null ) {
    def type = Term.findByCode(typeCode)
    if (!type) {
      log.error "Unable to retrive Term: ${typeCode}."
      throw new RuntimeException("Unable to retrive Term: ${typeCode}.")
    } else if (type.vocabulary.code != ORGANIZATION_TYPE) {
      log.error "${type} is not a valid Organization type."
      throw new RuntimeException("${type} is not a valid Organization type.")
    }

    def organization = new Organization(name: name,
                                        type: type,
                                        restricted: false
                                       )

    if (!organization.save()) {
      log.error "Unable to create Organization: ${organization}."
      organization.errors.each { log.error it }

      throw new RuntimeException("Unable to create Organization: ${organization}.")
    }

    if (postalAddress) {
      if (!postalAddress.save()) {
        log.error "Unable to create Postal Address: ${postalAddress}."
        postalAddress.errors.each { log.error it }

        throw new RuntimeException("Unable to create Postal Address: ${postalAddress}.")
      }

      def partyPostalAddress = new PartyPostalAddress(party: organization,
                                                      postalAddress: postalAddress,
                                                      fromDate: new Date(),
                                                      thruDate: new Date(),
                                                      restricted: false
                                                      )
      if (!partyPostalAddress.save()) {
        log.error "Unable to create Party Postal Address: ${partyPostalAddress}."
        partyPostalAddress.errors.each { log.error it }

        throw new RuntimeException("Unable to create Party Postal Address: ${partyPostalAddress}.")
      }

      organization.addToPartyPostalAddresses(partyPostalAddress)
      if (!organization.save()) {
        log.error "Unable to add Party Postal Address: '${partyPostalAddress}'' to Organization: '${organization}'."
        organization.errors.each { log.error it }

        throw new RuntimeException("Unable to add Party Postal Address: '${partyPostalAddress}'' to Organization: '${organization}'.")
      }
    }

    return organization
  }

  PartyRole createPartyRole( Party party, String typeCode ) {
    def type = Term.findByCode(typeCode)
    if (!type) {
      log.error "Unable to retrive Term: ${typeCode}."
      throw new RuntimeException("Unable to retrive Term: ${typeCode}.")
    } else if (type.vocabulary.code != PARTY_ROLE_TYPE) {
      log.error "${type} is not a valid Party Role type."
      throw new RuntimeException("${type} is not a valid Party Role type.")
    }

    def partyRole = new PartyRole(party: party,
                                  type: type
                                  )
    if (!partyRole.save()) {
      log.error "Unable to create Party Role: ${partyRole}."
      partyRole.errors.each { log.error it }

      throw new RuntimeException("Unable to create Party Role: ${partyRole}.")
    }

    party.addToPartyRoles(partyRole)
    if (!party.save()) {
      log.error "Unable to add Party Role: '${partyRole}' to Party: '${party}'."
      party.errors.each { log.error it }

      throw new RuntimeException("Unable to add Party Role: '${partyRole}' to Party: '${party}'.")
    }

    return partyRole
  }

  PartyRelationship createPartyRelationship( PartyRole from, PartyRole to, String typeCode ) {

    def type = Term.findByCode(typeCode)
    if (!type) {
      log.error "Unable to retrive Party Role type: ${typeCode}."
      throw new RuntimeException("Unable to retrive Party Role type: ${typeCode}.")
    } else if (type.vocabulary.code != PARTY_RELATIONSHIP_TYPE) {
      log.error "${PARTY_RELATIONSHIP_TYPE} is not a valid Party Relationship type."
      throw new RuntimeException("${PARTY_RELATIONSHIP_TYPE} is not a valid Party Relationship type.")
    }

    def partyRelationship = new PartyRelationship(fromPartyRole: from,
                                                  toPartyRole: to,
                                                  type: type,
                                                  restricted: false
                                                  )

    if (!partyRelationship.save()) {
      log.error "Unable to create Party Relationship: ${partyRelationship}."
      partyRelationship.errors.each { log.error it }

      throw new RuntimeException("Unable to create Party Relationship: ${partyRelationship}.")
    }

    return partyRelationship
  }

}