package ifmo.jjd.dao;

import java.sql.SQLException;

public class DaoTest {
    public static void main(String[] args) {
        BookDao bookDao = new BookDao();
        try {
            System.out.println(bookDao.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
