package com.concordia.riskGame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapParseProcessor;
import com.concordia.riskGame.model.Player.Player;

/**
 * This class contains all the methods to validate the map file.
 * 
 * @author d_modi
 */

public class MapValidator {

	private Boolean validMapFlag;
	private String statusMessage;

	/**
	 * method to check if all the labels are described properly
	 * @param mapContents It is of type map Contents Object
	 * @return true if all labels are described properly, otherwise false
	 */
	public boolean checkMapLabel(MapContents mapContents) {
		if(mapContents.getLabelCount() != 3) {
			return false;
		}
		return true;
	}

	/**
	 * method to check if Country Continent if from Continent list only
	 * 
	 * @param mapContents : GameMap object
	 * @return true if Country Continent is from Continent List, otherwise false
	 */
	public boolean checkContinent(MapContents mapContents) {

		List<String> listContinentName = new ArrayList<>();
		for (Continent continent : mapContents.getContinentAndItsCountries().keySet())
		{
			listContinentName.add(continent.getContinentName());
		}

		for (Country country : mapContents.getCountryAndNeighbors().keySet())
		{
			if (!listContinentName.contains(country.getBelongsToContinent())) {
				return false;
			}
		}

		return true;
	}
 

	/**
	 * method to check if map is connected continent graph or not
	 * 
	 * @param mapContinent map of continent and Countries
	 * @param visitedMap map of Country and visited value, 1 for visited and 0 if
	 *                   not visited
	 * @return true if graph is connected, otherwise false
	 */
	
	public boolean checkConnectedCountryContinent(MapContents mapContents) {
		
		Map<Country, List<Country>> listTempCountry =  mapContents.getCountryAndNeighbors();
		
		for(Continent c : mapContents.getContinentAndItsCountries().keySet()) {
			Set<String> setConnectedCountries = new HashSet<>();
			//System.out.println("Continent : " + c.getContinentName());
			int i = 0;
			for(Country country : mapContents.getContinentAndItsCountries().get(c)) {
				//System.out.println("*******************Country : " + country.getCountryName());
				
				for(Country nbCountry : country.getNeighbouringCountries()) {
					for(Country tempCountry : listTempCountry.keySet()) {
						if(nbCountry.getCountryName().equalsIgnoreCase(tempCountry.getCountryName())) {
							if(country.getBelongsToContinent().equalsIgnoreCase(tempCountry.getBelongsToContinent())) {
								//System.out.println("*******************Nb Country : " + tempCountry.getCountryName());
								setConnectedCountries.add(tempCountry.getCountryName());
								if(i==0) {
									setConnectedCountries.add(country.getCountryName());
									i = 1;
								}
								
							}
						}
					}
				}				
			}
			
			System.out.println("-----------------------------------------------");
			System.out.println("Continent : " + c.getContinentName());
			for(String countryName : setConnectedCountries) {
				System.out.println("Country Name : " + countryName);
			}
			System.out.println("-----------------------------------------------");
			System.out.println("mapContents.getContinentAndItsCountries().get(c).size() : " + mapContents.getContinentAndItsCountries().get(c).size());
			System.out.println("setConnectedCountries.size() : " + setConnectedCountries.size());
			if(mapContents.getContinentAndItsCountries().get(c).size()!=setConnectedCountries.size()) {
				System.out.println("Continent " + c.getContinentName() + " is Not Connected");
				return false;
			}
			else
			{
				System.out.println("Continent " + c.getContinentName() + " is Connected");
			}
			
		}
		
		return true;
	}
	
/*	public Map<String, Integer> checkConnectedContinentGraph(Map<Continent, List<Country>> mapContinent,
			Map<String, Integer> visitedMap) {

		//visitedMap.put(mapContinent.keySet().iterator().next().getContinentName(), 1);
		for(Entry<Continent, List<Country>> continent : mapContinent.entrySet()) {
			System.out.println("Continent name  : " + continent.getKey().getContinentName());
			List<Country> visitedCountries = new ArrayList<>();
			for(Country country : continent.getValue()) {
				System.out.println("Country name  : " + country.getCountryName());
				int count = 0;
				for(Country neighbour : country.getNeighbouringCountries()) {
					Player player = new Player();
					Country n = player.getSourceCountryFromString(neighbour.getCountryName());
					if(!visitedMap.containsKey(n.getBelongsToContinent()) && (n.getBelongsToContinent().equals(country.getBelongsToContinent())) && !(visitedCountries.contains(n))) {
						visitedMap.put(n.getBelongsToContinent(), 1);
						visitedCountries.add(n);
					}
					else if(visitedMap.containsKey(n.getBelongsToContinent()) && (n.getBelongsToContinent().equals(country.getBelongsToContinent())) && !(visitedCountries.contains(n))) {
						visitedMap.put(n.getBelongsToContinent(), visitedMap.get(n.getBelongsToContinent())+1);
						visitedCountries.add(n);
					}
				}

			}
		}
		System.out.println("***********************************************************");

		for(Entry<String, Integer> a  : visitedMap.entrySet() ) {
			System.out.println(a.getKey() +" : "+ a.getValue());
		}
		return visitedMap;
	}*/

