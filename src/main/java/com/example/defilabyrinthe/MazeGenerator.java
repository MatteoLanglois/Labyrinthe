package com.example.defilabyrinthe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Classe générant le labyrinthe.
 */
public class MazeGenerator {

  /**
   * Longueur du labyrinthe.
   */
  private int width;

  /**
   * Largeur du labyrinthe.
   */
  private int height;

  /**
   * Tableau de cellules représentant le labyrinthe.
   */
  private Cell[][] maze;

  /**
   * Générateur de nombres aléatoires.
   */
  private Random ran = new Random();

  /**
   * Tableau de directions.
   */
  private final char[] directions = {'N', 'E', 'S', 'W'};

  /**
   * Graphe représentant le labyrinthe.
   */
  private Graphe graphe;

  /**
   * GridPane représentant le labyrinthe pour l'affichage.
   */
  private GridPane grid;

  /**
   * Cellule d'entrée du labyrinthe.
   */
  private Cell entrance;

  /**
   * Cellule de sortie du labyrinthe.
   */
  private Cell exit;

  /**
   * Constructeur de la classe MazeGenerator. Initialise le labyrinthe et le graphe.
   *
   * @param width Largeur du labyrinthe
   * @param height Hauteur du labyrinthe
   */
  MazeGenerator(int width, int height) {
    this.width = width;
    this.height = height;
    maze = new Cell[width][height];
    graphe = new Graphe();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        maze[i][j] = new Cell(i, j);
        graphe.ajouterNoeud(maze[i][j]);
      }
    }
    grid = new GridPane();
    entrance = maze[0][0];
    exit = maze[width - 1][height - 1];
    generate();
  }


  /**
   * Génère le labyrinthe.
   *
   * @see MazeGenerator#carve_passage_from(int, int)
   * @see MazeGenerator#removeRandomWalls()
   * @see MazeGenerator#updateGraphe()
   */
  public void generate() {
    carve_passage_from(ran.nextInt(width - 1), ran.nextInt(height - 1));
    removeRandomWalls();
    updateGraphe();
  }

  /**
   * Enlève aléatoirement des murs dans le labyrinthe.
   *
   * @see MazeGenerator#generate()
   */
  private void removeRandomWalls() {
    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        for (char direction : directions) {
          if (maze[y][x].getWall(direction)) {
            if (ran.nextInt(100) < 10) {
              maze[y][x].removeWall(direction);
              if (direction == 'N' && y > 0) {
                maze[y - 1][x].removeWall('S');
              } else if (direction == 'E' && x < width - 1) {
                maze[y][x + 1].removeWall('W');
              } else if (direction == 'S' && y < height - 1) {
                maze[y + 1][x].removeWall('N');
              } else if (direction == 'W' && x > 0) {
                maze[y][x - 1].removeWall('E');
              }
            }
          }
        }
      }
    }
  }

  /**
   * Algorithme de génération du labyrinthe.
   *
   * @param x Coordonnée x de la cellule à partir de laquelle on génère le labyrinthe
   * @param y Coordonnée y de la cellule à partir de laquelle on génère le labyrinthe
   */
  private void carve_passage_from(int x, int y) {
    ArrayList<Character> directions = new ArrayList<>();
    directions.add('N');
    directions.add('E');
    directions.add('S');
    directions.add('W');
    Collections.shuffle(directions);
    for (char direction : directions) {
      int newX = x;
      int newY = y;
      if (direction == 'N') {
        newY -= 1;
      } else if (direction == 'E') {
        newX += 1;
      } else if (direction == 'S') {
        newY += 1;
      } else if (direction == 'W') {
        newX -= 1;
      }
      if (newX >= 0 && newX < width && newY >= 0 && newY < height
          && !maze[newY][newX].visited) {
        maze[y][x].removeWall(direction);
        maze[newY][newX].removeWall(getOppositeDirection(direction));
        maze[newY][newX].visited = true;
        carve_passage_from(newX, newY);
      }
    }
  }

  /**
   * Retourne la direction opposée à celle passée en paramètre.
   *
   * @param direction (N, S, E, W)
   * @return Direction opposée
   */
  private static char getOppositeDirection(char direction) {
    if (direction == 'N') {
      return 'S';
    } else if (direction == 'E') {
      return 'W';
    } else if (direction == 'S') {
      return 'N';
    } else if (direction == 'W') {
      return 'E';
    }
    return ' ';
  }

  /**
   * Met à jour le graphe représentant le labyrinthe.
   *
   * @see MazeGenerator#generate()
   */
  public void updateGraphe() {
    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        Cell cell = maze[y][x];
        if (!cell.northWall() && y > 0) {
          graphe.ajouterArete(cell, maze[y - 1][x]);
        }
        if (!cell.eastWall() && x < width - 1) {
          graphe.ajouterArete(cell, maze[y][x + 1]);
        }
        if (!cell.southWall() && y < height - 1) {
          graphe.ajouterArete(cell, maze[y + 1][x]);
        }
        if (!cell.westWall() && x > 0) {
          graphe.ajouterArete(cell, maze[y][x - 1]);
        }
      }
    }
  }

  /**
   * Getter permettant de récupérer l'entrée du labyrinthe.
   *
   * @return Cellule représentant l'entrée du labyrinthe
   */
  public Cell getEntrance() {
    return entrance;
  }

  /**
   * Getter permettant de récupérer la sortie du labyrinthe.
   *
   * @return Cellule représentant la sortie du labyrinthe
   */
  public Cell getExit() {
    return exit;
  }

  /**
   * Getter permettant de récupérer le graphe représentant le labyrinthe.
   *
   * @return Graphe représentant le labyrinthe
   */
  public Graphe getGraphe() {
    return graphe;
  }

  /**
   * Méthode permettant de mettre à jour la grille représentant le labyrinthe pour l'affichage.
   *
   * @param cellSize Taille d'une cellule
   * @return Grille représentant le labyrinthe
   */
  public GridPane updateGrid(int cellSize) {
    for (int y = 0; y < maze.length; y++) {
      for (int x = 0; x < maze[y].length; x++) {
        Pane square = showWalls(x, y, cellSize);
        grid.add(square, x, y);
      }
    }
    updateOneCell(entrance.getX(), entrance.getY(), cellSize, Color.GREEN);
    updateOneCell(exit.getX(), exit.getY(), cellSize, Color.RED);
    return grid;
  }

  /**
   * Méthode permettant de mettre à jour la grille représentant le labyrinthe pour l'affichage.
   * Cette méthode permet de mettre en évidence le chemin trouvé par l'algorithme de recherche.
   *
   * @param path Chemin trouvé par l'algorithme de recherche
   * @param cellSize Taille d'une cellule
   * @return Grille représentant le labyrinthe
   */
  public GridPane updateGrid(ArrayList<Cell> path, int cellSize) {
    grid = updateGrid(cellSize);
    for (Cell cell : path) {
      updateOneCell(cell.getX(), cell.getY(), cellSize, Color.LIGHTBLUE);
    }

    updateOneCell(entrance.getX(), entrance.getY(), cellSize, Color.GREEN);
    updateOneCell(exit.getX(), exit.getY(), cellSize, Color.RED);
    return grid;
  }

  /**
   * Méthode permettant de mettre à jour une cellule de la grille représentant le labyrinthe.
   *
   * @param x Coordonnée x de la cellule
   * @param y Coordonnée y de la cellule
   * @param cellSize Taille d'une cellule
   * @param color Couleur de la cellule
   * @return Grille représentant le labyrinthe
   */
  public GridPane updateOneCell(int x, int y, int cellSize, Color color) {
    Pane square = showWalls(y, x, cellSize);
    square.setStyle("-fx-background-color: " + color.toString().replace("0x", "#") + ";");

    grid.add(square, y, x);
    return grid;
  }

  /**
   * Méthode permettant de mettre les murs d'une cellule de la grille représentant le labyrinthe.
   *
   * @param x Coordonnée x de la cellule
   * @param y Coordonnée y de la cellule
   * @param cellSize Taille d'une cellule
   * @return Cellule représentant le labyrinthe
   */
  public Pane showWalls(int x, int y, int cellSize) {
    Pane square = new Pane();
    square.getChildren().clear();
    square.setPrefSize(cellSize, cellSize);
    Line wall = new Line();
    wall.setStroke(Color.DARKGRAY);

    if (x == 0 || maze[y][x].westWall()) {
      // Mur à gauche
      wall = new Line(1, 1, 1, cellSize - 1);
      square.getChildren().add(wall);
    }
    if (x == maze[0].length - 1 || maze[y][x].eastWall()) {
      // Mur à droite
      wall = new Line(cellSize - 1, 1, cellSize - 1, cellSize - 1);
      square.getChildren().add(wall);
    }
    if (y == 0 || maze[y][x].northWall()) {
      // Mur du dessus
      wall = new Line(1, 1, cellSize - 1, 1);
      square.getChildren().add(wall);
    }
    if (y == maze.length - 1 || maze[y][x].southWall()) {
      // Mur en dessous
      wall = new Line(1, cellSize - 1, cellSize - 1, cellSize - 1);
      square.getChildren().add(wall);
    }
    return square;
  }

  /**
   * Getter permettant de récupérer la grille représentant le labyrinthe.
   *
   * @return Grille représentant le labyrinthe
   */
  public GridPane getGrid() {
    return grid;
  }

}