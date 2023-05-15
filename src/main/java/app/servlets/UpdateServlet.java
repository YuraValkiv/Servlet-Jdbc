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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class UpdateServlet extends HttpServlet {
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
            int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String author = doc.getElementsByTagName("author").item(0).getTextContent();
            int release = Integer.parseInt(doc.getElementsByTagName("release").item(0).getTextContent());

            // Оновлення даних в базі даних
            jdbc.jdbcTemplate().update("UPDATE books SET name=?, author=?, release=? WHERE id=?", name, author, release, id);

            // Відправлення відповіді клієнту
            resp.setContentType("text/plain");
            resp.getWriter().write("Book updated id=" + id + " in database");
        } catch (Exception e) {
            resp.setContentType("text/plain");
            resp.getWriter().write("Error updating book in database: " + e.getMessage());
        }
    }

}
