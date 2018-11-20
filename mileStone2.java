package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene (root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("DEMO");
			scene.setFill(Color.WHITE);
			
			ListView<String> list1 = new ListView<String>();
			ListView<String> list2 = new ListView<String>();
			Label label1 = new Label("Current Food List");
			Label label2 = new Label("Current Meal List");
			Label label3 = new Label("Number of Food: (Replace with actual list)");
			VBox vbox = new VBox();
			Button btn1 = new Button("Load Additional Food");
			Button btn2 = new Button("Add Food");
			Button btn3 = new Button("Name Filter");
			Button btn4 = new Button("Nutrient Filter");
			Button btn5 = new Button("Save");
			Button btn6 = new Button("Anzlyze Meal");
			
			//replace with actual food list
			ObservableList<String> items1 =FXCollections.observableArrayList (
			    "Apple", "Beans", "Chicken", "Danelion Green", "Eggs", "Flax Seed"
			    , "Garlic", "Hominy", "Ice Cream", "Jicama", "Kale", "Lemon Juice", 
			    "Millet", "Nuts", "Olive Oil", "Pineapple", "Quinoa", "Raspberries", 
			    "Salmon", "Tomatoes", "Umami", "Vinegar", "Wild Rice", "Xigua", 
			    "Yogurt", "Zucchini");
			ObservableList<String> items2 =FXCollections.observableArrayList (
				"Apple", "Beans", "Chicken", "Danelion Green", "Eggs", "Flax Seed"
				, "Garlic", "Hominy");
			 btn1.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Text scenetitle = new Text("Load Additional Food");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 Label userName = new Label("File:");
		       		 load.add(userName, 0, 1);
					 TextField userTextField = new TextField();
					 load.add(userTextField, 1, 1);
					 Button file = new Button ("Choose File");
					 Button upload = new Button ("UPLOAD");
					 load.add(file, 1, 2);
					 load.add(upload, 1, 3);
					 file.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 FileChooser fileChooser = new FileChooser();
							 fileChooser.setTitle("Open Resource File");
							 File file = fileChooser.showOpenDialog(dialog);
							 userTextField.setText(file.toString());
							 //TODO
						 }
					 });
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 dialog.close();
							 //TODO
						 }
					 });
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			
			 btn2.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Text scenetitle = new Text("ADD FOOD");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 TextField userTextField = new TextField();
					 load.add(userTextField, 0, 1);
					 Button upload = new Button ("UPLOAD");
					 load.add(upload, 0, 2);
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 //TODO
							 dialog.close();
						 }
					 });
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			 btn3.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Text scenetitle = new Text("Name Filter");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 TextField userTextField = new TextField();
					 load.add(userTextField, 0, 1);
					 Button upload = new Button ("SEARCH");
					 load.add(upload, 0, 2);
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 //TODO
							 dialog.close();
						 }
					 });
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			 btn4.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Text scenetitle = new Text("Nutrient Filter");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 TextField userTextField = new TextField();
					 load.add(userTextField, 0, 1);
					 Button upload = new Button ("SEARCH");
					 load.add(upload, 0, 2);
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 //TODO
							 dialog.close();
						 }
					 });
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			 btn5.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 //TODO
				 }
			 });
			 btn6.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Text scenetitle = new Text("NUTRIENT ANAYLZE: UNAVAILABLE IN DEMO");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			
			
			list1.setItems(items1);
			list1.setPrefWidth(200);
			list1.setPrefHeight(450);
			list1.setTranslateX(50);
			list1.setTranslateY(50);
			
			list2.setItems(items2);
			list2.setPrefWidth(200);
			list2.setPrefHeight(400);
			list2.setTranslateX(550);
			list2.setTranslateY(50);
			
			label1.setTranslateX(50);
			label1.setTranslateY(30);
			
			label2.setTranslateX(550);
			label2.setTranslateY(30);
			
			label3.setTranslateX(50);
			label3.setTranslateY(510);
			
			vbox.setPrefSize(200, 50);
			btn1.setMinHeight(vbox.getPrefHeight());
			btn1.setMinWidth(vbox.getPrefWidth());
			btn1.setTranslateX(300);
			btn1.setTranslateY(50);
			
			btn2.setMinHeight(vbox.getPrefHeight());
			btn2.setMinWidth(vbox.getPrefWidth());
			btn2.setTranslateX(300);
			btn2.setTranslateY(150);
			
			btn3.setMinHeight(vbox.getPrefHeight());
			btn3.setMinWidth(vbox.getPrefWidth());
			btn3.setTranslateX(300);
			btn3.setTranslateY(250);
			
			btn4.setMinHeight(vbox.getPrefHeight());
			btn4.setMinWidth(vbox.getPrefWidth());
			btn4.setTranslateX(300);
			btn4.setTranslateY(350);
			
			btn5.setMinHeight(vbox.getPrefHeight());
			btn5.setMinWidth(vbox.getPrefWidth());
			btn5.setTranslateX(300);
			btn5.setTranslateY(450);
			
			btn6.setMinHeight(vbox.getPrefHeight());
			btn6.setMinWidth(vbox.getPrefWidth());
			btn6.setTranslateX(550);
			btn6.setTranslateY(450);
			
			root.getChildren().addAll(list1, list2
					, label1, label2, label3, btn1
					, btn2, btn3, btn4, btn5, btn6);

			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
