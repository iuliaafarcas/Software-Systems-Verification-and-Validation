package org.example;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import repository.StudentRepository;
import validation.StudentValidator;

import java.util.Iterator;

public class AddStudentTest {
    StudentRepository repository;

    @Before
    public void initialize() {
        repository = new StudentRepository(new StudentValidator());
    }

    @Test
    public void testId() {
        repository.save(new Student("932", "iuliasharra", 111));
        assert repository.findOne("932") != null;
        repository.save(new Student("", "iuliasharra", 112));
        assert repository.findOne("") == null;
        repository.save(new Student(null, "iuliasharra", 113));
        Iterator<Student> iterator = repository.findAll().iterator();
        iterator.next();
        assert !iterator.hasNext();
    }

}
