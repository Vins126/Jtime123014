# Gap Analysis: Progetto vs Specifica

Analisi basata sul file `Specifica Progetto - Gennaio_Febbraio 2026.md`.

## ✅ Cosa è stato fatto (Stato Attuale)

### 1. Modellazione del Dominio (Core)

- [x] **Task**:
  - Interfaccia e implementazione astratta presenti.
  - Stima tempo e calcolo Delta (Effettivo vs Stimato) implementati (`calculateDelta`).
- [x] **Progetto**:
  - Concetto di Progetto come aggregatore di Task.
  - Stati `IN_PROGRESS` e `COMPLETED` gestiti. Logica di chiusura (solo se task finiti) presente.
- [x] **Pianificazione (Scheduler)**:
  - Algoritmo Greedy + Priorità implementato via Strategy Pattern (`PriorityGreedyScheduler`).
  - Gestione `Day` e `RollingWindow` (31 giorni) implementata.
- [x] **Architettura**:
  - Package corretto (`it.unicam.cs.mpgc.jtime123014`).
  - Uso di Interfacce per estendibilità (rispetta specifica "garantire possibili estensioni future").
  - Principi SOLID rispettati (es. separazione Calendar/Scheduler).

---

## 🚨 Cosa Manca (GAP Critici)

### 1. Gestione Reportistica (MISSING)

La specifica richiede: _"redigere report riassuntivi, organizzati per progetto o per intervallo temporale"_.

- **Stato**: Non esiste nessuna classe o metodo per generare report.
- **Azione Richiesta**: Creare un `ReportService` o simile che:
  - Prenda in input una lista di Task.
  - Filtri per data (es. "settimana scorsa") o Progetto.
  - Generi una stringa/file di output (es. "Progetto A: 5 task completati, Delta totale: -20 min").

### 2. Persistenza dei Dati (MISSING)

La specifica richiede: _"meccanismi per la gestione della persistenza dei dati"_.

- **Stato**: Al riavvio dell'applicazione si perde tutto.
- **Azione Richiesta**: Implementare `SaveManager` / `LoadManager`.
  - Formato consigliato: **JSON** (facile da leggere/estendere).
  - Libreria: Jackson o Gson (da aggiungere a Gradle).

### 3. Interfaccia Grafica (GUI) (MISSING)

La specifica è esplicita: _"L’applicazione sviluppata dovrà fornire un’interfaccia grafica"_.

- **Stato**: Abbiamo solo Test JUnit. Nessuna UI.
- **Azione Richiesta**:
  - Scegliere tecnologia: **JavaFX** (standard moderno) o Swing (più vecchio ma semplice).
  - Implementare viste per: Lista Progetti, Calendario Giornaliero, Form Aggiunta Task.

### 4. Build & Dependencies

- **Stato**: Il `build.gradle` è minimale.
- **Azione Richiesta**:
  - Aggiungere dipendenze per JSON (es. Jackson).
  - Aggiungere dipendenze e plugin per JavaFX (se scelti).

---

## ⚠️ Punti di Attenzione (Code Smells potenziali per la valutazione)

- **Generics ovunque**: L'uso spinto di `<ID>` rende il codice molto flessibile ma forse _over-engineered_ se poi usiamo sempre `Integer` o `Date`.
  - _Consiglio_: Valutare se mantenere questa complessità o "chiudere" le implementazioni concrete su un tipo (es. `UUID`).
- **Gestione Risorse/Tag**: La specifica cita _"possibili estensioni future (ad esempio la gestione di tag e/o risorse)"_.
  - _Stato_: Non abbiamo Tag o Risorse.
  - _Consiglio_: Assicurarsi che `Task` sia estendibile (lo è, essendo interfaccia/astratta), magari aggiungendo un metodo `getTags()` vuoto o commentando dove andrebbe.

## Piano d'Azione Consigliato

1.  **Persistenza (Priority: HIGH)**: Senza salvare, non è un'app.
2.  **Reportistica (Priority: MEDIUM)**: Facile da fare, chiude un requisito funzionale.
3.  **GUI (Priority: HIGH)**: Richiede tempo. Valutare se fare una CLI prima per testare il flusso completo "user-side".
