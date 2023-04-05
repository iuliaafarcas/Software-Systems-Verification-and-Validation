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
}
