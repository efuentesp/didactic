package la.didactic.hr.competency

import la.didactic.survey.SurveyService

import grails.converters.JSON

class JsonCompetencySummaryController {
  
  def surveyService

  def index() {

    def jsonCategory = []
    def jsonCompetency = []
    def jsonIndicator = []

    def dataChart = surveyService.chartCompetencySurveyResults()

    dataChart.categoryChart.x.eachWithIndex { e, i ->
      def element = [ x: dataChart.categoryChart.x[i], data1: dataChart.categoryChart.data1[i], data2: dataChart.categoryChart.data2[i], xLabel: dataChart.categoryChart.xLabel[i] ]
      jsonCategory << element
    }

    dataChart.competencyChart.x.eachWithIndex { e, i ->
      def element = [ x: dataChart.competencyChart.x[i], data1: dataChart.competencyChart.data1[i], data2: dataChart.competencyChart.data2[i], xLabel: dataChart.competencyChart.xLabel[i] ]
      jsonCompetency << element
    }

    dataChart.indicatorChart.x.eachWithIndex { e, i ->
      def element = [ x: dataChart.indicatorChart.x[i], data1: dataChart.indicatorChart.data1[i], data2: dataChart.indicatorChart.data2[i], xLabel: dataChart.indicatorChart.xLabel[i] ]
      jsonIndicator << element
    }

    def json = [ y: dataChart.y, labels: dataChart.labels, category: jsonCategory, competency: jsonCompetency, indicator: jsonIndicator ]

println json as JSON

    render json as JSON
    
  }

}