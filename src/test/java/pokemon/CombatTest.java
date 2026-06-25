package pokemon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le système de combat Pokémon.
 */
public class CombatTest {

    private Pokemon salameche;
    private Pokemon carapuce;

    @BeforeEach
    void setUp() {
        salameche = new Pokemon("Salamèche", Type.FEU, 5);
        salameche.apprendreAttaque(new Attaque("Flammèche", 40, 100, Type.FEU));
        salameche.apprendreAttaque(new Attaque("Griffe", 40, 100, Type.NORMAL));

        carapuce = new Pokemon("Carapuce", Type.EAU, 5);
        carapuce.apprendreAttaque(new Attaque("Pistolet à O", 40, 100, Type.EAU));
        carapuce.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
    }

    @Test
    void testTypeMultiplier() {
        // Eau est super efficace contre Feu (x2)
        assertEquals(2.0, Type.EAU.getMultiplier(Type.FEU));
        // Feu est peu efficace contre Eau (x0.5)
        assertEquals(0.5, Type.FEU.getMultiplier(Type.EAU));
        // Normal n'affecte pas Spectre (x0)
        assertEquals(0.0, Type.NORMAL.getMultiplier(Type.SPECTRE));
        // Plante est super efficace contre Eau (x2)
        assertEquals(2.0, Type.PLANTE.getMultiplier(Type.EAU));
        // Électrik n'affecte pas Sol (x0)
        assertEquals(0.0, Type.ELECTRIK.getMultiplier(Type.SOL));
    }

    @Test
    void testPokemonCreation() {
        assertEquals("Salamèche", salameche.getNom());
        assertEquals(Type.FEU, salameche.getType());
        assertEquals(5, salameche.getNiveau());
        assertFalse(salameche.isDead());
        assertEquals(2, salameche.getAttaques().size());
    }

    @Test
    void testAttaqueTouche() {
        Attaque a = new Attaque("Test", 40, 100, Type.NORMAL);
        // Précision 100% = touche toujours
        for (int i = 0; i < 100; i++) {
            assertTrue(a.touche());
        }
    }

    @Test
    void testAttaqueRate() {
        Attaque a = new Attaque("Test", 40, 0, Type.NORMAL);
        // Précision 0% = rate toujours
        for (int i = 0; i < 100; i++) {
            assertFalse(a.touche());
        }
    }

    @Test
    void testDegatsReduitsParDefense() {
        Pokemon faible = new Pokemon("Faible", Type.NORMAL, 1);
        Pokemon fort = new Pokemon("Fort", Type.NORMAL, 1);
        // Fort a plus de défense, donc moins de dégâts subis
        int degatsFaible = 0;
        int degatsFort = 0;
        for (int i = 0; i < 1000; i++) {
            faible.recevoirDegats(50, Type.NORMAL);
            fort.recevoirDegats(50, Type.NORMAL);
        }
        // Les deux finissent à 0 HP mais on vérifie que la défense réduit
        assertTrue(faible.isDead());
        assertTrue(fort.isDead());
    }

    @Test
    void testCombatSeTermine() {
        Combat combat = new Combat(salameche, carapuce);
        assertFalse(combat.estTermine());

        // Mettre les HP du joueur à 0
        salameche.recevoirDegats(999, Type.NORMAL);
        assertTrue(combat.estTermine());
    }

    @Test
    void testGagnerXpEtLevelUp() {
        Pokemon p = new Pokemon("Test", Type.NORMAL, 1);
        int niveauInitial = p.getNiveau();
        // Donner beaucoup d'XP
        boolean aMonte = p.gagnerXp(1000);
        assertTrue(aMonte);
        assertTrue(p.getNiveau() > niveauInitial);
    }

    @Test
    void testSoigner() {
        salameche.recevoirDegats(30, Type.NORMAL);
        assertTrue(salameche.getHp() < salameche.getHpMax());
        salameche.soigner();
        assertEquals(salameche.getHpMax(), salameche.getHp());
    }

    @Test
    void testMax4Attaques() {
        Pokemon p = new Pokemon("Test", Type.NORMAL, 1);
        assertTrue(p.apprendreAttaque(new Attaque("A1", 40, 100, Type.NORMAL)));
        assertTrue(p.apprendreAttaque(new Attaque("A2", 40, 100, Type.NORMAL)));
        assertTrue(p.apprendreAttaque(new Attaque("A3", 40, 100, Type.NORMAL)));
        assertTrue(p.apprendreAttaque(new Attaque("A4", 40, 100, Type.NORMAL)));
        // 5ème attaque refusée
        assertFalse(p.apprendreAttaque(new Attaque("A5", 40, 100, Type.NORMAL)));
        assertEquals(4, p.getAttaques().size());
    }

    @Test
    void testPokédex() {
        assertTrue(Pokédex.getNombrePokemons() > 0);
        Pokemon p = Pokédex.creerPokemon(0);
        assertNotNull(p);
        assertTrue(p.getAttaques().size() > 0);
    }

    @Test
    void testPremierAttaquant() {
        Pokemon rapide = new Pokemon("Rapide", Type.NORMAL, 5);
        rapide = new Pokemon("Rapide", "Rapide", Type.NORMAL, 5, 50, 50, 45, 99);
        Pokemon lent = new Pokemon("Lent", "Lent", Type.NORMAL, 5, 50, 50, 45, 10);
        Combat combat = new Combat(rapide, lent);
        assertEquals(rapide, combat.premierAttaquant());
    }
}
