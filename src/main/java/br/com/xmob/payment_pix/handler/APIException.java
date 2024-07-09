package br.com.xmob.payment_pix.handler;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Log4j2
public class APIException extends RuntimeException {
	private HttpStatus statusException;
	private ErrorApiResponse bodyException;
	
	private APIException(HttpStatus statusException, String message, Exception e, ProblemType title) {
		super(message, e);
		this.statusException = statusException;
		this.bodyException = ErrorApiResponse.builder()
				.status(statusException.value())
				.type(title.getUri())
				.title(title.getTitle())
				.message(message)
				.description(getDescription(e))
				.timestamp(LocalDateTime.now())
				.build();}

		private APIException(HttpStatus statusException, String message, ProblemType title) {
			super(message);
			this.statusException = statusException;
			this.bodyException = ErrorApiResponse.builder()
					.status(statusException.value())
					.type(title.getUri())
					.title(title.getTitle())
					.message(message)
					.timestamp(LocalDateTime.now())
					.build();
		}

	public static APIException resourceNotFound(String message) {
		return new APIException(HttpStatus.NOT_FOUND, message, ProblemType.RESOURCE_NOT_FOUND);
	}
	public static APIException unavailableService(String message, Exception ex) {
		return new APIException(HttpStatus.SERVICE_UNAVAILABLE, message,ex, ProblemType.SERVICE_UNAVAILABLE);
	}
	public static APIException business(String message) {
		return new APIException(HttpStatus.BAD_REQUEST, message, ProblemType.BUSINESS_ERROR);
	}

	private String getDescription(Exception e) {
		return Optional.ofNullable(e)
				.map(APIException::getMessageCause).orElse(null);
	}

	private static String getMessageCause(Exception e) {
		return e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
	}
	
	

	public ResponseEntity<ErrorApiResponse> buildErrorResponseEntity() {
		return ResponseEntity
				.status(statusException)
				.body(bodyException);

	}
	
	private static final long serialVersionUID = 1L;
}
