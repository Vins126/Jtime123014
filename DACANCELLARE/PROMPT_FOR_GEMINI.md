# Context

I am developing "JTime", a Java desktop application for project management and automatic scheduling.
The project follows a strict **MVC** architecture and uses **SOLID** principles.

I need your help to brainstorm the **Reporting Module**.
I want to decide **what** kind of reports are useful and **how** to architect them.

## Data Structure (The "Model")

The system is built around these core entities. **This is the ONLY data available**:

1.  **`Calendar` (Aggregate Root)**
    - Contains a list of **`Project`** objects.
    - Contains a list of **`Day`** objects (the timeline).
    - Acts as the database in-memory.

2.  **`Project`**
    - Has: `Name`, `Description`, `Priority` (URGENT, HIGH, MEDIUM, LOW), `Status` (PENDING, IN_PROGRESS, COMPLETED).
    - Contains a list of **`Task`** objects.
    - Key methods: `getPendingTasks()`, `getTasks()`.

3.  **`Task`**
    - Has: `Name`, `Description`, `Status` (PENDING, IN_PROGRESS, COMPLETED).
    - **Time Data**:
      - `durationEstimate`: Initial estimate (minutes).
      - `durationActual`: Real time spent (set only when completed).
      - `calculateDelta()`: Difference between Actual and Estimate.
    - Lifecycle: Created in a Project -> Scheduled in a Day -> Completed.

4.  **`Day`**
    - Represents a specific date (`LocalDate`).
    - Has a **Buffer** (capacity in minutes, e.g., 480 min = 8 hours).
    - Has a **Free Buffer** (remaining capacity).
    - Contains a list of **`Task`** assigned to this day (Schedule).

5.  **`Scheduler` (Service)**
    - Algorithm (Greedy + Prio) that assigns Tasks to Days based on Project Priority and Day Buffer.

## Goals

I need you to:

1.  **Analyze the Data Potential**: Based _only_ on the data above, what specific metrics, KPIs, or insights can we generate? (e.g., Efficiency, Delays, Forecasting).
2.  **Propose Report Types**: Suggest 3-4 concrete report types (e.g., "Project Health", "Weekly Productivity") and exactly what data they should show.
3.  **Propose Architecture**:
    - How to separate **Data Calculation** from **Visualization** (Text, HTML, PDF)?
    - How to handle **Snapshot** (historical record) vs **Live** (dashboard) views?

**Constraint**: Do not write code yet. Focus on the _functional requirements_ and _architectural design_.
