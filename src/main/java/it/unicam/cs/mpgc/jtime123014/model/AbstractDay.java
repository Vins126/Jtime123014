package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import it.unicam.cs.mpgc.jtime123014.util.ValidationUtils;

import java.time.LocalDate;

/**
 * Implementazione base di un giorno.
 * <p>
 * Mantiene la lista delle task assegnate e gestisce il tempo disponibile
 * (buffer).
 */
@Getter
public abstract class AbstractDay extends AbstractEntity<LocalDate> implements Day<LocalDate> {

    private int buffer;

    @Setter
    @Getter
    private int freeBuffer;

    @Setter
    @NonNull
    private List<Task<?>> tasks;

    /**
     * Costruttore base con buffer inizialmente a zero.
     *
     * @param id la data del giorno.
     */
    public AbstractDay(LocalDate id) {
        super(id);
        this.setBuffer(0);
        this.setFreeBuffer(0);
        this.setTasks(new ArrayList<Task<?>>());
    }

    /**
     * Costruttore con buffer specificato.
     *
     * @param id     la data del giorno.
     * @param buffer i minuti di lavoro disponibili.
     */
    public AbstractDay(LocalDate id, int buffer) {
        super(id);
        this.setBuffer(buffer);
        this.setFreeBuffer(buffer);
        this.setTasks(new ArrayList<Task<?>>());
    }

    /**
     * Costruttore completo.
     *
     * @param id     la data del giorno.
     * @param buffer i minuti di lavoro disponibili.
     * @param tasks  la lista iniziale delle task.
     */
    public AbstractDay(LocalDate id, int buffer, List<Task<?>> tasks) {
        super(id);
        this.setBuffer(buffer);
        this.setFreeBuffer(buffer);
        this.setTasks(tasks);
    }

    /**
     * Costruttore con lista di task, buffer a zero.
     *
     * @param id    la data del giorno.
     * @param tasks la lista iniziale delle task.
     */
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
        ValidationUtils.checkNonNegative(buffer, "Il buffer");
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

    public boolean hasPendingTasks() {
        return tasks.stream().anyMatch(task -> task.getStatus() == Status.PENDING);
    }

    /**
     * Reimposta lo stato del giorno alle condizioni iniziali.
     * Le task non completate tornano in stato PENDING, la lista task viene svuotata
     * e il buffer viene ripristinato.
     */
    @Override
    public void reset() {
        // Resetta lo stato delle task a PENDING (se non completate)
        for (Task<?> task : this.tasks) {
            if (task.getStatus() != Status.COMPLETED) {
                task.setStatus(Status.PENDING);
            }
        }
        // Rimuove tutte le task dal giorno
        this.tasks.clear();
        // Resetta il buffer del giorno al suo massimo
        this.setFreeBuffer(this.getBuffer());
    }
}
