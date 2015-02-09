var dataCategories = [];
var dataCompetencies = [];
var dataIndicators = [];
var URL = '/didactic/jsonCompetencySummary';
var url = '';

function getStreamData() {

  //var municipalityId = $('#municipalityId').val();
  //if (municipalityId) {
  //  url = url + '?municipality=' + municipalityId
  //}

  console.log(url);
  $.getJSON(url, function (json) {
 
/*        var viewers = json.stream.viewers;
 
        dataSource.push({
            date: new Date(),
            viewers: viewers
        });*/
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
            visible: true,
            // verticalOffset: 0,
            format: 'fixedPoint',
            precision: 2,
            font: {size: 10}
          }
        },
        {
          valueField: 'data2',
          type: 'stackedbar',
          name: json.labels.data2,
          label: {
            visible: true,
            // verticalOffset: 50,
            format: 'fixedPoint',
            precision: 2,
            font: {size: 10}
          }
        }
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 3.2
      },
      animation: {
        enabled: true
      }
    });
 
    dataCompetencies = json.competency;
    $("#dev-competency-chart").dxChart({
      dataSource: dataCompetencies,
      commonSeriesSettings: {
        type: 'line',
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
        {valueField: 'data2', type: 'line', name: json.labels.data2, label: {verticalOffset: 0, format: 'fixedPoint', precision: 2, font: {size: 10}}}
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 3.5
      },
      animation: {
        enabled: true
      }
    });

    dataCategories = json.category;
    $("#dev-category-chart").dxChart({
      dataSource: dataCategories,
      commonSeriesSettings: {
        type: 'line',
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
        { valueField: 'data2', type: 'line', name: json.labels.data2, label: {verticalOffset: 0, format: 'fixedPoint', precision: 2} }
      ],
      valueAxis: {
        title: {
          text: 'Nivel'
        },
        min: 0,
        max: 3.4
      },
      animation: {
        enabled: true
      }
    });


  });
}

function getStates() {

  $.getJSON('/didactic/geographicBoundary/jsonStates?country=MX', function (json) {

    json.forEach(function(state) {
      //console.log(state);
      $('#state').append("<option value='" + state.id + "'>" + state.name + "</option>")
    });

  });

}

$( "#state" ).change(function() {
  //alert($(this).val());
  $('#subdirection').empty();
  $('#subdirection').append("<option value=''>-- Todos --</option>")
  $.getJSON('/didactic/geographicBoundary/jsonSubdirections?state=' + $(this).val(), function (json) {
    json.forEach(function(subdirection) {
      //console.log(subdirection);
      $('#subdirection').append("<option value='" + subdirection.id + "'>" + subdirection.name + "</option>")
    });

  });
});

$( "#subdirection" ).change(function() {
  //alert($(this).val());
  $('#municipality').empty();
  $('#municipality').append("<option value=''>-- Todos --</option>")
  $.getJSON('/didactic/geographicBoundary/jsonMunicipalities?subdirection=' + $(this).val(), function (json) {
    json.forEach(function(municipality) {
      //console.log(municipality);
      $('#municipality').append("<option value='" + municipality.id + "'>" + municipality.name + "</option>")
    });

  });
});

function getEducationalServices() {

  $.getJSON('/didactic/term/jsonEducationalServices', function (json) {

    json.forEach(function(educationalService) {
      //console.log(educationalService);
      $('#educationalService').append("<option value='" + educationalService.id + "'>" + educationalService.name + "</option>")
    });

  });

}

function getEducationalControls() {

  $.getJSON('/didactic/term/jsonEducationalControls', function (json) {

    json.forEach(function(educationalControl) {
      //console.log(educationalControl);
      $('#educationalControl').append("<option value='" + educationalControl.id + "'>" + educationalControl.name + "</option>")
    });

  });

}

function getEducationalAreas() {

  $.getJSON('/didactic/term/jsonEducationalAreas', function (json) {

    json.forEach(function(educationalArea) {
      //console.log(educationalArea);
      $('#educationalArea').append("<option value='" + educationalArea.id + "'>" + educationalArea.name + "</option>")
    });

  });

}

$(document).ready(function () {

  getStates();
  getEducationalServices();
  getEducationalControls();
  getEducationalAreas();

  //getStreamData();

});

$("#btnSearch").click(function() {
  var state = $( "select#state" ).val();
  var subdirection = $( "select#subdirection" ).val();
  var municipality = $( "select#municipality" ).val();
  var educationalService = $( "select#educationalService" ).val();
  var educationalControl = $( "select#educationalControl" ).val();
  var educationalArea = $( "select#educationalArea" ).val();

  var params = new Object();
  params['state'] = state;
  params['subdirection'] = subdirection;
  params['municipality'] = municipality;
  params['educationalService'] = educationalService;
  params['educationalControl'] = educationalControl;
  params['educationalArea'] = educationalArea;

  var urlParams = ''
  for (var k in params) {
    // use hasOwnProperty to filter out keys from the Object.prototype
    if (params.hasOwnProperty(k) && params[k]>0) {
      //alert('key is: ' + k + ', value is: ' + params[k]);
      if (urlParams == '') {
        urlParams += '?';
      } else {
        urlParams += '&';
      }
      urlParams = urlParams + k + '=' + params[k];
    }
  }
  //console.log(urlParams);
  url = URL + urlParams;
  getStreamData();

});