	/**
	 * method to check if all continent have at least one country
	 * 
	 * @param mapContents : GameMap object
	 * @return true if all continent has at least one country, otherwise false
	 */
	public boolean checkContinentCountry(MapContents mapContents) {

		for (Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			if (mapContents.getContinentAndItsCountries().get(continent).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * method to check if country is assigned to only one country
	 * 
	 * @param mapContents : GameMap object
	 * @return true if all countries are assigned to only one country, otherwise
	 *         false
	 */
	public boolean checkUniqueContinentCountry(MapContents mapContents) {

		Map<String, String> counrtyContinentValue = new HashMap<>();
		for(Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			for(Country country : mapContents.getContinentAndItsCountries().get(continent)) {
				if(!counrtyContinentValue.keySet().contains(country.getCountryName())) {
					counrtyContinentValue.put(country.getCountryName(), continent.getContinentName());
				}
				else {
					if(!continent.getContinentName().equalsIgnoreCase(counrtyContinentValue.get(country.getCountryName()))) {
						return false;
					}
				}

			}
		}
		return true;
	}

	/**
	 * method to check if map is connected graph or not
	 * 
	 * @param parent     starting node of the graph
	 * @param mapCountry map of Country and neighboring Countries
	 * @param visitedMap map of Country and visited value, 1 for visited and 0 if
	 *                   not visited
	 * @return true if graph is connected, otherwise false
	 */
	public Map<String, Integer> checkConnectedGraph(Country parent, Map<Country, List<Country>> mapCountry,
			Map<String, Integer> visitedMap) {
		List<Country> nbCountries = new ArrayList<>();
		visitedMap.put(parent.getCountryName().trim(), 1);

		for (Map.Entry<Country, List<Country>> entry : mapCountry.entrySet()) {
			if (parent.getCountryName().equalsIgnoreCase(entry.getKey().getCountryName())) {
				nbCountries = entry.getValue();
			}
		}

		if (nbCountries != null) {
			for (int i = 0; i < nbCountries.size(); i++) {
				if (!visitedMap.containsKey(nbCountries.get(i).getCountryName().trim())) {
					checkConnectedGraph(nbCountries.get(i), mapCountry, visitedMap);
				}
			}
		}

		return visitedMap;
	}

	/**
	 * method to check all the output returned by above described method
	 * 
	 * @param file : File Object
	 */
	public void init(File file) {
		MapValidator mapValidator = new MapValidator();
		Map<String, Integer> visitedMap1 = new HashMap<>();
		MapParseProcessor mapParseProcessor = new MapParseProcessor();
		MapContents mapContents = null;
		BufferedReader bufferReaderForFile = null;
		Map<String, Integer> visitedMapForContinent = new HashMap<>();
		try {
			bufferReaderForFile = new BufferedReader(new FileReader(file));
			mapContents = mapParseProcessor.readMapElements(bufferReaderForFile);


			Map<Country, List<Country>> mapCountry = mapContents.getCountryAndNeighbors();

			for (int i = 0; i < mapContents.getCountryAndNeighbors().keySet().size(); i++) {
				if (visitedMap1.size() != mapContents.getCountryAndNeighbors().keySet().size()) {
					Map<String, Integer> visitedMap = new HashMap<>();
					visitedMap1 = mapValidator.checkConnectedGraph(mapContents.getCountryAndNeighbors().keySet().iterator().next(), mapCountry, visitedMap);
				} else {
					break;
				}
			}

			int connectedCountries = 0;
			for (Map.Entry<String, Integer> entry : visitedMap1.entrySet()) {
				if (entry.getValue() == 1) {
					connectedCountries++;
				}
			}

			
			//visitedMapForContinent=checkConnectedContinentGraph(MapContents.getInstance().getContinentAndItsCountries(),visitedMapForContinent);

			if (mapValidator.checkMapLabel(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as labels are not properly described";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (mapContents.getContinentAndItsCountries().keySet().isEmpty()) {
				validMapFlag = false;
				statusMessage = "Map should have atleast one Continent";
				System.out.println("Message : " + statusMessage);
				return;
			} else {
				validMapFlag = true;
				statusMessage = "Map is valid";
			}

			if (mapContents.getCountryAndNeighbors().keySet().isEmpty()) {
				validMapFlag = false;
				statusMessage = "Map should have atleast one Country";
				System.out.println("Message : " + statusMessage);
				return;
			} else {
				validMapFlag = true;
				statusMessage = "Map is valid";
			}

			if (mapValidator.checkContinent(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Country Continent should be from only Continent List";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (mapValidator.checkContinentCountry(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Every Continent should have atleast one country";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (mapValidator.checkUniqueContinentCountry(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Country can assign to only one Continent";
				System.out.println("Message : " + statusMessage);
				return;
			}

			if (connectedCountries == mapContents.getCountryAndNeighbors().keySet().size()) {
				validMapFlag = true;
				statusMessage = "Map is valid";
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as it is not a connected graph";
				System.out.println("Message : " + statusMessage);
				return;
			}
			
			if (mapValidator.checkConnectedCountryContinent(mapContents)) {
				validMapFlag = true;
			} else {
				validMapFlag = false;
				statusMessage = "Map is invalid as Continents are not connected";
				System.out.println("Message : " + statusMessage);
				return;
			}

			System.out.println("Connected Countries : " + connectedCountries);
			System.out.println("Total Countries : " + mapContents.getCountryAndNeighbors().keySet().size());
			System.out.println("Message : " + statusMessage);
			System.out.println("Valid Map Flag : " + validMapFlag);
		} catch (FileNotFoundException e) {
			validMapFlag = false;
		}
		catch (Exception e) {
			validMapFlag = false;
		}

	}

	/**
	 * This method returns boolean value for Valid Map
	 * 
	 * @return validMapFlag true if map is valid otherwise false
	 */
	public Boolean getValidMapFlag() {
		return validMapFlag;
	}

}
