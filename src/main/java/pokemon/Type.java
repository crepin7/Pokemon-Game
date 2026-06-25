package pokemon;

/**
 * Enumération des 18 types officiels Pokémon.
 * Chaque type a un nom et des modificateurs de dégâts contre d'autres types.
 */
public enum Type {
    NORMAL("Normal"),
    FEU("Feu"),
    EAU("Eau"),
    PLANTE("Plante"),
    ELECTRIK("Électrik"),
    GLACE("Glace"),
    COMBAT("Combat"),
    POISON("Poison"),
    SOL("Sol"),
    VOL("Vol"),
    PSY("Psy"),
    INSECTE("Insecte"),
    ROCHE("Roche"),
    SPECTRE("Spectre"),
    DRAGON("Dragon"),
    TENEBRES("Ténèbres"),
    ACIER("Acier"),
    FEE("Fée");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retourne le multiplicateur de dégâts de ce type contre le type cible.
     * Les règles suivent la table officielle Pokémon (génération VI+).
     */
    public double getMultiplier(Type cible) {
        if (this == NORMAL) {
            if (cible == ROCHE || cible == ACIER) return 0.5;
            if (cible == SPECTRE) return 0.0;
        } else if (this == FEU) {
            if (cible == FEU || cible == EAU || cible == ROCHE || cible == DRAGON) return 0.5;
            if (cible == PLANTE || cible == GLACE || cible == INSECTE || cible == ACIER) return 2.0;
        } else if (this == EAU) {
            if (cible == EAU || cible == PLANTE || cible == DRAGON) return 0.5;
            if (cible == FEU || cible == SOL || cible == ROCHE) return 2.0;
        } else if (this == PLANTE) {
            if (cible == FEU || cible == PLANTE || cible == POISON || cible == VOL || cible == INSECTE || cible == DRAGON || cible == ACIER) return 0.5;
            if (cible == EAU || cible == SOL || cible == ROCHE) return 2.0;
        } else if (this == ELECTRIK) {
            if (cible == ELECTRIK || cible == PLANTE || cible == DRAGON) return 0.5;
            if (cible == EAU || cible == VOL) return 2.0;
            if (cible == SOL) return 0.0;
        } else if (this == GLACE) {
            if (cible == FEU || cible == EAU || cible == GLACE || cible == ACIER) return 0.5;
            if (cible == PLANTE || cible == SOL || cible == VOL || cible == DRAGON) return 2.0;
        } else if (this == COMBAT) {
            if (cible == POISON || cible == VOL || cible == PSY || cible == INSECTE || cible == FEE || cible == SPECTRE) return 0.5;
            if (cible == NORMAL || cible == GLACE || cible == ROCHE || cible == TENEBRES || cible == ACIER) return 2.0;
        } else if (this == POISON) {
            if (cible == POISON || cible == SOL || cible == ROCHE || cible == SPECTRE) return 0.5;
            if (cible == PLANTE || cible == FEE) return 2.0;
            if (cible == ACIER) return 0.0;
        } else if (this == SOL) {
            if (cible == PLANTE || cible == INSECTE) return 0.5;
            if (cible == FEU || cible == ELECTRIK || cible == POISON || cible == ROCHE || cible == ACIER) return 2.0;
            if (cible == VOL) return 0.0;
        } else if (this == VOL) {
            if (cible == ELECTRIK || cible == ROCHE || cible == ACIER) return 0.5;
            if (cible == PLANTE || cible == COMBAT || cible == INSECTE) return 2.0;
        } else if (this == PSY) {
            if (cible == PSY || cible == ACIER) return 0.5;
            if (cible == COMBAT || cible == POISON) return 2.0;
            if (cible == TENEBRES) return 0.0;
        } else if (this == INSECTE) {
            if (cible == FEU || cible == COMBAT || cible == POISON || cible == VOL || cible == SPECTRE || cible == ACIER || cible == FEE) return 0.5;
            if (cible == PLANTE || cible == PSY || cible == TENEBRES) return 2.0;
        } else if (this == ROCHE) {
            if (cible == COMBAT || cible == SOL || cible == ACIER) return 0.5;
            if (cible == FEU || cible == GLACE || cible == VOL || cible == INSECTE) return 2.0;
        } else if (this == SPECTRE) {
            if (cible == TENEBRES) return 0.5;
            if (cible == SPECTRE || cible == DRAGON) return 2.0;
            if (cible == NORMAL) return 0.0;
        } else if (this == DRAGON) {
            if (cible == ACIER) return 0.5;
            if (cible == DRAGON) return 2.0;
            if (cible == FEE) return 0.0;
        } else if (this == TENEBRES) {
            if (cible == COMBAT || cible == TENEBRES || cible == FEE) return 0.5;
            if (cible == PSY || cible == SPECTRE) return 2.0;
        } else if (this == ACIER) {
            if (cible == FEU || cible == EAU || cible == ELECTRIK || cible == ACIER) return 0.5;
            if (cible == GLACE || cible == ROCHE || cible == FEE) return 2.0;
        } else if (this == FEE) {
            if (cible == FEU || cible == POISON || cible == ACIER) return 0.5;
            if (cible == COMBAT || cible == DRAGON || cible == TENEBRES) return 2.0;
        }
        return 1.0;
    }
}
