package pokemon;

import java.util.List;
import java.util.Scanner;

/**
 * Interface console principale pour le jeu Pokémon.
 * Permet de choisir son Pokémon et de combattre.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        afficherBanniere();

        System.out.println("\nChoisissez votre Pokémon :\n");
        List<Pokemon> disponibles = Pokédex.getTous();
        for (int i = 0; i < disponibles.size(); i++) {
            Pokemon p = disponibles.get(i);
            System.out.printf("  %2d. %-12s [%s] Niv.%d HP=%d ATK=%d DEF=%d VIT=%d\n",
                    i + 1, p.getEspece(), p.getType().getDisplayName(),
                    p.getNiveau(), p.getHpMax(), p.getAttaque(), p.getDefense(), p.getVitesse());
        }

        System.out.print("\nVotre choix (1-" + disponibles.size() + ") : ");
        int choix = lireEntier(1, disponibles.size()) - 1;
        Pokemon joueur = Pokédex.creerPokemon(choix);
        System.out.println("\nVous avez choisi " + joueur.getEspece() + " !\n");

        // Choisir un adversaire aléatoire différent
        int idxAdversaire;
        do {
            idxAdversaire = (int) (Math.random() * disponibles.size());
        } while (idxAdversaire == choix);
        Pokemon adversaire = Pokédex.creerPokemon(idxAdversaire);
        System.out.println("Votre adversaire : " + adversaire.getEspece() + " Niv." + adversaire.getNiveau() + "\n");

        Combat combat = new Combat(joueur, adversaire);

        // Boucle de combat
        while (!combat.estTermine()) {
            System.out.println("\n" + joueur.getEspece() + " HP: " + joueur.getHp() + "/" + joueur.getHpMax()
                    + "  |  " + adversaire.getEspece() + " HP: " + adversaire.getHp() + "/" + adversaire.getHpMax());

            // Afficher les attaques disponibles
            System.out.println("\nVos attaques :");
            List<Attaque> attaques = joueur.getAttaques();
            for (int i = 0; i < attaques.size(); i++) {
                System.out.printf("  %d. %s\n", i + 1, attaques.get(i).toString());
            }

            System.out.print("\nChoisissez une attaque (1-" + attaques.size() + ") : ");
            int choixAttaque = lireEntier(1, attaques.size()) - 1;
            Attaque attaqueJoueur = attaques.get(choixAttaque);

            // L'IA choisit une attaque
            Attaque attaqueIA = combat.choisirAttaqueIA();

            // Exécuter le tour
            String resultat = combat.executerTour(attaqueJoueur, attaqueIA);
            System.out.println("\n" + resultat);
        }

        // Fin de combat
        System.out.println("\n--- Fin du combat ---\n");
        if (!joueur.isDead()) {
            System.out.println("🏆 Victoire ! " + joueur.getEspece() + " est toujours debout !");
        } else {
            System.out.println("💀 Défaite... " + joueur.getEspece() + " est K.O.");
        }
        System.out.println("\n" + joueur.toDetailString());

        scanner.close();
    }

    private static void afficherBanniere() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          ⚡ POKÉMON - JEU JAVA ⚡        ║");
        System.out.println("║         Combat tour par tour             ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static int lireEntier(int min, int max) {
        while (true) {
            try {
                String ligne = scanner.nextLine().trim();
                int val = Integer.parseInt(ligne);
                if (val >= min && val <= max) return val;
                System.out.print("Veuillez entrer un nombre entre " + min + " et " + max + " : ");
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Réessayez : ");
            }
        }
    }
}
