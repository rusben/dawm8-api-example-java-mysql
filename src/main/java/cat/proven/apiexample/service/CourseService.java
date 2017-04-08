/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.service;

import cat.proven.apiexample.dao.CourseDAO;
import cat.proven.apiexample.dao.DAOFactory;

import cat.proven.apiexample.entities.Course;
import cat.proven.apiexample.entities.Student;

import java.util.Collection;

public class CourseService {
    
    public CourseDAO courseDAO;
    
    public CourseService () {
        courseDAO = DAOFactory.getInstance().createCourseDAO();
    }
    
    public Course getCourseById(int idCourse) {
        Course c = courseDAO.findById(idCourse);
        return c;
    }
   
    public Course addCourse(Course course) {
        Course c = courseDAO.add(course);
        return c;
    }
    
    public Course updateCourse(Course course) {
        Course c = courseDAO.update(course);
        return c;
    }
    
    public int deleteCourse(Course course) {
        int result = courseDAO.delete(course);
        return result;
    }
    
    public Collection<Course> getCourses() { 
        Collection<Course> courses = courseDAO.findAll();
        return courses;
    }

    public Collection<Course> getCoursesByIdStudent(int idStudent) {
        Collection<Course> courses = courseDAO.findByIdStudent(idStudent);
        return courses;
    }
    
    public Collection<Student> getCourseStudents(int idCourse) {
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentsByIdCourse(idCourse);
        return students;
    }
    
}
