package la.didactic.survey

import grails.plugin.core.taxonomy.Vocabulary
import grails.plugin.core.taxonomy.Term

import grails.plugin.survey.Survey
import grails.plugin.survey.SurveyAnswer
import grails.plugin.survey.SurveyQuestion
import grails.plugin.survey.SurveyAssigned

class SurveyService {

  public static final String NECESIDADES_CAPACITACION = 'NECESIDADES_CAPACITACION'
  public static final String SURVEY_QUESTION_CATEGORY = 'SURVEY_QUESTION_CATEGORY'

  def chartCompetencySurveyResults(params) {

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
      category:   [ x: [], data1: [], data2: [], zum: [:], kount: [:] ],
      competency: [ x: [], data1: [], data2: [], zum: [:], kount: [:] ],
      indicator:  [ x: [], data1: [], data2: [], zum: [:], kount: [:] ],
    ]

    survey.answers.each { a->
      charts.y << a.title
    }

    def questionCategories = Term.findAllByVocabularyAndParent(vocabularyQuestionCategory, null)
    questionCategories.each { category->
      charts.category.x << "${category.name}"
      charts.category.zum[category.code] = 0
      charts.category.kount[category.code] = 0
      def competencies = Term.findAllByParent(category)
      competencies.each { competency->
        charts.competency.x << "${competency.name}"
        charts.competency.zum[competency.code] = 0
        charts.competency.kount[competency.code] = 0
      }
    }

    def surveyQuestions = SurveyQuestion.findAllBySurvey(survey)
    surveyQuestions.each { q->
      charts.indicator.x << "C${q.weight}"
      charts.indicator.zum[q.code] = 0
    }

    def surveysAssigned = SurveyAssigned.findAllBySurvey(survey)
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
      charts.category.data1 << ( charts.category.kount[key] > 0 ? Math.floor( value / charts.category.kount[key] ) : 0 )
    }

    charts.competency.zum.each { key, value ->
      charts.competency.data1 << ( charts.competency.kount[key] > 0 ? Math.floor( value / charts.competency.kount[key] ) : 0 )
    }

    charts.indicator.zum.each { key, value->
      charts.indicator.data1 << ( surveysAssigned.size() > 0 ? Math.floor( value / surveysAssigned.size() ) : 0 )
    }

    def columns = [x: charts.category.x, data1: charts.category.data1, data2: charts.category.data1]

    def names = [
      data1: 'Self evaluation',
      data2: 'Third-party evaluation'
    ]

    return [columns: columns, names: names]

  }

}