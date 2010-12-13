<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Default object property list template -->

<#list property.statements as statement>
    <div class="obj-prop-stmt-obj">
       <a href="${statement.object.url}">${statement.object.name}</a> ${statement.object.moniker!} 
    </div> <!-- end obj-prop-stmt-obj -->
</#list>