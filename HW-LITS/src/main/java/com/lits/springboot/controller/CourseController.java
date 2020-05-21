package com.lits.springboot.controller;

import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.service.StudentService;
import com.lits.springboot.service.TeacherService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Log
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/course/{courseId}")
    public CourseDto getById(@PathVariable(name = "courseId") Integer courseId) {
        log.info("Get course by courseId = " + courseId);
        return courseService.getOne(courseId);
    }

    @GetMapping
    public List<CourseDto> getAllCourses(@RequestParam(name = "durationType", required = false) String durationType,
                                         @RequestParam(name = "durationMonths", required = false) Integer durationMonths) {
        log.info("Get all courses");
        return courseService.getAllCourses(durationType, durationMonths);
    }

    @GetMapping("/student/{studentId}")
    public List<CourseDto> getAllCoursesByStudent(@PathVariable(name = "studentId") Integer studentId) {
        log.info("Get all courses by Student");
        return courseService.getAllCourseByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/count")
    public Integer countAllCoursesByStudent(@PathVariable(name = "studentId") Integer studentId) {
        log.info("Count all courses by Student");
        return courseService.getAllCourseByStudent(studentId).size();
    }

    @GetMapping("/student/{studentId}/teacher/{teacherId}")
    public List<CourseDto> getAllCoursesByStudentAndTeacher(@PathVariable(name = "studentId") Integer studentId, @PathVariable(name = "teacherId") Integer teacherId) {
        log.info("Get all courses by Student and Teacher");
        return courseService.getAllCourseByStudentAndTeacher(studentId, teacherId);
    }



    @PutMapping("/course/{courseId}")
    public CourseDto update(@PathVariable(name = "courseId") Integer courseId, @RequestBody CourseDto courseDto) {
        log.info("Update course by courseId = " + courseId);
        return courseService.update(courseId, courseDto.getCourseName());
    }

    @PutMapping("/course/{courseId}/students")
    public CourseDto addStudents(@PathVariable(name = "courseId") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> studentIds = new ArrayList<>();
        for (Integer studentId : courseDto.getStudentIds()) {
            studentIds.add(studentService.getOne(studentId).getId());
        }
        log.info("Add students to course");
        return courseService.addStudentsToCourse(courseId, studentIds);
    }

    @PutMapping("/course/{courseId}/teachers")
    public CourseDto addTeachers(@PathVariable(name = "courseId") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> teacherIds = new ArrayList<>();
        for (Integer teacherId : courseDto.getTeacherIds()) {
            teacherIds.add(teacherService.getOne(teacherId).getId());
        }
        log.info("Add teachers to course");
        return courseService.addTeachersToCourse(courseId, teacherIds);
    }



    @PostMapping("/course")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseDto create(@RequestBody CourseDto courseDto) {
        log.info("Create new course");
        return courseService.create(courseDto);
    }



    @DeleteMapping("/course/{courseId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable(name = "courseId") Integer courseId) {
        log.info("Delete course by courseId = " + courseId);
        courseService.delete(courseId);
    }

    @DeleteMapping("/course/{courseId}/students")
    public CourseDto removeStudents(@PathVariable(name = "courseId") Integer courseId, @RequestBody CourseDto courseDto) {
        List<Integer> studentIds = new ArrayList<>();
        for (Integer studentId : courseDto.getStudentIds()) {
            studentIds.add(studentService.getOne(studentId).getId());
        }
        log.info("Remove student from course");
        return courseService.removeStudentsFromCourse(courseId, studentIds);
    }
}
