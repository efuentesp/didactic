package la.didactic.core.auth

import grails.plugin.core.party.GeographicBoundary

class AdminController {

  def index() {
    println "${params}"
    if (params?.municipality) {
      def municipality = GeographicBoundary.get(params.municipality.toLong())
      if (!municipality) {
        log.error "Unable to retrive Municipality: ${params.municipality}."
        throw new RuntimeException("Unable to retrive Municipality: ${params.municipality}.")
      }
      println municipality.name
      return [municipality: municipality]
    }
  }

}