package org.example;


import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.TemaRepository;
import validation.TemaValidator;

public class AddAssignmentTest {
    private TemaRepository assignmentRepository;

    @Before
    public void initialize() {
        assignmentRepository = new TemaRepository(new TemaValidator());
    }

    @Test
    public void testIdNull() {
        assignmentRepository.save(new Tema(null, "ssvv", 2, 1));
        var iterator = assignmentRepository.findAll().iterator();
        assert !iterator.hasNext();
    }

    @Test
    public void testIdEmpty() {
        assignmentRepository.save(new Tema("", "ssvv", 2, 1));
        assert assignmentRepository.findOne("") == null;
    }

    @Test
    public void testIdValid() {
        assignmentRepository.save(new Tema("A1", "ssvv", 2, 1));
        assert assignmentRepository.findOne("A1") != null;
    }

    @Test
    public void testDescriptionEmpty() {
        assignmentRepository.save(new Tema("A1", "", 2, 1));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testDescriptionNull() {
        assignmentRepository.save(new Tema("A1", null, 2, 1));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testDeadlineBeforeInterval() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 0, 1));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testDeadlineAfterInterval() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 15, 1));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testDeadlineBeforeStartline() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 1, 2));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testStartlineBeforeInterval() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 2, 0));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testStartlineAfterInterval() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 2, 15));
        assert assignmentRepository.findOne("A1") == null;
    }

    @Test
    public void testStartlineAfterDeadline() {
        assignmentRepository.save(new Tema("A1", "descriere A1", 2, 3));
        assert assignmentRepository.findOne("A1") == null;
    }
}
