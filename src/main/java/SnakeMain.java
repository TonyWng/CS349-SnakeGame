import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class SnakeMain extends Application {
    public static void main(String[] args){
        System.out.println("Main");
        launch();
    }

    @Override
    public void init() throws Exception{
        super.init();
        System.out.println("INIT");
    }

    @Override
    public void start(Stage stage){
        SplashPage splash = new SplashPage(stage);
        Scene splashScene = splash.getSplashPage();
        stage.setHeight(800);
        stage.setWidth(1280);
        stage.setScene(splashScene);
//        Grid gameGrid = new Grid();
//        stage.setScene(gameGrid.getGrid());
        stage.setResizable(false);
        stage.setTitle("Tony's Snake Game");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("STOP");
    }
}