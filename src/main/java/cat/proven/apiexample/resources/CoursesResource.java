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


@Path("courses")
@Produces("application/json")
public class CoursesResource {

    StudentService studentService;
    CourseService courseService;

    public CoursesResource(@Context ServletContext context) {

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
    public Response courses() {
        Collection<Course> courses = courseService.getCourses();
        GenericEntity<Collection<Course>> result = new GenericEntity<Collection<Course>>(courses) {
        };
        return Response.ok().entity(result).build();
    }

    @Path("{id}")
    @GET
    public Response getCourseById(@PathParam("id") int id) {
        Course course = courseService.getCourseById(id);
        if (course == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(course).build();
    }

    @POST
    public Response addCourse(@FormParam("name") String name) {

        if (name == null || name.equals("")) {
            ApplicationException ex = new ApplicationException("Parameter name is mandatory");
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        Course course = courseService.addCourse(new Course(0, name));
        return Response.ok(course).build();
    }

    @Path("{id}")
    @POST
    public Response updateCourse(@FormParam("name") String name, @PathParam("id") int id) {

        if (name == null || name.equals("")) {
                ApplicationException ex = new ApplicationException("Parameter name is mandatory");
                return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }

        Course course = courseService.getCourseById(id);

        if (course == null)
                return Response.status(Response.Status.NOT_FOUND).build();

        course.setName(name);
        course = courseService.updateCourse(course);

        return Response.ok(course).build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteCourse(@PathParam("id") int id) {
        Course course = courseService.getCourseById(id);
        if (course == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else {
            courseService.deleteCourse(course);
            return Response.ok(course).build();
        }
    }

    @Path("{id}/students")
    @GET
    public Response getCourseStudents(@PathParam("id") int id) {
        List<Student> courseStudents = (List<Student>) courseService.getCourseStudents(id);

        if (courseStudents == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        else {

            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(courseStudents) {
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

        student.getCourses().add(course);
        return Response.ok().build();
    }

}
