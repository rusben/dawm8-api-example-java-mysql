package cat.proven.entities;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMS
 */
@XmlRootElement
public class Student {

    private int id;    
    private String name;
    private String lastname;    
    private ArrayList<Course> courses;

    public Student(){
        this.courses = new ArrayList<Course>();
    }
    
    public Student(int id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.courses = new ArrayList<Course>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }    
    
}
