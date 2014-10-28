package grails.plugin.survey

import grails.plugin.core.taxonomy.*

class SurveyQuestionAnswer implements Serializable {

  String code
  String title
  String instructions
  Boolean defaultAnswer = false
  Integer weight

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [question: SurveyQuestion]

  static constrains = {
    question (nullable: false)
    code (nullable: false)
    title (nullable: false)
    instructions (nullable: true)
    defaultAnswer (nullable: false)
    weight (nullable: false)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    title
  }

}