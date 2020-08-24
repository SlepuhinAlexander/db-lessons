package ifmo.jjd.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

// для использования пула соединений его необходимо подходящим образом настроить.
public class C3P0DataSource {
    private static ComboPooledDataSource cdps = new ComboPooledDataSource();

    // настройка пула соединений
    // все эти настройки можно (да и нужно) указать в property-файле
    static {
        try {
            // Обязательные настройки
            // настройка используемого драйвера
            cdps.setDriverClass("org.postgresql.Driver");
            // строка подключения к базе
            cdps.setJdbcUrl("jdbc:postgresql://localhost:5432/db_lessons");
            // пользователь бд
            cdps.setUser("jjdbd");
            // пароль пользователя
            cdps.setPassword("Pr!v333t");

            // Все остальные настройки не обязательные. Если не заданы - используются значения по умолчанию.
            // изначальное количество соединений
            cdps.setInitialPoolSize(5); // по умолчанию - 3
            // минимальное количество соединений
            cdps.setMinPoolSize(4); // по умолчанию - 3
            // максимальное количество соединений
            cdps.setMaxPoolSize(8); // по умолчанию - 15
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    // получение соединения из пула
    public static Connection getConnection() throws SQLException {
        return cdps.getConnection();
    }
}
