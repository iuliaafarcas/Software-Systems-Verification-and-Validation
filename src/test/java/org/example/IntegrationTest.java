package org.example;

import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {
    private TemaXMLRepository assignmentRepository;
    private StudentXMLRepository studentRepository;
    private NotaXMLRepository gradeRepository;
    private Service service;
    private StudentXMLRepository studentMockRepo;
    private TemaXMLRepository assignmentMockRepo;
    private Service mockService;

    @Before
    public void setUp() {
        assignmentRepository = new TemaXMLRepository(new TemaValidator(), "teme.xml");
        studentRepository = new StudentXMLRepository(new StudentValidator(), "studenti.xml");
        gradeRepository = new NotaXMLRepository(new NotaValidator(), "note.xml");
        service = new Service(studentRepository, assignmentRepository, gradeRepository);
        studentMockRepo = mock();
        when(studentMockRepo.save(new Student("112", "mirel", 111)))
                .thenReturn(null);
        when(studentMockRepo.findOne("112"))
                .thenReturn(new Student("112", "mirel", 111));

        assignmentMockRepo = mock();
        when(assignmentMockRepo.save(new Tema("A2", "ceva", 2, 1)))
                .thenReturn(null);
        when(assignmentMockRepo.findOne("A2"))
                .thenReturn(new Tema("A2", "ceva", 2, 1));

        mockService = new Service(studentMockRepo, assignmentMockRepo, gradeRepository);

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
    @Test
    public void addStudentMockTest() {
        assert mockService.saveStudent("112", "mirel", 111) == 1;
        assert studentMockRepo.findOne("112").equals(new Student("112", "mirel", 111));
    }

    @Test
    public void addAssignmentMockTest() {
        assert mockService.saveTema("A2", "ceva", 2, 1) == 1;
        assert assignmentMockRepo.findOne("A2").equals(new Tema("A2", "ceva", 2, 1));
    }

    @Test
    public void integrationTestStudentAssignment() {
        service.saveStudent("112", "gigel", 111);
        var students = service.findAllStudents();
        assert students.iterator().hasNext();
        service.saveTema("A1", "ceva", 2, 1);
        var assignments = service.findAllTeme();
        assert assignments.iterator().hasNext();
    }

    @Test
    public void integrationTestStudentAssignmentMock() {
        assert mockService.saveStudent("112", "mirel", 111) == 1;
        assert studentMockRepo.findOne("112").equals(new Student("112", "mirel", 111));

        assert mockService.saveTema("A2", "ceva", 2, 1) == 1;
        assert assignmentMockRepo.findOne("A2").equals(new Tema("A2", "ceva", 2, 1));
    }

    @Test
    public void addGradeMockTest() {
        mockService.saveStudent("112", "mirel", 111);
        assert studentMockRepo.findOne("112").equals(new Student("112", "mirel", 111));
        mockService.saveTema("A2", "ceva", 2, 1);
        assert assignmentMockRepo.findOne("A2").equals(new Tema("A2", "ceva", 2, 1));
        int returnValue = mockService.saveNota("112", "A2", 9.00, 2, "Bravo");
        assert returnValue != -1;
    }
}
