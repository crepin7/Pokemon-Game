/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author Coutso
 */
public class PokemonFeu extends Pokemon {
	public PokemonFeu(String nom, double hp, double atk) {
		super(nom, hp, atk);
	}
        
	public void attaquer(PokemonPlante p) {
            if(p.hp >= 2*this.atk){
                p.hp -= 2*this.atk;
            } else {
                p.hp = 0;
            }
	}
        
	public void attaquer(PokemonFeu p) {
            if(p.hp >= 0.5*this.atk){
                p.hp -= 0.5*this.atk;
            } else {
                p.hp = 0;
            }
	}
        
	public void attaquer(PokemonEau p) {
            if(p.hp >= 0.5*this.atk){
                p.hp -= 0.5*this.atk;
            } else {
                p.hp = 0;
            }
	}
        
        @Override
        public void attaquer(Pokemon p){
            if(p instanceof PokemonEau){
                attaquer((PokemonEau) p);
            } else if(p instanceof PokemonFeu){
                attaquer((PokemonFeu) p);
            } else if(p instanceof PokemonPlante){
                attaquer((PokemonPlante) p);
            } else {
                super.attaquer(p);
            }
        }
        
        @Override
        public void afficher() {
		System.out.println("\t\t\t                    |\t\t        |\t\t        ");
                if(!isDead()){
                    System.out.println("\t\t\t          " + String.format("%-10s", this.nom) + "|\t\t" + String.format("%4.2f", this.hp) + "  |\t\t" +this.atk + "\t FEU");
                } else {
                    System.out.println("\t\t\t          " + String.format("%-10s", this.nom) + "|\t\t" + String.format("%3.2f", this.hp) + "  |\t\t" +this.atk + "\t FEU" + "\t\t\tMORT");
                }
		System.out.println("\t\t\t                    |\t\t        |\t\t        ");
	}
        
        @Override
        public String toString(){
            return this.nom.toUpperCase() + " ( HP = " + this.hp + ", ATTAQUE = " + this.atk + " TYPE = FEU )" ;
        }
}
