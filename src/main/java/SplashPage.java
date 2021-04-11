import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashPage {

    private Scene SplashPage;
    private Stage stageRef;

    public SplashPage(Stage stage){
        stageRef = stage;
        Font font = Font.loadFont("file:resources/fonts/ArcadeFont.ttf", 60);
        Font sFont = Font.loadFont("file:resources/fonts/ArcadeFont.ttf", 35);
        Font snakeFont = Font.loadFont("file:resources/fonts/ArcadeFont.ttf", 70);

        //Instruction Box
        Label instructText = new Label("INSTRUCTIONS");
        instructText.setFont(sFont);
        Text instruct1 = new Text("-> Use the arrow keys to turn the snake");
        instruct1.setFont(sFont);
        Text instruct2 = new Text("-> P to pause/unpause");
        instruct2.setFont(sFont);
        Text instruct3 = new Text("-> R to reset to splash screen");
        instruct3.setFont(sFont);
        Text instruct4 = new Text("-> 1 to start level 1");
        instruct4.setFont(sFont);
        Text instruct5 = new Text("-> 2 to start level 2");
        instruct5.setFont(sFont);
        Text instruct6 = new Text("-> 3 to start level 3");
        instruct6.setFont(sFont);
        Text instruct7 = new Text("-> Q to quit and display high score");
        instruct7.setFont(sFont);

        VBox instructBox = new VBox(instructText, instruct1, instruct2, instruct3, instruct4, instruct5, instruct6, instruct7);
        instructBox.setAlignment(Pos.CENTER_LEFT);
        instructBox.setMaxSize(instructBox.USE_PREF_SIZE, instructBox.USE_PREF_SIZE);
        instructBox.setStyle("-fx-border-width: 2;" +
                "-fx-border-color: black;" +
                "-fx-border-radius: 3;" +
                "-fx-padding: 0 40 0 40;"
        );

        //Game Rules Box
        Label ruleText = new Label("Rules");
        ruleText.setFont(sFont);
        Text rule1 = new Text("-> The objective is to collect coins,");
        rule1.setFont(sFont);
        Text rule2 = new Text("   as you collect coins, your snake");
        rule2.setFont(sFont);
        Text extraLine1 = new Text("   will grow");
        extraLine1.setFont(sFont);
        Text rule3 = new Text("-> If your snake hits any of the");
        rule3.setFont(sFont);
        Text rule4 = new Text("   walls or itself, you lose");
        rule4.setFont(sFont);
        Text rule5 = new Text("-> As the levels progress, the speed");
        rule5.setFont(sFont);
        Text rule6 = new Text("   of your snake increases");
        rule6.setFont(sFont);
        Text rule7 = new Text("-> Level 1 & 2 both have a 30 sec timer");
        rule7.setFont(sFont);

        VBox ruleBox = new VBox(ruleText, rule1, rule2, extraLine1, rule3, rule4, rule5, rule6, rule7);
        ruleBox.setAlignment(Pos.CENTER_LEFT);
        ruleBox.setMaxSize(instructBox.USE_PREF_SIZE, ruleBox.USE_PREF_SIZE);
        ruleBox.setStyle("-fx-border-width: 2;" +
                "-fx-border-color: black;" +
                "-fx-border-radius: 3;" +
                "-fx-padding: 0 40 0 40;"
        );

        Label nameLabel = new Label("Tony Wang");
        Label idLabel = new Label("r438wang - 20779459");
        Label snake = new Label("SNAKE");
        snake.setFont(snakeFont);
        snake.setTextFill(Color.web("#00FF00"));
        idLabel.setFont(font);
        nameLabel.setFont(font);

        Button startButton = new Button("Click Here to Start!");
        startButton.setFont(sFont);
        startButton.setStyle("-fx-background-color: #00FF00;" +
                "-fx-background-radius: 15px;"
        );

        startButton.setOnAction(event -> {
            Grid gameGrid = new Grid(stage);
            stageRef.setScene(gameGrid.getGrid());
//            gameGrid.drawGrid();
        });

        VBox menuBox = new VBox(nameLabel, idLabel, snake, instructBox, ruleBox, startButton);
        menuBox.setSpacing(10);
        menuBox.setAlignment(Pos.TOP_CENTER);
        Scene splashScene = new Scene(menuBox, 1280, 800);
        SplashPage = splashScene;
    }

    public Scene getSplashPage(){
        return SplashPage;
    }

}
