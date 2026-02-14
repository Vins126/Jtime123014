package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.util.List;
import java.util.Iterator;

import java.time.LocalDate;

/**
 * Scheduler che assegna le attività in base alla priorità.
 * <p>
 * <b>Come funziona:</b>
 * <ol>
 * <li>Considera i progetti uno alla volta.</li>
 * <li>Per ogni progetto, guarda la sua priorità (es. ALTA, MEDIA, BASSA).</li>
 * <li>La priorità determina quanto spazio massimo nel giorno può occupare quel
 * progetto.</li>
 * <li>Inserisce le task nei giorni liberi rispettando questo limite.</li>
 * </ol>
 * <p>
 * Questo approccio assicura che i progetti più importanti abbiano spazio, ma
 * evita che un singolo progetto monopolizzi completamente il calendario.
 */
public class PriorityScheduler extends AbstractScheduler {

    /**
     * Crea un nuovo scheduler a priorità.
     */
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
    @Override
    public void schedule(Calendar<?> calendar, LocalDate startDate) {
        // Utilizza il metodo ereditato da AbstractScheduler per filtrare i giorni
        List<Day<?>> daysFromToday = getDaysFromToday(calendar, startDate);

        for (Project<?> project : calendar.getProjects()) {
            Priority priority = project.getPriority();

            for (Day<?> day : daysFromToday) {
                // Calcola il tempo massimo allocabile in questo giorno per task di questa
                // priorità
                int maxPercentage = calculateMaxAllocatableTime(day, priority);

                List<Task<?>> pendingTasks = project.getPendingTasks();
                Iterator<Task<?>> iterator = pendingTasks.iterator();

                // Finché ci sono task, c'è spazio nel buffer libero E non ho superato la quota
                // per priorità
                while (iterator.hasNext() && day.getFreeBuffer() > 0 && maxPercentage > 0) {
                    Task<?> task = iterator.next();

                    if (tryScheduleTask(day, task, maxPercentage)) {
                        iterator.remove();
                        maxPercentage -= task.getTimeConsuming();
                    }
                }
            }
        }
    }

    private int calculateMaxAllocatableTime(Day<?> day, Priority priority) {
        return (day.getBuffer() * priority.getMaxPercentage()) / 100;
    }

    /**
     * Tenta di pianificare una task in un determinato giorno.
     * Verifica se c'è abbastanza spazio nel buffer del giorno e se non si supera il
     * limite imposto dalla priorità.
     *
     * @param day                il giorno in cui inserire la task.
     * @param task               la task da inserire.
     * @param maxAllocatableTime il tempo massimo dedicabile a questa priorità.
     * @return true se la task è stata pianificata, false altrimenti.
     */
    private boolean tryScheduleTask(Day<?> day, Task<?> task, int maxAllocatableTime) {
        // Tenta di schedulare la task nel giorno (la logica di controllo buffer è nel
        // Day)
        int timeRequired = task.getTimeConsuming();

        // Logica di scheduling (SRP: appartiene al Service, non al Model)
        if (timeRequired <= day.getFreeBuffer() && timeRequired <= maxAllocatableTime) {
            day.addTask(task);
            task.setStatus(Status.IN_PROGRESS);
            day.updateBuffer(timeRequired);
            return true;
        }
        return false;
    }
}
