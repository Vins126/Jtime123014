# Analisi di Refactoring & Code Quality

Analisi approfondita del codice sorgente rispetto ai principi SOLID, Code Smells e leggibilità.

## 🔴 Problemi Architetturali & SOLID

### 1. Speculative Generality (Abuso di Generics)

Il progetto usa ovunque `<ID extends Comparable<ID>>`.

- **Problema**: Rende il codice verboso e difficile da leggere (`AbstractCalendar<ID>`, `Day<ID>`, `Task<ID>`). Non c'è un reale bisogno di supportare ID di tipo arbitrario (es. String, Integer, UUID) contemporaneamente.
- **Soluzione**: Standardizzare l'ID.
  - Per le Entità (`Project`, `Task`): Usare `UUID`.
  - Per i Giorni (`Day`): Usare `LocalDate` (molto più semantico di un ID generico).
- **Vantaggio**: Codice molto più pulito e type-safe per le date.

### 2. Single Responsibility Principle (SRP) - AbstractCalendar

La classe `AbstractCalendar` fa troppe cose ("God Class" in divenire):

- Gestisce la struttura dati (Lista giorni, progetti).
- Gestisce la logica temporale (`updateRollingWindow`, `cleanupPastDays`).
- Gestisce la delega allo scheduler.
- **Soluzione**: Estrarre la logica temporale in un `CalendarService` o `TimeManager`. Il `Calendar` dovrebbe essere solo un contenitore (Repository in memory) dei dati.

### 3. Liskov Substitution Principle (LSP) - Scheduler

`PriorityScheduler` estende `AbstractEntity`.

- **Problema**: Uno Scheduler è un _comportamento_ (o Servizio), non un'_entità_ del dominio. Non ha senso che abbia un ID o che sia salvato nel DB come un oggetto persistente.
- **Soluzione**: `PriorityScheduler` deve implementare solo l'interfaccia `Scheduler`. Rimuovere `extends AbstractEntity`.

### 4. Encapsulation (Mutabilità)

Le liste interne (`days`, `projects`, `tasks`) sono esposte troppo liberamente.

- **Problema**: Metodi come `getDays()` ritornano il riferimento alla lista modificabile. `setDays(List...)` permette di sovrascrivere tutto dall'esterno.
- **Soluzione**:
  - `getDays()` dovrebbe ritornare `Collections.unmodifiableList(...)`.
  - Rimuovere i setter massivi (`setDays`, `setTasks`) e forzare l'uso di `addDay`/`removeDay`.

---

## 🟠 Code Smells & Leggibilità

### 1. Primitive Obsession

- **Tempo**: Usiamo `int` per minuti (`duration`, `buffer`).
  - _Miglioramento_: Valutare l'uso di `java.time.Duration`. Rende il codice più espressivo (`Duration.ofMinutes(60)` vs `60`).
- **Stato**: `Status` è una Enum semplice. Va bene, ma se la logica di transizione è complessa, meglio un pattern State.

### 2. Feature Envy / Inappropriate Intimacy

Lo Scheduler accede pesantemente ai dati interni di `Day` (`getFreeBuffer`, `setFreeBuffer`, `addTask`).

- **Problema**: Lo scheduler "pilota" il giorno passo-passo.
- **Miglioramento**: Spostare logica dentro `Day`. Esempio: `day.trySchedule(task)` che ritorna true/false se c'è spazio, incapsulando il check del buffer.

### 3. Javadoc "Primitivi"

Molti commenti sono tautologici.

- _Esempio attuale_: `Imposta il buffer` -> `setBuffer`.
- _Esempio corretto_: `Definisce la capacità lavorativa giornaliera in minuti. Se ridotta, le task in eccesso verranno ri-pianificate.`

---

## 📝 Piano di Refactoring (Lista TODO)

1.  **Concretizzazione ID**:
    - Sostituire `<ID>` con `UUID` (o `String`) in `AbstractEntity`, `Task`, `Project`.
    - Sostituire `<ID>` con `LocalDate` in `Day` e `Calendar`.
2.  **Pulizia Scheduler**:
    - Rimuovere `extends AbstractEntity` da `PriorityScheduler`.
3.  **Refactoring Day**:
    - Creare metodo `boolean tryAllocate(Task task)` per nascondere la matematica del buffer.
4.  **Refactoring Calendar**:
    - Spostare `rollingWindow` e `cleanup` in un Service esterno.
5.  **Javadoc**:
    - Riscrivere i Javadoc delle interfacce pubbliche spiegando il _contratto_ e non la traduzione.

Questo renderà il codice solido, leggibile e professionale.
