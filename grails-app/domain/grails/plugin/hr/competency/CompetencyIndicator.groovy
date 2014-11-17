package grails.plugin.hr.competency

class CompetencyIndicator implements Serializable {

  String code
  String name
  String description
  Integer weight

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [competency: Competency]

  static mapping = {
    sort weight: 'asc'
  }

  static constrains = {
    competency (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    weight (nullable: false)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}