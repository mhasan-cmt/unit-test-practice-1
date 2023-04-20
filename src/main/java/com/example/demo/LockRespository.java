package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LockRespository extends CrudRepository<Lock, Long>{
}
