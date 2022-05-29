package it.bank.FabrickTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@ContextConfiguration(classes = {FabrickTestApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public class TransazioniTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	void transazioneKo() throws Exception {
		 mockMvc.perform(get("/accounts//listaTransazioni/{accountId}","14537780")
				 .param("fromAccountingDate", "2019-01-01")
				 .param("toAccountingDate", "2019-12-01"))
				 .andExpect(status().isOk());
	}
	
	@Test
	void transazioneDateNonPresenti() throws Exception {
		 mockMvc.perform(get("/accounts//listaTransazioni/{accountId}","14537780")).andExpect(status().is4xxClientError());
				 //.param("fromAccountingDate", "2019-01-01") //volutamente mancante
				 //.param("toAccountingDate", "2019-12-01"))  //volutamente mancante
	}
	
	@Test
	void transazioneDataNonValida() throws Exception {
		 mockMvc.perform(get("/accounts//listaTransazioni/{accountId}","14537780")
				 .param("fromAccountingDate", "2019-01-01")
				 .param("toAccountingDate", "2019-12-32")) //data errata
				 .andExpect(status().is4xxClientError());
	}
	
	@Test
	void transazioneDataNonValida2() throws Exception {
		 ResultActions result = mockMvc.perform(get("/accounts//listaTransazioni/{accountId}","14537780")
				 .param("fromAccountingDate", "2019-01-01")
				 .param("toAccountingDate", "2019-12-32")); //data errata
		 
		 byte[] arr =result.andReturn().getResponse().getContentAsByteArray();
		 String msg = new String(arr);
		 assertTrue(msg.contains("Invalid date format"));
		// .andExpect(status().reason(containsString("Bad credentials")));
				
	}
}
