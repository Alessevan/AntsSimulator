package fr.bakaaless.ants;

import fr.bakaaless.ants.graphical.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public static final double WIDTH = 1090;
    public static final double HEIGHT = 1000;

    public static final Base BASE = new Base(new Vector2D(WIDTH / 2, HEIGHT / 2));
    public static final List<Ant> POINTS = new ArrayList<>();
    public static final List<Pheromone> PHEROMONES = new ArrayList<>();
    public static final List<Food> FOODS = new ArrayList<>();

    public static void main(final String... args) {
        for (int index = 0; index < 150; index++) {
            POINTS.add(new Ant(BASE.position().clone(), new Vector2D((Math.random() - 0.5) * 2, (Math.random() - 0.5) * 2).normalized()));
        }

        for (int index = 0; index < 250; index++) {
            FOODS.add(new Food(new Vector2D(WIDTH * 3/4, HEIGHT * 3/7).add(new Vector2D((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100).normalized().multiply(Math.random() * 50))));
        }
        for (int index = 0; index < 250; index++) {
            FOODS.add(new Food(new Vector2D(WIDTH * 1/4, HEIGHT * 2/7).add(new Vector2D((Math.random() - 0.5) * 100, (Math.random() - 0.5) * 100).normalized().multiply(Math.random() * 50))));
        }
        UserInterface.startInterface(args);
    }

}
