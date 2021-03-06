package grails.plugin.core.auth

/**
 * Generated by the Shiro plugin. This filters class protects all URLs
 * via access control by convention.
 */
class SecurityFilters {
    def filters = {
        all(uri: "/**") {
            before = {
                println (controllerName+':'+actionName)

                // Ignore direct views (e.g. the default main index page).
                if (!controllerName || controllerName == 'assets') return true

                // Access control by convention.
                //accessControl()

                if ((controllerName+':'+actionName) in ['professor:print', 'jsonCompetencySummary:index']) {
                  return true
                } else {
                  accessControl() 
                }
            }
        }
    }
}
