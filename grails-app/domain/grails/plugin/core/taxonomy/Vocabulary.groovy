package grails.plugin.core.taxonomy

class Vocabulary implements Serializable {

  String code
  String name
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static hasMany = [terms: Term]

  static mapping = {
    sort name: 'desc' 
  }

  static constrains = {
    code (nullable: false, blank: false, unique: true)
    name (nullable: false, blank: false, unique: true)
    description (nullable: false, blank: false)
    terms (nullable: true)
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}