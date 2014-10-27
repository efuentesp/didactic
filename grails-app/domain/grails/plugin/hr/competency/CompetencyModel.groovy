package grails.plugin.hr.competency

class CompetencyModel implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [
    competencies: Competency,
    levels: CompetencyLevel
  ]

  static constrains = {
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    competencies (nullable: true)
    levels (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}