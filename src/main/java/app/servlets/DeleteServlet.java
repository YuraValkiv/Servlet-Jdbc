package app.servlets;

import app.config.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private DB jdbc = new DB();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Отримання ідентифікатора книжки з URL
        String[] pathParts = req.getPathInfo().split("/");
        int id = Integer.parseInt(pathParts[1]);

        // Видалення книжки з бази даних
        int numRowsDeleted = jdbc.jdbcTemplate().update("DELETE FROM books WHERE id = ?", id);

        // Перевірка результату видалення
        if (numRowsDeleted > 0) {
            resp.setContentType("text/plain");
            resp.getWriter().write("Book with ID " + id + " deleted from database");
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("Book with ID " + id + " not found in database");
        }
    }
}
