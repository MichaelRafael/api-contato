package br.com.projeto.blue.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime timestamp;
    private Integer status;
    private String error;

}
