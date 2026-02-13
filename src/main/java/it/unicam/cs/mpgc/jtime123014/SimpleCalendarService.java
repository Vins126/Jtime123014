package it.unicam.cs.mpgc.jtime123014;

import java.util.List;
import java.util.stream.Collectors;


import java.time.LocalDate;

public abstract class SimpleCalendarService implements CalendarService {

    /**
     * Aggiorna la finestra temporale assicurando che ci siano 31 giorni futuri a
     * quello dato al metodo.
     * 
     * @param today la data odierna (o l'ultimo giorno valido).
     */
    @Override
    public void updateRollingWindow(Calendar<?> calendar, LocalDate today, int buffer) {
        for (int i = 0; i < 31; i++) {
            LocalDate targetId = plusDays(today, i);
            if (!calendar.hasDay(targetId)) {
                calendar.addDay(createDay(targetId, buffer));
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

    // -- ABSTRACT --
    /**
     * Crea un giorno con l'id specificato
     * 
     * @param day    id del giorno
     * @param buffer buffer del giorno
     * @return il giorno creato
     */
    protected Day<LocalDate> createDay(LocalDate day, int buffer) {
        return new SimpleDay(day, buffer);
    }

    /**
     * Restituisce l' i-esimo giorno successivo a quello specificato
     * 
     * @param day il giorno da cui partire
     * @param i   il numero di giorni da aggiungere
     * @return l' i-esimo giorno successivo
     */
    protected abstract LocalDate plusDays(LocalDate day, int i);
    // --------

    /**
     * Restituisce i giorni passati
     * 
     * @param today la data odierna
     * @return lista dei giorni passati
     */
    private List<Day<?>> getPastDays(Calendar<?> calendar, LocalDate today) {
        return calendar.getDays().stream()
                .filter(d -> ((LocalDate) d.getId()).compareTo(today) < 0)
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
                .filter(d -> getPastDays(calendar, today).contains(d))
                .anyMatch(d -> d.hasPendingTasks());
    }

    /**
     * Metodo per il sort dei giorni in ordine sequenziale (da calendario)
     * 
     * @param days lista dei giorni da ordinare
     * @return lista dei giorni ordinati
     */
    private List<Day<?>> sortDays(List<Day<?>> days) {
        return days.stream()
                .sorted((d1, d2) -> ((LocalDate) d1.getId()).compareTo((LocalDate) d2.getId()))
                .collect(Collectors.toList());
    }

}
