package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Implementazione base di un calendario.
 * 
 * Gestisce le informazioni comuni come il mese, l'anno, e le liste di giorni e
 * progetti.
 */
@Getter
@Setter
public abstract class AbstractCalendar extends AbstractEntity<UUID> implements Calendar<UUID> {

    @NonNull
    private Months month;

    private int year;

    @NonNull
    private List<Day<?>> days;

    private List<Project<?>> projects;

    /**
     * Costruisce un nuovo calendario.
     *
     * @param id       l'identificatore univoco.
     * @param month    il mese di riferimento.
     * @param year     l'anno di riferimento.
     * @param days     la lista dei giorni.
     * @param projects la lista dei progetti.
     */
    public AbstractCalendar(UUID id, Months month, int year, List<Day<?>> days, List<Project<?>> projects) {
        super(id);
        this.month = month;
        this.year = year;
        this.days = days;
        this.projects = projects;

    }

    /**
     * Imposta l'anno del calendario.
     *
     * @param year l'anno da impostare.
     * @throws IllegalArgumentException se l'anno è negativo.
     */
    @Override
    public void setYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("L'anno non può essere negativo");
        }
        this.year = year;
    }

    /**
     * Imposta la lista dei giorni del calendario.
     *
     * @param days la lista dei giorni.
     * @throws NullPointerException se la lista è null.
     */
    @Override
    public void setDays(List<Day<?>> days) {
        if (isNull(days)) {
            throw new NullPointerException("La lista dei giorni non può essere null");
        }
        this.days = days;
    }

    /**
     * Aggiunge un giorno alla lista dei giorni.
     *
     * @param day il giorno da aggiungere.
     * @return true se il giorno è stato aggiunto, false altrimenti.
     * @throws NullPointerException     se il giorno è null.
     * @throws IllegalArgumentException se il giorno è già presente.
     */
    @Override
    public boolean addDay(Day<?> day) {
        if (isNull(day)) {
            throw new NullPointerException("Il giorno non può essere null");
        }
        if (days.contains(day)) {
            throw new IllegalArgumentException("Il giorno esiste già");
        }
        return days.add(day);
    }

    /**
     * Rimuove un giorno dalla lista.
     *
     * @param day il giorno da rimuovere.
     * @return true se il giorno è stato rimosso, false altrimenti.
     * @throws NullPointerException     se il giorno è null.
     * @throws IllegalArgumentException se il giorno non è presente.
     */
    @Override
    public boolean removeDay(Day<?> day) {
        if (isNull(day)) {
            throw new NullPointerException("Il giorno non può essere null");
        }
        if (!days.contains(day)) {
            throw new IllegalArgumentException("Il giorno non esiste");
        }
        return days.remove(day);
    }

    /**
     * Aggiunge un progetto alla lista.
     *
     * @param project il progetto da aggiungere.
     * @return true se il progetto è stato aggiunto, false altrimenti.
     * @throws NullPointerException     se il progetto è null.
     * @throws IllegalArgumentException se il progetto è già presente.
     */
    @Override
    public boolean addProject(Project<?> project) {
        if (isNull(project)) {
            throw new NullPointerException("Il progetto non può essere null");
        }
        if (projects.contains(project)) {
            throw new IllegalArgumentException("Il progetto esiste già");
        }
        return projects.add(project);
    }

    /**
     * Rimuove un progetto dalla lista.
     *
     * @param project il progetto da rimuovere.
     * @return true se il progetto è stato rimosso, false altrimenti.
     * @throws NullPointerException     se il progetto è null.
     * @throws IllegalArgumentException se il progetto non è presente.
     */
    @Override
    public boolean removeProject(Project<?> project) {
        if (isNull(project)) {
            throw new NullPointerException("Il progetto non può essere null");
        }
        if (!projects.contains(project)) {
            throw new IllegalArgumentException("Il progetto non esiste");
        }
        return projects.remove(project);
    }

    @Override
    public List<Project<?>> getProjects() {
        return projects;
    }

    // --METODI HELPER--
    /**
     * Controlla se un oggetto è null
     * 
     * @param obj l'oggetto da controllare
     * @return true se l'oggetto è null, false altrimenti
     */
    private boolean isNull(Object obj) {
        return obj == null;
    }

    @Override
    public boolean hasDay(LocalDate day) {
        return days.stream().anyMatch(d -> d.getId().equals(day));
    }

}
