package it.unicam.cs.mpgc.jtime123014.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

/**
 * Implementazione base di un progetto.
 * 
 * Contiene le informazioni fondamentali come nome, descrizione, stato e la
 * lista delle task.
 */
@Getter
@Setter
public abstract class AbstractProject extends AbstractEntity<UUID> implements Project<UUID> {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Priority priority;
    @NonNull
    private Status status;
    @NonNull
    private List<Task<?>> tasks;

    /**
     * Crea un nuovo progetto con le informazioni base.
     *
     * @param id          l'identificatore univoco.
     * @param name        il nome del progetto.
     * @param description la descrizione del progetto.
     */
    public AbstractProject(UUID id, @NonNull String name, @NonNull String description) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setPriority(Priority.MEDIUM);
        this.setStatus(Status.IN_PROGRESS);
        this.setTasks(new ArrayList<Task<?>>());
    }

    /**
     * Imposta lo stato del progetto a {@link Status#COMPLETED} se tutte le task del
     * progetto sono completate
     * 
     * @return true se il progetto è stato completato, false altrimenti
     * @exception IllegalStateException se non tutte le task del progetto sono
     *                                  completate
     */
    @Override
    public boolean completeProject() {
        if (isAllTasksCompleted()) {
            this.status = Status.COMPLETED;
            return true;
        } else {
            throw new IllegalStateException("Non tutte le task del progetto sono completate");
        }

    }

    /**
     * Inserisce una task nel progetto
     * 
     * @param task la task da inserire.
     * @return true se la task è stata inserita, false altrimenti.
     * @exception IllegalArgumentException se la task è null
     * @exception IllegalArgumentException se la task esiste già
     */
    @Override
    public boolean insertTask(@NonNull Task<?> task) {
        if (tasks.contains(task)) {
            throw new IllegalArgumentException("La task esiste già");
        }
        return tasks.add(task);
    }

    /**
     * Rimuove una task dal progetto
     * 
     * @param task la task da rimuovere.
     * @return true se la task è stata rimossa, false altrimenti.
     * @exception IllegalArgumentException se la task è null
     */
    @Override
    public boolean deleteTask(@NonNull Task<?> task) {
        return tasks.remove(task);
    }

    /**
     * Imposta la lista delle task del progetto
     * 
     * @param newTasks la nuova lista delle task da impostare.
     * @exception IllegalArgumentException se la lista delle task è null
     * @exception IllegalArgumentException se la lista contiene task null
     */
    @Override
    public void setTasks(@NonNull List<Task<?>> newTasks) {
        if (newTasks.stream().anyMatch(this::isTaskMissing)) {
            throw new IllegalArgumentException("La lista non può contenere task null");
        }
        this.tasks = new ArrayList<>(newTasks);
    }

    // ---METODI HELPER--

    /**
     * Verifica se tutte le task del progetto sono completate
     * 
     * @return true se tutte le task sono completate, false altrimenti
     */
    private boolean isAllTasksCompleted() {
        return tasks.stream()
                .filter(s -> s.getStatus() != Status.COMPLETED)
                .findAny()
                .isEmpty();
    }

    /**
     * Verifica se la task è null
     * 
     * @param task
     * @return true se la task è null, false altrimenti
     */
    private boolean isTaskMissing(Task<?> task) {
        return task == null;
    }

    @Override
    public List<Task<?>> getPendingTasks() {
        return tasks.stream()
                .filter(s -> s.getStatus() == Status.PENDING)
                .collect(Collectors.toList());
    }

}
