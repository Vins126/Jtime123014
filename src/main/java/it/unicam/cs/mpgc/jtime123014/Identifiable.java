package it.unicam.cs.mpgc.jtime123014;

/*
    Interfaccia che definisce le operazioni relative all'identificazione
    da implementare in chiunque voglia definire un'entità che abbia un identificatore
*/

public interface Identifiable<ID> {
    /**
     * Restituisce l'identificatore univoco dell'entità.
     *
     * @return l'identificatore come {@link ID}.
     */
    ID getId();

    /**
     * Imposta l'identificatore univoco dell'entità.
     * 
     * @param id l'identificatore da impostare.
     */
    void setId(ID id);
}
