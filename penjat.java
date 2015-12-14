/*
 * penjat.java  13-12-2015
 * 
 * Copyright 2015 Charly <charlykoch@gmail.com>
 * 
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information. 
 */

import java.util.Scanner;
import java.util.Locale;
import java.util.Random;


/* 
 * to-do: llegir de l'arxiu ca.dic les paraules
 * ignorar, de cada línia, a partir de /
 * al llegir-les, totes les lletres han de passar a minúscula
 * comprovar si s.matches("[a-zàèéòóíú·ç]")  es suficient.
 * 
 * */
public class penjat {
	public static String[] arrParaules = {"paraula", "cacatua", "murcielago", "llaminadura", "supercalifragilisticoexpialidoso"}; // Falta llegir-ho del llistat ca.dic
	public static String clean = clean(50);
	public static boolean trobada = false;
	public static boolean win = false;
	public static String paraulaGuions = "";
	public static void main (String args[]) {
		//System.out.println(getParaula());
		//System.out.println(dibuixParaula("paraula", "a".charAt(0)));
		//for (int i=1; i<=9; i++)
		//System.out.println(i+"\n"+dibuixIntents(i)+"\n");
		jugar();
	}
	
	public static void jugar() {
		String paraula = getParaula();
		int intents = 0; // Numero d'intents fallats
		String lletresDites = ""; // lletres d'intents fallats
		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.ENGLISH);
		System.out.println(clean);
		System.out.println(dibuixParaula(paraula, " ".charAt(0)));
		System.out.print("\n\nEscriu una lletra: ");
		String s = sc.nextLine();
		while(true) {
			if (s.matches("[a-zàèéòóíú·ç]")) { // lletres
				if (!lletresDites.contains(s)) { // Si encara no s'ha dit la lletra...
					String str = dibuixParaula(paraula, s.charAt(0));
					if (!trobada) {
						lletresDites+=s.charAt(0) + ", ";
						intents++;
					}
					System.out.println(clean); // Neteja la pantalla
					if (intents<9) { // Mentre els intents siguin inferiors a 9...
						System.out.println(dibuixIntents(intents)); 
						System.out.println("\n"+lletresDites);
						System.out.println("\n"+str);
						if (win) { // Si ha guanyat...
							System.out.println("\n\nHas trobat la paraula!!! Felicitats!");
							break;
						}
					}
					else {
						System.out.println(dibuixIntents(intents)); 
						System.out.println("\n\nHas perdut...");
						break;
						
					}
				}
				else {
					System.out.println("Ja has dit aquesta lletra !!");
				}
			} 
			else {
				System.out.println("Error, has d'escriure únicament una lletra. Només s'accepten lletres en minúscula i accents vàlids en català");
			}
			System.out.print("\n\nEscriu una lletra: ");
			s = sc.nextLine();
		}
	}
	
	
	public static String dibuixIntents (int n) {
		switch (n) {
			case 0: return ":)";
			case 1: return "________";
			case 2: return " |\n |\n |\n |\n_|______";
			case 3: return " _______\n |      \n |\n |\n |\n_|______";
			case 4: return " _______\n |/     \n |\n |\n |\n_|______";
			case 5: return " _______\n |/    |\n |\n |\n |\n_|______";
			case 6: return " _______\n |/    |\n |     0\n |\n |\n_|______";
			case 7: return " _______\n |/    |\n |     0\n |     |\n |\n_|______";
			case 8: return " _______\n |/    |\n |     0\n |    /|\\\n |\n_|______";
			case 9: return " _______\n |/    |\n |     0\n |    /|\\\n |    / \\\n_|______";
			default: return "error d'intents";
		}
	}
	public static String dibuixParaula (String paraula, char lletra) {
		int l = paraula.length();
		String str = "";
		trobada = false; 
		for (int i=0; i<l; i++) {
			if (paraula.charAt(i)==lletra) {
				trobada = true;
				str+=lletra + " ";
			}
			else str+="_ ";
		}
		if (paraulaGuions=="") paraulaGuions=str; // Sí es la primera "tirada"..
		else { 
			StringBuilder parBuild = new StringBuilder(paraulaGuions);
			
			for (int i=0; i<str.length(); i=i+2) {
				if (paraulaGuions.charAt(i)=="_".charAt(0) && str.charAt(i)!="_".charAt(0)) {
					parBuild.setCharAt(i, str.charAt(i));
				}
			}
			paraulaGuions=parBuild.toString();
			
			win = true; // Diem que ha guanyat per defecte..
			int x=0;
			for (int i=0; i<str.length(); i=i+2) {
				if (paraulaGuions.charAt(i)!=paraula.charAt(x)) { // Si troba alguna lletra que no coincideix amb la paraula original..
					win = false; // No ha guanyat
					break; // i sortim del bucle
				}
				x++;
			}
		}
		return paraulaGuions;
	}
	public static String getParaula() { // Retorna una paraula aleatoria del llistat inicial
		int l = arrParaules.length;
		return arrParaules[randInt(0,l-1)]; 
	}
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt normalmente exluye el valor maximo
		// por ello se le suma uno para incluirlo en los posibles valores
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	public static String clean(int n) {
		String str = "\n";
		for (int i=0; i<n; i++) {
			str += "\n";
		}
		return str;
	}
}

