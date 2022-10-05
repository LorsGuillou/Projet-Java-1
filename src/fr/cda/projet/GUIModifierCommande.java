package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.util.TerminalException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
// Classe de l'IHM secondaire de modification de commande
public class GUIModifierCommande implements FormulaireInt {

    private GUISite formPP; // L'IHM principale
    private Site site; // Le site
    private int commande; // Le num�ro de la commande

    // Constructeur
    public GUIModifierCommande(GUISite formPP, Site site, int commande) throws Exception {
        this.formPP = formPP;
        this.site = site;
        this.commande = commande;
        // Lev�e d'exception si le num�ro de commande n'existe pas
        if (commande < 0) {
            throw new Exception();
        } else {
            Formulaire form = new Formulaire("Modifier commande", this, 320, 260);
            form.setPosition(20, 10);
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
            // Cr�ation du formulaire en fonction du nombre de r�f�rences
                for (int i = 0; i < com.getReferences().size(); i++) {
                    String[] tab = com.getReferences().get(i).split("=", 2);
                    String ref = tab[0];
                    form.addText(ref, ref, true, "");
                }
            form.addButton("VALIDER", "Valider");
            form.afficher();
        }
    }

    // M�thode appel�e lors du clic sur le bouton Valider
    @Override
    public void submit(Formulaire form, String nomSubmit) {

        if (nomSubmit.equals("VALIDER")) {
            ArrayList<Produit> stock = this.site.getStock();
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
            // On r�cup�re les produits et commandes, puis la r�f�rence et la quantit� demand�e
            for (int i = 0; i < com.getReferences().size(); i++) {
                for (Produit prod : stock) {
                    String[] tab = com.getReferences().get(i).split("=", 2);
                    String ref = tab[0];
                    // On compare avec le bon produit pour le modifier dans le champ voulu
                    if (prod.getReference().equals(ref)) {
                        tab[1] = form.getValeurChamp(ref);
                        com.getReferences().set(i, tab[0] + "=" + tab[1]);
                    }
                }
            }
            form.fermer();
        }
    }
}
