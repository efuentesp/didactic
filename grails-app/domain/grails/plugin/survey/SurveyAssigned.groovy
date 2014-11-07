package grails.plugin.survey

import grails.plugin.core.party.PartyRole

class SurveyAssigned implements Serializable {

  Date dateAssigned = new Date()
  Date dateResponded = new Date()

  static belongsTo = [
    interviewee: PartyRole,
    survey: Survey
  ]

  static hasMany = [responses: SurveyAssignedResponse]

  static constrains = {
    dateAssigned (nullable: false)
    dateResponded (nullable: false)
    interviewee (nullable: false)
    survey (nullable: false)
    responses (nullable: true)
  }

  String toString() {
    if (responses) {
      "${dateAssigned} : ${dateResponded} : ${interviewee} : ${survey} : (${responses.size()})"
    } else {
      "${dateAssigned} : ${dateResponded} : ${interviewee} : ${survey} : (0)"
    }
  }

}