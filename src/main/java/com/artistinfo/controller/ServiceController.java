package com.artistinfo.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.artistinfo.service.InfoService;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
public class ServiceController {
	private InfoService service = new InfoService();

	private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@GetMapping(path = "/averagewords", produces = MediaType.APPLICATION_JSON_VALUE)
	public double getAverageWords(@RequestParam(value = "artist") String artist)

	{

		return service.getAverageWords(artist);

	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		CustomErrorResponse customError = new CustomErrorResponse();
		customError.setTimestamp(LocalDateTime.now());
		customError.setError(ex.getMessage());
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			customError.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

		} else {
			customError.setStatus(HttpStatus.NOT_FOUND.value());
		}

		return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
	}

	class CustomErrorResponse {
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")

		private LocalDateTime timestamp;
		private int status;
		private String error;

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setError(String error) {
			this.error = error;
		}
	}
}