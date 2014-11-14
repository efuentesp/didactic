package la.didactic.core.auth

import grails.plugin.hr.party.Employee

class ProfessorController {

  def index() {}

  def print() {
    def professor = Employee.findByCode(params.id)

    [ professor: professor ]
  }

}