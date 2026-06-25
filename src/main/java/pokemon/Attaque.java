package pokemon;

/**
 * Représente une attaque avec un nom, une puissance, une précision et un type.
 */
public class Attaque {
    private final String nom;
    private final int puissance;
    private final int precision; // 0-100
    private final Type type;
    private final double ratioAtk; // Multiplicateur d'attaque (1.0 = normal)

    public Attaque(String nom, int puissance, int precision, Type type) {
        this(nom, puissance, precision, type, 1.0);
    }

    public Attaque(String nom, int puissance, int precision, Type type, double ratioAtk) {
        this.nom = nom;
        this.puissance = puissance;
        this.precision = Math.max(0, Math.min(100, precision));
        this.type = type;
        this.ratioAtk = ratioAtk;
    }

    public String getNom() { return nom; }
    public int getPuissance() { return puissance; }
    public int getPrecision() { return precision; }
    public Type getType() { return type; }
    public double getRatioAtk() { return ratioAtk; }

    /**
     * Détermine si l'attaque touche selon sa précision.
     */
    public boolean touche() {
        if (precision >= 100) return true;
        return (Math.random() * 100) < precision;
    }

    @Override
    public String toString() {
        return nom + " [" + type.getDisplayName() + "] PUI=" + puissance + " PRE=" + precision + "%";
    }
}
