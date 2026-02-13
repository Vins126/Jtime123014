# MODELLAZIONE - Loreti  
  
- [ ] 23-04-> 1h 30 min -> 90min  
- [ ] 29-04-> 1h 30 min -> 90min  
  
- [x] 11-06-> 1h 40 min -> 100min  
- [x] 29-05-> 1h 30 min -> 90min  
- [x] 21-05-> 1h 30 min -> 90min  
- [x] 14-05-> 1h 30 min -> 90min  
- [x] 13-05-> 45 min  
- [x] 30-04-> 1h -> 60min  
- [x] 16-04-> 45 min  
- [x] 15-04-> 1h 40 min -> 100min  
- [x] 02-04-> 3h -> 180min  
- [x] 01-04->1h 30min -> 90min  
- [x] 27-03-> 1h 30min -> 90 min  
- [x] 13-03-> 1h 30 min -> 90min  
- [x] 12-03-> 3h -> 180min  
- [x] 11-03-> 1h 30 min ->90min  
- [x] 5-03-> 2h 30min -> 150min  
- [x] 4-03-> 45min  
****TOTALE 31H****   
Un oggetto immutabile viene usato per rappresentare concetti che non hanno un concetto di stato  
  
**Record**: viene usato per definire oggetti immutabili. Semplifica la scrittura del codice. Io posso definire una classe che ha automaticamente i metodi get. La classe così definita diventerà final  
```
ES. Record Position(int row, int column){}

```
  
Lo stato di un oggetto può essere modificato SOLAMENTE dall’oggetto stesso. Per far si che le informazioni dell’oggetto rimangano sempre coerenti   
  
Le regole generali per la definizione di una classe sono:  
- Usare solo campi private e protected  
- Fornire metodi getter e setter  
In questo modo è possibile controllare al meglio l’accesso alle informazioni e la correttezza dei dati  
  
Quando si progetta una classe dobbiamo tenere in mente una serie di funzionalità, **di conseguenza** i metodi inclusi nella classe devono fornire un implementazione per le funzionalità messe a disposizione dalla classe  
  
**Quando sviluppiamo **del codice dobbiamo fare **sempre delle assunzioni**  
- Sullo stato del’oggetto  
- Sui parametri che un metodo riceve  
- Sul risultati dell’esecuzione del metodo  
  
  
**Esistono due tipi di assunzioni**:  
- Invarianti di classe: proprietà che ogni oggetto verifica per tutta la sua esistenza  
- Contratti dei metodi: garanzia sull’effetto dell’esecuzione di un metodo  quando invocato con parametri che hanno determinate condizioni   
  
Quasi tutti i linguaggi di programmazione non consente di specificare i contratti. Per questo motivo solitamente le informazioni vengono inserite nel javadoc  
Nasce così la programmazione difensiva, ossia all’inizio di ogni metodo si fanno dei controlli per verificare che le condizioni d’uso siano rispettate  
I **controlli** devono essere fatti **solo** nei **metodi pubblici**, per questo devono essere limitati   
Quando un metodo identifica una violazione si può restituire un codice d’errore o tramite le eccezioni. Uno dei metodi nella catena delle chiamate si occuperà di gestire le varie eccezioni.  
La verifica di un contratto è indefinibile, per cui possiamo fare analisi del codice (tool) o test  
  
Il Javadoc serve ad identificare il contenuto di una classe, esso è un commento particolare. Al loro interno possiamo inserire anche dei tag  
Essi devono avere:  
- Descrizione sommaria del metodo (e del suo contratto)  
- Descrizione del ruolo di ogni parametro  
- Descrizione del valore di ritorno  
- Descrizione delle eccezioni sollevate  
![deprecate inserend orase limina da una](Attachments/9AC2C345-04A0-412E-B75E-97C470C8DA6A.png)  
  
  
Quando bisogna effettuare una ricerca abbiamo due opzioni:  
- La ricerca **esaustiva** che trova la sequenza di mosse per una soluzione  
- La ricerca **limitata** che con un numero limitato di passi trova la mossa migliore  
In questo modo potremo confrontare diverse soluzioni o fornire diverse opzioni  
  
