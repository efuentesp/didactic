package grails.plugin.core.menu

class MenuItem implements Serializable {

  String name
  String title
  String description
  String icon
  String permission = "*"
  Integer weight

  String linkController
  String linkAction

  static belongsTo = [menu: Menu]
  
  boolean restricted = false
  Date dateCreated = new Date()
  Date lastUpdated = new Date()

  static mapping = {
    sort weight: 'asc'
  }

  static constraints = {
    name (nullable: false, blank: false, unique: true)
    title (nullable: false, blank: false)
    description (nullable: true, blank: false)
    icon (nullable: true, blank: false)
    permission (nullable: true, blank: false)
    weight (nullable: false)

    linkController (nullable: true, blank: false)
    linkAction (nullable: true, blank: false)

    menu (nullable: false)

    restricted (nullable: false)
  }

  String toString() {
    "${title} : ${linkController}/${linkAction}"
  }

}