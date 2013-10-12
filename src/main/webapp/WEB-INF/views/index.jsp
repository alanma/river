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
    <script language="JavaScript" type="text/JavaScript" src="/static//script/date.js"></script>
    <meta name="keywords" content="League of Legends, LoL, video game, stream, twitch, streaming, LoL game, elo">
    <meta name="description"
          content="League of Legends Video Stream Portal - Browse and watch LoL streams based on champion played, streamer, elo, and more">
    <meta name="author" content="Mark Donkey">
</head>

<body>
<h1>
    <a href="/"><img class="logo" src="/static/images/logo.png"></a>
</h1>

<form method="POST" action="/searchClips" autocomplete="on">
    <table>
        <tr>
            <td class="randomSkin"><img class="randomSkin" src="/static/images/skins/skin_akali1.jpg"</td>
            <td>Streamer<br>
                <input type="text" name="streamerName" value=${fn:escapeXml(param.streamerName)}>
            </td>
            <td class="checkBox"><input type="checkbox" name="roleCriteria" value="TOP">Top<br>
                <input type="checkbox" name="roleCriteria" value="MID" checked>Mid<br>
                <input type="checkbox" name="roleCriteria" value="JUNG">Jung<br>
                <input type="checkbox" name="roleCriteria" value="ADC" checked>ADC<br>
                <input type="checkbox" name="roleCriteria" value="SUPP">Supp<br></td>
            <td>Champion Played<br>
                <input type="text" name="championPlayedString" value=${fn:escapeXml(param.championPlayedString)}></td>
            <td>Champion Faced<br>
                <input type="text" name="championFacedString" value=${fn:escapeXml(param.championFacedString)}></td>
            <td class="checkBox"><input type="checkbox" name="eloCriteria" value="CHALLENGER">Challenger<br>
                <input type="checkbox" name="eloCriteria" value="DIAMOND" checked>Diamond<br>
                <input type="checkbox" name="eloCriteria" value="PLATINUM">Platinum<br>
                <input type="checkbox" name="eloCriteria" value="GOLD" checked>Gold<br>
                <input type="checkbox" name="eloCriteria" value="SILVER">Silver<br>
                <input type="checkbox" name="eloCriteria" value="BRONZE">Bronze<br></td>
            <td>Min Length<br>
                <input class="clipLength" type="number" name="minLength" value=${fn:escapeXml(param.minLength)}><br>
                Max Length<br>
                <input class="clipLength" type="number" name="maxLength" value=${fn:escapeXml(param.maxLength)}><br>
            </td>
            <td><input class="searchButton" type="image" src="/static/images/buttons/button_search.png"
                       alt="Search Clips"></td>
            <c:set var="prevPage1" value="${param.p - 3}"></c:set>
            <c:set var="prevPage2" value="${param.p - 2}"></c:set>
            <c:set var="prevPage3" value="${param.p - 1}"></c:set>
            <c:set var="nextPage1" value="${param.p + 1}"></c:set>
            <c:set var="nextPage2" value="${param.p + 2}"></c:set>
            <c:set var="nextPage3" value="${param.p + 3}"></c:set>
            <td class="paginationHeader" colspan="2">
                Page<br>
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
                </c:if>
        </tr>
        <tr>
            <th>Date</th>
            <th>Streamer</th>
            <th>Role</th>
            <th colspan="2">Matchup</th>
            <th>Tier</th>
            <th>Length</th>
            <th>Views</th>
            <th>Rating</th>
            <th></th>
        </tr>

        <c:forEach items="${clips}" var="clip">
            <tr>
                <td><fmt:formatDate type="date" value="${clip.startTime}"/></td>
                <td>${clip.streamerName}</td>
                <td>${fn:toLowerCase(clip.rolePlayed.name)}</td>
                <td colspan="2">
                    <c:set var="championPlayedName" value="${fn:toLowerCase(clip.championPlayed.name)}"/>
                    <img class="avatarSelf"
                         src="static/images/avatars/avatar_${championPlayedName}.png">

                    <c:set var="lanePartnerChampionName" value="${fn:toLowerCase(clip.lanePartnerChampion.name)}"/>
                    <c:if test="${not empty lanePartnerChampionName}">
                        <img class="avatar"
                             src="static/images/avatars/avatar_${lanePartnerChampionName}.png">
                    </c:if>

                    vs

                    <c:set var="championFacedName" value="${fn:toLowerCase(clip.championFaced.name)}"/>
                    <img class="avatarEnemy" src="static/images/avatars/avatar_${championFacedName}.png">

                    <c:set var="enemyLanePartnerChampionName"
                           value="${fn:toLowerCase(clip.enemyLanePartnerChampion.name)}"/>
                    <c:if test="${not empty enemyLanePartnerChampionName}">
                        <img class="avatar"
                             src="static/images/avatars/avatar_${enemyLanePartnerChampionName}.png">
                    </c:if>
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