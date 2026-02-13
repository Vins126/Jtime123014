package it.unicam.cs.mpgc.jtime123014.model;

import it.unicam.cs.mpgc.jtime123014.service.TestCalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CalendarRollingTest {

    private TestModels.TestCalendar calendar;
    private TestCalendarService service;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        today = LocalDate.of(2024, 1, 1);
        calendar = new TestModels.TestCalendar(UUID.randomUUID());
        service = new TestCalendarService();
    }

    @Test
    void testInitialRollingWindow() {
        // Avvia con oggi. Deve creare 31 giorni
        service.updateRollingWindow(calendar, today, 8); // Buffer 8 ore

        List<Day<?>> days = calendar.getDays();
        assertEquals(31, days.size());
        assertEquals(today, days.get(0).getId());
        assertEquals(today.plusDays(30), days.get(30).getId());
    }

    @Test
    void testCleanupAndRefill() {
        // Setup iniziale
        service.updateRollingWindow(calendar, today, 8);
        assertEquals(31, daysCount());

        // Simula passaggio del tempo: oggi diventa today + 5.
        // Puliamo il passato
        LocalDate newToday = today.plusDays(5);
        service.cleanupPastDays(calendar, newToday);

        List<Day<?>> days = calendar.getDays();
        assertEquals(26, days.size(), "Dovrebbero restare 26 giorni (5..30)");
        assertEquals(newToday, days.get(0).getId(), "Il primo giorno dovrebbe essere newToday");

        // Aggiorna finestra per riempire i buchi futuri
        service.updateRollingWindow(calendar, newToday, 8);

        days = calendar.getDays();
        assertEquals(31, days.size(), "Deve tornare a 31 giorni");
        assertEquals(newToday, days.get(0).getId());
        assertEquals(newToday.plusDays(30), days.get(30).getId(), "L'ultimo giorno dovrebbe essere newToday+30");
    }

    @Test
    void testNoDuplicates() {
        service.updateRollingWindow(calendar, today, 8);
        service.updateRollingWindow(calendar, today, 8); // chiama due volte
        assertEquals(31, daysCount(), "Non deve duplicare i giorni esistenti");
    }

    private int daysCount() {
        return calendar.getDays().size();
    }
}
