package pokemon.ui;

import pokemon.Attaque;
import pokemon.CombatEvent;
import pokemon.Pokemon;
import pokemon.TourResult;

import java.util.List;

/**
 * Interface responsable de TOUTES les sorties utilisateur du jeu Pokémon.
 *
 * Le moteur de combat ({@code pokemon.Combat}) est pur et ne produit aucune IUI ;
 * c'est le {@code ConsoleRenderer} qui formate et affiche les données de jeu
 * (bannière, liste des Pokémon, tours de combat, écran de fin, etc.).
 *
 * Découpler ainsi la logique de jeu de l'affichage permet de remplacer
 * cette console par une IUI graphique, des tests assertions sur les sorties, etc.
 */
public interface ConsoleRenderer {

    /** Affiche la bannière d'accueil du jeu. */
    void afficherBanniere();

    /**
     * Affiche la liste des Pokémon disponibles à la sélection.
     * @param disponibles liste des Pokémon proposés au joueur
     */
    void displayPokemonList(List<Pokemon> disponibles);

    /**
     * Invite l'utilisateur à faire un choix dans l'intervalle [min, max].
     * @param prompt message d'invite
     * @param min valeur minimale acceptée (1-based)
     * @param max valeur maximale acceptée (1-based)
     * @return le choix de l'utilisateur (1-based)
     */
    int promptChoice(String prompt, int min, int max);

    /**
     * Affiche l'état courant des deux Pokémon (HP).
     * @param joueur Pokémon du joueur
     * @param adversaire Pokémon adverse
     */
    void displayHp(Pokemon joueur, Pokemon adversaire);

    /**
     * Affiche la liste des attaques disponibles pour le joueur.
     * @param attaques attaques du joueur
     */
    void displayAttaques(List<Attaque> attaques);

    /**
     * Affiche le déroulement complet d'un tour de combat.
     * @param resultat résultat structuré du tour
     */
    void displayCombatTurn(TourResult resultat);

    /**
     * Affiche l'écran de fin de combat.
     * @param joueur Pokémon du joueur
     * @param adversaire Pokémon adverse
     */
    void displayEndScreen(Pokemon joueur, Pokemon adversaire);

    // --- Méthodes utilitaires d'affichage d'événements structurés ---

    /**
     * Formate un événement de combat en texte lisible.
     * Utilisé par défaut par {@link #displayCombatTurn}.
     */
    String formatEvent(CombatEvent event);

    /**
     * Affiche un message brut (confirmation, info).
     */
    void displayMessage(String message);
}
