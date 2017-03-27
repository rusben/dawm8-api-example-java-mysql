package cat.proven.apiexample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cat.proven.apiexample.entities.Student;

/**
 *
 * @author AMS
 */
public class StudentDAO {

	private Connection connection;

	public StudentDAO(Connection connection) {
		this.connection = connection;
	}

	public Collection<Student> findAll() {
		// return db;
		String sql = "SELECT * FROM Student";
		ArrayList<Student> list = new ArrayList<Student>();

		try {
			PreparedStatement statement = getConnection().prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				Student student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("lastname"));
				list.add(student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public Student findById(int id) {
		String sql = "SELECT * FROM Student WHERE id= ?";
		Connection conn = null;

		try {
			PreparedStatement statement = getConnection().prepareStatement(sql);

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			// Visualizar todos los datos de la tabla.
			while (rs.next()) {
				Student student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("lastname"));
				return student;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Afegeix l'estudiant i el retorna amb l'Id autoincremental.
	 * 
	 * @param student
	 * @return
	 */
	public Student add(Student student) {
		String sql = "INSERT INTO Student (`id`, `name`, `lastname`) VALUES (NULL,?,?)";
		// INSERT INTO `Student` (`id`, `name`, `lastname`) VALUES (NULL,
		// 'Peter', 'Tosh');

		try {
			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setString(1, student.getName());
			statement.setString(2, student.getLastname());
			int rs = statement.executeUpdate();

			// Visualizar todos los datos de la tabla.

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;

		/*
		 * //Cerquem el màxim id per incrementar-lo en 1 int idMax = 0; for
		 * (Student st : db) { if(idMax < st.getId()) idMax = st.getId(); }
		 * student.setId(idMax + 1); db.add(student);DELETE FROM table_name
		 * WHERE condition; return student;
		 */
	}

	public Connection getConnection() {
		return connection;
	}

	public Student modify(Student student) {
		String sql = "UPDATE Student SET `name` = ?, `lastname` = ? WHERE `Student`.`id` = ?";

		try {
			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setString(1, student.getName());
			statement.setString(2, student.getLastname());
			statement.setInt(3, student.getId());
			int rs = statement.executeUpdate();
			// Visualizar todos los datos de la tabla.

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;

	}

	public int remove(Student student) {
		String sql = "DELETE FROM Student WHERE id = ?";
		int rs = 0;

		try {
			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setInt(1, student.getId());
			rs = statement.executeUpdate();
			// Visualizar todos los datos de la tabla.

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return rs;

	}

}
