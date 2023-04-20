package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LockIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LockRespository lockRepo;

    @Test
    public void testAddLock() throws Exception {
        Lock lock = new Lock();
        lock.setId(1L);
        lock.setName("Lock 1");
        lock.setCombo1(1234);
        when(lockRepo.save(Mockito.any(Lock.class))).thenReturn(lock);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/add")
                                .content(asJsonString(lock))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testGetLocks() throws Exception {
        Lock lock1 = new Lock();
        lock1.setId(1L);
        lock1.setName("Lock 1");
        lock1.setCombo1(1234);

        Lock lock2 = new Lock();
        lock2.setId(2L);
        lock2.setName("Lock 2");
        lock2.setCombo1(5678);

        List<Lock> locks = Arrays.asList(lock1, lock2);

        when(lockRepo.findAll()).thenReturn(locks);

        mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Lock 1")))
                .andExpect(jsonPath("$[0].combo1", is(1234)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Lock 2")))
                .andExpect(jsonPath("$[1].combo1", is(5678)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
