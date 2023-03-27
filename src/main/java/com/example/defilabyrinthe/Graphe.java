package com.example.defilabyrinthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Méthode représentant le graphe.
 */
public class Graphe {
  /**
   * Liste des noeuds du graphe.
   */
  private ArrayList<Cell> noeuds;

  /**
   * Liste d'adjacence du graphe.
   */
  private HashMap<Cell, Set<Cell>> listeAdjacence;

  /**
   * Liste des noeuds précédents pour avoir le chemin le plus court.
   */
  public Map<Cell, Cell> predecessors = new HashMap<>();

  /**
   * Liste des noeuds explorés.
   */
  public ArrayList<Cell> explored = new ArrayList<>();

  /**
   * Constructeur de la classe Graphe.
   */
  public Graphe() {
    noeuds = new ArrayList<>();
    listeAdjacence = new HashMap<>();
  }

  /**
   * Ajoute un noeud au graphe.
   *
   * @param noeud Noeud à ajouter
   */
  public void ajouterNoeud(Cell noeud) {
    noeuds.add(noeud);
    listeAdjacence.put(noeud, new HashSet<>());
  }

  /**
   * Ajoute une arête entre deux noeuds du graphe.
   *
   * @param noeud1 Noeud 1
   * @param noeud2 Noeud 2
   */
  public void ajouterArete(Cell noeud1, Cell noeud2) {
    listeAdjacence.get(noeud1).add(noeud2);
    listeAdjacence.get(noeud2).add(noeud1);
  }

  /**
   * Retourne la liste des voisins d'un noeud.
   *
   * @param noeud Noeud dont on veut les voisins
   * @return Liste des voisins du noeud
   */
  public Set<Cell> getVoisins(Cell noeud) {
    return listeAdjacence.get(noeud);
  }

  /**
   * Calcule le chemin le plus court entre deux noeuds du graphe. Utilise l'algorithme
   * A*.
   *
   * @param depart Noeud de départ
   * @param arrivee Noeud d'arrivée
   * @return distance entre les deux noeuds
   */
  public double aaEtoile(Cell depart, Cell arrivee) {
    predecessors.clear();
    Map<Cell, Double> distances = new HashMap<>();
    for (Cell cellule : noeuds) {
      distances.put(cellule, Double.POSITIVE_INFINITY);
    }
    distances.put(depart, 0.);
    ArrayList<Cell> candidats = new ArrayList<>();
    candidats.add(depart);
    explored.clear();

    Cell noeud = depart;
    while (!candidats.isEmpty() && arrivee != noeud) {
      noeud = trouverProchainNoeud(candidats, distances, arrivee);
      if (noeud == null) {
        break;
      }
      ArrayList<Cell> s1 = new ArrayList<>();
      for (Cell noeudPossible : getVoisins(noeud)) {
        if (!explored.contains(noeudPossible)) {
          s1.add(noeudPossible);
        }
      }
      for (Cell prochainNoeudPossible : s1) {
        if (!candidats.contains(prochainNoeudPossible)) {
          candidats.add(prochainNoeudPossible);
        }
        if (distances.get(prochainNoeudPossible) > distances.get(noeud) + 1) {
          distances.replace(prochainNoeudPossible, distances.get(noeud) + 1);
          predecessors.put(prochainNoeudPossible, noeud);
        }
      }
      candidats.remove(noeud);
      explored.add(noeud);
    }
    return distances.get(arrivee);
  }

  /**
   * Trouve le prochain noeud à explorer.
   *
   * @param candidats Liste des noeuds candidats
   * @param distances Liste des distances entre les noeuds
   * @param arrivee Noeud d'arrivée
   * @return Le prochain noeud à explorer
   */
  private Cell trouverProchainNoeud(ArrayList<Cell> candidats,
                                    Map<Cell, Double> distances, Cell arrivee) {
    double mini = Double.POSITIVE_INFINITY;
    Cell sommet = null;
    for (Cell noeud : candidats) {
      if (distances.get(noeud) + noeud.distance(arrivee) < mini) {
        mini = distances.get(noeud) + noeud.distance(arrivee);
        sommet = noeud;
      }
    }
    return sommet;
  }

  /**
   * Reconstruit le chemin le plus court entre deux noeuds.
   *
   * @param depart Noeud de départ
   * @param arrivee Noeud d'arrivée
   * @return Chemin le plus court entre les deux noeuds
   */
  public ArrayList<Cell> getChemin(Cell depart, Cell arrivee) {
    ArrayList<Cell> chemin = new ArrayList<>();
    Cell noeud = arrivee;
    while (noeud != depart) {
      chemin.add(noeud);
      noeud = predecessors.get(noeud);
    }
    chemin.add(depart);
    return chemin;
  }

}
