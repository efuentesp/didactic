package la.didactic.survey

import grails.plugin.core.taxonomy.Vocabulary
import grails.plugin.core.taxonomy.Term

import grails.plugin.hr.party.Employee

import grails.plugin.core.party.PartyRelationship
import grails.plugin.core.party.GeographicBoundary

import grails.plugin.hr.competency.CompetencyModel
import grails.plugin.hr.competency.CompetencyLevel

import grails.plugin.survey.Survey
// import grails.plugin.survey.SurveyAnswer
import grails.plugin.survey.SurveyQuestion
import grails.plugin.survey.SurveyAssigned
import grails.plugin.survey.ViewSurveyResultsByMunicipality

class SurveyService {

  public static final String NECESIDADES_CAPACITACION = 'NECESIDADES_CAPACITACION'
  public static final String SURVEY_QUESTION_CATEGORY = 'SURVEY_QUESTION_CATEGORY'

  def chartCompetencySurveyResults(params) {
println "chartCompetencySurveyResults(): ${params}"

    if (params?.municipality) {
      def municipality = GeographicBoundary.get(params.municipality)
      if (!municipality) {
        log.error "Unable to retrive Municipality: ${params.municipality}."
        throw new RuntimeException("Unable to retrive Municipality: ${params.municipality}.")
      }
println municipality.name
    }

    def survey = Survey.findByCode(NECESIDADES_CAPACITACION)
    if (!survey) {
      log.error "Unable to retrive Survey: 'NECESIDADES_CAPACITACION'."
      throw new RuntimeException("Unable to retrive Survey: 'NECESIDADES_CAPACITACION'.")
    }

    def vocabularyQuestionCategory = Vocabulary.findByCode(SURVEY_QUESTION_CATEGORY)
    if (!vocabularyQuestionCategory) {
      log.error "Unable to retrive Survey Question Category Vocabulary: 'SURVEY_QUESTION_CATEGORY'."
      throw new RuntimeException("Unable to retrive Survey Question Category Vocabulary: 'SURVEY_QUESTION_CATEGORY'.")
    }

    def charts = [
      y: [],
      category:   [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
      competency: [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
      indicator:  [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
    ]

/*    def surveyAnswers = SurveyAnswer.findAllBySurvey(survey)
    surveyAnswers.each { a->
      charts.y << a.title
    }*/

    def competencyModel = CompetencyModel.findAllByCode(NECESIDADES_CAPACITACION)
    if (!competencyModel) {
      log.error "Unable to retrive Competency Model: 'NECESIDADES_CAPACITACION'."
      throw new RuntimeException("Unable to retrive Competency Model: 'NECESIDADES_CAPACITACION'.")
    }

    def competencyLevels = CompetencyLevel.findByModel(competencyModel)
    competencyLevels.each { l->
      charts.y << l.name
    }

    def questionCategories = Term.findAllByVocabularyAndParent(vocabularyQuestionCategory, null)
    questionCategories.each { category->
      charts.category.x << "${category.name}"
      charts.category.xLabel << "${category.name}"
      charts.category.zum[category.code] = 0
      charts.category.kount[category.code] = 0
      def competencies = Term.findAllByParent(category)
      competencies.each { competency->
        if (!(competency.code in ['15'])) {
          charts.competency.x << "${competency.name}"
          charts.competency.xLabel << "${competency.name}"
          charts.competency.zum[competency.code] = 0
          charts.competency.kount[competency.code] = 0
        }
      }
    }

    def surveyQuestions = SurveyQuestion.findAllBySurvey(survey)
    surveyQuestions.each { q->
      if (!(q.code in ['46', '77'])) {
        charts.indicator.x << "I${q.weight}"
        charts.indicator.xLabel << "${q.title}"
        charts.indicator.zum[q.code] = 0
        charts.indicator.kount[q.code] = 0
      }
    }

    def viewSurveyResultsByMunicipality
    if (params.params.municipality) {
      viewSurveyResultsByMunicipality = ViewSurveyResultsByMunicipality.findAllByMunicipalityId(params.params.municipality)
    } else {
      viewSurveyResultsByMunicipality = ViewSurveyResultsByMunicipality.list()
    }
    viewSurveyResultsByMunicipality.each { r->
      charts.category.zum[r.questionCategoryCode] += r.level
      charts.category.kount[r.questionCategoryCode]++
      charts.competency.zum[r.questionCompetencyCode] += r.level
      charts.competency.kount[r.questionCompetencyCode]++
      charts.indicator.zum[r.questionIndicatorCode] += r.level
      charts.indicator.kount[r.questionIndicatorCode]++
    }

/*    def surveysAssigned = SurveyAssigned.findAllBySurvey(survey)
    surveysAssigned.each { s->
      def schoolMunicipality
      if (params?.params.municipality) {
        def employmentRelationship = PartyRelationship.findByFromPartyRole(s.interviewee)
        if (!employmentRelationship) {
          log.error "Unable to retrive Employment Relationship for Interviewee: ${s.interviewee.party}."
          throw new RuntimeException("Unable to retrive Employment Relationship for Interviewee: ${s.interviewee.party}.")
        }
        schoolMunicipality = employmentRelationship.toPartyRole.party.partyPostalAddresses[0].postalAddress.geographicBoundary
      }
      
      if (!params || params?.params.municipality.toLong() == schoolMunicipality.id) {
        s.responses.each { r->
          charts.category.zum[r.question.category.parent.code] += r.answer.value
          charts.category.kount[r.question.category.parent.code]++
          charts.competency.zum[r.question.category.code] += r.answer.value
          charts.competency.kount[r.question.category.code]++
          charts.indicator.zum[r.question.code] += r.answer.value
          charts.indicator.kount[r.question.code]++
        }
      }
    }
*/
    charts.category.zum.each { key, value ->
      def avgValue = charts.category.kount[key] > 0 ? ( value / charts.category.kount[key] ) : 0
      charts.category.data1 << avgValue
      charts.category.data2 << avgValue * 0.75
    }

    charts.competency.zum.each { key, value ->
      def avgValue = charts.competency.kount[key] > 0 ? ( value / charts.competency.kount[key] ) : 0
      charts.competency.data1 << avgValue
      charts.competency.data2 << avgValue * 0.75
    }

    charts.indicator.zum.each { key, value->
      def avgValue = charts.indicator.kount[key] > 0 ? ( value / charts.indicator.kount[key] ) : 0
      charts.indicator.data1 << avgValue
      charts.indicator.data2 << avgValue * 0.75
    }

    def chart1 = [x: charts.category.x,   data1: charts.category.data1,   data2: charts.category.data2,   xLabel: charts.category.xLabel]
    def chart2 = [x: charts.competency.x, data1: charts.competency.data1, data2: charts.competency.data2, xLabel: charts.competency.xLabel]
    def chart3 = [x: charts.indicator.x,  data1: charts.indicator.data1,  data2: charts.indicator.data2,  xLabel: charts.indicator.xLabel]

    def labels = [
/*      data1: 'Self evaluation',
      data2: 'Third-party evaluation'*/
      data1: 'Auto-evaluation',
      data2: 'Estimación Real'
    ]

    return [y: charts.y, labels: labels, categoryChart: chart1, competencyChart: chart2, indicatorChart: chart3]

  }

  def chartCompetencySurveyDetailResults(params) {

    def survey = Survey.findByCode(NECESIDADES_CAPACITACION)
    if (!survey) {
      log.error "Unable to retrive Survey: 'NECESIDADES_CAPACITACION'."
      throw new RuntimeException("Unable to retrive Survey: 'NECESIDADES_CAPACITACION'.")
    }

    def vocabularyQuestionCategory = Vocabulary.findByCode(SURVEY_QUESTION_CATEGORY)
    if (!vocabularyQuestionCategory) {
      log.error "Unable to retrive Survey Question Category Vocabulary: 'SURVEY_QUESTION_CATEGORY'."
      throw new RuntimeException("Unable to retrive Survey Question Category Vocabulary: 'SURVEY_QUESTION_CATEGORY'.")
    }

    def charts = [
      y: [],
      category:   [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
      competency: [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
      indicator:  [ x: [], data1: [], data2: [], xLabel: [], zum: [:], kount: [:] ],
    ]

/*    def surveyAnswers = SurveyAnswer.findAllBySurvey(survey)
    surveyAnswers.each { a->
      charts.y << a.title
    }*/

    def competencyModel = CompetencyModel.findByCode(NECESIDADES_CAPACITACION)
    if (!competencyModel) {
      log.error "Unable to retrive Competency Model: 'NECESIDADES_CAPACITACION'."
      throw new RuntimeException("Unable to retrive Competency Model: 'NECESIDADES_CAPACITACION'.")
    }

    def competencyLevels = CompetencyLevel.findAllByModel(competencyModel)
    competencyLevels.each { l->
      charts.y << l.name
    }

    def questionCategories = Term.findAllByVocabularyAndParent(vocabularyQuestionCategory, null)
    questionCategories.each { category->
      charts.category.x << "${category.name}"
      charts.category.xLabel << "${category.name}"
      charts.category.zum[category.code] = 0
      charts.category.kount[category.code] = 0
      def competencies = Term.findAllByParent(category)
      competencies.each { competency->
        charts.competency.x << "C${competency.weight}"
        charts.competency.xLabel << "${competency.name}"
        charts.competency.zum[competency.code] = 0
        charts.competency.kount[competency.code] = 0
      }
    }

    def surveyQuestions = SurveyQuestion.findAllBySurvey(survey)
    surveyQuestions.each { q->
      charts.indicator.x << "I${q.weight}"
      charts.indicator.xLabel << "${q.title}"
      charts.indicator.zum[q.code] = 0
    }

    def employee = Employee.findByCode(params.employeeCode)
    if (!employee) {
      log.error "Unable to retrive Employee: ${params.employeeCode}."
      throw new RuntimeException("Unable to retrive Employee: ${params.employeeCode}.")
    }

    def surveysAssigned = SurveyAssigned.findAllBySurveyAndInterviewee(survey, employee)
    surveysAssigned.each { s->
      s.responses.each { r->
        charts.category.zum[r.question.category.parent.code] += r.answer.value
        charts.category.kount[r.question.category.parent.code]++
        charts.competency.zum[r.question.category.code] += r.answer.value
        charts.competency.kount[r.question.category.code]++
        charts.indicator.zum[r.question.code] += r.answer.value
      }
    }

    charts.category.zum.each { key, value ->
      def avgValue = charts.category.kount[key] > 0 ? ( value / charts.category.kount[key] ) : 0
      charts.category.data1 << avgValue
      charts.category.data2 << avgValue * 0.75
    }

    charts.competency.zum.each { key, value ->
      def avgValue = charts.competency.kount[key] > 0 ? ( value / charts.competency.kount[key] ) : 0
      charts.competency.data1 << avgValue
      charts.competency.data2 << avgValue * 0.75
    }

    charts.indicator.zum.each { key, value->
      def avgValue = surveysAssigned.size() > 0 ? ( value / surveysAssigned.size() ) : 0
      charts.indicator.data1 << avgValue
      charts.indicator.data2 << avgValue * 0.75
    }

    def chart1 = [x: charts.category.x,   data1: charts.category.data1,   data2: charts.category.data2,   xLabel: charts.category.xLabel]
    def chart2 = [x: charts.competency.x, data1: charts.competency.data1, data2: charts.competency.data2, xLabel: charts.competency.xLabel]
    def chart3 = [x: charts.indicator.x,  data1: charts.indicator.data1,  data2: charts.indicator.data2,  xLabel: charts.indicator.xLabel]

    def labels = [
      data1: 'Auto-evaluación',
      data2: 'Evaluación de tercero'
    ]

    return [y: charts.y, labels: labels, categoryChart: chart1, competencyChart: chart2, indicatorChart: chart3]

  }

}