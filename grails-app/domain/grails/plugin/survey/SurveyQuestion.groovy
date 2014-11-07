package grails.plugin.survey

import grails.plugin.core.taxonomy.*

class SurveyQuestion implements Serializable {

  String code
  String title
  String description
  String instructions
  Boolean optionalQuestion = false
  Integer weight

  Term category

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [survey: Survey]

  static hasMany = [answers: SurveyQuestionAnswer]

  static constrains = {
    survey (nullable: false)
    code (nullable: false)
    title (nullable: false)
    description (nullable: true)
    instructions (nullable: true)
    optionalQuestion (nullable: false)
    weight (nullable: false)
    category (nullable: false)
    answers (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    title
  }

}