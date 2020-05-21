package com.lits.springboot.repository;

import com.lits.springboot.model.Teacher;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

    Teacher findOneById(Integer id);

    List<Teacher> findAll();

    List<Teacher> findAll(Sort sortByAge);

    Optional<Teacher> findById(Integer id);

    Optional<Teacher> findByPhone(String phone);
}
