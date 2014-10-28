package grails.plugin.survey

class SurveyAssigned implements Serializable {

  Date dateAssigned = new Date()
  Date dateResponded = new Date()

  static belongsTo = [
    interviewee: SurveyInterviewee,
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
    "${dateAssigned} : ${dateResponded} : ${interviewee} : ${survey} : (${responses.size()})"
  }

}