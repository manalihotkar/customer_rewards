package com.retailer.customerreward.exception;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.retailer.customerreward.model.v1.Error;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomerRewardExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Error> exception(Exception ex) {
		log.error("Exception occured "+ ex.getMessage());
		Error message = new Error();
		message.setCode(BigDecimal.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		message.setDescription(ex.getMessage());
		return new ResponseEntity<Error>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