Il vantaggio di partire dalla programmazione con le interfacce è la possibilità di patire con una soluzione che è corretta dal punto di vista architetturale  
  
Le **interfacce** sono un meccanismo per definire un contratto. Per questo un risolutore è un oggetto che fornisce un metodo che, secondo uno schema, restituisce una mossa da eseguire  
Utilizzando un interfaccia si astrae dalla sua reale implementazione, questo meccanismo è chiamato **polimorfismo**. Questo rende un risolutore valido anche per altre implementazioni.  
Un interfaccia può estenderne un altra  
  
Una classe può implementare qualsiasi numero di interfacce e dovrà fornire tutte le implementazioni dell’interfaccia.  
**Se incappiamo in conflitto tra nomi**, quando implemento due metodi con lo stesso nome ne viene riscritto solo uno unico. Se però i metodi hanno contratti differenti ci possono essere seri problemi  
  
Un interfaccia può contenere anche delle costanti (di base *++public static final)++ *e sono accessibili tramite il “.”   
Oltre i metodi astratti possiamo dichiarare metodi:  
-**Statici**  
Permette di fornire funzionalità generiche (es. i factory methods)  
-**Di default**  
Verranno aggiunti automaticamente ad ogni classe quando viene implementata un interfaccia. Molto utile per gestire evoluzione delle interfacce  
-**Privati**  
Serve per aggiungere funzionalità che verranno copiate nella classe ed usati solamente dei ++Default++  
Sono però da evitare il più possibile   
Le interfacce sono la prima caso a cui pensare, devono far emergere i concetti centrali del software. Dobbiamo rappresentare i dati da trattare e le componenti che devono usare questi dati  
  
—  
****Game of life****  
  
![Un esempio: Il gioco della vita](Attachments/C711B7A5-455A-4AA3-80CF-A985FF26ED57.png)  
Per rappresentare il gioco dobbiamo capire:  
- Di cosa abbiamo bisogno per implementare il gioco (tipo di dato, oggetti ecc)?  
- Quali funzionalità mettere a disposizione e come interagiscono tra loro?  
- Quali sono le informazioni nel loro stato?  
Usare le interfacce consente di rispondere in modo incrementale alle domande raggiungendo una soluzione per raffinamenti successivi  
  
Di cosa abbiamo bisogno?  
- Il campo  
- La cella  
- Lo stato della cella  
- Posizione della cella  
- Le regole  
Come li rappresentiamo?  
- Il campo: Interfaccia  
- La cella: Interfaccia  
- Stato della cella: Enumerazione  
- Posizione della cella: Record  
- Le regole: Interfaccia  
  
————————  
  
Estendere una classe permette di aggiungere funzionalità senza dover riscrivere tutto  
  
Una sottoclasse può definire dei costruttori, questi devono invocare i costruttori della classe base per poter garantire la creazioni dei campi privati  
Se la classe base mette a disposizione un costruttore di default allora può essere omessa. Se questo non è disponibile la classe derivata deve necessariamente definire un costruttore e invocare il costruttore della classe base  
  
È sempre possibile assegnare ad una variabile di tipo *++classe base++* un oggetto della classe derivata  
Ogni variabile in Java ha quindi tue tipi:  
- Il tipo statico: quello assegnato dal compilatore al momento della compilazione  
- Il tipo dinamico: il tipo degli oggetti a cui fa riferimento la variabile  
  
OVERLOAD DEI METODI  
Un metodo viene identificata tramite la sua **signature** costituita da:  
- Nome del metodo  
- Numero e tipo dei parametri  
  
DYNAMIC LOOKUP  
Per alcune situazione è meglio evitare di estendere classi ma piuttosto richiamare un istanza di una classe al suo interno, così da non dover estendere tutto e rendere tutto molto più solido  
Ad ogni classe java viene associata una tabella di metodi.  
Quando usiamo un metodo il compilatore individua la segnature specifica che stiamo usando, e la selezione viene fatta per mezzo del tipo statico delle espressioni  
Quando un oggetto riceve un invocazione il metodo da invocare viene selezionato sulla base della signature, la ricerca avviene usando il tipo dinamico del ricevente   
   
  
  
