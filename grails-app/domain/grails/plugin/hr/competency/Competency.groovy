package grails.plugin.hr.competency

import grails.plugin.core.taxonomy.*

class Competency implements Serializable {

  String code
  String name
  String description

  Term category

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [model: CompetencyModel]

  static hasMany = [indicators: CompetencyIndicator]

  static constrains = {
    model (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    category (nullable: false)
    indicators (nullable: true)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}