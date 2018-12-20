/**
 * Filename:   Main.java
 * Project:    Team Project
 * Authors:    Debra Deppeler, Xudong Tang, Yixian Gan, 
 *			Yiye Dang, Daoxing Zhang, Qiuhong Li
 * Emails:     xtang75@wisc.edu, ygan23@wisc.edu, dang6@wisc.edu, 
 *			dzhang268@wisc.edu, qli288@wisc.edu
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    lec001
 * 
 * Due Date:   Before 10pm on November 30, 2018
 * Version:    1.0
 * 
 * Credits:    N/A
 * 
 * Bugs:       no known bugs
 */

package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.darkprograms.speech.translator.GoogleTranslate;

//import com.google.api.services.translate
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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

/**
 * This class 
 * 
 * 
 * 
 * 
 * 
 * @author Xudong Tang (xtang75@wisc.edu)
 * @author Yixian Gan (ygan23@wisc.edu)
 * @author Yiye Dang (dang6@wisc.edu)
 * @author Daoxing Zhang (dzhang268@wisc.edu)
 * @author Qiuhong Li (qli288@wisc.edu)
 * 
 */
public class Main extends Application{
	//initialize all the variables
	
	//creates food items that stored in an arrayList.
	private FoodData foods = new FoodData();
	
	//list that change dynamically
	private ObservableList<String> items1 = FXCollections.observableArrayList();
	private ObservableList<String> items2 = FXCollections.observableArrayList();
	
	//listView for display
	private ListView<String> list1 = new ListView<String>();
	private ListView<String> list2 = new ListView<String>();
	
	//creates list of food items for meallist and filter and rule
	private List<FoodItem> mealList = new ArrayList<FoodItem>();
	private List<FoodItem> filterNut = new ArrayList<FoodItem>();
	private List<String> rule = new ArrayList<String>();
	private Label label3 = new Label("Number of Food: ");
	private HashMap<String, String> a = new HashMap<String, String> ();
	private List<Button> allButton = new ArrayList <Button> ();
	private List<Label> allLabel = new ArrayList <Label> ();
	
	//set up specific int for designing scenes
	private final int SMALL_POPUP_WIDTH = 300;
	private final int SMALL_POPUP_HEIGHT = 200;
	private final int BIG_POPUP_WIDTH = 400;
	private final int BIG_POPUP_HEIGHT = 600;

	/**
	 * This method builds the GUI controls,including 
	 * creating buttons, layout and scenes. It also creates 
	 * lists for button, food, and meal, and specifies the size,
	 * height, margin and alignment for the lists
	 * 
	 * @param primaryStage the original stage 
	 */
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		//set names for each button
		Button loadFoodButton = new Button("Load Additional Food");
		allButton.add(loadFoodButton);
		Button addFoodButton = new Button("Add Food");
		allButton.add(addFoodButton);
		Button nameFilterButton = new Button("Name Filter");
		allButton.add(nameFilterButton);
		Button nutrientFilterButton = new Button("Nutrient Filter");
		allButton.add(nutrientFilterButton);
		Button saveButton = new Button("Save");
		allButton.add(saveButton);
		Button analyzeButton = new Button("Anzlyze");
		allButton.add(analyzeButton);
		Button addToMealButton = new Button("Add to Meal List");
		allButton.add(addToMealButton);
		Button removeButton = new Button("Remove");
		allButton.add(removeButton);
		Button helpButton = new Button("Help");
		allButton.add(helpButton);
		Button otherFunction = new Button("Other");
		allButton.add(otherFunction);
		
		Label label1 = new Label("Current Food List");
		allLabel.add(label1);
		Label label2 = new Label("Current Meal List");
		allLabel.add(label2);
		allLabel.add(label3);
		VBox vboxButton = new VBox();
		VBox vboxFoodList = new VBox();
		VBox vboxMealList = new VBox();
		BorderPane mainPane = new BorderPane();

