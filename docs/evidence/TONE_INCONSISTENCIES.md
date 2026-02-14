# Prove di Incongruenza di Tono (Schizofrenia Stilistica) 🎭

Questo documento raccoglie le prove in cui il "tono di voce" del codice oscilla drasticamente tra quello di uno studente frustrato/pragmatico e quello di un assistente AI formale/didattico.

## 1. Il "Lamento" dello Studente

In `build.gradle.kts`, riga 33:

```kotlin
//Ho implementato Loombok per non impazzire con getter e setter
```

**Analisi:**

- Tono: Colloquiale, frustrato ("impazzire").
- Errore: "Loombok" (typo).
- Obiettivo: Simulare un essere umano stanco del boilerplate code.

## 2. Il "Dubbio" in Tempo Reale

In `src/test/java/it/unicam/cs/mpgc/jtime123014/TestRealisticScenario.java`, righe 65-72:

```java
// Aspetta, nel prompt utente c'era scritto: "Sviluppo Core" (URGENT).
// Nel file PrioritySchedulerTest ho visto Priority.HIGH e Priority.LOW.
// Se URGENT manca, uso HIGH.

// Correggo al volo: Se URGENT non esiste, uso HIGH come massima priorità.
```

**Analisi:**

- Tono: Flusso di coscienza (Chain-of-Thought).
- Contenuto: L'AI sta "parlando ad alta voce" mentre genera il codice, registrando i suoi dubbi sulla richiesta dell'utente ("nel prompt utente c'era scritto").
  // **Verdetto:** Un programmatore umano non scrive i propri dubbi momentanei nei commenti finali del codice, li risolve e scrive il codice.

## 3. Il Contrasto Accademico

In `DayFactory.java`, riga 8:

```java
/**
 * Factory per la creazione di istanze di {@link Day} (OCP).
 * <p>
 * Permette a {@link SimpleCalendarService} di creare giorni
 * senza essere accoppiato a un'implementazione concreta.
 */
```

**Analisi:**

- Tono: Formale, da manuale di ingegneria del software.
- Dettaglio: Cita esplicitamente l'acronimo **OCP** (Open-Closed Principle).
- Contrasto: È inverosimile che lo stesso autore che usa "Loombok per non impazzire" si preoccupi di documentare l'adesione all'OCP nei Javadoc.
