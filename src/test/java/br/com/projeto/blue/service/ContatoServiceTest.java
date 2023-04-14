package br.com.projeto.blue.service;

import br.com.projeto.blue.DTO.ContatoDTO;
import br.com.projeto.blue.exceptions.DataIntegrityViolationException;
import br.com.projeto.blue.exceptions.EntityNotFoundException;
import br.com.projeto.blue.model.Contato;
import br.com.projeto.blue.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ContatoServiceTest {

    public static final long ID = 1L;
    public static final String NOME = "André";
    public static final String EMAIL = "andre@gmail.com";
    public static final String TELEFONE = "998983632";
    public static final String NENHUM_CONTATO_ENCONTRADO_COM_ESTE_ID = "Nenhum contato encontrado com este ID";
    public static final int INDEX = 0;

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ModelMapper modelMapper;

    private Contato contato;
    private ContatoDTO contatoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startContato();
    }

    @Test
    void whenFindByIdThenReturnAnContato() {
        when(contatoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(contato));

        Contato response = contatoService.findById(ID);
        assertNotNull(response);
        assertEquals(Contato.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(TELEFONE, response.getTelefone());
    }

    @Test
    void whenFindByIdThenReturnAnEntityNotFoundException() {
        when(contatoRepository.findById(Mockito.anyLong())).
                thenThrow(new EntityNotFoundException(NENHUM_CONTATO_ENCONTRADO_COM_ESTE_ID));

        try {
            contatoService.findById(ID);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals(NENHUM_CONTATO_ENCONTRADO_COM_ESTE_ID, e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfContats() {
        when(contatoRepository.findAll()).thenReturn(List.of(contato));

        List<Contato> response = contatoService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Contato.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(TELEFONE, response.get(INDEX).getTelefone());

    }

    @Test
    void whenSaveThenReturnSuccess() {
        when(contatoRepository.save(any())).thenReturn(contato);
        when(modelMapper.map(any(), any())).thenReturn(contato);

        Contato response = contatoService.save(contatoDTO);

        assertNotNull(response);
        assertEquals(Contato.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(TELEFONE, response.getTelefone());
    }

    @Test
    void whenSaveThenReturnAnDataIntegrityViolationException() {
        when(contatoRepository.findByEmail(anyString())).thenReturn(Optional.of(contato));

        try {
            contato.setId(2L);
            contatoService.save(contatoDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado na base de dados!", ex.getMessage());
        }
    }

    @Test
    void whenUpDateThenReturnSuccess() {
        when(contatoRepository.findById(anyLong())).thenReturn(Optional.of(contato));
        when(contatoRepository.save(any())).thenReturn(contato);
        when(modelMapper.map(any(), any())).thenReturn(contato);

        Contato response = contatoService.upDate(contatoDTO);

        assertNotNull(response);
        assertEquals(Contato.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(TELEFONE, response.getTelefone());
    }

    @Test
    void whenUpDateThenReturnAnDataIntegrityViolationException() {
        when(contatoRepository.findByEmail(anyString())).thenReturn(Optional.of(contato));

        try {
            contato.setId(2L);
            contatoService.save(contatoDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email já cadastrado na base de dados!", ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(contatoRepository.findById(anyLong())).thenReturn(Optional.of(contato));
        doNothing().when(contatoRepository).deleteById(anyLong());
        contatoService.delete(ID);

        verify(contatoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException() {
       when(contatoRepository.findById(anyLong()))
               .thenThrow(new EntityNotFoundException(NENHUM_CONTATO_ENCONTRADO_COM_ESTE_ID));
       try {
           contatoService.delete(ID);
       } catch (Exception ex) {
            assertEquals(EntityNotFoundException.class, ex.getClass());
            assertEquals(NENHUM_CONTATO_ENCONTRADO_COM_ESTE_ID, ex.getMessage());
       }
    }

    private void startContato() {
        contato = new Contato(ID, NOME, EMAIL, TELEFONE);
        contatoDTO = new ContatoDTO(ID, NOME, EMAIL, TELEFONE);
    }
}