package la.didactic.hr.competency

import grails.converters.*

class JsonCompetencySummaryController {

  def index() {

    def columns = [
      x: ['c1','c2','c3','c4','c5','c6','c7','c8','c9','c10','c11','c12','c13','c14','c15','c16','c17','c18','c19','c20','c21','c22','c23','c24','c25','c26','c27','c28','c29','c30','c31','c32','c33','c34','c35'],
      data1: [4,2,2,4,2,2,3,2,4,2,1,2,4,1,4,4,3,3,2,3,1,1,2,3,4,4,1,4,3,2,3,2,4,2,4],
      data2: [4,4,2,4,2,3,2,4,3,2,1,2,4,4,2,1,2,2,1,1,3,2,4,3,4,1,2,1,4,4,4,4,3,3,3]
    ]

    def names = [
      data1: 'Self evaluation',
      data2: 'Third-party evaluation'
    ]

    def data = [columns: columns, names: names]

    render data as JSON
    
  }

}