package grails.plugin.core

import grails.plugin.core.auth.*
import grails.plugin.core.taxonomy.*
import grails.plugin.core.party.*
import grails.plugin.core.menu.*
import grails.plugin.hr.party.*
import grails.plugin.hr.competency.*
import grails.plugin.survey.*

import la.didactic.core.party.*

import temp.*

import groovy.time.TimeCategory
import groovy.time.TimeDuration

import grails.converters.JSON

import org.apache.shiro.crypto.hash.Sha256Hash

class SetupCoreController {

  def partyService
  def schoolService

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

      switch (pr.type) {
        case 'EMPLOYEE':
          def employee = new Employee(party: party,
                                      type: Term.findByCode(pr.type),
                                      restricted: pr.restricted,
                                      code: pr.code
                                      )
          if (!employee.save()) {
            log.error "Unable to create Employee: ${employee}."
            employee.errors.each { log.error it }

            throw new RuntimeException("Unable to create Employee: ${employee}.")
          }

          def email = new Email(email: pr.contact.email, type: Term.findByCode(pr.contact.type))
          if (!email.save()) {
            log.error "Unable to create Email: ${email}."
            Email.errors.each { log.error it }

            throw new RuntimeException("Unable to create Email: ${email}.")
          }

          def employeeEmail = new PartyContactMechanism(party: party,
                                                        contactMechanism: email,
                                                        fromDate: new Date(),
                                                        thruDate: new Date(),
                                                        comment: 'comments'
                                                        )
          if (!employeeEmail.save()) {
            log.error "Unable to create Employee Email: ${employeeEmail}."
            employeeEmail.errors.each { log.error it }

            throw new RuntimeException("Unable to create Employee Email: ${employeeEmail}.")
          }
          break

        case 'SURVEY_INTERVIEWEE':
          def interviewee = new SurveyInterviewee(party: party,
                                                  type: Term.findByCode(pr.type),
                                                  restricted: pr.restricted
                                                 )

          if (!interviewee.save()) {
            log.error "Unable to create Survey Interviewee: ${interviewee.party}."
            interviewee.errors.each { log.error it }

            throw new RuntimeException("Unable to create Survey Interviewee: ${interviewee.party}.")
          }
          break

        default:
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
        from = PartyRole.findByParty(fromParty)
      }
      if (pr.from.person) {
        fromParty = Person.findByFirstNameAndLastName(pr.from.person.firstName, pr.from.person.lastName)
        from = PartyRole.findByParty(fromParty)
      }

      if (pr.to.organization) {
        toParty = Organization.findByName(pr.to.organization)
        to = PartyRole.findByParty(toParty)
      }
      if (pr.to.person) {
        toParty = Person.findByFirstNameAndLastName(pr.to.person.firstName, pr.to.person.lastName)
        to = PartyRole.findByParty(toParty)
      }
      if (pr.to.employee) {
        toParty = Employee.findByCode(pr.to.employee)

        if (!toParty) {
          log.error "Unable to retrieve Employee: ${pr.to.employee}."

          throw new RuntimeException("Unable to retrieve Employee: ${pr.to.employee}.")
        }
        to = toParty
      }

      type = Term.findByCode(pr.type)
      if (!type) {
        log.error "Unable to retrieve Party Relationship type: ${pr.type}."
        throw new RuntimeException("Unable to retrieve Party Relationship type: ${pr.type}.")
      }

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

