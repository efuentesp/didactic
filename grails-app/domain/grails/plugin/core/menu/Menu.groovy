package grails.plugin.core.menu

class Menu implements Serializable {

  String module
  String name
  String title
  String description
  String icon
  String permission ="*"

  static belongsTo = [parent: Menu]
  static hasMany = [submenus: Menu, items: MenuItem]
  
  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static constraints = {
    module (nullable: false, blank: false)
    name (nullable: false, blank: false, unique: true)
    title (nullable: false, blank: false)
    description (nullable: true, blank: false)
    icon (nullable: true, blank: false)
    permission (nullable: true, blank: false)

    parent (nullable: true)
    submenus (nullable: true)
    items (nullable: true)

    restricted (nullable: false)
  }

  String toString() {
    title
  }

}