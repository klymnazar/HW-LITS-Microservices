package com.lits.springboot.service.impl;

import com.lits.springboot.dto.StudentDto;
import com.lits.springboot.exceptions.student.StudentNotFoundException;
import com.lits.springboot.exceptions.student.StudentRequestException;
import com.lits.springboot.model.Student;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.StudentRepository;
import com.lits.springboot.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDto create(String firstName, String lastName, Integer age) {
        Student student;
        student = studentRepository.save(new Student(firstName, lastName, age));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAll() {
        return studentRepository.findAll().stream().map(student -> modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDto update(StudentDto newStudentDto) {
       Student newStudent = modelMapper.map(newStudentDto, Student.class);
       return modelMapper.map(studentRepository.save(newStudent), StudentDto.class);
    }

    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto getOne(Integer id) {
        Student student;
        if (id == null) {
            throw new StudentRequestException("Enter Student id");
        } else {
            student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(format("Student with id : %d doesn't exist", id)));
            return modelMapper.map(student, StudentDto.class);
        }
    }

    @Override
    public List<StudentDto> getAll(String sortBy) {
        return null;
    }

}
