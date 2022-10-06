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
    private int commande; // Le numero de la commande

    /** Constructeur
     * @param formPP
     * @param site
     * @param commande
     * @throws Exception
     */
    public GUIModifierCommande(GUISite formPP, Site site, int commande) throws Exception {
        this.formPP = formPP;
        this.site = site;
        this.commande = commande;
        // Levee d'exception si le numero de commande n'existe pas
        if (commande < 0) {
            throw new Exception();
        } else {
            Formulaire form = new Formulaire("Modifier commande", this, 320, 260);
            form.setPosition(20, 10);
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
            // Creation du formulaire en fonction du nombre de references
                for (int i = 0; i < com.getReferences().size(); i++) {
                    String[] tab = com.getReferences().get(i).split("=", 2);
                    String ref = tab[0];
                    form.addText(ref, ref, true, "");
                }
            form.addButton("VALIDER", "Valider");
            form.afficher();
        }
    }

    /** Methodee appelée lors du clic sur le bouton Valider
     *
     * @param form Le formulaire dans lequel se trouve le bouton
     * @param nomSubmit Le nom du bouton qui a ete utilise.
     */

    @Override
    public void submit(Formulaire form, String nomSubmit) {

        if (nomSubmit.equals("VALIDER")) {
            ArrayList<Produit> stock = this.site.getStock();
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
            // On recupere les produits et commandes, puis la référence et la quantité demandée
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
