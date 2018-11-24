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
			Scanner scnr;
			int count = 0;
			int foodIndex = 0;
			BPTree<Double, FoodItem> cal = new BPTree<Double,FoodItem>(4);
			BPTree<Double, FoodItem> fat = new BPTree<Double,FoodItem>(4);
			BPTree<Double, FoodItem> carbo = new BPTree<Double,FoodItem>(4);
			BPTree<Double, FoodItem> fiber = new BPTree<Double,FoodItem>(4);
			BPTree<Double, FoodItem> protein = new BPTree<Double,FoodItem>(4);

			try {
				scnr = new Scanner(new File(filePath));
			while (scnr.hasNext()) {
				String[] foodEntry = scnr.nextLine().split(",");
				if (foodEntry.length == 0) {
					continue;
				}

				FoodItem singleFood = new FoodItem(foodEntry[0], foodEntry[1]);
				singleFood.addNutrient("calories", Double.parseDouble(foodEntry[3]));
				singleFood.addNutrient("fat", Double.parseDouble(foodEntry[5]));
				singleFood.addNutrient("carbohydrate", Double.parseDouble(foodEntry[7]));
				singleFood.addNutrient("fiber", Double.parseDouble(foodEntry[9]));
				singleFood.addNutrient("protein", Double.parseDouble(foodEntry[11]));

				cal.insert(Double.parseDouble(foodEntry[3]), singleFood);
				fat.insert(Double.parseDouble(foodEntry[5]), singleFood);
				carbo.insert(Double.parseDouble(foodEntry[7]), singleFood);
				fiber.insert(Double.parseDouble(foodEntry[9]), singleFood);
				protein.insert(Double.parseDouble(foodEntry[11]), singleFood);

				foodItemList.add(new FoodItem(foodEntry[0], foodEntry[1]));
				foodIndex++;

				boolean notDone = true;
				for (int i = 0; i < foodItemList.size(); i++) {
					String curFood = foodEntry[1];
					if (curFood.compareTo(foodItemList.get(i).getName()) < 0) {
						foodItemList.add(i, singleFood);
						notDone = false;
						break;
					}
				}
				if (notDone) {
					foodItemList.add(singleFood);
				}
			}

				scnr.close();
				this.indexes.put("calories", cal);
				this.indexes.put("fat", fat);
				this.indexes.put("carbohydrate", carbo);
				this.indexes.put("fiber", fiber);
				this.indexes.put("protein", protein);
		 
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
    		//FIXME buildNutrientMap();
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
	
	private ArrayList<String> convert(java.util.List<FoodItem> list) {
		 ArrayList<String> foods = new ArrayList<>();
		 for (int i = 0; i < list.size(); i++) {
			 String curFood = list.get(i).getName();
			 boolean notDone = true;
			 for (int j = 0; j < i; j++) {
				 if(curFood.compareTo(foods.get(j)) < 0) {
					 foods.add(j, curFood);
					 notDone = false;
					 break;
				 }
			 }
			 if (notDone) {foods.add(curFood);}
		 }
		 return foods;
	 }

}