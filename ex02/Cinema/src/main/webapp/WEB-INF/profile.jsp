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
    <%
        User user = (User) session.getAttribute("user");
        Map<String, String> images = (Map<String, String>) session.getAttribute("images");

        out.println("<h5> " + user.getFirstName() + " </h5>");
        out.println("<h5> " + user.getEmail() + " </h5>");
        out.println("<form action='/action_page.php'>");
        out.println("<input type='file' id='myFile' name='filename'>");
        out.println("<input type='submit'>");
        out.println("</form>");

        out.println("<table id='auths'>");
        out.println("  <tr>");
        out.println("    <th>Date</th>");
        out.println("    <th>Time</th>");
        out.println("    <th>IP</th>");
        out.println("  </tr>");
        out.println("  <tr>");
        out.println("<td>Alfreds Futterkiste</td>");
        out.println("<td>Maria Anders</td>");
        out.println("<td>Germany</td>");
        out.println("  </tr>");
        out.println("</table><br/><br/>");

        out.println("<table id='files'>");
        out.println("  <tr>");
        out.println("    <th>File name</th>");
        out.println("    <th>Size</th>");
        out.println("    <th>MIME</th>");
        out.println("  </tr>");

        if (images != null) {
            Set<Map.Entry<String, String>> set =  images.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                String value = entry.getValue();
                out.println("  <tr>");
                out.println("    <th> <a href='" + "/images/" + key + "'>" + key + "</a></th>");
                out.println("    <th>" + value + "</th>");
                out.println("    <th>image/jpeg</th>");
                out.println("  </tr>");
            }
        }

        out.println("</table>");
    %>
</body>
</html>
