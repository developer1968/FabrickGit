package it.bank.FabrickTest.service;

import java.util.ArrayList;
import java.util.Date;
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
import it.bank.FabrickTest.model.transazioni.TransazioneResponse;
import it.bank.FabrickTest.model.transazioni.Trx;


/*
 * Service per gestire le transazioni
 */
@Service
public class TransazioniService {

	@Autowired
	private RestTemplate restTemplate;

	public ArrayList<Trx> getTransazioni(int accountId, Date fromAccountingDate,Date toAccountingDate) {
		//la fonte dei dati è una API di Fabrick esposta come servizi REST, quindi utilizziamo RestTemplate
		//per comunicare con essa

		//la url del servizio del recupero transazioni
		String url = AccountInfo.endPoint+"/"+AccountInfo.urlBankingAccount+"/"+accountId+"/transactions"
				+ "?fromAccountingDate="+fromAccountingDate+"&"+"toAccountingDate="+toAccountingDate;

		//settiamo nell'header le info per l' autenticazione
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Auth-Schema", AccountInfo.AuthSchema);
		headers.add("apikey", AccountInfo.ApiKey);

		HttpEntity request = new HttpEntity(headers);

		try {
			ResponseEntity<TransazioneResponse> resp;
			resp = restTemplate.exchange(url,
					HttpMethod.GET, request,
					TransazioneResponse.class);

			if (resp.getStatusCode()==HttpStatus.OK) {
				//recuperiamo la lista delle transazioni
				ArrayList<Trx> listaTransazioni = resp.getBody().getPayload().getList();
				return listaTransazioni;
			}

		}
		catch (HttpStatusCodeException  e) {
			String msg = e.getMessage();
			//System.out.println("Error from server: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
			ResponseEntity resp =new ResponseEntity<String>(msg,e.getStatusCode());

			return null;
		}
		catch(Exception e) {
			ResponseEntity resp =new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

			return null;
		}
		return null;
	}

	public void saveTransazioniNelPeriodo(int accountId, Date fromAccountingDate,Date toAccountingDate) {
		//recupera la lista delle transazioni relative al conto accountId nel periodo  fromAccountingDate toAccountingDate
		//e le storicizza su DB

	}

}
