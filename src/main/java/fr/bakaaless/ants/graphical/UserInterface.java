package fr.bakaaless.ants.graphical;

import fr.bakaaless.ants.Ant;
import fr.bakaaless.ants.Pheromone;
import fr.bakaaless.ants.Simulator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserInterface extends Application {

    public static void startInterface(final String... args) {
        launch(args);
    }

    private Scene scene;
    private GraphicsContext context;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final StackPane pane = new StackPane();

        final Canvas canvas = new Canvas(Simulator.WIDTH, Simulator.HEIGHT);

        pane.getChildren().add(canvas);

        primaryStage.setScene(new Scene(pane, Simulator.WIDTH, Simulator.HEIGHT));
        primaryStage.setTitle("Random");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.centerOnScreen();

        this.context = canvas.getGraphicsContext2D();

        final Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(50), event -> this.render()));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        primaryStage.setOnCloseRequest(event -> timeLine.stop());
    }

    public void render() {

        Simulator.POINTS.forEach(Ant::updateVelocity);
        Simulator.POINTS.forEach(Ant::addPheromone);

        this.context.setFill(Color.BLACK);
        this.context.fillRect(0, 0, Simulator.WIDTH, Simulator.HEIGHT);

        Simulator.PHEROMONES.removeIf(pheromone -> {
            final long timeLeft = pheromone.apparitionTime() + Pheromone.MAX_AGE - System.currentTimeMillis();
            if (timeLeft < 0) {
                return true;
            }
            final double radius = 4;
            if (pheromone.type() == Pheromone.PheromoneType.HOME)
                this.context.setFill(Color.color(0.0f, 0.0f, 0.5019608f, timeLeft / (Pheromone.MAX_AGE + 0f)));
            else
                this.context.setFill(Color.color(1.0f, 0.0f, 0.0f, timeLeft / (Pheromone.MAX_AGE + 0f)));
            this.context.fillOval(pheromone.position().getX() - radius / 2, pheromone.position().getY() - radius / 2, radius, radius);
            return false;
        });

        Simulator.FOODS.forEach(food -> {
            final double radius = 6;
            this.context.setFill(Color.GREEN);
            this.context.fillOval(food.getPosition().getX() - radius / 2, food.getPosition().getY() - radius / 2, radius, radius);
        });

        Simulator.POINTS.forEach(point -> {
            double radius = 12;
            if (point.isHungry())
                this.context.setFill(Color.WHITE);
            else
                this.context.setFill(Color.LIMEGREEN);
//            if (point.getPosition().getX() < - radius || point.getPosition().getX() > 500 + radius || point.getPosition().getY() < - radius || point.getPosition().getY() > 500 + radius)
//                return;
            this.context.fillOval(point.getPosition().getX() - radius / 2, point.getPosition().getY() - radius / 2, radius, radius);
//           if (!point.isHungry()) {
//               for (int i = -1; i < 2; i++) {
//                   radius = 15;
//                   final Vector2D amplifier = point.getVelocity().clone().rotateAroundZ(Math.PI / 5 * i).normalized().multiply(20);
//                   final Vector2D sensor = point.getPosition().clone().add(amplifier);
//                   this.context.setFill(Color.YELLOW);
//                   this.context.fillOval(sensor.getX() - radius / 2, sensor.getY() - radius / 2, radius, radius);
//               }
//           }
        });

        this.context.setFill(Color.PAPAYAWHIP);
        this.context.fillRect(Simulator.BASE.position().getX() - 10, Simulator.BASE.position().getY() - 10, 20, 20);

    }
}
