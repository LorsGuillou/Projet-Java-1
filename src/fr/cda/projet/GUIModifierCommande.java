package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.util.TerminalException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GUIModifierCommande implements FormulaireInt {

    private GUISite formPP;
    private Site site;
    private int commande;

    public GUIModifierCommande(GUISite formPP, Site site, int commande) throws Exception {
        this.formPP = formPP;
        this.site = site;
        this.commande = commande;
        if (commande < 0) {
            throw new Exception();
        } else {
            Formulaire form = new Formulaire("Modifier commande", this, 320, 260);
            form.setPosition(20, 10);
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
                for (int i = 0; i < com.getReferences().size(); i++) {
                    String[] tab = com.getReferences().get(i).split("=", 2);
                    String ref = tab[0];
                    form.addText(ref, ref, true, "");
                }
            form.addButton("VALIDER", "Valider");
            form.afficher();
        }
    }
    @Override
    public void submit(Formulaire form, String nomSubmit) {
        if (nomSubmit.equals("VALIDER")) {
            ArrayList<Produit> stock = this.site.getStock();
            ArrayList<Commande> commandes = this.site.getCommandes();
            Commande com = commandes.get(this.commande);
            for (int i = 0; i < com.getReferences().size(); i++) {
                for (Produit prod : stock) {
                    String[] tab = com.getReferences().get(i).split("=", 2);
                    String ref = tab[0];
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
