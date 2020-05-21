package com.lits.springboot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.exceptions.teacher.TeacherCreateException;
import com.lits.springboot.exceptions.teacher.TeacherNotFoundException;
import com.lits.springboot.exceptions.teacher.TeacherRequestException;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.repository.TeacherRepository;
import com.lits.springboot.service.TeacherService;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceImplTest {

//    private TeacherDto actual;
    private TeacherService teacherService;
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private ModelMapper modelMapper;

    @Before
    public void init() {
        teacherService = new TeacherServiceImpl(teacherRepository, modelMapper);
    }

    @Test
    public void update_newFirstName_teacherDto() throws IOException {
        //Arrange
        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/firstName/positive_data.json", new TypeReference<>() {
        });
        TeacherDto expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/firstName/result.json", new TypeReference<>() {
        });

        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        //Act
        TeacherDto actual = teacherService.update(1, "Donald", "Smith");
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void update_newLastName_teacherDto() throws IOException {
        //Arrange
        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/lastName/positive_data.json", new TypeReference<>() {
        });
        TeacherDto expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/update/lastName/result.json", new TypeReference<>() {
        });

        when(teacherRepository.findOneById(eq(1))).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        //Act
        TeacherDto actual = teacherService.update(1, "John", "Duck");
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void getOne_teacherId_teacher() throws IOException {
        //Arrange
        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getOne/positive_data.json", new TypeReference<>() {
        });
        TeacherDto expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getOne/result.json", new TypeReference<>() {
        });

        when(teacherRepository.findById(eq(1))).thenReturn(Optional.of(teacher));

        //Act
        TeacherDto actual = teacherService.getOne(1);
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void create_teacher_teacherDto() throws IOException {
        //Arrange
        Teacher teacher = ParseDataUtils.prepareData("unit/serviceImpl/teacher/create/positive_data.json", new TypeReference<>() {
        });
        TeacherDto expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/create/result.json", new TypeReference<>() {
        });

        when(teacherRepository.save(new Teacher(teacher.getFirstName(), teacher.getLastName(), teacher.getAge(), teacher.getPhone()))).thenReturn(new Teacher(teacher.getFirstName(), teacher.getLastName(), teacher.getAge(), teacher.getPhone()));

        //Act
        TeacherDto actual = teacherService.create(teacher.getFirstName(), teacher.getLastName(), teacher.getAge(), teacher.getPhone());
//        Teacher actual = modelMapper.map(teacherDto, Teacher.class);

        //Assert
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

//    @Test
//    public void getAll_sortByAge_sortTeachers() throws IOException {
//        //Arrange
//        List<Teacher> teachers = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {});
//        List<TeacherDto> expected = ParseDataUtils.prepareData("unit/serviceImpl/teacher/getAll/result.json", new TypeReference<>() {});
//
//        String sortBy = "age";
//        Sort sortByAge = Sort.by(sortBy).descending();
//        when(teacherRepository.findAll(sortByAge)).thenReturn(teachers);
//
//        //Act
//        List<TeacherDto> actual = teacherService.getAll(sortBy);
//
//        //Assert
//        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
//    }

    @Test
    public void delete() throws IOException {
        //Arrange
        doNothing().when(teacherRepository).deleteById(eq(1));

        //Act
        teacherService.delete(1);

        //Assert
        verify(teacherRepository, times(1)).deleteById(eq(1));
    }


    @Test
    public void getOne_nullId_RequestException() {
        //Arrange
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.getOne(null));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherRequestException.class);
    }

    @Test
    public void getOne_negativeId_NotFoundException() {
        //Arrange
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.getOne(-1));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherNotFoundException.class);
    }

    @Test
    public void create_nullFirstName_CreateException() {
        //Arrange
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.create(null, "Smith", 21, "+380979766297"));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
    }

    @Test
    public void create_nullLastName_CreateException() {
        //Arrange
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.create("John", null, 21, "+380979766297"));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
    }

    @Test
    public void create_nullAge_CreateException() {
        //Arrange
        //Act
        Throwable thrown = catchThrowable(() -> teacherService.create("John", "Smith", null, "+380979766297"));
        //Assert
        Assertions.assertThat(thrown).isInstanceOf(TeacherCreateException.class);
    }


}