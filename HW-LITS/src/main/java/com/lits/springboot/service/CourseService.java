package com.lits.springboot.service;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.model.Course;

import java.util.List;

public interface CourseService {

    CourseDto create(CourseDto courseDto);
    CourseDto getOne(Integer id);
    CourseDto update(Integer id, String newCourseName);
    void delete(Integer id);
    List<CourseDto> getAll();
    List<String> getAllCoursesWithoutTeacher();
    Course updateCourseTeacher(Integer courseId, Integer teacherId);
    CourseDto addTeachersToCourse(Integer courseId, List<Integer> teacherIds);
    List<CourseDto> getAllCourses(String type, Integer numberMonth);

    CourseDto addStudentsToCourse(Integer courseId, List<Integer> studentIds);
    CourseDto removeStudentsFromCourse(Integer courseId, List<Integer> studentIds);
    List<CourseDto> getAllCourseByStudent(Integer studentId);
    List<CourseDto> getAllCourseByStudentAndTeacher(Integer studentId, Integer teacherId);
}
