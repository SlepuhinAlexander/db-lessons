package ifmo.jjd.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ifmo_group") /* для postgres название group для таблицы уже занято, нужно любое другое имя*/
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries/* для формирования именованных запросов */({
        // для формирования нескольких именованных запросов
        /*
         * Запрос query составляется на синтаксисе JPQL
         * Равносилен SQL, но вместо таблиц используются имена классов, а вместо столбцов - имена полей.
         *
         * Для использования нативного SQL в именованных запросах необходимо использовать другие аннотации:
         * @NamedNativeQueries({@NamedNativeQuery(), @NamedNativeQuery()})
         * */
        @NamedQuery/* для одного запроса */(name = "Group.getAll", query = "SELECT g FROM Group g"),
        @NamedQuery(name = "Group.findByName", query = "SELECT g FROM Group g WHERE g.groupName = :groupName")
        /* :groupName -- указание на переменную. groupName - */
})
public class Group extends BaseIdentify {

    @Getter
    @Setter
    @NonNull
    private String groupName;

    @Getter
    @Setter
    @NonNull
    private int duration;

    @Getter
    @Setter
    @NonNull
    private int price;

    /*
     * JPA позволяет использовать в сущностях некоторые коллекции, например листы.
     * */
    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private List<Student> students = new ArrayList<>();
}
