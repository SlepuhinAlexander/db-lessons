package ifmo.jjd.base;

import java.sql.*;

public class JDBCLesson {
    //строка подключения: драйвер , путь , база
    static final String CON_STR = "jdbc:postgresql://localhost:5432/db_lessons";
    static final String LOGIN = "jjdbd";
    static final String PWD = "Pr!v333t";

    public static void main(String[] args) {
        /*
         * Для подключения к серверу БД нужно знать
         * - адрес (IP)
         * - порт
         * - логин
         * - пароль
         *
         * Для работы с БД нужно создать подключение к серверу БД, составить SQL запрос, отправить его на сервер и
         * получить результат: успех или ошибка.
         *
         * На одном SQL сервере может быть несколько баз данных.
         * Внутри каждой БД может быть много таблиц.
         * В каждой таблице может быть множество записей.
         * */

        /*
         * В языке есть набор абстрактных методов для соединения с БД и выполнения запросов к ней: JDBC
         * В зависимости от того какую конкретно БД использует проект, нужно выбирать соответствующий драйвер.
         *
         * Для работы с postgresql мы в pom.xml установили зависимость:
         *     <dependency>
         *         <groupId>org.postgresql</groupId>
         *         <artifactId>postgresql</artifactId>
         *         <version>42.2.12</version>
         *         <scope>runtime</scope>
         *     </dependency>
         * По сути, это будет скачивание конкретной реализации этих абстрактных методов для работы с БД
         *
         * Реализация будет использована именно в момент выполнения программы, в процессе написания кода используются
         * абстрактные методы, из языка, а не из библиотеки.
         *
         * Поэтому scope зависимости должен быть runtime
         * */
        try {
//            createTable();
//            insertIntoBook("Java", 234);
            getAllBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable() throws ClassNotFoundException, SQLException {
        String create = "CREATE TABLE IF NOT EXISTS book(" +
                        "id SERIAL PRIMARY KEY," +
                        "title VARCHAR(50) NOT NULL," +
                        "page_count INTEGER NOT NULL" +
                        ");";
        // регистрация драйвера
        Class.forName("org.postgresql.Driver");
        /*
         * Динамически загружаем класс драйвера подключения к БД
         * */
        try (Connection connection = DriverManager.getConnection(CON_STR, LOGIN, PWD)) {
            /*
             * Объект Statement нужен для того чтобы выполнить SQL-запрос
             * Если в запросе нет переменных (нет вставки данных через переменные), то используется класс Statement
             * Если есть переменные, то нужно использовать класс PreparedStatement
             * */
            try (Statement statement = connection.createStatement()) {
                int res = statement.executeUpdate(create); // возвращает количество успешно обработанных строк
                System.out.println("res " + res); // res = 0
            }
        }
    }

    public static void insertIntoBook(String title, int pageCount) throws ClassNotFoundException, SQLException {
        String insert = "INSERT INTO book (title, page_count) VALUES ('Java', 234)";
        /*
         * Когда данные для запроса приходят динамически в каких-то переменных, так делать нельзя:
         * String insert = "INSERT INTO book (title, page_count) VALUES (" + title + ", " + pageCount + ");";
         *
         * Такой подход исключает проверку драйвера на sql-инъекции в запросе
         * и отменяет кэширование такого запроса драйвером.
         *
         * Правильный подход:
         * */
        insert = "INSERT INTO book (title, page_count) VALUES (?, ?);";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(CON_STR, LOGIN, PWD)) {
            /*
             * Для выполнения запроса с параметрами (со знаками вопроса - такой запрос называется ПОДГОТОВЛЕННЫМ
             * ЗАПРОСОМ) вместо объекта Statement нужен объект PreparedStatement
             * */
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setString(1, title);
                // вместо первого '?' в запросе нужно подставить соответствующий параметр запроса
                statement.setInt(2, pageCount);
                // вместо второго '?' в запросе нужно подставить соответствующий параметр запроса
                int res = statement.executeUpdate();
                System.out.println("res = " + res); // res = 1
            }
        }
    }

    public static void getAllBooks() throws ClassNotFoundException, SQLException {
        String select = "SELECT * FROM book;";
        Class.forName("org.postgresql.Driver");
        /*
         * Правильней, для каждого выполнения запроса не открывать новое соединение, а брать готовое соединение из
         * пула соединений.
         * Для работы с пулом соединений, нужно подключить подходящую библиотеку. Например com.mchange c3p0
         *
         * */
        try (Connection connection = DriverManager.getConnection(CON_STR, LOGIN, PWD)) {
            try (Statement statement = connection.createStatement()) {
                // executeQuery()
                /*
                 * для получения данных из запроса нужен метод executeQuery()
                 * он в результате выполнения возвращает объект типа ResultSet
                 * */
                ResultSet resultSet = statement.executeQuery(select);
                /*
                 * Данные из ResultSet нужно обрабатывать в рамках открытого Statement
                 * Нельзя вернуть ResultSet из метода и потом из него что-то считать - там уже ничего не будет.
                 * */
                while (resultSet.next()) {
                    /*
                     * в рамках одной итерации работаем с одной строкой (пока такие строки есть)
                     * */
                    String title = resultSet.getString("title");
                    /*
                     * если в запросе использовались псевдонимы, то в качестве columnLabel нужно использовать этот
                     * самый псевдоним в результирующей таблице
                     * */
                    int pageCount = resultSet.getInt("page_count");
                    System.out.println("title = " + title);
                    System.out.println("pageCount = " + pageCount);
                }
            }
        }
    }
}