## 4 Programmation linéaire en nombres entiers  

### 4.1 Algorithme de branch-and-bound

- Solution de la relaxation continue



**Initialisation** :

​		Calculer une solution admissible de valeur $Z^*$, ou poser $Z^*=-\infin$

​		Résoudre la relaxation continue et mettre à jour $Z^∗$ si besoin

**Tant qu**’il reste des nœuds non élagués :

​		Choisir un nœud non élagué

​		Brancher sur une des variables de valeur fractionnaire en ce nœud

​		Résoudre la relaxation continue des deux nœuds obtenus et mettre à jour $Z^∗$

​		Appliquer les tests d’élagage



### 4.2 Algorithme de branch-and-cut

- Principe - Ajout de coupe : Séparer l’optimum continu des solutions admissibles
- Inégalité valide pour $(P)$ : $ax ≤ b$ est vérifiée par tout $x \in F(P)$
- Inégalité valide "intéressante" : $ax ≤ b$ "tronque" $F(P_R)$



- Procédure arborescente
- Ajout de coupes en chaque nœud

