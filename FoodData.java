package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;

    /**
     * Public constructor
     */
    public FoodData() {
	    	this.foodItemList = new ArrayList<FoodItem>();
	    	this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(filePath);
			Scanner scnr = new Scanner(fileInput);
			while(scnr.hasNextLine()) {
				String line = scnr.nextLine();
				String [] info = line.split(",");
				if(info.length != 12) {
					continue;
				}
				FoodItem food = new FoodItem(info[0], info[1]);
			
				if(info[2].equals("calories")) {
					food.addNutrient(info[2], Double.parseDouble(info[3]));
				} else {
					continue;
				}
				if(info[4].equals("fat")) {
					food.addNutrient(info[4], Double.parseDouble(info[5]));
				} else {
					continue;
				}
				if(info[6].equals("carbohydrate")) {
					food.addNutrient(info[6], Double.parseDouble(info[7]));
				} else {
					continue;
				}
				if(info[8].equals("fiber")) {
					food.addNutrient(info[8], Double.parseDouble(info[9]));
				} else {
					continue;
				}
				if(info[10].equals("protein")) {
					food.addNutrient(info[10], Double.parseDouble(info[11]));
				} else {
					continue;
				}
				foodItemList.add(food);
			}
			scnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    /**
     * Private method to construct the nurient map
     */
    private void buildNutrientMap() {
    		BPTree<Double, FoodItem> calories = new BPTree<Double, FoodItem>(4);
    		BPTree<Double, FoodItem> fat = new BPTree<Double, FoodItem>(4);
    		BPTree<Double, FoodItem> carbohydrate = new BPTree<Double, FoodItem>(4);
    		BPTree<Double, FoodItem> fiber = new BPTree<Double, FoodItem>(4);
    		BPTree<Double, FoodItem> protein = new BPTree<Double, FoodItem>(4);
    	 	for (int i = 0; i < foodItemList.size(); i++) {
    	 		FoodItem food = foodItemList.get(i);
    	 		calories.insert(food.getNutrientValue("calories"),food);
    	 		fat.insert(food.getNutrientValue("fat"), food);
    	 		carbohydrate.insert(food.getNutrientValue("carbohydrate"), food);
    	 		fiber.insert(food.getNutrientValue("fiber"), food);
    	 		protein.insert(food.getNutrientValue("protein"), food);
    	 	}
    	 	indexes.put("calories", calories);
    	 	indexes.put("fat", fat);
    	 	indexes.put("carbohydrate", carbohydrate);
    	 	indexes.put("fiber", fiber);
    	 	indexes.put("protein", protein);
    }
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
    		List<FoodItem> filteredFood = new ArrayList<FoodItem> ();
         for (int i = 0; i < foodItemList.size(); i++) {
         	if (foodItemList.get(i).getName().contains(substring)) {
         		filteredFood.add(foodItemList.get(i));
         	}
         }
         return filteredFood;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     * FIXME: a bug exists because of the implementation of BPTree
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    		List<FoodItem> result = new ArrayList<FoodItem> ();
    		buildNutrientMap();
    		for(int i = 0; i < rules.size(); i++) {
    			String[] rule = rules.get(i).split(" ");
    			String nutrient = rule[0];
    			String comparator = rule[1];
    			Double value = Double.parseDouble(rule[2]);
    			List<FoodItem> filtered = indexes.get(nutrient).rangeSearch(value, comparator);
    			for(FoodItem food: filtered) {
    				if(!result.contains(food)) {
    					result.add(food);
    				}
    			}
    		}
        return result;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     * 
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#saveFoodItems(java.lang.String)
     */
	@Override
	public void saveFoodItems(String filename) {
		File fileOutput = new File(filename);
		try {
			PrintWriter outFS = new PrintWriter(fileOutput);
			Collections.sort(foodItemList, new Comparator<FoodItem>() {
				@Override
				public int compare(FoodItem food1, FoodItem food2) {
			        return food1.getName().compareTo(food2.getName());
			    }
			});
			for (int i = 0; i < foodItemList.size(); i++) {
				System.out.println(foodItemList.get(i).getName());
				outFS.println(foodItemList.get(i).getName());
			}
			outFS.flush();
			outFS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		FoodData data = new FoodData();
		data.loadFoodItems("foodItems.txt");
//		for (int i = 0; i < data.foodItemList.size(); i++) {
//			System.out.println(data.foodItemList.get(i).getName());
//		}
		
//		List<FoodItem> nameFilter = data.filterByName("ee");
//		for (int i = 0; i < nameFilter.size(); i++) {
//			System.out.println(nameFilter.get(i).getName());
//		}
//		
		
//		FoodItem newfood = new FoodItem("007","Cola");
//		data.addFoodItem(newfood);
//		HashMap<String, Double> nutrients = new HashMap<String, Double>();
//		nutrients.put("calories", 20.0);
//		nutrients.put("fat", 20.0);
//		nutrients.put("carbohydrate", 20.0);
//		nutrients.put("fiber", 20.0);
//		nutrients.put("protein", 20.0);
		
//		List<String> rules = new ArrayList<String>();
//		rules.add("calories == 0");
//		
//		List<FoodItem> nutrientFilter = data.filterByNutrients(rules);
//		for (int i = 0; i < nutrientFilter.size(); i++) {
//			System.out.println(nutrientFilter.get(i).getName());
//		}
		
		data.saveFoodItems("sorted.txt");
	}

}
