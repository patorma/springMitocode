package com.mitocode.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	
	private LocalDateTime  fecha;
	private String mensaje;
	private String detalles;

}
