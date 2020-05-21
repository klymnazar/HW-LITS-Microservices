CREATE TABLE course_student(
    course_id INT(11) NOT NULL,
    student_id INT(11) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (student_id) REFERENCES student(id)
);