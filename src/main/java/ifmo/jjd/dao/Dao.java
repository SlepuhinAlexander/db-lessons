package ifmo.jjd.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, PK> {
    // T - тип данных таблицы / класса
    // PK - тип данных первичного ключа

    // получение всех записей
    List<T> getAll() throws SQLException;

    // получение записи по первичному ключу
    T getByPK(PK pk) throws SQLException;

    // добавление записи
    void add(T entity) throws SQLException;

    // обновление записи
    void update(T entity) throws SQLException;

    // удаление записи
    void delete(T entity) throws SQLException;
}
