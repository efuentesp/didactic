package grails.plugin.core.party

class Gender implements Serializable {

  String code
  String description

  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static mapping = {
    sort description: 'desc' 
  }

  static constrains = {
    code (nullable: false, blank: false, unique: true)
    description (nullable: false, blank: false, unique: true)
    restricted (nullable: true)
    dateCreated (nullable: true)
    lastUpdated (nullable: true)
  }

  String toString() {
    description
  }

}