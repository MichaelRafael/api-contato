package br.com.projeto.blue.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.blue.DTO.ContatoDTO;
import br.com.projeto.blue.exceptions.DataIntegrityViolationException;
import br.com.projeto.blue.exceptions.EntityNotFoundException;
import br.com.projeto.blue.model.Contato;
import br.com.projeto.blue.repository.ContatoRepository;

import javax.xml.crypto.Data;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Contato findById(Long id) {
		Optional<Contato> obj = contatoRepository.findById(id);
		return obj.orElseThrow(() -> new EntityNotFoundException("Nenhum contato encontrada com este ID"));
	}

	public List<Contato> findAll() {
		return contatoRepository.findAll();
	}

	public Contato save(ContatoDTO objDTO) {
		findByEmail(objDTO);
		return contatoRepository.save(modelMapper.map(objDTO, Contato.class));
	}

	public Contato upDate(ContatoDTO objDTO) {
		findById(objDTO.getId());
		findByEmail(objDTO);
		return contatoRepository.save(modelMapper.map(objDTO, Contato.class));
	}

	public void delete(Long id) {
		findById(id);
		contatoRepository.deleteById(id);
	}

	public void findByEmail(ContatoDTO objDTO) {
		Optional<Contato> contato = contatoRepository.findByEmail(objDTO.getEmail());
		if (contato.isPresent() && !contato.get().getId().equals(objDTO.getId())) {
			throw new DataIntegrityViolationException("Email já cadastrado na base de dados!");
		}
	}

}
