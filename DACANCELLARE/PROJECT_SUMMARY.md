# JTime Project - Analisi e Responsabilità dei File

Questo documento fornisce una panoramica dettagliata di tutti i file sorgente del progetto, descrivendo le loro responsabilità, le deleghe e le dipendenze.

## Package: `it.unicam.cs.mpgc.jtime123014.model`

Contiene le entità e le strutture dati fondamentali del dominio.

### Interfacce e Classi Astratte (Scheletro)

- **`Identifiable<ID>`** (Interfaccia): Definisce il contratto per oggetti identificabili univocamente tramite un ID.
  - _Responsabilità:_ Fornire un metodo `getId()`.
  - _Dipendenze:_ Nessuna.
  - _Stato:_ Completa.

- **`AbstractEntity<ID>`** (Classe Astratta): Implementazione base di `Identifiable`.
  - _Responsabilità:_ Gestisce il campo `id` e l'implementazione di `equals` e `hashCode` basata sull'ID.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Completa. Base per tutte le entità.

- **`Task<ID>`** (Interfaccia): Rappresenta un'attività da svolgere.
  - _Responsabilità:_ Definire i metodi per nome, descrizione, stato (`Status`) e stima durata.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Completa.

- **`AbstractTask`** (Classe Astratta): Implementazione base di `Task`.
  - _Responsabilità:_ Implementa getters/setters standard e la gestione dello stato.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Usata come base per `SimpleTask`.

- **`Project<ID>`** (Interfaccia): Rappresenta un progetto composto da più task.
  - _Responsabilità:_ Gestire una collezione di `Task`, la priorità e lo stato del progetto. Espone `getPendingTasks()` per chi deve pianificare.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Completa.

- **`AbstractProject`** (Classe Astratta): Implementazione base di `Project`.
  - _Responsabilità:_ Gestisce la lista interna dei task, verifica il completamento del progetto (`completeProject` lancia eccezione se task pendenti). Include logica `getPendingTasks`.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Usata come base per `SimpleProject`.

- **`Day<ID>`** (Interfaccia): Rappresenta un giorno lavorativo con un buffer di tempo.
  - _Responsabilità:_ Gestire il tempo disponibile (`buffer`), il tempo libero (`freeBuffer`) e la lista dei task assegnati. Include logica di controllo (`hasPendingTasks`, `scheduleTask`).
  - _Deleghe:_ Nessuna.
  - _Stato:_ Completa (include logica "rich" per validazione buffer).

- **`AbstractDay`** (Classe Astratta): Implementazione base di `Day`.
  - _Responsabilità:_ Implementa la logica critica di `scheduleTask` (verifica capienza buffer) e `updateBuffer`.
  - _Deleghe:_ Nessuna.
  - _Stato:_ Usata come base per `SimpleDay`.

- **`Calendar<ID>`** (Interfaccia): Container principale di Giorni e Progetti.
  - _Responsabilità:_ Mantenere liste di `Day` e `Project`. Metodi per aggiungere/rimuovere e verificare esistenza (`hasDay`).
  - _Deleghe:_ Nessuna.
  - _Stato:_ Completa (senza logica di business complessa, solo gestione dati).

- **`AbstractCalendar`** (Classe Astratta): Implementazione base di `Calendar`.
  - _Responsabilità:_ Implementa la gestione delle liste interne.
  - _Deleghe:_ Nessuna (dipendenza da Scheduler rimossa).
  - _Stato:_ Usata come base per `SimpleCalendar`.

### Implementazioni Concrete (Standard)

- **`SimpleTask`**: Implementazione concreta standard di `AbstractTask`.
  - _Uso:_ Istanziare task normali.
- **`SimpleProject`**: Implementazione concreta standard di `AbstractProject`.
  - _Uso:_ Istanziare progetti normali.
- **`SimpleDay`**: Implementazione concreta standard di `AbstractDay` (usa `LocalDate` come ID).
  - _Uso:_ Istanziare giorni nel calendario.
