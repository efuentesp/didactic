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
      title: '',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        {valueField: 'data1', name: json.labels.data1},
        {valueField: 'data2', type: 'line', name: json.labels.data2}
      ],
      valueAxis: {
        title: {
          text: 'Level'
        },
        min: 0,
        max: 4
      }
    });
 
    dataCompetencies = json.competency;
    $("#dev-competency-chart").dxChart({
      dataSource: dataCompetencies,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x'
      },
      tooltip: { enabled: true },
      title: '',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        {valueField: 'data1', name: json.labels.data1},
        {valueField: 'data2', type: 'line', name: json.labels.data2}
      ],
      valueAxis: {
        title: {
          text: 'Level'
        },
        min: 0,
        max: 4
      }
    });

    dataCategories = json.category;
    $("#dev-category-chart").dxChart({
      dataSource: dataCategories,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x'
      },
      tooltip: {
        enabled: true
      },
      title: '',
      legend: {
        verticalAlignment: 'bottom',
        horizontalAlignment: 'center'
      },
      series: [
        {valueField: 'data1', name: json.labels.data1},
        {valueField: 'data2', type: 'line', name: json.labels.data2}
      ],
      valueAxis: {
        title: {
          text: 'Level'
        },
        min: 0,
        max: 4
      }
    });


  });
}


$(document).ready(function () {

  getStreamData();

});

