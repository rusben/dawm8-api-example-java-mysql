
package cat.proven.services;

import cat.proven.entities.Course;
import cat.proven.entities.Student;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author AMS
 */
public class CourseService {
    
    ArrayList<Course> db;
    
    public CourseService(){
        db = new ArrayList<Course>();
        
        Course[] arrCourses = new Course[] {
          new Course(0, "DAM-1"),
          new Course(0, "DAM-2"),
          new Course(0, "DAW-1"),
          new Course(0, "DAW-2"),
          new Course(0, "ASIX-1"),
          new Course(0, "ASIX-2")
        };
        
        for (Course course : arrCourses) {
            this.add(course);
        }
        
    }
    
    public Course add(Course c) {
        //Cerquem el màxim id per incrementar-lo en 1
        int idMax = 0;
        for (Course course : db) {
            if(idMax < course.getId()) idMax = course.getId();
        }
        c.setId(idMax + 1);
        db.add(c);
        return c;      
    }
    
    public Collection<Course> findAll(){
        return db;
    }
    
    public Course findById(int id){
        for(Course c : db){
            if(c.getId() == id) return c;
        }
        
        return null;
    }
    
    public void remove(int id){
        
    }
    
    public void remove(Course c){
        
    }
    
}
