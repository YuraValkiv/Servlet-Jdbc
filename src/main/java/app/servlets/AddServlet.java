package app.servlets;

import app.config.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class AddServlet extends HttpServlet {
    private DB jdbc = new DB();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Отримання даних з тіла запиту у вигляді рядка
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String xmlData = sb.toString();

        // Розбір XML-документа за допомогою JAXP
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlData));
            Document doc = dBuilder.parse(inputSource);
            doc.getDocumentElement().normalize();

            // Отримання значень елементів XML-документа
            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String author = doc.getElementsByTagName("author").item(0).getTextContent();
            int release = Integer.parseInt(doc.getElementsByTagName("release").item(0).getTextContent());

            // Додавання до бази даних
            jdbc.jdbcTemplate().update("INSERT INTO books (name, author, release) VALUES (?, ?, ?)", name, author, release);

            // Повернення результату додавання
            resp.setContentType("text/plain");
            resp.getWriter().write("Book added to database");
        } catch (Exception e) {
            resp.setContentType("text/plain");
            resp.getWriter().write("Error adding book to database: " + e.getMessage());
        }

    }
}
