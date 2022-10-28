package com.work.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.rest.data.Course;
import com.work.rest.data.Student;

// This service works as a bridge between MainController and FileDatabaseService (which is used as a "database")
// Console messages are included for easier debugging and code testing

@Service // Sets class in services "group" which can be accessed easily with @Autowired system
public class SchoolService { //Kuvitteellinen tietokantapalvelu, jonne tallentuu kaikki tiedot opiskelijoista ja kursseista
    
    @Autowired // Wires FileDatabaseService to object "database" which supports Spring boot hierarchy
    FileDatabaseService database; // Roots are located in local drive: C://temp//school_database

    public void addStudentToDatabase(Student student) { // Creates text file of student in C://temp//school_database//students
        database.writeStudentToFileDatabase(student);
    }

    public void addCourseToDatabase(Course course) {        // Creates course folder in C://temp//school_database//courses//
        database.writeCourseToFile(course);                 // Folder contains text file of course data and folder of students
                                                            // Examples: C:\temp\school_database\courses\1_Matikka\students\   ,  C:\temp\school_database\courses\1_Matikka\1_Matikka.txt
    }

    public List<Student> getAllStudentsList() {                 // Returns all the students in C://temp//school_database//students -folder
        return database.readAllStudentsFromDatabaseFile();      // Example: C://temp//school_database//students
    }

    public List<Course> getAllCoursesList() {                   // Returns all the courses and shows students in the course
        return database.readAllCoursesFromFile();               

    }

    public void CreateStudent(Student student) {        // Creates student in the database folder C://temp//school_database//students//
        database.writeStudentToFileDatabase(student);
        System.out.println("New student created: "+student.getFname()+" "+student.getLname());
            
    }

    public Student getStudentById(Long studentId) {     // Returns student if the id matches
        List<Student>studentList = database.readAllStudentsFromDatabaseFile();
        for (Student student : studentList) {
            if(Long.valueOf(studentId).equals(Long.valueOf(student.getId()))) { 
                System.out.println(studentId + " student found!");
                return student;
            }
        }
        System.out.println(studentId + " student not found");
        return null;  
    }

    //Course data

    public void CreateCourse(Course course) {       // Creates new course in the database
        database.writeCourseToFile(course);
        System.out.println(course.getCourseName()+" course created!");

    }

    public List<Course> GetAllCourses() {           //Returns every course in the database   
        System.out.println("Showing all courses");
        return database.readAllCoursesFromFile();
    }

    public Course GetCourse(Long courseId) {        //Returns course by id
        List<Course> courseList = database.readAllCoursesFromFile();
        for (Course course : courseList) {
            
            if(Long.valueOf(courseId).equals(Long.valueOf(course.getId()))) {
                return course;
            }
        }
        System.out.println(courseId + " course not found");
        return null;
    }

    public void AddStudentToCourse(Long courseId, Long studentId) {                            //Creates student text file to the wanted course (by id's)
        List<Course> courseList = database.readAllCoursesFromFile();
        for (Course c : courseList) {
            if(Long.valueOf(courseId).equals(Long.valueOf(c.getId()))) {                       // Checks if the course id's matches     
                List<Student>studentList = database.readAllStudentsFromDatabaseFile();
                for (Student student : studentList) { 
                    if(Long.valueOf(studentId).equals(Long.valueOf(student.getId()))) {        // Checks if the student id's matches
                        c.AddStudentToCourse(student);// Adds student in the code (not necessary with current build)
                        database.writeStudentToCourseFile(c, student, true);
                        return; // Returns with success
                    }   
                }
                System.out.println("Student by id: " + studentId +" was not in found in the data!");
                return; // Student was not found
            }
        }
        System.out.println("Course by id: " + courseId + " was not in found in the data!");
        return; // Course was not found
    }

    public void RemoveStudentFromCourse(Long courseId, Long studentId) {                    // Removes student from the course (by id's)
        List<Course> courseList = database.readAllCoursesFromFile();
        for (Course c : courseList) {
            if(Long.valueOf(courseId).equals(Long.valueOf(c.getId()))) {                    // Checks if the course id's matches
                List<Student>studentList = database.readAllStudentsFromDatabaseFile();
                for (Student student : studentList) { 
                    if(Long.valueOf(studentId).equals(Long.valueOf(student.getId()))) {     // Checks if the student id's matches
                        c.RemoveStudentFromCourse(student); // Removes student in the code (not necessary with current build)
                        database.writeStudentToCourseFile(c, student, false);
                        return;   // Returns with success
                    }   
                }
                System.out.println("Student by id: " + studentId +" was not in found in the data!");
                return; // Student was not found
            }
        }
        System.out.println("Course by id: " + courseId + " was not in found in the data!");
        return; // Course was not found
        
    }


}