  def competency() {
    println "competency: ${params}"
    
    def file = request.getFile('jsonCompetencyUpload')
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

    // Competency Model
    json.models.each { m ->
      def competencyModel = new CompetencyModel(code: m.code,
                                      name: m.name,
                                      description: m.description,
                                      restricted: m.restricted
                                      )
      if (!competencyModel.save(flush: true)) {
        log.error "Unable to create Competency Model: ${m.name}."
        competencyModel.errors.each { log.error it }

        throw new RuntimeException("Unable to create Competency Model: ${m.name}.")
      }
    }

    // Competency Level
    json.levels.each { l ->
      def competencyModel = CompetencyModel.findByCode(l.model)
      if (!competencyModel) {
        log.error "Unable to retrieve Competency Model: ${l.model}."
        throw new RuntimeException("Unable to retrieve Competency Model: ${l.model}.")
      }


      def competencyLevel = new CompetencyLevel(code: l.code,
                                                name: l.name,
                                                description: l.description,
                                                restricted: l.restricted
                                                )

      competencyModel.addToLevels(competencyLevel)

      if (!competencyModel.save(flush: true)) {
        log.error "Unable to add a Level to Competency Model: ${l.name}."
        competencyModel.errors.each { log.error it }

        throw new RuntimeException("Unable to add a Level to Competency Model: ${l.name}.")
      }
    }

    // Competency
    json.competencies.each { c ->
      def competencyModel = CompetencyModel.findByCode(c.model)
      if (!competencyModel) {
        log.error "Unable to retrieve Competency Model: ${c.model}."
        throw new RuntimeException("Unable to retrieve Competency Model: ${c.model}.")
      }

      def category = Term.findByCode(c.category)
      if (!category) {
        log.error "Unable to retrieve Competency category: ${c.category}."
        throw new RuntimeException("Unable to retrieve Competency category: ${c.category}.")
      }

      def competency = new Competency(code: c.code,
                                      name: c.name,
                                      description: c.description,
                                      category: category,
                                      restricted: c.restricted
                                      )

      c.indicators.each { i ->
        def competencyIndicator = new CompetencyIndicator(code: i.code,
                                                          name: i.name,
                                                          description: i.description,
                                                          restricted: i.restricted
                                                          )
        competency.addToIndicators(competencyIndicator)
      }

      competencyModel.addToCompetencies(competency)

      if (!competencyModel.save(flush: true)) {
        log.error "Unable to add a Competency to Competency Model: ${c.name}."
        competencyModel.errors.each { log.error it }

        throw new RuntimeException("Unable to add a Competency to Competency Model: ${c.name}.")
      }
    }

    [json: json]
  }

  def survey() {
    println "survey: ${params}"
    
    def file = request.getFile('jsonSurveyUpload')
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

    // Survey
    json.surveys.each { s ->
      def survey = new Survey(code: s.code,
                              title: s.title,
                              description: s.description,
                              instructions: s.instructions,
                              restricted: s.restricted
                              )

      s.answers.each { a->
        def answer = new SurveyAnswer(code: a.code,
                                      title: a.title,
                                      instructions: a.instructions,
                                      defaultAnswer: a.default,
                                      weight: a.weight,
                                      restricted: a.restricted
                                      )
        survey.addToAnswers(answer)
      }

      s.questions.each { q ->
        def question = new SurveyQuestion(code: q.code,
                                          title: q.title,
                                          description: q.description,
                                          instructions: q.instructions,
                                          optionalQuestion: q.optional,
                                          weight: q.weight,
                                          category: Term.findByCode(q.category),
                                          restricted: q.restricted
                                          )
        survey.answers.each { a->
          def answer = new SurveyQuestionAnswer(code: a.code,
                                                title: a.title,
                                                instructions: a.instructions,
                                                defaultAnswer: a.defaultAnswer,
                                                weight: a.weight,
                                                restricted: a.restricted
                                                )
          question.addToAnswers(answer)
        }

        survey.addToQuestions(question)
      }

      if (!survey.save()) {
        log.error "Unable to create Survey: ${survey}."
        survey.errors.each { log.error it }

        throw new RuntimeException("Unable to create Survey: ${survey}.")
      }
    }

    [json: json]
  }

  def surveyResponses() {
    println "surveyResponses: ${params}"
    
    def file = request.getFile('jsonSurveyResponsesUpload')
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

    // Survey Responses
    json.surveyQuestionResonses.each { r ->
      def type = Term.findByCode('SURVEY_INTERVIEWEE')
      if (!type) {
        log.error "Unable to retrieve Party Role: INTERVIEWEE"
        throw new RuntimeException("Unable to retrieve Party Role: INTERVIEWEE")
      }

      def person = Person.findByFirstNameAndLastName(r.interviewee.person.firstName, r.interviewee.person.lastName)
      if (!person) {
        log.error "Unable to retrieve Person: ${r.interviewee.person.firstName} ${r.interviewee.person.lastName}."
        throw new RuntimeException("Unable to retrieve Person: ${r.interviewee.person.firstName} ${r.interviewee.person.lastName}.")
      }

      def interviewee = SurveyInterviewee.findByPartyAndType(person, type)
      if (!interviewee) {
        log.error "Unable to retrieve Interviewee: ${person}."
        throw new RuntimeException("Unable to retrieve Interviewee: ${person}.")
      }

      r.surveys.each { s ->
        def survey = Survey.findByCode(s.survey)
        if (!survey) {
          log.error "Unable to retrieve Survey: ${s.survey}."
          throw new RuntimeException("Unable to retrieve Survey: ${s.survey}.")
        }

        def surveyAssigned = new SurveyAssigned(interviewee: interviewee,
                                                survey: survey,
                                                dateAssigned: new Date()-1,
                                                dateResponded: new Date())

        s.responses.each { resp ->
          def question = SurveyQuestion.findByCode(resp.question)
          if (!question) {
            log.error "Unable to retrieve Survey Question: ${resp.question}."
            throw new RuntimeException("Unable to retrieve Survey Question: ${resp.question}.")
          }

          def answer = SurveyQuestionAnswer.findByCode(resp.response)
          if (!answer) {
            log.error "Unable to retrieve Survey Answer: ${resp.response}."
            throw new RuntimeException("Unable to retrieve Survey Answer: ${resp.response}.")
          }
          def surveyAssignedResponse = new SurveyAssignedResponse(surveyAssigned: surveyAssigned,
                                                                  question: question,
                                                                  answer: answer,
                                                                  dateResponded: new Date()
                                                              )
          println "<<<< ${surveyAssignedResponse}"

          surveyAssigned.addToResponses(surveyAssignedResponse)
          if (!surveyAssigned.save()) {
            log.error "Unable to create Survey Assigned: ${surveyAssigned}."
            surveyAssigned.errors.each { log.error it }

            throw new RuntimeException("Unable to create Survey Assigned: ${surveyAssigned}.")
          }
        }
      }
    }

    [json: json]
  }

