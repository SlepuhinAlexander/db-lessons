package ifmo.jjd.jpa.repository;

import java.util.List;

/**
 * Репозиторий - абстрактная прослойка между Сущностями и таблицами БД.
 * Похож на DAO по использованию.
 * Может использовать DAO или рефлексию.
 */
public interface Repository<T, PK> {
    void add(T t); // добавление

    void update(T t); // обновление

    void delete(PK pl); // удаление по первичному ключу

    T getByPk(PK pk); // получение по первичному ключу

    List<T> getAll(); // получение всех записей
}
