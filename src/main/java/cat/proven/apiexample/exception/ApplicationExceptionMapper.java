
package cat.proven.apiexample.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author AMS
 */
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        
        ApplicationException e = 
                new ApplicationException("Error intern", exception);
        
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e)
                .build();
    }
    
}
