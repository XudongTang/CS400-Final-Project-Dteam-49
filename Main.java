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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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

public class Main extends Application{
	
	private FoodData foods = new FoodData();
	private ObservableList<String> items1 = FXCollections.observableArrayList();
	private ObservableList<String> items2 = FXCollections.observableArrayList();
	private ListView<String> list1 = new ListView<String>();
	private ListView<String> list2 = new ListView<String>();
	private List<FoodItem> mealList = new ArrayList<FoodItem>();
	private List<FoodItem> filterNut = new ArrayList<FoodItem>();
	private List<String> rule = new ArrayList<String>();
	private Label label3 = new Label("Number of Food: ");
	private final int SMALL_POPUP_WIDTH = 300;
	private final int SMALL_POPUP_HEIGHT = 200;
	private final int BIG_POPUP_WIDTH = 400;
	private final int BIG_POPUP_HEIGHT = 600;

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		Button loadFoodButton = new Button("Load Additional Food");
		Button addFoodButton = new Button("Add Food");
		Button nameFilterButton = new Button("Name Filter");
		Button nutrientFilterButton = new Button("Nutrient Filter");
		Button saveButton = new Button("Save");
		Button analyzeButton = new Button("Anzlyze Meal");
		Button addToMealButton = new Button("Add to Meal List");
		Button removeButton = new Button("Remove");
		Button helpButton = new Button("Help");
		Label label1 = new Label("Current Food List");
		Label label2 = new Label("Current Meal List");
		VBox vboxButton = new VBox();
		VBox vboxFoodList = new VBox();
		VBox vboxMealList = new VBox();
		BorderPane mainPane = new BorderPane();

