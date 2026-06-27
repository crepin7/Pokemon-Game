#!/bin/bash
# Compiler et lancer le jeu Pokemon sans Gradle
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

OUT_DIR="out"
SRC_DIR="src/main/java"
TEST_SRC_DIR="src/test/java"
TEST_OUT_DIR="out-test"

# --- Compilation principale ---
CLASSPATH="$(find lib -name '*.jar' 2>/dev/null | tr '\n' ':')"
SOURCES="$(find $SRC_DIR -name '*.java')"

mkdir -p $OUT_DIR

needs_compile=false
if [ ! -f "$OUT_DIR/pokemon/Main.class" ]; then
    needs_compile=true
else
    newer="$(find $SRC_DIR -name '*.java' -newer $OUT_DIR/pokemon/Main.class 2>/dev/null | head -1)"
    if [ -n "$newer" ]; then
        needs_compile=true
    fi
fi

if $needs_compile; then
    echo "Compilation..."
    javac -cp "$CLASSPATH" -d $OUT_DIR $SOURCES
fi

# --- Compilation & exécution des tests (optionnel) ---
if [ -d "lib" ] && ls lib/*.jar >/dev/null 2>&1; then
    if [ -d "$TEST_SRC_DIR" ]; then
        echo "Compilation des tests..."
        mkdir -p $TEST_OUT_DIR
        javac -cp "$OUT_DIR:$(find lib -name '*.jar' | tr '\n' ':')" \
              -d $TEST_OUT_DIR $TEST_SRC_DIR/pokemon/*.java 2>/dev/null || true
    fi
fi

echo ""
java -cp $OUT_DIR pokemon.Main
