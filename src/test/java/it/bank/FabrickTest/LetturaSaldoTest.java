package it.bank.FabrickTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {FabrickTestApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public class LetturaSaldoTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	void saldoOk() throws Exception {
		 mockMvc.perform(get("/accounts/letturaSaldo/{accountId}","14537780")).andExpect(status().isOk());
	}
	
	@Test
	void saldoAccountIdNonPresente() throws Exception {
		 mockMvc.perform(get("/accounts/letturaSaldoYYY/{accountId}","")).andExpect(status().is4xxClientError());
	}
	
	@Test
	void saldoAccountNonTrovato() throws Exception {
		 mockMvc.perform(get("/accounts/letturaSaldoYYY/{accountId}","999")).andExpect(status().is4xxClientError());
	}
	
}
