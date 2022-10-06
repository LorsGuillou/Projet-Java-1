# Projet-Java-1

Cette application permet de gérer les stocks et les livraisons d'un site de ventes.
On peut à la fois visionner les stocks, les bons de commandes, commencer les livraisons, modifier les commandes au besoin et calculer les revenus faient à partir d'une commande.

Les fonctionnalités sont les suivantes :

1) Tous le stock

Ce bouton va afficher dans la fenêtre Résultats tout les produits proposés par le site de vente.
L'affichage est organisé comme tel :

Référence (ex: LIVRE-1)     Nom du produit      Prix        Quantité en stock

2) Toutes les commandes

Ce bouton va afficher tout les bons de commandes.
L'affichage est organisé comme tel :

Numéro de commande
Date de la commande
Nom du client
Références des produits commandés

Dans les références présentes sur les bons de commandes, on ajoute également la quantité commandée du produit référencés, au format :

Référence produit=Quantité commandée

Le champ numéro de commande permet de faire fonctionner les fonctionnalités Afficher, Modifier et Calculer somme commande.

3) Afficher

On y affiche la commande dont le numéro a été saisie dans le champ au-dessus, au même format que la fonctionnalité précédente. Si un numéro d'une commande non existente est saisie, ou si l'ont saisie autre chose qu'un numéro entier positif, un message d'erreur s'affichera. 

4) Livrer

Ce bouton permet de tenter la livraison des commandes. Si la livraison est impossible à cause d'une différence dans la capacité des stocks, le bon de la commande non expédiée s'affichera comme précédemment, avec en plus les produits qui ne sont pas en mesure d'être livrés et la quantité manquante.

5) Modifier

Ce bouton permet de modifier la commande dont le numéro a été saisie dans le champ. On peut ici modifier la quantité des produits commandés pour s'accorder aux stocks disponibles. Comme précédemment, un message d'erreur s'affichera si autre chose qu'un entier positif est saisi.