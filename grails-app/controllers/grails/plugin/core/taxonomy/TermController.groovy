package grails.plugin.core.taxonomy

import grails.converters.JSON

class TermController {

  public static final String EDUCATIONAL_SERVICE_TYPE = 'EDUCATIONAL_SERVICE_TYPE'
  public static final String EDUCATIONAL_CONTROL_TYPE = 'EDUCATIONAL_CONTROL_TYPE'
  public static final String EDUCATIONAL_AREA_TYPE = 'EDUCATIONAL_AREA_TYPE'

  static scaffold = true

  def jsonEducationalServices() {

    def educationalServiceType = Vocabulary.findByCode(EDUCATIONAL_SERVICE_TYPE)
    if (!educationalServiceType) {
      log.error "Unable to retrive Vocubalary type: 'EDUCATIONAL_SERVICE_TYPE'."
      throw new RuntimeException("Unable to retrive Vocubalary type: 'EDUCATIONAL_SERVICE_TYPE'.")
    }

    def educationalServices = Term.findAllByVocabulary(educationalServiceType)
    if (!educationalServices) {
      log.error "Unable to retrive Terms for Vocubalary: '${educationalServiceType}'."
      throw new RuntimeException("Unable to retrive Terms for Vocubalary: '${educationalServiceType}'.")
    }

    render educationalServices as JSON
  }

  def jsonEducationalControls() {

    def educationalControlType = Vocabulary.findByCode(EDUCATIONAL_CONTROL_TYPE)
    if (!educationalControlType) {
      log.error "Unable to retrive Vocubalary type: 'EDUCATIONAL_CONTROL_TYPE'."
      throw new RuntimeException("Unable to retrive Vocubalary type: 'EDUCATIONAL_CONTROL_TYPE'.")
    }

    def educationalControls = Term.findAllByVocabulary(educationalControlType)
    if (!educationalControls) {
      log.error "Unable to retrive Terms for Vocubalary: '${educationalControlType}'."
      throw new RuntimeException("Unable to retrive Terms for Vocubalary: '${educationalControlType}'.")
    }

    render educationalControls as JSON
  }

  def jsonEducationalAreas() {

    def educationalAreaType = Vocabulary.findByCode(EDUCATIONAL_AREA_TYPE)
    if (!educationalAreaType) {
      log.error "Unable to retrive Vocubalary type: 'EDUCATIONAL_AREA_TYPE'."
      throw new RuntimeException("Unable to retrive Vocubalary type: 'EDUCATIONAL_AREA_TYPE'.")
    }

    def educationalAreas = Term.findAllByVocabulary(educationalAreaType)
    if (!educationalAreas) {
      log.error "Unable to retrive Terms for Vocubalary: '${educationalAreaType}'."
      throw new RuntimeException("Unable to retrive Terms for Vocubalary: '${educationalAreaType}'.")
    }

    render educationalAreas as JSON
  }

}