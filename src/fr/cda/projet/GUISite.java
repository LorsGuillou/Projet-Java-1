package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.util.TerminalException;

// Classe de definition de l'IHM principale du compte
//
public class GUISite implements FormulaireInt
{

    private Site site;  // Le site

    // Constructeur
    //
    public GUISite(Site site)
    {
        this.site = site;

        /** Creation du formulaire
         *
         */
        Formulaire form = new Formulaire("Site de vente",this,1100,730);
        
        //  Creation des elements de l'IHM
        //
        form.addLabel("Afficher tous les produits du stock");
        form.addButton("AFF_STOCK","Tous le stock");
        form.addLabel("");
        form.addLabel("Afficher tous les bons de commande");
        form.addButton("AFF_COMMANDES","Toutes les commandes");
        form.addLabel("");
        form.addText("NUM_COMMANDE","Numero de commande",true,"1");
        form.addButton("AFF_COMMANDE","Afficher");
        form.addLabel("");
        form.addButton("LIVRER", "Livrer");
        form.addLabel("");
        form.addButton("MODIFIER", "Modifier");
        form.addLabel("");
        form.addButton("CALCUL", "Calculer somme commande");

        form.setPosition(400,0);
        form.addZoneText("RESULTATS","Resultats",
                            true,
                            "",
                            600,700);

        // Affichage du formulaire
        form.afficher();
    }

    /** Methode appellee quand on clique dans un bouton
     *
     * @param form Le formulaire dans lequel se trouve le bouton
     * @param nomSubmit Le nom du bouton qui a été utilisé.
     */
    public void submit(Formulaire form,String nomSubmit)
    {

        // Affichage de tous les produits du stock
        //
        if (nomSubmit.equals("AFF_STOCK"))
            {
                String res = site.listerTousProduits();
                form.setValeurChamp("RESULTATS",res);
            }

        // Affichage de toutes les commandes
        //
        if (nomSubmit.equals("AFF_COMMANDES"))
            {
                String res = site.listerToutesCommandes();
                form.setValeurChamp("RESULTATS",res);
            }

        // Affichage d'une commande
        //
        if (nomSubmit.equals("AFF_COMMANDE"))
            {
                try {
                    String numStr = form.getValeurChamp("NUM_COMMANDE");
                    int num = Integer.parseInt(numStr);
                    String res = site.listerCommande(num);
                    form.setValeurChamp("RESULTATS", res);
                    // Levée des exceptions
                } catch (IndexOutOfBoundsException e) {
                    form.setValeurChamp("RESULTATS", "Cette commande n'existe pas.");
                } catch (Exception e) {
                    form.setValeurChamp("RESULTATS", "Veuillez entrer un entier positif.");
                }
            }

        // Livraison des commandes
        if (nomSubmit.equals("LIVRER")) {
            String res = site.statutLivraison();
            form.setValeurChamp("RESULTATS", res);
        }

        // Modification d'une commande donnee
        if (nomSubmit.equals("MODIFIER")) {
            try {
                String numStr = form.getValeurChamp("NUM_COMMANDE");
                int num = Integer.parseInt(numStr);
                if (!this.site.getCommandes().get(num - 1).isStatut()) { // On ne modifie que les commandes non livrées
                    GUIModifierCommande gui = new GUIModifierCommande(this, this.site, num - 1);
                } else {
                    form.setValeurChamp("RESULTATS", "Cette commande a été expédiée.");
                } // Levée des exceptions
            } catch (IndexOutOfBoundsException e) {
                form.setValeurChamp("RESULTATS", "Cette commande n'existe pas.");
            } catch (Exception e) {
                form.setValeurChamp("RESULTATS", "Veuillez entrer un entier positif.");
            }
        }

        // Calcul somme totale d'une commande
        if (nomSubmit.equals("CALCUL")) {
            try {
                String numStr = form.getValeurChamp("NUM_COMMANDE");
                int num = Integer.parseInt(numStr);
                String res = this.site.sommeCommande(num - 1);
                if (!this.site.getCommandes().get(num - 1).isStatut()) { // On ne calcul que la somme d'une commande livrée
                    form.setValeurChamp("RESULTATS", "Cette commande n'a pas pû être expédiée");
                } else {
                    form.setValeurChamp("RESULTATS", res);
                }
            } catch (IndexOutOfBoundsException e) {
                form.setValeurChamp("RESULTATS", "Cette commande n'existe pas.");
            } catch (Exception e) {
                form.setValeurChamp("RESULTATS", "Veuillez entrer un entier positif.");
            }
        }
    }

}