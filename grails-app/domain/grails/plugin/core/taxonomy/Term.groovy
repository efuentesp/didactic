package grails.plugin.core.taxonomy

class Term implements Serializable {

  String code
  String name
  String description
  Integer weight = 0
  Term parent

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static belongsTo = [vocabulary: Vocabulary]

  static mapping = {
    sort weight: 'asc' 
  }

  static constrains = {
    vocabulary (nullable: true)
    parent (nullable: true)
    code (nullable: false, blank: false, unique: true)
    name (nullable: false, blank: false, unique: true)
    description (nullable: true)
    weight (nullable: false)
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    name
  }

}