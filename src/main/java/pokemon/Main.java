package pokemon;

import pokemon.ui.ConsoleRenderer;
import pokemon.ui.DefaultConsoleRenderer;

import java.util.List;

/**
 * Point d'entrée (bootstrapping) du jeu Pokémon.
 *
 * Cette classe est fine : elle wire le moteur de combat pur ({@code Combat})
 * à l'interface console ({@code ConsoleRenderer}), lit les choix de l'utilisateur
 * et orchestre le déroulement des tours. Elle ne contient aucune règle métier.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleRenderer ui = new DefaultConsoleRenderer();
        try {
            new Game(ui).run();
        } finally {
            if (ui instanceof DefaultConsoleRenderer) {
                ((DefaultConsoleRenderer) ui).close();
            }
        }
    }

    /**
     * Orchestre une partie : sélection des Pokémon puis boucle de combat.
     * Séparé de {@code main} pour rester testable avec un renderer quelconque.
     */
    static class Game {
        private final ConsoleRenderer ui;

        Game(ConsoleRenderer ui) {
            this.ui = ui;
        }

        void run() {
            ui.afficherBanniere();

            // Sélection du Pokémon du joueur
            List<Pokemon> disponibles = Pokédex.getTous();
            ui.displayPokemonList(disponibles);
            int choix = ui.promptChoice("\nVotre choix (1-" + disponibles.size() + ") : ", 1, disponibles.size()) - 1;
            Pokemon joueur = Pokédex.creerPokemon(choix);
            ui.displayMessage("\nVous avez choisi " + joueur.getEspece() + " !\n");

            // Adversaire aléatoire différent du joueur
            int idxAdversaire;
            do {
                idxAdversaire = (int) (Math.random() * disponibles.size());
            } while (idxAdversaire == choix);
            Pokemon adversaire = Pokédex.creerPokemon(idxAdversaire);
            ui.displayMessage("Votre adversaire : " + adversaire.getEspece() + " Niv." + adversaire.getNiveau() + "\n");

            Combat combat = new Combat(joueur, adversaire);

            // Boucle de combat
            while (!combat.estTermine()) {
                ui.displayHp(joueur, adversaire);

                List<Attaque> attaques = joueur.getAttaques();
                ui.displayAttaques(attaques);

                int choixAttaque = ui.promptChoice(
                        "\nChoisissez une attaque (1-" + attaques.size() + ") : ", 1, attaques.size()) - 1;
                Attaque attaqueJoueur = attaques.get(choixAttaque);

                // L'IA choisit une attaque
                Attaque attaqueIA = combat.choisirAttaqueIA();

                // Exécution du tour par le moteur pur
                TourResult resultat = combat.executerTour(attaqueJoueur, attaqueIA);
                ui.displayCombatTurn(resultat);
            }

            // Fin de combat
            ui.displayEndScreen(joueur, adversaire);
        }
    }
}