		try {
			//set up the main stage
			StackPane root = new StackPane();
			root.setId("pane");
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("FOOD QUERY");
			//scene.setFill(Color.WHITE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// create Buttons
			createLoadFoodButton(primaryStage, loadFoodButton);
			createAddFoodButton(primaryStage, addFoodButton);
			createNameFilterButton(primaryStage, nameFilterButton);
			createNutrientFilterButton(primaryStage, nutrientFilterButton);
			createSaveButton(primaryStage, saveButton);
			createAnalyzeButton(primaryStage, analyzeButton);
			createAddToMealButton(addToMealButton);
			createRemoveButton(removeButton);
			createHelpButton(primaryStage, helpButton);
			createOtherFuncButton(primaryStage, otherFunction);
			
			// list view layout
			list1.setItems(items1);
			list1.setMaxWidth(200);
			list1.setPrefHeight(400);
			list1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			list2.setItems(items2);
			list2.setMaxWidth(200);
			list2.setPrefHeight(400);
			list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			// Button size set up
			vboxButton.setPadding(new Insets(30, 0, 0, 0));
			vboxButton.setSpacing(40);
			vboxButton.setPrefSize(200, 50);
			vboxButton.setAlignment(Pos.CENTER);
			vboxButton.getChildren().addAll(loadFoodButton, addFoodButton, 
					nameFilterButton, nutrientFilterButton, saveButton);

			// food list
			vboxFoodList.setPrefWidth(200);
			vboxFoodList.setPadding(new Insets(25, 50, 0, 50));
			vboxFoodList.setAlignment(Pos.CENTER_LEFT);
			vboxMealList.setMargin(label3, new Insets(5, 0, 0, 0));
			vboxFoodList.getChildren().addAll(label1, list1, addToMealButton, label3);

			// meal list
			vboxMealList.setPrefWidth(200);
			vboxMealList.setPadding(new Insets(50, 50, 0, 50));
			vboxMealList.setAlignment(Pos.CENTER_RIGHT);
			vboxMealList.setMargin(helpButton, new Insets(20, 0, 0, 0));
			vboxMealList.setMargin(otherFunction, new Insets(-32, 100, 0, 0));
			vboxMealList.getChildren().addAll(label2, list2, 
					new HBox(analyzeButton, removeButton), helpButton, otherFunction);

			// main pane
			mainPane.setLeft(vboxFoodList);
			mainPane.setCenter(vboxButton);
			mainPane.setRight(vboxMealList);
			
			a.put("Russian", "ru");
			a.put("Chinese", "zh-CN");
			a.put("French", "fr");
			a.put("Hindi", "hi");
			a.put("German", "de");
			a.put("Japanese", "ja");
			a.put("Korean", "ko");
			a.put("Hebrew", "iw");
			
			
			
			root.getChildren().addAll(mainPane);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates Button in the purpose of helping. 
	 * When clicking the help button, it will pop up a help
	 * scence for user
	 * 
	 * @param primaryStage the original stage
	 * @param helpButton the button for help
	 */
	private void createHelpButton(Stage primaryStage, Button helpButton) {
		setSize(helpButton, 100, 25);
		helpButton.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		helpButton.setOnAction(x -> {
			//set up the pop up window
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			Scene helpScene = new Scene(new Group());
			VBox root = new VBox();
			final ImageView selectedImage = new ImageView();
			try {
				//load the image for the help button
				Image image1 = new Image(new FileInputStream("help.jpg"));
				selectedImage.setImage(image1);
				root.getChildren().addAll(selectedImage);
				helpScene.setRoot(root);
				dialog.setScene(helpScene);
				dialog.show();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
	}

	/**
	 * This method creates button to remove food from
	 * current meal list
	 * 
	 * @param removeButton the button use to remove thing
	 */
	private void createRemoveButton(Button removeButton) {
		setSize(removeButton, 100, 50);
		
		//The button's action, which is invoked whenever the button is fired.
		removeButton.setOnAction(x -> {
			ObservableList<Integer> index = list2.getSelectionModel().getSelectedIndices();
			//remove the chosen food
			for (int i = index.size() - 1; i > -1; i--) {
				mealList.remove((int) index.get(i));
			}
			//sort the meal list again
			mealList = sort(mealList);
			// put the new meal list in the list view
			items2 = FXCollections.observableArrayList(convert(mealList));
			list2.setItems(items2);
		});
	}
	

	/**
	 * This method creates button that adds food to meal list
	 * 
	 * @param addToMealButton the button to adds food
	 */
	private void createAddToMealButton(Button addToMealButton) {
		setSize(addToMealButton, 200, 50);
		addToMealButton.setOnAction(x -> {
			// get the selected item's index
			ObservableList<Integer> index = list1.getSelectionModel().getSelectedIndices();
			//add to the meal list
			for (int i = 0; i < index.size(); ++i) {
				mealList.add(foods.getAllFoodItems().get(index.get(i)));
			}
			//sort and display in the list view
			mealList = sort(mealList);
			items2 = FXCollections.observableArrayList(convert(mealList));
			list2.setItems(items2);
		});
	}

	/**
	 * This method creates button to analyze the food items
	 * in the meal list
	 * 
	 * @param primaryStage the original stage
	 * @param analyzeButton the button use to analyze food when clicked
	 */
	private void createAnalyzeButton(Stage primaryStage, Button analyzeButton) {
		setSize(analyzeButton, 100, 50);
		analyzeButton.setOnAction(x -> {
			// set up the pop up window
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Nutritional Information Summary", 1, 1);

			double[] nutrient = new double[] { 0d, 0d, 0d, 0d, 0d };

			//gets nutrient elements of each food item in meal list
			for (FoodItem food : mealList) {
				nutrient[0] += food.getNutrientValue("calories");
				nutrient[1] += food.getNutrientValue("fat");
				nutrient[2] += food.getNutrientValue("carbohydrate");
				nutrient[3] += food.getNutrientValue("fiber");
				nutrient[4] += food.getNutrientValue("protein");
			}

			//complete analyzing sentence
			Text[] text = new Text[5];
			text[0] = new Text(String.format("Total energy:    %.2f Cal", nutrient[0]));
			text[1] = new Text(String.format("Total fat:    %.2f g", nutrient[1]));
			text[2] = new Text(String.format("Total carbohydrate:    %.2f g", nutrient[2]));
			text[3] = new Text(String.format("Total fiber:    %.2f g", nutrient[3]));
			text[4] = new Text(String.format("Total protein:    %.2f g", nutrient[4]));

			for (int i = 0; i < text.length; i++) {
				load.add(text[i], 0, i+1);
			}

			Scene dialogScene = new Scene(load, SMALL_POPUP_WIDTH, SMALL_POPUP_HEIGHT);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * This method creates button that uses to save the current
	 * food list into the local directory
	 * 
	 * @param primaryStage the original stage
	 * @param saveButton the button uses to save food list when clicked
	 */
	private void createSaveButton(Stage primaryStage, Button saveButton) {
		setSize(saveButton, 200, 50);
		saveButton.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		
		//handling save Button events
		saveButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Save file", 1, 1);

			//set label to display non-editable text to the user
			Label fileName = new Label("Filename");
			Label location = new Label("Where");
			gridSetCol(load, new Node[] {fileName, location }, 0);

			//use textfiled to get input from the user
			TextField userTextField = new TextField();
			TextField fileLocation = new TextField();
			Button file = new Button("Browse");
			Button upload = new Button("SAVE");
			gridSetCol(load, new Node[] { userTextField, fileLocation, file, upload }, 1);

			//handling file event
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
			
			//handling upload event
			upload.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					if (!userTextField.getText().isEmpty()) {
						String path = "";
						if (!fileLocation.getText().isEmpty()) {
							path = fileLocation.getText() + "/" + userTextField.getText();
						} else {
							path = fileLocation.getText() + "/foodData.txt";
						}
						foods.saveFoodItems(path);
						dialog.close();
					}

					dialog.close();
				}
			});
			Scene dialogScene = new Scene(load, SMALL_POPUP_WIDTH, SMALL_POPUP_HEIGHT);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * This method creates button that uses to filter the 
	 * food items by nutrient, according to 
	 * the rule list. And then add to the meal list.
	 * The filter process includes filter, upload, 
	 * sumit and addButton events
	 * 
	 * @param primaryStage the original stage
	 * @param nutrientFilterBUtton the button that filters the nutrients when clicked
	 */
	@SuppressWarnings("static-access")
	private void createNutrientFilterButton(Stage primaryStage, Button nutrientFilterButton) {
		setSize(nutrientFilterButton, 200, 50);
		
		//handling nutrient-filter event
		nutrientFilterButton.setOnAction(x -> {
			rule = new ArrayList<String>();
			filterNut = new ArrayList<FoodItem>();

			// setup stage
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			// layouts
			BorderPane trail = new BorderPane();
			trail.setPrefSize(300, 200);
			HBox buttons = new HBox();

			//display the list of rule
			ListView<String> rulesList = new ListView<>();
			rulesList.setMinWidth(80);
			Text scenetitle = new Text("Nutrient Filter");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			Button upload = new Button("SEARCH");
			Button upload2 = new Button("ADD RULE");

			// borderPane
			ObservableList<String> showRule = FXCollections.observableArrayList(rule);
			rulesList.setItems(showRule);
			Label currentRule = new Label("Current Rules");
			buttons.getChildren().addAll(upload, upload2);
			buttons.setAlignment(Pos.CENTER);
			buttons.setMargin(upload, new Insets(15, 30, 15, 30));
			buttons.setMargin(upload2, new Insets(15, 30, 15, 30));

			//set panel layout
			trail.setTop(scenetitle);
			trail.setBottom(buttons);
			trail.setLeft(currentRule);
			trail.setCenter(rulesList);
			trail.setMargin(currentRule, new Insets(0, 10, 0, 10));
			trail.setMargin(rulesList, new Insets(20, 20, 0, 0));
			trail.setMargin(scenetitle, new Insets(20, 0, 0, 0));
			trail.setAlignment(rulesList, Pos.CENTER);
			trail.setAlignment(currentRule, Pos.CENTER);
			trail.setAlignment(scenetitle, Pos.CENTER);

			//handling upload event
			upload2.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					final Stage popUp = new Stage();
					popUp.initModality(Modality.APPLICATION_MODAL);
					popUp.initOwner(dialog);
					GridPane load1 = new GridPane();
					gridSetUp(load1, "", 1, 1);

					//display nutrient list when file changes
					ObservableList<String> nutrient = FXCollections.observableArrayList("calories", "fat",
							"carbohydrate", "fiber", "protein");
					ObservableList<String> comparator = FXCollections.observableArrayList(">=", "<=", "==");
					final ComboBox<String> combo1 = new ComboBox<String>(nutrient);
					final ComboBox<String> combo2 = new ComboBox<String>(comparator);

					//use label to display text
					Label nutrientL = new Label("Nutrients");
					Label comparatorL = new Label("Comparator");
					Label numL = new Label("Number");
					TextField number = new TextField();
					Button submit = new Button("ADD RULE");
					Label warn = new Label("invalid input");

					//set the layout of grid panel
					gridSetCol(load1, new Node[] { nutrientL, combo1 }, 0);
					gridSetCol(load1, new Node[] { comparatorL, combo2 }, 1);
					gridSetCol(load1, new Node[] { numL, number, submit }, 2);

					//handling sumit event
					submit.setOnAction(y -> {
						try {
							//set and add rules into rule list
							String curRule = null;
							curRule = combo1.getValue() + " " + 
									combo2.getValue() + " " + number.getText();
							Double.parseDouble(number.getText());
							rule.add(curRule);
							ObservableList<String> showRule = FXCollections.observableArrayList(rule);
							rulesList.setItems(showRule);
							popUp.close();
						} catch (NumberFormatException e2) {
							try {
								// warning message for invalid input
								warn.setTextFill(Color.RED);
								load1.add(warn, 2, 4);
							} catch (IllegalArgumentException e3) {

							}
						} catch (IllegalArgumentException e1) {
							popUp.close();
						}
					});

					Scene newScene = new Scene(load1, SMALL_POPUP_WIDTH + 100, SMALL_POPUP_HEIGHT);
					popUp.setScene(newScene);
					popUp.show();
				}
			});

			upload.setOnAction(y -> {
				//set up the pop up window
				final Stage panel = new Stage();
				panel.initModality(Modality.APPLICATION_MODAL);
				panel.initOwner(dialog);
				Button addButton = new Button("Add to list");
				VBox box = new VBox();
				ListView<String> nutSearch = new ListView<>();
				try {
					// list of food filtered 
					filterNut = foods.filterByNutrients(rule);
					nutSearch.setMinHeight(500);
					// enable multiple selection
					nutSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					// put the filtered list in the list view
					nutSearch.setItems(FXCollections.observableArrayList(convert(filterNut)));
					box.getChildren().addAll(nutSearch, addButton);
					box.setAlignment(Pos.TOP_CENTER);
					Insets margin = new Insets(0, 0, 25, 0);
					box.setMargin(nutSearch, margin);
				} catch (Exception e1) {
					nutSearch.setItems(FXCollections.observableArrayList("No Match Found"));
				}

				// add the food filtered to the meal list
				addButton.setOnAction(z -> {
					ObservableList<Integer> index = nutSearch.getSelectionModel().getSelectedIndices();
					for (int i = 0; i < index.size(); ++i) {
						mealList.add(filterNut.get(index.get(i)));
					}
					mealList = sort(mealList);
					items2 = FXCollections.observableArrayList(convert(mealList));
					list2.setItems(items2);
					panel.close();
					dialog.close();
				});
				Scene searchList = new Scene(box, BIG_POPUP_WIDTH, BIG_POPUP_HEIGHT);
				panel.setScene(searchList);
				panel.show();
			});
			Scene dialogScene = new Scene(trail, SMALL_POPUP_WIDTH, SMALL_POPUP_HEIGHT);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * This method create buttons that filters the food items 
	 * in meal list by food names, and then adds to meal list
	 * 
	 * @param primaryStage the original stage 
	 * @param nameFileterButton the button uses to filter food when clicked
	 */
	@SuppressWarnings("static-access")
	private void createNameFilterButton(Stage primaryStage, Button nameFilterButton) {
		setSize(nameFilterButton, 200, 50);
		//works when the name-filter button is clicked
		nameFilterButton.setOnAction(x -> {
			final Stage dialog = new Stage(); //the stage for filter button
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane(); //grid panel for load food
			gridSetUp(load, "Name Filter", 2, 1);
			
			TextField userTextField = new TextField();
			Button upload = new Button("SEARCH");
			load.add(userTextField, 0, 1);
			load.add(upload, 0, 2);
			// search button
			upload.setOnAction(y -> {
				// pop up window
				final Stage panel = new Stage();
				panel.initModality(Modality.APPLICATION_MODAL);
				panel.initOwner(dialog);
				VBox box = new VBox();
				Button addButton = new Button("Add to list");
				ListView<String> nameSearch = new ListView<>();
				nameSearch.setMinHeight(500);
				// enable multiple selection
				nameSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				// get the filtered list
				String name = userTextField.getText();
				List<FoodItem> qualified = sort(foods.filterByName(name));
				nameSearch.setItems(FXCollections.observableArrayList(convert(qualified)));
				box.getChildren().addAll(nameSearch, addButton);
				box.setAlignment(Pos.TOP_CENTER);
				Insets margin = new Insets(0, 0, 25, 0);
				box.setMargin(nameSearch, margin);
				// add the selected food to the meal list
				addButton.setOnAction(z -> {
					ObservableList<Integer> index = nameSearch.getSelectionModel().getSelectedIndices();
					for (int i = 0; i < index.size(); ++i) {
						mealList.add(qualified.get(index.get(i)));
					}
					mealList = sort(mealList);
					items2 = FXCollections.observableArrayList(convert(mealList));
					list2.setItems(items2);
					panel.close();
					dialog.close();
				});
				Scene searchList = new Scene(box, BIG_POPUP_WIDTH, BIG_POPUP_HEIGHT);
				panel.setScene(searchList);
				panel.show();
			});
			Scene dialogScene = new Scene(load, SMALL_POPUP_WIDTH, SMALL_POPUP_HEIGHT);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * This method creates button that uses to add food into the
	 * food list
	 * 
	 * @param primaryStage the original stage
	 * @param addFoodButton the button uses to add food 
	 */
	private void createAddFoodButton(Stage primaryStage, Button addFoodButton) {
		setSize(addFoodButton, 200, 50);
		addFoodButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Add Food", 2, 1);

			//label for id in 24 digits in food list
			Label id = new Label("ID (24 digits):");
			TextField userID = new TextField();
			//label for names of food items
			Label name = new Label("Name:");
			TextField userName = new TextField();
			Label calorie = new Label("Calories:");
			TextField userCal = new TextField();
			Label fat = new Label("Fat:");
			TextField userFat = new TextField();
			Label carb = new Label("Carbohydrate:");
			TextField userCarbo = new TextField();
			Label fiber = new Label("Fiber:");
			TextField userFiber = new TextField();
			Label protein = new Label("Protein:");
			TextField userProtein = new TextField();
			Label warn = new Label("Invalid input");
			warn.setTextFill(Color.RED);
			
			//set up the grid
			gridSetCol(load, new Label[] {id, name, calorie, fat, 
					carb, fiber, protein }, 0);
			gridSetCol(load, new TextField[] { userID, userName, userCal, 
					userFat, userCarbo, userFiber, userProtein }, 1);
			Button gen = new Button ("Generate ID");
			load.add(gen, 0, 8);
			// generate a 24 digits ID
			gen.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					userID.setText(idGenerator());
				}
			});
			// ADD button for adding a new food
			Button upload = new Button("UPLOAD");
			load.add(upload, 1, 8);
			upload.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					String id1 = userID.getText();
					String name1 = userName.getText();
					FoodItem newFood = null;
					if (name1 != null && id1 != null && id1.length() == 24) {
						try {
							//add a new food item to the food list
							newFood = new FoodItem(id1, name1);
							newFood.addNutrient("calories", Double.parseDouble(userCal.getText()));
							newFood.addNutrient("fat", Double.parseDouble(userFat.getText()));
							newFood.addNutrient("carbohydrate", Double.parseDouble(userCarbo.getText()));
							newFood.addNutrient("fiber", Double.parseDouble(userFiber.getText()));
							newFood.addNutrient("protein", Double.parseDouble(userProtein.getText()));
							foods.addFoodItem(newFood);
							dialog.close();
						} catch (Exception e) {
							try {
								//warning message for invalid input
								load.add(warn, 1, 9);
							} catch (IllegalArgumentException e1) {

							}
						}
					} else {
						try {
							load.add(warn, 1, 9);
						} catch (IllegalArgumentException e) {

						}
					}
					//update the food list view
					update(foods.getAllFoodItems());
				}
			});
			Scene dialogScene = new Scene(load, 400, 500);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * This method creates buttoon to add food items
	 * from file in local directory to food list
	 * 
	 * @param primaryStage the original stage 
	 * @param loadFoodButton the button that uses to add food
	 * @return loadFoodButton the food list after adding the food items
	 */
	private Button createLoadFoodButton(Stage primaryStage, Button loadFoodButton) {
		setSize(loadFoodButton, 200, 50);
		loadFoodButton.setOnAction(y -> {
			// set up a popup window
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Load Additional Food", 2, 1);

			Label userName = new Label("File:");
			load.add(userName, 0, 1);
			TextField userTextField = new TextField();
			Button file = new Button("Choose File");
			Button upload = new Button("UPLOAD");
			gridSetCol(load, new Node[] { userTextField, file, upload }, 1);
			// file chooser
			file.setOnAction(x -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				File filePath = fileChooser.showOpenDialog(dialog);
				if (filePath != null) {
					userTextField.setText(filePath.toString());
				}
			});
			// upload the file and update the food list view
			upload.setOnAction(x -> {
				if (!userTextField.getText().isEmpty()) {
					foods.loadFoodItems(userTextField.getText());
					convert(foods.getAllFoodItems());
					update(foods.getAllFoodItems());
				}

				dialog.close();
			});
			Scene dialogScene = new Scene(load, 300, 200);
			dialog.setScene(dialogScene);
			dialog.show();
		});

		return loadFoodButton;
	}
	
	private void createOtherFuncButton(Stage primaryStage, Button otherFunc) {
		setSize(otherFunc, 100, 25);
//		otherFunc.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		otherFunc.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			Button translate = new Button ("Translate");
			VBox load = new VBox();
			load.setPadding(new Insets(30, 0, 0, 0));
			load.setSpacing(40);
			load.setPrefSize(200, 50);
			load.setAlignment(Pos.CENTER);
			load.getChildren().addAll(translate);
			translate.setOnAction(y -> {
				final Stage pop = new Stage();
				pop.initModality(Modality.APPLICATION_MODAL);
				pop.initOwner(dialog);
				GridPane load1 = new GridPane();
				gridSetUp(load1, "", 1, 1);
				
				Label select = new Label("Select Language");
				ObservableList<String> languageOption = FXCollections.observableArrayList("Russian", 
						"Chinese", "French", "Hindi", "German", "Japanese", "Korean", "Hebrew");
				final ComboBox<String> combo1 = new ComboBox<String>(languageOption);
				Button submit = new Button("Submit");
				gridSetCol(load1, new Node[] {select,  combo1, submit}, 0);
				submit.setOnAction(z -> {
					for (int i = 0; i < allButton.size(); ++i) {
						String originalText = allButton.get(i).getText();
						allButton.get(i).setText(googleTranslate(originalText, combo1.getValue()));;
					}
					for (int i = 0; i < allLabel.size(); ++i) {
						String originalText = allLabel.get(i).getText();
						allLabel.get(i).setText(googleTranslate(originalText, combo1.getValue()));;
					}
					pop.close();
					dialog.close();
				});
				Scene popUp = new Scene(load1, 300, 300);
				pop.setScene(popUp);
				pop.show();
			});
			
			Scene dialogScene = new Scene(load, 200, 400);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}
	
	/**
	 * This method converts the foodItem list into an String ArrayList of food name
	 * 
	 * @param list foodItem list
	 * @return foodList list of food name after uploading data
	 */
	private ArrayList<String> convert(java.util.List<FoodItem> list) {
		// the list of food name 
		ArrayList<String> foodList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			//add the name from foodItem list
			foodList.add(i + 1 + ". " + list.get(i).getName());
		}

		return foodList;
	}
	
	/**
	 * This method sorts the food list in a alphabetic order
	 * 
	 * @param list foodItem list
	 * @return list the food list after sorting
	 */
	private List<FoodItem> sort(java.util.List<FoodItem> list) {
		// sort the food item lists according to alphabetic order
		Collections.sort(list, new Comparator<FoodItem>() {
			@Override
			public int compare(FoodItem food1, FoodItem food2) {
				return food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase());
			};
		});
		return list;
	}

