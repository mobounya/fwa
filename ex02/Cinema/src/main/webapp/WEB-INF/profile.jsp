<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="fr.fortytwo.cinema.models.User" %>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
     <style>
         table, th, td {
            border: 1px solid black;
         }

         /* Hide the actual file input */
         #file {
            display: none;
         }

        /* Style the custom upload button */
        .custom-upload-button {
            background-color: #0074d9; /* Button background color */
            color: white; /* Text color */
            padding: 10px 20px; /* Padding for the button */
            border: none; /* Remove button border */
            cursor: pointer; /* Add a pointer cursor on hover */
        }
     </style>
    <title>New Profile</title>
</head>
<body>
    <%!
        List<String> auths;
        User user = null;
        Map<String, String> images = null;
    %>

    <%
        User user = (User) session.getAttribute("user");
        auths = (List<String>) session.getAttribute("auths");
    %>

    <div style='overflow: auto; max-width: 780px; max-height: 100%; margin-left: 783px; margin-top: 260px;'>

    <div id='user'>

    <c:set var="avatar" value="${sessionScope.avatar}" />

    <div id='image' style='display: inline;'>
        <c:if test="${avatar == null}">
            <c:set var="avatar" value="/images/not_available.png" />
        </c:if>
        <c:out value="<img style='max-height: auto; max-width: 500px;' src='${avatar}'  alt='avatar'>" escapeXml="false" />
    </div>

    <div id='user_info' style='float: right;'>
    <%= "<h3> " + user.getFirstName() + " " + user.getLastName() + " </h3>\n" %>
    <%= "<h3> " + user.getEmail() + " </h3>\n" %>

    <table id='auths'>
    <tr>
        <th>Date</th>
        <th>Time</th>
        <th>IP</th>
    </tr>

    <c:set var="auths" value="${sessionScope.auths}" />

    <c:forEach var="data" items="${auths}">
        <c:set var="array" value="${fn:split(data, '-')}" />
        <tr>
            <c:out value="<th> ${array[0]} </th>" escapeXml="false" />
            <c:out value="<th> ${array[1]} </th>" escapeXml="false" />
            <c:out value="<th> ${array[2]} </th>" escapeXml="false" />
        </tr>
    </c:forEach>

    </table>

    </div></div>

    <div id='image_table'>

    <form action='/images' method='POST' enctype='multipart/form-data' style='margin-top: 25px; margin-bottom: 25px;'>
        <label for='file' class='custom-upload-button'>Choose a File</label>
        <input type='file' id='file' name='myFile' accept='image/*'>
        <input type='submit' value='Upload'>
    </form>

    <table id='files'>
    <tr>
        <th>File name</th>
        <th>Size</th>
        <th>MIME</th>
    </tr>

    <c:set var="images" value="${sessionScope.images}" />
    <c:forEach var="image" items="${images}">
        <c:set var="array" value="${fn:split(image, '-')}" />
        <tr>
            <c:out value="<th> <a href='/images/${array[0]}' target='_blank'> ${fn:substring(array[0], 10, array[0].length())} </a> </th>" escapeXml="false" />
            <c:out value="<th> ${array[1]} </th>" escapeXml="false" />
            <c:out value="<th> ${array[2]} </th>" escapeXml="false" />
        </tr>
    </c:forEach>

    <%= "</table></div></div></div>\n" %>
</body>
</html>
