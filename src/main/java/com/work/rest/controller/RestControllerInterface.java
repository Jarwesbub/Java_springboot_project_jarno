package com.work.rest.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.rest.data.Course;
import com.work.rest.data.Student;

@RestController // Spring boot's own RestController which is linked to "ProjectAplication.java"
@RequestMapping("")
public interface RestControllerInterface {       // This is an interface which works in harmony with Controller

    //Root = http://localhost:8080/

    @PostMapping("students/add")                            // Adds student in database with POST command
    public void CreateStudent(@RequestBody Student student);

    @GetMapping("students/all")                             // Returns all the students located in the database
    List<Student> GetAllStudentsList();                     

    @GetMapping("students/{studentId}")                     //Example: "http://localhost:8080/students/1001" -> Finds student with an id of 1001
    public Student GetStudentById(@PathVariable Long studentId);

    @PostMapping("courses/add")                             // Adds new course in the database
    public void CreateCourse(@RequestBody Course course);

    @GetMapping("courses/all")                              // Returns list of all the courses
    List<Course> GetAllCourses();

    @GetMapping("courses/{courseId}")                       // Finds the course info by id
    public Course GetCourse(@PathVariable Long courseId);   // Example "http://localhost:8080/courses/1"

    @GetMapping("courses/{courseId}/addstudent={studentId}")        // Adds student to course by both id's -> "http://localhost:8080/courses/0/addstudent=1001"
    public void AddStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId);

    @GetMapping("courses/{courseId}/removestudent={studentId}")     // Removes a student from the course by id -> "http://localhost:8080/courses/0/removestudent=1001"
    public void RemoveStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId);
}
