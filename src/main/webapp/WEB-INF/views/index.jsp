<%--
  Created by IntelliJ IDEA.
  User: mxia
  Date: 10/8/13
  Time: 10:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>LolRiver - League of Legends Video Stream Portal</title>
    <link href="/static/css/index.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" type="text/JavaScript" src="/static//script/util.js"></script>
    <meta name="keywords" content="League of Legends, LoL, video game, stream, twitch, streaming, LoL game, elo">
    <meta name="description"
          content="League of Legends Video Stream Portal - Browse and watch LoL streams based on champion played, streamer, elo, and more">
    <meta name="author" content="Mark Donkey">
</head>

<body>
<a href="/"><img class="logo" src="/static/images/logo.png"></a>

<table class="search">
    <form method="POST" action="/searchClips" autocomplete="on">
        <input type="hidden" name="prevStreamerName" value=${fn:escapeXml(param.streamerName)}>
        <input type="hidden" name="prevChampionPlayedString"
               value=${fn:escapeXml(param.championPlayedString)}>
        <input type="hidden" name="prevChampionFacedString"
               value=${fn:escapeXml(param.championFacedString)}>
        <input type="hidden" name="prevMinLength" value=${fn:escapeXml(param.minLength)}>
        <input type="hidden" name="prevMaxLength" value=${fn:escapeXml(param.maxLength)}>
        <td class="randomSkin"><img class="randomSkin" src=${randomSkinFile}></td>
        <td class="search">Streamer<br>
            <input type="text" name="streamerName" value=${fn:escapeXml(param.streamerName)}>

            <br>Champion Played<br>
            <input type="text" name="championPlayedString"
                   value=${fn:escapeXml(param.championPlayedString)}>

            <br>Champion Faced<br>
            <input type="text" name="championFacedString" value=${fn:escapeXml(param.championFacedString)}>

            <br>Min Length<br>
            <input class="clipLength" type="number" name="minLength" value=${fn:escapeXml(param.minLength)}>
            <br>Max Length<br>
            <input class="clipLength" type="number" name="maxLength" value=${fn:escapeXml(param.maxLength)}>
        </td>
        <td class="searchRole checkBox">
            <c:set var="topChecked" value=""/>
            <c:set var="midChecked" value=""/>
            <c:set var="jungChecked" value=""/>
            <c:set var="adcChecked" value=""/>
            <c:set var="suppChecked" value=""/>

            <c:forEach items="${roleCriteria}" var="role">
                <c:choose>
                    <c:when test="${role eq '\"TOP\"'}">
                        <c:set var="topChecked" value="checked"/>
                    </c:when>
                    <c:when test="${role eq '\"MID\"'}">
                        <c:set var="midChecked" value="checked"/>
                    </c:when>
                    <c:when test="${role eq '\"JUNG\"'}">
                        <c:set var="jungChecked" value="checked"/>
                    </c:when>
                    <c:when test="${role eq '\"ADC\"'}">
                        <c:set var="adcChecked" value="checked"/>
                    </c:when>
                    <c:when test="${role eq '\"SUPP\"'}">
                        <c:set var="suppChecked" value="checked"/>
                    </c:when>
                </c:choose>
            </c:forEach>

            <input type="checkbox" name="roleCriteria" value="TOP" ${topChecked}>Top<br>
            <input type="checkbox" name="roleCriteria" value="MID" ${midChecked}>Mid<br>
            <input type="checkbox" name="roleCriteria" value="JUNG" ${jungChecked}>Jung<br>
            <input type="checkbox" name="roleCriteria" value="ADC" ${adcChecked}>ADC<br>
            <input type="checkbox" name="roleCriteria" value="SUPP" ${suppChecked}>Supp<br></td>
        <td class="searchTier checkBox">
            <c:set var="challengerChecked" value=""/>
            <c:set var="diamondChecked" value=""/>
            <c:set var="platinumChecked" value=""/>
            <c:set var="goldChecked" value=""/>
            <c:set var="silverChecked" value=""/>
            <c:set var="bronzeChecked" value=""/>

            <c:forEach items="${eloCriteria}" var="elo">
                <c:choose>
                    <c:when test="${elo eq 'CHALLENGER'}">
                        <c:set var="challengerChecked" value="checked"/>
                    </c:when>
                    <c:when test="${elo eq 'DIAMOND'}">
                        <c:set var="diamondChecked" value="checked"/>
                    </c:when>
                    <c:when test="${elo eq 'PLATINUM'}">
                        <c:set var="platinumChecked" value="checked"/>
                    </c:when>
                    <c:when test="${elo eq 'GOLD'}">
                        <c:set var="goldChecked" value="checked"/>
                    </c:when>
                    <c:when test="${elo eq 'SILVER'}">
                        <c:set var="silverChecked" value="checked"/>
                    </c:when>
                    <c:when test="${elo eq 'BRONZE'}">
                        <c:set var="bronzeChecked" value="checked"/>
                    </c:when>
                </c:choose>
            </c:forEach>
            <input type="checkbox" name="eloCriteria" value="CHALLENGER" ${challengerChecked}>Challenger<br>
            <input type="checkbox" name="eloCriteria" value="DIAMOND" ${diamondChecked}>Diamond<br>
            <input type="checkbox" name="eloCriteria" value="PLATINUM" ${platinumChecked}>Platinum<br>
            <input type="checkbox" name="eloCriteria" value="GOLD" ${goldChecked}>Gold<br>
            <input type="checkbox" name="eloCriteria" value="SILVER" ${silverChecked}>Silver<br>
            <input type="checkbox" name="eloCriteria" value="BRONZE" ${bronzeChecked}>Bronze<br></td>

        <td class="search"><input class="searchButton" type="image"
                                  src="/static/images/buttons/button_search.png"
                                  alt="Search clips"><br><br>
    </form>
    <form method="POST" action="/searchClips" autocomplete="on">
        <input type="hidden" name="prevStreamerName" value=${fn:escapeXml(param.streamerName)}>
        <input type="hidden" name="prevChampionPlayedString"
               value=${fn:escapeXml(param.championPlayedString)}>
        <input type="hidden" name="prevChampionFacedString"
               value=${fn:escapeXml(param.championFacedString)}>
        <input type="hidden" name="prevMinLength" value=${fn:escapeXml(param.minLength)}>
        <input type="hidden" name="prevMaxLength" value=${fn:escapeXml(param.maxLength)}>

        <input type="hidden" name="streamerName" value="${sessionScope.param.prevStreamerName}">
        <input type="hidden"
               name="prevChampionPlayedString" value="${sessionScope.param.prevChampionPlayedString}">
        <input type="hidden" name="prevChampionFacedString"
               value="${sessionScope.param.prevChampionFacedString}">
        <input type="hidden" name="prevMinLength" value="${sessionScope.param.prevMinLength}">
        <input type="hidden" name="prevMaxLength" value="${sessionScope.param.prevMaxLength}">
        <input class="backButton" type="image" src="/static/images/buttons/button_back.png"
               alt="Back to previous search"></form>
    </td>
