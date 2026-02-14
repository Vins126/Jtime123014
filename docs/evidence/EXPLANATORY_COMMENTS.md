# Prove di Commenti "Spiegoni" (Chain of Thought Residue) 🧠

Questo documento raccoglie i commenti che rivelano il processo di ragionamento interno dell'AI, sfuggiti alla revisione e finiti nel codice sorgente finale.

## 1. La Prova Regina: Il Dibattito Interno sulla Priorità

In `src/test/java/it/unicam/cs/mpgc/jtime123014/TestRealisticScenario.java`, righe 55-77:

```java
// Priority ha: HIGH, MEDIUM, LOW. Se URGENT non c'è, uso HIGH.
// Controllo Priority.java... Se non c'è uso HIGH e scrivo un commento.
// Assumo per ora che URGENT esista, se no correggo.
// EDIT: Ho visto Priority.java solo parzialmente. Se non c'è, userò HIGH.
// Dal task description utente: "Sviluppo Core" (URGENT). Se URGENT non c'è,
// devo controllare.
// Controllo Priority.java nei prossimi step se fallisce la compilazione, ma
// assumiamo HIGH se URGENT manca.
// Userò HIGH per sicurezza se non sono sicuro, ma provo con HIGH che ho visto
// nel codice.
// Aspetta, nel prompt utente c'era scritto: "Sviluppo Core" (URGENT).
// Nel file PrioritySchedulerTest ho visto Priority.HIGH e Priority.LOW.
// Se URGENT manca, uso HIGH.

// Correggo al volo: Se URGENT non esiste, uso HIGH come massima priorità.
// Ma nel prompt l'utente ha chiesto esplicitamente URGENT.
// Se non c'è nel codice base, forse dovrei aggiungerlo o usare HIGH.
// Controllo Priority.java dopo? No, scrivo HIGH per ora e LOW e MEDIUM.

// Rileggendo Priority.java listato prima (step 16, dimensione 478 bytes), è
// piccolo. Probabilmente ha solo HIGH, MEDIUM, LOW.
// Userò HIGH per "Sviluppo Core" se URGENT non c'è.
```

**Analisi:**

- **META-COMMENTO (STEP 16):** L'AI cita uno "step 16" (probabilmente un passaggio nel prompt precedente o nella sua memoria contestuale). Un essere umano non numera i passaggi mentali nel codice.
- **Flusso di Coscienza:** L'intero blocco è un monologo in cui l'AI dibatte con se stessa su come gestire una discrepanza tra il codice esistente (`Priority.java`) e la richiesta dell'utente ("URGENT").
- **Autocorrezione:** "EDIT:", "Correggo al volo", "Aspetta...".

## 2. La Giustificazione Didattica (SRP)

In `PriorityScheduler.java`, riga 92:

```java
// Logica di scheduling (SRP: appartiene al Service, non al Model)
```

**Analisi:**

- Acronimo SRP: Cita il Single Responsibility Principle.
- Giustificazione: Spiega _perché_ il codice è lì ("non al Model").
- **Verdetto:** Questo è un commento generato per _spiegare_ la scelta architetturale all'utente che ha commissionato il codice, non un appunto per sé stesso.

## 3. Spiegare l'Ovvio

In `PriorityScheduler.java`:

```java
// Finché ci sono task, c'è spazio nel buffer libero E non ho superato la quota
// per priorità
while (iterator.hasNext() && day.getFreeBuffer() > 0 && maxPercentage > 0)
```

**Analisi:**

- Traduzione letterale: Il commento ripete in italiano esattamente ciò che dice il codice (`while ... && ... && ...`).
- Scopo didattico: Tipico output di AI quando si chiede "commenta il codice riga per riga".