  def loadCentrosTrabajoSep() {
    Date start = new Date()

    def parentOrganization = Organization.findByName('SEP Estado de México')
    if (!parentOrganization) {
      log.error "Unable to retrieve parent Organization: SEP Estado de México."
      throw new RuntimeException("Unable to retrieve parent Organization: SEP Estado de México.")
    }

    def parentPartyRole = PartyRole.findByParty(parentOrganization)
    if (!parentPartyRole) {
      log.error "Unable to retrieve parent Party Role: ${parentOrganization}."
      throw new RuntimeException("Unable to retrieve parent Party Role: ${parentOrganization}.")
    }

    def record = CentrosTrabajoSep.list()

    record.each { r->
      def geographicBoundary = GeographicBoundary.get(r.municipio)
      if (!geographicBoundary) {
        log.error "Unable to retrieve Geographic Boundary: ${r.municipio}."
        throw new RuntimeException("Unable to retrieve Geographic Boundary: ${r.municipio}.")
      }

      def postalAddress = new PostalAddress(address1: (r.domicilio ? r.domicilio : 'N/D'),
                                            address2: (r.localidad ? r.localidad : 'N/D'),
                                            geographicBoundary: geographicBoundary
                                            )

      def school = schoolService.registerSchool(parentPartyRole,
                                                r.cct,
                                                r.centroEducativo,
                                                r.ambito,
                                                r.control,
                                                r.servicio,
                                                postalAddress
                                                )
    }

    Date stop = new Date()
    TimeDuration td = TimeCategory.minus( stop, start )
    render "Centros de Trabajo cargados: ${record.size()} (duración ${td})"
  }

  def loadProfesores() {
    Date start = new Date()

    def record = ProfesoresCct.list()

    record.each { r->
      def school = School.findByCode(r.cct)
      if (!school) {
        log.error "Unable to retrieve School: ${r.cct}."
        throw new RuntimeException("Unable to retrieve School: ${r.cct}.")
      }

      def profesor = Profesores.get(r.id)
      if (!profesor) {
        log.error "Unable to retrieve Profesor: ${r.id}."
        throw new RuntimeException("Unable to retrieve Profesor: ${r.id}.")
      }

      if (!Employee.findByCode(profesor.servidorPublicoClave) && profesor.servidorPublicoClave) {
        def emailType = Term.findByCode(partyService.EMAIL_WORK)
        if (!emailType) {
          log.error "Unable to retrive Term: ${partyService.EMAIL_WORK}."
          throw new RuntimeException("Unable to retrive Term: ${partyService.EMAIL_WORK}.")
        }

        def email = new Email(email: profesor.correoElectronico,
                              type: emailType)

        def person = new Person(firstName: profesor.nombre,
                                lastName: '.'
                                )

        def employee = new Employee(code: profesor.servidorPublicoClave,
                                    party: person
                                    )

        def professor = schoolService.hireProfessor( school, employee, [contactMechanism: email] )
      }

    }

    Date stop = new Date()
    TimeDuration td = TimeCategory.minus( stop, start )
    render "Profesores cargados: ${record.size()} (duración ${td})"
  }

