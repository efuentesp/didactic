var dataCategories = [];
var dataCompetencies = [];
var dataIndicators = [];
var url = '/didactic/jsonCompetencySummary/index/';

function getStreamData() {
  url = url + $('#professorCode').val();
console.log(url);

  $.getJSON(url, function (json) {
 
console.log(json.indicator);
    dataIndicators = json.indicator;
    $("#dev-indicators-chart").dxChart({
      dataSource: dataIndicators,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x',
        tagField: 'xLabel'
      },
      tooltip: {
        enabled: true,
        customizeText: function () { return this.point.tag; }
      },
      // title: 'Competency Indicators Chart',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        {
          valueField: 'data1',
          name: json.labels.data1,
          label: {
            visible: false,
            verticalOffset: 0,
            format: 'fixedPoint',
            precision: 2
          }
        },
        {
          valueField: 'data2',
          type: 'stackedbar',
          name: json.labels.data2,
          label: {
            visible: false,
            verticalOffset: 50,
            format: 'fixedPoint',
            precision: 2
          }
        }
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 4
      },
      animation: {
        enabled: false
      }
    });
 
    dataCompetencies = json.competency;
    $("#dev-competency-chart").dxChart({
      dataSource: dataCompetencies,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x',
        label: {
          visible: true
        }
      },
      tooltip: { enabled: true },
      // title: 'Competency Chart',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        {valueField: 'data1', name: json.labels.data1, label: {format: 'fixedPoint', precision: 2, font: {size: 10}}},
        {valueField: 'data2', type: 'stackedbar', name: json.labels.data2, label: {verticalOffset: 0, format: 'fixedPoint', precision: 2, font: {size: 10}}}
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 3.5
      },
      animation: {
        enabled: false
      }
    });

    dataCategories = json.category;
    $("#dev-category-chart").dxChart({
      dataSource: dataCategories,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x',
        label: {
          visible: true
        }
      },
      tooltip: {
        enabled: true
      },
      // title: 'Category Chart',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        { valueField: 'data1', name: json.labels.data1, label: {verticalOffset: 0, format: 'fixedPoint', precision: 2} },
        { valueField: 'data2', type: 'stackedbar', name: json.labels.data2, label: {verticalOffset: 0, format: 'fixedPoint', precision: 2} }
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 3.4
      },
      animation: {
        enabled: false
      }
    });


  });
}


$(document).ready(function () {

  getStreamData();

});

