<!DOCTYPE html>
<html lang="en">

  <head>

    <title>Users</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  </head>

  <body role="document">

    <div class="container theme-showcase" role="main">
      <div class="jumbotron">
        <h1>Users</h1>

        <#list users as user>
          <div>
            ${user.name} ~ <b> ${user.time.getTime()} </b> 
          </div>
        </#list>

      </div>

      <form role="form" action="save" method="post">
        <div class="form-group">
          <label for="name">User name</label>
          <input type="text" class="form-control" name="name" id="name">
        </div>
        <div class="form-group">
          <label for="gmt">GMT</label>
          <input type="text" class="form-control" name="gmt" id="gmt">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>

    </div>

  </body>

</html>
