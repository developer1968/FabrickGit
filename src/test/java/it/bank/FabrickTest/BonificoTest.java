package it.bank.FabrickTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import it.bank.FabrickTest.model.Account;
import it.bank.FabrickTest.model.Creditor;
import it.bank.FabrickTest.model.FabrickBonificoRequest;
import it.bank.FabrickTest.util.Utility;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class BonificoTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void bonfificoNonPermesso() throws Exception {
		FabrickBonificoRequest bonificoRequest= new FabrickBonificoRequest();
		Creditor creditorObject = new Creditor();
		Account accountCred = new Account();
		accountCred.setAccountCode("14537799");
		creditorObject.setAccount(accountCred);
		
		
		bonificoRequest.setCreditor(creditorObject);
		bonificoRequest.setAmount(1000);
		bonificoRequest.setCurrency("EUR");
		bonificoRequest.setDescription("Stipendio Giugno");
		bonificoRequest.setExecutionDate("2022-05-28");
		
		
		
		 ResultActions result = mockMvc.perform(post("/accounts/bonifico/{accountId}","14537780")
				 .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				 .content(Utility.asJsonString(bonificoRequest)));
		 
		 byte[] arr =result.andReturn().getResponse().getContentAsByteArray();
		 String msg = new String(arr);
		 assertTrue(msg.contains("Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780"));
				
	}
	
}
