# Pokemon-Game

Un jeu de combat Pokémon en Java, en console, avec tour-par-tour.

## Fonctionnalités

- **16 Pokémon** de départ avec 18 types officiels
- **Double-type** supporté (x4 super efficace, résistance combinée)
- **Matrice de types** complète (x2 super efficace, x0.5 peu efficace, x0 immunisé)
- **Système de combat** tour-par-tour avec :
  - Choix d'attaque parmi 4 maximum
  - Précision (certaines attaques peuvent échouer)
  - Défense qui réduit les dégâts
  - Vitesse qui détermine l'ordre d'attaque
- **Progression** : XP, montée de niveau, augmentation des stats
- **IA adverse** intelligente : maximise les dégâts selon le type adverse

## Prérequis

Java 17+ (aucun autre outil nécessaire).

## Compiler et lancer

```bash
# Méthode 1 — script fourni (recommandé)
./run.sh

# Méthode 2 — manuellement
mkdir -p out
javac -d out src/main/java/pokemon/*.java src/main/java/pokemon/ui/*.java
java -cp out pokemon.Main
```

## Lancer les tests (optionnel)

Les tests utilisent JUnit 5. Téléchargez le standalone jar sur
[junit.org](https://junit.org/junit5/downloads/) et placez-le dans `lib/` :

```bash
mkdir -p lib
curl -sL -o lib/junit-platform-console-standalone-1.10.2.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar

# Compiler les tests
javac -cp "out:lib/*" -d out-test src/test/java/pokemon/*.java

# Lancer les tests
java -jar lib/junit-platform-console-standalone-1.10.2.jar \
  --class-path out:out-test \
  --scan-class-path out-test
```

## Structure du projet

```
src/
├── main/java/pokemon/
│   ├── Type.java          # Enum des 18 types + matrice de faiblesses
│   ├── Attaque.java       # Classe attaque (nom, puissance, précision, type)
│   ├── Pokemon.java       # Classe Pokémon (stats, niveau, XP, double-type)
│   ├── Pokédex.java       # Registre des Pokémon disponibles
│   ├── Combat.java        # Moteur de combat tour-par-tour (pur, sans I/O)
│   ├── CombatEvent.java   # Événements structurés (Attaque, KO, Victoire, XP)
│   ├── TourResult.java        # Résultat structuré d'un tour
│   ├── Main.java              # Point d'entrée console
│   └── ui/
│       ├── ConsoleRenderer.java       # Interface d'affichage
│       └── DefaultConsoleRenderer.java # Implémentation console
└── test/java/pokemon/
    └── CombatTest.java    # Tests unitaires JUnit 5
```

## Types supportés

Normal, Feu, Eau, Plante, Électrik, Glace, Combat, Poison, Sol, Vol, Psy, Insecte, Roche, Spectre, Dragon, Ténèbres, Acier, Fée

## Licence

Projet éducatif — libre d'utilisation.
