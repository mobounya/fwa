<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="fr.fortytwo.cinema.models.User" %>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
     <%= "<style>" %>
     <%= "table, th, td {" %>
     <%= "     border: 1px solid black;" %>
     <%= "}" %>
     <%= "</style>" %>
    <title>New Profile</title>
</head>
<body>
    <%!
        User user = null;
        Map<String, String> images = null;
    %>

    <%
        User user = (User) session.getAttribute("user");
        Map<String, String> images = (Map<String, String>) session.getAttribute("images");
    %>

    <%= "<h5> " + user.getFirstName() + " </h5>" %>
    <%= "<h5> " + user.getEmail() + " </h5>" %>

    <%= "<form action='/images' method='POST' enctype='multipart/form-data'>" %>
    <%= "<input type='file' id='file' name='myFile'>" %>
    <%= "<input type='submit'>" %>
    <%= "</form>" %>

    <%= "<table id='auths'>\n" +
        "  <tr>\n" +
        "    <th>Date</th>" +
        "    <th>Time</th>" +
        "    <th>IP</th>" +
        "  </tr>"
    %>

    <%
        String[] data = {"September 20, 2023", "05:00", "localhost"};
        out.println("  <tr>");
        for (String str : data)
            out.println("<td>" + str + "</td>");
        out.println("  </tr>");
        out.println("</table><br/><br/>");
    %>

    <%=
        "<table id='files'>" +
        "  <tr>" +
        "    <th>File name</th>" +
        "    <th>Size</th>" +
        "    <th>MIME</th>" +
        "  </tr>"
    %>

    <%
        if (images != null) {
            Set<Map.Entry<String, String>> set =  images.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                String value = entry.getValue();
                out.println("  <tr>");
                out.println("    <th> <a href='" + "/images/" + key + "'>" + key.substring(10) + "</a></th>");
                out.println("    <th>" + value + "</th>");
                out.println("    <th>image/jpeg</th>");
                out.println("  </tr>");
            }
        }

        out.println("</table>");
    %>
</body>
</html>
