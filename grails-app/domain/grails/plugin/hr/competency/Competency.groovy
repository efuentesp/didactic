package grails.plugin.hr.competency

import grails.plugin.core.taxonomy.*

class Competency implements Serializable {

  String code
  String name
  String description
  Integer weight

  Term category

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [model: CompetencyModel]

  static hasMany = [indicators: CompetencyIndicator]

  static mapping = {
    sort weight: 'asc'
  }

  static constrains = {
    model (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    weight (nullable: false)
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