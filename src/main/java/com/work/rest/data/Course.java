package com.work.rest.data;

import java.util.ArrayList;
import java.util.List;

// Class object that holds all the important course's data and participating students -> is stored as a folder with files when using "FileDatabaseService"

public class Course {
    private String courseName;
    private String courseType; //Is the course "Online"- or "ClassRoomCourse" etc.
    private String teacherName;
    private String className;
    private int courseId;
    private final String startDate;
    private final String endDate;
    private List<Student> courseStudents = new ArrayList<>();   //Lista kurssille ilmoittautuneista opiskelijoista

    // Constructor that sets all the important data when created

    public Course(String courseName, String courseType, String teacherName, String className, String startDate, String endDate) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.className = className;
        this.courseType = courseType;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public String getCourseName() {     // Returns the course name
        return this.courseName;
    }

    public List<Student> getCourseStudentsList() {      // Returns the list of participating students from the course
        return this.courseStudents;
    }
    public void setCourseStudentsList(List<Student> courseStudents) {      // Sets all the course students (necessary when reading text file data in "FileDatabaseService")
        this.courseStudents = courseStudents;
    }

    public void setId(int id) {      // Set the course id (unique)
        this.courseId = id;
    }

    public int getId() {       // Returns the course id
        return courseId;
    }

    public void AddStudentToCourse(Student student) {   // Add student to the course (List)

        if(!courseStudents.contains(student)){
            courseStudents.add(student);
            System.out.println(student.getFullName() + " added to "+courseName+" -course");
        }
        else
            System.out.println(student.getFullName() + " is already in "+courseName+" -course");

    }

    public void RemoveStudentFromCourse(Student student) {  // Removes student from the course (list)
        Long id = student.getId();

        for (Student s : courseStudents) {
            if(id.equals(Long.valueOf(s.getId()))) {
                courseStudents.remove(s);
                System.out.println(s.getFullName() + " removed from "+courseName+" -course");
                return;
            }
        }
        System.out.println(student.getFullName() + " was not found in the "+courseName+" -course");
    }
    public void setCourseName(String courseName) { // Set the course name
        this.courseName = courseName;
    }

    public String getCourseType() {         // Get course type -> "Online course" or ""
        return this.courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCourseId() {
        return this.courseId;
    }


    public String getStartDate() {
        return this.startDate;
    }


    public String getEndDate() {
        return this.endDate;
    }


}
