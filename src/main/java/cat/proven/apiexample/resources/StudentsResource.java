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
import cat.proven.apiexample.exception.CourseService;

/**
 *
 * @author AMS
 */
@Path("students")
// @Produces({"application/xml", "application/json"})
@Produces("application/json")
public class StudentsResource {

	// http://theopentutorials.com/tutorials/java/jdbc/jdbc-mysql-create-database-example/

	StudentDAO studentDAO;
	CourseService courseService;

	public StudentsResource(@Context ServletContext context) {
		if (context.getAttribute("studentDAO") != null)
			studentDAO = (StudentDAO) context.getAttribute("studentDAO");
		else {
			studentDAO = DAOFactory.getInstance().createStudentDAO();
			context.setAttribute("studentDAO", studentDAO);
		}

		/*
		 * if (context.getAttribute("courseService") != null) courseService =
		 * (CourseService) context.getAttribute("courseService"); else {
		 * courseService = new CourseService();
		 * context.setAttribute("courseService", courseService); }
		 */
	}

	@GET
	public Response students() {
		System.out.println(studentDAO);
		Collection<Student> allStudents = studentDAO.findAll();
		GenericEntity<Collection<Student>> result = new GenericEntity<Collection<Student>>(allStudents) {
		};
		return Response.ok().entity(result).build();
	}

	@Path("{id}")
	@GET
	public Response findStudentById(@PathParam("id") int id) {
		Student s = studentDAO.findById(id);
		if (s == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		else
			return Response.ok(s).build();
	}

	// @Path("{id}")

	@POST
	// @Consumes("multipart/form-data")
	// @Consumes("multipart/x-www-form-urlencoded")
	public Response addStudent(

			@FormParam("name") String name, @FormParam("lastname") String lastname) {

		// Student s = serviceStudent.findById(id);
		// System.out.println("ID::::::::::::::::...."+s.getId());

		if (name == null || name.equals("")) {
			ApplicationException ex = new ApplicationException("El paràmetre name és obligatori");
			return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
		}

		if (lastname == null || lastname.equals("")) {
			ApplicationException ex = new ApplicationException("El paràmetre lastname és obligatori");
			return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
		}

		Student s = new Student(0, name, lastname);
		s = studentDAO.add(s);

		return Response.ok(s).build();
	}

	@Path("{id}")
	@POST
	public Response modifyStudent(@FormParam("name") String name, @FormParam("lastname") String lastname,
			@PathParam("id") int id) {

		if (name == null || name.equals("")) {
			ApplicationException ex = new ApplicationException("El paràmetre name és obligatori");
			return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
		}

		if (lastname == null || lastname.equals("")) {
			ApplicationException ex = new ApplicationException("El paràmetre lastname és obligatori");
			return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
		}

		Student student = studentDAO.findById(id);

		if (student == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		student.setName(name);
		student.setLastname(lastname);
		student = studentDAO.modify(student);
		return Response.ok(student).build();

	}

	@DELETE
	@Path("{id}")
	public Response deleteStudent(@PathParam("id") int id) {
		Student student = studentDAO.findById(id);
		if (student == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		else {
			studentDAO.remove(student);
			return Response.ok(student).build();
		}
	}

	@Path("{id}/courses")
	@GET
	public Response getStudentCourses(@PathParam("id") int id) {
		Student student = studentDAO.findById(id);
		if (student == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		else {
			List<Course> cursos = student.getCourses();
			GenericEntity<List<Course>> entity = new GenericEntity<List<Course>>(cursos) {
			};

			return Response.ok().entity(entity).build();
		}
	}

	@Path("{id_student}/courses/{id_course}")
	@POST
	public Response addStudentToCourse(@PathParam("id_student") int idStudent, @PathParam("id_course") int idCourse) {

		Student student = studentDAO.findById(idStudent);
		if (student == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		Course course = courseService.findById(idCourse);
		if (course == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		student.getCourses().add(course);
		return Response.ok().build();
	}

}