  def loadSurveyQuestions() {
    Date start = new Date()

    def surveys = Survey.list()

    surveys.each { s->
      println "Survey: ${s}"
      s.questions.each { q->
        println "Question: ${q}"
        s.answers.each { a ->
          println "Answer: ${a}"
          def questionAnswer = new SurveyQuestionAnswer(code: a.code,
                                                        title: a.title,
                                                        instructions: a.instructions,
                                                        defaultAnswer: a.defaultAnswer,
                                                        weight: a.weight,
                                                        restricted: a.restricted
                                                        )
          q.addToAnswers(questionAnswer)
        }
        if (!q.save()) {
          log.error "Unable to update Survey Question: ${q} with answers."
          q.errors.each { log.error it }

          throw new RuntimeException("Unable to update Survey Question: ${q} with answers.")
        }
      }
    }


    Date stop = new Date()
    TimeDuration td = TimeCategory.minus( stop, start )
    render "Respuestas a Preguntas de Encuestas cargadas (duración ${td})"
  }

  def loadResultadoEncuesta() {
    Date start = new Date()

    def survey = Survey.findByCode('NECESIDADES_CAPACITACION')
    if (!survey) {
      log.error "Unable to retrieve Survey: 'NECESIDADES_CAPACITACION'."
      throw new RuntimeException("Unable to retrieve Survey: 'NECESIDADES_CAPACITACION'.")
    }

    def record = EncuestaEncabezado.list()

    record.each { r->
      def p = Profesores.get(r.profesorId)
      if (!p) {
        log.error "Unable to retrieve Profesor: ${r.profesorId}."
        throw new RuntimeException("Unable to retrieve Profesor: ${r.profesorId}.")
      }

      def professor = Employee.findByCode(p.servidorPublicoClave)
      if (!professor) {
        log.error "Unable to retrieve Professor: ${p.servidorPublicoClave}."
        println "${p.servidorPublicoClave}"
        //throw new RuntimeException("Unable to retrieve Professor: ${p.servidorPublicoClave}.")
      } else {
/*      def type = Term.findByCode('SURVEY_INTERVIEWEE')
        if (!type) {
          log.error "Unable to retrieve Party Role: INTERVIEWEE"
          throw new RuntimeException("Unable to retrieve Party Role: INTERVIEWEE")
        }

        def interviewee = new SurveyInterviewee(party: professor.party,
                                                fromDate: new Date(),
                                                thruDate: new Date(),
                                                type: type,
                                                restricted: false
                                                )*/

        def surveyAssigned = new SurveyAssigned(interviewee: professor,
                                                survey: survey,
                                                dateAssigned: Date.parse("yyyy-MM-dd", r.fechaInicio),
                                                dateResponded: Date.parse("yyyy-MM-dd", r.fechaFin)
                                                )
        if (!surveyAssigned.save()) {
          log.error "Unable to add SurveyAssigned: ${surveyAssigned}."
          surveyAssigned.errors.each { log.error it }

          throw new RuntimeException("Unable to add SurveyAssigned: ${surveyAssigned}.")
        }

        def encuestaDetalle = EncuestaDetalle.findAllBySurveyMonkeyId(r.surveyMonkeyId)

        encuestaDetalle.each { d->
          def sQuestion = SurveyQuestion.findByCode(d.estandarId)
          if (!sQuestion) {
            log.error "Unable to retrieve Survey Question: ${sQuestion}."
            throw new RuntimeException("Unable to retrieve Survey Question: ${sQuestion}.")
          }

          def sQuestionAnswer = SurveyQuestionAnswer.findByQuestionAndCode(sQuestion, d.escalaId)
          if (!sQuestionAnswer) {
            log.error "Unable to retrieve Survey Question Answer: ${sQuestionAnswer}."
            throw new RuntimeException("Unable to retrieve Survey Question Answer: ${sQuestionAnswer}.")
          }

          def surveyAssignedResponse = new SurveyAssignedResponse(surveyAssigned: surveyAssigned,
                                                                question: sQuestion,
                                                                answer: sQuestionAnswer,
                                                                dateResponded: Date.parse("yyyy-MM-dd", r.fechaFin)
                                                                )
          if (!surveyAssignedResponse.save()) {
            log.error "Unable to add SurveyAssignedResponse: ${surveyAssignedResponse}."
            surveyAssignedResponse.errors.each { log.error it }

            throw new RuntimeException("Unable to add SurveyAssignedResponse: ${surveyAssignedResponse}.")
          }

        }
        
      }

    }

    Date stop = new Date()
    TimeDuration td = TimeCategory.minus( stop, start )
    render "Resultados de encuesta cargadas: ${record.size()} (duración ${td})"
  }

}