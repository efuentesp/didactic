package grails.plugin.hr.competency

class CompetencyLevel implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [model: CompetencyModel]

  static constrains = {
    model (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}