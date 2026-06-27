package pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Résultat structuré d'un tour de combat.
 *
 * Contient le numéro du tour, la liste ordonnée d'événements produits,
 * lesPokémon premier/second attaquants, et un résumé texte (backward-compatible).
 *
 * L'accès structuré aux événements permet à l'IUI de formatter librement la sortie,
 * tout en gardant le moteur de combat (Combat) totalement indépendant de la console.
 */
public class TourResult {
    private final int tour;
    private final Pokemon premier;
    private final Pokemon second;
    private final List<CombatEvent> events;
    private final String resumeTexte;

    public TourResult(int tour, Pokemon premier, Pokemon second,
                      List<CombatEvent> events, String resumeTexte) {
        this.tour = tour;
        this.premier = premier;
        this.second = second;
        this.events = Collections.unmodifiableList(new ArrayList<>(events));
        this.resumeTexte = resumeTexte;
    }

    /** Numéro du tour (1-based). */
    public int getTour() { return tour; }

    /** Pokémon ayant attaqué en premier ce tour (basé sur la vitesse). */
    public Pokemon getPremier() { return premier; }

    /** Pokémon ayant attaqué en second ce tour. */
    public Pokemon getSecond() { return second; }

    /** Liste ordonnée, immuable des événements du tour. */
    public List<CombatEvent> getEvents() { return events; }

    /**
     * Résumé texte du tour, backward-compatible avec l'ancien format.
     * Pratique pour du debugging rapide ou un affichage basique.
     */
    public String getResumeTexte() { return resumeTexte; }

    /** true si le combat s'est terminé pendant ce tour (K.O.). */
    public boolean isCombatTermine() {
        for (CombatEvent e : events) {
            if (e instanceof CombatEvent.VictoireEvent) return true;
        }
        return false;
    }
}
