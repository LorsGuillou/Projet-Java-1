package fr.cda.projet;

import java.util.*;

import fr.cda.util.*;

// Classe de definition du site de vente
//
public class Site
{
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    /** Constructeur
     *
     */

    public Site()
    {
        stock = new ArrayList<Produit>();

        // lecture du fichier data/Produits.txt
        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");

        commandes = new ArrayList<Commande>();
        //  lecture du fichier data/Commandes.txt
        //  pour chaque ligne on cree une commande ou on ajoute une reference
        //  d'un produit a une commande existante.
        initialiserCommandes("data/Commandes.txt");
        
    }

    public ArrayList<Produit> getStock() {
        return stock;
    }

    public ArrayList<Commande> getCommandes() {
        return commandes;
    }

    /** Methode qui retourne sous la forme d'une chaine de caracteres tous les produits du stock
     *
    */
    public String listerTousProduits()
    {
        String res="";
        for(Produit prod : stock)
            res=res+prod.toString()+"\n";

        return res;
    }

    /** Methode qui retourne sous la forme d'une chaine de caracteres toutes les commandes
     *
    */
    public String listerToutesCommandes()
    {
        String res="";
        for (Commande com : commandes)
            res += com.toString()+"\n";

        return res;
    }

    /** Methode qui retourne sous la forme d'une chaine de caractere une commande
     *
     * @param numero
     * @return
     * @throws Exception
     */

    public String listerCommande (int numero) throws Exception
    {
        String res="";
        if (numero < 1) {
            throw new Exception();
        } else {
            res += commandes.get(numero - 1).toString();
        }
        
        return res;
    }

    /** Chargement du fichier de stock
     *
     * @param nomFichier
     */
    private void initialiserStock(String nomFichier)
    {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for(String ligne :lignes)
            {
                System.out.println(ligne);
                String[] champs = ligne.split(";",4);
                String reference = champs[0];
                String nom = champs[1];
                double prix = Double.parseDouble(champs[2]);
                int quantite =  Integer.parseInt(champs[3]);
                Produit p = new Produit(reference,
                                        nom,
                                        prix,
                                        quantite
                                        );
                stock.add(p);
            }
    }

    /** Initialisation des commandes a partir du fichier texte source
     *
     * @param nomFichier
     */
    private void initialiserCommandes(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            System.out.println(ligne);
            // S?paration des lignes dans des tableaux
            String[] champs = ligne.split(";", 4);
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            String reference = champs[3];
            Commande c = new Commande(numero, date, client);
            // Ajout des references produits dans les commandes si elles n'y sont pas deja
            if (!commandes.contains(c)) {
                commandes.add(c);
            }
            for (Commande com : commandes) {
                if (com.getNumero() == numero) {
                    com.ajouterRef(reference);
                }
            }
        }
    }

    /** Calcul de la somme des produits d'une commande
     *
     * @param index
     * @return
     */
    public String sommeCommande(int index) throws Exception {
        String res = "";
        double somme = 0;
        Commande com = commandes.get(index);
        res += com.toString();
        if (index < 0) {
            throw new Exception();
        } else {
            for (int i = 0; i < com.getReferences().size(); i++) {
            // Recuperation de la reference commande
                String[] tab = com.getReferences().get(i).split("=", 2);
                String ref = tab[0];
                int quantCom = Integer.parseInt(tab[1]);
                for (Produit prod : stock) {
                // Comparaison avec la reference produit
                    if (prod.getReference().equals(ref)) {
                        somme += (prod.getPrix() * quantCom);
                    }
                }
            }
        }
        res += "Total commande : " + somme;
        return res;
    }

    /** Verification de la possibilite d'envoi d'une commande
     *
     * @return
     */
    public String statutLivraison() {
        boolean checkStock = true;
        boolean checkLivraison = true;
        int quantProd = 0;
        String res = "";
        for (Commande com : commandes) {
            for (int i = 0; i < com.getReferences().size(); i++) {
                // Recuperation de la reference produit
                String[] tab = com.getReferences().get(i).split("=", 2);
                String ref = tab[0];
                // Recuperation de la quantite commandee
                int demande = Integer.parseInt(tab[1]);
                for (Produit prod : stock) {
                    // Comparaisons references et quantites des produits en stock
                    if (prod.getReference().equals(ref)) {
                        // Si la demande est plus grande que le stock, la livraison n'est pas faite
                        if (prod.getQuantite() < demande) {
                            quantProd = prod.getQuantite();
                            checkStock = false;
                            checkLivraison = false;
                        } else {
                            prod.setQuantite(prod.getQuantite() - demande);
                        }
                    }
                }
                // Si la livraison n'est pas faite, on calcule le manque entre demande et stock
                if (!checkStock) {
                    int manqueStock = demande - quantProd;
                    com.setRaison(ref, manqueStock);
                }
            }
            // Si la livraison n'est pas faite, son statut reste a false et on l'affiche
            if (!checkLivraison) {
                com.setStatut(false);
                res += com.toString();
                // Sinon son statut devient true et elle est faites sans encombre
            } else {
                com.setStatut(true);
            }
        }
        return res;
    }
}