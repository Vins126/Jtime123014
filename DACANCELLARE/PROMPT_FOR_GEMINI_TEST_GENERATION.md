# Context: JTime Project Structure & Functionality

**Project Name:** JTime (Java Time Management System)
**Architecture:** MVC (Model-View-Controller) with Service Layer.
**Language:** Java 21+

## 1. System Overview

JTime is a desktop application for managing projects, tasks, and daily schedules. It uses a **Priority-Greedy Scheduling Algorithm** to automatically assign tasks to days based on available capacity and project priority.

### Key Components

#### A. Controller (Facade)

- **`JTimeController`**: The main entry point.
  - **Responsibilities**: Initialization (Composition Root), delegation to services, orchestrating workflows.
  - **Key Methods**:
    - `addProject(Project)`: Adds a project to the system.
    - `schedule()`: Triggers the scheduling algorithm starting from `LocalDate.now()`.
    - `createProjectReport(projectId)`: Generates XML report for a project.
    - `createIntervalReport(start, end)`: Generates XML report for a time range.

#### B. Model (Domain Entities)

1.  **`Project<ID>`** (`SimpleProject`):
    - Attributes: Name, Description, `Priority` (LOW, MEDIUM, HIGH, URGENT).
    - Contains a list of `Task`.
2.  **`Task<ID>`** (`SimpleTask`):
    - Attributes: Name, Description, `durationEstimate` (mins), `durationActual` (mins), `Status` (PENDING, IN_PROGRESS, COMPLETED).
    - **Shared Reference**: The _same_ Task object instance exists in the `Project`'s list and in a `Day`'s list when scheduled.
3.  **`Day<ID>`** (`SimpleDay`):
    - Attributes: `LocalDate` (ID), `buffer` (total capacity in mins), `freeBuffer` (remaining capacity).
    - Contains a list of scheduled `Task`.
4.  **`Calendar<ID>`** (`SimpleCalendar`):
    - Aggregates `Project`s and `Day`s.

#### C. Services

1.  **`PriorityScheduler`**:
    - **Logic**: Iterates through projects (ordered). For each project, it tries to fill available days starting from today.
    - **Constraints**:
      - **Day Capacity**: Cannot exceed `day.getFreeBuffer()`.
      - **Priority Limit**: A project of `Priority X` can only use up to `X.maxPercentage` of a day's total buffer (Time Boxing).
      - _Example_: `URGENT` allows 100% usage, `LOW` might allow only 25%.
2.  **`ReportController` / `ReportService`**:
    - Generates `ProjectReportDTO` (Progress, Delta, Forecast, Task Status Lists).
    - Generates `IntervalReportDTO` (Velocity, Workload, Saturation).
    - Exports to XML in the `reports/` directory.

---

## 2. Workflows to Test

### Scenario A: Creation & Scheduling

1.  User instantiates `JTimeController`.
2.  User creates `Project`s with different priorities (e.g., "Website" URGENT, "Docs" LOW).
3.  User adds `Task`s to projects with estimated durations (e.g., 240 mins).
4.  User calls `controller.schedule()`.
    - _Expectation_: Tasks are distributed across `Day`s (`LocalDate.now()`, `now()+1`, ...).
    - _Expectation_: High priority tasks take precedence or fill more space.
    - _Expectation_: If a day is full, tasks move to the next day.

### Scenario B: Work Execution & Tracking

1.  User simulates work by finding a scheduled `Task` in a `Day`.
2.  User updates the task: `task.setStatus(COMPLETED)`, `task.setDurationActual(300)`.
3.  _Verification_: The change must be reflected in the `Project` (because it's the same object).

### Scenario C: Reporting

1.  User calls `controller.createProjectReport(...)`.
2.  User calls `controller.createIntervalReport(...)`.
3.  _Verification_: XML files are created in `reports/` and contain correct calculated stats (Delta = Actual - Est).

---

## 3. Request for Agent

**Goal:** Generate a comprehensive JUnit 5 Test Suite.

**Requirements:**

1.  **Unit Tests**: Create small tests for `SimpleDay` (buffer logic), `SimpleProject` (task management), and `PriorityScheduler` (algorithm logic).
2.  **Integration/System Test**: Create a **`TestRealisticScenario.java`**.
    - **Setup**: Create 3 Projects (Urgent, High, Low) with ~10 Tasks each of varying lengths (30m to 400m).
    - **Action**: Call `schedule()`.
    - **Simulate Time**: "Mock" the passage of time or simulate completion of tasks on Day 1.
    - **Reporting**: Generate reports.
    - **Assertions**:
      - Verify tasks are on expected days (or roughly distributed).
      - Verify `freeBuffer` of days decreases.
      - Verify reports are generated.
      - Verify `JTimeController` acts as a proper facade.

**Technical Reference**:

- Use `it.unicam.cs.mpgc.jtime123014.controller.JTimeController`.
- Use `it.unicam.cs.mpgc.jtime123014.model.*`.
- Assume standard `LocalDate.now()` usage.
