package fr.cda.projet;

import java.util.*;

// Classe de definition d'un produit du stock
//
public class Produit
{
    // Les caracteristiques d'un Produit
    //
    private String  reference;      // reference du produit
    private String  nom;            // nom du produit
    private double  prix;           // prix du produit
    private int     quantite;       // quantit� du produit

    // Constructeur
    //
    public Produit(String reference,
                   String nom,
                   double prix,
                   int quantite)
    {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void reductionStock(Commande com) {
        for (int i = 0; i < com.getReferences().size(); i++) {
            String[] tab = com.getReferences().get(i).split("=", 2);
            String ref = tab[0];
            int demande = Integer.parseInt(tab[1]);
            if (this.getNom().equals(ref)) {
                this.setQuantite(this.getQuantite() - demande);
            }
        }
    }

    // Conversion en chaine
    //
    public String toString()
    {
        return String.format("%-15s %-50s %3.2f   %3d",reference,nom,prix,quantite);
    }

}