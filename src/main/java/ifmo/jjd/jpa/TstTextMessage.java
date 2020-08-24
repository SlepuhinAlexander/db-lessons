package ifmo.jjd.jpa;

import ifmo.jjd.jpa.entity.TextMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.GregorianCalendar;

public class TstTextMessage {
    public static void main(String[] args) {
        // создаём объект, который будет управлять сущностями.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ormLesson");
        /*
         * метод createEntityManagerFactory создаёт объект-фабрику EntityManagerFactory, который умеет создавать
         * объекты типа EntityManager, которые будут управлять сущностями, которые описаны в переданном
         * persistence-unit-е (используя их настройки).
         * */

        EntityManager manager = factory.createEntityManager();
        TextMessage textMessage = new TextMessage();
        textMessage.setSent(new GregorianCalendar());
        textMessage.setAuthor("author");
        textMessage.setText("message text");
        manager.getTransaction().begin(); // начать транзакцию
        manager.persist(textMessage); // добавить запись в БД.
        /*
         * В ORM реализованы методы
         *  - добавления записи в БД: persist
         *  - обновления записи в БД: merge
         *  - удаления записи из БД: remove
         *  - получения записи по первичному ключу: find
         * */

        manager.getTransaction().commit(); // применить транзакцию.

        // получение записи по первичному ключу
        TextMessage messageFromDb = manager.find(TextMessage.class, 1);
        System.out.println("---messageFromDb--- " + messageFromDb.getAuthor());

        /*
         * по окончании работ менеджеры и фабрику нужно закрыть.
         * */
        manager.close();
        factory.close();
    }
}
