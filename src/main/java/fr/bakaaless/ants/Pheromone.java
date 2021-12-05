package fr.bakaaless.ants;

public record Pheromone(Vector2D position,
                        PheromoneType type,
                        long apparitionTime) implements Targetable {

    public static long MAX_AGE = 20000;

    @Override
    public Vector2D getPosition() {
        return position();
    }

    public enum PheromoneType {
        FOOD,
        HOME
    }

}
