package com.daci.coronavirustracker.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class CountryCovidRecord {
	private String stateName;
	private String countryName;
	private Map<String, Integer> totalCasesPerDay = new LinkedHashMap<>();

	public CountryCovidRecord(String stateName, String countryName) {
		this.countryName = countryName;
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void addCasesForADay(String day, Integer totalCases) {
		this.totalCasesPerDay.put(day, totalCases);
	}

	@Override
	public String toString() {
		return "CountryCovidRecord [stateName=" + stateName + ", countryName="
				+ countryName + ", totalCasesPerDay=" + totalCasesPerDay + "]";
	}

}
