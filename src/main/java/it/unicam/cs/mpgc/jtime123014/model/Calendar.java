package it.unicam.cs.mpgc.jtime123014.model;

import java.util.List;
import java.time.LocalDate;

/**
 * Interfaccia radice del modello di dominio (Aggregate Root).
 * <p>
 * Il {@code Calendar} è il contenitore principale che aggrega:
 * <ul>
 * <li>I {@link Project}, che contengono le attività da svolgere.</li>
 * <li>I {@link Day}, che rappresentano la linea temporale disponibile per
 * svolgere le attività.</li>
 * </ul>
 * <p>
 * Agisce come banca dati in memoria per il Controller e i Service.
 * Non contiene logica di business complessa (come lo scheduling), ma fornisce i
 * metodi CRUD
 * sicuri per manipolare lo stato dell'applicazione.
 *
 * @param <ID> Il tipo dell'identificativo del calendario.
 */
public interface Calendar<ID> extends Identifiable<ID> {

    /**
     * Imposta il mese di riferimento del calendario (se usato come vista mensile).
     * 
     * @param month il mese.
     * @throws NullPointerException se il mese è null.
     */
    void setMonth(Months month);

    /**
     * Restituisce il mese corrente del calendario.
     * 
     * @return il mese.
     */
    Months getMonth();

    /**
     * Imposta l'anno di riferimento.
     * 
     * @param year l'anno.
     * @throws IllegalArgumentException se l'anno è negativo.
     */
    void setYear(int year);

    /**
     * Restituisce l'anno corrente.
     * 
     * @return l'anno.
     */
    int getYear();

    /**
     * Sostituisce l'intera lista dei giorni gestiti dal calendario.
     * 
     * @param days la nuova lista di giorni.
     * @throws NullPointerException se la lista è null.
     */
    void setDays(List<Day<?>> days);

    /**
     * Restituisce la lista di tutti i giorni attualmente gestiti (es. la finestra
     * di 31 giorni).
     * 
     * @return lista dei giorni.
     */
    List<Day<?>> getDays();

    /**
     * Aggiunge un nuovo giorno al calendario.
     * 
     * @param day il giorno da aggiungere.
     * @return {@code true} se aggiunto con successo.
     * @throws NullPointerException     se il giorno è null.
     * @throws IllegalArgumentException se un giorno con lo stesso ID esiste già.
     */
    boolean addDay(Day<?> day);

    /**
     * Rimuove un giorno dal calendario.
     * 
     * @param day il giorno da rimuovere.
     * @return {@code true} se rimosso con successo.
     * @throws NullPointerException se il giorno è null.
     */
    boolean removeDay(Day<?> day);

    /**
     * Aggiunge un nuovo progetto al calendario.
     * 
     * @param project il progetto da aggiungere.
     * @return {@code true} se aggiunto con successo.
     * @throws NullPointerException     se il progetto è null.
     * @throws IllegalArgumentException se il progetto esiste già.
     */
    boolean addProject(Project<?> project);

    /**
     * Rimuove un progetto dal calendario.
     * ATTENZIONE: Solitamente è permesso rimuovere solo progetti completati.
     * 
     * @param project il progetto da rimuovere.
     * @return {@code true} se rimosso.
     * @throws NullPointerException  se il progetto è null.
     * @throws IllegalStateException se si tenta di rimuovere un progetto non
     *                               completato (dipende dall'impl).
     */
    boolean removeProject(Project<?> project);

    /**
     * Restituisce la lista di tutti i progetti gestiti.
     * 
     * @return lista dei progetti.
     */
    List<Project<?>> getProjects();

    /**
     * Verifica l'esistenza di un giorno nel calendario dato il suo identificativo
     * temporale.
     * <p>
     * Metodo essenziale per evitare duplicazioni e per i servizi che devono
     * navigare il calendario.
     * 
     * @param day la data (ID) del giorno da cercare.
     * @return {@code true} se il giorno esiste, {@code false} altrimenti.
     */
    boolean hasDay(LocalDate day);

}
