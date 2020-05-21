package com.lits.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "FirstName is not valid. Enter firstName.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "LastName is not valid. Enter lastName.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @DecimalMin(value = "16", message = "Age is not valid. Enter age greater 16")
    @Column(name = "age", nullable = false)
    private Integer age;

    private String phone;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_course", joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Course> courseList;

    public Teacher(String firstName, String lastName, Integer age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }
}