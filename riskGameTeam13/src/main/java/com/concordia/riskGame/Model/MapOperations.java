package com.concordia.riskGame.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.concordia.riskGame.entity.Continent;
import com.concordia.riskGame.entity.Country;

/**
 * This class performs different operations for the map file
 * 
 * @author Sandeep
 *
 */
public class MapOperations {
	
	private StringBuilder mapFileContents;
	
	
	/**
	 * This method creates .map file after taking inputs from the user
	 * @param mapContents
	 * @param fileName
	 */
	public void writeMapFile(MapContents mapContents,String fileName) {
		mapFileContents = new StringBuilder();
		mapFileContents.append("[MAP]");
		mapFileContents.append("\n");
		mapFileContents.append("[Continents]\n");
		for(Continent continent : mapContents.getContinentAndItsCountries().keySet()) {
			mapFileContents.append(continent.getContinentName());
			mapFileContents.append("=");
			mapFileContents.append(continent.getNumberOfCountries());
			mapFileContents.append("\n");
		}
		mapFileContents.append("\n[Territories]\n");
		for(Map.Entry<Country, List<Country>> countryAndNeighbours : mapContents.getCountryAndNeighbors().entrySet()) {
			mapFileContents.append("\n"+countryAndNeighbours.getKey().getCountryName()+",");
			mapFileContents.append("0,0");
			
			List<Country> neighbours = countryAndNeighbours.getValue();
			mapFileContents.append(neighbours.get(0).getBelongsToContinent());
			for(Country country : neighbours) {
			mapFileContents.append(","+country.getCountryName());
			}
		}
		fileName = fileName + ".map";
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            out.print(mapFileContents);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * This method returns map contents in String builder
	 * @return mapFileContents Map File content
	 */
	public StringBuilder getMapFileContents() {
		return mapFileContents;
	}
}