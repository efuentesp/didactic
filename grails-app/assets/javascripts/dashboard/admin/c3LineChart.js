var data;
var url = '/didactic/jsonCompetencySummary';

//d3.json(url, data);
$.getJSON(url, function( data ) {
  //console.log(url);
  //console.log(data);

  var chart = c3.generate({
    bindto: '#c3-line-chart',
    data: {
      x : 'x',
      json: data.columns,
      names: data.names,
      types: {
          data1: 'line',
          data2: 'bar'
      },
    },
    axis: {
      x: {
        label: 'Competency',
        type: 'category' // this needed to load string x value
      },
      y: {
        label: 'Level'
      }
    },
  });

});
