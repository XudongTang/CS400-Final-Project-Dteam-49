/**
 * Filename:   FoodData.java
 * Project:    Team Project
 * Authors:    Debra Deppeler, Xudong Tang, Yixian Gan, 
 *		Yiye Dang, Daoxing Zhang, Qiuhong Li
 * Emails:     xtang75@wisc.edu, ygan23@wisc.edu, dang6@wisc.edu, 
 *		dzhang268@wisc.edu, qli288@wisc.edu
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * This class represents all food data passed in by a .csv file containing all
 * the information about one food item. All food items are stored in an
 * arrayList. All nutrition are stored in a hash map This class also does some
 * operations on the food items such as filter food items using some specific
 * rules and show all the food items This class also save some food items into a
 * text file.
 * 
 * @author Xudong Tang (xtang75@wisc.edu)
 * @author Yixian Gan (ygan23@wisc.edu)
 * @author Yiye Dang (dang6@wisc.edu)
 * @author Daoxing Zhang (dzhang268@wisc.edu)
 * @author Qiuhong Li (qli288@wisc.edu)
 * 
 */
public class FoodData implements FoodDataADT<FoodItem> {

	// List of all the food items.
	private List<FoodItem> foodItemList;

	// Map of nutrition and their corresponding index
	private HashMap<String, BPTree<Double, FoodItem>> indexes;
	
