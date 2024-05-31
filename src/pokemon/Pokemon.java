/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author Coutso
 */
public class Pokemon {
	protected String nom;
	protected double hp;
	protected double atk;
	
	public Pokemon(String nom, double hp, double atk) {
		this.nom = nom;
		this.hp = hp;
		this.atk = atk;
	}
	
        public String getNom(){
            return this.nom;
        }
	public double getHp() {
		return this.hp;
	}
	public double getAtk() {
		return this.atk;
	}
	
	public boolean isDead() {
		if(this.hp == 0){
                    return true;
                } else {
                    return false;
                }
	}
	
	public void attaquer(Pokemon p){
            if(p.hp >= this.atk){
                p.hp -= this.atk;
            } else {
                p.hp = 0;
            }
        }
	
	public void afficher() {
		System.out.println("\t\t\t                    |\t\t        |\t\t        ");
                if(!isDead()){
                    System.out.println("\t\t\t          " + String.format("%-10s", this.nom) + "|\t\t" + String.format("%4.2f", this.hp) + "  |\t\t" +this.atk + "\t NORMAL");
                } else {
                    System.out.println("\t\t\t          " + String.format("%-10s", this.nom) + "|\t\t" + String.format("%3.2f", this.hp) + "  |\t\t" +this.atk + "\t NORMAL" + "\t\t\tMORT");
                }
		System.out.println("\t\t\t                    |\t\t        |\t\t        ");
	}
        @Override
        public String toString(){
            return this.nom.toUpperCase() + " ( HP = " + this.hp + ", ATTAQUE = " + this.atk + " TYPE = NORMAL )" ;
        }
}