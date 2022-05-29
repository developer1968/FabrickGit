package it.bank.FabrickTest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import it.bank.FabrickTest.constants.AccountInfo;
import it.bank.FabrickTest.entity.Transazione;
import it.bank.FabrickTest.exceptions.AccountException;
import it.bank.FabrickTest.model.BonificoResponse;
import it.bank.FabrickTest.model.FabrickAccountResponse;
import it.bank.FabrickTest.model.FabrickBonificoRequest;
import it.bank.FabrickTest.model.PayloadSaldo;
import it.bank.FabrickTest.model.transazioni.TransazioneResponse;
import it.bank.FabrickTest.model.transazioni.Trx;
import it.bank.FabrickTest.repository.TransazioneRepository;

@Service
public class SaldoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TransazioneRepository trxRepo;
	
	public PayloadSaldo getLetturaSaldo(int accountId) throws AccountException {
		String url = AccountInfo.endPoint+"/"+AccountInfo.urlBankingAccount+"/"+accountId;
		
		//settiamo nell'header le info per l' autenticazione
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Auth-Schema", AccountInfo.AuthSchema);
		headers.add("apikey", AccountInfo.ApiKey);
		
		ResponseEntity<FabrickAccountResponse> resp = restTemplate.exchange(url,
				HttpMethod.GET, new HttpEntity<Object>(headers),
				FabrickAccountResponse.class);
		
		if (resp.getStatusCode()==HttpStatus.OK) {
			PayloadSaldo payload = resp.getBody().getPayload();
			return payload;
		}
		else {
			throw new AccountException("Errore :"+resp.getStatusCodeValue());
		}
	}
	
	
	public List<Trx> getListaTransazioni(Integer accountId, String fromAccountingDate,String toAccountingDate)  throws AccountException {
		String url = AccountInfo.endPoint+"/"+AccountInfo.urlBankingAccount+"/"+accountId+"/transactions"
				+ "?fromAccountingDate="+fromAccountingDate+"&"+"toAccountingDate="+toAccountingDate;

		//settiamo nell'header le info per l' autenticazione
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Auth-Schema", AccountInfo.AuthSchema);
		headers.add("apikey", AccountInfo.ApiKey);

		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<TransazioneResponse> resp;
		resp = restTemplate.exchange(url,
				HttpMethod.GET, request,
				TransazioneResponse.class);

		if (resp.getStatusCode()==HttpStatus.OK) {
			List<Trx> listaTrx = resp.getBody().getPayload().getList();
			return listaTrx;
		}
		else {
			throw new AccountException("Errore :"+resp.getStatusCodeValue());
		}
	}
	
	
	public BonificoResponse bonifico(Integer accountId, FabrickBonificoRequest requestBonifico) throws AccountException {
		String url = AccountInfo.endPoint+"/"+AccountInfo.urlBankingAccount+"/"+accountId+"/payments/money-transfers";

		
		//settiamo nell'header le info per l' autenticazione
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Auth-Schema", AccountInfo.AuthSchema);
		headers.add("apikey", AccountInfo.ApiKey);
		
		
		HttpEntity<FabrickBonificoRequest> request = new HttpEntity<>(requestBonifico, headers);
	     
	   
		ResponseEntity<FabrickAccountResponse> resp = restTemplate.postForEntity(url, request, FabrickAccountResponse.class);
		if (resp.getStatusCode()==HttpStatus.OK) {
			PayloadSaldo payload = resp.getBody().getPayload();
			
			BonificoResponse bonificoResponse=new BonificoResponse();
			bonificoResponse.setCode("OK");
			bonificoResponse.setDescription("");
			return bonificoResponse;
			
		}
		else {
			BonificoResponse bonificoResponse=new BonificoResponse();
			bonificoResponse.setCode("KO");
			bonificoResponse.setDescription("Errore tecnico  La condizione BP049 non e' prevista per il conto");
			return bonificoResponse;
		}
	}
	
	/*
	 * Salva su DB le transazioni passate come argomento
	 */
	public void saveTransazioni(List<Trx> transazioni) {
		for (Trx transazione : transazioni) {
			//creo una istanza della Entity Transazione per poterla salvare sul DB
			Transazione trxRecord=new Transazione();
			trxRecord.setTransactionId(transazione.getTransactionId());
			trxRecord.setOperationId(transazione.getOperationId());
			trxRecord.setValueDate(transazione.getValueDate());
			trxRecord.setDescription(transazione.getDescription());
			trxRecord.setCurrency(transazione.getCurrency());
			trxRecord.setAmount(transazione.getAmount());
			trxRecord.setAccountingDate(transazione.getAccountingDate());
			
			trxRepo.save(trxRecord);
		}
	}
}
