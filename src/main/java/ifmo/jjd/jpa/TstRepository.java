package ifmo.jjd.jpa;

import ifmo.jjd.jpa.entity.Group;
import ifmo.jjd.jpa.entity.Student;
import ifmo.jjd.jpa.entity.StudentInfo;
import ifmo.jjd.jpa.repository.GroupRepository;
import ifmo.jjd.jpa.repository.StudentRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TstRepository {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("ormLesson");
        EntityManager manager = factory.createEntityManager();

        GroupRepository groupRepository = new GroupRepository(manager);
        StudentRepository studentRepository = new StudentRepository(manager);


        Group jjd = new Group("JJD", 3, 20000);
        Group python = new Group("Python", 2, 15000);
        Group nodeJS = new Group("Node JS", 3, 15000);
        Group web = new Group("Web Developer", 4, 25000);
        /*
         * Просто созданы объекты типа Group, никакой взаимосвязи с БД пока нет.
         * */


        // ДОБАВЛЕНИЕ ГРУПП В БД
        manager.getTransaction().begin();
        groupRepository.add(jjd);
        /*
         * Метод add() вызывает метод persist(), он генерирует запрос к БД, идёт добавление в БД.
         * */
        groupRepository.add(python);
        groupRepository.add(nodeJS);
        groupRepository.add(web);
        manager.getTransaction().commit();


        Student ivan = new Student("ivan@mail.ru");
        Student oleg = new Student("oleg@mail.ru");
        Student misha = new Student("misha@mail.ru");

        StudentInfo ivanInfo = new StudentInfo("Ivan", 16);
        StudentInfo olegInfo = new StudentInfo("Oleg", 27);

        ivanInfo.setStudent(ivan);
        olegInfo.setStudent(oleg);

        ivan.setStudentInfo(ivanInfo);
        oleg.setStudentInfo(olegInfo);
        /*
         * Создали объекты студентов, инфо, передали им ссылки друг на друга.
         * */

        // ДОБАВЛЕНИЕ СТУДЕНТОВ И ИХ КОНТАКТОВ В БД
        manager.getTransaction().begin();
        studentRepository.add(ivan);
        /*
         * Добавлятся студент в таблицу, связанные с ним данные автоматически тоже добавляются: в данном случае
         * контакты студента
         * */
        studentRepository.add(oleg);
        studentRepository.add(misha);
        /*
         * Условия, при которых связанные данные будут добавлены автоматически.
         * - на уровне кода нужно пробросить взаимные ссылки (студенту - инфо, инфо - студента)
         * - у добавляемой сущности (у студента), у связанной информации (инфо), в аннотации связи должно быть
         *      CascadeType.PERSIST (или CascadeType.ALL, который его включает)
         *      - Варианты каскада:
         *          - ALL - всё
         *          - PERSIST - добавление
         *          - MERGE - обновление
         *          - REMOVE - удаление
         *          - REFRESH - хз
         *          - DETACH - хз
         *      - способ задания нескольких условий каскада: cascade = {CascadeType.MERGE, CascadeType.PERSIST}
         * */

        manager.getTransaction().commit();

        // ДОБАВЛЕНИЕ ГРУППЫ СТУДЕНТУ
        ivan.getGroups().add(jjd);
        ivan.getGroups().add(python);
        oleg.getGroups().add(python);
        oleg.getGroups().add(nodeJS);
        misha.getGroups().add(jjd);

        // ДОБАВЛЕНИЕ СТУДЕНТА ГРУППЕ
        jjd.getStudents().add(ivan);
        jjd.getStudents().add(misha);
        python.getStudents().add(ivan);
        python.getStudents().add(oleg);
        nodeJS.getStudents().add(oleg);
        /*
         * в объекты студентов и групп добавилась информация, на БД это никак не отразилось.
         * */

        // ОБНОВЛЕНИЕ ИНФОРМАЦИИ ПО СТУДЕНТАМ В БД
        // (у объектов студентов обновилась информация по группам)
        manager.getTransaction().begin();
        studentRepository.update(ivan);
        /*
         * обновляются данные в таблице student и все связанные данные в таблицах group и student_group
         * */
        studentRepository.update(oleg);
        studentRepository.update(misha);
        manager.getTransaction().commit();

        System.out.println("---все группы---");
        List<Group> groupList = groupRepository.getAll();
        for (Group group : groupList) {
            System.out.println(group.getGroupName());
        }
        Group group = groupRepository.getByGroupName("JJD");
        System.out.println(group.getGroupName());

        manager.close();
        factory.close();

    }
}
