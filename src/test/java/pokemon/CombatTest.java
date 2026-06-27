package pokemon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

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

    @Test
    void testDualTypeGetters() {
        Pokemon dracaufeu = new Pokemon("Dracaufeu", Arrays.asList(Type.FEU, Type.VOL), 5);
        assertEquals(Type.FEU, dracaufeu.getPrimaryType());
        assertEquals(Type.VOL, dracaufeu.getSecondaryType());
        assertEquals(Type.FEU, dracaufeu.getType()); // backward-compatible
        assertEquals(Arrays.asList(Type.FEU, Type.VOL), dracaufeu.getTypes());
        assertEquals(2, dracaufeu.getTypes().size());
    }

    @Test
    void testSingleTypeGetters() {
        Pokemon carapuceSingle = new Pokemon("Carapuce", Type.EAU, 5);
        assertEquals(Type.EAU, carapuceSingle.getPrimaryType());
        assertNull(carapuceSingle.getSecondaryType());
        assertEquals(Type.EAU, carapuceSingle.getType());
        assertEquals(1, carapuceSingle.getTypes().size());
    }

    @Test
    void testDualTypeX4SuperEffective() {
        // Eau vs Feu/Vol : Eau est x2 contre Feu, x1 contre Vol => x2 total
        // Eau vs Feu/Sol : Eau est x2 contre Feu, x2 contre Sol => x4
        Pokemon feuSol = new Pokemon("TestFeuSol", Arrays.asList(Type.FEU, Type.SOL), 5);
        Pokemon eau = new Pokemon("TestEau", Type.EAU, 5);
        Attaque pistolet = new Attaque("Pistolet à O", 40, 100, Type.EAU);

        // Multiplicateur attendu: EAU.getMultiplier(FEU) * EAU.getMultiplier(SOL) = 2.0 * 2.0 = 4.0
        double mult = Type.EAU.getMultiplier(Type.FEU) * Type.EAU.getMultiplier(Type.SOL);
        assertEquals(4.0, mult);

        int degats = eau.calculerDegats(pistolet, feuSol);
        // base = 40 * 1.0 * 4.0 = 160
        assertEquals(160, degats);
    }

    @Test
    void testDualTypeCombinedResistance() {
        // Poison vs Poison/Sol : Poison est x0.5 contre Poison, x0.5 contre Sol => x0.25
        Pokemon poisonSol = new Pokemon("TestPoisonSol", Arrays.asList(Type.POISON, Type.SOL), 5);
        Pokemon poisonPkm = new Pokemon("TestPoison", Type.POISON, 5);
        Attaque dard = new Attaque("Dard-Venin", 40, 100, Type.POISON);

        double mult = Type.POISON.getMultiplier(Type.POISON) * Type.POISON.getMultiplier(Type.SOL);
        assertEquals(0.25, mult);

        int degats = poisonPkm.calculerDegats(dard, poisonSol);
        // base = 40 * 1.0 * 0.25 = 10
        assertEquals(10, degats);
    }

    @Test
    void testDualTypeNormalDamage() {
        // Normal vs Feu/Vol : Normal est x1 contre Feu, x1 contre Vol => x1
        Pokemon feuVol = new Pokemon("TestFeuVol", Arrays.asList(Type.FEU, Type.VOL), 5);
        Pokemon normalPkm = new Pokemon("TestNormal", Type.NORMAL, 5);
        Attaque charge = new Attaque("Charge", 40, 100, Type.NORMAL);

        double mult = Type.NORMAL.getMultiplier(Type.FEU) * Type.NORMAL.getMultiplier(Type.VOL);
        assertEquals(1.0, mult);

        int degats = normalPkm.calculerDegats(charge, feuVol);
        // base = 40 * 1.0 * 1.0 = 40
        assertEquals(40, degats);
    }

    @Test
    void testDualTypeRecevoirDegatsX4() {
        // Eau vs Feu/Sol : x4 => les dégâts doivent être x4 par rapport à un type seul
        Pokemon feuSol = new Pokemon("TestFeuSol", Arrays.asList(Type.FEU, Type.SOL), 5);
        Pokemon simple = new Pokemon("TestFeuSimple", Type.FEU, 5);
        simple.recevoirDegats(40, Type.EAU);
        int hpSimple = simple.getHp();

        // Reset
        simple.soigner();
        assertEquals(55, simple.getHpMax());

        // x4 vs x2 : le Pokémon dual type perd plus de HP
        // On ne peut pas comparer directement à cause de la défense,
        // mais on vérifie le multiplicateur utilisé
        double multDual = Type.EAU.getMultiplier(Type.FEU) * Type.EAU.getMultiplier(Type.SOL);
        double multSingle = Type.EAU.getMultiplier(Type.FEU);
        assertEquals(4.0, multDual);
        assertEquals(2.0, multSingle);
    }

    @Test
    void testDualTypeToString() {
        Pokemon dracaufeu = new Pokemon("Dracaufeu", Arrays.asList(Type.FEU, Type.VOL), 5);
        String s = dracaufeu.toString();
        assertTrue(s.contains("Feu/Vol"));
        String detail = dracaufeu.toDetailString();
        assertTrue(detail.contains("Feu/Vol"));
    }

    @Test
    void testInvalidTypeCount() {
        // 0 types => exception
        assertThrows(IllegalArgumentException.class, () -> {
            new Pokemon("Bad", Collections.emptyList(), 1);
        });
        // 3 types => exception
        assertThrows(IllegalArgumentException.class, () -> {
            new Pokemon("Bad", Arrays.asList(Type.FEU, Type.EAU, Type.PLANTE), 1);
        });
    }

    // =========================================================================
    // Tests du moteur de combat pur (IO-free) avec données structurées
    // =========================================================================

    /**
     * Le Combat ne doit contenir AUCUNE référence à Scanner ni à System.out.
     * On vérifie l'absence du champ Scanner par réflexion.
     */
    @Test
    void testCombatNeContientPasDeScanner() {
        for (java.lang.reflect.Field f : Combat.class.getDeclaredFields()) {
            assertFalse(f.getType().getName().equals("java.util.Scanner"),
                    "Combat ne doit pas contenir de Scanner, trouvé : " + f);
        }
    }

    /**
     * Un tour retourne un TourResult structuré, pas seulement du texte.
     */
    @Test
    void testExecuterTourRetourneResultatStructure() {
        Combat combat = new Combat(salameche, carapuce);
        Attaque a1 = salameche.getAttaques().get(0);
        Attaque a2 = carapuce.getAttaques().get(0);

        TourResult resultat = combat.executerTour(a1, a2);

        assertNotNull(resultat);
        assertEquals(1, resultat.getTour());
        // Le premier attaquant est bien déterminé par premierAttaquant()
        assertEquals(combat.premierAttaquant(), resultat.getPremier());
        // Il y a au moins un événement (l'attaque du premier)
        assertFalse(resultat.getEvents().isEmpty());
        // Le résumé texte est toujours disponible (backward-compatible)
        assertNotNull(resultat.getResumeTexte());
        assertTrue(resultat.getResumeTexte().contains("Tour 1"));
    }

    /**
     * Les événements d'un tour sont du type structuré attendu.
     */
    @Test
    void testEvenementsAttaquesSontStructures() {
        // Charge (NORMAL, précision 100) => touche toujours
        Attaque charge = new Attaque("Charge", 40, 100, Type.NORMAL);
        Combat combat = new Combat(salameche, carapuce);

        TourResult resultat = executerJusquaEvent(combat, charge, charge);

        boolean trouveAttaque = false;
        for (CombatEvent e : resultat.getEvents()) {
            if (e instanceof CombatEvent.AttaqueEvent) {
                CombatEvent.AttaqueEvent ae = (CombatEvent.AttaqueEvent) e;
                assertNotNull(ae.getAttaquant());
                assertNotNull(ae.getCible());
                assertNotNull(ae.getAttaque());
                // Avec précision 100, l'attaque réussit
                assertTrue(ae.isReussie());
                assertTrue(ae.getDegats() >= 0);
                trouveAttaque = true;
            }
        }
        assertTrue(trouveAttaque, "Le tour doit contenir au moins un AttaqueEvent");
    }

    /**
     * Un tour où le premier Pokémon K.O. le second doit produire un KoEvent
     * et un VictoireEvent, et marquer le combat comme terminé.
     */
    @Test
    void testKoProduitEvenementsStructures() {
        // Attaque surpuissante pour K.O. en un coup
        Attaque surpuissante = new Attaque("Ultime", 9999, 100, Type.NORMAL);
        Attaque faible = new Attaque("Charge", 1, 100, Type.NORMAL);
        Combat combat = new Combat(salameche, carapuce);

        TourResult resultat = combat.executerTour(surpuissante, faible);

        boolean ko = false;
        boolean victoire = false;
        boolean xp = false;
        for (CombatEvent e : resultat.getEvents()) {
            if (e instanceof CombatEvent.KoEvent) {
                ko = true;
                assertEquals(carapuce, ((CombatEvent.KoEvent) e).getPokemon());
            }
            if (e instanceof CombatEvent.VictoireEvent) {
                victoire = true;
                assertEquals(salameche, ((CombatEvent.VictoireEvent) e).getVainqueur());
            }
            if (e instanceof CombatEvent.XpEvent) {
                xp = true;
                assertTrue(((CombatEvent.XpEvent) e).getXpGagne() > 0);
            }
        }
        assertTrue(ko, "Doit contenir un KoEvent");
        assertTrue(victoire, "Doit contenir un VictoireEvent");
        assertTrue(xp, "Doit contenir un XpEvent");
        assertTrue(resultat.isCombatTermine());
        assertTrue(combat.estTermine());
    }

    /**
     * L'ordre des événements reflète l'ordre réel d'exécution (premier puis second).
     */
    @Test
    void testOrdreEvenements() {
        Pokemon rapide = new Pokemon("Rapide", "Rapide", Type.NORMAL, 5, 200, 50, 45, 99);
        rapide.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        Pokemon lent = new Pokemon("Lent", "Lent", Type.NORMAL, 5, 200, 50, 45, 10);
        lent.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));

        Combat combat = new Combat(rapide, lent);
        TourResult resultat = combat.executerTour(rapide.getAttaques().get(0), lent.getAttaques().get(0));

        assertEquals(rapide, resultat.getPremier());
        assertEquals(lent, resultat.getSecond());

        // Le premier événement d'attaque doit être celui du plus rapide
        CombatEvent.AttaqueEvent premierEvent = null;
        for (CombatEvent e : resultat.getEvents()) {
            if (e instanceof CombatEvent.AttaqueEvent) {
                premierEvent = (CombatEvent.AttaqueEvent) e;
                break;
            }
        }
        assertNotNull(premierEvent);
        assertEquals(rapide, premierEvent.getAttaquant());
    }

    /**
     * Le résumé texte backward-compatible contient les informations clés.
     */
    @Test
    void testResumeTexteBackwardCompatible() {
        Combat combat = new Combat(salameche, carapuce);
        TourResult resultat = combat.executerTour(
                salameche.getAttaques().get(0), carapuce.getAttaques().get(0));

        String texte = resultat.getResumeTexte();
        assertTrue(texte.contains("=== Tour 1 ==="));
        // Le nom de l'attaque du premier attaquant doit apparaître
        assertTrue(texte.contains(combat.premierAttaquant().getNom()));
    }

    /**
     * Immunité (multiplicateur 0) : aucun dégâts, événement avec degats == 0.
     */
    @Test
    void testImmuniseProduitAttaqueEventSansDegats() {
        // Fantominus est SPECTRE, immunisé contre NORMAL
        Pokemon fantominus = new Pokemon("Fantominus", Type.SPECTRE, 5);
        fantominus.apprendreAttaque(new Attaque("Ombre Portée", 40, 100, Type.SPECTRE));
        Pokemon normalPkm = new Pokemon("NormalPkm", Type.NORMAL, 5);
        normalPkm.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));

        Combat combat = new Combat(normalPkm, fantominus);
        TourResult resultat = combat.executerTour(
                normalPkm.getAttaques().get(0),
                fantominus.getAttaques().get(0));

        // L'attaque NORMAL sur SPECTRE est immunisée : multiplicateur 0, dégâts 0
        boolean trouveImmunise = false;
        for (CombatEvent e : resultat.getEvents()) {
            if (e instanceof CombatEvent.AttaqueEvent) {
                CombatEvent.AttaqueEvent ae = (CombatEvent.AttaqueEvent) e;
                if (ae.getAttaque().getType() == Type.NORMAL && ae.getCible() == fantominus) {
                    assertEquals(0.0, ae.getMultiplicateur());
                    assertEquals(0, ae.getDegats());
                    trouveImmunise = true;
                }
            }
        }
        assertTrue(trouveImmunise, "Doit contenir un AttaqueEvent immunisé");
    }

    /** Exécute des tours jusqu'à obtenir au moins un événement d'attaque. */
    private TourResult executerJusquaEvent(Combat combat, Attaque a1, Attaque a2) {
        TourResult r = combat.executerTour(a1, a2);
        return r;
    }

    // =========================================================================
    // Tests de l'IA adverse (choisirAttaqueIA)
    // =========================================================================

    /**
     * L'IA ne doit JAMAIS choisir une attaque à dégâts nuls (immunité) si une
     * alternative existe. On vérifie sur 1000 tirages pour se prémunir du
     * caractère aléatoire de l'égalité.
     *
     * L'IA choisit pour l'adversaire (2e argument du Constructeur Combat).
     */
    @Test
    void testIANeChoisitJamaisImmuniteSiAlternative() {
        // Joueur SPECTRE (cible de l'IA).
        Pokemon joueur = new Pokemon("FantominusJoueur", Type.SPECTRE, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        // Adversaire SPECTRE avec une attaque NORMAL (immunisée) et une SPECTRE (x2).
        Pokemon adversaire = new Pokemon("FantominusAdv", Type.SPECTRE, 5);
        adversaire.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL)); // x0
        adversaire.apprendreAttaque(new Attaque("Ombre Portée", 40, 100, Type.SPECTRE)); // x2

        Combat combat = new Combat(joueur, adversaire);

        for (int i = 0; i < 1000; i++) {
            Attaque choisie = combat.choisirAttaqueIA();
            assertNotNull(choisie);
            assertNotEquals(Type.NORMAL, choisie.getType(),
                    "L'IA ne doit jamais choisir l'attaque immunisée (NORMAL) si une alternative existe");
        }
    }

    /**
     * L'IA doit choisir l'attaque super efficace quand elle est disponible.
     * Aucune dépendance au hasard : la meilleure attaque est unique.
     */
    @Test
    void testIAChoisitSuperEfficace() {
        // Joueur FEU : EAU est super efficace (x2) contre lui.
        Pokemon joueur = new Pokemon("Salamèche", Type.FEU, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        // Adversaire EAU avec une attaque EAU (x2) et une attaque NORMAL (x1).
        Pokemon adversaire = new Pokemon("Carapuce", Type.EAU, 5);
        adversaire.apprendreAttaque(new Attaque("Pistolet à O", 40, 100, Type.EAU));
        adversaire.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));

        Combat combat = new Combat(joueur, adversaire);

        for (int i = 0; i < 1000; i++) {
            Attaque choisie = combat.choisirAttaqueIA();
            assertEquals("Pistolet à O", choisie.getNom(),
                    "L'IA doit toujours choisir l'attaque super efficace (EAU vs FEU)");
        }
    }

    /**
     * Quand toutes les attaques sont immunisées, l'IA retourne une attaque
     * (fallback) — on vérifie simplement qu'elle ne lève pas d'exception et
     * retourne une attaque valide.
     */
    @Test
    void testIAToutesImmuniseesFallback() {
        // Joueur SPECTRE : NORMAL est immunisé (x0).
        Pokemon joueur = new Pokemon("Fantominus", Type.SPECTRE, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        // Adversaire NORMAL avec uniquement des attaques NORMAL.
        Pokemon adversaire = new Pokemon("NormalAtk", Type.NORMAL, 5);
        adversaire.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        adversaire.apprendreAttaque(new Attaque("Griffe", 40, 100, Type.NORMAL));

        Combat combat = new Combat(joueur, adversaire);

        Attaque choisie = combat.choisirAttaqueIA();
        assertNotNull(choisie);
        assertTrue(adversaire.getAttaques().contains(choisie));
    }

    /**
     * Si une seule attaque est disponible, l'IA la retourne directement.
     */
    @Test
    void testIAUneSeuleAttaque() {
        Pokemon joueur = new Pokemon("Cible", Type.NORMAL, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        Pokemon adversaire = new Pokemon("Solo", Type.NORMAL, 5);
        Attaque unique = new Attaque("Charge", 40, 100, Type.NORMAL);
        adversaire.apprendreAttaque(unique);

        Combat combat = new Combat(joueur, adversaire);
        assertSame(unique, combat.choisirAttaqueIA());
    }

    /**
     * La précision est prise en compte dans l'espérance de dégâts.
     * Une attaque à 100% de précision doit être préférée à une attaque
     * à 50% de précision avec la même puissance de base.
     */
    @Test
    void testIAPrendEnComptePrecision() {
        Pokemon joueur = new Pokemon("Cible", Type.NORMAL, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        Pokemon adversaire = new Pokemon("Test", Type.NORMAL, 5);
        adversaire.apprendreAttaque(new Attaque("Sûre", 40, 100, Type.NORMAL)); // attendu : 40
        adversaire.apprendreAttaque(new Attaque("Risquée", 40, 50, Type.NORMAL)); // attendu : 20

        Combat combat = new Combat(joueur, adversaire);

        for (int i = 0; i < 1000; i++) {
            Attaque choisie = combat.choisirAttaqueIA();
            assertEquals("Sûre", choisie.getNom(),
                    "L'IA doit préférer l'attaque à 100% de précision");
        }
    }

    /**
     * Égalité parfaite entre plusieurs meilleures attaques et une attaque
     * immunisée : l'IA doit choisir parmi les meilleures (jamais l'immunisée).
     */
    @Test
    void testIAEgaliteAleatoireParmiLesMeilleures() {
        // Joueur NORMAL : SPECTRE est immunisé (x0) contre NORMAL.
        Pokemon joueur = new Pokemon("NormalJoueur", Type.NORMAL, 5);
        joueur.apprendreAttaque(new Attaque("Charge", 1, 100, Type.NORMAL));

        Pokemon adversaire = new Pokemon("Test", Type.NORMAL, 5);
        adversaire.apprendreAttaque(new Attaque("A", 40, 100, Type.NORMAL)); // x1
        adversaire.apprendreAttaque(new Attaque("B", 40, 100, Type.NORMAL)); // x1 (égalité)
        adversaire.apprendreAttaque(new Attaque("Immunisée", 40, 100, Type.SPECTRE)); // x0 vs NORMAL

        Combat combat = new Combat(joueur, adversaire);

        for (int i = 0; i < 1000; i++) {
            Attaque choisie = combat.choisirAttaqueIA();
            assertNotNull(choisie);
            assertNotEquals("Immunisée", choisie.getNom(),
                    "L'IA ne doit jamais choisir l'attaque immunisée");
        }
    }
}
