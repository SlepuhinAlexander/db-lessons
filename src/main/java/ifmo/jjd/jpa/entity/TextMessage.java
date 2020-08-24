package ifmo.jjd.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;

/*
 * указание, что данный класс является entity. Название таблицы будет собрано из названия класса.
 * в данном случае 'text_message'
 * */
@Entity
/*
 * если необходимо переопределить имя для создаваемой таблицы
 * */
@Table(name = "message")
@ToString
/*
 * Entity класс не может быть финальным классом.
 * 1. Регистрация в persistence.xml - в теге <class> указать полное имя класса
 * 2. Аннотация @Entity
 * 3. Обязательно наличие первичного ключа
 * 4. Обязательно наличие геттеров и сеттеров для всех полей класса, которые будут столбцами в таблице
 * 5. Обязательно наличие конструктора без параметров
 * 6. По умолчанию, все поля @Entity класса будут столбцами в БД.
 *
 * Поля, которые не будут столбцами в БД:
 * final static / static / transient / @Transient
 * */
public class TextMessage {
    /*
     * Допустимые типы полей у @Entity классов
     * 1. примитивные типы и их обёртки
     * 2. строки
     * 3. Любые сериализуемые типы (implements Serializable)
     * 4. Перечисления (Enum-s)
     * 5. Другие @Entity классы
     * 6. Коллекции
     * */

    /*
     * Обязательно наличие первичного ключа. Он может быть любого типа данных. Размечается аннотацией @Id, если ключ не
     * является составным. Для составного ключа нужно писать отдельный класс.
     * */
    @Id
    /*
     * GeneratedValue - аннотация, указывающая, что значение поля автоматически генерируются.
     * Указание GenerationType.AUTO, означает, что первичный ключ будет устанавливаться авто-инкрементом.
     * Инкремент будет общий на все таблицы: т.е. другие таблицы, использующие GenerationType.AUTO тоже будут учитывать
     * и использовать этот же авто инкремент.
     * */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    /*
     * на каждое поле Entity класса будет создаваться столбец в таблице. Если нужно переопределить какие-то
     * дополнительные параметры столбца, то нужно использовать @Column
     * варианты:
     * String name() default ""; - имя столбца
     * boolean unique() default false; - является ли содержимое столбца уникальным
     * boolean nullable() default true; - может ли быть нулл0ом
     * boolean insertable() default true; - можно ли вставлять записи
     * boolean updatable() default true; - можно ли изменять записи
     * String columnDefinition() default ""; - текстовое описание
     * String table() default ""; - хз
     * int length() default 255; - длина столбца
     * int precision() default 0; - хз
     * int scale() default 0; - хз
     * */
    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    @Column(length = 1000)
    private String text;

    /*
     * JPA не умеет работать с современным DateTimeAPI java. Может работать только с Date и Calendar.
     * @Temporal указывает, что данное поле является датой-временем.
     * Варианты:
     *      - TemporalType.TIMESTAMP - дата-время
     *      - TemporalType.TIME - только время
     *      - TemporalType.DATE - только дата
     * */
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Calendar sent;

    /*
     * Для @Entity класса обязательно наличие конструктора без параметров
     *
     * Обязательно наличие геттеров-сеттеров. Напрямую к свойствам класса JPA обращаться не разрешает.
     * Аннотации @Getter и @Setter из библиотеки lombok позволят создать стандартные геттеры-сеттеры.
     * */
}