- **`SimpleCalendar`**: Implementazione concreta standard di `AbstractCalendar`.
  - _Uso:_ L'oggetto calendario principale manipolato dal Controller.

### Enumerazioni

- **`Priority`**: (URGENT, HIGH, MEDIUM, LOW) con associata `%` di buffer utilizzabile.
- **`Status`**: (PENDING, IN_PROGRESS, COMPLETED).
- **`Months`**: Enumerazione dei mesi.

---

## Package: `it.unicam.cs.mpgc.jtime123014.service`

Contiene la logica di business che opera sul modello.

- **`Scheduler`** (Interfaccia): Contratto per algoritmi di pianificazione.
  - _Responsabilità:_ Definire `schedule(Calendar, LocalDate)`.
  - _Deleghe:_ Nessuna.

- **`PriorityScheduler`** (Classe Concreta): Implementa l'algoritmo Greedy con vincoli di priorità.
  - _Responsabilità:_ Itera progetti e giorni per allocare task.
  - _Deleghe:_
    - Al `Project`: chiede `getPendingTasks()`.
    - Al `Day`: chiede `scheduleTask()` per verificare se c'è spazio.
    - Rispetta rigorosamente il SRP e Tell-Don't-Ask.

- **`CalendarService`** (Interfaccia): Contratto per servizi di manutenzione del calendario.
  - _Responsabilità:_ Gestire finestra temporale (rolling window) e pulizia.

- **`SimpleCalendarService`** (Classe Astratta): Logica comune dei servizi calendario.
  - _Responsabilità:_ Implementa `updateRollingWindow` (assicura 31 giorni futuri) e `cleanupPastDays` (rimuove giorni passati se vuoti).
  - _Deleghe:_
    - Al `Calendar`: usa `hasDay` e `addDay`.
    - Al `Day`: usa `hasPendingTasks`.
  - _Incompleta:_ Metodo `plusDays` astratto (dipende dal tipo di data).

- **`LocalDateCalendarService`** (Classe Concreta): Implementazione per `LocalDate`.
  - _Responsabilità:_ Fornisce l'aritmetica delle date per `LocalDate`.
  - _Uso:_ Servizio standard usato dal Controller.

---

## Package: `it.unicam.cs.mpgc.jtime123014.controller`

Coordina l'interazione tra UI, Modello e Servizi.

- **`JTimeController`** (Classe Concreta): Application Controller / Facade.
  - _Responsabilità:_
    1.  **Composition Root**: Istanzia le implementazioni concrete (`SimpleCalendar`, `PriorityScheduler`, `LocalDateCalendarService`).
    2.  **Orchestrazione**: Espone metodi semplici (`schedule`, `addProject`) e delega l'esecuzione ai servizi appropriati passando loro il modello.
  - _Deleghe:_
    - A `calendarService` per manutenzione giorni.
    - A `scheduler` per pianificazione task.
    - A `calendar` per accesso dati CRUD.
  - _Stato:_ Punto di ingresso principale per l'applicazione (UI o Main).

---

## Analisi Architetturale

Il progetto segue ora un'architettura **MVC (Model-View-Controller)** pulita con **Layered Architecture**:

1.  **Model**: `it.unicam.cs.mpgc.jtime123014.model`
    - Puro, contiene dati e logica di validazione interna (es. `Day.scheduleTask`).
    - Disaccoppiato dai servizi.

2.  **Service Layer**: `it.unicam.cs.mpgc.jtime123014.service`
    - Contiene la logica di business "esterna" (Scheduling, Rolling Window).
    - Opera sul Modello ma non ne fa parte.

3.  **Controller**: `it.unicam.cs.mpgc.jtime123014.controller`
    - Collega tutto. L'utente interagisce solo con questo.

### Dipendenze e Completezza

- Tutte le classi astratte hanno ora una controparte concreta (`Simple...`).
- Non ci sono dipendenze circolari (Model -> Service eliminata).
- L'incapsulamento è stato rafforzato (`Project` e `Day` nascondono i dettagli implementativi delle loro liste).
