package com.aa.microservices.inventory.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timeStamp, String message, String path, String errorCode, String errorDetails) {

	
}
