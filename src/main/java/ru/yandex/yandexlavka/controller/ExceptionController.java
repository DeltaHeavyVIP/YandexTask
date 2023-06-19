package ru.yandex.yandexlavka.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionController {

    private String badRequestBody = "{}";

   @ExceptionHandler({ConstraintViolationException.class})
   public ResponseEntity<String> handleSQLValidateException() {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestBody);
   }

   @ExceptionHandler({InvalidInputDataException.class})
   public ResponseEntity<String> handleInvalidInputDataException() {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestBody);
   }

   @ExceptionHandler({MethodArgumentTypeMismatchException.class})
   public ResponseEntity<String> handleMethodArgumentTypeMismatchException() {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestBody);
   }

   @ExceptionHandler({ResourceNotFoundException.class})
   public ResponseEntity<String> handleResourceNotFoundException() {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(badRequestBody);
   }

       @ExceptionHandler({NullPointerException.class})
   public ResponseEntity<String> handleResourceNotFoundException() {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestBody);
   }

}
