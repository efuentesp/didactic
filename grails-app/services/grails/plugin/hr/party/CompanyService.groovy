package grails.plugin.hr.party

import grails.plugin.core.taxonomy.Term
import grails.plugin.core.party.*
import grails.plugin.hr.party.*

class CompanyService {
  
  def partyService

  public static final String EMPLOYEE = 'EMPLOYEE'
  public static final String EMPLOYMENT_RELATIONSHIP = 'EMPLOYMENT_RELATIONSHIP'

  PartyRelationship hireEmployee( PartyRole organization, Employee employee, Map params = [:] ) {
    if (organization.type.vocabulary.code != ORGANIZATION_TYPE) {
      log.error "${type} is not a valid Organization type."
      throw new RuntimeException("${type} is not a valid Organization type.")
    }

    def person = Person.findByUuid(employee.party.uuid)
    if (!person) {
      person = partyService.createPerson(employee.person, [])
    }

    if (!employee.save()) {
      log.error "Unable to create Employee: ${employee}."
      employee.errors.each { log.error it }

      throw new RuntimeException("Unable to create Employee: ${employee}.")
    }

    return
  }

}