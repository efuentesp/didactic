import grails.plugin.core.auth.*
import grails.plugin.core.menu.*

import org.apache.shiro.crypto.hash.Sha256Hash

class BootStrap {

	def userService
	def adminService

	def init = { servletContext ->

		def adminRole = Role.findByName(AdminService.ADMIN_ROLE)
		if (!adminRole) {
			adminRole = new Role(description: 'Assigned to users who are considered to be system wide administrators',
			                     name: AdminService.ADMIN_ROLE,
			                     restricted: true)
			adminRole.save()

			if (adminRole.hasErrors()) {
				adminRole.errors.each { log.error(it) }
				throw new RuntimeException("Unable to create valid administrative role")
			}
		}

		if(!User.findByUsername("admin")) {
			// Create example Administrative account
			def admins = Role.findByName(AdminService.ADMIN_ROLE)

			def admin = new User (username: 'admin',
														passwordHash: new Sha256Hash("admin").toHex(),
														name: 'Administrator',
														email: 'admin@mail.com',
														enabled: true,
														restricted: true )

			log.info("Creating default admin account with username: admin")

			adminRole.addToUsers(admin)
			admin.addToRoles(adminRole)

			if (!adminRole.save()) {
				log.error "Unable to grant administration privilege to [$admin.id]:$admin.username"
				adminRole.errors.each {  log.error '[${admin.username}] - ' + it }
				adminRole.discard()
				admin.discard()
				throw new RuntimeException("Unable to grant administration privilege to [$admin.id]:$admin.username")
			}

			if (!admin.save()) {
				log.error "Unable to grant administration role to [$admin.id]$admin.username failed to modify admin account"
				admin.errors.each { log.error it }

				throw new RuntimeException("Unable to grant administration role to [$admin.id]$admin.username")
			}

			log.info "Granted administration privileges to [$admin.id]:$admin.username"
		}

		if(!Menu.findByName("menu.core.main")) {
			def mainMenu = new Menu(module: 'core',
															name: 'menu.core.main',
															title: 'Main Menu',
															permission: '*',
															restricted: true)

			if (!mainMenu.save()) {
				log.error "Unable to create Main Menu."
				mainMenu.errors.each {  log.error '[${mainMenu.name}] - ' + it }
				mainMenu.discard()
				throw new RuntimeException("Unable to grant administration privilege to [$mainMenu.id]:$mainMenu.name")
			}
		}

	}

  def destroy = {
	}

}
