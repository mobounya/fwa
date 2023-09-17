package fr.fortytwo.cinema.servlets;

import fr.fortytwo.cinema.services.UsersService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/images/*")
public class ImagesServlet extends HttpServlet {
    private UsersService usersService;
    private String imageStoragePath;
    private final static long KB = 1000;
    private final static long MB = KB * 1000;
    private final static long GB = MB * 1000;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.usersService = applicationContext.getBean(UsersService.class);
        this.imageStoragePath = applicationContext.getEnvironment().getProperty("storage.path");
    }

    private String getFileSize(long fileSize) {
        if (fileSize < KB)
            return fileSize + "B";
        else if (fileSize < MB)
            return (fileSize / KB) + "KB";
        else if (fileSize < GB)
            return (fileSize / MB) + "MB";
        else
            return (fileSize / GB) + "GB";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = request.getServletContext();
        String pathInfo = request.getPathInfo();
        String imagePath = imageStoragePath + pathInfo;
        try (InputStream input = context.getResourceAsStream(imagePath)) {
            if (input == null) {
                response.setStatus(404);
            } else {
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] bytes = input.readAllBytes();
                response.setContentType("images/jpeg");
                outputStream.write(bytes);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        String pathInfo = request.getPathInfo();
        String imagePath = context.getRealPath(imageStoragePath + pathInfo);
        String randomString = RandomStringUtils.randomAlphanumeric(10);
        imagePath += randomString;
        HttpSession session = request.getSession();
        if (session.getAttribute("images") == null) {
            session.setAttribute("images", new HashMap<String, String>());
        }
        Map<String, String> images = (Map<String, String>) session.getAttribute("images");
        InputStream stream = request.getInputStream();
        File file = new File(imagePath);
        if (!file.createNewFile()) {
            response.setStatus(500);
            return ;
        }
        try (FileOutputStream newImage = new FileOutputStream(file)) {
            byte[] bytes = new byte[100];
            int bytesRead;
            long totalFileSize = 0;
            while ((bytesRead = stream.read(bytes)) > 0) {
                newImage.write(bytes, 0, bytesRead);
                totalFileSize += bytesRead;
            }
            newImage.flush();
            images.put(pathInfo.substring(1) + randomString, getFileSize(totalFileSize));
            response.setStatus(200);
        }
    }
}
