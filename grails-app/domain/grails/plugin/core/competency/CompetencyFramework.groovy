package grails.plugin.core.competency

class CompetencyFramework implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [
    models: CompetencyModel,
    levels: CompetencyLevel
  ]

  static constrains = {
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    models (nullable: true)
    levels (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}