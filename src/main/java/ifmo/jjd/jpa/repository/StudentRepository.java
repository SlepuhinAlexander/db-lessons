package ifmo.jjd.jpa.repository;

import ifmo.jjd.jpa.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentRepository implements Repository<Student, Integer> {
    private EntityManager manager;

    public StudentRepository(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public void add(Student student) {
        manager.persist(student);
    }

    @Override
    public void update(Student student) {
        manager.merge(student);
    }

    @Override
    public void delete(Integer integer) {

    }


    @Override
    public Student getByPk(Integer integer) {
        return null;
    }


    @Override
    public List<Student> getAll() {
        return null;
    }

}
