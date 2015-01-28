package grails.plugin.survey

class ViewSurveyResults {

  String id
  Long stateId
  String stateName
  Long subdirectionId
  String subdirectionName
  Long municipalityId
  String municipalityName
  Long serviceId
  String serviceCode
  String serviceName
  Long controlId
  String controlCode
  String controlName
  Long areaId
  String areaCode
  String areaName
  Long questionCategoryId
  String questionCategoryCode
  String questionCategoryName
  Long questionCompetencyId
  String questionCompetencyCode
  String questionCompetencyName
  Long questionIndicatorId
  String questionIndicatorCode
  String questionIndicatorTitle
  Float level

  static mapping = {
    id generator: 'assigned'
    table 'view_survey_results'

    stateId column: 'state_id '
    stateName column: 'state_name'

    subdirectionId column: 'subdirection_id '
    subdirectionName column: 'subdirection_name'

    municipalityId column: 'municipality_id'
    municipalityName column: 'municipality_name'

    serviceId column: 'service_id'
    serviceCode column: 'service_code'
    serviceName column: 'service_name'

    controlId column: 'control_id'
    controlCode column: 'control_code'
    controlName column: 'control_name'

    areaId column: 'area_id'
    areaCode column: 'area_code'
    areaName column: 'area_name'

    questionCategoryId column: 'question_category_id'
    questionCategoryCode column: 'question_category_code'
    questionCategoryName column: 'question_category_name'

    questionCompetencyId column: 'question_competency_id'
    questionCompetencyCode column: 'question_competency_code'
    questionCompetencyName column: 'question_competency_name'

    questionIndicatorId column: 'question_indicator_id'
    questionIndicatorCode column: 'question_indicator_code'
    questionIndicatorTitle column: 'question_indicator_title'
    
    level column: 'level'
  }
}