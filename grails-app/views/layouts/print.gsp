<!DOCTYPE html>
<%-- <html lang="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).toString().replace('_', '-')}"> --%>
<html lang="${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}">

<head>
  <title><g:layoutTitle default="${meta(name:'app.name')}" /></title>
  
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
  <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">

  <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
  <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  
  <asset:stylesheet src="application.css"/>
  <asset:javascript src="application.js"/>

  <g:layoutHead />

  <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
  <!--[if lt IE 9]>
    <script src="https://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

  <%-- For Javascript see end of body --%>
</head>

<body>
<!--   <g:render template="/_menu/navbar"/>     -->                      

  <g:render template="/layouts/content"/>

  <g:render template="/layouts/footer"/>                            
</body>

</html>