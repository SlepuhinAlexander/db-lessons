package ifmo.jjd.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*
 * Базовые абстрактные родительские классы тоже можно использовать для создания классов-сущностей.
 * Такие базовые классы отмечаются @MappedSuperclass.
 * */
@MappedSuperclass
public abstract class BaseIdentify {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Для типа данных SERIAL
    /*
     * Идентификатор будет для данной таблицы свой. В отличии от GenerationType.ALL, где идентификатор был общий.
     * */
    private int id;
}
