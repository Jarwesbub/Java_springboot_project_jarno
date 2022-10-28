package com.work.rest.data;

// Class object that holds all the important student's data -> is stored as a text file when using "FileDatabaseService"

public class Student {
    private String fname;
    private String lname;
    private String studentClass;
    private long id;

    // Constructor which sets all the necessary student data
    public Student(String fname, String lname, String studentClass) {

        this.fname = fname;
        this.lname = lname;
        this.studentClass = studentClass;
    }

    public String getFname() {      // Get first name
        return this.fname;
    }

    public String getLname() {      // Get last name
        return this.lname;
    }

    public String getFullName() {   // Get the full name
        return this.fname + " " + this.lname;
    }

    public void setId(long id) {    // Set student id (unique)
        this.id = id;
    }

    public long getId() {   // Get student id
        return this.id;
    }

    public String getStudentClass(){    // Get student's class
        return studentClass;
    }
}
