package grails.plugin.survey

class ViewSurveyResultsByMunicipality {

  String id
  Long stateId
  String stateName
  Long municipalityId
  String municipalityName
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
    table 'view_survey_results_by_municipality'
    stateId column: 'state_id '
    stateName column: 'state_name'
    municipalityId column: 'municipality_id'
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