package grails.plugin.core.auth

class RoleService {

	Role createRole(String name, String description, boolean protect) {
		def role = new Role(name: name, description: description, protect: protect)

		if(!role.validate()) {
			log.debug("Supplied values for new role are invalid")
			role.errors.each { log.debug it }
			return role
		}

		def savedRole = role.save()
		if (savedRole) {
			log.info("Created role [$role.id]$role.name")
			return savedRole
		}
	}

}