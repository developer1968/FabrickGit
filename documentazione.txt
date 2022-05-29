Il presente progetto rappresenta un Test di accesso alle API di Fabrick attraverso gli strumenti offerti da Spring Boot.

Data la semplicità del test si è deciso di creare un singolo controller: FabrickController che implementa le 3 API
richieste più una aggiuntiva per permettere di salvare su DB alcune transazioni.


L'accesso alle API esposte da Fabrick è stata realizzata all' interno di un Service: SaldoService attraverso
lo strumento RestTemplate offerto da Spring.

Il controller utilizzerà appunto questo service per ottenere i dati da Fabrick.

Per gestire le informazioni ritornate dalle API Fabrick sono state create delle classi a partire dai Json richiesti
come input o attesi come risposte. Probabilmente, per lo scopo del test, sarebbe bastato recuperare i valori di 
ritorno come semplice JSon e recuperare soltanto le informazioni richieste, ma si è preferito implementare una soluzione
che possa essere effettivamente usata dopo un breve periodo di ingegnerizzazione.

Purtroppo la API https://sandbox.platfr.io//api/gbs/banking/v4.0/accounts/14537780/transactions, dopo aver funzionato
adesso non ritorna alcuna transazione per il conto di prova per qualsivoglia range di date. Quindi la API corrispondente
 implementata: http://localhost:8080/accounts/listaTransazioni/14537780 ritorna sempre lista vuota.
 
 Per il punto facoltativo di salvataggio su DB, si è scelto di usare SqlLite come database. Il progetto crea automaticamente un file fabrick 
 sulla root del progetto che può essere facilmente importato da SqlLiteStudio per visualizzarne i record.
 La tecnologia di accesso al DB è JPA utilizzata per la implementazione di un semplice Repository TransazioneRepository
 utilizzato dal service TransazioniService.
 Purtroppo poichè,come detto sopra, la API delle transazioni non ritorna più transazioni, si è semplicemente
 implementato un metodo di test salvare alcune transazioni riempite a codice.
 
 
 
 



