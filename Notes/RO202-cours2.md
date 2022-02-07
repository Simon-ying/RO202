# 2 Flots et coupes  

## Définition

- Flux
- Flot 
- Flot réalisable  
- Arc saturé  
- Flot complet  
- Valeur d’un flot  
- Flot maximal
- Chaîne améliorante  

## L’algorithme de Ford-Fulkerson  

**Données :** $G = (V, A, C)$
Établir un flot admissible (complet de préférence)
**répéter**
		Retirer toutes les marques

​		Marquer '$+$' le sommet $s$

​		**répéter**

​				Marquer ’+i’ le sommet terminal j de tout

​						arc $(ij)$ tel que :

​						$i$ est marqué

​						$j$ est non marqué

​						$(ij)$ non saturé

​						Marquer ' $-j$ ' le sommet initial i de tout arc $(ij)$ tel que :

​								$i$ est non marqué

​								$j$ est marqué

​								$(ij)$ a un flux non nul

​		**tant que** <u>un nouveau sommet a été marqué</u> **et** <u>$t$ n’est pas marqué</u>

​				**si** <u>$t$ est marqué</u> **alors**
​						Améliorer le flux via une chaîne améliorante
**tant que** <u>$t$ est marqué</u>  	