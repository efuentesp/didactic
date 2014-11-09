var dataSource = [];
var url = '/didactic/jsonCompetencySummary';

function getStreamData() {
    $.getJSON(url, function (json) {
 
        var viewers = json.stream.viewers;
 
        dataSource.push({
            date: new Date(),
            viewers: viewers
        });
 
        $('#streamChartContainer').dxChart('option', 'dataSource', dataSource);
    });
}


$(document).ready(function () {

  var dataSource = [
    {argument: "1", value1: 120, value2: 125, value3: 112, value4: 100},
    {argument: "2", value1: 150, value2: 120, value3: 135, value4: 115},
    {argument: "3", value1: 170, value2: 165, value3: 168, value4: 158},
    {argument: "4", value1: 157, value2: 140, value3: 120, value4: 110},
    {argument: "5", value1: 180, value2: 170, value3: 160, value4: 175},
    {argument: "6", value1: 211, value2: 190, value3: 200, value4: 220},
  ];

  $("#div-line-chart").dxChart({
    dataSource: dataSource,
    commonSeriesSettings: {
      type: 'bar',
      argumentField: 'argument'      
    },
    tooltip: { enabled: true },
    title: '',
    legend: {
      verticalAlignment: 'bottom',
      horizontalAlignment: 'center'
    },
    series: [
      {valueField: 'value1', name: 'Self evaluation'},
      {valueField: 'value2', type: 'line', name: 'Third-party evaluation'}
    ],
    valueAxis: {
      title: {
          text: 'Level'
      }
    }
  });

});