	/**
	 * This method update the food list 
	 * 
	 * @param list foodItem list
	 */
	private void update(java.util.List<FoodItem> list) {
		ObservableList<String> updateList = FXCollections.observableArrayList(convert(list));
		list1.setItems(updateList);
		label3.setText(String.format("Number of Food: %d", 
				foods.getAllFoodItems().size()));
	}
	
	/**
	 * This method generate a random id, which is 24 digit for 
	 * each food items
	 * 
	 * @return id the randomized id
	 */
	private String idGenerator () {
		//random generator
		Random rand1 = new Random();
		Random rand2 = new Random();
		Random rand3 = new Random();
		// ID for food
		String id = "";
		char letter = 0;
		// random number based on ASCll
		int randomNum = 0;
		// random lower case letter based on ASCll
		int randomLetter = 0;
		int choose = 0;
		do {
			// 24 digits
			for (int i = 0; i < 24; ++i) {
				//choose between a letter or a number
		         choose = rand3.nextInt((2 - 1) + 1) + 1;
					if (choose == 1) {
						// generate a number and add to id
						randomNum = rand1.nextInt((57 - 48) + 1) + 48;
						letter = (char)randomNum;
						id += Character.toString(letter);
					} else {
						//generate a letter and add to id
						randomLetter = rand2.nextInt((122 - 97) + 1) + 97;
						letter = (char)randomLetter;
						id += Character.toString(letter);
					}
				}
			//check duplicate
		} while (!checkIDDuplicate(id)); 
		return id;
	}
	
