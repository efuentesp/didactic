<%@page import="grails.plugin.hr.competency.CompetencyModel"%>
<%@page import="grails.plugin.hr.competency.CompetencyLevel"%>
<%@page import="grails.plugin.hr.competency.Competency"%>
<%@page import="grails.plugin.hr.competency.CompetencyIndicator"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="print"/>
    <title>Print Results</title>
    <style type="text/css" media="screen">
      body {
        padding-top: 0px;
        padding-bottom: 0px;
      }

    </style>
  </head>

  <body>

    <!-- Page Heading -->
    <div class="row-fluid">

      <div class="col-xs-12">
        <h1 class="page-header">
          Resultados Encuesta <small>${professor?.party?.firstName} (${professor?.code})</small>
        </h1>
      </div>

    </div>
    <!-- /.row -->

    <g:hiddenField id="professorCode" name="professorCode" value="${professor?.code}" />

    <div class="row-fluid">

      <div class="col-xs-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Indicadores por Competencia</h3>
          </div>
          <div class="panel-body">
            <div id="dev-indicators-chart" style="width:100%; height:300px; margin: 0 auto"></div>
          </div>
        </div>
        <!-- <div id="dev-indicators-chart" style="max-width:800px; height:300px; margin: 0 auto"></div> -->
      </div>

    </div>
    <!-- /.row -->

    <div class="row-fluid">

      <div class="col-xs-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Competencias</h3>
          </div>
          <div class="panel-body">
            <div id="dev-competency-chart" style="width:100%; height:300px; margin: 0 auto"></div>
          </div>
        </div>
        <!-- <div id="dev-competency-chart" style="max-width:800px; height:300px; margin: 0 auto"></div> -->
      </div>

    </div>
    <!-- /.row -->

    <div class="row-fluid">

      <div class="col-xs-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Categorias</h3>
          </div>
          <div class="panel-body">
            <div id="dev-category-chart" style="width:100%; height:300px; margin: 0 auto"></div>
          </div>
        </div>
        <!-- <div id="dev-category-chart" style="max-width:800px; height:300px; margin: 0 auto"></div> -->
      </div>

    </div>
    <!-- /.row -->

    <div class="row-fluid">

      <div class="col-xs-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-info-circle fa-fw"></i> Niveles de Desempeño</h3>
          </div>
          <div class="panel-body">
            <g:set var="competencyModel" value="${CompetencyModel.findByCode('NECESIDADES_CAPACITACION')}" />
            <g:set var="competencyLevels" value="${CompetencyLevel.findAllByModel(competencyModel)}" />
            <table class="table">
              <thead>
                <tr>
                  <th class="col-xs-4">Nombre</th>
                  <th class="col-xs-8">Descripción</th>
                </tr>
              </thead>
              <tbody>
                <g:each in="${competencyLevels}" var="level">
                  <tr>
                    <td><strong>${level.value}</strong>.- ${level.name}</td>
                    <td>${level.description}</td>
                  </tr>
                </g:each>
              </tbody>
            </table>
          </div>
        </div>
      </div>

    </div>
    <!-- /.row -->

    <div class="row-fluid">

      <div class="col-xs-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-info-circle fa-fw"></i> Descripción de Competencias</h3>
          </div>
          <div class="panel-body">
            <g:set var="competencies" value="${Competency.findAllByModel(competencyModel)}" />
            <table class="table table-condensed">
              <thead>
                <tr>
                  <th class="col-xs-4">Competencia</th>
                  <th class="col-xs-8">Indicadores</th>
                </tr>
              </thead>
              <tbody>
                <g:each in="${competencies}" var="competency">
                  <tr>
                    <td><strong>(C${competency.code})</strong> ${competency.name}</td>
                    <td>
                      <g:set var="indicators" value="${CompetencyIndicator.findAllByCompetency(competency)}" />
                      <ul>
                        <g:each in="${indicators}" var="indicator">
                          <li><strong>(I${indicator.code})</strong> ${indicator.name}</li>
                        </g:each>
                      </ul>
                    </td>
                  </tr>
                </g:each>
              </tbody>
            </table>
          </div>
        </div>
      </div>

    </div>
    <!-- /.row -->

    <asset:javascript src="vendor/globalize/globalize.js"/>
    <asset:javascript src="vendor/devextreme/dx.chartjs.js"/>
    <asset:javascript src="dashboard/admin/professorChart.js"/>

  </body>