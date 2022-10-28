package com.work.rest.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;
import com.work.rest.data.Course;
import com.work.rest.data.Student;

// This service provides possibility to read data from the text files
// To mimic real database: this service reads and writes data to C://temp folder and does it by every method call

@Repository
public class FileDatabaseService { 
    private String studentPath = "C://temp//school_database//students//";   // Path where all the students are spawned
    private String coursePath = "C://temp//school_database//courses//";     // Path where all the course data is spawned

    private List<String> studentIDs = new ArrayList<>();    // Reads the ID's that are used in text files (avoid duplicants when creating new data)
    private List<Integer> courseIDs = new ArrayList<>();

    public FileDatabaseService() {
        checkDirectories();
        setAllIDLists();
    }

    private void checkDirectories() {      // Checks if directory exists and creates new if needed
        if (!Files.isDirectory(Path.of(studentPath))) {
            new File(studentPath).mkdirs();  // Create new file with the parent folders -> "absolute path"
            System.out.println("Path created: "+studentPath);
        }
        else 
            System.out.println("Paths exists: "+coursePath); // Tells that folder exists in console

        if (!Files.isDirectory(Path.of(coursePath))) {
            new File(studentPath).mkdirs();  // Create new file with the parent folders -> "absolute path"
            System.out.println("Path created: "+coursePath);
        }
        else 
            System.out.println("Paths exists: "+coursePath); // Tells that folder exists in console

    }

    public void setAllIDLists() {   // Sets all the ID's for the students and courses that are in use
    studentIDs = new ArrayList<>();
    try {
        File[] studentListOfFiles = new File(studentPath).listFiles(); // Create array list of files located in "students" -folder

        for (File file : studentListOfFiles) {
            Student student = convertStudentFileToList(file);           //Turn text file into data
            studentIDs.add(String.valueOf(student.getId()));            // Sets the id of the student
        }
        
    } catch (Exception e) {
        System.out.println("No students in database");
    }

    try {
        courseIDs = new ArrayList<>();
        File[] courseListOfFiles = new File(coursePath).listFiles();    // Create array list of folders located in "courses" -folder
    
        for (File file : courseListOfFiles) {                           // Loops all the course folders and gets the course text files
            String courseFile = file.getName();
            File courseData = new File(coursePath+courseFile+"//"+courseFile+".txt");
            Course course = convertCourseFileToList(courseData);        // Turn text file into data
            courseIDs.add(course.getId());      // Sets the id of the course
        }     
    } catch (Exception e) {
        System.out.println("No courses in database");
    }


    System.out.println("Student id's loaded: "+studentIDs);         // Shows all the student ID's in database
    System.out.println("Course id's loaded: "+courseIDs);           // Shows all the course ID's in database
}
 
    public void writeStudentToFileDatabase(Student student) { //Student is added to database for the first time
        try {
            int id = 1000;      
            do{                 // Loop for choosing student id that is not in database
                id++;
            }while(studentIDs.contains(String.valueOf(id)));
            studentIDs.add(String.valueOf(id));

            FileWriter fw = new FileWriter(studentPath+ id + "_" + student.getFullName() + ".txt"); // Writes text file to the "students" folder
            fw.write(student.getFname()+ System.lineSeparator());   // Write first line
            fw.write(student.getLname()+ System.lineSeparator());   // Write second line
            fw.write(student.getStudentClass()+ System.lineSeparator());    // Write third line
            student.setId(id);
            fw.write(id+ System.lineSeparator());
            fw.close(); // Close the created FileWriter -> avoids unneccessary data leakage
            
        } catch (IOException  ioe) { // Catches exception if file can't be read
            System.out.println(ioe); 
        }

    }

    public List<Student> readAllStudentsFromDatabaseFile() {    // Reads all the student text files in "students" folder
        List<Student> studentList = new ArrayList<>(); // Create list
        studentIDs = new ArrayList<>(); // Sets new id list

        File folder = new File(studentPath);        // Sets the "students" folder
        File[] listOfFiles = folder.listFiles();    // Creates array of all the files inside "students" folder  
  
        for (File file : listOfFiles) {     // Loops all the files
            Student student = convertStudentFileToList(file); // Converts student text file to student class object
            studentList.add(student);
            studentIDs.add(String.valueOf(student.getId())); // adds student id to the list -> avoids duplicants when creating new one
        }

        return studentList;     // Returns the text file content in "Student" class object as a list
    }

    // COURSES

