package grails.plugin.hr.party

import grails.plugin.core.taxonomy.Term
import grails.plugin.core.party.*
import grails.plugin.hr.party.*

class CompanyService {
  
  def partyService

  public static final String EMPLOYEE = 'EMPLOYEE'
  public static final String EMPLOYMENT_RELATIONSHIP = 'EMPLOYMENT_RELATIONSHIP'

  PartyRole hireEmployee( PartyRole organization, Employee employee, Map params = [:] ) {
    println "hireEmployee: ${params}"
    if (organization.type.vocabulary.code != PartyService.PARTY_ROLE_TYPE) {
      log.error "${organization.type.code} is not a valid Party Role type."
      throw new RuntimeException("${organization.type.code} is not a valid Party Role type.")
    }

    def person = Person.findByUuid(employee.party.uuid)
    if (!person) {
      person = partyService.createPerson(employee.party, [contactMechanism: params.contactMechanism])
    }

    def employeePartyRoleType = Term.findByCode(EMPLOYEE)
    if (!employeePartyRoleType) {
      log.error "Unable to retrive Gender type: ${EMPLOYEE}."
      throw new RuntimeException("Unable to retrive Gender type: ${EMPLOYEE}.")
    }

    employee.type = employeePartyRoleType
    employee.fromDate = params.fromDate ? params.fromDate : new Date()
    employee.thruDate = params.thruDate ? params.thruDate : new Date()
    employee.restricted = params.restricted ? params.restricted : false

    if (!employee.save()) {
      log.error "Unable to create Employee: ${employee}."
      employee.errors.each { log.error it }

      throw new RuntimeException("Unable to create Employee: ${employee}.")
    }

/*    def employmentPartyRelationshipType = Term.findByCode(EMPLOYMENT_RELATIONSHIP)
    if (!employmentPartyRelationshipType) {
      log.error "Unable to retrive Gender type: ${EMPLOYMENT_RELATIONSHIP}."
      throw new RuntimeException("Unable to retrive Gender type: ${EMPLOYMENT_RELATIONSHIP}.")
    }*/

    def employmentRelationship = partyService.createPartyRelationship(employee, organization, EMPLOYMENT_RELATIONSHIP)

    return employee
  }

}