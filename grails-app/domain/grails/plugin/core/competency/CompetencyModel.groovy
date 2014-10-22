package grails.plugin.core.competency

class CompetencyModel implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [framework: CompetencyFramework]

  static hasMany = [
    competencies: Competency
  ]

  static constrains = {
    framework (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    competencies (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}