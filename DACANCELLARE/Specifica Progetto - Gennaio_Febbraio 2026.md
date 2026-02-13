# Metodologie di Programmazione Modellazione e Gestione della Conoscenza Programmazione Avanzata Specifica Progetto

# Gennaio/Febbraio 2026

Per organizzare al meglio il nostro tempo, abbiamo bisogno di sviluppare un’applicazione che consenta di tenere traccia dei propri impegni, pianificare le attività e generare report. Le attività (o *task*) possono essere raggruppate in *progetti*. Per supportare l’organizzazione del lavoro, ad ogni attività può essere associata una stima del tempo necessario al suo svolgimento. Terminata l’attività, sarà possibile inserire la durata effettiva, consentendo di valutare a posteriori la differenza rispetto a quanto stimato.

Sviluppare il prototipo di un'applicazione che permetta di:

* **Gestione dei progetti:** inserire e consultare la lista dei progetti attivi o completati, nonché aprire un nuovo progetto o chiuderlo, nel caso non ci siano attività pendenti.   
* **Gestione attività:** aggiungere, cancellare o completare le attività associate ad un progetto.  
* **Gestione della pianificazione:** definire le attività da svolgere in un giorno specifico (tenendo eventualmente conto dell’impegno totale richiesto per il loro svolgimento).  
* **Gestione della reportistica:** redigere report riassuntivi, organizzati per progetto o per intervallo temporale, con le attività svolte e/o programmate.

***È importante chiarire che le specifiche caratteristiche dell’applicazione da sviluppare costituiscono una vostra scelta e parte della valutazione.*** 

**L’applicazione dovrà essere progettata in modo da garantire possibili estensioni future** (ad esempio la gestione di tag e/o risorse da associare alle attività da svolgere) e **facilitare l’integrazione di nuove funzionalità**. Non è importante quindi che tutte le funzionalità siano disponibili nella prima release, ma che sia chiaro come queste possano essere integrate in un futuro. 

L’applicazione sviluppata dovrà fornire un’interfaccia grafica per accedere alle funzionalità implementate e meccanismi per la gestione della persistenza dei dati.

Tutte le classi sviluppate devono far parte del package:

**it.unicam.cs.mpgc.jtime\<matricola\>** 

La consegna del progetto dovrà essere fatta caricando un archivio in formato .zip, .tgz o .tar.gz con una singola cartella contenente:

- Un progetto Gradle per il building e l’esecuzione dell’applicazione;  
- Una relazione contenente una descrizione:  
  - delle funzionalità implementate;  
  - delle responsabilità individuate;  
  - delle classi ed interfacce sviluppate con le *responsabilità* associate ad ognuna di queste (**N.B. la descrizione del pattern MVC non è la definizione delle responsabilità\!**);  
  - dell’organizzazione dei dati e di come è stata garantita la persistenza e come possono essere gestite eventuali estensioni;   
  - dei meccanismi messi a disposizione per poter integrare nuove funzionalità.

Nella valutazione del progetto verranno considerate:

* Definizione delle responsabilità delle classi	  
* Metodi coerenti con le responsabilità	  
* Principi SOLID rispettati	  
* Estendibilità del progetto	  
* Pulizia/Stile del codice	  
* Uso degli strumenti e metodologie viste durante il corso	  
* Relazione (Scrittura)

Di seguito viene riportata la scheda di valutazione dei progetti:

* Relazione:  
  * Descrizione delle funzionalità implementate  
  * Descrizione delle responsabilità individuate  
  * Descrizione della gerarchia delle classi individuate con associate le rispettive responsabilità implementate  
  * Descrizione di come nuove funzionalità possono essere integrate  
* Il progetto gradle compila  
* Il progetto gradle esegue  
* Sono rispettati i principi SOLID  
* Non sono presenti code smells  
* Vengono utilizzati gli strumenti e le metodologie viste a lezione  
* Le classi proposte sono facilmente estendibili   
* Organizzazione e funzionamento dell’Interfaccia grafica  
* Gestione della persistenza dei dati


  
**CONSEGNE CHE NON RISPETTANO LA SPECIFICA NON VERRANNO VALUTATE\!**