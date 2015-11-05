<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Avenida Hazelcast console</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

    <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  </head>

  <body role="document">

    <div class="container theme-showcase" role="main">
      <h1>Members</h1>
      <#list members as member>
        <div>
          ${member.address}
        </div>
      </#list>
      <div class="table-responsive">
        <table class="table">
          <#list caches as cache>
            <tr>
              <td>
                ${cache.name}
              </td>
              <td>
                <#if !cache.readOnly>
                  <a href="clean/${cache.name}">clean</a>
                </#if>
              </td>
            </tr>
          </#list>
        </table>
      </div>
    </div>

  </body>

</html>
