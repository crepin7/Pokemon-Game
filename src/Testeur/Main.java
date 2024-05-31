
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
package Testeur;
import pokemon.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stu
		Pokemon p = new Pokemon("mashle", 100, 20);
		PokemonEau pEau = new PokemonEau("katara", 100, 15);
		PokemonFeu pFeu = new PokemonFeu("zuko", 100, 8.5);
		PokemonPlante pPlante = new PokemonPlante("hashirama", 100, 30);
		
		Scanner scanner = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		while(true) {
			System.out.println("\t\t\t*************************************************");
			System.out.println("\t\t\t******************** MENU ***********************");
			System.out.println("\t\t\t*************************************************");
			System.out.println();
			System.out.println("\t\t\t 1- Voir les details des pokemons");
			System.out.println("\t\t\t 2- Simuler un combat");
			System.out.println("\t\t\t 3- Quitter");
			System.out.print("\t\t\t Votre choix: ");
			int choix = scanner.nextInt();
			switch(choix) {
				case 1:
					System.out.println();
					System.out.println();
					System.out.println("\t\t***************************************************************************");
					System.out.println("\t\t********************************* DETAILS *********************************");
					System.out.println("\t\t***************************************************************************");
					System.out.println();
					System.out.println();
					System.out.println("\t\t\t                 NOM\t\t   HP        \tATTAQUE\t");
					System.out.println("\t\t\t       ---------------------------------------------------");
					p.afficher();
					pEau.afficher();
					pFeu.afficher();
					pPlante.afficher();
					System.out.println();
					System.out.println();
					System.out.println();
					
					break;
				case 2:
                                        System.out.println();
                                        System.out.println("\t ************** SIMULATION DE COMBAT ***************");
					System.out.println();
					System.out.print("\t Saisir le nom du pokemon attaquant: ");
					String nomPokemon = sc1.nextLine();
                                                                                
					if(nomPokemon.toLowerCase().equals(p.getNom()) && !p.isDead()) {
						Pokemon pokemonChoisi = p;
                                                System.out.println();
                                                
                                                System.out.println("\t " + pokemonChoisi);
                                                System.out.println();
                                                
                                                
                                                System.out.print("\t Saisir le nom du pokemon a attaque: ");
                                                String nomPokemonAttaque = sc2.nextLine();
                                                
                                            if(nomPokemonAttaque.toLowerCase().equals(p.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
						Pokemon pokemonAttaque = p;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                pokemonChoisi.attaquer(pokemonAttaque);
                                                
                                                System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                System.out.println();
                                                System.out.println("\t Voir les details sur les pokemons...");
                                                System.out.println();
                                                System.out.println();
                                            } else if(nomPokemonAttaque.toLowerCase().equals(pFeu.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                    PokemonFeu pokemonAttaque = pFeu;
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    pokemonChoisi.attaquer(pokemonAttaque);
                                                    
                                                    System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    System.out.println();
                                                    System.out.println("\t Voir les details sur les pokemons...");
                                                    System.out.println();
                                                    System.out.println();
                                            } else if(nomPokemonAttaque.toLowerCase().equals(pEau.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                    PokemonEau pokemonAttaque= pEau;
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    pokemonChoisi.attaquer(pokemonAttaque);
                                                    
                                                    System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    System.out.println();
                                                    System.out.println("\t Voir les details sur les pokemons...");
                                                    System.out.println();
                                                    System.out.println();
                                            } else if(nomPokemonAttaque.toLowerCase().equals(pPlante.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                    PokemonPlante pokemonAttaque = pPlante;
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    pokemonChoisi.attaquer(pokemonAttaque);
                                                    
                                                    System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                    
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                    
                                                    System.out.println();
                                                    System.out.println("\t Voir les details sur les pokemons...");
                                                    System.out.println();
                                                    System.out.println();
                                            } else {
                                                    System.out.println();
                                                    System.out.println("\t NE PEUT PAS ATTAQUE CE POKEMON...");
                                                    System.out.println();
                                                    break;
                                            }
					} else if(nomPokemon.toLowerCase().equals(p.getNom()) && p.isDead()){
                                            System.out.println();
                                            System.out.println("\t" + p);
                                            System.out.println();
                                            System.out.println("\t " + p.getNom().toUpperCase() + " EST MORT DONC NE PEUT PAS ATTAQUE...");
                                            System.out.println();
                                        } else if(nomPokemon.toLowerCase().equals(pFeu.getNom()) && pFeu.isDead()){
                                            System.out.println();
                                            System.out.println("\t" + pFeu);
                                            System.out.println();
                                            System.out.println("\t " + pFeu.getNom().toUpperCase() + " EST MORT DONC NE PEUT PAS ATTAQUE...");
                                            System.out.println();
                                        } else if(nomPokemon.toLowerCase().equals(pFeu.getNom()) && !pFeu.isDead()) {
						PokemonFeu pokemonChoisi = pFeu;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonChoisi);
                                                System.out.println();
                                                
                                                System.out.print("\t Saisir le nom du pokemon a attaque: ");
                                                String nomPokemonAttaque = sc2.nextLine();
                                                
                                                if(nomPokemonAttaque.toLowerCase().equals(p.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
						Pokemon pokemonAttaque = p;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                pokemonChoisi.attaquer(pokemonAttaque);
                                                
                                                System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                System.out.println();
                                                System.out.println("\t Voir les details sur les pokemons...");
                                                System.out.println();
                                                System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pFeu.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonFeu pokemonAttaque = pFeu;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pEau.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonEau pokemonAttaque= pEau;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pPlante.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonPlante pokemonAttaque = pPlante;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else {
                                                        System.out.println();
                                                        System.out.println("\t NE PEUT PAS ATTAQUE CE POKEMON...");
                                                        System.out.println();
                                                        break;
                                                }
					} else if(nomPokemon.toLowerCase().equals(pEau.getNom()) && pEau.isDead()){
                                            System.out.println();
                                            System.out.println("\t" + pEau);
                                            System.out.println();
                                            System.out.println("\t " + pEau.getNom().toUpperCase() + " EST MORT DONC NE PEUT PAS ATTAQUE...");
                                            System.out.println();
                                        } else if(nomPokemon.toLowerCase().equals(pEau.getNom()) && !pEau.isDead()) {
						PokemonEau pokemonChoisi = pEau;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonChoisi);
                                                System.out.println();
                                                
                                                System.out.print("\t Saisir le nom du pokemon a attaque: ");
                                                String nomPokemonAttaque = sc2.nextLine();
                                                
                                                if(nomPokemonAttaque.toLowerCase().equals(p.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
						Pokemon pokemonAttaque = p;
                                                
                                                    System.out.println();
                                                    System.out.println("\t" + pokemonAttaque);
                                                    System.out.println();
                                                
                                                pokemonChoisi.attaquer(pokemonAttaque);
                                                
                                                System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                 
                                                System.out.println();
                                                System.out.println("\t Voir les details sur les pokemons...");
                                                System.out.println();
                                                System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pFeu.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonFeu pokemonAttaque = pFeu;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pEau.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonEau pokemonAttaque= pEau;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
0
                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pPlante.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonPlante pokemonAttaque = pPlante;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else {
                                                        System.out.println();
                                                        System.out.println("\t NE PEUT PAS ATTAQUE CE POKEMON...");
                                                        System.out.println();
                                                        break;
                                                }
					} else if(nomPokemon.toLowerCase().equals(pPlante.getNom()) && pPlante.isDead()){
                                            System.out.println();
                                            System.out.println("\t" + pPlante);
                                            System.out.println();
                                            System.out.println("\t " + pPlante.getNom().toUpperCase() + " EST MORT DONC NE PEUT PAS ATTAQUE...");
                                            System.out.println();
                                        } else if(nomPokemon.toLowerCase().equals(pPlante.getNom()) && !pPlante.isDead()) {
                                                PokemonPlante pokemonChoisi = pPlante;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonChoisi);
                                                System.out.println();
                                                
                                                System.out.print("\t Saisir le nom du pokemon a attaque: ");
                                                String nomPokemonAttaque = sc2.nextLine();
                                                
                                                if(nomPokemonAttaque.toLowerCase().equals(p.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
						Pokemon pokemonAttaque = p;
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                pokemonChoisi.attaquer(pokemonAttaque);
                                                
                                                System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                
                                                System.out.println();
                                                System.out.println("\t" + pokemonAttaque);
                                                System.out.println();
                                                
                                                System.out.println();
                                                System.out.println("\t Voir les details sur les pokemons...");
                                                System.out.println();
                                                System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pFeu.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonFeu pokemonAttaque = pFeu;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pEau.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonEau pokemonAttaque= pEau;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else if(nomPokemonAttaque.toLowerCase().equals(pPlante.getNom()) && !nomPokemonAttaque.toLowerCase().equals(nomPokemon)) {
                                                        PokemonPlante pokemonAttaque = pPlante;
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();

                                                        pokemonChoisi.attaquer(pokemonAttaque);
                                                        
                                                        System.out.println("\t ..." + pokemonChoisi.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase());
                                                        
                                                        System.out.println();
                                                        System.out.println("\t" + pokemonAttaque);
                                                        System.out.println();
                                                        
                                                        
                                                        System.out.println();
                                                        System.out.println("\t Voir les details sur les pokemons...");
                                                        System.out.println();
                                                        System.out.println();
                                                } else {
                                                        System.out.println();
                                                        System.out.println("\t NE PEUT PAS ATTAQUE CE POKEMON...");
                                                        System.out.println();
                                                        break;
                                                }
					} else {
						System.out.println("\t LE POKEMON CHOISI POUR ATTAQUE NE FIGURE PAS DANS LA LISTE...");
						System.out.println();
						break;
					}
                                        
					break;
				case 3:
					return;
				default:
					System.out.println("\t Choix invalide");
					System.out.println();
			}
			
		}
	}
}
*/

package Testeur;
import pokemon.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Coutso
 */
public class Main {
    
        public static List<Pokemon> pokemons = new ArrayList<>();;
        
        public static void initialiser(){
            pokemons.add(new Pokemon("mashle", 100, 20));
            pokemons.add(new PokemonEau("katara", 100, 15));
            pokemons.add(new PokemonFeu("zuko", 100, 8.5));
            pokemons.add(new PokemonPlante("hashirama", 100, 30));
        }
        
        public static Pokemon trouver(String nom){
            for(Pokemon pokemon : pokemons){
                if(pokemon.getNom().equals(nom)){
                    return pokemon;
                }
            }
            return null;
        }

	public static void main(String[] args) {
            
                initialiser();
                
		Scanner scanner = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
                
		while(true) {
			System.out.println("\t\t\t*************************************************");
			System.out.println("\t\t\t******************** MENU ***********************");
			System.out.println("\t\t\t*************************************************");
			System.out.println();
			System.out.println("\t\t\t 1- Voir les details des pokemons");
			System.out.println("\t\t\t 2- Simuler un combat");
			System.out.println("\t\t\t 3- Quitter");
			System.out.print("\t\t\t Votre choix: ");
			int choix = scanner.nextInt();
			switch(choix) {
				case 1:
					System.out.println();
					System.out.println();
					System.out.println("\t\t***************************************************************************");
					System.out.println("\t\t********************************* DETAILS *********************************");
					System.out.println("\t\t***************************************************************************");
					System.out.println();
					System.out.println();
					System.out.println("\t\t\t                 NOM\t\t   HP        \tATTAQUE\t\tTYPE");
					System.out.println("\t\t\t       ----------------------------------------------------------------");
					for(Pokemon pokemon : pokemons){
                                            pokemon.afficher();
                                        }
					System.out.println();
					System.out.println();
					System.out.println();
					
					break;
				case 2:
                                        System.out.println();
                                        System.out.println("\t ************** SIMULATION DE COMBAT ***************");
					System.out.println();
					System.out.print("\t Saisir le nom du pokemon attaquant: ");
					String nomPokemon = sc1.nextLine();
                                                                                
                                        Pokemon pokemonAttaquant = trouver(nomPokemon);
                                        
                                        if(pokemonAttaquant == null){
                                            System.out.println();
                                            System.out.println("\t POKEMON ATTAQUANT NON TROUVE...");
                                            break;
                                        } else {
                                            System.out.println();
                                            System.out.println("\t " + pokemonAttaquant);
                                            System.out.println();
                                            System.out.print("\t Saisir le nom du pokemon a attaque: ");
                                            String nomPokemonAttaque = sc1.nextLine();
                                            
                                            Pokemon pokemonAttaque = trouver(nomPokemonAttaque);
                                            
                                            if(pokemonAttaque == null){
                                                System.out.println();
                                                System.out.println("\t POKEMON ATTAQUE NON TROUVE...");
                                                break;
                                            }
                                            
                                            if(pokemonAttaque == pokemonAttaquant){
                                                System.out.println();
                                                System.out.println("\t " + pokemonAttaque.getNom().toUpperCase() + " NE PEUT PAS S'ATTAQUE LUI-MEME...");
                                            } else {
                                                System.out.println();
                                                
                                                System.out.println("\t " + pokemonAttaque);
                                                
                                                System.out.println();
                                                
                                                System.out.println("\t " + pokemonAttaquant.getNom().toUpperCase() + " a attaque " + pokemonAttaque.getNom().toUpperCase() + "...");
                                                
                                                System.out.println();
                                                
                                                pokemonAttaquant.attaquer(pokemonAttaque);
                                                                                                
                                                System.out.println("\t " + pokemonAttaque);
                                                
                                                System.out.println();
                                            }
                                        }
					break;
				case 3:
					return;
				default:
					System.out.println("\t Choix invalide");
					System.out.println();
			}
			
		}
	}
}
