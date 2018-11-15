import java.io.File;
import java.io.FileNotFoundException;
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
    	this.foodItemList = null;
    	this.indexes = null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @SuppressWarnings("null")
	@Override
    public void loadFoodItems(String filePath) {
			Scanner scnr;
			int count = 0;
			int foodIndex = 0;
			try {
				scnr = new Scanner(new File(filePath));
				scnr.useDelimiter(",");
				while(scnr.hasNext()) {
					String id = null;
					String name = null;
					count ++;
					if (count % 12 == 1) {
						id = scnr.next();
					}
					if (count % 12 == 2) {
						name = scnr.next();
						foodItemList.add(new FoodItem(id, name));
						foodIndex ++;
					}
					if (count % 12 == 3) {
						scnr.next();
					}
					if (count % 12 == 4) {
						foodItemList.get(foodIndex).addNutrient("calories", scnr.nextInt());
					}
					if (count % 12 == 5) {
						scnr.next();
					}
					if (count % 12 == 6) {
						foodItemList.get(foodIndex).addNutrient("fat", scnr.nextInt());
					}
					if (count % 12 == 7) {
						scnr.next();
					}
					if (count % 12 == 8) {
						foodItemList.get(foodIndex).addNutrient("carbohydrate", scnr.nextInt());
					}
					if (count % 12 == 9) {
						scnr.next();
					}
					if (count % 12 == 10) {
						foodItemList.get(foodIndex).addNutrient("fiber", scnr.nextInt());					
					}
					if (count % 12 == 11) {
						scnr.next();
					}
					if (count % 12 == 0) {
						foodItemList.get(foodIndex).addNutrient("protein", scnr.nextInt());
					}
				}
				scnr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        // TODO : Complete
        return null;
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
        // TODO : Complete
        return null;
    }


	@Override
	public void saveFoodItems(String filename) {
		// TODO Auto-generated method stub
		
	}

}
