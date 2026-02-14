#   
****CODE SMELLS****  
Identifica una caratteristica del codice di un programma che indica la possibile presenza di problemi seri  
La determinazione di un code small è soggettiva  
  
**METODI TROPPO LUNGHI**  
++Sintomi:++ Un metodo più lungo di 10/15 righe, il metodo probabilmente è sbagliato  
++Perchè?:++ Cattiva organizzazione del codice, codice ripetuto, difficile da testare e mantenere  
**++Contromisura++**: **Dividere il metodo**  
  
++Consigli?++   
- Aggiungere commenti all’interno del metodo è il segno che quel pezzo di codice può essere messo in un metodo a parte.  
- Utilizzare metodi semplici, con un nome significativo  
- Fattorizzare il codice creando metodi di utilità (Al posto di avere un metodo lungo posso creare un oggetto con metodi che portano ad eseguire gli stessi compiti del metodo grande)  
  
  
**CLASSI GRANDI**  
++Sintomi:++ Classe con molti campi, metodi e linee  
++Perchè?:++ Se una classe è troppo grande probabilmente avrà più responsabilità  
**++Contromisura++**: Dividere la classe   
++Consigli?:++  
- Spostare alcuni comportamenti fuori dalla classe  
- Estrarre sottoclassi o interfacce  
- Usare, se possibile, composizione tra oggetti  
  
  
**OSSESSIONE DEI TIPI PRIMITIVI**  
++Sintomi++: Uso di tipi primitivi, costanti o loro array per rappresentare informazioni strutturate  
**++Contromisura:++** **Creare oggetti **  
++Consigli?++  
- Se un dato primitivo ha delle logiche di validazione o formattazione, estrailo in una classe specifica  
  
  
**LUNGA LISTA DI PARAMETRI**  
++Sintomi++: Metodi con più di 3/4 parametri   
**++Contromisura:++** ** Unirli in un oggetto che li rappresenta come parametro unico**  
++Consigli?++  
- Se i parametri vengono da un oggetto, passa direttamente l’oggetto  
- Se i parametri sono slegati, crea un oggetto apposito per raggrupparli   
  
  
**DATA CLUMPS**  
++Sintomi++: Uso di un gruppo di variabili distinte, dipendenti tra loro (es. le coordinate)  
**++Contromisura:++** ** Creare oggetti**  
++Consigli? ++  
- Estrai una classe che contiene i campi  
- Cerca metodi che li manipolano e spostali nella nuova classe  
  
  
**CAMPI TEMPORANEI**  
++Sintomi++: Uso di campi un una classe che sono usati solo in alcune circostanze altrimenti vuoti/ignorati  
**++Contromisura:++** ** Valuta necessita, valuta se passarlo, incapsulalo (in un metodo o una classe piccola)**  
++Consigli? ++  
- Estrai il codice che usa questi campo in una classe dedicata, così avranno senso di esistere per tutto i cliclo di vita del nuovo oggetto  
  
  
**EREDITA’ RIFIUTATA**  
++Sintomi++: Una classe utilizza solo alcuni metodi della sovraclasse   
**++Contromisura:++** ** Valutare concetti, introdurre interfacce/classi**  
++Consigli?++  
- Sostituisci ereditarietà con delega (sposta i metodi in un campo delegato e rimuovi “extends”  
- Se eredità e corretta, ma gerarchia strutturata male, crea una superclasse intermedia che contiene solo i metodi condivisi  
  
  
**CLASSI DIVERSE, STESSA INTERFACCIA**  
++Sintomi++: Due classi svolgono lo stesso lavoro, ma con interfacce differenti   
**++Contromisura:++** ** Rinomina metodo e sposta metodi**  
++Consigli?++   
- Uniforma i nomi dei metodi in entrambi le classe. Se il codice è duplicato, estrai una superclasse  
  
  
**MODIFICHE A CASCATA**  
++Sintomi++: Si è costretti a modificare diversi metodi, spesso non correlati, a seguito della modifica di una singola classe  
**++Contromisura:++** ** Sposta metodo e sposta campo**  
++Consigli?++   
- L’obbiettivo è spostare tutta la logica sparsa in un unica classe responsabile, così la modifica avviene in un solo punto  
  
  
**GERARCHIE PARALLELE**  
++Sintomi++: Se si vuole estendere una classe A si è costretti ad estendere anche una classe B  
**++Contromisura ++Sposta metodo e sposta campo**  
++Consigli?++   
- Cerca di far riferire una gerarchia all’altra, così da eliminare la necessità di una delle due gerarchie  
  
  
**COMMENTI ECCESSIVI**  
++Sintomi++: Un metodo è pieno di commenti, noi avremmo bisogno solo dei metodi descritti nel Javadoc   
**++Contromisura:++ Estrai metodo e Rinomina metodo**  
++Consigli?++   
- Se si sente il bisogno di scrivere un commento per spiegare cosa fa un blocco di codice, trasforma quel blocco in un metodo con un nome che spiega cosa fa  
  
  
**CODICE DUPLICATO**  
++Sintomi++: Più pezzi di codice sostanzialmente identico  
**++Contromisura++: Estrai metodo **  
++Consigli?++   
- Stessa classe: Estrai il codice in un metodo privato  
- Classi sorelle: Estrai il metodo e portalo in una superclasse  
  
  
**CODICE MORTO**  
++Sintomi++: Classi, metodi o campi che non vengono usati  
**++Contromisura++**++:++** Cancella il codice**  
  
  
**INDIVIDA DELLE CARATTERISTICHE**  
++Sintomi++: Un metodo utilizza maggiormente i dati di un altro oggetti che i propri  
**++Contromisura:++ Sposta metodo**  
++Consigli?++   
- Sposta il metodo nella classe che contiene i dati che sta usando  
  
  
**INTIMITA’ NON APPROPRIATA**  
++Sintomi++: Una classe utilizza i campi o metodi di un altra classe sistematicamente   
**++Contromisura:++ Sposta metodo e Sposta campo**  
++Consigli?++   
- Sposta le parti condivise in una nuova classe o usa la delega per nascondere dettagli interni  
