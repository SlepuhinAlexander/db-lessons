package ifmo.jjd.jpa.repository;

import ifmo.jjd.jpa.entity.Group;
import ifmo.jjd.jpa.entity.Group_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class GroupRepository implements Repository<Group, Integer> {
    private final EntityManager manager;

    public GroupRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Group group) {
        manager.persist(group); // добавление записи в таблицу
    }

    @Override
    public void update(Group group) {
        manager.merge(group); // обновление существующей записи
    }


    @Override
    public void delete(Integer integer) {
//        manager.getTransaction().begin();
        Group group = getByPk(integer);
        manager.remove(group); // удаление объекта
        /*
         * по JPA метод remove принимает на вход ОБЪЕКТ, который нужно удалить. а не первичный ключ.
         *
         * если нужен метод удаления по первичному ключу - пишем свой: получить по ключу объект и удалить объект.
         *
         * Получение и удаление должны быть завёрнуты в одну транзакцию: если не получил, то и нет смысла удалять.
         * */
//        manager.getTransaction().commit();
    }

    @Override
    public Group getByPk(Integer integer) {
        // получение записи по первичному ключу
        return manager.find(Group.class, integer);
        /*
         * метод find() ожидает ссылку на класс сущности и первичный ключ
         *
         * равносилен SELECT * FROM group WHERE id = 1
         * где id = 1 -- первичный ключ.
         * */
    }

    @Override
    public List<Group> getAll() {
        // реализуем запрос "SELECT * FROM group;" тремя способами.
        // 1. named queries - именованные запросы
        /*
         * именованный запрос - запрос, у которого есть имя.
         * он должен быть где-то задан, и сохранён под каким-то именем.
         * где-то -- в соответствующем классе-сушности.
         *
         * создали @NamedQuery(name = "Group.getAll", query = "SELECT g FROM Group g")
         * затем нужно создать объект запроса на основе именованного запроса:
         * */
        TypedQuery<Group> typedQuery = manager.createNamedQuery("Group.getAll", Group.class);
        /*
         * тип данных TypedQuery (типизированный запрос) позволяет в дженерик-параметре указать ожидаемый тип
         * возвращаемых данных.
         *
         * метод createNamedQuery ожидает указания имени запроса и указания где его искать (ссылку на класс)
         * */
        List<Group> groups1 = typedQuery.getResultList();
        /*
         * Если запрос предполагает возвращение 1 результата - нужен метод getSingleResult
         * Если ожидается несколько результатов - можно использовать getResultList (вернёт List) или getResultStream
         * (вернёт stream)
         * */

        // 2. сразу создать и использовать JPQL запрос
        Query query = manager.createQuery("SELECT g FROM Group g");
        //noinspection unchecked
        List<Group> groups2 = ((List<Group>) query.getResultList());

        // 3. сформировать запрос методами: Criteria API
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        // <Group> -- тип, который будет возвращать запрос (а не имя сущности, из которой выбираем)
        CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
        // корневой объект. Указывает, откуда будут браться данные (аналогичен FROM в SQL)
        Root<Group> root = criteriaQuery.from(Group.class); // аналогично FROM ifmo_group
        /*
         * Если запрос содержит несколько таблиц (JOIN ...), то корневым объектом будет первая таблица, та, которая
         * объединяется дальше с чем-то там.
         * */
        criteriaQuery.select(root); // аналогично SELECT * from ifmo_group;
        TypedQuery<Group> typedQuery3 = manager.createQuery(criteriaQuery);
        //noinspection UnnecessaryLocalVariable
        List<Group> groups3 = typedQuery.getResultList();

        /*
         * Для создания CriteriaQuery обязательны:
         * - наличия CriteriaBuilder (для конструирования запроса)
         * - наличия CriteriaQuery (содержащий, собственно, запрос)
         * - наличия Root
         * - наличия объекта типа Query, чтобы его можно было выполнить
         * */
        return groups3;
    }

    public Group getByGroupName(String groupName) {
        // "SELECT * FROM ifmo_group WHERE groupname = " + groupName;
        TypedQuery<Group> typedQuery = manager.createNamedQuery("Group.findByName", Group.class);
        typedQuery.setParameter("groupName", groupName);
        /*
         * setParameter() ожидает имя параметра из именованного параметризованного запроса и фактическое передаваемое
         * значение
         * */
        Group group = typedQuery.getSingleResult();

        // Criteria API
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class);
        // блок WHERE
        Predicate condition = criteriaBuilder.equal(root.get(Group_.groupName), groupName);
        criteriaQuery.select(root).where(condition);
        TypedQuery<Group> typedQuery2 = manager.createQuery(criteriaQuery);
        Group group2 = typedQuery2.getSingleResult();
        return group2;
    }
}
