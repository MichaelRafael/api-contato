package br.com.projeto.blue.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.*;

@Data
@AllArgsConstructor
public class ContatoDTO {
	
	private Long id;
	
	@NotBlank(message = "O campo nome é requrido")
	private String nome;
	
	@Email(message = "Campo inválido!")
	@NotBlank(message = "O campo email é requrido")
	private String email;
	
	@NotBlank(message = "O campo telefone é requrido")
	private String telefone;

}
