package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Moteur de combat gérant un affrontement tour-par-tour entre deux Pokémon.
 *
 * Cette classe est un moteur de jeu pur : elle ne réalise AUCUNE entrée/sortie
 * (ni Scanner, ni System.out). Elle retourne des données structurées (TourResult)
 * que l'IUI est responsable d'afficher.
 */
public class Combat {
    private final Pokemon joueur;
    private final Pokemon adversaire;
    private final Random random = new Random();
    private int tour;

    public Combat(Pokemon joueur, Pokemon adversaire) {
        this.joueur = joueur;
        this.adversaire = adversaire;
        this.tour = 0;
    }

    /**
     * Détermine quel Pokémon agit en premier (basé sur la vitesse).
     */
    public Pokemon premierAttaquant() {
        return joueur.getVitesse() >= adversaire.getVitesse() ? joueur : adversaire;
    }

    /**
     * Exécute un tour de combat : les deux Pokémon attaquent dans l'ordre de vitesse.
     *
     * Retourne un {@link TourResult} contenant à la fois les événements structurés
     * du tour et un résumé texte backward-compatible.
     *
     * Aucune lecture/écriture console n'est effectuée : ce moteur est pur.
     */
    public TourResult executerTour(Attaque attaqueJoueur, Attaque attaqueAdversaire) {
        tour++;
        Pokemon premier = premierAttaquant();
        Pokemon second = (premier == joueur) ? adversaire : joueur;
        Attaque attaquePremier = (premier == joueur) ? attaqueJoueur : attaqueAdversaire;
        Attaque attaqueSecond = (premier == joueur) ? attaqueAdversaire : attaqueJoueur;

        List<CombatEvent> events = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Tour ").append(tour).append(" ===\n");

        // Le premier attaque
        executerAttaque(premier, second, attaquePremier, events, sb);
        if (second.isDead()) {
            events.add(new CombatEvent.KoEvent(second));
            events.add(new CombatEvent.VictoireEvent(joueur));
            sb.append("\n").append(second.getNom()).append(" est K.O. !\n");
            sb.append(joueur.getNom()).append(" remporte le combat !\n");
            int xpGagne = 30 + adversaire.getNiveau() * 10;
            boolean levelUp = joueur.gagnerXp(xpGagne);
            events.add(new CombatEvent.XpEvent(joueur, xpGagne, levelUp));
            sb.append(joueur.getNom()).append(" gagne ").append(xpGagne).append(" XP !\n");
            if (levelUp) {
                sb.append("🎉 ").append(joueur.getNom()).append(" monte au niveau ").append(joueur.getNiveau()).append(" !\n");
            }
            return new TourResult(tour, premier, second, events, sb.toString());
        }

        // Le second attaque
        executerAttaque(second, premier, attaqueSecond, events, sb);
        if (premier.isDead()) {
            events.add(new CombatEvent.KoEvent(premier));
            events.add(new CombatEvent.VictoireEvent(adversaire));
            sb.append("\n").append(premier.getNom()).append(" est K.O. !\n");
            sb.append(adversaire.getNom()).append(" remporte le combat !\n");
        }

        return new TourResult(tour, premier, second, events, sb.toString());
    }

