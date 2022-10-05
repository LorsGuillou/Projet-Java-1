package fr.cda.projet;

import fr.cda.util.Terminal;

import java.util.*;

// Classe de definition d'une commande
//
public class Commande
{
    // Les caracteristiques d'une commande
    //
    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private ArrayList<String> references = new ArrayList<>(); // les references des produits de la commande
    private boolean statut = false;
    private String raison = "";

    public Commande(int numero, String date, String client) {
        this.numero = numero;
        this.date = date;
        this.client = client;
    }

    // Ajout d'une référence
    public void ajouterRef(String ref) {
        this.references.add(ref);
    }

    public Commande(){}

    // Getters et setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String produit, int quant) {
        for (String ref : references) {
            this.raison = "Il manque " + quant + " " + produit + "\n";
        }
    }

    // Override d'equals pour permettre de s'en servir sur une commande
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Commande)) {
            return false;
        }
        Commande other = (Commande)obj;
        return numero == (other.numero) && date.equals(other.date) && client.equals(other.client);
    }

    // Affichage des références
    public String afficherRef() {
        String res = "";
        for (String ref : references) {
            res += "        " + ref + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return "Commande : " + numero + "\n"
                + "Date : " + date + "\n"
                + "Client : " + client + "\n"
                + "RefProduits :\n" + afficherRef() + "\n"
                + getRaison() + "\n"
                + "______________________________\n";
    }
}