![Consideriamo le seguenti classe:](Attachments/45CE585D-31A5-487C-9E46-ED0BDB63A756.png)  
![Consideriamo le seguenti classe:](Attachments/B70A9D1F-66FF-4C82-AA44-2F5BAE4F8A3D.png)  
  
  
SPIEGAZIONE SCREEN SOTTOSTANTE.  
Qui è da fare attenzione alla signature, quando io chiamo d.m(c) io sto passando c che è di tipo “ClasseA”, per tanto richiamo il metodo “m” che ha signature “classe A” come parametro, per tanto ottengo quel dato output.  
Istanziare quindi un oggetto di un determinato tipo, passando l’oggetto di una superclasse estende le funzionalità (la tabella) ma NON il tipo con cui viene riconosciuto  
  
![Consideriamo ora la seguente definizione:](Attachments/1A1054CB-161F-49EB-B28B-47B2DED595AC.png)  
  
****MODIFICATORE FINAL****  
Quando un metodo viene dichiarato *final* **nessuna sottoclasse** può sovrascrivero  
  
****METODI E CLASSI ASTRATTE****  
Una classe può definire un metodo senza un implementazione, forzando le sottoclassi a fornirne una implementazione  
Per esempio si può estendere la classe “studente” che estende la classe “persona”  
  
Sia le classi astratte che le interfacce sono uno strumento di astrazione  
**Quando usare una o l’altra?**  
Le ++interfacce++ vanno usate per definire il contratto generale degli oggetti che vogliamo definire  
Le ++classi astratte++*** ***vanno usate quando vogliamo definire un comportamento i cui elementi sono fissati:  
- Vogliamo definire un comportamento comune con uno stato comune definito  
- Abbiamo un comportamento che dipende da alcuni metodi da personalizzare  
  
  
**TIPI GENERICI** servono per definire una struttura dati la cui definizione è indipendente dal suo contenuto  
  
**L’arrayList** è un tipo generico che implementa una lista contenente elementi di tipo T  
  
**Logica del prim’ordine** Non ci ho capito un cazzo, cercala  
  
**Logica del secondo ordine**: Logica in cui quantifico sugli insiemi delle variabili.  
  
Un metodo generico è un metodo definito indipendentemente dal tipo specifico che dichiariamo .  
In questo caso è il compilatore che determina se il tipo che andiamo ad utilizzare è giusto.  
  
Talvolta è utile passare in modo esplicito il parametro del tipo del metodo  
![Arrays.<String>swap(friends, 0,1);](Attachments/5ACE7BE3-CD0E-4F2E-A016-DFBF8A07AEA2.png)  
  
