package pokemon;

/**
 * Événement élémentaire produit pendant un tour de combat.
 * Un tour (TourResult) est une liste ordonnée de CombatEvent, ce qui permet
 * d'accéder aux données de manière structurée plutôt que via du texte concaténé.
 */
public abstract class CombatEvent {

    /**
     * Événement représentant une attaque tentée par un Pokémon sur un autre.
     */
    public static class AttaqueEvent extends CombatEvent {
        private final Pokemon attaquant;
        private final Pokemon cible;
        private final Attaque attaque;
        private final boolean reussie;
        private final int degats;
        private final double multiplicateur;

        public AttaqueEvent(Pokemon attaquant, Pokemon cible, Attaque attaque,
                            boolean reussie, int degats, double multiplicateur) {
            this.attaquant = attaquant;
            this.cible = cible;
            this.attaque = attaque;
            this.reussie = reussie;
            this.degats = degats;
            this.multiplicateur = multiplicateur;
        }

        public Pokemon getAttaquant() { return attaquant; }
        public Pokemon getCible() { return cible; }
        public Attaque getAttaque() { return attaque; }
        /** true si l'attauche a touché la cible. */
        public boolean isReussie() { return reussie; }
        /** Dégâts infligés (0 si l'attaque a raté ou la cible est immunisée). */
        public int getDegats() { return degats; }
        /** Multiplicateur de type appliqué (1.0 = neutre, >1 = super efficace, <1 = peu efficace, 0 = immunisé). */
        public double getMultiplicateur() { return multiplicateur; }
    }

    /**
     * Événement représentant le K.O. d'un Pokémon.
     */
    public static class KoEvent extends CombatEvent {
        private final Pokemon pokemon;

        public KoEvent(Pokemon pokemon) {
            this.pokemon = pokemon;
        }

        public Pokemon getPokemon() { return pokemon; }
    }

    /**
     * Événement représentant la victoire d'un Pokémon (le combat se termine).
     */
    public static class VictoireEvent extends CombatEvent {
        private final Pokemon vainqueur;

        public VictoireEvent(Pokemon vainqueur) {
            this.vainqueur = vainqueur;
        }

        public Pokemon getVainqueur() { return vainqueur; }
    }

    /**
     * Événement représentant le gain d'XP et éventuellement un level-up.
     */
    public static class XpEvent extends CombatEvent {
        private final Pokemon pokemon;
        private final int xpGagne;
        private final boolean levelUp;

        public XpEvent(Pokemon pokemon, int xpGagne, boolean levelUp) {
            this.pokemon = pokemon;
            this.xpGagne = xpGagne;
            this.levelUp = levelUp;
        }

        public Pokemon getPokemon() { return pokemon; }
        public int getXpGagne() { return xpGagne; }
        public boolean isLevelUp() { return levelUp; }
    }
}
