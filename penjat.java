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
 * to-do: 
 * llegir d'un arxiu les paraules (per exemple, ca.dic)
 * al llegir-les, totes les lletres han de passar a minúscula
 * 
 * */
public class penjat {
	public static String[] arrParaules = {"suficient", "ignorar", "menys", "estona", "llegir",
										"felicitats", "guanyat", "dibuix", "lletres", "penjat", 
										"professor", "paraula", "cacatua", "llaminadura", 
										"supercalifragilisticoexpialidoso", "jugar", "intents" }; // Falta llegir-ho del llistat ca.dic o un altre
	public static String[] arrIntents = {":)", 
										"________", 
										" |\n |\n |\n |\n_|______",
										" _______\n |      \n |\n |\n |\n_|______",
										" _______\n |/     \n |\n |\n |\n_|______",
										" _______\n |/    |\n |\n |\n |\n_|______",
										" _______\n |/    |\n |     0\n |\n |\n_|______",
										" _______\n |/    |\n |     0\n |     |\n |\n_|______",
										" _______\n |/    |\n |     0\n |    /|\\\n |\n_|______",
										" _______\n |/    |\n |     0\n |    /|\\\n |    / \\\n_|______" }; // Possibilitat de llegir-ho d'arxiu ?
	public static String lletresAcceptades = "[a-zàèéòóíúüïç'·]"; // per fer string.matches(lletresAcceptades); (retorna boolean) (utilitza expresions regulars)
	public static String clean = clean(50); // Crea un string de n salts de linea
	public static boolean trobada = false;
	public static boolean win = false;
	public static String paraulaGuions = "";
	
	public static void main (String args[]) {
		benvinguda();
		jugar();
	}

	public static void benvinguda() {
		System.out.println("Benvingut/da al joc del penjat!");
	}
	
	public static void jugar() {
		String paraula;
		int intents = 0; // Numero d'intents fallats
		String lletresDites = ""; // lletres d'intents fallats
		Scanner sc = new Scanner(System.in);
		sc.useLocale(Locale.ENGLISH);
		System.out.println(clean);
		while(true) { 
			System.out.println("Vols escriure una paraula o obtenir una aleatoria?");
			System.out.println("1. Escriure paraula");
			System.out.println("2. Obtenir paraula aleatoria");
			System.out.print("Opcio [1/2]:");
			String opcio = sc.nextLine();
			if (opcio.equals("1")) {
				System.out.println(clean);
				System.out.println("Escriu la paraula que vulguis, en minúscula:\n");
				paraula = sc.nextLine();
				if (paraula.matches(lletresAcceptades+"+")) { // El més per indicar que la paraula ha de ser de 1 lletra o més (expresions regulars)
					break;
				}
				else {
					System.out.println(clean);
					System.out.println("Error, has introduït un caràcter invàlid\n");
					continue;
				}
					
			} else if (opcio.equals("2")) {
				paraula = getParaula(); // obtenim paraula aleatoriament 
				break;
			}
			else {
				System.out.println(clean);
				System.out.println("Error, has de seleccionar 1 o 2...");
				continue;
			}
		}
		System.out.println(clean);
		System.out.println(dibuixParaula(paraula, " ".charAt(0)));
		System.out.print("\n\nEscriu una lletra: ");
		String s = sc.nextLine();
		while(true) {
			if (s.matches(lletresAcceptades)) { // lletres
				if (!lletresDites.contains(s)) { // Si encara no s'ha dit la lletra...
					String str = dibuixParaula(paraula, s.charAt(0));
					if (!trobada) {
						lletresDites+=s.charAt(0) + ", ";
						intents++;
					}
					System.out.println(clean); // Neteja la pantalla
					if (intents<9) { // Mentre els intents siguin inferiors a 9... (nº d'intents máxim aquí)
						System.out.println(arrIntents[intents]); 
						System.out.println("\n"+lletresDites);
						System.out.println("\n"+str);
						if (win) { // Si ha guanyat...
							System.out.println("\n\nHas trobat la paraula!!! Felicitats!");
							break;
						}
					}
					else {
						System.out.println(arrIntents[intents]); 
						System.out.println("\n\nHas perdut...");
						System.out.println("La paraula era: " + paraula);
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
		sc.close();
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

