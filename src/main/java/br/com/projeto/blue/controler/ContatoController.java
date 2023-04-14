package br.com.projeto.blue.controler;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.projeto.blue.model.Contato;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.projeto.blue.DTO.ContatoDTO;
import br.com.projeto.blue.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/contato")
public class ContatoController {

	public static final String ID = "/{id}";
	@Autowired
	private ContatoService contatoService;

	@Autowired
	private ModelMapper modelMapper;
	
	
	@GetMapping(ID)
	@Operation(summary = "Retorna um contato através do seu ID")
	public ResponseEntity<ContatoDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(modelMapper.map(contatoService.findById(id), ContatoDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<List<ContatoDTO>> findAll() {
		return ResponseEntity.ok().body(contatoService.findAll()
				.stream().map(x -> modelMapper.map(x, ContatoDTO.class)).collect(Collectors.toList()));
	}
	
	@PostMapping
	public ResponseEntity<ContatoDTO> save(@Valid @RequestBody ContatoDTO contatoDTO) {
		return ResponseEntity.ok().body(modelMapper.map(contatoService.save(contatoDTO), ContatoDTO.class));

	}
	
	@PutMapping(ID)
	public ResponseEntity<ContatoDTO> upDate(@PathVariable Long id, @Valid @RequestBody ContatoDTO contatoDTO) {
		contatoDTO.setId(id);
		return ResponseEntity.ok().body(modelMapper.map(contatoService.upDate(contatoDTO), ContatoDTO.class));
	}
	
	@DeleteMapping(ID)
	public ResponseEntity<ContatoDTO> delete(@PathVariable Long id) {
		contatoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}






