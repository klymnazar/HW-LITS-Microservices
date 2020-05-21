package com.lits.springboot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lits.springboot.dto.TeacherDto;
import com.lits.springboot.model.Teacher;
import com.lits.springboot.service.TeacherService;
import com.lits.springboot.utils.ParseDataUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherService teacherService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll_unsort_returnTeachers() throws Exception {
        List<TeacherDto> teacherDtos = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {});
        List<Teacher> expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {});
        when(teacherService.getAll()).thenReturn(teacherDtos);

        String result = this.mockMvc.
                perform(MockMvcRequestBuilders.get("/teachers"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Teacher> actual = objectMapper.readValue(result, new TypeReference<>() {
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_sortByAge_returnTeachers() throws Exception {
        String sortBy = "age";

        List<TeacherDto> teacherDtos = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {});
        List<Teacher> expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getAll/positive_data.json", new TypeReference<>() {});
        when(teacherService.getAll(sortBy)).thenReturn(teacherDtos);

        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teachers?sortBy=age"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Teacher> actual = objectMapper.readValue(result, new TypeReference<>() {
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getById_id_returnTeacher() throws Exception {
        Integer id = 1;

        TeacherDto teacherDto = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getOne/positive_data.json", new TypeReference<>() {});
        Teacher expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/getOne/positive_data.json", new TypeReference<>() {});
        when(teacherService.getOne(eq(id))).thenReturn(teacherDto);

        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/teacher/" + id))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Teacher actual = objectMapper.readValue(result, new TypeReference<>() {
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_name_returnTeacher() throws Exception {
        Integer id = 1;
        TeacherDto teacherDto = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/update/firstName/positive_data.json", new TypeReference<>() {});
        Teacher expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/update/firstName/positive_data.json", new TypeReference<>() {});
        when(teacherService.update(id, "John", "Smith")).thenReturn(teacherDto);

        URI fileName = getClass().getClassLoader()
                .getResource("unit/serviceImpl/teacher/update/firstName/positive_data.json")
                .toURI();
        String content = Files.readString(Paths.get(fileName));

        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/teacher/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Teacher actual = objectMapper.readValue(result, new TypeReference<>() {});

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_teacher() throws Exception {
        Integer id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/" + id)).andExpect(status().isAccepted());
    }

    @Test
    public void create_teacher_returnTeacher() throws Exception {

        TeacherDto teacherDto = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/create/positive_data.json", new TypeReference<>() {});
        Teacher expected = ParseDataUtils
                .prepareData("unit/serviceImpl/teacher/create/positive_data.json", new TypeReference<>() {});
        when(teacherService.create("Donald", "Duck", 30, "+380979766297")).thenReturn(teacherDto);

        URI fileName = getClass().getClassLoader()
                .getResource("unit/serviceImpl/teacher/create/positive_data.json")
                .toURI();
        String content = Files.readString(Paths.get(fileName));

        String result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/teacher")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Teacher actual = objectMapper.readValue(result, new TypeReference<>() {
        });

        Assert.assertEquals(expected, actual);
    }

}
