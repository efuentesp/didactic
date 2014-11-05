package la.didactic.core.party

import grails.plugin.core.taxonomy.Term

import grails.plugin.core.party.*
import grails.plugin.hr.party.*

import grails.plugin.core.party.PartyService
import grails.plugin.hr.party.CompanyService

class SchoolService {
  
  public static final String EDUCATIONAL_SERVICE_PROVIDER = 'EDUCATIONAL_SERVICE_PROVIDER'
  public static final String PARTY_ROLE_TYPE = 'PARTY_ROLE_TYPE'
  public static final String CAMPUS_PARTY_ROLE_TYPE = 'CAMPUS'
  public static final String EDUCATIONAL_AREA_TYPE = 'EDUCATIONAL_AREA_TYPE'
  public static final String EDUCATIONAL_CONTROL_TYPE = 'EDUCATIONAL_CONTROL_TYPE'
  public static final String EDUCATIONAL_SERVICE_TYPE = 'EDUCATIONAL_SERVICE_TYPE'

  def partyService
  def companyService

  School registerSchool( PartyRole parentPartyRole, String cct, String name, String area, String control, String service, PostalAddress postalAddress = null ) {

    def organization = partyService.createOrganization(name, EDUCATIONAL_SERVICE_PROVIDER, postalAddress)

    def type = Term.findByCode(CAMPUS_PARTY_ROLE_TYPE)
    if (!type) {
      log.error "Unable to retrive Party Role type: ${CAMPUS_PARTY_ROLE_TYPE}."
      throw new RuntimeException("Unable to retrive Party Role type: ${CAMPUS_PARTY_ROLE_TYPE}.")
    } else if (type.vocabulary.code != PARTY_ROLE_TYPE) {
      log.error "${PARTY_ROLE_TYPE} is not a valid Party Role type."
      throw new RuntimeException("${PARTY_ROLE_TYPE} is not a valid Party Role type.")
    }

    def educationalAreaType = Term.findByCode(area)
    if (!educationalAreaType) {
      log.error "Unable to retrive Educational Area type: ${area}."
      throw new RuntimeException("Unable to retrive Educational Area type: ${area}.")
    } else if (educationalAreaType.vocabulary.code != EDUCATIONAL_AREA_TYPE) {
      log.error "${area} is not a valid Educational Area type."
      throw new RuntimeException("${area} is not a valid Educational Area type.")
    }

    def educationalControlType = Term.findByCode(control)
    if (!educationalControlType) {
      log.error "Unable to retrive Educational Control type: ${control}."
      throw new RuntimeException("Unable to retrive Educational Control type: ${control}.")
    } else if (educationalControlType.vocabulary.code != EDUCATIONAL_CONTROL_TYPE) {
      log.error "${control} is not a valid Educational Control type."
      throw new RuntimeException("${control} is not a valid Educational Control type.")
    }

    def educationalServiceType = Term.findByCode(service)
    if (!educationalServiceType) {
      log.error "Unable to retrive Educational Service type: ${service}."
      throw new RuntimeException("Unable to retrive Educational Service type: ${service}.")
    } else if (educationalServiceType.vocabulary.code != EDUCATIONAL_SERVICE_TYPE) {
      log.error "${service} is not a valid Educational Service type."
      throw new RuntimeException("${service} is not a valid Educational Service type.")
    }

    def partyRole = new School(party: organization,
                                type: type,
                                code: cct,
                                area: educationalAreaType,
                                control: educationalControlType,
                                service: educationalServiceType,
                                fromDate: new Date(),
                                thruDate: new Date()
                                )
    if (!partyRole.save()) {
      log.error "Unable to create School: ${partyRole}."
      partyRole.errors.each { log.error it }

      throw new RuntimeException("Unable to create School: ${partyRole}.")
    }

    organization.addToPartyRoles(partyRole)
    if (!organization.save()) {
      log.error "Unable to add School: '${partyRole}' to Party: '${organization}'."
      organization.errors.each { log.error it }

      throw new RuntimeException("Unable to add School: '${partyRole}' to Party: '${organization}'.")
    }

    def partyRelationship = partyService.createPartyRelationship(partyRole, parentPartyRole, partyService.ORGANIZATION_ROLLUP)

    return partyRole
  }

  Employee hireProfessor( PartyRole organization, Employee employee, Map params = [:] ) {
    println "hireProfessor ${params}"
    if (organization.type.code != CAMPUS_PARTY_ROLE_TYPE) {
      log.error "${organization.type} is not a valid School."
      throw new RuntimeException("${organization.type} is not a valid School.")
    }

    def professor = companyService.hireEmployee(organization, employee, [contactMechanism: params.contactMechanism])

    return professor
  }

}