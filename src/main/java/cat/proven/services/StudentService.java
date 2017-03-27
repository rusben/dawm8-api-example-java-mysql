package cat.proven.services;

import cat.proven.entities.Course;
import cat.proven.entities.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author AMS
 */
public class StudentService {

    //private Collection<Student> db;
    StudentDAO mysqlConnect = new StudentDAO();
    
    
    public StudentService(){
        
        
        
//
//        
//        db = new ArrayList<Student>();
//        
//        Student[] arrStdDAM = new Student[] 
//        {
//            new Student(0, "Pol", "Arroyo"),
//            new Student(0, "Busquets", "Raul"),
//            new Student(0, "Cañete", "Adrian"),
//            new Student(0, "Castells", "Aleix"),
//            new Student(0, "Cebrian", "Isaac"),
//            new Student(0, "Chevez", "Maynord"),
//            new Student(0, "Cortes", "Adria"),
//            new Student(0, "Dura", "David"),
//            new Student(0, "Felgar", "Jonathan"),
//            new Student(0, "Galarza", "Carlos Alberto"),
//            new Student(0, "Gimenez", "Sergio"),
//            new Student(0, "Gonzalez", "Alexis"),
//            new Student(0, "Herrera", "Ivan"),
//            new Student(0, "Larroy", "Oscar"),
//            new Student(0, "Lazaro", "Claudi"),
//            new Student(0, "Malatesta", "Eloy"),
//            new Student(0, "Marca", "Zulma Roxana"),
//            new Student(0, "Marquez", "Victor"),
//            new Student(0, "Mata", "Jaume"),
//            new Student(0, "Mediavilla", "Pedro"),
//            new Student(0, "Merino", "Francisco"),
//            new Student(0, "Naranjo", "Scarlet Denise"),
//            new Student(0, "Oufighou", "Nassim"),
//            new Student(0, "Ponce", "Ferran"),
//            new Student(0, "Ramirez", "Albert"),
//            new Student(0, "Ramos", "Rafael"),
//            new Student(0, "Rincon", "Xavier"),
//            new Student(0, "Rubio", "Sonia")            
//        };
//        
//        Student[] arrStdDAWA = new Student[] {
//            new Student(0, "Blanco", "Irene"),
//            new Student(0, "Carneros", "Sergi"),
//            new Student(0, "Delriu", "Jordi"),
//            new Student(0, "Ferre", "Andreu"),
//            new Student(0, "Garcia", "Carlos"),
//            new Student(0, "Gomez", "Ismael"),
//            new Student(0, "Gomez", "Sergio"),
//            new Student(0, "Gutierrez", "Alejandro"),
//            new Student(0, "Nieto", "Adria"),
//            new Student(0, "Rueda", "Francisco Javier")
//            
//        };
//        
//        Student[] arrStdDAWB = new Student[] 
//        {
//            new Student(0, "Catasus", "Jordi"),
//            new Student(0, "De Feo", "Adrián"),
//            new Student(0, "Dieguez", "Ismael"),
//            new Student(0, "Garcia", "Luciano"),
//            new Student(0, "Garcia", "Alejandro"),
//            new Student(0, "Garcia", "Sergio"),
//            new Student(0, "Garcia", "Eric"),
//            new Student(0, "Gonzalez", "Angel"),
//            new Student(0, "Guardeño", "Manuel"),
//            new Student(0, "Guerrero", "Javier"),
//            new Student(0, "Mabtoul", "Sofiane"),
//            new Student(0, "Muñoz", "Juan Antonio"),
//            new Student(0, "Nole", "Jorge Luis"),
//            new Student(0, "Rodriguez", "Juan"),
//            new Student(0, "Salado", "Adrián")            
//        };
//        
//        
//        for (Student student : arrStdDAWA) {
//            student.getCourses().add(new Course(2, "DAW2A"));
//            this.add(student);
//        }
//        
//        for (Student student : arrStdDAWB) {
//            student.getCourses().add(new Course(3, "DAW2B"));
//            this.add(student);
//        }
//        
//        for (Student student : arrStdDAM) {
//            student.getCourses().add(new Course(1, "DAM2"));
//            this.add(student);
//        }
//        
      
        
    }
    
    public Collection<Student> findAll(){
        //return db; 
        String sql = "SELECT * FROM Student";
         ArrayList<Student> list = new ArrayList<Student>();

        try {
            Connection conn = mysqlConnect.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
 
            while (rs.next()) {
                
                Student st = new Student(rs.getInt(1), rs.getString(2), rs.getString(3)) ;
                list.add(st);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }
        return list;
       
    
    }
    
    public Student findById(int id){
         String sql = "SELECT * FROM Student WHERE id= ?";
         
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            // Visualizar todos los datos de la tabla.
            while (rs.next()) {
                Student st = new Student(rs.getInt(1), rs.getString(2), rs.getString(3)) ;
                return st;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }
        return null;
       
    
    }
    
    /**
     * Afegeix l'estudiant i el retorna amb l'Id 
     * autoincremental.
     * @param student
     * @return 
     */
    public Student add(Student student){
        String sql = "INSERT INTO Student (`id`, `name`, `lastname`) VALUES (NULL,?,?)";
                //INSERT INTO `Student` (`id`, `name`, `lastname`) VALUES (NULL, 'Peter', 'Tosh');
         
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            int rs = statement.executeUpdate();
            
            // Visualizar todos los datos de la tabla.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }
        return student;
       
    
        /*//Cerquem el màxim id per incrementar-lo en 1
        int idMax = 0;
        for (Student st : db) {
            if(idMax < st.getId()) idMax = st.getId();
        }
        student.setId(idMax + 1);
        db.add(student);DELETE FROM table_name
WHERE condition;
        return student;        */
    }
    
    
        public Student modify(Student student){
        String sql = "UPDATE Student SET `name` = ?, `lastname` = ? WHERE `Student`.`id` = ?";

         
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.setInt(3, student.getId());
            int rs = statement.executeUpdate();
            // Visualizar todos los datos de la tabla.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
        }
        return student;
       
    
    
        /*//Cerquem el màxim id per incrementar-lo en 1
        int idMax = 0;
        for (Student st : db) {
            if(idMax < st.getId()) idMax = st.getId();
        }
        student.setId(idMax + 1);
        db.add(student);
        return student;        */
    }
        
        
    public int remove(Student student) {
        String sql = "DELETE FROM Student WHERE id = ?";
        //DELETE FROM `Student` WHERE `Student`.`id` = 262
        int rs=0;

         
        try {
            PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
            statement.setInt(1, student.getId());
            rs = statement.executeUpdate();
            // Visualizar todos los datos de la tabla.

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mysqlConnect.disconnect();
            
        }
       return rs;
    
    }
    

    
}
