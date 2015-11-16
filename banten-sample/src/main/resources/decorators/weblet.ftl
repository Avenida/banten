<#--
Renders a weblet.
-->
<#macro weblet moduleName webletName>
  ${request.getAttribute("::webletRenderer")}
</#macro>

<#--
Renders a weblet inside a div decorator.

The div has a class named after the weblet:
  'weblet weblet_[modulename]_[webletname]_[instance]'.
-->
<#macro decoratedWeblet moduleName webletName instance = '' >
  <#if !instance?is_hash && instance != '' >
    <#assign className='weblet weblet_' + moduleName + '_' + webletName + '_' +
      instance>
  <#else>
    <#assign className='weblet weblet_' + moduleName + '_' + webletName>
  </#if>
  <div class='${className}'>
    ${request.getAttribute("::weblet-renderer").renderWebletResponse(
      moduleName, webletName, instance, request, response)}
  </div>
</#macro>

