import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private Stage stageRef;
    private Scene grid;
    private boolean isGameOver;
    private Canvas gameCanvas;
    private Snake snake;
    private int tileSize = 40;
    private static int colSize = 700/40;
    private static int rowSize = 1240/40;
    private GraphicsContext gc;
    private ArrayList<Point> fruitCords;
    private Image snakeHeadImgRight = new Image("file:resources/images/SnakeHeadRight.png");
    private Image snakeHeadImgLeft = new Image("file:resources/images/SnakeHeadLeft.png");
    private Image snakeHeadImgDown = new Image("file:resources/images/SnakeHeadDown.png");
    private Image snakeHeadImgUp = new Image("file:resources/images/SnakeHeadUp.png");
    private int level;
    private VBox sceneRoot;
    private boolean isPaused;
    private int highScore;
    private int timerVal;

    public Grid(Stage stage){
        stageRef = stage;
        //Grid Layout
        Font sFont = Font.loadFont("file:resources/fonts/ArcadeFont.ttf", 45);
        Canvas gridCanvas = new Canvas(1240, 700);
        gameCanvas = gridCanvas;
        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(sFont);
        scoreLabel.setPadding(new Insets(10,10,10,10));
        Label levelLabel = new Label("Level: 1");
        levelLabel.setFont(sFont);
        levelLabel.setPadding(new Insets(10,10,10,10));
        Label timerLabel = new Label("Timer: 30");
        timerLabel.setFont(sFont);
        timerLabel.setPadding(new Insets(10,10,10,10));
        HBox infoBar = new HBox(scoreLabel, levelLabel, timerLabel);
        infoBar.setAlignment(Pos.CENTER);
        infoBar.setPrefWidth(800);
        infoBar.setPrefHeight(60);
        infoBar.setStyle("-fx-background-color: #808080;" +
                "-fx-border-width: 5;" +
                "-fx-border-color: white;"
        );
        VBox rootNode = new VBox(infoBar, gridCanvas);
        rootNode.setPrefSize(1280, 800);
        rootNode.setStyle("-fx-background-color: black;" +
                "-fx-border-width: 20;" +
                "-fx-border-color: black;"
        );
        rootNode.setAlignment(Pos.BASELINE_CENTER);
        Scene gridScene = new Scene(rootNode);
        sceneRoot = rootNode;
        grid = gridScene;
        gc = gridCanvas.getGraphicsContext2D();
        Snake gameSnake = new Snake();
        snake = gameSnake;

        fruitCords = generateFruitCords(1);
        level = 1;
        timerVal = 30;
        startGame();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame kF = new KeyFrame(Duration.millis(level * 300), event->{startGame();});
        timeline.getKeyFrames().add(kF);
        timeline.play();
        Timeline timerTimeline = new Timeline();
        timerTimeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame timerKF = new KeyFrame(Duration.seconds(1), e->{
            if(isGameOver){
                timerTimeline.stop();
            }
            if(timerVal == 0){
                timeline.stop();
                timerTimeline.stop();
                timeline.getKeyFrames().clear();
                snake = new Snake();
                if(level == 1){
                    timerVal = 30;
                    fruitCords = generateFruitCords(2);
                    updateLevelLbl(2);
                    updateTimerLbl(Integer.toString(timerVal));
                    level = 2;
                    KeyFrame newGameFrame = new KeyFrame(Duration.millis(200), event->{startGame();});
                    timeline.getKeyFrames().add(newGameFrame);
                    timeline.play();
                    timerTimeline.playFromStart();
                    startGame();
                } else if(level == 2){
                    fruitCords = generateFruitCords(3);
                    updateLevelLbl(3);
                    updateTimerLbl("Infinity");
                    level = 3;
                    KeyFrame newGameFrame = new KeyFrame(Duration.millis(100), event->{startGame();});
                    timeline.getKeyFrames().add(newGameFrame);
                    timeline.play();
                    startGame();
                }
            } else {
                updateTimerLbl(Integer.toString(timerVal));
                timerVal--;
            }
        });
        timerTimeline.getKeyFrames().add(timerKF);
        timerTimeline.play();

        //Start Game
        gridCanvas.setFocusTraversable(true);
        gridCanvas.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case DIGIT1:
                    timerVal = 30;
                    snake = new Snake();
                    fruitCords = generateFruitCords(1);
                    updateLevelLbl(1);
                    level = 1;
                    timeline.stop();
                    timeline.getKeyFrames().clear();
                    KeyFrame newKF = new KeyFrame(Duration.millis(300), event->{startGame();});
                    timeline.getKeyFrames().add(newKF);
                    timeline.play();
                    timerTimeline.playFromStart();
                    startGame();
                    break;
                case DIGIT2:
                    timerVal = 30;
                    snake = new Snake();
                    fruitCords = generateFruitCords(2);
                    updateLevelLbl(2);
                    level = 2;
                    timeline.stop();
                    timeline.getKeyFrames().clear();
                    newKF = new KeyFrame(Duration.millis(200), event->{startGame();});
                    timeline.getKeyFrames().add(newKF);
                    timeline.play();
                    timerTimeline.playFromStart();
                    startGame();
                    break;
                case DIGIT3:
                    snake = new Snake();
                    fruitCords = generateFruitCords(3);
                    updateLevelLbl(3);
                    updateTimerLbl("Infinity");
                    level = 3;
                    timeline.stop();
                    timeline.getKeyFrames().clear();
                    newKF = new KeyFrame(Duration.millis(100), event->{startGame();});
                    timeline.getKeyFrames().add(newKF);
                    timeline.play();
                    startGame();
                    break;
                case RIGHT:
                    if(snake.getSnakeDir() == Snake.Direction.UP){
                        snake.setSnakeDir(Snake.Direction.RIGHT);
                    } else if(snake.getSnakeDir() == Snake.Direction.DOWN){
                        snake.setSnakeDir(Snake.Direction.LEFT);
                    } else if(snake.getSnakeDir() == Snake.Direction.RIGHT){
                        snake.setSnakeDir(Snake.Direction.DOWN);
                    } else if(snake.getSnakeDir() == Snake.Direction.LEFT){
                        snake.setSnakeDir(Snake.Direction.UP);
                    }
                    break;
                case LEFT:
                    if(snake.getSnakeDir() == Snake.Direction.UP){
                        snake.setSnakeDir(Snake.Direction.LEFT);
                    } else if(snake.getSnakeDir() == Snake.Direction.DOWN){
                        snake.setSnakeDir(Snake.Direction.RIGHT);
                    } else if(snake.getSnakeDir() == Snake.Direction.RIGHT){
                        snake.setSnakeDir(Snake.Direction.UP);
                    } else if(snake.getSnakeDir() == Snake.Direction.LEFT){
                        snake.setSnakeDir(Snake.Direction.DOWN);
                    }
                    break;
                case P:
                    if(isPaused){
                        timeline.play();
                        timerTimeline.play();
                        isPaused = false;
                    } else {
                        timeline.pause();
                        timerTimeline.pause();
                        isPaused = true;
                    }
                    break;
                case R:
                    SplashPage splash = new SplashPage(stageRef);
                    Scene splashScene = splash.getSplashPage();
                    stageRef.setScene(splashScene);
                    break;
                case Q:
                    isGameOver = true;
            }
        });
    }

    private void updateScore(){
        Node infoBar = sceneRoot.getChildren().get(0);
        if(infoBar instanceof HBox){
            Node scoreLbl = ((HBox) infoBar).getChildren().get(0);
            if(scoreLbl instanceof Label){
                ((Label) scoreLbl).setText("Score: " + snake.getScore());
            }
        }
    }

    private void updateLevelLbl(int level){
        Node infoBar = sceneRoot.getChildren().get(0);
        if(infoBar instanceof HBox){
            Node scoreLbl = ((HBox) infoBar).getChildren().get(1);
            if(scoreLbl instanceof Label){
                ((Label) scoreLbl).setText("Level: " + level);
            }
        }
    }

    private void updateTimerLbl(String newTime){
        Node infoBar = sceneRoot.getChildren().get(0);
        if(infoBar instanceof HBox){
            Node timerLbl = ((HBox) infoBar).getChildren().get(2);
            if(timerLbl instanceof Label){
                ((Label) timerLbl).setText("Timer: " + newTime);
            }
        }
    }

    private void startGame(){
        if(snake.checkLoss() || isGameOver){
            //End Game
            gc.setFill(Color.web("Red"));
            Font endFont = Font.loadFont("file:resources/fonts/ArcadeFont.ttf", 80);
            gc.setFont(endFont);
            gc.fillText("GAME OVER", 1240/2 - 150, 700/2);
            gc.fillText("High Score:" + highScore, 1240/2 - 150, 700/2 + 200);
            if(snake.getScore() > highScore){
                highScore = snake.getScore();
            }
            return;
        }
        checkFruitNum();
        updateScore();
        moveSnake();
        drawGrid(gc);
    }

    private void checkFruitNum(){
        Random rand = new Random();
        int diff = 0;
        if(fruitCords.size() < 5 && level == 1){
            diff = 5 - fruitCords.size();
        } else if(fruitCords.size() < 10 && level == 2){
            diff = 10 - fruitCords.size();
        } else if(fruitCords.size() < 15 && level == 3){
            diff = 15 - fruitCords.size();
        }
        if(diff == 0){
            return;
        } else {
            for(int i = 0; i < diff; i++){
                Point fruitCord = new Point(rand.nextInt(rowSize), rand.nextInt(colSize), false);
                fruitCords.add(fruitCord);
            }
        }
    }

    private void moveSnake(){
        //Move Snake
        snake.translateSnake(fruitCords);
    }



    private void drawGrid(GraphicsContext gc){
        gc.clearRect(0,0, gameCanvas.getWidth(), gameCanvas.getHeight());
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                if((i+ j) % 2 == 0){
                    gc.setFill(Color.web("#32CD32"));
                } else {
                    gc.setFill(Color.web("#006400"));
                }
                gc.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
        spawnFruit();

        //Draw Snake
        ArrayList<Point> snakeArr = snake.getSnake();
        for(int i = 0; i < snakeArr.size(); i++){
            Point snakePoint = snakeArr.get(i);
            if(snakePoint.isHead() && snake.getSnakeDir() == Snake.Direction.RIGHT){
                gc.drawImage(snakeHeadImgRight, snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize + 5, tileSize + 5);
            } else if(snakePoint.isHead() && snake.getSnakeDir() == Snake.Direction.LEFT){
                gc.drawImage(snakeHeadImgLeft, snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize + 5, tileSize + 5);
            } else if(snakePoint.isHead() && snake.getSnakeDir() == Snake.Direction.UP){
                gc.drawImage(snakeHeadImgUp, snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize + 5, tileSize + 5);
            } else if(snakePoint.isHead() && snake.getSnakeDir() == Snake.Direction.DOWN){
                gc.drawImage(snakeHeadImgDown, snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize + 5, tileSize + 5);
            } else {
                gc.setFill(Color.web("black"));
                gc.strokeOval(snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize+ 5, tileSize);
                gc.fillOval(snakePoint.getX()*tileSize, snakePoint.getY()*tileSize, tileSize + 5, tileSize);
            }
        }

    }

    private ArrayList<Point> generateFruitCords(int level){
        Random rand = new Random();
        int numFruit = 5;
        ArrayList<Point> fruitCords = new ArrayList();
        if(level == 1){
            numFruit = 5;
        } else if(level == 2){
            numFruit = 10;
        } else if(level == 3){
            numFruit = 15;
        }
        for(int i = 0; i < numFruit; i++){
            Point fruitCord = new Point(rand.nextInt(rowSize), rand.nextInt(colSize), false);
            fruitCords.add(fruitCord);
        }
        return fruitCords;
    }

    private void spawnFruit(){
        Image foodImg = new Image("file:resources/images/SnakeApple.png");
        for(int i = 0; i < fruitCords.size(); i++){
            Point currFruit = fruitCords.get(i);
            gc.drawImage(foodImg, currFruit.getX()*tileSize, currFruit.getY()*tileSize, tileSize , tileSize);
        }

    }

    public Scene getGrid(){
        return grid;
    }

}
