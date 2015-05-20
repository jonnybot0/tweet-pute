<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'url.label', default: 'Url')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-url" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div>
            <h1>Top 7 Domains</h1>
            <table>
                <tr>
                    <th>Domain</th>
                    <th>URL Count</th>
                </tr>
                <g:each in="${topDomains}" >
                    <tr><td>${it.domain}</td><td> ${it.total}</td></tr>
                </g:each>
            </table>
        </div>
        <div id="list-url" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${urlList}" />

            <div class="pagination">
                <g:paginate total="${urlCount ?: 0}" />
            </div>
        </div>
    </body>
</html>