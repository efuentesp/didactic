package grails.plugin.core.competency

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

  static constrains = {
    model (nullable: false)
    code (nullable: false)
    name (nullable: false)
    description (nullable: true)
    category (nullable: false)
    restricted (nullable: false)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

}