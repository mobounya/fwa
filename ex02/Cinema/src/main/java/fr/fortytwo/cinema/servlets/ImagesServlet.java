package fr.fortytwo.cinema.servlets;

import fr.fortytwo.cinema.services.UsersService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/images/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 50)  // 50 MB
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

        if (this.imageStoragePath != null && this.imageStoragePath.charAt(this.imageStoragePath.length() - 1) != '/')
            this.imageStoragePath = this.imageStoragePath + "/";
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

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("myFile");
        if (filePart == null) {
            response.setStatus(400);
            return ;
        }
        ServletContext context = getServletContext();
        String imageName = getFileName(filePart);
        if (imageName == null || imageName.isEmpty()) {
            response.setStatus(400);
            return ;
        }
        String randomString = RandomStringUtils.randomAlphanumeric(10);
        String fullImageName = randomString + imageName;
        String imagePath = context.getRealPath(imageStoragePath + fullImageName);
        File imageFile = new File(imagePath);
        if (!imageFile.createNewFile()) {
            response.setStatus(500);
            return ;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("images") == null)
            session.setAttribute("images", new HashMap<String, String>());
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            byte[] bytes = new byte[100];
            int bytesRead;
            long totalFileSize = 0;
            InputStream inputStream = filePart.getInputStream();
            while ((bytesRead = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, bytesRead);
                totalFileSize += bytesRead;
            }
            outputStream.flush();
            Map<String, String> images = (Map<String, String>) session.getAttribute("images");
            images.put(fullImageName, getFileSize(totalFileSize));
            session.setAttribute("avatar", "/images/" + fullImageName);
            response.sendRedirect("/profile");
        }
    }
}