		try {
			Group root = new Group();
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("FOOD QUERY");
			scene.setFill(Color.WHITE);

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

			// layout
			list1.setItems(items1);
			list1.setMaxWidth(200);
			list1.setPrefHeight(400);
			list1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			list2.setItems(items2);
			list2.setMaxWidth(200);
			list2.setPrefHeight(400);
			list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			// list of buttons
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
			vboxFoodList.getChildren().addAll(label1, list1, addToMealButton, label3);

			// meal list
			vboxMealList.setPrefWidth(200);
			vboxMealList.setPadding(new Insets(50, 50, 0, 50));
			vboxMealList.setAlignment(Pos.CENTER_RIGHT);
			vboxMealList.setMargin(helpButton, new Insets(20, 0, 0, 0));
			vboxMealList.getChildren().addAll(label2, list2, 
					new HBox(analyzeButton, removeButton), helpButton);

			// main pane
			mainPane.setLeft(vboxFoodList);
			mainPane.setCenter(vboxButton);
			mainPane.setRight(vboxMealList);

			root.getChildren().addAll(mainPane);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param primaryStage
	 * @param helpButton
	 */
	private void createHelpButton(Stage primaryStage, Button helpButton) {
		setSize(helpButton, 100, 30);

		helpButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			Scene helpScene = new Scene(new Group());
			VBox root = new VBox();
			final ImageView selectedImage = new ImageView();
			try {
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
	 * @param removeButton
	 */
	private void createRemoveButton(Button removeButton) {
		setSize(removeButton, 100, 50);
		removeButton.setOnAction(x -> {
			ObservableList<Integer> index = list2.getSelectionModel().getSelectedIndices();
			for (int i = index.size() - 1; i > -1; i--) {
				mealList.remove((int) index.get(i));
			}
			mealList = sort(mealList);

			items2 = FXCollections.observableArrayList(convert(mealList));
			list2.setItems(items2);
		});
	}

	/**
	 * @param addToMealButton
	 */
	private void createAddToMealButton(Button addToMealButton) {
		setSize(addToMealButton, 200, 50);
		addToMealButton.setOnAction(x -> {
			ObservableList<Integer> index = list1.getSelectionModel().getSelectedIndices();
			for (int i = 0; i < index.size(); ++i) {
				mealList.add(foods.getAllFoodItems().get(index.get(i)));
			}
			mealList = sort(mealList);
			items2 = FXCollections.observableArrayList(convert(mealList));
			list2.setItems(items2);
		});
	}

	/**
	 * @param primaryStage
	 * @param analyzeButton
	 */
	private void createAnalyzeButton(Stage primaryStage, Button analyzeButton) {
		setSize(analyzeButton, 100, 50);
		analyzeButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Nutritional Information Summary", 1, 1);

			double[] nutrient = new double[] { 0d, 0d, 0d, 0d, 0d };

			for (FoodItem food : mealList) {
				nutrient[0] += food.getNutrientValue("calories");
				nutrient[1] += food.getNutrientValue("fat");
				nutrient[2] += food.getNutrientValue("carbohydrate");
				nutrient[3] += food.getNutrientValue("fiber");
				nutrient[4] += food.getNutrientValue("protein");
			}

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
	 * @param primaryStage
	 * @param saveButton
	 */
	private void createSaveButton(Stage primaryStage, Button saveButton) {
		setSize(saveButton, 200, 50);
		saveButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Save file", 1, 1);

			Label fileName = new Label("Filename");
			Label location = new Label("Where");
			gridSetCol(load, new Node[] {fileName, location }, 0);

			TextField userTextField = new TextField();
			TextField fileLocation = new TextField();
			Button file = new Button("Browse");
			Button upload = new Button("SAVE");
			gridSetCol(load, new Node[] { userTextField, fileLocation, file, upload }, 1);

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
	 * @param primaryStage
	 * @param nutrientFilterBUtton
	 */
	@SuppressWarnings("static-access")
	private void createNutrientFilterButton(Stage primaryStage, Button nutrientFilterButton) {
		setSize(nutrientFilterButton, 200, 50);
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

			upload2.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					final Stage popUp = new Stage();
					popUp.initModality(Modality.APPLICATION_MODAL);
					popUp.initOwner(dialog);
					GridPane load1 = new GridPane();
					gridSetUp(load1, "", 1, 1);

					ObservableList<String> nutrient = FXCollections.observableArrayList("calories", "fat",
							"carbohydrate", "fiber", "protein");
					ObservableList<String> comparator = FXCollections.observableArrayList(">=", "<=", "==");
					final ComboBox<String> combo1 = new ComboBox<String>(nutrient);
					final ComboBox<String> combo2 = new ComboBox<String>(comparator);

					Label nutrientL = new Label("Nutrients");
					Label comparatorL = new Label("Comparator");
					Label numL = new Label("Number");
					TextField number = new TextField();
					Button submit = new Button("ADD RULE");
					Label warn = new Label("invalid input");

					gridSetCol(load1, new Node[] { nutrientL, combo1 }, 0);
					gridSetCol(load1, new Node[] { comparatorL, combo2 }, 1);
					gridSetCol(load1, new Node[] { numL, number, submit }, 2);

					submit.setOnAction(y -> {
						try {
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
				final Stage panel = new Stage();
				panel.initModality(Modality.APPLICATION_MODAL);
				panel.initOwner(dialog);
				Button addButton = new Button("Add to list");
				VBox box = new VBox();
				ListView<String> nutSearch = new ListView<>();
				try {
					filterNut = foods.filterByNutrients(rule);
					nutSearch.setMinHeight(500);
					nutSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					nutSearch.setItems(FXCollections.observableArrayList(convert(filterNut)));
					box.getChildren().addAll(nutSearch, addButton);
					box.setAlignment(Pos.TOP_CENTER);
					Insets margin = new Insets(0, 0, 25, 0);
					box.setMargin(nutSearch, margin);
				} catch (Exception e1) {
					nutSearch.setItems(FXCollections.observableArrayList("No Match Found"));
				}

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
	 * @param primaryStage
	 * @param nameFileterButton
	 */
	@SuppressWarnings("static-access")
	private void createNameFilterButton(Stage primaryStage, Button nameFilterButton) {
		setSize(nameFilterButton, 200, 50);
		nameFilterButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Name Filter", 2, 1);

			TextField userTextField = new TextField();
			Button upload = new Button("SEARCH");
			load.add(userTextField, 0, 1);
			load.add(upload, 0, 2);
			upload.setOnAction(y -> {
				final Stage panel = new Stage();
				panel.initModality(Modality.APPLICATION_MODAL);
				panel.initOwner(dialog);
				VBox box = new VBox();
				Button addButton = new Button("Add to list");
				ListView<String> nameSearch = new ListView<>();
				nameSearch.setMinHeight(500);
				nameSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				String name = userTextField.getText();
				List<FoodItem> qualified = sort(foods.filterByName(name));
				nameSearch.setItems(FXCollections.observableArrayList(convert(qualified)));

				box.getChildren().addAll(nameSearch, addButton);
				box.setAlignment(Pos.TOP_CENTER);
				Insets margin = new Insets(0, 0, 25, 0);
				box.setMargin(nameSearch, margin);

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
	 * @param primaryStage
	 * @param addFoodButton
	 */
	private void createAddFoodButton(Stage primaryStage, Button addFoodButton) {
		setSize(addFoodButton, 200, 50);
		addFoodButton.setOnAction(x -> {
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			GridPane load = new GridPane();
			gridSetUp(load, "Add Food", 2, 1);

			Label id = new Label("ID (24 digits):");
			TextField userID = new TextField();
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
			
			gridSetCol(load, new Label[] {id, name, calorie, fat, 
					carb, fiber, protein }, 0);
			gridSetCol(load, new TextField[] { userID, userName, userCal, 
					userFat, userCarbo, userFiber, userProtein }, 1);
			Button gen = new Button ("Generate ID");
			load.add(gen, 0, 8);
			gen.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					userID.setText(idGenerator());
				}
			});
			Button upload = new Button("UPLOAD");
			load.add(upload, 1, 8);
			upload.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent x) {
					String id1 = userID.getText();
					String name1 = userName.getText();
					FoodItem newFood = null;
					if (name1 != null && id1 != null && id1.length() == 24) {
						try {
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
								load.add(warn, 2, 8);
							} catch (IllegalArgumentException e1) {

							}
						}
					} else {
						try {
							load.add(warn, 2, 8);
						} catch (IllegalArgumentException e) {

						}
					}
					update(foods.getAllFoodItems());
				}
			});
			Scene dialogScene = new Scene(load, 400, 400);
			dialog.setScene(dialogScene);
			dialog.show();
		});
	}

	/**
	 * @param primaryStage
	 */
	private Button createLoadFoodButton(Stage primaryStage, Button loadFoodButton) {
		setSize(loadFoodButton, 200, 50);
		loadFoodButton.setOnAction(y -> {
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

			file.setOnAction(x -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				File filePath = fileChooser.showOpenDialog(dialog);
				if (filePath != null) {
					userTextField.setText(filePath.toString());
				}
			});
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

	private ArrayList<String> convert(java.util.List<FoodItem> list) {
		ArrayList<String> foodList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			foodList.add(i + 1 + ". " + list.get(i).getName());
		}

		return foodList;
	}

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

	private void update(java.util.List<FoodItem> list) {
		ObservableList<String> updateList = FXCollections.observableArrayList(convert(list));
		list1.setItems(updateList);
		label3.setText(String.format("Number of Food: %d", 
				foods.getAllFoodItems().size()));
	}
	
	private String idGenerator () {
		Random rand1 = new Random();
		Random rand2 = new Random();
		Random rand3 = new Random();
		String id = "";
		char letter = 0;
		int randomNum = 0;
		int randomLetter = 0;
		int choose = 0;
		do {
			for (int i = 0; i < 24; ++i) {
		         choose = rand3.nextInt((2 - 1) + 1) + 1;
					if (choose == 1) {
		            randomNum = rand1.nextInt((57 - 48) + 1) + 48;
						letter = (char)randomNum;
						id += Character.toString(letter);
					} else {
		            randomLetter = rand2.nextInt((122 - 97) + 1) + 97;
						letter = (char)randomLetter;
						id += Character.toString(letter);
					}
				}
		} while (!checkIDDuplicate(id)); 
		return id;
	}
	
	private boolean checkIDDuplicate(String id) {
		for (int i = 0; i < foods.getAllFoodItems().size(); ++i) {
			if (id.equals(foods.getAllFoodItems().get(i).getID())) {
				return false;
			}
		}
		return true;
	}

	private void setSize(Button button, int width, int height) {
		button.setMinSize(width, height);
	}

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

	public static void main(String[] args) {
		launch(args);
	}
	
}
