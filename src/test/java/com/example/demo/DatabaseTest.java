package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class DatabaseTest {

    @Autowired
    private LockRespository repository;

    private static final Lock lock = new Lock();


    @AfterEach
    public void destroyAll() {
        repository.deleteAll();
    }

    @BeforeEach
    void initUseCase() {
        lock.setId(1L);
        lock.setName("test lock");
        lock.setCombo1(12345);
    }

    @Test
    void testLockSaving() {
        // Save the Lock object to the mock repository
        repository.save(lock);

        // Retrieve the Lock object from the mock repository
        Lock savedLock = repository.findById(1L).orElse(null);

        // Assert that the retrieved Lock object matches the original Lock object
        assertNotNull(savedLock);
        assertEquals("test lock", savedLock.getName());
        assertEquals(12345, savedLock.getCombo1());
    }

}