</table>
<br>
<table class="clips">
    <tr>
        <th>Date</th>
        <th>Streamer</th>
        <th>Role</th>
        <th colspan="2">Matchup</th>
        <th>Tier</th>
        <th>Length</th>
        <th>Views</th>
        <th>Rating</th>
        <td class="paginationHeader">
            Page<br>
            <c:set var="prevPage1" value="${param.p - 3}"></c:set>
            <c:set var="prevPage2" value="${param.p - 2}"></c:set>
            <c:set var="prevPage3" value="${param.p - 1}"></c:set>
            <c:set var="nextPage1" value="${param.p + 1}"></c:set>
            <c:set var="nextPage2" value="${param.p + 2}"></c:set>
            <c:set var="nextPage3" value="${param.p + 3}"></c:set>
            <c:if test="${param.p gt 1}">
                <a href="?p=${param.p - 1}">&lt;</a>
            </c:if>

            <c:if test="${prevPage1 gt 1}">
                <a href="?p=1">1</a>
            </c:if>

            <c:if test="${prevPage1 gt 2}">
                ...
            </c:if>

            <c:if test="${prevPage1 ge 1}">
                <a class="page" href="?p=${prevPage1}">${prevPage1}</a>
            </c:if>
            <c:if test="${prevPage2 ge 1}">
                <a class="page" href="?p=${prevPage2}">${prevPage2}</a>
            </c:if>
            <c:if test="${prevPage3 ge 1}">
                <a class="page" href="?p=${prevPage3}">${prevPage3}</a>
            </c:if>

            <a class="page" id="curPage" href="?p=${param.p}" class="curentPage">${param.p}</a>

            <c:if test="${nextPage1 le numClipPages}">
                <a class="page" href="?p=${nextPage1}">${nextPage1}</a>
            </c:if>
            <c:if test="${nextPage2 le numClipPages}">
                <a class="page" href="?p=${nextPage2}">${nextPage2}</a>
            </c:if>
            <c:if test="${nextPage3 le numClipPages}">
                <a class="page" href="?p=${nextPage3}">${nextPage3}</a>
            </c:if>

            <c:if test="${nextPage3 lt numClipPages}">
                ...
                <a class="page" href="?p=${numClipPages}">${numClipPages}</a>
            </c:if>

            <c:if test="${param.p lt numClipPages}">
                <a href="?p=${param.p + 1}">&gt;</a>
            </c:if></td>
    </tr>

    <c:forEach items="${clips}" var="clip">
        <tr>
            <td><fmt:formatDate type="date" value="${clip.startTime}"/></td>
            <td>${clip.streamerName}</td>
            <td>${fn:toLowerCase(clip.rolePlayed.name)}</td>
            <td colspan="2">
                <img class="avatarSelf"
                     src="static/images/avatars/avatar_${fn:toLowerCase(clip.championPlayed.name)}.png">

                <c:set var="lanePartnerChampionName" value="${fn:toLowerCase(clip.lanePartnerChampion.name)}"/>
                <c:if test="${empty lanePartnerChampionName}">
                    <c:set var="lanePartnerChampionName" value="blank"></c:set>
                </c:if>
                <img class="avatar" src="static/images/avatars/avatar_${lanePartnerChampionName}.png">

                VS

                <img class="avatarEnemy"
                     src="static/images/avatars/avatar_${fn:toLowerCase(clip.championFaced.name)}.png">

                <c:set var="enemyLanePartnerChampionName"
                       value="${fn:toLowerCase(clip.enemyLanePartnerChampion.name)}"/>
                <c:if test="${empty enemyLanePartnerChampionName}">
                    <c:set var="enemyLanePartnerChampionName" value="blank"></c:set>
                </c:if>
                <img class="avatar"
                     src="static/images/avatars/avatar_${enemyLanePartnerChampionName}.png">
            </td>
            <td><img class="badgeChallenger"
                     src="static/images/badge/badge3_challenger.png"></td>
            <td><fmt:formatNumber value="${clip.length / 60}" maxFractionDigits="0"/> min</td>
            <td><fmt:formatNumber value="${clip.views}"/></td>
            <td>9.5</td>
            <td><a href="${clip.url}"><img class="watchButton"
                                           src="static/images/buttons/button_watchVideo.png"/></a></td>
        </tr>
    </c:forEach>

</table>
</form>
</body>

</html>