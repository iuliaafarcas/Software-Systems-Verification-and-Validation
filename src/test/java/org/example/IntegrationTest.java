package org.example;

import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class IntegrationTest {
    private TemaXMLRepository assignmentRepository;
    private StudentXMLRepository studentRepository;
    private NotaXMLRepository gradeRepository;
    private Service service;

    @Before
    public void setUp() {
        assignmentRepository = new TemaXMLRepository(new TemaValidator(), "teme.xml");
        studentRepository = new StudentXMLRepository(new StudentValidator(), "studenti.xml");
        gradeRepository = new NotaXMLRepository(new NotaValidator(), "note.xml");
        service = new Service(studentRepository, assignmentRepository, gradeRepository);
    }

    @Test
    public void addStudentTest() {
        service.saveStudent("112", "dalia", 111);
        var students = service.findAllStudents();
        assert students.iterator().hasNext();
    }

    @Test
    public void addAssignmentTest() {
        service.saveTema("A1", "descriere", 2, 1);
        var assignments = service.findAllTeme();
        assert assignments.iterator().hasNext();
    }

    @Test
    public void addGradeTest() {
        int retval = service.saveNota("112", "A2", 9.00, 2, "Bravo");
        assert retval == -1;
    }

    @Test
    public void integrationTest() {
        service.saveStudent("112", "dalia", 111);
        var students = service.findAllStudents();
        assert students.iterator().hasNext();
        service.saveTema("A1", "descriere", 2, 1);
        var assignments = service.findAllTeme();
        assert assignments.iterator().hasNext();
        int retval = service.saveNota("112", "A1", 9.00, 2, "Bravo");
        assert retval != -1;

    }
}
