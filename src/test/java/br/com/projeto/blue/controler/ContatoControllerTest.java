package br.com.projeto.blue.controler;

import br.com.projeto.blue.DTO.ContatoDTO;
import br.com.projeto.blue.exceptions.FieldMessage;
import br.com.projeto.blue.exceptions.ValidationError;
import br.com.projeto.blue.model.Contato;
import br.com.projeto.blue.service.ContatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContatoControllerTest {

    public static final long ID = 1L;
    public static final String NOME = "André";
    public static final String EMAIL = "andre@gmail.com";
    public static final String TELEFONE = "998983632";
    public static final int INDEX = 0;

    private Contato contato;
    private ContatoDTO contatoDTO;


    private ValidationError validationError = new ValidationError();

    private FieldMessage fieldMessage = new FieldMessage();

    @InjectMocks
    private ContatoController contatoController;

    @Mock
    private ContatoService contatoService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startContato();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(contatoService.findById(anyLong())).thenReturn(contato);
        when(modelMapper.map(any(), any())).thenReturn(contatoDTO);

        ResponseEntity<ContatoDTO> response = contatoController.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContatoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(TELEFONE, response.getBody().getTelefone());

    }

    @Test
    void whenFindAllThenReturnAnListOfContacts() {
        when(contatoService.findAll()).thenReturn(List.of(contato));
        when(modelMapper.map(any(), any())).thenReturn(contatoDTO);

        ResponseEntity<List<ContatoDTO>> response = contatoController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ContatoDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NOME, response.getBody().get(INDEX).getNome());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(TELEFONE, response.getBody().get(INDEX).getTelefone());
    }

    @Test
    void whenSaveThenReturnCreated() {
        when(contatoService.save(any())).thenReturn(contato);
        when(modelMapper.map(any(), any())).thenReturn(contatoDTO);

        ResponseEntity<ContatoDTO> response = contatoController.save(contatoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(TELEFONE, response.getBody().getTelefone());
    }

    @Test
    void whenUpDateThenRetunrSuccess() {
        when(contatoService.upDate(contatoDTO)).thenReturn(contato);
        when(modelMapper.map(any(), any())).thenReturn(contatoDTO);

        ResponseEntity<ContatoDTO> response = contatoController.upDate(ID, contatoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ContatoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(TELEFONE, response.getBody().getTelefone());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(contatoService).delete(anyLong());

        ResponseEntity<ContatoDTO> response = contatoController.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contatoService, times(1)).delete(anyLong());
    }

    private void startContato() {
        contato = new Contato(ID, NOME, EMAIL, TELEFONE);
        contatoDTO = new ContatoDTO(ID, NOME, EMAIL, TELEFONE);
    }
}