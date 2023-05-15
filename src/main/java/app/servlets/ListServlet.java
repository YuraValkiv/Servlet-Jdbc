package app.servlets;

import app.config.DB;
import app.dao.BooksMapper;
import app.entities.Books;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListServlet extends HttpServlet {

    private DB jdbc = new DB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Books> books = jdbc.jdbcTemplate().query("SELECT * FROM books", new BooksMapper());
        resp.setContentType("text/xml"); // Встановлюємо тип відповіді на XML
        PrintWriter out = resp.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); // Починаємо XML документ
        out.println("<books>"); // Відкриваємо тег для списку книг

        for (Books book : books) {
            out.println("<book>"); // Відкриваємо тег для книги
            out.println("<id>" + book.getId() + "</id>");
            out.println("<name>" + book.getName() + "</name>"); // Додаємо тег з назвою книги
            out.println("<author>" + book.getAuthor() + "</author>"); // Додаємо тег з автором книги
            out.println("<release>" + book.getRelease() + "</release>");
            out.println("</book>"); // Закриваємо тег для книги
        }

        out.println("</books>"); // Закриваємо тег для списку книг
        out.close(); // Закриваємо потік виведення

    }
}
