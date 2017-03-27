package cat.proven.apiexample.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {

	private Integer id;
	private String name;
	private String lastname;
	private List<Course> courses;

	public Student() {
		this.courses = new ArrayList<Course>();
	}

	public Student(int id, String name, String lastname) {
		// Call to default constructor, in this case to fill the courses list
		this();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
