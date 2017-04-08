
package cat.proven.apiexample.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApplicationException extends Exception {

	private String message;
	private String exceptionType;

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		this.message = message;
	}

	public ApplicationException(String message, Exception origin) {
		this.message = message;
		this.exceptionType = origin.getClass().toString();
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
