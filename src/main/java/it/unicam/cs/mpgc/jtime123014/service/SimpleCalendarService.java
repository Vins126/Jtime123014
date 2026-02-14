package it.unicam.cs.mpgc.jtime123014.service;

import it.unicam.cs.mpgc.jtime123014.model.*;
import it.unicam.cs.mpgc.jtime123014.util.ServiceConstants;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;

/**
 * Implementazione di base di {@link CalendarService} che lavora con date
 * {@link LocalDate}.
 */
public abstract class SimpleCalendarService implements CalendarService {

    private final DayFactory dayFactory;

    /**
     * Costruttore principale.
     *
     * @param dayFactory la factory per la creazione dei giorni.
     */
    protected SimpleCalendarService(DayFactory dayFactory) {
        this.dayFactory = dayFactory;
    }

    /**
     * Costruttore di default — usa {@link SimpleDay}.
     */
    protected SimpleCalendarService() {
        this(SimpleDay::new);
    }

    /**
     * Aggiorna la finestra temporale assicurando che ci siano 31 giorni futuri a
     * quello dato al metodo.
     *
     * @param today la data odierna (o l'ultimo giorno valido).
     */
    @Override
    public void updateRollingWindow(Calendar<?> calendar, LocalDate today, int buffer) {
        for (int i = 0; i < ServiceConstants.ROLLING_WINDOW_DAYS; i++) {
            LocalDate targetId = plusDays(today, i);
            if (!calendar.hasDay(targetId)) {
                calendar.addDay(dayFactory.create(targetId, buffer));
            }
        }
        calendar.setDays(sortDays(calendar.getDays()));
    }

    /**
     * Rimuove i giorni passati.
     *
     * @param today la data odierna (esclusa dalla rimozione).
     * @return true se ha rimosso qualcosa.
     * @throws IllegalStateException se ci sono task pendenti.
     */
    public boolean cleanupPastDays(Calendar<?> calendar, LocalDate today) {
        if (hasPendingTasksInPast(calendar, today)) {
            throw new IllegalStateException("Ci sono task pendenti nel passato");
        }
        List<Day<?>> daysToRemove = getPastDays(calendar, today);
        for (Day<?> d : daysToRemove) {
            calendar.removeDay(d);
        }
        return true;
    }

    /**
     * Restituisce l'i-esimo giorno successivo a quello specificato.
     *
     * @param day il giorno da cui partire.
     * @param i   il numero di giorni da aggiungere.
     * @return l'i-esimo giorno successivo.
     */
    protected abstract LocalDate plusDays(LocalDate day, int i);

    /**
     * Restituisce i giorni passati .
     *
     * @param today la data odierna.
     * @return lista dei giorni passati.
     */
    private List<Day<?>> getPastDays(Calendar<?> calendar, LocalDate today) {
        return calendar.getDays().stream()
                .filter(d -> d.getId() instanceof LocalDate date && date.isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Controlla se ci sono task pendenti nei giorni precedenti a oggi.
     *
     * @param today la data odierna.
     * @return true se ci sono task pendenti nel passato.
     */
    public boolean hasPendingTasksInPast(Calendar<?> calendar, LocalDate today) {
        return calendar.getDays().stream()
                .filter(d -> d.getId() instanceof LocalDate date && date.isBefore(today))
                .anyMatch(Day::hasPendingTasks);
    }

    /**
     * Ordina i giorni in ordine cronologico .
     *
     * @param days lista dei giorni da ordinare.
     * @return lista dei giorni ordinati.
     */
    private List<Day<?>> sortDays(List<Day<?>> days) {
        return days.stream()
                .sorted((d1, d2) -> {
                    if (d1.getId() instanceof LocalDate ld1 && d2.getId() instanceof LocalDate ld2) {
                        return ld1.compareTo(ld2);
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }

    /**
     * Reimposta lo scheduling del calendario a partire da una data specifica.
     * Resetta tutti i giorni successivi o uguali alla data data.
     *
     * @param calendar il calendario da resettare.
     * @param fromDate la data da cui iniziare il reset.
     */
    @Override
    public void resetSchedule(Calendar<?> calendar, LocalDate fromDate) {
        for (Day<?> day : calendar.getDays()) {
            if (day.getId() instanceof LocalDate date && !date.isBefore(fromDate)) {
                day.reset();
            }
        }
    }

    /**
     * Prepara i giorni per una nuova pianificazione, impostando il buffer iniziale.
     *
     * @param calendar           il calendario da preparare.
     * @param fromDate           la data da cui partire.
     * @param dailyBufferMinutes i minuti di lavoro disponibili per ogni giorno.
     */
    @Override
    public void prepareDaysForScheduling(Calendar<?> calendar, LocalDate fromDate, int dailyBufferMinutes) {
        for (Day<?> day : calendar.getDays()) {
            if (day.getId() instanceof LocalDate date && !date.isBefore(fromDate)) {
                day.setBuffer(dailyBufferMinutes);
                day.setFreeBuffer(dailyBufferMinutes);
            }
        }
    }
}