	/**
	 * This method checks whether there are duplicate id for different
	 * food items
	 * 
	 * @return true for not having duplicate id
	 * 		   false for having duplicate id
	 */
	private boolean checkIDDuplicate(String id) {
		for (int i = 0; i < foods.getAllFoodItems().size(); ++i) {
			if (id.equals(foods.getAllFoodItems().get(i).getID())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method sets buttons size, including width and height
	 * 
	 * @param button the button to be setted
	 * @param width the width of button
	 * @param height the height of button
	 */
	private void setSize(Button button, int width, int height) {
		button.setMinSize(width, height);
	}

	/**
	 * This method sets the grid layout
	 * 
	 * @param grid GridPane for set up
	 * @param child items added to the grid pane
	 * @param col the column of the item
	 */
	private void gridSetCol(GridPane grid, Node[] childs, int col) {
		for (int i = 0; i < childs.length; i++) {
			grid.add(childs[i], col, i+1);
		}
	}

	private void gridSetUp(GridPane grid, String str, int spanCol, int spanRow) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 0, 0, 0));
		Text scenetitle = new Text(str);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(scenetitle, 0, 0, spanCol, spanRow);
	}
	
	private String googleTranslate (String originalText, String language) {
		String rtn = "";
		try {
			rtn = GoogleTranslate.translate(languageList(language), originalText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;
	}
	private String languageList(String lan) {
		return a.get(lan);
	}
	
	
	/**
	 * launch GUI
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
	
}