Quando usiamo “…” indichiamo che stiamo passando un array di valori   
ES.↘️  
![public static <> TI Swap(int i, int i, T... values) ( 1 usage](Attachments/F9B52121-2C27-45D2-A5AE-1025C6F3FFB0.png)  
  
La type inference nei metodi parte dal primo tipo che trova nel generico (ad esempio se al posto di “A” metto 1, considererà in automatico gli int, di conseguenza dovremo adattare la struttura esterna)  
  
**TYPE BOUND**  
A volte **il parametro di tipo** di una classe o metodo d**eve soddisfare alcuni vincoli**, per cui in questi casi possiamo specificare un type bound per chiedere che il tipo utilizzando estenda o implementi interfacce.  
Un parametro di tipo può avere anche bound multipli  
![public static <T extends AutoCloseable>](Attachments/3D357000-2BD9-427E-971D-277CA79D8A8F.png)  
  
 Le **wildcards** permettono di specificare un generico che estende una classe base, utilizzando quindi i metodi della classe base poiché qualsiasi parametro dato, se valido, rispetterà sicuramente i vincoli  
![La wildcard ? extends ClasseA indica che il parametro arg sarà di tipo ArrayList<T>](Attachments/BBB18EB4-AAEC-4843-ABD2-1C09E10FE51F.png)  
  
**Supertype wildcards**  
Possiamo usare le wildcard per richiedere che un dato parametro sia sopratipo di un altro tipo usando “super”  
“<? super ClasseB>” ci indica che possiamo usare classe B ma anche altre classi che lo estendono   
  
**Type Erasure**  
In Java i tipi generici esistono solo a tempo di Coding.  
Quando una classe generica è compilata tutte le info sul tipo generico sono persi e le classi sono trasformati in tipi raw  
  
**MATCHING DI TIPO**  
Meccanismo per limitare l’uso di cast   
![espressione.](Attachments/E3AE5A70-F75A-4911-A679-8475F14A9153.png)  
Possiamo addirittura utilizzare il costrutto “when”  
![switch (obj) {](Attachments/C2D25372-B2BB-497D-859D-AE7AE67D3529.png)  
****Perchè dobbiamo usare questo tipo di struttura?****  
Supponiamo di voler rappresentare l’insieme delle espressioni generati dalla seguente grammatica:   
![expr=nEN #x xexprtexpr expr-expr expr*expr](Attachments/D00271BB-C3C5-481E-BC0E-27D3097FFC34.png)  
Per rappresentarla in termini del linguaggio:  
- Usiamo un’interfaccia per rappresentare l’insieme delle espressioni  
- Definiamo una classe per ogni regola della grammatica  
  
Vale in pratica ci generi un programma, ma non ho Caputo bene come. Lezione 16-04-2025  
——  
  
****LIMITARE ESTENDIBILITA’****  
  
Per limitare il numero di classi che implementano un interfaccia possiamo usare il modificatore “*++sealed++*”  
![public sealed interface AnInterface permits ClassA, ClassB {](Attachments/B9EC6B69-89B4-4A0B-B2E5-588E7DD794F9.png)  
Una classe permitted dovrà essere:  
*   final, per impedire che altre classi la possano estendere;  
*   sealed, con la lista delle classi permits;  
*   non-sealed, per consentire ulteriori estensioni.  
I *record* dato che sono final possono essere usate come classi *++sealed++*  
Tutte le classi devono essere definite nello stesso pacchetto (o modulo)  
  
Con le classi sealed possiamo usare un pattern matching esaustivo  
Es.↘️  
![public class integertvaluator implenents Evaluatore nteger>"](Attachments/3A3366C2-BA0E-4FD9-8669-0CA72CEBB962.png)  
Se il prof chiede cosa sono le classi sealed e come funzionano DOBBIAMO RISPONDERE BENE, per cui rivedere bene tutta la spiegazione (Lezione 16-04-2025 min 45 ( o prima) )  
—————————————————————————————————————————————————————————  
  
## ==LEZIONI DA INTEGRARE==  
- [ ] ==23-04-> 1h 30 min -> 90min==  
- [ ] ==29-04-> 1h 30 min -> 90min==  
  
**Interfacce funzionali**  
Queste interfacce hanno un solo metodo astratto. Ossia un metodo di cui non conosciamo l’implemntazione ma questa interfaccia poterebbe avere altri metodi di default (metodi che implementano altre funzionalità)  
Nella dichiarazionne dobbiamo aggiungere il tag *@FunctionalInterface*, questo serve a compile tipe per verificare che le cose siano state fatte bene  
  
**LAMBDA**  
Le interfacce funzionali possono essere instanziate per mezzo di *++espressioni lambda++*  
Sono porzioni di codice che vengono opportunamente gestite dal compilatore per generare oggetti a runtime** **  
Sintassi:↘️  
![(arg1,...,argn) → expr](Attachments/14DE2138-9411-4226-BFCC-ED3EB55D3340.png)  
Il tipo di un metodo NON è il suo tipo di ritorno ma è tupla di tipi parametri “freccia” tipo di ritorno   
![Il tipo di un metodo in Java è definito come:](Attachments/CC9C4D0E-EA8A-47BD-8BDD-791140C319DB.png)  
  
Un interfaccia funzionale può essere rimpiazzata da un riferimento ad un metodo  
Possibili sintassi:  
![• NomeClasse: : nomeMetodo, dove nomeMetodo è un metodo di istanza.](Attachments/0B00EDD5-EE07-463C-A2D0-30F382C4372F.png)  
  
![Alcue interfacce funzionali...](Attachments/78EC6C7E-25B4-464C-8624-8C2CD05943F7.png)  
  
Se stiamo facendo il refactor aggiungendo un generico ci possono essere problemi ad esempio se dobbiamo creare un array poiché non è possibile instanziare un array di generici.  
Entrano quindi in gioco le interfacce funzionali.  
Possiamo ad esempio avere un costruttore così definito  
![protected final Intfunction‹S> arrayfactory; 1 usage](Attachments/560BE07E-3575-4A39-AE0F-7C0CDEB63654.png)  
In questo modo poi in una situazione in cui dobbiamo instanziare un array possiamo fare questa cosa  
![private St] getStatusof (SetsT> neighbours) { 1 usage](Attachments/E39BF788-5EC3-432C-9A0B-930A3F27969C.png)  
  
  
—————————————————————————————————————————————————————————  
****PRINCIPI SOLID****  
  
  
****SINGOLA RESPONSABILITA’****  
Ci permette di capire quando un software è fatto in modo corretto poiché una ogni classe deve avere una sola responsabilità  
  
****Principio OPEN-CLOSED****  
Non dobbiamo conoscere come la classe è implementata ma devo poter estendere la classe in modo opportuno  
  
Un modulo è detto:  
- APERTO: quando permette estensioni  
- CHIUSO: se è disponibile per l’uso da parte di altre componenti senza doverne conoscere l’implementazioni   
![Esempio di violazione:](Attachments/359EC66B-300F-4AC4-8742-6E5B9BF21BD8.png)  
![Refactoring:](Attachments/1BFBF514-9715-4A4D-9077-32D412055BE7.png)  
  
  
****PRINCIPIO DI SOSTITUZIONE DI LISKOV****  
In un programma è sempre possibile sostituire le istanze di una classe con le istanze di una sua sottoclasse senza alterare il comportamento del programma   
  
DEFINIZIONE (da imparare a memoria)  
Sia *q(x)* una proprietà verificata da tutti gli oggetti *x* di tipo *T*. Allora *q(y)* sarà verificata da tutti gli oggetti ++y++ di tipo *S* dove *S* è un sottotipo di *T*  
  
IN POCHE PAROLE  
Se io ho una proproetà soddisfatta da tutti gli oggetti di tipo T, allora essa deve essere soddisfatta anche da oggetti di tipo S se S estende T  
  
Esempio:  
![Esempio di violazione:](Attachments/47FB1B71-4828-4EE7-8B2B-3492818CF93A.png)  
Dimostrazione errore: (se io imposto altezza modifico anche larghezza e viceversa con quadrato) quindi non riporta   
   
![public class Class {](Attachments/CD28FEC7-42E6-45AC-9B10-C7216D0CD30E.png)  
  
****SEGREGAZIONE DELLE INTERFACCE****  
Meglio avere interfacce specifiche al posto di una singola interfaccia generale  
  
Il principio dice che nessuna classe dovrebbe essere forzata ad essere dipendente da metodi che non vengono utilizzati  
  
****INVERSIONE DELLE DIPENDENZE****  
Le dipendenze devono essere sempre verso le astrazioni e mai verso le concretizzazioni  
  
Ciò significa che campi e parametri dei metodi pubblici di una classe devono essere SEMPRE astrazioni  
(Es. io non devo ricevere una LinkedList ma una lista)  
  
Il principia afferma che:  
- Le interfacce non dovrebbero dipendere dalle classi concrete. Entrambi dipendono da astrazioni (ammenochèi riferimenti non sono classi final o enumerazioni)  
- Le astrazioni non devono dipendere dai dettagli. I dettagli dovrebbero dipendere dalle astrazioni (gli argomenti dei metodi in un interfaccia devono essere interfacce e non classi concrete)   
  
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
## ——————————————————————————————————————————————  
## GLI STREAM  
Permette di concatenare una serie di operazioni  
  
Vengono utilizzati al meglio per elaborare le strutture dati  
  
Forniscono una vista sui dati la quale permette di specificare delle operazioni ad un livello concettuale più alto rispetto alle collezioni  
  
Specifichiamo cosa vogliamo fare, lasciando la gestione all’implementazione sottostante  
![long count = words](Attachments/DFFC149D-FC94-4145-8461-0AE7BA1AA1D3.png)  
**NON MEMORIZZA** i suoi elementi, ammenochè non decidiamo di farlo  
  
++Non modificano la sorgente++. Sono quindi dette *++LAZY++*  
  
**Flusso di lavoro**  
- Creare uno stream  
- Specificare operazioni intermedie per trasformare lo stream iniziale  
-  Applico un operazione terminale che da un risultato  
- ORA* *Lo stream non può più essere utilizzato  
  
**Creazione di stream**  
MODO 1: Chiedo ad un oggetto di fornirmi uno Steam di informazioni  
MODO 2: Può essere costruito da un array   
MODO 3: Possono essere usati dei generatori. In questo caso sono infiniti. Possono essere creati con i metodi statici della classe “Stream”  
  
Idealmente questo stream contiene TUTTI i numeri della sequenza di Fibonacci, ma per ottenerli devo ovviamente elaborarli   
![Esempi...](Attachments/87E899AD-E77F-4409-ADA3-2CB6CAAC1824.png)  
  
**TRASFORMAZIONE DI STREAM**  
Una trasformazione produce uno stream i cui elementi derivano da quelli di un altro stream  
Es.  
![Metodo map per trastormare ooni elemento.](Attachments/F99E665F-7201-4898-86F5-7CFB506F9A31.png)  
  
**==RIVEDERE MIN 38->49 Lezione 14-05==**  
  
  
**ESTRAZIONE SOTTOSTREAM**  
Dato uno Steam possiamo estrarne un sottostream   
  
![Metodo linit per prendere solo i primi n elementi:](Attachments/AA6C3228-2994-4BAE-8750-4F0F4F34DE63.png)  
  
**COMBINARE STEAM**  
Possibile concatenare gli stream  
  
**ALTRE TRASFORMAZIONI**  
Rimuovere duplicati  
![Stream<T> distinct()](Attachments/41433002-EC99-47B2-8E26-D742E5ED9D0D.png)  
Ordinare elementi  
![Stream<T> sorted() //T implementa Comparable<T>](Attachments/05CC26C5-6154-4C6A-977C-BBC84308273F.png)  
Eseguire un operazioni per ogni elemento   
![Stream<T> forEach(Consumer<? super T> action)](Attachments/843F8EFF-4421-45CD-AEB2-69463B7943E8.png)  
  
**VALORI OPZIONALI**  
Lo Steam può essere definito come NON definito, quindi concettualmente potremmo imbatterci nel NULL (dato che è un valore)  
  
Esiste però Optional<T> che è ++un contenitore per un oggetto T **o nessun** oggetto ++  
Un optional viene prodotto da un metodo che restituisce un valore solo se presente, altrimenti non restituisce nulla  
  
L’interfaccia optional mette a disposizione una serie di metodi   
![T orElse(T other)](Attachments/B32574ED-A15D-4842-AC1F-0F896D0B7370.png)  
  
Da notare come sia presente T ed un Supplier di T, questo può succedere  perchè se passo un supplier l’espressione che passo viene valutata solo nel momento in cui ne abbiamo bisogno  
  
**RIDUZIONI**  
Sono operazioni terminali che riducono lo Steam in qualcosa di concreto   
![Massimo elemento secondo un Comparator:](Attachments/2ADF1DB4-E652-4C7C-9DB0-59C7E606A46D.png)  
Altre operazioni  
![void forEach (Consumer<? super T> action)](Attachments/8A9E787E-0BB6-4698-B307-B806753A2357.png)  
Esistono anche dei Collector   
![• Collectors.toList()](Attachments/FEB7A4CD-1219-4F1C-A9F2-FF1DFE696A98.png)  
  
Abbiamo poi il metodo **reduce** che consente di calcolare un valore da uno stream  
![T reduce(T identity, BinaryüperatoreT> accumulator)](Attachments/B1B40B0A-D2A3-44AD-A53E-ADB7E2911AD4.png)  
  
**STEAM PARALLELI**  
Uno stream che può essere elaborato in parallelo, le cui operazioni possono essere svolte in modo indipendente  
  
Possiamo usare uno *stream parallelo con:*  
![collection.parallelStream()](Attachments/B557F7EF-3562-49AC-A6A6-08DBD3008494.png)  
Qualsiasi stream può essere reso parallelo con:  
![stream.parallel()](Attachments/9DC7EE2E-8E1F-44E1-A720-D3031FAEDD24.png)  
  
Nello stream parallelo, quando viene eseguito il metodo terminale, tutte le operazioni intermedie sono parallelizzate  
  
Possono soffrire di race condition   
  
**STEAM DI TIPI PRIMITIVI**  
Per gestire gli stream di tipi primitivi Java mette a disposizione delle varianti:  
![• IntSt ream, stream in int;](Attachments/CD0F52CF-D887-4592-977F-E6C23DA4089A.png)  
  
——  
****==SPIEGAZIONE ESAME LEZIONE 21-05 MIN 1.02.00==****  
——  
##   
## PROGRAMMAZIONE CONCORRENTE  
  
**MULTITHREADING**  
Il threading è la creazione e la gestione di unità multiple di si esecuzione in un singolo processo  
  
Fonte significativa di errori di programmazione a causa di data race e deadlock  
  
```
BINARI
Programmi inattivi che riesiedono in memoria, pronti per essere eseguiti ma non ancora avviati

PROCESSI
Astrazione del sistema operativo che rappresenta binari in esecuzione

THREAD
Unità di esecuzione di un processo


```
  
Il primo passo per sviluppare ++programmi concorrenti++ è quello di dividere le attività in **task**  
  
Nell’interfaccia Runnable è usato per descrivere un’attività che si vuole eseguire,   
![public interface Runnable {|](Attachments/9734DEAD-5E3D-45F9-85C3-ECED11D1FD43.png)  
  
Il metodo run viene eseguito da un thread  
  
Un task può essere eseguito:  
- In un thread appositamente creato  
- Tramite un executor (un classe che ha come unico obbiettivo quello di eseguire e gestire thread)  
  
**EXECUTOR**  
  
![Le API Java forniscono la classe ExecutorService che consentono l'esecuzione di attività](Attachments/252326DF-5BB2-4B0B-AC6E-B6B39F08A1A9.png)  
  
- FixedThreadPoool: Instanti un numero fisso di thread  
- CachedThreadPoool: Costruisci thread quando ne hai bisogno e poi lo tieni da parte quando hai finito   
- WorkStealingPoool: Funziona simile al Cachet, ma la quantità di thread dipende dal sistema   
  
  
Si può passare una ThreadFactory per controllare la creazione di nuovi thread  
  
**Future**  
Tramite l’interfaccia Callable<V> si può eseguire un istanza che restituisce un valore e può generare un eccezione  
  
Un callable viene eseguito con “submit” , deve essere eseguito nella dichiarazione di una variabile di tipo Future<T>  
(Che è un oggetto)  
  
**Esecuzione multipla**  
Se dobbiamo aspettare i risultati di più task: utilizza il metodo ***++invokeAll++***  
Se ci interessa solo il primo che risponde e gli altri risultati li scartiamo: utilizza il metodo ***++invokeAny++***  
  
++Dopo aver fatto il submit dobbiamo fare “nomeVariabile” di tipo Future poi “.get”++*** ***. Questo blocca l’esecuzione  
  
Possiamo usare anche dei ***++CompletableFuture++****++ ++*che ci permette di registrare un ++callback alert ++che viene invocata **con il risultato** una volta disponibile  
![future. thenAccept](Attachments/DF9B1865-6A16-4247-9C9D-CA7C7E830F31.png)  
  
Un CompletableFuture può essere completato manualmente   
  
I metodi complete e completeExceptionally possono essere usati per completare un futuro:  
![boolean complete(T value)](Attachments/6BA2E341-6984-467C-83F3-614568F82CCE.png)  
Un future può essere completato da più thread (solo il primo viene memorizzato).  
  
**VISIBILITA’**  
Il compilatore esegue le ottimizzando ipotizzando che non ci siano accessi simultanei alla memoria  
Se ci sono, la macchina virtuale deve saperlo per evitare possibili errori  
  
Ogni thread infatti usa una copia della memoria principale e gli aggiornamenti vengono effettuati solo in alcune condizioni:  
- Il valore di una variabile final è visibile **dopo** l’inizializzazione  
- Il valore di una variabile static è visibile dopo l’inizializzazione  
- Le modifiche alle variabili volatili sono prima o poi visibili   
- Le modifiche prima del rilascio di un Lock sono visibili a chiunque **acquisisca **un Lock  
  
  
**RACE CONDITION**  
Si ottiene equando più thread tentano di modificare, in modo concorrente, una variabile  
  
Modi per risolverla:  
- ++Confinamento:++ Ridurre quantità di dati condivisi  
- ++Immutabilità++: Usare oggetti immutabili  
- ++Sezioni critiche / Lock++: garantire l’accesso esclusivo alle risorse condivise tramite sezioni critiche o Lock  
  
  
**BLOCCHI SINCRONIZZATI**  
Per garantire che un thread possa eseguire una porzione di codice , possiamo usare blocchi *synchronized*  
![synchronize(value) {](Attachments/A0BC550D-1EBD-4B47-98F7-9FE434F2548F.png)  
  
In un blocco *synchronized*, l’etichetta svolge il ruolo di **Lock**  
  
Il lock:  
- Viene ++acquisito++: quando un thread entra nel blocco  
- Viene ++rilasciato++: quando il thread esce dal blocco  
![Esempio:](Attachments/7F3827F5-5351-465C-B73D-0658BED0BBCF.png)  
  
Esiste synchronized anche per i metodi  
  
**MONITOR**  
Struttura dati che consente ai thread di avere sia la mutua esclusione che la possibilità di attendere specifiche condizioni  
  
I monitor forniscono meccanismi per segnalare ad altri thread che le condizioni sono soddisfatte  
  
Sono costruiti da:  
- Un mutex (un lock)  
- Condition variables: contenitore dove I thread sono in attesa delle condizioni  
  
Ogni oggetto il Java è un monitor e mette a disposizione i seguenti metodi:  
![• void wait() throws InterruptedException](Attachments/81786B01-9E9E-421E-8DE7-0BFD07CB5A2F.png)  
  
**LIBRERIE DI ALTO LIVELLO**  
Java collection framework mette a disposizione strutture di alto livello che semplificano la programmazione concorrente  
  
- Lock: oggetti che permettono di coordinare le attività dei thread  
  
- Concurrent collections: strutture dati pensate per gestire grosse moli di dati riducendo la necessità di sincronizzazione  
  
- Atomic variables: variabili che consentono di minimizzare la sincronizzazione garantendo la consistenza in memoria   
  
**DIFFERENZA TRA LOCK (oggetto) E BLOCCO SYNCHRONIZED**  
Con un synchronized rilascio il lock quando esco dalla sezione critica  
  
Con un lock (oggetto) fino a che non sblocco il lock non rilascio. Viene quindi usato try-finally  
  
  
**LE CONDIZIONI**  
Sono l’equivalente della *await* ma solo su condizioni specifiche  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
