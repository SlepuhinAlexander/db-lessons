package ifmo.jjd.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor /* генератор конструктора с параметрами */
@NoArgsConstructor /* генератор конструктора без параметров */
public class StudentInfo extends BaseIdentify /* унаследуется первичный ключ */ {
    @Getter
    @Setter
    @NonNull
    /*
     * поле с аннотацией @NonNull считается обязательным, и из таких полей будет собран конструктор с параметрами.
     * */
    private String name;

    @Getter
    @Setter
    @NonNull
    private int age;

    @Getter
    @Setter
    /*
     * указание, что ссылка является 1-к-1 связью
     * без указания FetchType по умолчанию при селектах из БД будут запрашиваться и связанные записи из связанной
     * таблицы.
     * указание cascade определяет каскадное поведение со связью. CascadeType.ALL означает полную зависимость: при
     * обновлении, удалении будут обновляться / удаляться зависимые записи.
     **/
    @OneToOne(fetch = FetchType.LAZY)
    /*
     * Добавление столбца-связи с именем name, завязанным на referencedColumnName в связанной таблице
     * */
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student; // ссылка на студента
}
