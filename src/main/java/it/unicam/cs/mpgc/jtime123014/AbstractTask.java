package it.unicam.cs.mpgc.jtime123014;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NonNull
public abstract class AbstractTask extends AbstractEntity<UUID> implements Task<UUID> {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Status status;
    private int durationEstimate;
    private Integer durationActual;
    private int timeConsuming;

    @Override
    public void setDurationEstimate(int durationEstimate) {
        if (durationEstimate < 0) {
            throw new IllegalArgumentException("La durata stimata non può essere negativa");
        }
        this.durationEstimate = durationEstimate;
    }

    @Override
    public void setDurationActual(Integer durationActual) {
        if (durationActual != null && durationActual < 0) {
            throw new IllegalArgumentException("La durata effettiva non può essere negativa");
        }
        this.durationActual = durationActual;
    }

    public AbstractTask(UUID id, @NonNull String name, @NonNull String description, int durationEstimate) {
        super(id);
        if (durationEstimate < 0)
            throw new IllegalArgumentException("La durata stimata non può essere negativa");
        this.name = name;
        this.description = description;
        this.durationEstimate = durationEstimate;
        this.status = Status.PENDING;
        this.durationActual = null;
        this.timeConsuming = durationEstimate;
    }

    /**
     * Calcola la differenza tra la durata effettiva e quella stimata.
     * <p>
     * Questo metodo deve essere chiamato solo dopo che {@code durationActual} è
     * stato impostato.
     *
     * @return la differenza (delta) in minuti.
     * @throws IllegalStateException se la durata effettiva non è stata ancora
     *                               impostata (è null).
     */
    @Override
    public int calculateDelta() {
        if (isDurationActualMissing()) {
            throw new IllegalStateException("La durata effettiva non è stata impostata");
        }
        return this.durationActual - this.durationEstimate;
    }

    /**
     * Completa il task impostando lo stato a {@link Status#COMPLETED}.
     * <p>
     * Questo metodo imposta anche la durata effettiva prima di calcolare il delta.
     *
     * @param durationActual la durata effettiva in minuti.
     * @return il delta calcolato (effettiva - stimata).
     */
    @Override
    public int completeTaskAndSetDuration(int durationActual) {
        this.status = Status.COMPLETED;
        this.durationActual = durationActual;
        return this.calculateDelta();
    }

    /**
     * Completa il task impostando lo stato a {@link Status#COMPLETED}.
     * <p>
     * <b>Nota:</b> Questo metodo presume che {@code durationActual} sia già stato
     * impostato
     * precedentemente (es. tramite setter). Se è null, viene lanciata un'eccezione.
     *
     * @return il delta calcolato (effettiva - stimata).
     * @throws IllegalStateException se la durata effettiva non è stata impostata.
     */
    @Override
    public int completeTask() {
        if (isDurationActualMissing()) {
            throw new IllegalStateException("La durata effettiva non è stata impostata");
        }
        this.status = Status.COMPLETED;
        return this.calculateDelta();
    }

    /**
     * Verifica se lo status della task è {@link Status#COMPLETED}
     * 
     * @return true se lo status è {@link Status#COMPLETED}, false altrimenti
     */
    @Override
    public boolean isCompleted() {
        return this.status == Status.COMPLETED;
    }

    @Override
    public void setTimeConsuming(int timeConsuming) {
        if (timeConsuming < 0) {
            throw new IllegalArgumentException("Il tempo necessario non può essere negativo");
        }
        this.timeConsuming = timeConsuming;
    }

    /**
     * Metodo helper privato che controlla se la durata effettiva è stata impostata
     * 
     * @return true se la durata effettiva non è stata impostata, false altrimenti
     */
    private boolean isDurationActualMissing() {
        return this.durationActual == null;
    }
}
