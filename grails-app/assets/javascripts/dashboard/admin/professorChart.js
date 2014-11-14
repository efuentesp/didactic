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
        valueField: 'data1',
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
        type: 'discrete',
        categories: json.y
      }
    });
 
    dataCompetencies = json.competency;
    $("#dev-competency-chart").dxChart({
      dataSource: dataCompetencies,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x',   
        valueField: 'data1'
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
        type: 'discrete',
        categories: json.y
      }
    });

    dataCategories = json.category;
    $("#dev-category-chart").dxChart({
      dataSource: dataCategories,
      commonSeriesSettings: {
        type: 'bar',
        argumentField: 'x',
        valueField: 'data1'
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
        type: 'discrete',
        categories: json.y
      }
    });


  });
}


$(document).ready(function () {

  getStreamData();

});

