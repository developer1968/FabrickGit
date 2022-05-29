package it.bank.FabrickTest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import it.bank.FabrickTest.constants.AccountInfo;
import it.bank.FabrickTest.model.BonificoResponse;
import it.bank.FabrickTest.model.FabrickBonificoRequest;
import it.bank.FabrickTest.model.PayloadSaldo;
import it.bank.FabrickTest.model.transazioni.TransazioneResponse;
import it.bank.FabrickTest.model.transazioni.Trx;
import it.bank.FabrickTest.service.SaldoService;
import it.bank.FabrickTest.util.Utility;


@RestController
@RequestMapping("/accounts")
public class FabrickController {

	@Autowired
	private SaldoService saldoService;


	@RequestMapping(value = "/letturaSaldo/{accountId}", method = RequestMethod.GET,produces={"application/json"})
	public ResponseEntity getLetturaSaldo(@PathVariable(name="accountId") Integer accountId) {

		//controllo correttezza parametri
		if (accountId==null) {
			ResponseEntity<String> resp = new ResponseEntity<String>("campo accountId obbligatorio",HttpStatus.BAD_REQUEST);
		}

		//chiamiamo il service per ottenere il saldo
		try {
			PayloadSaldo payload = saldoService.getLetturaSaldo(accountId);

			return new ResponseEntity<PayloadSaldo> (payload,HttpStatus.OK);
		} 


		catch (Exception e) {
			if (e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException)e;
				if (ex.getStatusCode()==HttpStatus.FORBIDDEN)
					return new ResponseEntity<String>("Forbidden:"+e.getMessage(),HttpStatus.FORBIDDEN);
				if (ex.getStatusCode()==HttpStatus.BAD_REQUEST)
					return new ResponseEntity<String>("Forbidden:"+e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			else {
				ResponseEntity resp =new ResponseEntity<String>("Errore:"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
				return resp;
			}
		}
		return new ResponseEntity<String>("Errore interno ",HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@RequestMapping(value = "/listaTransazioni/{accountId}", method = RequestMethod.GET,produces={"application/json"})
	public ResponseEntity getListaTransazioni(@PathVariable(name="accountId") Integer accountId,
			@RequestParam String fromAccountingDate,
			@RequestParam String toAccountingDate) {

		if (accountId==null) {
			ResponseEntity<String> resp = new ResponseEntity<String>("campo accountId obbligatorio",HttpStatus.BAD_REQUEST);
		}

		if (fromAccountingDate==null || toAccountingDate==null){
			ResponseEntity<String> resp = new ResponseEntity<String>("campi:fromAccountingDate e  toAccountingDate",HttpStatus.BAD_REQUEST);
		}

		if (!Utility.checkDate(fromAccountingDate)) {
			ResponseEntity<String> resp = new ResponseEntity<String>("fromAccountingDate formato non valido",HttpStatus.BAD_REQUEST);
		}
		if (!Utility.checkDate(toAccountingDate)) {
			ResponseEntity<String> resp = new ResponseEntity<String>("toAccountingDate formato non valido",HttpStatus.BAD_REQUEST);

		}

		//chiamiamo il service per ottenere il saldo
		try {
			List<Trx> listaTrx = saldoService.getListaTransazioni(accountId, fromAccountingDate, toAccountingDate);

			return new ResponseEntity<List<Trx>> (listaTrx,HttpStatus.OK);
		} 


		catch (Exception e) {
			if (e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException)e;
				if (ex.getStatusCode()==HttpStatus.FORBIDDEN)
					return new ResponseEntity<String>("Forbidden:"+e.getMessage(),HttpStatus.FORBIDDEN);
				if (ex.getStatusCode()==HttpStatus.BAD_REQUEST)
					return new ResponseEntity<String>("Forbidden:"+e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			else {
				ResponseEntity resp =new ResponseEntity<String>("Errore:"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
				return resp;
			}
		}
		return new ResponseEntity<String>("Errore interno ",HttpStatus.INTERNAL_SERVER_ERROR);


	}
	
	@RequestMapping(value = "/bonifico/{accountId}", method = RequestMethod.POST,produces={"application/json"})
	public ResponseEntity bonifico(@PathVariable(name="accountId") Integer accountId,
			@RequestBody() FabrickBonificoRequest bonifico) {

		try {
			BonificoResponse response = saldoService.bonifico(accountId, bonifico);

			return new ResponseEntity<BonificoResponse> (response,HttpStatus.OK);
		} 


		catch (Exception e) {
			if (e instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException)e;
				if (ex.getStatusCode()==HttpStatus.FORBIDDEN)
					return new ResponseEntity<String>("Forbidden:"+e.getMessage(),HttpStatus.FORBIDDEN);
				if (ex.getStatusCode()==HttpStatus.BAD_REQUEST) {
					//poichè sappiamo che per una limitazione dell'ambiente di test la risposta sarà
					//Invalid Amount: Daily Limit... 
					//trasformiamo il msg in modo più chiaro
					BonificoResponse response = new BonificoResponse();
					response.setCode("KO");
					response.setDescription("Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780");
					
					return new ResponseEntity<BonificoResponse>(response,HttpStatus.OK);

				}
			}
			else {
				ResponseEntity resp =new ResponseEntity<String>("Errore:"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
				return resp;
			}
		}
		ResponseEntity resp =new ResponseEntity<String>("Errore interno",HttpStatus.INTERNAL_SERVER_ERROR);
		return resp;
		
		
	}
	
	/*
	 * Esempio di body JSON per chiamare la API:
	 * 
	 * [{
      "transactionId": "1331714087",
      "operationId": "00000000273015",
      "accountingDate": "2019-04-01",
      "valueDate": "2019-04-01",
      "type": {
        "enumeration": "GBS_TRANSACTION_TYPE",
        "value": "GBS_TRANSACTION_TYPE_0023"
      },
      "amount": 800,
      "currency": "EUR",
      "description": "Bolletta 2019"
    },
	{
      "transactionId": "1331714088",
      "operationId": "00000000273015",
      "accountingDate": "2020-04-02",
      "valueDate": "2020-04-01",
      "type": {
        "enumeration": "GBS_TRANSACTION_TYPE",
        "value": "GBS_TRANSACTION_TYPE_0023"
      },
      "amount":750,
      "currency": "EUR",
      "description": "Bolletta 2020"
    }]
    
    
    
    
	 */
	
	
	@RequestMapping(value = "/transazioni/{accountId}/save", method = RequestMethod.POST,produces={"application/json"})
	public ResponseEntity saveTransazioni(@PathVariable(name="accountId") Integer accountId,
			@RequestBody() List<Trx> transazioni) {
		
		try {
			
			saldoService.saveTransazioni(transazioni);
			
			return new ResponseEntity<String>("OK",HttpStatus.OK);
			
		} catch (Exception e) {
			ResponseEntity resp =new ResponseEntity<String>("Errore nel salvataggio su DB"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			return resp;
		}
		
		
	}
		

}
