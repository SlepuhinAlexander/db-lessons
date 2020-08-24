package ifmo.jjd.dao;

import ifmo.jjd.pool.C3P0DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements Dao<Book, Integer> {
    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String select = "SELECT * FROM book;";
        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("page_count")));
            }
        }
        return books;
    }

    @Override
    public Book getByPK(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public void add(Book entity) throws SQLException {

    }

    @Override
    public void update(Book entity) throws SQLException {

    }

    @Override
    public void delete(Book entity) throws SQLException {

    }
}
