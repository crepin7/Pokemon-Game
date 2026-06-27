package pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe principale représentant un Pokémon avec ses statistiques, ses types,
 * et ses attaques. Supporte la progression par niveaux et XP.
 * Supporte un ou deux types (règles officielles Pokémon).
 */
public class Pokemon {
    private final String nom;
    private final List<Type> types;
    private int niveau;
    private int xp;
    private int xpProchainNiveau;

    private int hpMax;
    private int hp;
    private int attaque;
    private int defense;
    private int vitesse;

    private final List<Attaque> attaques;
    private final String espece;

    /**
     * @param espece  Nom de l'espèce (ex: "Dracaufeu")
     * @param nom     Nom donné au Pokémon (peut être personnalisé)
     * @param types   Liste des 1 ou 2 types du Pokémon
     * @param niveau  Niveau initial
     * @param hpMax   HP max à ce niveau
     * @param attaque Stat d'attaque de base
     * @param defense Stat de défense de base
     * @param vitesse Stat de vitesse de base
     */
    public Pokemon(String espece, String nom, List<Type> types, int niveau, int hpMax, int attaque, int defense, int vitesse) {
        if (types == null || types.isEmpty() || types.size() > 2) {
            throw new IllegalArgumentException("Un Pokémon doit avoir 1 ou 2 types.");
        }
        this.espece = espece;
        this.nom = nom;
        this.types = Collections.unmodifiableList(new ArrayList<>(types));
        this.niveau = niveau;
        this.xp = 0;
        this.xpProchainNiveau = calculerXpProchainNiveau(niveau);
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.attaque = attaque;
        this.defense = defense;
        this.vitesse = vitesse;
        this.attaques = new ArrayList<>();
    }

    /**
     * Constructeur simplifié pour niveau 1 avec stats par défaut.
     */
    public Pokemon(String espece, List<Type> types, int niveau) {
        this(espece, espece, types, niveau, 55, 50, 45, 45);
    }

    /**
     * Constructeur backward-compatible avec un seul type.
     */
    public Pokemon(String espece, String nom, Type type, int niveau, int hpMax, int attaque, int defense, int vitesse) {
        this(espece, nom, Collections.singletonList(type), niveau, hpMax, attaque, defense, vitesse);
    }

    /**
     * Constructeur simplifié backward-compatible avec un seul type.
     */
    public Pokemon(String espece, Type type, int niveau) {
        this(espece, espece, Collections.singletonList(type), niveau, 55, 50, 45, 45);
    }

    private int calculerXpProchainNiveau(int niv) {
        // Formule simplifiée : XP nécessaire = niv^2 * 1.2
        return (int) (niv * niv * 1.2);
    }

    // --- Getters ---
    public String getNom() { return nom; }
    public String getEspece() { return espece; }

    /**
     * Retourne le type primaire (backward-compatible).
     */
    public Type getType() { return types.get(0); }

    /**
     * Retourne la liste des types (immuable).
     */
    public List<Type> getTypes() { return types; }

    /**
     * Retourne le type primaire.
     */
    public Type getPrimaryType() { return types.get(0); }

    /**
     * Retourne le second type, ou null si le Pokémon n'a qu'un seul type.
     */
    public Type getSecondaryType() { return types.size() > 1 ? types.get(1) : null; }

    public int getNiveau() { return niveau; }
    public int getHpMax() { return hpMax; }
    public int getHp() { return hp; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getVitesse() { return vitesse; }
    public int getXp() { return xp; }
    public int getXpProchainNiveau() { return xpProchainNiveau; }
    public List<Attaque> getAttaques() { return Collections.unmodifiableList(attaques); }

    public boolean isDead() { return hp <= 0; }

    /**
     * Ajoute une attaque au Pokémon (max 4 attaques).
     */
    public boolean apprendreAttaque(Attaque a) {
        if (attaques.size() >= 4) return false;
        attaques.add(a);
        return true;
    }

    /**
     * Reçoit des dégâts en tenant compte de la défense et des types du défenseur.
     * Le multiplicateur est le produit des multiplicateurs contre chaque type du défenseur.
     * (ex: x2 contre Feu + x2 contre Sol = x4 super efficace)
     * Formule simplifiée : degats = (degatsBruts * multiplicateur_type) * (50 / (50 + defense))
     */
    public void recevoirDegats(int degatsBruts, Type typeAttaque) {
        double multiplicateur = 1.0;
        for (Type t : types) {
            multiplicateur *= typeAttaque.getMultiplier(t);
        }
        double reduction = 50.0 / (50.0 + defense);
        int degats = (int) Math.max(1, degatsBruts * multiplicateur * reduction);
        hp = Math.max(0, hp - degats);
    }

    /**
     * Calcule les dégâts infligés par une attaque.
     * Le multiplicateur est le produit des multiplicateurs contre chaque type de la cible.
     */
    public int calculerDegats(Attaque attaque, Pokemon cible) {
        double multiplicateur = 1.0;
        for (Type t : cible.getTypes()) {
            multiplicateur *= attaque.getType().getMultiplier(t);
        }
        double base = attaque.getPuissance() * attaque.getRatioAtk() * multiplicateur;
        return Math.max(1, (int) base);
    }

    /**
     * Gagne de l'XP et potentiellement monte de niveau.
     * Retourne true si le Pokémon a monté de niveau.
     */
    public boolean gagnerXp(int montant) {
        xp += montant;
        boolean aMonte = false;
        while (xp >= xpProchainNiveau) {
            xp -= xpProchainNiveau;
            monterNiveau();
            aMonte = true;
        }
        return aMonte;
    }

    private void monterNiveau() {
        niveau++;
        xpProchainNiveau = calculerXpProchainNiveau(niveau);
        // Augmentation des stats
        int gainHp = 2 + (int)(Math.random() * 3);
        hpMax += gainHp;
        hp = hpMax; // Soin complet lors du level-up
        attaque += 1 + (int)(Math.random() * 2);
        defense += 1 + (int)(Math.random() * 2);
        vitesse += 1 + (int)(Math.random() * 2);
    }

    /**
     * Soigne le Pokémon à fond.
     */
    public void soigner() {
        hp = hpMax;
    }

    private String typesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            if (i > 0) sb.append("/");
            sb.append(types.get(i).getDisplayName());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return nom + " [" + espece + "] Niv." + niveau + " " + typesToString()
                + " HP=" + hp + "/" + hpMax + " ATK=" + attaque + " DEF=" + defense + " VIT=" + vitesse;
    }

    /**
     * Affiche une ligne formatée pour le détail du Pokémon.
     */
    public String toDetailString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s Niv.%-3d [%s]\n", nom, niveau, typesToString()));
        sb.append(String.format("  HP: %d/%d | ATK: %d | DEF: %d | VIT: %d\n", hp, hpMax, attaque, defense, vitesse));
        sb.append(String.format("  XP: %d/%d\n", xp, xpProchainNiveau));
        sb.append("  Attaques:\n");
        for (Attaque a : attaques) {
            sb.append("    - ").append(a.toString()).append("\n");
        }
        return sb.toString();
    }
}
