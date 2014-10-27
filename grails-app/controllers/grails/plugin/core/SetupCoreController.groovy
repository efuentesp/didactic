package grails.plugin.core

import grails.plugin.core.auth.*
import grails.plugin.core.taxonomy.*
import grails.plugin.core.party.*
import grails.plugin.core.menu.*
import grails.plugin.hr.party.*

import grails.converters.JSON

import org.apache.shiro.crypto.hash.Sha256Hash

class SetupCoreController {

  def index() {

  }

  def auth() {
    println "auth: ${params}"
    
    def file = request.getFile('jsonAuthUpload')
    //println file.inputStream.text
    //println "File content type: " + file.getContentType()
    
    def okcontents = ['application/json', 'application/octet-stream']
    
    if (! okcontents.contains(file.getContentType())) {
      flash.message = "File must be one of: ${okcontents}, and was received: [${file.getContentType()}]"
      render(view: 'index')
      return;
    }
    
    def json = JSON.parse(file.getInputStream(),"UTF-8")
    
    println json

    // Permissions
    json.permissions.each { perm ->
      new Permission(module: perm.module,
                     code: perm.code,
                     title: perm.title,
                     description: perm.description,
                     domain: perm.domain,
                     actions: perm.actions,
                     instances: perm.instances,
                     restricted: perm.restricted
                    ).save(failOnError: true)
    }

    // Roles
    json.roles.each { r ->
      new Role(name: r.name,
               description: r.description,
               restricted: r.restricted
              ).save(failOnError: true)
    }

    // Users
    json.users.each { u ->
      def user = new User (username: u.username,
                          passwordHash: new Sha256Hash(u.password).toHex(),
                          name: u.name,
                          email: u.email,
                          enabled: u.enabled
                         )

      u.roles.each { r ->
        def role = Role.findByName(r)
        user.addToRoles(role)
      }

      user.save(failOnError: true)

      u.roles.each { r ->
        def role = Role.findByName(r)
        role.addToUsers(user)
        role.save()
      }

    }

    [json: json]
  }

  def menu() {
    println "menu: ${params}"
    
    def file = request.getFile('jsonMenuUpload')
    //println file.inputStream.text
    //println "File content type: " + file.getContentType()
    
    def okcontents = ['application/json', 'application/octet-stream']
    
    if (! okcontents.contains(file.getContentType())) {
      flash.message = "File must be one of: ${okcontents}, and was received: [${file.getContentType()}]"
      render(view: 'index')
      return;
    }
    
    def json = JSON.parse(file.getInputStream(),"UTF-8")
    
    println json

    // Menus
    json.menus.each { menu ->
      def parent

      if (menu.parent) {
        parent = Menu.findByName(menu.parent)
      }

      new Menu(module: menu.module,
               name: menu.name,
               title: menu.title,
               description: menu.description,
               icon: menu.icon,
               permission: menu.permission,
               parent: parent
              ).save(failOnError: true)
    }
    
    // Menu Items
    json.items.each { item->
      def menu = Menu.findByName(item.menu)

      new MenuItem(name: item.name,
                   title: item.title,
                   description: item.description,
                   icon: item.icon,
                   permission: item.permission,
                   linkController: item.linkController,
                   linkAction: item.linkAction,
                   menu: menu,
                   weight: item.weight
                  ).save(failOnError: true)
    }

    [json: json]
  }

  def taxonomy() {
    println "taxonomy: ${params}"
    
    def file = request.getFile('jsonTaxonomyUpload')
    //println file.inputStream.text
    println "File content type: " + file.getContentType()
    
    def okcontents = ['application/json', 'application/octet-stream']
    
    if (! okcontents.contains(file.getContentType())) {
      flash.message = "File must be one of: ${okcontents}, and was received: [${file.getContentType()}]"
      render(view: 'index')
      return;
    }
    
    def json = JSON.parse(file.getInputStream(),"UTF-8")
    
    println json

    // Vocabulary
    json.vocabularies.each { voc ->
      def vocabulary= new Vocabulary(code: voc.code,
                                     name: voc.name,
                                     description: voc.description,
                                     restricted: voc.restricted
                                    )
      if (!vocabulary.save()) {
        log.error "Unable to create vocabulary: ${vocabulary.name}."
        vocabulary.errors.each { log.error it }

        throw new RuntimeException("Unable to create vocabulary: ${vocabulary.name}.")
      }
    }

    // Terms
    json.terms.each { t ->
      def vocab
      def term= new Term(code: t.code,
                         name: t.name,
                         description: t.description,
                         weight: t.weight,
                         restricted: t.restricted
                        )
      if (t.vocabulary) {
        vocab = Vocabulary.findByCode(t.vocabulary)
        term.vocabulary = vocab
      }
      if (t.parent) {
        def parent = Term.findByCode(t.parent)
        term.parent = parent
      }

      if (term.save()) {
        if (vocab) {
          vocab.addToTerms(term)
          vocab.save()
        }
      } else {
        log.error "Unable to create term: ${term.code}."
        term.errors.each { log.error it }

        throw new RuntimeException("Unable to create term: ${term.code}.")
      }
    }

    [json: json]
  }

