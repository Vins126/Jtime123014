package it.unicam.cs.mpgc.jtime123014;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
public abstract class AbstractDay extends AbstractEntity<LocalDate> implements Day<LocalDate> {

    private int buffer;

    @Setter
    @Getter
    private int freeBuffer;

    @Setter
    @NonNull
    private List<Task<?>> tasks;

    public AbstractDay(LocalDate id) {
        super(id);
        this.setBuffer(0);
        this.setFreeBuffer(0);
        this.setTasks(new ArrayList<Task<?>>());
    }

    public AbstractDay(LocalDate id, int buffer) {
        super(id);
        this.setBuffer(buffer);
        this.setFreeBuffer(buffer);
        this.setTasks(new ArrayList<Task<?>>());
    }

    public AbstractDay(LocalDate id, int buffer, List<Task<?>> tasks) {
        super(id);
        this.setBuffer(buffer);
        this.setFreeBuffer(buffer);
        this.setTasks(tasks);
    }

    public AbstractDay(LocalDate id, List<Task<?>> tasks) {
        super(id);
        this.setBuffer(0);
        this.setFreeBuffer(0);
        this.setTasks(tasks);
    }

    /**
     * Imposta il buffer del giorno
     * 
     * @param buffer il buffer da impostare.
     * @throws IllegalArgumentException se il buffer è negativo
     */
    @Override
    public void setBuffer(int buffer) {
        if (buffer < 0) {
            throw new IllegalArgumentException("Il buffer non può essere negativo");
        }
        this.buffer = buffer;
    }

    /**
     * Aggiorna il buffer del giorno in base al tempo utilizzato dalle task
     * Se il buffer non è mai stato impostato, viene impostato al valore di default
     * 
     * @param usedBuffer il tempo utilizzato dalle task
     * @return true se il buffer è stato aggiornato, false altrimenti
     */
    @Override
    public boolean updateBuffer(int usedBuffer) {
        if (freeBuffer - usedBuffer < 0) {
            return false;
        }
        this.freeBuffer -= usedBuffer;
        return true;
    }

    /**
     * Aggiunge una task al giorno
     * 
     * @param task la task da aggiungere
     * @return true se la task è stata aggiunta, false altrimenti
     * @exception NullPointerException     se la task è null
     * @exception IllegalArgumentException se la task esiste già
     */
    @Override
    public boolean addTask(Task<?> task) {
        if (task == null) {
            throw new NullPointerException("La task non può essere null");
        }
        if (tasks.contains(task)) {
            throw new IllegalArgumentException("La task esiste già");
        }
        return tasks.add(task);
    }

    /**
     * Rimuove una task dal giorno
     * 
     * @param task la task da rimuovere
     * @return true se la task è stata rimossa, false altrimenti
     * @exception NullPointerException     se la task è null
     * @exception IllegalArgumentException se la task non esiste
     */
    @Override
    public boolean removeTask(Task<?> task) {
        if (task == null) {
            throw new NullPointerException("La task non può essere null");
        }
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException("La task non esiste");
        }
        return tasks.remove(task);
    }

    @Override
    public boolean scheduleTask(Task<?> task, int maxPercentage) {
        int timeRequired = task.getTimeConsuming();

        if (timeRequired <= freeBuffer && timeRequired <= maxPercentage) {
            this.addTask(task);
            task.setStatus(Status.IN_PROGRESS);
            this.updateBuffer(timeRequired);
            return true;
        }
        return false;
    }

    public boolean hasPendingTasks() {
        return tasks.stream().anyMatch(task -> task.getStatus() == Status.PENDING);
    }

}
