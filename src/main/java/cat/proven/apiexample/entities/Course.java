package cat.proven.apiexample.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMS
 */
@XmlRootElement
public class Course {
    private int id;
    private String name;
    private List<Student> students;

    public Course() {
        this.students = new ArrayList<Student>();
    }

    public Course(int id, String name) {
        this();
        this.id = id;
        this.name = name;
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
    
    public List<Student> getStudents() {
            return students;
    }

    public void setCourses(List<Student> students) {
            this.students = students;
    }
    
}
