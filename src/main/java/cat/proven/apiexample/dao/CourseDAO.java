/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.proven.apiexample.dao;

import cat.proven.apiexample.entities.Course;
import cat.proven.apiexample.entities.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rusben
 */
public class CourseDAO {
    private Connection connection;

	public CourseDAO(Connection connection) {
		this.connection = connection;
	}
        
        public Connection getConnection() {
		return connection;
	}

	public Collection<Course> findAll() {
            String sql = "SELECT * FROM Course";
            ArrayList<Course> list = new ArrayList<Course>();

            try {
                    PreparedStatement statement = getConnection().prepareStatement(sql);
                    ResultSet rs = statement.executeQuery();

                    while (rs.next()) {
                        Course course = new Course(rs.getInt("id"), rs.getString("name"));
                        list.add(course);
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }
            return list;

	}
        
        public Collection<Course> findByIdStudent(int idStudent) {
            String sql = "SELECT id, name FROM StudentCourse sc INNER JOIN Course c ON sc.idCourse = c.id WHERE idStudent= ?";

            ArrayList<Course> list = new ArrayList<Course>();

            try {
                    PreparedStatement statement = getConnection().prepareStatement(sql);
                    statement.setInt(1, idStudent);
                    ResultSet rs = statement.executeQuery();

                    while (rs.next()) {
                            Course course = new Course(rs.getInt("id"), rs.getString("name"));
                            list.add(course);
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }
            return list;

	}
        
        public Course findById(int id) {
            String sql = "SELECT * FROM Course WHERE id= ?";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);

                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Course course = new Course(rs.getInt("id"), rs.getString("name"));
                    return course;
                }

            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return null;

	}
        
        public Course add(Course course) {
            String sql = "INSERT INTO Course (`id`, `name`) VALUES (NULL,?)";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setString(1, course.getName());
                int rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return course;
                
	}
        
        public Course update(Course course) {
            String sql = "UPDATE Course SET `name` = ? WHERE `Course`.`id` = ?";

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setString(1, course.getName());
                statement.setInt(2, course.getId());
                int rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return course;

	}

	public int delete(Course course) {
            String sql = "DELETE FROM Course WHERE id = ?";
            int rs = 0;

            try {
                PreparedStatement statement = getConnection().prepareStatement(sql);
                statement.setInt(1, course.getId());
                rs = statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;

	}

}
