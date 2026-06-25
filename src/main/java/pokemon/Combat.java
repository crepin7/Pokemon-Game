package pokemon;

import java.util.Random;

/**
 * Moteur de combat gérant un affrontement tour-par-tour entre deux Pokémon.
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
     * Exécute un tour de combat : l'attaquant choisit une attaque et inflige des dégâts.
     * Retourne un résumé du tour ou null si le combat est terminé.
     */
    public String executerTour(Attaque attaqueJoueur, Attaque attaqueAdversaire) {
        tour++;
        StringBuilder sb = new StringBuilder();
        sb.append("=== Tour ").append(tour).append(" ===\n");

        Pokemon premier = premierAttaquant();
        Pokemon second = (premier == joueur) ? adversaire : joueur;
        Attaque attaquePremier = (premier == joueur) ? attaqueJoueur : attaqueAdversaire;
        Attaque attaqueSecond = (premier == joueur) ? attaqueAdversaire : attaqueJoueur;

        // Le premier attaque
        sb.append(executerAttaque(premier, second, attaquePremier));
        if (second.isDead()) {
            sb.append("\n").append(second.getNom()).append(" est K.O. !\n");
            sb.append(joueur.getNom()).append(" remporte le combat !\n");
            int xpGagne = 30 + adversaire.getNiveau() * 10;
            boolean levelUp = joueur.gagnerXp(xpGagne);
            sb.append(joueur.getNom()).append(" gagne ").append(xpGagne).append(" XP !\n");
            if (levelUp) {
                sb.append("🎉 ").append(joueur.getNom()).append(" monte au niveau ").append(joueur.getNiveau()).append(" !\n");
            }
            return sb.toString();
        }

        // Le second attaque
        sb.append(executerAttaque(second, premier, attaqueSecond));
        if (premier.isDead()) {
            sb.append("\n").append(premier.getNom()).append(" est K.O. !\n");
            sb.append(adversaire.getNom()).append(" remporte le combat !\n");
            return sb.toString();
        }

        return sb.toString();
    }

    /**
     * Exécute une attaque d'un Pokémon sur un autre.
     */
    private String executerAttaque(Pokemon attaquant, Pokemon cible, Attaque attaque) {
        StringBuilder sb = new StringBuilder();
        sb.append(attaquant.getNom()).append(" utilise ").append(attaque.getNom()).append(" !\n");

        if (!attaque.touche()) {
            sb.append("Mais cela échoue...\n");
            return sb.toString();
        }

        int degats = attaquant.calculerDegats(attaque, cible);
        double multiplicateur = attaque.getType().getMultiplier(cible.getType());

        if (multiplicateur > 1.0) {
            sb.append("C'est super efficace ! (x").append(multiplicateur).append(")\n");
        } else if (multiplicateur > 0 && multiplicateur < 1.0) {
            sb.append("Ce n'est pas très efficace... (x").append(multiplicateur).append(")\n");
        } else if (multiplicateur == 0.0) {
            sb.append(cible.getNom()).append(" est immunisé !\n");
            return sb.toString();
        }

        cible.recevoirDegats(degats, attaque.getType());
        sb.append(cible.getNom()).append(" perd ").append(degats).append(" HP (").append(cible.getHp()).append("/").append(cible.getHpMax()).append(")\n");
        return sb.toString();
    }

    /**
     * Vérifie si le combat est terminé.
     */
    public boolean estTermine() {
        return joueur.isDead() || adversaire.isDead();
    }

    /**
     * Choisit une attaque aléatoire pour l'adversaire (IA simple).
     */
    public Attaque choisirAttaqueIA() {
        if (adversaire.getAttaques().isEmpty()) return null;
        return adversaire.getAttaques().get(random.nextInt(adversaire.getAttaques().size()));
    }

    public Pokemon getJoueur() { return joueur; }
    public Pokemon getAdversaire() { return adversaire; }
    public int getTour() { return tour; }
}
