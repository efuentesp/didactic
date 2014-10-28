package grails.plugin.survey

class SurveyAssignedResponse implements Serializable {

  Date dateResponded = new Date()

  static belongsTo = [
    surveyAssigned: SurveyAssigned,
    question: SurveyQuestion,
    answer: SurveyAnswer
  ]

  static constrains = {
    dateResponded (nullable: false)
    survey (nullable: false)
    question (nullable: false)
    answer (nullable: false)
  }

  String toString() {
    "${dateResponded} : ${interviwee} : ${question} : ${answer}"
  }

}