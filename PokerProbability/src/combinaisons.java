import javax.swing.ListModel;
import java.util.ArrayList;
import java.util.Arrays;

public class combinaisons {
	/*
	 * Ici, on va lister toutes les combinaisons et essayer de calculer les proba que ca arrive 
	 * Pour faire ca, on va prendre en argument les 2 cartes du joueur + 3, 4 ou 5 cartes qui sont les cartes communes
	 */
	
	ArrayList<String> cartes = new ArrayList<String>();   
	
	public combinaisons(ListModel<String> joueur, ArrayList<ListModel<String>> commun) {
		System.out.println("création combinaisons " + joueur + commun);
		
		/*
		 * on fait un tableau de string avec les cartes 
		 */
		for (int i=0; i<joueur.getSize();i++) {
			cartes.add(joueur.getElementAt(i)); 
		}
		for (int i = 0; i<commun.size(); i++) {
			if (commun.get(i).getSize() != 0 ) {
				cartes.add(commun.get(i).getElementAt(0));
			}
		}
		System.out.println(cartes); 
		
		FirstScan();
		
	}
	
	
	
	
	
	/*
	 * Là on va checker si le joueur a une combinaison ou non 
	 */
	
	// on va juste faire un premier scan, chercher si y'a des cartes ou des couleurs qui se répètent 
	public void FirstScan() {
		
		ArrayList<Integer> valeurs = new ArrayList<Integer>( 
				Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0)); // tableau qui contient le nombre d'occurence de chacun des chiffres (ordre : As, 2, 3, ..., J, Q, K)
		
		ArrayList<Integer> couleurs = new ArrayList<Integer>( 
				Arrays.asList(0,0,0,0)); // tableau qui contient le nombre d'occurence des couleurs (ordre : coeur, carreau, trèfle, pique)
		
		// on passe par toutes les cartes pour remplir les tableaux 
		for (int i=0; i < cartes.size(); i++) {
			// pour chaque carte, 
			char val = cartes.get(i).charAt(0) ; // la valeur 
			char coul = cartes.get(i).charAt(cartes.get(i).length()-1) ; // la couleur 
			
			switch(val){ // on tri les valeurs 
		       case 'A':  valeurs.set(0, valeurs.get(0)+1) ;   break;
		       case '1': valeurs.set(9, valeurs.get(9)+1) ;  break;
		       case 'J':  valeurs.set(10, valeurs.get(10)+1) ;  break;
		       case 'Q':  valeurs.set(11, valeurs.get(11)+1) ;   break;
		       case 'K': valeurs.set(12, valeurs.get(12)+1) ;  break;
		       default: int value = Character.getNumericValue(val) ; valeurs.set(value-1, valeurs.get(value-1)+1) ;  break;
		    }
			
			switch(coul){ // on trie les couleurs 
		       case '♥': couleurs.set(0, couleurs.get(0)+1) ; break;
		       case '◆': couleurs.set(1, couleurs.get(1)+1) ;  break;
		       case '♣': couleurs.set(2, couleurs.get(2)+1) ;  break;
		       case '♠':couleurs.set(3, couleurs.get(3)+1) ; break;
		    }
		}
		
		/* Premier check des combinaisons 
		 * 
		 * COULEUR 
		 * Quinte flush royale : As Roi Dame Valet 10 d'une couleur 
		 * Quite flush : Suite d'une même couleur 
		 * Couleur : 5 cartes de la même couleur 
		 * 
		 * VALEUR
		 * Carré : 4 même cartes
		 * Full : Brelan + paire 
		 * Brelan : 3 même cartes
		 * Double paire : 2 * 2 mêmes cartes
		 * Paire : 2 même cartes 
		 * 
		 * AUTRE 
		 * Suite : 5 cartes qui se suivent (2-3-4-5-6 par exemple) 
		 */
		
		System.out.println("Couleurs : " + couleurs); 
		System.out.println("Valeurs : " + valeurs); 
		
		boolean symbole = false ; 
		int paire = 0 ; 
		boolean brelan = false ; 
		boolean carre = false ; 
		boolean suite = false ; 
		
		
		// check couleur (si 5 cartes mêmes couleurs ou pas) 
		for (int i=0; i<couleurs.size(); i++) {
			if (couleurs.get(i) >= 5) { symbole = true ;  }
		}
		
		// check valeur 
		for (int i=0; i<valeurs.size(); i++) {
			if (valeurs.get(i) == 2) { paire += 1 ;} 
			if (valeurs.get(i) == 3) { brelan = true ;} 
			if (valeurs.get(i) == 4) { carre = true ; } 
		}

		// check suite 
		for (int i=2; i<valeurs.size()-2; i++) {
			if (valeurs.get(i-2)== 1 && valeurs.get(i-1)== 1 && valeurs.get(i)== 1 && valeurs.get(i+1)== 1 && valeurs.get(i+2)== 1 ) {
				suite = true ; 
			}
		} 
		
		// on a les résultat, on peut maintenant déduire les bails

		if (suite && symbole) {System.out.println("Probablement Quinte Flush ! ");}
		else if (carre) {System.out.println("Carré comme un kiri");}
		else if (brelan && paire == 1) {System.out.println("Fiulllll");}
		else if (symbole) {System.out.println("Symbole ! enfin couleur mais en vrai c'est symbole");}
		else if (suite) {System.out.println("C'est rigolo ca se suit ");}
		else if (brelan) {System.out.println("Brelan, branlé, c'est pareil un peu");}
		else if (paire == 2 ) {System.out.println("Double papa");}
		else if (paire == 1 ) {System.out.println("Y'en a pas deux ");}
		else {System.out.println("J'espère t'as un bonne carte haute...");}
	}
	
	/*
	 * Quite flush royale 
	 */

	
	/*
	 * Quinte flush 
	 */
	
	/* 
	 * Carré 
	 */
	
	/*
	 * Full
	 */
	
	/*
	 * Couleur
	 */
	
	/*
	 * Suite
	 */

	/*
	 * Brelan 
	 */
	
	/*
	 * Double paire 
	 */
	
	/*
	 * Paire 
	 */
	
	/*
	 * Carte haute
	 */

}
