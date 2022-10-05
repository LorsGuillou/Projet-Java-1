package fr.cda.projet;

import java.util.*;

import fr.cda.util.*;

// Classe de definition du site de vente
//
public class Site
{
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    // Constructeur
    //
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
    
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  tous les produits du stock
    //
    public String listerTousProduits()
    {
        String res="";
        for(Produit prod : stock)
            res=res+prod.toString()+"\n";

        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  toutes les commandes
    //
    public String listerToutesCommandes()
    {
        String res="";
        for (Commande com : commandes)
            res += com.toString()+"\n";

        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  une commande
    //
    public String listerCommande (int numero) throws Exception
    {
        String res="";
        if (numero < 0) {
            throw new Exception();
        } else {
            res += commandes.get(numero - 1).toString();
        }
        
        return res;
    }

    // Chargement du fichier de stock
    //
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

    private void initialiserCommandes(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            System.out.println(ligne);
            String[] champs = ligne.split(";", 4);
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            String reference = champs[3];
            Commande c = new Commande(numero, date, client);
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

    public void reductionStock(Commande com) {
        for (String ref : com.getReferences()) {
            for (Produit prod : stock) {
                String[] tab = ref.split("=", 2);
                String refCommande = tab[0];
                int demande = Integer.parseInt(tab[1]);
                if (prod.getNom().equals(refCommande)) {
                    prod.setQuantite(prod.getQuantite() - demande);
                }
            }
        }
    }

    // TODO : fixer cette méthode

    /* public String statutLivraison() {
        String res = "Commandes non livrées :\n=========================\n";
        boolean dispo = true;
        boolean possible = true;
        int quantProd = 0;
        for (Commande com : commandes) {
            for (String ref : com.getReferences()) {
                String[] tab = ref.split("=", 2);
                String nomRefCommande = tab[0];
                int demande = Integer.parseInt(tab[1]);
                for (Produit prod : stock) {
                    if (nomRefCommande.equals(prod.getReference())) {
                        if (demande > quantProd) {
                            quantProd = prod.getQuantite();
                            dispo = false;
                            possible = false;
                        }
                    }
                }
                if (!dispo) {
                    int manqueStock = demande - quantProd;
                    com.setRaison(nomRefCommande, manqueStock);
                }
                if (!possible) {
                    res += com.toString();
                } else {
                    reductionStock(com);
                    com.setStatut(true);
                }
            }
        }
        return res;
    } */


    public String statutLivraison() {
        boolean checkStock = true;
        boolean checkLivraison = true;
        int quantProd = 0;
        String res = "";
        for (Commande com : commandes) {
            for (int i = 0; i < com.getReferences().size(); i++) {
                String[] tab = com.getReferences().get(i).split("=", 2);
                String ref = tab[0];
                int demande = Integer.parseInt(tab[1]);
                for (Produit prod : stock) {
                    if (prod.getReference().equals(ref)) {
                        if (prod.getQuantite() < demande) {
                            quantProd = prod.getQuantite();
                            prod.setQuantite(0);
                            checkStock = false;
                            checkLivraison = false;
                        }
                    }
                } // Fin boucle Produit
                if (!checkStock) {
                    int manqueStock = demande - quantProd;
                    com.setRaison(ref, manqueStock);
                }
            }
            if (!checkLivraison) {
                res += com.toString();
            } else {
                reductionStock(com);
                com.setStatut(true);
            }
        }
        return res;
    }

    /* public String livraisonsNonFaites() {
        String res = "Commandes non livrées:\n______________________________\n";
        for (Commande com : commandes) {
            statutLivraison();
            if (!com.isStatut()) {
                res += com.toString();
            }
        }
        return res;
    } */
}