package com.work.rest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.work.rest.data.Course;
import com.work.rest.data.Student;
import com.work.rest.services.SchoolService;

//This is an controller which implements the RestControllerInterface and overrides the methods
//Sends a call for the SchoolService which is linked to FileDatabaseService

@Controller
public class MainController implements RestControllerInterface {   //Luokka toteuttaa RestControllerInterface rajapinnan

    @Autowired // Wires SchoolService which is located in the "@Service group" (beans)
    SchoolService schoolService; // Creates autowired object which gives easy access to the class properties (Spring Boot support)

    @Override // Overrides the method in the RestControllerInterface
    public void CreateStudent(Student student) {
        schoolService.CreateStudent(student);

    }

    @Override
    public List<Student> GetAllStudentsList() {
        return schoolService.getAllStudentsList();
    }

    @Override
    public Student GetStudentById(Long studentId) {
    return schoolService.getStudentById(studentId);
    }


    @Override
    public void CreateCourse(Course course) {
        schoolService.CreateCourse(course);

    }

    @Override
    public List<Course> GetAllCourses() {
        return schoolService.getAllCoursesList();
    }

    @Override
    public Course GetCourse(Long courseId) {      
        return schoolService.GetCourse(courseId);
    }

    @Override
    public void AddStudentToCourse(Long courseId, Long studentId) { 

        schoolService.AddStudentToCourse(courseId, studentId);
    }

    @Override
    public void RemoveStudentFromCourse(Long courseId, Long studentId) {  
        schoolService.RemoveStudentFromCourse(courseId, studentId);
    }

}
