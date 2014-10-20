package grails.plugin.core.auth

class Permission implements Serializable {

	String module
	String code
	String title
	String description

	String domain
	String actions = "*"
	String instances = "*"

	boolean restricted = false
	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	static belongsTo = [Role]

	static hasMany = [
		roles: Role
	]

	static constraints = {
		module (nullable: false, blank: false)
		code (nullable: false, blank: false, unique: true)
		title (nullable: false, blank: false)
		description (nullable: true, blank: false)
		domain (blank: false)
		actions (blank: false)
		instances (blank: false)

		roles (nullable: true)
	}

	String toString() {
		"${domain}:${actions}:${instances}"
	}
}