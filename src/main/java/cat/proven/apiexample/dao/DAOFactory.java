package cat.proven.apiexample.dao;

import java.sql.Connection;

public class DAOFactory {
    private static DAOFactory instance;

    public static DAOFactory getInstance() {
        if (instance == null) {
                instance = new DAOFactory();
        }
        return instance;
    }

    private DAOFactory() {
        super();
    }

    public StudentDAO createStudentDAO() {
        Connection connection = ConnectionFactory.getInstance().connect();
        return new StudentDAO(connection);
    }

    public CourseDAO createCourseDAO() {
        Connection connection = ConnectionFactory.getInstance().connect();
        return new CourseDAO(connection);
    }
}
