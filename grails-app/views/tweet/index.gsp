<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'tweet.label', default: 'Tweet')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-tweet" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            </ul>
        </div>
        <div>
            <h1>Averages</h1>
                <g:each in="${average}" var="avg" >
                <p>${avg.key}: <g:formatNumber number="${avg.value}" format="###,###" /> </p>
                </g:each>
            <h1>Stats</h1>
            <g:each in="${stats}" var="stat" >
                <p>${stat.key}: <g:formatNumber number="${stat.value}" type="percent"/></p>
            </g:each>
        </div>
        <div id="list-tweet" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${tweetList}" />

            <div class="pagination">
                <g:paginate total="${tweetCount ?: 0}" />
            </div>
        </div>
    </body>
</html>