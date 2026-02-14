package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;

import java.util.List;
import java.util.Iterator;

import java.time.LocalDate;

/**
 * Scheduler che assegna le attività in base alla priorità.
 * 
 * In pratica guarda i progetti uno per uno e checka la priorità (ALTA, MEDIA,
 * BASSA).
 * In base a quella decide quanto spazio occupare nel giorno.
 * Se è ALTA ovviamente si prende più spazio.
 * Poi piazza le task dove trova posto.
 * 
 * Questo serve per non far monopolizzare il calendario da un solo progetto.
 */
public class PriorityScheduler extends AbstractScheduler {

    /**
     * Crea un nuovo scheduler a priorità.
     */
    public PriorityScheduler() {
    }

    /**
     * Modifica lo stato del calendario assegnando le task ai giorni e aggiornando
     * lo stato delle task a {@link Status#IN_PROGRESS}.
     * 
     * @param calendar  il calendario su cui operare. Non deve essere null.
     * @param startDate la data di inizio pianificazione. Non deve essere null.
     */
    @Override
    public void schedule(Calendar<?> calendar, LocalDate startDate) {
        List<Day<?>> daysFromToday = getDaysFromToday(calendar, startDate);

        for (Project<?> project : calendar.getProjects()) {
            Priority priority = project.getPriority();

            for (Day<?> day : daysFromToday) {
                int maxPercentage = calculateMaxAllocatableTime(day, priority);

                List<Task<?>> pendingTasks = project.getPendingTasks();
                Iterator<Task<?>> iterator = pendingTasks.iterator();

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
        int timeRequired = task.getTimeConsuming();

        if (timeRequired <= day.getFreeBuffer() && timeRequired <= maxAllocatableTime) {
            day.addTask(task);
            task.setStatus(Status.IN_PROGRESS);
            day.updateBuffer(timeRequired);
            return true;
        }
        return false;
    }
}