  def party() {
    println "party: ${params}"
    
    def file = request.getFile('jsonPartyUpload')
    //println file.inputStream.text
    println "File content type: " + file.getContentType()
    
    def okcontents = ['application/json', 'application/octet-stream']
    
    if (! okcontents.contains(file.getContentType())) {
      flash.message = "File must be one of: ${okcontents}, and was received: [${file.getContentType()}]"
      render(view: 'index')
      return;
    }
    
    def json = JSON.parse(file.getInputStream(),"UTF-8")
    
    println json

    // Genders
    json.genders.each { gender ->
      new Gender(code: gender.code,
                 description: gender.description,
                 restricted: gender.restricted
                ).save(failOnError: true)
    }

    // Geographic Boundaries
    json.geographicBoundaries.each { geo ->
      def geographicBoundary = new GeographicBoundary(code: geo.code,
                                                        name: geo.name,
                                                        abbreviation: geo.abbreviation,
                                                        type: Term.findByCode(geo.type),
                                                        parent: GeographicBoundary.findByCode(geo.parent)
                                                       )
      if (!geographicBoundary.save()) {
        log.error "Unable to create Geographic Boundary: ${geographicBoundary}."
        geographicBoundary.errors.each { log.error it }

        throw new RuntimeException("Unable to create Geographic Boundary: ${geographicBoundary}.")
      }
    }

    // Organizations
    json.organizations.each { org ->
      def organization = new Organization(name: org.name,
                                          type: Term.findByCode(org.type),
                                          restricted: org.restricted
                                         )

      if (!organization.save()) {
        log.error "Unable to create organization: ${organization.name}."
        organization.errors.each { log.error it }

        throw new RuntimeException("Unable to create organization: ${organization.name}.")
      }
    }

    // Persons
    json.persons.each { p ->
      def person = new Person(firstName: p.firstName,
                              lastName: p.lastName,
                              gender: Gender.findByCode(p.gender)
                             )

      if (!person.save()) {
        log.error "Unable to create person: ${person}."
        person.errors.each { log.error it }

        throw new RuntimeException("Unable to create person: ${person}.")
      }
    }

    // Party Role
    json.partyRoles.each { pr ->
      println pr
      def party
      if (pr.party.organization) {
        party = Organization.findByName(pr.party.organization)

        if (!party) {
          log.error "Unable to retrieve party: ${pr.party.organization}."

          throw new RuntimeException("Unable to retrieve party: ${pr.party.organization}.")
        }
      }
      if (pr.party.person) {
        party = Person.findByFirstNameAndLastName(pr.party.person.firstName, pr.party.person.lastName)

        if (!party) {
          log.error "Unable to retrieve party: ${pr.party.person.firstName} ${pr.party.person.lastName}."

          throw new RuntimeException("Unable to retrieve party: ${pr.party.person.firstName} ${pr.party.person.lastName}.")
        }
      }

      if (pr.type == 'EMPLOYEE') {

        def employee = new Employee(party: party,
                                    type: Term.findByCode(pr.type),
                                    restricted: pr.restricted,
                                    code: pr.code
                                    )
        if (!employee.save()) {
          log.error "Unable to create Employee: ${employee}."
          Employee.errors.each { log.error it }

          throw new RuntimeException("Unable to create Employee: ${employee}.")
        }

        def email = new Email(email: pr.contact.email, type: Term.findByCode(pr.contact.type))
        if (!email.save()) {
          log.error "Unable to create Email: ${email}."
          Email.errors.each { log.error it }

          throw new RuntimeException("Unable to create Email: ${email}.")
        }

        def employeeEmail = new PartyContactMechanism(party: employee, contactMechanism: email)
        if (!employeeEmail.save()) {
          log.error "Unable to create Employee Email: ${employeeEmail}."
          PartyContactMechanism.errors.each { log.error it }

          throw new RuntimeException("Unable to create Employee Email: ${employeeEmail}.")
        }

      } else {

        def partyRole = new PartyRole(party: party,
                                      type: Term.findByCode(pr.type),
                                      restricted: pr.restricted
                                     )

        if (!partyRole.save()) {
          log.error "Unable to create party role: ${partyRole.party}."
          partyRole.errors.each { log.error it }

          throw new RuntimeException("Unable to create party role: ${partyRole.party}.")
        }

      }
    }

    // Party Relationship
    json.partyRelationships.each { pr ->
      def from, to, type
      def fromParty, toParty

      if (pr.from.organization) {
        fromParty = Organization.findByName(pr.from.organization)
      }
      if (pr.from.person) {
        fromParty = Person.findByFirstNameAndLastName(pr.from.person.firstName, pr.from.person.lastName)
      }
      from = PartyRole.findByParty(fromParty)

      if (pr.to.organization) {
        toParty = Organization.findByName(pr.to.organization)
      }
      if (pr.to.person) {
        toParty = Person.findByFirstNameAndLastName(pr.to.person.firstName, pr.to.person.lastName)
      }
      if (pr.to.employee) {
        toParty = Employee.findByCode(pr.employee.code)
      }
      to = PartyRole.findByParty(toParty)

      type = Term.findByCode(pr.type)

      def partyRelationship = new PartyRelationship(fromPartyRole: from,
                                                    toPartyRole: to,
                                                    type: type,
                                                    restricted: pr.restricted
                                                   )

      if (!partyRelationship.save()) {
        log.error "Unable to create party relationship: ${partyRelationship}."
        partyRelationship.errors.each { log.error it }

        throw new RuntimeException("Unable to create party relationship: ${partyRelationship}.")
      }
    }

    [json: json]
  }

}