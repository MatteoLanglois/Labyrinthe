package com.example.defilabyrinthe;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Classe gérant l'affichage du labyrinthe.
 */
public class LabyrinthFx extends Application {

  /**
   * Largeur du labyrinthe.
   */
  private static final int width = 20;

  /**
   * Hauteur du labyrinthe.
   */
  private static final int height = 20;

  /**
   * Taille des cellules du labyrinthe.
   */
  private static int cell_size;

  /**
   * GridPane représentant le labyrinthe.
   */
  private static GridPane grid;

  /**
   * Label affichant la longueur du trajet.
   */
  private static final Label longueurTrajet = new Label();

  /**
   * Générateur de labyrinthe.
   */
  private static MazeGenerator generator = new MazeGenerator(width, height);

  /**
   * HBox contenant le labyrinthe et les boutons.
   */
  private static HBox root = new HBox();

  /**
   * Méthode principale de l'application.
   *
   * @param stage the primary stage for this application, onto which the application scene
   *              can be set. Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  @Override
  public void start(Stage stage) {
    cell_size = 30;
    grid = generator.getGrid();

    generator.updateGrid(cell_size);

    VBox vbox = new VBox();

    Button generateNewMaze = new Button("Generate new maze");
    Button solveMaze = new Button("Solve maze");
    vbox.getChildren().add(generateNewMaze);
    vbox.getChildren().add(solveMaze);
    vbox.getChildren().add(longueurTrajet);

    root.getChildren().add(vbox);
    root.getChildren().add(grid);

    generateNewMaze.setOnAction(e -> {
      generator = new MazeGenerator(width, height);
      longueurTrajet.setText("");
      root.getChildren().remove(grid);
      grid = generator.updateGrid(cell_size);
      root.getChildren().add(grid);
    });

    solveMaze.setOnAction(e -> {
      Graphe graphe = generator.getGraphe();
      double dist = graphe.aaEtoile(generator.getEntrance(), generator.getExit());
      longueurTrajet.setText("Longueur du trajet : \n" + dist + " blocs");
      boolean oneByOne = false;
      root.getChildren().remove(grid);
      if (oneByOne) {
        updateGridOneByOne(graphe.getChemin(generator.getEntrance(), generator.getExit()),
            cell_size, root);
      } else {
        grid = generator.updateGrid(graphe.getChemin(generator.getEntrance(), generator.getExit()),
            cell_size);
        root.getChildren().add(grid);
      }
    });



    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

  }

  /**
   * Met à jour le labyrinthe cellule par cellule.
   *
   * @param path Chemin à suivre
   * @param cellSize Taille des cellules
   * @param scene HBox contenant le labyrinthe
   */
  private static void updateGridOneByOne(ArrayList<Cell> path, int cellSize, HBox scene) {
    Collections.reverse(path);
    try {
      for (Cell cell : path) {
        scene.getChildren().remove(grid);
        grid = generator.updateOneCell(cell.getX(), cell.getY(),
            cellSize, Color.BLUE);
        scene.getChildren().add(grid);
        Thread.sleep(100);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Méthode principale.
   *
   * @param args Arguments de la ligne de commande
   */
  public static void main(String[] args) {
    launch();
  }

}