/**
 * Filename:   FoodItem.java
 * Project:    Team Project
 * Authors:    Debra Deppeler, Xudong Tang, Yixian Gan, Yiye Dang, Daoxing Zhang, Qiuhong Li
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    lec001
 * 
 * Due Date:   Before 10pm on November 30, 2018
 * Version:    1.0
 * 
 * Credits:    NA
 * 
 * Bugs:       no known bugs, but not complete either
 */

/**
 * This is a data structure class to store the information of a single food item
 * 
 * @author Xudong Tang (xtang75@wisc.edu)
 * @author Yixian Gan (ygan23@wisc.edu)
 * @author Yiye Dang (dang6@wisc.edu)
 * @author Daoxing Zhang (dzhang268@wisc.edu)
 * @author Qiuhong Li (qli288@wisc.edu)
 */

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem {
    // The name of the food item.
    private String name;

    // The id of the food item.
    private String id;

    // Map of nutrition and value.
    private HashMap<String, Double> nutrients;
    
    /**
     * Constructor
     * @param name name of the food item
     * @param id unique id of the food item 
     */
    public FoodItem(String id, String name) {
	    	this.id = id;
	    	this.name = name;	
	    	this.nutrients = new HashMap<String, Double>();
    	}
    
    /**
     * Gets the name of the food item
     * 
     * @return name of the food item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the unique id of the food item
     * 
     * @return id of the food item
     */
    public String getID() {
        return this.id;
    }
    
    /**
     * Gets the nutrition of the food item
     * 
     * @return nutrition of the food item
     */
    public HashMap<String, Double> getNutrients() {
        return this.nutrients;
    }

    /**
     * Adds a nutrition and its value to this food. 
     * If nutrition already exists, updates its value.
     */
    public void addNutrient(String name, double value) {
    		nutrients.put(name, value);
    }

    /**
     * Returns the value of the given nutrition for this food item. 
     * If not present, then returns 0.
     */
    public double getNutrientValue(String name) {
    		if(!nutrients.containsKey(name)) {
    			return 0;
    		}
    		return nutrients.get(name);
    }
    
}