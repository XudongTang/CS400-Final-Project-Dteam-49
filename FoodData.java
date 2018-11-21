import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    	this.foodItemList = new ArrayList<FoodItem> ();
    	this.indexes = new HashMap<String, BPTree<Double, FoodItem>> ();
    }//FIXME
    
    
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
				while(scnr.hasNext()) {				
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
					
//					count ++;
//					int result = count % 12;
//					switch (result) {
//					case 1:
//						id = scnr.next();
//						break;
//					case 2:
//						name = scnr.next();
//						foodItemList.add(new FoodItem(id, name));
//						foodIndex ++;
//						break;
//					case 3:
//						scnr.next();
//						break;
//					case 4:
//						double test = Double.parseDouble(scnr.next());
//						foodItemList.get(foodIndex).addNutrient("calories", test);
//						break;
//					case 5:
//						scnr.next();
//						break;
//					case 6:
//						foodItemList.get(foodIndex).addNutrient("fat", scnr.nextDouble());
//						break;
//					case 7:
//						scnr.next();
//						break;
//					case 8:
//						foodItemList.get(foodIndex).addNutrient("carbohydrate", scnr.nextDouble());
//						break;
//					case 9:
//						scnr.next();
//						break;
//					case 10:
//						foodItemList.get(foodIndex).addNutrient("fiber", scnr.nextDouble());
//						break;
//					case 11:
//						scnr.next();
//						break;
//					case 0:
//						test = scnr.nextDouble();
//						foodItemList.get(foodIndex).addNutrient("protein", test);
//						break;
//					}
				}
				scnr.close();
				this.indexes.put("calories", cal);
				this.indexes.put("fat", fat);
				this.indexes.put("carbohydrate", carbo);
				this.indexes.put("fiber", fiber);
				this.indexes.put("protein", protein);

			} catch (FileNotFoundException e) {
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
        for (int i = 0; i < foodItemList.size(); ++i) {
        	boolean exists = Pattern.compile(Pattern.quote(substring), Pattern.CASE_INSENSITIVE).matcher(foodItemList.get(i).getName()).find();
        	if (exists) {
        		filteredFood.add(foodItemList.get(i));
        	}
        }
        return filteredFood;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        // TODO : Complete
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
       return this.foodItemList;
    }


	@Override
	public void saveFoodItems(String filename) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		FoodData a = new FoodData();
		a.loadFoodItems("foodItems.csv");
		ArrayList<FoodItem> b = (ArrayList<FoodItem>) a.filterByName("soy");
		for(FoodItem food : b) {
			System.out.println(food.getName());
		}
	}

}
