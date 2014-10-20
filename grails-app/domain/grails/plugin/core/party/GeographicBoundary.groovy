package grails.plugin.core.party

class GeographicBoundary implements Serializable {

  String code
  String name
  String abbreviation

  static constraints = {
    code (nullable: false, blank: false)
    name (nullable: false, blank: false)
    abbreviation (nullable: true)
  }

  String toString() {
    name
  }

}