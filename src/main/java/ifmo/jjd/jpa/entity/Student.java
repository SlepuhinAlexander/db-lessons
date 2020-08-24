package ifmo.jjd.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Student extends BaseIdentify {
    @Getter
    @Setter
    @NonNull
    private String email;

    @Getter
    @Setter
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    /*
     * Связь по имени поля mappedBy из этого класса: в данном случае по полю student класса Contact
     * */
    private StudentInfo studentInfo;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
    /*
     * Если поставить аннотацию @JoinTable без конкретизации:
     * - сводная таблица будет создана с настройками по умолчанию:
     *      - название: названиеT1_названиеT2
     * - дополнительные столбцы тоже создадутся
     *      - названия генерируются названиям полей в классах: groups_id и students_id
     *      - данная таблица названиеT1_названиеT2 и таблица students будет связана по столбцу students_id
     *      - данная таблица названиеT1_названиеT2 и таблица groups будет связана по столбцу group_id
     * */
    /*
     * При желании, можно переопределить настройки создания сводной таблицы:
     * @JoinTable(name = "student_group", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
     *              inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
     *
     * - name - имя создаваемой таблицы
     * - joinColumns - создания связываемого столбца в данной сводной таблице. Cвязь с таблицей текущего entity
     *      - name - имя этого столбца
     *      - referencedColumnName - связь этого столбца с таблицей student (таблицей текущего entity) по имени
     *          первичного ключа
     * - inverseJoinColumns - создание связываемого столбца в данной сводной таблице со связываемой таблицей (group)
     *      - name - имя этого столбца
     *      - referencedColumnName - связь этого столбца с таблицей group по имени первичного ключа.
     * */
    private List<Group> groups = new ArrayList<>();
}
