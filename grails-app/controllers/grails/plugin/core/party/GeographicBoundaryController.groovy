package grails.plugin.core.party

import grails.plugin.core.taxonomy.Vocabulary
import grails.plugin.core.taxonomy.Term

import grails.converters.JSON

class GeographicBoundaryController {

  public static final String GEOGRAPHIC_BOUNDARY_TYPE = 'GEOGRAPHIC_BOUNDARY_TYPE'
  public static final String COUNTRY = 'COUNTRY'
  public static final String STATE = 'STATE'
  public static final String MUNICIPALITY = 'MUNICIPALITY'
  public static final String SUBDIRECTION = 'SUBDIRECTION'

  static scaffold = true

  def jsonStates() {
    println "jsonStates(): ${params}"

    def geoBoundaryType = Vocabulary.findByCode(GEOGRAPHIC_BOUNDARY_TYPE)
    if (!geoBoundaryType) {
      log.error "Unable to retrive Vocubalary type: 'GEOGRAPHIC_BOUNDARY_TYPE'."
      throw new RuntimeException("Unable to retrive Vocubalary type: 'GEOGRAPHIC_BOUNDARY_TYPE'.")
    }

    def countrtyType = Term.findByVocabularyAndCode(geoBoundaryType, COUNTRY)
    if (!countrtyType) {
      log.error "Unable to retrive Term type: 'COUNTRY'."
      throw new RuntimeException("Unable to retrive Term type: 'COUNTRY'.")
    }

    def country = GeographicBoundary.findByTypeAndCode(countrtyType, params.country)
    if (!country) {
      log.error "Unable to retrive GeographicBoundary: '${params.contry}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${params.contry}'.")
    }

    def stateType = Term.findByCode(STATE)
    if (!stateType) {
      log.error "Unable to retrive Term type: 'STATE'."
      throw new RuntimeException("Unable to retrive Term type: 'STATE'.")
    }

    def states = GeographicBoundary.findAllByTypeAndParent(stateType, country)
    if (!states) {
      log.error "Unable to retrive GeographicBoundary: '${stateType} / ${country}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${stateType} / ${country}'.")
    }

    render states as JSON
  }

  def jsonSubdirections() {

    def stateType = Term.findByCode(STATE)
    if (!stateType) {
      log.error "Unable to retrive Term type: 'STATE'."
      throw new RuntimeException("Unable to retrive Term type: 'STATE'.")
    }

    def state = GeographicBoundary.findByTypeAndId(stateType, params.state)
    if (!state) {
      log.error "Unable to retrive GeographicBoundary: '${stateType} / ${params.state}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${stateType} / ${params.state}'.")
    }

    def subdirectionType = Term.findByCode(SUBDIRECTION)
    if (!subdirectionType) {
      log.error "Unable to retrive Term type: 'SUBDIRECTION'."
      throw new RuntimeException("Unable to retrive Term type: 'SUBDIRECTION'.")
    }

    def subdirections = GeographicBoundary.findAllByTypeAndParent(subdirectionType, state)
    if (!subdirections) {
      log.error "Unable to retrive GeographicBoundary: '${subdirectionType} / ${state}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${subdirectionType} / ${state}'.")
    }

    render subdirections as JSON
  }

  def jsonMunicipalities() {

    def subdirectionType = Term.findByCode(SUBDIRECTION)
    if (!subdirectionType) {
      log.error "Unable to retrive Term type: 'SUBDIRECTION'."
      throw new RuntimeException("Unable to retrive Term type: 'SUBDIRECTION'.")
    }

    def subdirection = GeographicBoundary.findByTypeAndId(subdirectionType, params.subdirection)
    if (!subdirection) {
      log.error "Unable to retrive GeographicBoundary: '${subdirectionType} / ${params.subdirection}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${subdirectionType} / ${params.subdirection}'.")
    }

    def municipalityType = Term.findByCode(MUNICIPALITY)
    if (!municipalityType) {
      log.error "Unable to retrive Term type: 'MUNICIPALITY'."
      throw new RuntimeException("Unable to retrive Term type: 'MUNICIPALITY'.")
    }

    def municipalities = GeographicBoundary.findAllByTypeAndParent(municipalityType, subdirection)
    if (!municipalities) {
      log.error "Unable to retrive GeographicBoundary: '${municipalityType} / ${subdirection}'."
      throw new RuntimeException("Unable to retrive GeographicBoundary: '${municipalityType} / ${subdirection}'.")
    }

    render municipalities as JSON
  }

}