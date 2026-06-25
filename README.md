# Pokemon-Game

Un jeu de combat Pokémon en Java, en console, avec tour-par-tour.

## Fonctionnalités

- **16 Pokémon** de départ avec 18 types officiels
- **Matrice de types** complète (x2 super efficace, x0.5 peu efficace, x0 immunisé)
- **Système de combat** tour-par-tour avec :
  - Choix d'attaque parmi 4 maximum
  - Précision (certaines attaques peuvent échouer)
  - Défense qui réduit les dégâts
  - Vitesse qui détermine l'ordre d'attaque
- **Progression** : XP, montée de niveau, augmentation des stats
- **IA adverse** simple (choix aléatoire d'attaque)

## Prérequis

- Java 17+
- Gradle 8+ (wrapper inclus)

## Compiler et lancer

```bash
# Compiler
gradle build

# Lancer le jeu
gradle run

# Lancer les tests
gradle test
```

## Structure du projet

```
src/
├── main/java/pokemon/
│   ├── Type.java          # Enum des 18 types + matrice de faiblesses
│   ├── Attaque.java       # Classe attaque (nom, puissance, précision, type)
│   ├── Pokemon.java       # Classe Pokémon (stats, niveau, XP, attaques)
│   ├── Pokédex.java       # Registre des Pokémon disponibles
│   ├── Combat.java        # Moteur de combat tour-par-tour
│   └── Main.java          # Point d'entrée console
└── test/java/pokemon/
    └── CombatTest.java    # Tests unitaires JUnit 5
```

## Types supportés

Normal, Feu, Eau, Plante, Électrik, Glace, Combat, Poison, Sol, Vol, Psy, Insecte, Roche, Spectre, Dragon, Ténèbres, Acier, Fée

## Exemple de combat

```
╔══════════════════════════════════════════╗
║          ⚡ POKÉMON - JEU JAVA ⚡        ║
║         Combat tour par tour             ║
╚══════════════════════════════════════════╝

Choisissez votre Pokémon :

   1. Salamèche    [Feu] Niv.5 HP=55 ATK=50 DEF=45 VIT=45
   2. Carapuce     [Eau] Niv.5 HP=55 ATK=50 DEF=45 VIT=45
   3. Bulbizarre   [Plante] Niv.5 HP=55 ATK=50 DEF=45 VIT=45
   ...

Votre choix (1-16) : 1

Vous avez choisi Salamèche !

Votre adversaire : Carapuce Niv.5

Salamèche HP: 55/55  |  Carapuce HP: 55/55

Vos attaques :
  1. Flammèche [Feu] PUI=40 PRE=100%
  2. Griffe [Normal] PUI=40 PRE=100%

Choisissez une attaque (1-2) : 1

=== Tour 1 ===
Salamèche utilise Flammèche !
Ce n'est pas très efficace... (x0.5)
Carapuce perd 10 HP (45/55)
Carapuce utilise Pistolet à O !
C'est super efficace ! (x2)
Salamèche perd 20 HP (35/55)
```

## Licence

Projet éducatif — libre d'utilisation.