    public void writeCourseToFile(Course course) { //When course is created for the first time
        try {
                if(course.getId()==0) { // Checks if course is created for the first time (course id are: 1,2,3,4,5... etc.)
                    int id = 1;
                    while(courseIDs.contains(id)) {
                        id++;
                    }
                    courseIDs.add(id); // Adds the new id in used list
                    course.setId(id); // Sets new id to the wanted course
                }

            String thisCoursePath = coursePath + course.getId() + "_" + course.getCourseName()+"//"; // Example: "C://temp//school_database//courses//1_Matikka//" -folder
            
            new File(thisCoursePath).mkdirs(); // Creates the folder path

            FileWriter fw = new FileWriter(thisCoursePath + course.getId() + "_" + course.getCourseName() + ".txt"); // Creates the "Course" text file inside the folder
            fw.write(course.getCourseName()+ System.lineSeparator());                                                // Example: "C://temp//school_database//courses//1_Matikka//1_Matikka.txt"
            fw.write(course.getCourseType()+ System.lineSeparator());                                                // 1_Matikka = 'course id' + "_" + 'course name'
            fw.write(course.getTeacherName()+ System.lineSeparator());
            fw.write(course.getClassName()+ System.lineSeparator());
            fw.write(course.getStartDate()+ System.lineSeparator());
            fw.write(course.getEndDate()+ System.lineSeparator());
            fw.write(course.getId()+ System.lineSeparator());          
            fw.close();

            if(!Files.isDirectory(Path.of(thisCoursePath + "students"))) // Create "students" folder inside of course folder if none exists
                new File(thisCoursePath + "students").mkdirs();          // Example: "C://temp//school_database//courses//1_Matikka//students

            List<Student> students = course.getCourseStudentsList();    // Set all the Course students to list
            if(students!=null) {
                for (Student student : students) {                      // Gets students 1 by 1 and writes them in their own files
                    FileWriter fw2 = new FileWriter(thisCoursePath + "students//" + student.getFullName() + ".txt");
                    fw2.write(student.getFname()+ System.lineSeparator());
                    fw2.write(student.getLname()+ System.lineSeparator());
                    fw2.write(student.getStudentClass()+ System.lineSeparator());
                    fw2.write(student.getId()+ System.lineSeparator());
                    fw2.close();
                }

            }
            
        } catch (IOException  ioe) {
            System.out.println(ioe);
        }
    }

    public List<Course> readAllCoursesFromFile() { //Reads all the courses in file with the participating students 
        List<Course> courseList = new ArrayList<>(); 
        courseIDs = new ArrayList<>();

        File courseFolder = new File(coursePath);               // Create file of the course folder path
        File[] courseListOfFiles = courseFolder.listFiles();    // Makes array list of all the files inside the "courses" -folder

        for (File file : courseListOfFiles) {                   // Read all the course folders and add them to a list (Course)
            String courseFile = file.getName();
            File courseData = new File(coursePath+courseFile+"//"+courseFile+".txt");
            Course course = convertCourseFileToList(courseData);
            courseList.add(course); // Convert file to "Course" class object
            courseIDs.add(course.getId());
            System.out.println(courseData);
        }

        return courseList;

    }

    // Cheaper way to add new student to course (no need to rewrite the full course when making changes)
    public void writeStudentToCourseFile(Course course, Student student, boolean write) { // write(true) or delete(false) student to wanted course
                                                                                          // 
        try {
            File[] courseListOfFiles = new File(coursePath).listFiles();
            String courseName = course.getCourseId()+"_"+ course.getCourseName();
    
            for (File file : courseListOfFiles) {
                if(file.getName().equalsIgnoreCase(courseName)) {
                    String fileRoot = file.getParentFile()+"//"+ courseName +"//students//"+student.getId()+"_"+student.getFullName()+".txt";
                    if(write) // When true -> write the Student data to a file
                    {
                        FileWriter fw = new FileWriter(fileRoot);
                        fw.write(student.getFname()+ System.lineSeparator());
                        fw.write(student.getLname()+ System.lineSeparator());
                        fw.write(student.getStudentClass()+ System.lineSeparator());
                        fw.write(student.getId()+ System.lineSeparator());
                        fw.close();
                    }
                    else { // When false -> delete the Student data from file
                        File delStudent = new File(fileRoot);
                        System.out.println(delStudent+" was removed from the course "+course.getCourseName());
                        delStudent.delete();
                        if(delStudent.exists()) {
                            delStudent.delete(); }
                    }
                    break;
                }
            }

        } catch (IOException  ioe) {
            System.out.println(ioe);
        }

    }

    // DATA CONVERTERS ->

    private Student convertStudentFileToList(File file) { // Convert Student class object to text file

        try{
            Scanner sc = new Scanner(file); // Create scanner that scans the text file
            String fname = sc.nextLine();   // Read first line from the text file
            String lname = sc.nextLine();   // Read second line etc.
            String studentClass = sc.nextLine();
            String studentId = sc.nextLine();
            sc.close(); // Close scanner to unnecessary prevent data leakage

            Student student = new Student(fname, lname, studentClass);
            student.setId(Integer.parseInt(studentId));
            return student;

        }catch(IOException ioe){ // Sends IO exception if text file can't be read
            System.out.println(ioe);
            return null;
        }

    }

    private Course convertCourseFileToList(File file) { // Converts Course (class object) to text file
        try {
            Scanner sc = new Scanner(file); // Create scanner that scans the text file
            String courseName = sc.nextLine();
            String courseType = sc.nextLine();
            String teacherName = sc.nextLine();
            String className = sc.nextLine();
            String startDate = sc.nextLine();
            String endDate = sc.nextLine();
            String courseId = sc.nextLine();
            sc.close();

            Course course = new Course(courseName, courseType, teacherName, className, startDate, endDate); // Create course file with data for the constructor
            course.setId(Integer.parseInt(courseId)); // Converts string value to int
    
            List<Student> students = new ArrayList<>(); // Sets students list

            File studentsFolder = new File(file.getParent() +"//students//"); // Example: "C://temp//school_database//courses//1_Matikka//students//
            File[] studentListOfFiles = studentsFolder.listFiles();           // Makes array of the files inside the "students" -folder  
                
            if(studentListOfFiles!=null) {                      // Checks if the "students" -folder is not empty
                for (File studentFile : studentListOfFiles) {   // Loop -> checks every text file and converts them in to "Students" class object
                    students.add(convertStudentFileToList(studentFile)); 
                }
            }
            
            course.setCourseStudentsList(students);             // Adds students to course class object
            return course; // Returns the course with all the data

        }catch(IOException ioe){
            System.out.println(ioe);
            return null;
        }
    }

}

