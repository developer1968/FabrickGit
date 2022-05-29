package it.bank.FabrickTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import it.bank.FabrickTest.exceptions.AccountException;
import it.bank.FabrickTest.model.BonificoResponse;
import it.bank.FabrickTest.model.FabrickAccountResponse;
import it.bank.FabrickTest.model.FabrickBonificoRequest;
import it.bank.FabrickTest.model.PayloadSaldo;
import it.bank.FabrickTest.model.transazioni.Payload;
import it.bank.FabrickTest.model.transazioni.TransazioneResponse;
import it.bank.FabrickTest.model.transazioni.Trx;
import it.bank.FabrickTest.service.SaldoService;

@ContextConfiguration(classes = {FabrickTestApplication.class})
@SpringBootTest
public class SaldoServiceTest {

	//@Autowired
	//private SaldoService service;
	
	@InjectMocks
    @Spy
    private SaldoService service;

	@Mock
	private RestTemplate restTemplate;

	@Test
	public void getLetturaSaldoTest() {

		try {

			FabrickAccountResponse fabrickAccountResponse  = new FabrickAccountResponse();
			fabrickAccountResponse.setStatus("OK");
			PayloadSaldo payloadObject = new PayloadSaldo();
			payloadObject.setAbiCode("abi");
			payloadObject.setAccount("1233");
			payloadObject.setIban("99999");
			fabrickAccountResponse.setPayload(payloadObject);
			ResponseEntity<FabrickAccountResponse> response= new ResponseEntity<FabrickAccountResponse>(fabrickAccountResponse,HttpStatus.OK); 
			Mockito.when(restTemplate.exchange(Mockito.anyString(),
					Mockito.any(),
					ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(Class.class)		
					)).thenReturn(response);

			PayloadSaldo payload = service.getLetturaSaldo(14537780);

			assertEquals( "99999",payload.getIban());

		} catch (AccountException e) {
			fail();
		}
	}
	
	@Test
	public void getListaTransazioniTest() {
		
		
		try {
			TransazioneResponse trxResponse = new TransazioneResponse();
			trxResponse.setStatus("OK");
			Payload payload = new Payload();
			ArrayList<Trx> listaTranssazioni = new ArrayList<Trx>();
			Trx trx1 = new Trx();
			trx1.setAmount(1000);
			trx1.setDescription("pagamento stipendio");
			//TODO: inserire altri campi da testare
			listaTranssazioni.add(trx1);
			
			payload.setList(listaTranssazioni);
			trxResponse.setPayload(payload);
			ResponseEntity<TransazioneResponse> resp = new ResponseEntity<TransazioneResponse>(trxResponse,HttpStatus.OK);
//			resp = restTemplate.exchange(url,
//					HttpMethod.GET, request,
//					TransazioneResponse.class);
//			
			Mockito.when(restTemplate.exchange(Mockito.anyString(),
					Mockito.any(),
					ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(Class.class),
					ArgumentMatchers.any(Class.class)
					)).thenReturn(resp);
			
			List<Trx> listaTrx = service.getListaTransazioni(14537780, "2022-01-10", "2022-01-20");
			
			assertEquals(1000, resp.getBody().getPayload().getList().get(0).getAmount());
			assertEquals("pagamento stipendio", resp.getBody().getPayload().getList().get(0).getDescription());
			
		} catch (AccountException e) {
			fail();
		}
	}
	
	@Test
	public void bonificoTest() {
		
		
		FabrickBonificoRequest requestBonifico = new FabrickBonificoRequest();
		requestBonifico.setAmount(999);
		try {

			FabrickAccountResponse fabrickAccountResponse = new FabrickAccountResponse();
			fabrickAccountResponse.setStatus("KO");
			ResponseEntity<FabrickAccountResponse> respAtteso = new ResponseEntity<FabrickAccountResponse>(fabrickAccountResponse,HttpStatus.BAD_REQUEST);

			Mockito.when(restTemplate.postForEntity(Mockito.anyString(),
					Mockito.any(),
					ArgumentMatchers.any(Class.class)		
					)).thenReturn(respAtteso);
			
			BonificoResponse resp = service.bonifico(14537780, requestBonifico);
			
			
			assertEquals("Errore tecnico  La condizione BP049 non e' prevista per il conto", resp.getDescription());
			assertEquals("KO", resp.getCode());
		} catch (AccountException e) {
			fail();
		}
	}

}
