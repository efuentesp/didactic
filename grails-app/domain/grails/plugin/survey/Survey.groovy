package grails.plugin.survey

class Survey implements Serializable {

  String code
  String title
  String description
  String instructions

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [
    answers: SurveyAnswer,
    questions: SurveyQuestion
  ]

  static constrains = {
    code (nullable: false)
    title (nullable: false)
    description (nullable: true)
    instructions (nullable: true)
    answers (nullable: true)
    questions (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    title
  }

}