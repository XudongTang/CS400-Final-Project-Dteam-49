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
					int result = count % 12;
					switch (result) {
					case 1:
						id = scnr.next();
						break;
					case 2:
						name = scnr.next();
						foodItemList.add(new FoodItem(id, name));
						foodIndex ++;
						break;
					case 3:
						scnr.next();
						break;
					case 4:
						foodItemList.get(foodIndex).addNutrient("calories", scnr.nextInt());
						break;
					case 5:
						scnr.next();
						break;
					case 6:
						foodItemList.get(foodIndex).addNutrient("fat", scnr.nextInt());
						break;
					case 7:
						scnr.next();
						break;
					case 8:
						foodItemList.get(foodIndex).addNutrient("carbohydrate", scnr.nextInt());
						break;
					case 9:
						scnr.next();
						break;
					case 10:
						foodItemList.get(foodIndex).addNutrient("fiber", scnr.nextInt());
						break;
					case 11:
						scnr.next();
						break;
					case 0:
						foodItemList.get(foodIndex).addNutrient("protein", scnr.nextInt());
						break;
					}
				}
				scnr.close();
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