    /**
     * Exécute une attaque d'un Pokémon sur un autre.
     * Enregistre l'événement structuré dans {@code events} et ajoute la description
     * texte dans {@code sb}. Aucune I/O.
     */
    private void executerAttaque(Pokemon attaquant, Pokemon cible, Attaque attaque,
                                 List<CombatEvent> events, StringBuilder sb) {
        sb.append(attaquant.getNom()).append(" utilise ").append(attaque.getNom()).append(" !\n");

        if (!attaque.touche()) {
            events.add(new CombatEvent.AttaqueEvent(attaquant, cible, attaque, false, 0, 1.0));
            sb.append("Mais cela échoue...\n");
            return;
        }

        int degats = attaquant.calculerDegats(attaque, cible);
        double multiplicateur = attaque.getType().getMultiplier(cible.getType());

        if (multiplicateur > 1.0) {
            sb.append("C'est super efficace ! (x").append(multiplicateur).append(")\n");
        } else if (multiplicateur > 0 && multiplicateur < 1.0) {
            sb.append("Ce n'est pas très efficace... (x").append(multiplicateur).append(")\n");
        } else if (multiplicateur == 0.0) {
            // Immunisé : aucun dégâts infligés
            events.add(new CombatEvent.AttaqueEvent(attaquant, cible, attaque, true, 0, multiplicateur));
            sb.append(cible.getNom()).append(" est immunisé !\n");
            return;
        }

        cible.recevoirDegats(degats, attaque.getType());
        events.add(new CombatEvent.AttaqueEvent(attaquant, cible, attaque, true, degats, multiplicateur));
        sb.append(cible.getNom()).append(" perd ").append(degats).append(" HP (").append(cible.getHp()).append("/").append(cible.getHpMax()).append(")\n");
    }

    /**
     * Vérifie si le combat est terminé.
     */
    public boolean estTermine() {
        return joueur.isDead() || adversaire.isDead();
    }

    /**
     * Choisit la meilleure attaque pour l'adversaire (IA heuristique).
     *
     * Heuristique : maximise les dégâts attendus (degâts bruts * précision),
     * en tenant compte de la table de types via {@link Type#getMultiplier}.
     * Ne choisit jamais une attaque à dégâts nuls (immunité) si une alternative
     * existe. En cas d'égalité, choix aléatoire parmi les meilleures.
     */
    public Attaque choisirAttaqueIA() {
        List<Attaque> attaques = adversaire.getAttaques();
        if (attaques.isEmpty()) return null;
        if (attaques.size() == 1) return attaques.get(0);

        // Calcule les dégâts attendus de chaque attaque contre le joueur.
        double[] expected = new double[attaques.size()];
        for (int i = 0; i < attaques.size(); i++) {
            Attaque a = attaques.get(i);
            // Détecte l'immunité via la table de types (multiplicateur == 0).
            double multiplicateur = 1.0;
            for (Type t : joueur.getTypes()) {
                multiplicateur *= a.getType().getMultiplier(t);
            }
            if (multiplicateur == 0.0) {
                expected[i] = 0.0;
                continue;
            }
            int degatsBruts = adversaire.calculerDegats(a, joueur);
            expected[i] = degatsBruts * (a.getPrecision() / 100.0);
        }

        // Détecte s'il existe au moins une attaque non immunisée (dégâts > 0).
        boolean existeNonImmunise = false;
        for (double d : expected) {
            if (d > 0.0) {
                existeNonImmunise = true;
                break;
            }
        }

        // Trouve la valeur maximale, en excluant les attaques immunitaires
        // si une alternative existe (règle : ne jamais gaspiller un tour).
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < expected.length; i++) {
            if (existeNonImmunise && expected[i] == 0.0) continue;
            if (expected[i] > max) max = expected[i];
        }

        // Si toutes les attaques sont immunitaires, max reste -Inf : on choisit
        // alors aléatoirement parmi toutes les attaques (aucun meilleur choix).
        List<Attaque> meilleures = new ArrayList<>();
        for (int i = 0; i < expected.length; i++) {
            if (existeNonImmunise && expected[i] == 0.0) continue;
            if (Double.compare(expected[i], max) == 0) {
                meilleures.add(attaques.get(i));
            }
        }
        if (meilleures.isEmpty()) {
            // Tous les choix sont immunités : fallback aléatoire sur l'ensemble.
            return attaques.get(random.nextInt(attaques.size()));
        }
        return meilleures.get(random.nextInt(meilleures.size()));
    }

    public Pokemon getJoueur() { return joueur; }
    public Pokemon getAdversaire() { return adversaire; }
    public int getTour() { return tour; }
}
