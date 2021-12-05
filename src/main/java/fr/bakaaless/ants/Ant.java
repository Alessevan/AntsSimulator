package fr.bakaaless.ants;

public class Ant {

    private static final double SPEED = 1.5;
    public static final double INTERACT = 14;
    public static final double SIGHT = 60;

    private final Vector2D position;
    private final Vector2D velocity;
    private int step;
    private boolean hungry;

    private Targetable target;

    public Ant(final Vector2D position, final Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
        this.hungry = true;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getVelocity() {
        return this.velocity;
    }

    public boolean isHungry() {
        return this.hungry;
    }

    public void setHungry(final boolean hungry) {
        this.hungry = hungry;
    }

    public Targetable getTarget() {
        return this.target;
    }

    public void setTarget(final Targetable target) {
        this.target = target;
    }

    public void updateVelocity() {
        update();
        if (target == null) {
            int pheromoneStrength = -2;
            double density = -1;
            for (int i = -1; i < 2; i++) {
                final Vector2D sensor = this.position.clone().add(this.velocity.clone().rotateAroundZ(Math.PI / 5 * i).normalized().multiply(20));
                double localDensity = 0;
                for (final Pheromone pheromone : Simulator.PHEROMONES) {
                    if (pheromone.type() == Pheromone.PheromoneType.FOOD && this.hungry || pheromone.type() == Pheromone.PheromoneType.HOME && !this.hungry) {
                        final Vector2D vector = pheromone.getPosition().clone().subtract(sensor);
                        if (vector.lengthSquared() < SIGHT / 2) {
                            final long timeLeft = pheromone.apparitionTime() + Pheromone.MAX_AGE - System.currentTimeMillis();
                            localDensity += timeLeft / (Pheromone.MAX_AGE + 0d);
                        }
                    }
                }
                if (localDensity > density) {
                    density = localDensity;
                    pheromoneStrength = i;
                }
            }
            if (density > 0)
                this.velocity.rotateAroundZ(Math.PI / 8 * pheromoneStrength);
            this.velocity.add((Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, 1);
        }
        else {
            this.velocity.set(this.target.getPosition().clone().subtract(this.position).add((Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, 1), 0.5, 1).normalized();
        }
    }

    public void update() {
        this.position.add(this.velocity.clone().multiply(SPEED));
        double distance = Double.MAX_VALUE;
        Food target = null;
        Food toRemove = null;
        for (final Food food : Simulator.FOODS) {
            final Vector2D vector = food.getPosition().clone().subtract(this.position);
            if (this.hungry && this.target == food && vector.lengthSquared() < INTERACT) {
                this.hungry = false;
                for (final Ant ants : Simulator.POINTS) {
                    if (ants.getTarget() == food) {
                        ants.setTarget(null);
                    }
                }
                toRemove = food;
                this.velocity.rotateAroundZ(Math.PI);
                break;
            } else if (this.hungry && distance > vector.length()) {
                if (this.position.clone().subtract(food.getPosition()).lengthSquared() < SIGHT && vector.dot(this.velocity) > 0) {
                    target = food;
                    distance = vector.length();
                }
            }
        }
        if (this.hungry && target != null && (this.target == null || distance < this.target.getPosition().clone().subtract(this.position).length())) {
            this.target = target;
        }
        if (toRemove != null)
            Simulator.FOODS.remove(toRemove);
            if (!this.hungry && this.position.clone().subtract(Simulator.BASE.position()).lengthSquared() < SIGHT) {
                this.target = Simulator.BASE;
            }
        if (this.position.clone().subtract(Simulator.BASE.position()).lengthSquared() < INTERACT + 4 && !this.hungry) {
            this.hungry = true;
            if (this.target == Simulator.BASE)
                this.target = null;
            this.velocity.rotateAroundZ(Math.PI);
        }
    }

    public void addPheromone() {
        if (step++ % 11 == 0) {
            if (this.hungry)
                Simulator.PHEROMONES.add(new Pheromone(this.position.clone(), Pheromone.PheromoneType.HOME, System.currentTimeMillis()));
            else
                Simulator.PHEROMONES.add(new Pheromone(this.position.clone(), Pheromone.PheromoneType.FOOD, System.currentTimeMillis()));
        }
    }
}