	// Comparator that helps to sort the list
    private static final Comparator<FoodItem> FOOF_COMPARATOR = new Comparator<FoodItem>() {
		@Override
		public int compare(FoodItem food1, FoodItem food2) {
	        return food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase());
	    };
    };
	/**
	 * Default constructor
	 */
	public FoodData() {
		this.foodItemList = new ArrayList<FoodItem>();
		this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
	}

	/**
	 * This method deal with the information in the .csv file
	 * 
	 * It creates a single FoodItem according to the information in each line,
	 * including the id, name, calories, fat, carbohydrate, fiber, protein. Then it
	 * adds the single FoodItem into the foodItemList.
	 * 
	 * It also adds each nutrition to its corresponding nutrition BPTree. Then it
	 * adds each nutrition BPTree to the indexes HashMap
	 * 
	 * @param filePath path of the food item data file (e.g.
	 *                 folder1/subfolder1/.../foodItems.csv)
	 */
	@Override
	public void loadFoodItems(String filePath) {
		Scanner scnr; // the scanner to iterate through the file
		//create the BPTree of different nutrients
		BPTree<Double, FoodItem> cal = new BPTree<Double, FoodItem>(4); 
		BPTree<Double, FoodItem> fat = new BPTree<Double, FoodItem>(4); 
		BPTree<Double, FoodItem> carbo = new BPTree<Double, FoodItem>(4); 
		BPTree<Double, FoodItem> fiber = new BPTree<Double, FoodItem>(4);
		BPTree<Double, FoodItem> protein = new BPTree<Double, FoodItem>(4); 

		try {
			scnr = new Scanner(new File(filePath));
			while (scnr.hasNext()) {
				// array of information of each food item in one line
				String[] foodEntry = scnr.nextLine().split(","); 
				if (foodEntry.length < 12) {
					continue;
				}
				// create the new food item 
				FoodItem singleFood = new FoodItem(foodEntry[0], foodEntry[1]); 
				// complete the nutrient content of each food item
				singleFood.addNutrient("calories", Double.parseDouble(foodEntry[3]));
				singleFood.addNutrient("fat", Double.parseDouble(foodEntry[5]));
				singleFood.addNutrient("carbohydrate", Double.parseDouble(foodEntry[7]));
				singleFood.addNutrient("fiber", Double.parseDouble(foodEntry[9]));
				singleFood.addNutrient("protein", Double.parseDouble(foodEntry[11]));

				// add nutrients to the BPTrees
				cal.insert(Double.parseDouble(foodEntry[3]), singleFood);
				fat.insert(Double.parseDouble(foodEntry[5]), singleFood);
				carbo.insert(Double.parseDouble(foodEntry[7]), singleFood);
				fiber.insert(Double.parseDouble(foodEntry[9]), singleFood);
				protein.insert(Double.parseDouble(foodEntry[11]), singleFood);

				// add the food item
				foodItemList.add(singleFood);
				
			}
			// sort the food item lists according to alphabetic order
			Collections.sort(foodItemList, FOOF_COMPARATOR );
	
			// add each nutrition BPTree into the indexes HashMap
			this.indexes.put("calories", cal);
			this.indexes.put("fat", fat);
			this.indexes.put("carbohydrate", carbo);
			this.indexes.put("fiber", fiber);
			this.indexes.put("protein", protein);
			
			scnr.close();//close the scanner
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method gets all the food items that have name containing the substring.
	 * 
	 * @see skeleton.FoodDataADT#filterByName(java.lang.String)
	 * @param substring substring to be searched
	 * @return list of filtered food items; if no food item matched, return empty
	 *         list
	 */
	@Override
	public List<FoodItem> filterByName(String substring) {
		if (substring == null) {
			return new ArrayList<FoodItem>();
		}
		// the new food item lists that have name containing the substring
		List<FoodItem> filteredFood = new ArrayList<FoodItem>(); 
		for (int i = 0; i < foodItemList.size(); ++i) {
			// checks whether this food containing the substring
			boolean exists = Pattern.compile(Pattern.quote(substring), Pattern.CASE_INSENSITIVE)
					.matcher(foodItemList.get(i).getName()).find();
			if (exists) {
				filteredFood.add(foodItemList.get(i));
			}
		}
		return filteredFood;
	}

	/**
	 * This method gets all the food items that fulfill ALL the provided rules
	 * 
	 * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
	 * @param rules list of rules
	 * @return list of filtered food items; if no food item matched, return empty
	 *         list
	 */
	@Override
	public List<FoodItem> filterByNutrients(List<String> rules) {
		// list of filtered food items
		List<FoodItem> result = new ArrayList<FoodItem>(); 
		// if there is no rule, return empty list
		if (rules.isEmpty()) {
			return result;
		}

		// the first rule string array
		String[] basicRule = rules.get(0).split(" "); 
		// the first nutrition name of the first rule string array
		String nutrient_1 = basicRule[0]; 
		// the first comparator of the first rule string array
		String comparator_1 = basicRule[1]; 
		// the first comparison value of the first rule string array
		Double value_1 = Double.parseDouble(basicRule[2]); 
		// do rangeSearch according to the first rule string array
		result = indexes.get(nutrient_1).rangeSearch(value_1, comparator_1); 
		// find the rest rule string arrays
		for (int i = 1; i < rules.size(); i++) {
			// the ith rule string array
			String[] rule = rules.get(i).split(" "); 
			String nutrient = rule[0]; // the ith nutrition name
			String comparator = rule[1]; // the ith comparator
			// the ith comparison value of the ith rule string array
			Double value = Double.parseDouble(rule[2]); 
			// do rangeSearch according to the ith rule string array
			List<FoodItem> filtered = indexes.get(nutrient).rangeSearch(value, comparator); 		
			// the new list of filtered food items according to several rule
			List<FoodItem> newResult = new ArrayList<>(); 

			// combine two list according to several rules
			for (FoodItem food : result) {
				if (filtered.contains(food)) {
					newResult.add(food);
				}
			}
			result = newResult;

		}
		return result;
	}

	/**
	 * Adds a food item to the loaded data.
	 * 
	 * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
	 * @param foodItem the food item instance to be added
	 */
	@Override
	public void addFoodItem(FoodItem foodItem) {
		if (foodItem == null) {
			return;
		}

		// add the nutrition to the indexes
		indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
		indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
		indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
		indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
		indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);

		// add the food item
		foodItemList.add(foodItem);
		// sort the food item lists according to alphabetic order
		Collections.sort(foodItemList, FOOF_COMPARATOR );
	}

	/**
	 * Gets the list of all food items.
	 * 
	 * @see skeleton.FoodDataADT#getAllFoodItems()
	 * @return list of FoodItem
	 */
	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemList;
	}

	/**
	 * Save the list of food items in ascending order by name
	 * 
	 * @see skeleton.FoodDataADT#saveFoodItems(java.lang.String)
	 * @param filename name of the file where the data needs to be saved
	 */
	@Override
	public void saveFoodItems(String filename) {
		File fileOutput = new File(filename); // the File to save information
		try {
			// The PrintWriter object to print information to file
			PrintWriter outFS = new PrintWriter(fileOutput); 
			// print all information of all food items
			for (int i = 0; i < foodItemList.size(); i++) {
				System.out.println(foodItemToString(foodItemList.get(i)));
				outFS.println(foodItemToString(foodItemList.get(i)));
			}
			outFS.flush();
			outFS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * The private helper method that returns all the information of a single food
	 * item including the id, name, calories, fat, carbohydrate, fiber, protein.
	 * 
	 * @param item the specific food item
	 * @return the information of a single food item
	 */
	private String foodItemToString(FoodItem item) {
		String str = item.getID() + ","; // the information of a single food item
		str += item.getName() + ",";
		str += "calories," + item.getNutrientValue("calories");
		str += ",fat," + item.getNutrientValue("fat");
		str += ",carbohydrate," + item.getNutrientValue("carbohydrate");
		str += ",fiber," + item.getNutrientValue("fiber");
		str += ",protein," + item.getNutrientValue("protein");
		return str;
	}

}

