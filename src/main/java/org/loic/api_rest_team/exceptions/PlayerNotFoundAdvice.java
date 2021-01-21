package org.loic.api_rest_team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class PlayerNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(TeamNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String playerNotFoundHandler(PlayerNotFoundException ex) {
		return ex.getMessage();
	}
}
