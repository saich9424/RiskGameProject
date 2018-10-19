package com.concordia.riskGame.model.Continent;

import java.util.List;

import com.concordia.riskGame.model.Country.Country;

/**
 * Continent Class go get and set all properties of a continent.
 * @author sande
 *
 */
public class Continent {
	private String continentName;
	private List<Country> countries;
	private int numberOfCountries;
	private int continentControlValue;
	
	/**
	 * This is a constructor to create the Continent given only the continent. 
	 * @param continentName This is the name of the Continent
	 */
	public Continent(String continentName) {
		this.continentName = continentName;
	}
	
	
	/**
	 * This is a constructor to assign Continent  Name and its control value
	 * @param continentName This is the name of the Continent
	 * @param continentControlValue This is the control value of the continent
	 */
	public Continent(String continentName,int continentControlValue) {
		this.continentName = continentName;
		this.continentControlValue = continentControlValue;
	}
	
	
	/**
	 * This is a constructor to create the Continent given the continent and countries of that continent.
	 * @param continentName This is the name of the Continent
	 * @param countries This is the list of the countries that belong to that continent.
	 */
	public Continent(String continentName, List<Country> countries) {
		this.continentName = continentName;
		this.countries=countries;
	}
	/**
	 * method to get the countries which belong to this continent
	 * @return String
	 */
	public List<Country> getCountries() {
		return countries;
	}
	/**
	 * method to set the countries which belong to this continent
	 * @param countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	/**
	 * method to get the name of the continent
	 * @return String
	 */
	public String getContinentName() {
		return continentName;
	}
	/**
	 * method to set the name of the continent
	 * @param continentName
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * Get the count of countries in this continent
	 * @return numberOfCountries 
	 */
	public int getNumberOfCountries() {
		return numberOfCountries;
	}

	/**
	 * Set the count of countries in this continent
	 * @param numberOfCountries 
	 */
	public void setNumberOfCountries(int numberOfCountries) {
		this.numberOfCountries = numberOfCountries;
	}

	/**
	 * Get the control value of the Continent
	 * @return continentControlValue 
	 */

	public int getContinentControlValue() {
		return continentControlValue;
	}

	/**
	 * Set the control value of the Continent
	 * @param continentControlValue 
	 */

	public void setContinentControlValue(int continentControlValue) {
		this.continentControlValue = continentControlValue;
	}
	
	
	
}