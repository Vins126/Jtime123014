package it.unicam.cs.mpgc.jtime123014;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCalendar extends AbstractEntity<UUID> implements Calendar<UUID> {

    @NonNull
    private Months month;

    private int year;

    @NonNull
    private List<Day<?>> days;

    private List<Project<?>> projects;

    public AbstractCalendar(UUID id, Months month, int year, List<Day<?>> days, List<Project<?>> projects) {
        super(id);
        this.month = month;
        this.year = year;
        this.days = days;
        this.projects = projects;
    }

    @Override
    public void setYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("L'anno non può essere negativo");
        }
        this.year = year;
    }

    @Override
    public void setDays(List<Day<?>> days) {
        if (isNull(days)) {
            throw new NullPointerException("La lista dei giorni non può essere null");
        }
        this.days = days;
    }

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

    @Override
    public boolean removeProject(Project<?> project) {
        if (isNull(project)) {
            throw new NullPointerException("Il progetto non può essere null");
        }
        if (!projects.contains(project)) {
            throw new IllegalArgumentException("Il progetto non esiste");
        }
        // if (project.getStatus() != Status.COMPLETED) {
        // throw new IllegalStateException("Il progetto non è completato");
        // }
        // REMOVE RESTRICTION AS REQUESTED BY USER
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
