package grails.plugin.survey

class SurveyAnswer implements Serializable {

  String code
  String title
  String instructions
  Boolean defaultAnswer = false
  Integer weight

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [survey: Survey]

  static mapping = {
    sort weight: 'asc'
  }

  static constrains = {
    survey (nullable: false)
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