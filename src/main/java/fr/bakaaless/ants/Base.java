package fr.bakaaless.ants;

public record Base(Vector2D position) implements Targetable {

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

}
