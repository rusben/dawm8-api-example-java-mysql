package cat.proven.apiexample.resources;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import cat.proven.apiexample.dao.DAOFactory;
import cat.proven.apiexample.dao.StudentDAO;
import cat.proven.apiexample.entities.Course;
import cat.proven.apiexample.entities.Student;
import cat.proven.apiexample.exception.ApplicationException;
import cat.proven.apiexample.service.CourseService;
import cat.proven.apiexample.service.StudentService;


@Path("students")
@Produces("application/json")
public class StudentsResource {

    StudentService studentService;
    CourseService courseService;

    public StudentsResource(@Context ServletContext context) {

        if (context.getAttribute("studentService") != null)
                studentService = (StudentService) context.getAttribute("studentService");
        else {
                studentService = new StudentService();
                context.setAttribute("studentService", studentService);
        }

        if (context.getAttribute("courseService") != null)
                courseService = (CourseService) context.getAttribute("courseService");
        else {
                courseService = new CourseService();
                context.setAttribute("courseService", courseService);
        }

    }

    @GET
    public Response students() {
        Collection<Student> students = studentService.getStudents();
        GenericEntity<Collection<Student>> result = new GenericEntity<Collection<Student>>(students) {
        };
        return Response.ok().entity(result).build();
    }

    @Path("{id}")
    @GET
    public Response getStudentById(@PathParam("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(student).build();
    }

    @POST
    public Response addStudent(@FormParam("name") String name, @FormParam("lastname") String lastname) {

        if (name == null || name.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter name is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if (lastname == null || lastname.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter lastname is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        Student student = studentService.addStudent(new Student(0, name, lastname));
        return Response.ok(student).build();
    }

    @Path("{id}")
    @POST
    public Response updateStudent(@FormParam("name") String name, @FormParam("lastname") String lastname, @PathParam("id") int id) {

        if (name == null || name.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter name is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        if (lastname == null || lastname.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter lastname is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        Student student = studentService.getStudentById(id);

        if (student == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        student.setName(name);
        student.setLastname(lastname);
        student = studentService.updateStudent(student);

        return Response.ok(student).build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else {
            studentService.deleteStudent(student);
            return Response.ok(student).build();
        }
    }

    @Path("{id}/courses")
    @GET
    public Response getStudentCourses(@PathParam("id") int id) {
        List<Course> studentCourses = (List<Course>) studentService.getStudentCourses(id);

        if (studentCourses == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        else {

            GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(studentCourses) {
            };

            return Response.ok().entity(entity).build();
        }
    }


    @Path("{id_student}/courses/{id_course}")
    @POST
    public Response addStudentToCourse(@PathParam("id_student") int idStudent, @PathParam("id_course") int idCourse) {

        Student student = studentService.getStudentById(idStudent);
        if (student == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        Course course = courseService.getCourseById(idCourse);
        if (course == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        Course c = studentService.addStudentToCourse(student, course);
        return Response.ok(c).build();
    }

}
