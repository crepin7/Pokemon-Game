package pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Pokédex contenant tous les Pokémon disponibles avec leurs attaques de base.
 */
public class Pokédex {

    private static final List<Pokemon> pokemons = new ArrayList<>();

    static {
        // --- Pokémon de départ ---

        // Feu
        Pokemon salameche = new Pokemon("Salamèche", Type.FEU, 5);
        salameche.apprendreAttaque(new Attaque("Griffe", 40, 100, Type.NORMAL));
        salameche.apprendreAttaque(new Attaque("Flammèche", 40, 100, Type.FEU));
        pokemons.add(salameche);

        // Eau
        Pokemon carapuce = new Pokemon("Carapuce", Type.EAU, 5);
        carapuce.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        carapuce.apprendreAttaque(new Attaque("Pistolet à O", 40, 100, Type.EAU));
        pokemons.add(carapuce);

        // Plante
        Pokemon bulbizarre = new Pokemon("Bulbizarre", Type.PLANTE, 5);
        bulbizarre.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        bulbizarre.apprendreAttaque(new Attaque("Fouet Lianes", 45, 100, Type.PLANTE));
        pokemons.add(bulbizarre);

        // Électrik
        Pokemon pikachu = new Pokemon("Pikachu", Type.ELECTRIK, 5);
        pikachu.apprendreAttaque(new Attaque("Éclair", 40, 100, Type.ELECTRIK));
        pikachu.apprendreAttaque(new Attaque("Vive-Attaque", 40, 100, Type.NORMAL));
        pokemons.add(pikachu);

        // Psy
        Pokemon psykokwak = new Psykokwak();
        pokemons.add(psykokwak);

        // Combat
        Pokemon caninos = new Pokemon("Caninos", Type.FEU, 5);
        caninos.apprendreAttaque(new Attaque("Morsure", 60, 100, Type.TENEBRES));
        caninos.apprendreAttaque(new Attaque("Flammèche", 40, 100, Type.FEU));
        pokemons.add(caninos);

        // Spectre
        Pokemon fantominus = new Fantominus();
        pokemons.add(fantominus);

        // Dragon
        Pokemon minidraco = new Pokemon("Minidraco", Type.DRAGON, 5);
        minidraco.apprendreAttaque(new Attaque("Draco-Souffle", 60, 100, Type.DRAGON));
        minidraco.apprendreAttaque(new Attaque("Griffe", 40, 100, Type.NORMAL));
        pokemons.add(minidraco);

        // Glace
        Pokemon stalgamin = new Stalgamin();
        pokemons.add(stalgamin);

        // Normal
        Pokemon rattata = new Pokemon("Rattata", Type.NORMAL, 3);
        rattata.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        rattata.apprendreAttaque(new Attaque("Morsure", 60, 100, Type.TENEBRES));
        pokemons.add(rattata);

        // Poison
        Pokemon smogo = new Pokemon("Smogo", Type.POISON, 5);
        smogo.apprendreAttaque(new Attaque("Dard-Venin", 35, 100, Type.POISON));
        smogo.apprendreAttaque(new Attaque("Gaz Toxik", 40, 90, Type.POISON));
        pokemons.add(smogo);

        // Vol
        Pokemon roucool = new Pokemon("Roucool", Type.VOL, 4);
        roucool.apprendreAttaque(new Attaque("Tornade", 40, 100, Type.VOL));
        roucool.apprendreAttaque(new Attaque("Vive-Attaque", 40, 100, Type.NORMAL));
        pokemons.add(roucool);

        // Insecte
        Pokemon chenipan = new Pokemon("Chenipan", Type.INSECTE, 3);
        chenipan.apprendreAttaque(new Attaque("Sécrétion", 40, 95, Type.INSECTE));
        chenipan.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        pokemons.add(chenipan);

        // Sol
        Pokemon taupiqueur = new Pokemon("Taupiqueur", Type.SOL, 4);
        taupiqueur.apprendreAttaque(new Attaque("Piège de Roc", 50, 80, Type.SOL));
        taupiqueur.apprendreAttaque(new Attaque("Griffe", 40, 100, Type.NORMAL));
        pokemons.add(taupiqueur);

        // Roche
        Pokemon racaillou = new Pokemon("Racaillou", Type.ROCHE, 5);
        racaillou.apprendreAttaque(new Attaque("Jet-Pierres", 50, 90, Type.ROCHE));
        racaillou.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        pokemons.add(racaillou);

        // Fée
        Pokemon melofee = new Pokemon("Mélofée", Type.FEE, 5);
        melofee.apprendreAttaque(new Attaque("Écras'Face", 40, 100, Type.NORMAL));
        melofee.apprendreAttaque(new Attaque("Voix Enjôleuse", 40, 100, Type.FEE));
        pokemons.add(melofee);

        // Acier
        Pokemon magneti = new Pokemon("Magnéti", Type.ACIER, 5);
        magneti.apprendreAttaque(new Attaque("Éclair", 40, 100, Type.ELECTRIK));
        magneti.apprendreAttaque(new Attaque("Tête de Fer", 60, 80, Type.ACIER));
        pokemons.add(magneti);

        // Ténèbres
        Pokemon tenebras = new Pokemon("Ténébras", Type.TENEBRES, 5);
        tenebras.apprendreAttaque(new Attaque("Morsure", 60, 100, Type.TENEBRES));
        tenebras.apprendreAttaque(new Attaque("Griffe Ombre", 70, 100, Type.SPECTRE));
        pokemons.add(tenebras);
    }

    public static List<Pokemon> getTous() {
        return new ArrayList<>(pokemons);
    }

    public static Pokemon creerPokemon(int index) {
        if (index < 0 || index >= pokemons.size()) {
            throw new IndexOutOfBoundsException("Pokémon #" + index + " n'existe pas.");
        }
        Pokemon original = pokemons.get(index);
        Pokemon copie = new Pokemon(original.getEspece(), original.getEspece(), original.getType(), original.getNiveau(),
                original.getHpMax(), original.getAttaque(), original.getDefense(), original.getVitesse());
        for (Attaque a : original.getAttaques()) {
            copie.apprendreAttaque(new Attaque(a.getNom(), a.getPuissance(), a.getPrecision(), a.getType(), a.getRatioAtk()));
        }
        return copie;
    }

    public static int getNombrePokemons() {
        return pokemons.size();
    }

    /**
     * Classe interne pour Pokémon avec attaques spéciales.
     */
    private static class Psykokwak extends Pokemon {
        Psykokwak() {
            super("Psykokwak", Type.PSY, 5);
            this.apprendreAttaque(new Attaque("Choc Mental", 50, 100, Type.PSY));
            this.apprendreAttaque(new Attaque("Pistolet à O", 40, 100, Type.EAU));
        }
    }

    private static class Fantominus extends Pokemon {
        Fantominus() {
            super("Fantominus", Type.SPECTRE, 5);
            this.apprendreAttaque(new Attaque("Ombre Portée", 60, 100, Type.SPECTRE));
            this.apprendreAttaque(new Attaque("Vibra Soin", 40, 100, Type.PSY));
        }
    }

    private static class Stalgamin extends Pokemon {
        Stalgamin() {
            super("Stalgamin", Type.GLACE, 5);
            this.apprendreAttaque(new Attaque("Laser Glace", 55, 95, Type.GLACE));
            this.apprendreAttaque(new Attaque("Charge", 40, 100, Type.NORMAL));
        }
    }
}
