package application;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Main extends Application implements EventHandler<ActionEvent>{
	
	private FoodData foods = new FoodData();
	private ObservableList<String> items1 = FXCollections.observableArrayList();
	private ObservableList<String> items2 = FXCollections.observableArrayList();
	private ListView<String> list1 = new ListView<String>();
	private ListView<String> list2 = new ListView<String>();
	private Label label1 = new Label("Current Food List");
	private Label label2 = new Label("Current Meal List");
	private Label label3 = new Label("Number of Food: ");
	private VBox vbox = new VBox();
	private Button btn1 = new Button("Load Additional Food");
	private Button btn2 = new Button("Add Food");
	private Button btn3 = new Button("Name Filter");
	private Button btn4 = new Button("Nutrient Filter");
	private Button btn5 = new Button("Save");
	private Button btn6 = new Button("Anzlyze Meal");
	private Button btn7 = new Button("Add to Meal List");
	private Button btn8 = new Button("Remove");
	private ArrayList <String> uploadList = new ArrayList<String> ();
	private List <FoodItem> mealList = new ArrayList<FoodItem> ();

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene (root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("DEMO");
			scene.setFill(Color.WHITE);
			
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
							 if (file != null) {
								 userTextField.setText(file.toString());
							 }
						 }
					 });
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent x) {
							if (!userTextField.getText().isEmpty()) {
								foods.loadFoodItems(userTextField.getText());
								uploadList = convert(foods.getAllFoodItems());
								items1 = FXCollections.observableArrayList(uploadList);
								list1.setItems(items1);
							}

							dialog.close();
							// TODO
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
					 
					 Label warn = new Label("Invalid input");
					 Text scenetitle = new Text("ADD FOOD");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 load.add(scenetitle, 0, 0, 2, 1);
					 Label id = new Label("ID (24 digits):");
		       		 load.add(id, 0, 1);
					 TextField userTextField1 = new TextField();
					 load.add(userTextField1, 1, 1);
					 Label name = new Label("Name:");
		       		 load.add(name, 0, 2);
					 TextField userTextField2 = new TextField();
					 load.add(userTextField2, 1, 2);
					 Label calorie = new Label("Calories:");
		       		 load.add(calorie, 0, 3);
					 TextField userTextField3 = new TextField();
					 load.add(userTextField3, 1, 3);
					 Label fat = new Label("Fat:");
		       		 load.add(fat, 0, 4);
					 TextField userTextField4 = new TextField();
					 load.add(userTextField4, 1, 4);
					 Label carb = new Label("Carbohydrate:");
		       		 load.add(carb, 0, 5);
					 TextField userTextField5 = new TextField();
					 load.add(userTextField5, 1, 5);
					 Label fiber = new Label("Fiber:");
		       		 load.add(fiber, 0, 6);
					 TextField userTextField6 = new TextField();
					 load.add(userTextField6, 1, 6);
					 Label protein = new Label("Protein:");
		       		 load.add(protein, 0, 7);
					 TextField userTextField7 = new TextField();
					 load.add(userTextField7, 1, 7);
					 Button upload = new Button ("UPLOAD");
					 load.add(upload, 0, 8);
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 String id1 = userTextField1.getText();
							 String name1 = userTextField2.getText();
							 FoodItem newFood = null;
							 if (name1 != null && id1 != null && id1.length() == 24) {
								 try{
									 newFood = new FoodItem(id1, name1);
									 newFood.addNutrient("calories", Double.parseDouble(userTextField3.getText()));
									 newFood.addNutrient("fat", Double.parseDouble(userTextField4.getText()));
									 newFood.addNutrient("carbohydrate", Double.parseDouble(userTextField5.getText()));
									 newFood.addNutrient("fiber", Double.parseDouble(userTextField6.getText()));
									 newFood.addNutrient("protein", Double.parseDouble(userTextField7.getText()));
									 foods.addFoodItem(newFood);
									 dialog.close();
								 } catch (Exception e) {
									 load.add(warn, 1, 8);
								 }
							 } else {
								 load.add(warn, 1, 8);
							 }
							 update (foods.getAllFoodItems());
						 }
					 });
					 Scene dialogScene = new Scene(load, 400, 400);
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
							 final Stage panel = new Stage();
							 panel.initModality(Modality.APPLICATION_MODAL);
							 panel.initOwner(primaryStage);
							 VBox box = new VBox();
							 Button addButton = new Button("Add to list");
							 ListView<String> nameSearch = new ListView<>();
							 nameSearch.setMinHeight(500);
							 nameSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
							 
							 String name = userTextField.getText();
							 List<FoodItem> qualified = sort(foods.filterByName(name));
							 nameSearch.setItems(FXCollections.observableArrayList(convert(qualified)));
							 
							 box.getChildren().addAll(nameSearch,addButton);
							 box.setAlignment(Pos.TOP_CENTER);
							 Insets margin = new Insets(0,0,25,0);
							 box.setMargin(nameSearch, margin);
							 
							 addButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									ObservableList<Integer> index = nameSearch.getSelectionModel().getSelectedIndices();
									for (int i = 0; i < index.size(); ++i) {
										mealList.add(qualified.get(index.get(i)));
									}
									mealList = sort(mealList);
									items2 = FXCollections.observableArrayList(convert(mealList));
									list2.setItems(items2);
									panel.close();
									dialog.close();
								}
							 });
							 Scene searchList = new Scene(box,400,600);
							 panel.setScene(searchList);
							 panel.show();
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
						Button upload = new Button("SEARCH");
						load.add(upload, 0, 1);
						Button upload2 = new Button("ADD RULE");
						load.add(upload2, 1, 1);
						upload2.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent x) {
								final Stage popUp = new Stage();
								popUp.initModality(Modality.APPLICATION_MODAL);
								popUp.initOwner(dialog);
								GridPane load1 = new GridPane();
								load1.setAlignment(Pos.CENTER);
								load1.setHgap(10);
								load1.setVgap(10);
								load1.setPadding(new Insets(0, 0, 0, 0));
								Label nutrientL = new Label("Nutrient");
								load1.add(nutrientL, 0, 0);
								Label comparatorL = new Label("Comparator");
								load1.add(comparatorL, 1, 0);
								Label numL = new Label("Number");
								load1.add(numL, 2, 0);
								ObservableList<String> nutrient = 
									    FXCollections.observableArrayList(
									        "calories",
									        "fat",
									        "carbohydrate",
									        "fiber",
									        "protein"
									    );
								ObservableList<String> comparator = 
									    FXCollections.observableArrayList(
									        ">=",
									        "<=",
									        "=="
									    );
								final ComboBox<String> combo1 = new ComboBox<String>(nutrient);
								final ComboBox<String> combo2 = new ComboBox<String>(comparator);
								TextField number = new TextField();
								load1.add(combo1, 0, 1);
								load1.add(combo2, 1, 1);
								load1.add(number, 2, 1);
								Button submit = new Button ("ADD RULE");
								load1.add(submit, 2, 2);
								Scene newScene = new Scene(load1, 400, 200);
								popUp.setScene(newScene);
								popUp.show();
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
					 final Stage dialog = new Stage();
					 dialog.initModality(Modality.APPLICATION_MODAL);
					 dialog.initOwner(primaryStage);
					 GridPane load = new GridPane();
					 load.setAlignment(Pos.CENTER);
					 load.setHgap(10);
					 load.setVgap(10);
					 load.setPadding(new Insets(0, 0, 0, 0));
					 Label fileName = new Label("Save As:");
					 Label location = new Label("Where");
		       		 load.add(fileName, 0, 1);
		       		 load.add(location, 0, 2);
					 
		       		 TextField userTextField = new TextField();
					 load.add(userTextField, 1, 1);
					 TextField fileLocation = new TextField();
					 load.add(fileLocation, 1, 2);
					 
					 Button file = new Button ("Browse");
					 Button upload = new Button ("SAVE");
					 load.add(file, 1, 3);
					 load.add(upload, 1, 4);
					 file.setOnAction(new EventHandler<ActionEvent>() {
						 public void handle(ActionEvent x) {
							 DirectoryChooser fileChooser = new DirectoryChooser();
							 fileChooser.setTitle("Open Resource File");
							 File file = fileChooser.showDialog(dialog);
							 if (file != null) {
								 fileLocation.setText(file.toString());
							 }
						 }
					 });
					 upload.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent x) {
							if (!userTextField.getText().isEmpty()) {
								String path = "";
								if(!fileLocation.getText().isEmpty()) {
									path = fileLocation.getText() + "/" + userTextField.getText();
								} else {
									path = fileLocation.getText() + "/foodData.txt";
								}
								foods.saveFoodItems(path);
								dialog.close();
							}

							dialog.close();
							// TODO
						}
					 });
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
//					 foods.saveFoodItems();
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
					 Text scenetitle = new Text("Nutritional Information Summary");
					 scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
					 
					 Double calories = 0d;
					 Double fat = 0d;
					 Double carbohydrate = 0d;
					 Double fiber = 0d;
					 Double protein = 0d;
					 
					 for (FoodItem food: mealList) {
						 calories += food.getNutrientValue("calories");
						 fat += food.getNutrientValue("fat");
						 carbohydrate += food.getNutrientValue("carbohydrate");
						 fiber += food.getNutrientValue("fiber");
						 protein += food.getNutrientValue("protein");
					 }
					 
					 Text Cal = new Text(String.format("Total energy:    %.2f Cal", calories));
					 Text Fat = new Text(String.format("Total fat:    %.2f g", fat));
					 Text Carbo = new Text(String.format("Total carbohydrate:    %.2f g", carbohydrate));
					 Text Fiber = new Text(String.format("Total fiber:    %.2f g", fiber));
					 Text Protein = new Text(String.format("Total protein:    %.2f g", protein));

					 load.add(scenetitle, 0, 0);
					 load.add(Cal, 0, 1);
					 load.add(Fat, 0, 2);
					 load.add(Carbo, 0, 3);
					 load.add(Fiber, 0, 4);
					 load.add(Protein, 0, 5);
					 
					 Scene dialogScene = new Scene(load, 300, 200);
		             dialog.setScene(dialogScene);
		             dialog.show();
				 }
			 });
			 btn7.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 ObservableList<Integer> index = list1.getSelectionModel().getSelectedIndices();
					 for (int i = 0; i < index.size(); ++i) {
						 mealList.add(foods.getAllFoodItems().get(index.get(i)));
					 }
					 mealList = sort(mealList);
					 items2 = FXCollections.observableArrayList(convert(mealList));
					 list2.setItems(items2);
				 }
				
			 });
			 btn8.setOnAction(new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent e) {
					 ObservableList<Integer> index = list2.getSelectionModel().getSelectedIndices();
						for (int i = index.size() - 1; i > -1; i--) {
							mealList.remove((int)index.get(i));
						}
						mealList = sort(mealList);
						
						items2 = FXCollections.observableArrayList(convert(mealList));
						list2.setItems(items2);
				 } 
			});
			
			list1.setItems(items1);
			list1.setPrefWidth(200);
			list1.setPrefHeight(400);
			list1.setTranslateX(50);
			list1.setTranslateY(50);
			list1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			list2.setItems(items2);
			list2.setPrefWidth(200);
			list2.setPrefHeight(400);
			list2.setTranslateX(550);
			list2.setTranslateY(50);
			list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
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
			btn6.setMinWidth(vbox.getPrefWidth()/2);
			btn6.setTranslateX(550);
			btn6.setTranslateY(450);
			
			btn7.setMinHeight(vbox.getPrefHeight());
			btn7.setMinWidth(vbox.getPrefWidth());
			btn7.setTranslateX(50);
			btn7.setTranslateY(450);
			
			btn8.setMinHeight(vbox.getPrefHeight());
			btn8.setMinWidth(vbox.getPrefWidth()/2);
			btn8.setTranslateX(650);
			btn8.setTranslateY(450);
			
			root.getChildren().addAll(list1, list2
					, label1, label2, label3, btn1
					, btn2, btn3, btn4, btn5, btn6
					, btn7, btn8);

			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> convert(java.util.List<FoodItem> list) {
		ArrayList<String> foodList = new ArrayList<>(); 
		for (int i = 0; i < list.size(); i++) {
			foodList.add(list.get(i).getName());
		}
			 
		 return foodList;
	 }
	
	private List<FoodItem> sort (java.util.List<FoodItem> list) {
		List<FoodItem> sortedList = new ArrayList<>();
		 for (int i = 0; i < list.size(); i++) {
			 FoodItem curFood = list.get(i);
			 boolean notDone = true;
			 for (int j = 0; j < sortedList.size(); j++) {
				 if(curFood.getName().toLowerCase()
						 .compareTo(sortedList.get(j).getName().toLowerCase()) < 0) {
					 sortedList.add(j, curFood);
					 notDone = false;
					 break;
				 }
			 }
			 if (notDone) {sortedList.add(curFood);}
		 }
		 return sortedList;
	 }
	
	private void update (java.util.List<FoodItem> list) {
		ObservableList<String> updateList = FXCollections.observableArrayList(convert(list));
		list1.setItems(updateList);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}