package com.example.defilabyrinthe;

/**
 * Classe représentant une cellule du labyrinthe.
 */
public class Cell {

  /**
   * Coordonnées en abscisse de la cellule.
   */
  private int abscisseCoord;

  /**
   * Coordonnées en ordonnées de la cellule.
   */
  private int ordonneeCoord;

  /**
   * Murs de la cellule.
   */
  private Boolean east;
  private Boolean north;
  private Boolean west;
  private Boolean south;

  /**
   * Indique si la cellule a déjà été visitée.
   */
  public boolean visited;

  /**
   * Constructeur par défaut.
   */
  Cell() {
    east = true;
    north = true;
    south = true;
    west = true;
    visited = false;
  }

  /**
   * Constructeur.
   *
   * @param abscisseCoord Coordonnée x de la cellule
   * @param ordonneeCoord Coordonnée y de la cellule
   */
  Cell(int abscisseCoord, int ordonneeCoord) {
    this.abscisseCoord = abscisseCoord;
    this.ordonneeCoord = ordonneeCoord;
    east = true;
    north = true;
    south = true;
    west = true;
    visited = false;
  }

  /**
   * Calcule la distance entre la cellule courante et celle passée en paramètre.
   *
   * @param cell Cellule à laquelle on veut calculer la distance
   * @return Distance entre les deux cellules
   */
  public double distance(Cell cell) {
    return Math.sqrt(Math.pow(cell.abscisseCoord - abscisseCoord, 2) + Math.pow(cell.ordonneeCoord
        - ordonneeCoord, 2));
  }

  /**
   * Supprime un mur de la cellule.
   *
   * @param direction Direction du mur à supprimer
   */
  public void removeWall(char direction) {
    if (direction == 'N') {
      north = false;
    } else if (direction == 'E') {
      east = false;
    } else if (direction == 'S') {
      south = false;
    } else if (direction == 'W') {
      west = false;
    }
  }

  /**
   * Ajoute un mur à la cellule.
   *
   * @param direction Direction du mur à ajouter
   */
  public void addWall(char direction) {
    if (direction == 'N') {
      north = true;
    } else if (direction == 'E') {
      east = true;
    } else if (direction == 'S') {
      south = true;
    } else if (direction == 'W') {
      west = true;
    }
  }

  /**
   * Retourne la valeur d'un mur.
   *
   * @param direction Direction du mur
   * @return Valeur du mur
   */
  public Boolean getWall(char direction) {
    if (direction == 'N') {
      return north;
    } else if (direction == 'E') {
      return east;
    } else if (direction == 'S') {
      return south;
    } else if (direction == 'W') {
      return west;
    }
    return false;
  }

  /**
   * Retourne si la cellule a un mur au nord.
   *
   * @return Si la cellule a un mur au nord
   */
  public Boolean northWall() {
    return north;
  }

  /**
   * Retourne si la cellule a un mur à l'est.
   *
   * @return Si la cellule a un mur à l'est
   */
  public Boolean eastWall() {
    return east;
  }

  /**
   * Retourne si la cellule a un mur au sud.
   *
   * @return Si la cellule a un mur au sud
   */
  public Boolean southWall() {
    return south;
  }

  /**
   * Retourne si la cellule a un mur à l'ouest.
   *
   * @return Si la cellule a un mur à l'ouest
   */
  public Boolean westWall() {
    return west;
  }

  /**
   * Retourne les coordonnées de la cellule.
   *
   * @return Coordonnées de la cellule
   */
  @Override
  public String toString() {
    return "(" + abscisseCoord + "," + ordonneeCoord + ")";
  }

  public int getX() {
    return abscisseCoord;
  }

  public int getY() {
    return ordonneeCoord;
  }
}
