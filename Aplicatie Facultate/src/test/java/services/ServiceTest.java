package services;

import com.sun.tools.javac.util.Pair;
import domains.Nota;
import domains.Student;
import domains.Tema;
import domains.validators.NotaValidator;
import domains.validators.StudentValidator;
import domains.validators.TemaValidator;
import domains.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inMemoryRepository.CrudRepository;
import repository.inMemoryRepository.NotaRepository;
import repository.inMemoryRepository.StudentRepository;
import repository.inMemoryRepository.TemaRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServiceTest {
    Service service;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        CrudRepository<String, Student> studentCrudRepository = new StudentRepository(studentValidator);
        Validator<Tema> temaValidator = new TemaValidator();
        CrudRepository<String, Tema> temaCrudRepository = new TemaRepository(temaValidator);
        Validator<Nota> notaValidator = new NotaValidator();
        CrudRepository<Pair<String, String>, Nota> notaCrudRepository = new NotaRepository(notaValidator);
        service = new Service(studentCrudRepository, temaCrudRepository, notaCrudRepository);
        Service.setCurrentWeek(5);
    }

    @Test
    void saveNotaThrowServiceExceptionIdNULL() {
        assertThrows(ServiceException.class, () -> {
            service.saveNota("", "0", LocalDate.of(2019, 11, 11), "Horia", 10, "OK", "NU");
        });
    }
}