package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockController {

    @Autowired  
    LockRespository lockRepo;

    @GetMapping({"/", "/home"})
    Iterable<Lock> getLocks() {
        Iterable<Lock> locks = lockRepo.findAll();
        return locks;
    }

    @PostMapping(value = {"/add"}, produces = "application/json")
    ResponseEntity<Lock> addLock(@RequestBody Lock lock) {
        lockRepo.save(lock);
        return ResponseEntity.ok().build();
    }
    
}
