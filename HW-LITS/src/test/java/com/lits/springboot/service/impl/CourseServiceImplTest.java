package com.lits.springboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lits.springboot.dto.CourseDto;
import com.lits.springboot.exceptions.course.CourseCreateException;
import com.lits.springboot.exceptions.course.CourseNotFoundException;
import com.lits.springboot.exceptions.course.CourseRequestException;
import com.lits.springboot.model.Course;
import com.lits.springboot.repository.CourseRepository;
import com.lits.springboot.repository.StudentRepository;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.CourseService;
import com.lits.springboot.utils.ParseDataUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private CourseDto courseDto;
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    @Before
    public void init() {
        courseService = new CourseServiceImpl(courseRepository, teacherRepository, studentRepository, modelMapper);
    }

    @Test
    public void create_course_courseDto() throws IOException {
        //Arrange
        CourseDto courseDto = ParseDataUtils
                .prepareData("unit/serviceImpl/course/create/positive_data.json", new TypeReference<>() {});
        Course course = ParseDataUtils
                .prepareData("unit/serviceImpl/course/create/positive_data.json", new TypeReference<>() {});
        CourseDto expected = ParseDataUtils
                .prepareData("unit/serviceImpl/course/create/result.json", new TypeReference<>() {});
        when(courseRepository.save(course)).thenReturn(course);

        //Act
        CourseDto actual = courseService.create(courseDto);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void update_newCourseName_courseDto() throws IOException {
        //Arrange
        CourseDto courseDto = ParseDataUtils
                .prepareData("unit/serviceImpl/course/update/positive_data.json", new TypeReference<>() {});
        Course course = ParseDataUtils
                .prepareData("unit/serviceImpl/course/update/positive_data.json", new TypeReference<>() {});
        CourseDto expected = ParseDataUtils
                .prepareData("unit/serviceImpl/course/update/result.json", new TypeReference<>() {});
        when(courseRepository.findOneById(eq(1))).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        //Act
        CourseDto actual = courseService.update(1, "Java Advance");

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void delete() throws IOException {
        //Arrange
        doNothing().when(courseRepository).deleteById(eq(1));

        //Act
        courseService.delete(1);

        //Assert
        verify(courseRepository, times(1)).deleteById(eq(1));
    }

    @Test
    public void getAll_Courses() throws IOException {
        //Arrange
        List<CourseDto> courseDtos = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getAll/positive_data.json", new TypeReference<>() {});
        List<Course> courses = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getAll/positive_data.json", new TypeReference<>() {});
        List<CourseDto> expected = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getAll/result.json", new TypeReference<>() {});
        when(courseRepository.findAll()).thenReturn(courses);

        //Act
        List<CourseDto> actual = courseService.getAll();

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void getOne_courseId_course() throws IOException {
        //Arrange
        CourseDto courseDto = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getOne/positive_data.json", new TypeReference<>() {});
        Course course = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getOne/positive_data.json", new TypeReference<>() {});
        CourseDto expected = ParseDataUtils
                .prepareData("unit/serviceImpl/course/getOne/result.json", new TypeReference<>() {});
        when(courseRepository.findById(eq(1))).thenReturn(Optional.of(course));

        //Act
        CourseDto actual = courseService.getOne(1);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void create_nullCourseName_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName(null);
        courseDto.setStartDate(now);
        courseDto.setEndDate(now.plusMonths(2));
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void create_nullStartDate_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Java");
        courseDto.setStartDate(null);
        courseDto.setEndDate(now.plusMonths(2));
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void create_nullEndDate_CreateException() {
        //Arrange
//        init();
        LocalDate now = LocalDate.now();
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Java");
        courseDto.setStartDate(now);
        courseDto.setEndDate(null);
        //Act
        Throwable thrown = catchThrowable(() -> courseService.create(courseDto));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseCreateException.class);
    }

    @Test
    public void getOne_nullId_RequestException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> courseService.getOne(null));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseRequestException.class);
    }

    @Test
    public void getOne_negativeId_NotFoundException() {
        //Arrange
//        init();
        //Act
        Throwable thrown = catchThrowable(() -> courseService.getOne(-1));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(CourseNotFoundException.class);
    }

}