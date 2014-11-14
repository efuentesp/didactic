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
    <div class="row">

      <div class="col-lg-12">
        <h1 class="page-header">
          Survey Results <small>${professor?.party?.firstName} (${professor?.code})</small>
        </h1>
      </div>

    </div>
    <!-- /.row -->

    <g:hiddenField id="professorCode" name="professorCode" value="${professor?.code}" />

    <div class="row">

      <div class="col-lg-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Competency Indicators Chart</h3>
          </div>
          <div class="panel-body">
            <div id="dev-indicators-chart" style="height:300px; margin: 0 auto"></div>
          </div>
        </div>
      </div>

    </div>
    <!-- /.row -->

    <div class="row">

      <div class="col-lg-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Competency Chart</h3>
          </div>
          <div class="panel-body">
            <div id="dev-competency-chart" style="height:300px; margin: 0 auto"></div>
          </div>
        </div>
      </div>

    </div>
    <!-- /.row -->

    <div class="row">

      <div class="col-lg-6">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Category Chart</h3>
          </div>
          <div class="panel-body">
            <div id="dev-category-chart" style="height:240px; margin: 0 auto"></div>
          </div>
        </div>
      </div>

    </div>
    <!-- /.row -->

    <asset:javascript src="vendor/globalize/globalize.js"/>
    <asset:javascript src="vendor/devextreme/dx.chartjs.js"/>
    <asset:javascript src="dashboard/admin/professorChart.js"/>

  </body>