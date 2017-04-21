/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.service;

import cat.proven.apiexample.dao.DAOFactory;
import cat.proven.apiexample.dao.StudentDAO;
import cat.proven.apiexample.entities.Course;
import cat.proven.apiexample.entities.Student;

import java.util.Collection;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

public class StudentService {
    
    public StudentDAO studentDAO;
    
    public StudentService () {
        studentDAO = DAOFactory.getInstance().createStudentDAO();
    }
    
    public Student getStudentById(int idStudent) {
        Student student = studentDAO.findById(idStudent);
        return student;
    }
    
    public Student addStudent(Student student) {
        Student s = studentDAO.add(student);
        return s;
    }
    
    public Student updateStudent(Student student) {
        Student s = studentDAO.update(student);
        return s;
    }
    
    public int deleteStudent(Student student) {
        int result = studentDAO.delete(student);
        return result;
    }
    
    public Collection<Student> getStudents() { 
        Collection<Student> students = studentDAO.findAll();
        return students;
    }
    
    public Collection<Student> getStudentsByIdCourse(int idCourse) {
        Collection<Student> students = studentDAO.findByIdCourse(idCourse);
        return students;
    }
    
    public Collection<Course> getStudentCourses(int idStudent) {
        CourseService courseService = new CourseService();
        Collection<Course> courses = courseService.getCoursesByIdStudent(idStudent);
        return courses;
    }

    // http://stackoverflow.com/questions/6324547/how-to-handle-many-to-many-relationships-in-a-restful-api
    public Course addStudentToCourse(Student student, Course course) {       
        return studentDAO.addStudentToCourse(student, course);
    }
    
}
