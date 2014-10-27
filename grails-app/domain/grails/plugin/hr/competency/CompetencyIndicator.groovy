package grails.plugin.hr.competency

class CompetencyIndicator implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [competency: Competency]

  static constrains = {
    competency (nullable: false)
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