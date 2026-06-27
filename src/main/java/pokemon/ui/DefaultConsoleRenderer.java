package pokemon.ui;

import pokemon.Attaque;
import pokemon.CombatEvent;
import pokemon.Pokemon;
import pokemon.TourResult;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Implémentation console standard de {@link ConsoleRenderer}.
 *
 * Affiche le jeu dans le terminal via System.out et lit les choix via Scanner.
 * Toute l'I/O est concentrée ici ; le moteur de combat reste pur.
 */
public class DefaultConsoleRenderer implements ConsoleRenderer {

    private final Scanner scanner;

    public DefaultConsoleRenderer() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void afficherBanniere() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          ⚡ POKÉMON - JEU JAVA ⚡        ║");
        System.out.println("║         Combat tour par tour             ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    @Override
    public void displayPokemonList(List<Pokemon> disponibles) {
        System.out.println("\nChoisissez votre Pokémon :\n");
        for (int i = 0; i < disponibles.size(); i++) {
            Pokemon p = disponibles.get(i);
            System.out.printf("  %2d. %-12s [%s] Niv.%d HP=%d ATK=%d DEF=%d VIT=%d\n",
                    i + 1, p.getEspece(), p.getType().getDisplayName(),
                    p.getNiveau(), p.getHpMax(), p.getAttaque(), p.getDefense(), p.getVitesse());
        }
    }

    @Override
    public int promptChoice(String prompt, int min, int max) {
        System.out.print(prompt);
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

    @Override
    public void displayHp(Pokemon joueur, Pokemon adversaire) {
        System.out.println("\n" + joueur.getEspece() + " HP: " + joueur.getHp() + "/" + joueur.getHpMax()
                + "  |  " + adversaire.getEspece() + " HP: " + adversaire.getHp() + "/" + adversaire.getHpMax());
    }

    @Override
    public void displayAttaques(List<Attaque> attaques) {
        System.out.println("\nVos attaques :");
        for (int i = 0; i < attaques.size(); i++) {
            System.out.printf("  %d. %s\n", i + 1, attaques.get(i).toString());
        }
    }

    @Override
    public void displayCombatTurn(TourResult resultat) {
        // On utilise le résumé texte backward-compatible produit par le moteur.
        System.out.println("\n" + resultat.getResumeTexte());
    }

    @Override
    public void displayEndScreen(Pokemon joueur, Pokemon adversaire) {
        System.out.println("\n--- Fin du combat ---\n");
        if (!joueur.isDead()) {
            System.out.println("🏆 Victoire ! " + joueur.getEspece() + " est toujours debout !");
        } else {
            System.out.println("💀 Défaite... " + joueur.getEspece() + " est K.O.");
        }
        System.out.println("\n" + joueur.toDetailString());
    }

    @Override
    public String formatEvent(CombatEvent event) {
        if (event instanceof CombatEvent.AttaqueEvent) {
            CombatEvent.AttaqueEvent e = (CombatEvent.AttaqueEvent) event;
            if (!e.isReussie()) {
                return e.getAttaquant().getNom() + " utilise " + e.getAttaque().getNom() + " ! Mais cela échoue...";
            }
            if (e.getMultiplicateur() == 0.0) {
                return e.getAttaquant().getNom() + " utilise " + e.getAttaque().getNom() + " ! "
                        + e.getCible().getNom() + " est immunisé !";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(e.getAttaquant().getNom()).append(" utilise ").append(e.getAttaque().getNom()).append(" ! ");
            if (e.getMultiplicateur() > 1.0) sb.append("C'est super efficace ! ");
            else if (e.getMultiplicateur() < 1.0) sb.append("Ce n'est pas très efficace... ");
            sb.append(e.getCible().getNom()).append(" perd ").append(e.getDegats()).append(" HP (")
                    .append(e.getCible().getHp()).append("/").append(e.getCible().getHpMax()).append(")");
            return sb.toString();
        } else if (event instanceof CombatEvent.KoEvent) {
            return ((CombatEvent.KoEvent) event).getPokemon().getNom() + " est K.O. !";
        } else if (event instanceof CombatEvent.VictoireEvent) {
            return ((CombatEvent.VictoireEvent) event).getVainqueur().getNom() + " remporte le combat !";
        } else if (event instanceof CombatEvent.XpEvent) {
            CombatEvent.XpEvent e = (CombatEvent.XpEvent) event;
            String base = e.getPokemon().getNom() + " gagne " + e.getXpGagne() + " XP !";
            if (e.isLevelUp()) {
                base += " 🎉 " + e.getPokemon().getNom() + " monte au niveau " + e.getPokemon().getNiveau() + " !";
            }
            return base;
        }
        return event.toString();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /** Ferme le scanner sous-jacent. */
    public void close() {
        scanner.close();
    }
}
