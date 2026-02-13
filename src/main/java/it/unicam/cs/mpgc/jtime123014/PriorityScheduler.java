package it.unicam.cs.mpgc.jtime123014;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

import java.time.LocalDate;

/**
 * Implementazione concreta dello {@link Scheduler} che utilizza una strategia
 * Greedy basata sulle Priorità.
 * <p>
 * <b>Algoritmo:</b>
 * <ol>
 * <li>Filtra i giorni a partire dalla data odierna.</li>
 * <li>Itera su ogni {@link Project} presente nel calendario.</li>
 * <li>Per ogni progetto:
 * <ul>
 * <li>Recupera la {@link Priority} del progetto.</li>
 * <li>Calcola la percentuale massima di buffer giornaliero utilizzabile in base
 * alla priorità (es. HIGH -> 75%).</li>
 * <li>Recupera le task pendenti del progetto.</li>
 * <li>Tenta di allocare queste task nei giorni disponibili, riempiendo fino al
 * limite consentito dalla priorità.</li>
 * </ul>
 * </li>
 * </ol>
 * <p>
 * Questa strategia favorisce il completamento dei progetti nell'ordine in cui
 * sono elencati,
 * ma limita l'occupazione giornaliera per evitare che un solo progetto consumi
 * tutte le risorse (Time Boxing per priorità).
 */
public class PriorityScheduler implements Scheduler {

    public PriorityScheduler() {
    }

    /**
     * Esegue la pianificazione delle attività utilizzando l'algoritmo
     * Priority-Greedy.
     * <p>
     * Modifica lo stato del calendario assegnando le task ai giorni e aggiornando
     * lo stato delle task a {@link Status#IN_PROGRESS}.
     * 
     * @param calendar  il calendario su cui operare. Non deve essere null.
     * @param startDate la data di inizio pianificazione. Non deve essere null.
     */
    public void schedule(Calendar<?> calendar, LocalDate startDate) {
        List<Day<?>> daysFromToday = getDaysFromToday(calendar, startDate);

        for (Project<?> project : calendar.getProjects()) {
            Priority priority = project.getPriority();

            for (Day<?> day : daysFromToday) {
                // Calcola il tempo massimo allocabile in questo giorno per task di questa
                // priorità
                int maxPercentage = (day.getBuffer() * priority.getMaxPercentage()) / 100;

                List<Task<?>> pendingTasks = project.getPendingTasks();
                Iterator<Task<?>> iterator = pendingTasks.iterator();

                // Finché ci sono task, c'è spazio nel buffer libero E non ho superato la quota
                // per priorità
                while (iterator.hasNext() && day.getFreeBuffer() > 0 && maxPercentage > 0) {
                    Task<?> task = iterator.next();

                    // Tenta di schedulare la task nel giorno (la logica di controllo buffer è nel
                    // Day)
                    if (day.scheduleTask(task, maxPercentage)) {
                        iterator.remove(); // Rimuove dalla lista temporanea per efficienza
                        maxPercentage -= task.getTimeConsuming(); // Riduce la quota residua
                    }
                }
            }
        }
    }

    /**
     * Restituisce la lista dei giorni del calendario successivi o uguali alla data
     * specificata.
     * 
     * @param calendar il calendario sorgente.
     * @param today    la data di partenza (inclusa).
     * @return lista dei giorni filtrata.
     */
    private List<Day<?>> getDaysFromToday(Calendar<?> calendar, LocalDate today) {
        return calendar.getDays().stream()
                .filter(d -> ((LocalDate) d.getId()).compareTo(today) >= 0)
                .collect(Collectors.toList());
    }

}
