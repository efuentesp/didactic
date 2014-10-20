package grails.plugin.core.auth

class Role implements Serializable {

	String name
	String description
	
	boolean restricted = false
	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	static hasMany = [
		users: User,
		permissions: Permission
	]

	static mapping = {
		sort "name"
	}

	static constraints = {
		name (blank: false, unique: true, minSize: 4, maxSize: 255)
		description (nullable: true, blank: false)
		permissions (nullable: true)
		users (nullable: true)
		restricted (nullable: false)
	}

	String toString() {
		name
	}
}