package grails.plugin.core.auth

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.DisabledAccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authz.permission.WildcardPermission

class DbRealm {
  static authTokenClass = org.apache.shiro.authc.UsernamePasswordToken

  def credentialMatcher
  def shiroPermissionResolver
  def adminService

  def authenticate(authToken) {
    log.info "Attempting to authenticate ${authToken.username} from local repository"
    def username = authToken.username

    // Null username is invalid
    if (username == null) {
        throw new AccountException("Null usernames are not allowed by this realm.")
    }

    // Get the user with the given username. If the user is not
    // found, then they don't have an account and we throw an
    // exception.
    def user = User.findByUsername(username)
    if (!user) {
        throw new UnknownAccountException("No account found for user [${username}]")
    }

    log.info "Found user '[$user.id]${user.username}' in data repository, starting authentication process"

    if (!user.enabled) {
      log.warn "User [$user.id]$user.username is disabled preventing authentication"
      throw new DisabledAccountException("This account is currently disabled")
    }

    // Now check the user's password against the hashed value stored
    // in the database.
    def account = new SimpleAccount(username, user.passwordHash, "DbRealm")
    if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
        log.info "Supplied password for user [$user.id]:$user.username is incorrect"
        throw new IncorrectCredentialsException("Invalid password for user '${username}'")
    }

    log.info("Successfully logged in user [$user.id]:$user.username using local repository")
    return account
  }

  def hasRole(principal, roleName) {
    def roles = User.withCriteria {
        roles {
            eq("name", roleName)
        }
        eq("username", principal)
    }

    return roles.size() > 0
  }

  def hasAllRoles(principal, roles) {
      def r = User.withCriteria {
          roles {
              'in'("name", roles)
          }
          eq("username", principal)
      }

      return r.size() == roles.size()
  }

  def isPermitted(principal, requiredPermission) {
    boolean permitted = false

    def user = User.findByUsername(principal)

    // Get the permissions from the roles that the user does have.
    log.debug("Determining if roles assigned to user [$user.id]:$user.username contain a permission that implies $requiredPermission")

    rolepermsearch:
    for (role in user.roles) {
      println "${user.username} | ${role} | ${requiredPermission}"
      if (role.name == AdminService.ADMIN_ROLE) {
        permitted = true
        break rolepermsearch
      } else {
        for (permission in role.permissions) {
          println permission
          def perm = new WildcardPermission(permission.toString(), false)
          if (perm.implies(requiredPermission)) {
            permitted = true
            break rolepermsearch
          }
        }
      }
    }

    if (permitted)
      log.debug("User [$user.id]:$user.username has a permission which implies $requiredPermission via role memberships")
    else
      log.debug("User [$user.id]:$user.username does not have a permission which implies $requiredPermission via role memberships")

    return permitted
  }
}
