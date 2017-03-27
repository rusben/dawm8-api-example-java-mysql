package cat.proven.resources;

import cat.proven.entities.Course;
import cat.proven.entities.Student;
import cat.proven.services.ApplicationException;
import cat.proven.services.CourseService;
import cat.proven.services.StudentService;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author AMS
 */
@Path("students")
//@Produces({"application/xml", "application/json"})
@Produces("application/json")
public class StudentsResource {
    
    StudentService serviceStudent;
    CourseService courseService;
    
    public StudentsResource(@Context ServletContext context){        
        if(context.getAttribute("studentService") != null)
            serviceStudent = (StudentService)context.getAttribute("studentService");
        else{
            serviceStudent = new StudentService();
            context.setAttribute("studentService", serviceStudent);
        }
        
        if(context.getAttribute("courseService") != null)
            courseService = (CourseService)context.getAttribute("courseService");
        else{
            courseService = new CourseService();
            context.setAttribute("courseService", courseService);
        }        
    }
    
    @GET
    public Response students(){                
        Collection<Student> allStudents = serviceStudent.findAll();
        GenericEntity<Collection<Student>> result = new GenericEntity<Collection<Student>>(allStudents){};
        return Response.ok().entity(result).build();        
    }
    
    @Path("{id}")
    @GET
    public Response findStudentById(@PathParam("id") int id){
        Student s = serviceStudent.findById(id);
        if(s == null) 
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(s).build();
    }
    
    //      @Path("{id}")
    
    @POST
    //@Consumes("multipart/form-data")
    //@Consumes("multipart/x-www-form-urlencoded")
    public Response addStudent(
            
            @FormParam("name") String name, 
            @FormParam("lastname") String lastname){
        
        //Student s = serviceStudent.findById(id);
       //System.out.println("ID::::::::::::::::...."+s.getId());
        
        if(name == null || name.equals("")){
            ApplicationException ex = new ApplicationException("El paràmetre name és obligatori");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if(lastname == null || lastname.equals("")){
            ApplicationException ex = new ApplicationException("El paràmetre lastname és obligatori");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
           
        Student s = new Student(0, name, lastname);
        s = serviceStudent.add(s);
        
        return Response.ok(s).build();
    }
    
    @Path("{id}")
    @POST
    public Response modifyStudent(
            
            @FormParam("name") String name, 
            @FormParam("lastname") String lastname,
            @PathParam("id") int id){
        
        Student s = serviceStudent.findById(id);
        
       //System.out.println("ID::::::::::::::::...."+s.getId());
        
        if(s == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        if(name == null || name.equals("")){
            ApplicationException ex = new ApplicationException("El paràmetre name és obligatori");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if(lastname == null || lastname.equals("")){
            ApplicationException ex = new ApplicationException("El paràmetre lastname és obligatori");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
        
        
        s.setName(name);
        s.setLastname(lastname);
        s=serviceStudent.modify(s);
        /*
        s.setName(name);
        s.setLastname(lastname);*/
        return Response.ok(s).build(); 
        
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") int id){
        Student s = serviceStudent.findById(id);
        if(s == null) 
            return Response.status(Response.Status.NOT_FOUND).build();
        else{
            serviceStudent.remove(s);
            return Response.ok(s).build();
        }            
    }
    
    @Path("{id}/courses")
    @GET
    public Response getCoursesByStudentId(@PathParam("id") int id){
        Student s = serviceStudent.findById(id);
        if(s == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else {
            ArrayList<Course> cursos = s.getCourses();
            GenericEntity<ArrayList<Course>> entity =
                    new GenericEntity<ArrayList<Course>>(cursos) {};
                    
            return Response
                    .ok()
                    .entity(entity)
                    .build();
        }
    }
    
    @Path("{id_student}/courses/{id_course}")
    @POST
    public Response addStudentToCourse(
            @PathParam("id_student") int idStudent, 
            @PathParam("id_course") int idCourse){
        
        Student s = serviceStudent.findById(idStudent);
        if(s == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        
        Course c = courseService.findById(idCourse);
        if(c == null)
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        
        s.getCourses().add(c);
        return Response.ok().build();
    }
    
    
}
