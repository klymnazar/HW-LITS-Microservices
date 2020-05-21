package com.lits.springboot.repository;

import com.lits.springboot.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findOneById(Integer id);

    List<Student> findAll();

    Optional<Student> findById(Integer id);

}
