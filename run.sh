#!/bin/bash
# Script de compilation et lancement du jeu Pokemon
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

# Compiler si nécessaire
if [ ! -d "out" ] || [ "$(find src -name '*.java' -newer out/pokemon/Main.class 2>/dev/null | wc -l)" -gt 0 ]; then
    echo "Compilation..."
    mkdir -p out
    javac -d out src/main/java/pokemon/*.java
fi

# Lancer le jeu
java -cp out pokemon.Main
