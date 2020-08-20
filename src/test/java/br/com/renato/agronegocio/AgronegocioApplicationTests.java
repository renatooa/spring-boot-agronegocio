package br.com.renato.agronegocio;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renato.agronegocio.model.dto.AnimalForm;
import br.com.renato.agronegocio.model.dto.FazendaForm;

@SpringBootTest
@AutoConfigureMockMvc
class AgronegocioApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private List<AnimalForm> animasCrudFazenda;

	@Test
	void listarFazendas() throws Exception {
		mockMvc.perform(get("/fazenda").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void obterFazendas() throws Exception {
		mockMvc.perform(get("/fazenda/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void fazendaNaoEncontrada() throws Exception {
		mockMvc.perform(get("/fazenda/999999").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	void inserirFazenda() throws Exception {

		FazendaForm<AnimalForm> fazendaForm = criarFazenda("Fazenda Teste");

		mockMvc.perform(post("/fazenda").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(fazendaForm))).andExpect(status().isOk());
	}

	@Test
	void editarFazenda() throws Exception {

		FazendaForm<AnimalForm> fazendaForm = criarFazenda("Fazenda Edicao");

		mockMvc.perform(put("/fazenda/0").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(fazendaForm))).andExpect(status().isOk());
	}

	@Test
	void apagarFazenda() throws Exception {

		mockMvc.perform(delete("/fazenda/9999999")).andExpect(status().isNoContent());
	}

	@Test
	public void inserirAnimais() throws Exception {

		mockMvc.perform(
				post("/fazenda/0/animais").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(getAnimasCrudFazenda())))
				.andExpect(status().isOk());
	}

	@Test
	public void apagarAnimais() throws Exception, JsonProcessingException {

		mockMvc.perform(
				delete("/fazenda/0/animais").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(getAnimasCrudFazenda())))
				.andExpect(status().isOk());
	}

	@Test
	public void editarAnimais() throws Exception, JsonProcessingException {

		mockMvc.perform(
				put("/fazenda/0/animais").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(getAnimasCrudFazenda())))
				.andExpect(status().isOk());
	}

	@Test
	void listarAnimais() throws Exception {
		mockMvc.perform(get("/fazenda/0/animais").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void listarAnimaisFazendaNaoEncontrada() throws Exception {
		mockMvc.perform(get("/fazenda/9999999/animais").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	private FazendaForm<AnimalForm> criarFazenda(String nome) {
		List<AnimalForm> animais = criarAnimaisNovos();

		FazendaForm<AnimalForm> fazendaForm = new FazendaForm<AnimalForm>(nome, animais);
		return fazendaForm;
	}

	private List<AnimalForm> criarAnimaisNovos() {
		List<AnimalForm> animais = Arrays.asList(new AnimalForm(UUID.randomUUID().toString()),
				new AnimalForm(UUID.randomUUID().toString()));
		return animais;
	}

	public List<AnimalForm> getAnimasCrudFazenda() {

		if (animasCrudFazenda == null) {
			animasCrudFazenda = criarAnimaisNovos();
		}

		return animasCrudFazenda;
	}
}
