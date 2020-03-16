package festivalPlanner.gui.gui_views;

import festivalPlanner.data_system.Data;
import festivalPlanner.gui.gui_controllers.NPCController;
import festivalPlanner.gui.gui_controllers.TimelineScrollBar;
import festivalPlanner.simulation.Map;
import festivalPlanner.simulation.Visitor;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import org.jfree.fx.FXGraphics2D;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class SimulationView extends StackPane {

    private Data data;
    private Canvas canvas;
    private FXGraphics2D graphics;

    private TimelineScrollBar scrollBar;
    private Map map;
    private ArrayList<Visitor> visitors;
    private BufferedImage image;
    private AffineTransform affineTransform;
    private NPCController npcController;

    public static double mouseX;
    public static double mouseY;


    public SimulationView(Data data) {
        this.data = data;
        whats();

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics);
            }
        }.start();
    }


    public void whats(){
        setWidth(1920);
        setHeight(1080);
        map = new Map("proftaakmap.json");
        this.affineTransform = new AffineTransform();
        this.canvas = new Canvas();

        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        this.canvas.setHeight(1080);
        this.canvas.setWidth(1920);


        this.map.drawMap(graphics);

        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

        File file = new File("map.png");

        try {
           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

           this.image = ImageIO.read(getClass().getResource("map.png"));

        } catch (IOException e) {
        }

        this.scrollBar = new TimelineScrollBar(canvas);
        this.npcController = new NPCController(scrollBar,data);
        getChildren().addAll(canvas,scrollBar.makehbox());
        this.visitors = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            this.visitors.add(new Visitor(new Point2D.Double(10, 10)));
        }
        for (Visitor visitor : this.visitors) {
            visitor.setOtherVisitors(this.visitors);
        }

    }


    public void update(double deltatime){
        for (Visitor visitor : this.visitors) {
            visitor.update(this.canvas);
        }
        canvas.setOnMouseMoved(event -> {
            this.mouseX = event.getX();
            this.mouseY = event.getY();
        });
        this.scrollBar.update();
        if(this.scrollBar.getTimeMinutes() % 60 == 0){
            npcController.update();
        }

    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.setBackground(Color.WHITE);

        graphics.drawImage(this.image,affineTransform,null);

        for (Visitor visitor : this.visitors) {
            visitor.draw(graphics);
        }

        this.scrollBar.draw(graphics);


    }


}
