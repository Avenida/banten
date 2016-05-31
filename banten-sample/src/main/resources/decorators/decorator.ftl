<!DOCTYPE html>
<html lang="en">

<html>

  <head>
    <title><sitemesh:write property='title'/></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="/banten-menu/static/menu.css">
    <sitemesh:write property='head'/>
  </head>

  <body>
    <div>
      <a href="/logout">logout</a>
    </div>
    <div>
      <@weblet name = "menu" module = "Menu-Module" />
    </div>
    <div>
      <sitemesh:write property='body'/>
    </div>

    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

  </body>

</html>
