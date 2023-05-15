package app.dao;

import app.entities.Books;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksMapper implements RowMapper<Books> {

    @Override
    public Books mapRow(ResultSet rs, int rowNum) throws SQLException {
        Books books = new Books();
        books.setId(rs.getInt("id"));
        books.setName(rs.getString("name"));
        books.setAuthor(rs.getString("author"));
        books.setRelease(rs.getInt("release"));

        return books;
    }
}
