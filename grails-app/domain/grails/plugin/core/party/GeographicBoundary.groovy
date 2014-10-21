package grails.plugin.core.party

import grails.plugin.core.taxonomy.*

class GeographicBoundary implements Serializable {

  String code
  String name
  String abbreviation

  Term type

  GeographicBoundary parent

  static constraints = {
    code (nullable: false, blank: false)
    name (nullable: false, blank: false)
    abbreviation (nullable: true)
    type (nullable: false)
    parent (nullable: true)
  }

  String toString() {
    name
  }

}