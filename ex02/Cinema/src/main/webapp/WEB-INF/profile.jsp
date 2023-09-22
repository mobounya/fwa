<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="fr.fortytwo.cinema.models.User" %>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>

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
        String getFileType(String fileName) {
            int pointIndex = fileName.indexOf('.');
            if (pointIndex == -1)
                return null;
            String type = fileName.substring(pointIndex + 1);
            if (type.equals("jpeg") || type.equals("jpg"))
                return "image/jpeg";
            else if (type.equals("png"))
                return "image/png";
            else if (type.equals("svg"))
                return "image/svg";
            else if (type.equals("avif"))
                return "image/avif";
            else
                return null;
        }
        User user = null;
        Map<String, String> images = null;
        List<String> auths;
    %>

    <%
        User user = (User) session.getAttribute("user");
        Map<String, String> images = (Map<String, String>) session.getAttribute("images");
        String avatar = (String) session.getAttribute("avatar");
        auths = (List<String>) session.getAttribute("auths");
        if (avatar == null)
            avatar = "/images/not_available.png";
    %>

    <%= "<div style='overflow: auto; max-width: 780px; max-height: 100%; margin-left: 783px; margin-top: 260px;'>\n" %>

    <%= "<div id='user''>\n" %>

    <%= "<div id='image' style='display: inline;'>\n" %>
    <%= "<img style='max-height: auto; max-width: 500px;' src='" + avatar + "'alt='avatar'>\n" %>
    <%= "</div>" %>

    <%= "<div id='user_info' style='float: right;'>"%>
    <%= "<h3> " + user.getFirstName() + " " + user.getLastName() + " </h3>\n" %>
    <%= "<h3> " + user.getEmail() + " </h3>\n" %>

    <%= "<table id='auths'>\n" +
        "  <tr>\n" +
        "    <th>Date</th>\n" +
        "    <th>Time</th>\n" +
        "    <th>IP</th>\n" +
        "  </tr>"
    %>

    <%
        String[] data = auths.toArray(new String[0]);

        for (String str : data) {
            String[] splits = str.split("-");
            out.println("<tr>\n");
            out.println("   <td>" + splits[0] + "</td>\n");
            out.println("   <td>" + splits[1] + "</td>\n");
            out.println("   <td>" + splits[2] + "</td>\n");
            out.println("</tr>\n");
        }
        out.println("</table>\n");
    %>
    <%= "</div></div>\n" %>

    <%= "<div id='image_table' style=''>\n" %>

    <%= "<form action='/images' method='POST' enctype='multipart/form-data' style='margin-top: 25px; margin-bottom: 25px;'>" %>
    <%= "<label for='file' class='custom-upload-button'>Choose a File</label>" %>
    <%= "   <input type='file' id='file' name='myFile' accept='image/*'>" %>
    <%= "   <input type='submit' value='Upload'>" %>
    <%= "</form>" %>

    <%=
        "<table id='files'>\n" +
        "  <tr>\n" +
        "    <th>File name</th>\n" +
        "    <th>Size</th>\n" +
        "    <th>MIME</th>\n" +
        "  </tr>\n"
    %>

    <%
        if (images != null) {
            Set<Map.Entry<String, String>> set =  images.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                String value = entry.getValue();
                String type = getFileType(key);
                if (type == null)
                    type = "N/A";
                out.println("  <tr>\n");
                out.println("    <th> <a href='" + "/images/" + key + "'>" + key.substring(10) + "</a></th>\n");
                out.println("    <th>" + value + "</th>\n");
                out.println("    <th>" + type + "</th>\n");
                out.println("  </tr>\n");
            }
        }
        out.println("</table>\n");
    %>
    <%= "</div>\n" %>
    <%= "</div>\n" %>
    <%= "</div>\n" %>
</body>
</html>
