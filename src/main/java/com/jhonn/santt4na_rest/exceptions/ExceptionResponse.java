package com.jhonn.santt4na_rest.exceptions;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
