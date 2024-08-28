package com.server.turistai;

import com.server.turistai.config.SecurityConfig;
import com.server.turistai.controller.TravelController;
import com.server.turistai.controller.dto.CreateTravelDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TravelController.class)
@Import(SecurityConfig.class)
@SpringBootTest
class TuristaiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser // Simula um usuário autenticado
	public void testCreateTravelWithDate() throws Exception {
		String jsonContent = "{ \"date\": \"2025-10-25\", \"title\": \"Sample Title\", \"description\": \"Sample Description\" }";

		// Construção da requisição e execução do teste
		mockMvc.perform(MockMvcRequestBuilders.post("/travels") // Ajuste o endpoint conforme necessário
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()); // Ajuste as expectativas conforme necessário

		// Adicione outras verificações se necessário
	}

}
