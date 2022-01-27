package com.daci.coronavirustracker.business;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daci.coronavirustracker.models.CountryCovidRecord;
import com.opencsv.CSVReader;

@Service
public class CoronaVirusServiceImpl implements CoronaVirusService {

	private static String CORONA_VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private static List<CountryCovidRecord> countryRecords = new ArrayList<>();

	/**
	 * Fetches the data from an API and sends it further to processing.
	 */
	private void fetchData() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest
				.newBuilder(URI.create(CORONA_VIRUS_DATA)).build();

		try {
			HttpResponse response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			processResponseBody(response.body().toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates the field countryRecords with data from the requset response
	 * body.
	 * 
	 * @param responseBody
	 *            The response body of the request that have been made to fetch
	 *            the data
	 */
	private void processResponseBody(String responseBody) {
		CSVReader reader = new CSVReader(new StringReader(responseBody));
		try {
			List<String[]> allData = reader.readAll();
			List<String> headerRow = new ArrayList<>(
					Arrays.asList(allData.get(0)));

			allData.stream().skip(1) // skips the header row
					.forEach(countryData -> {
						CountryCovidRecord record = new CountryCovidRecord(
								countryData[0], countryData[1]);
						for (int i = 4; i < countryData.length; i++) {
							record.addCasesForADay(headerRow.get(i),
									Integer.parseInt(countryData[i]));
						}
						countryRecords.add(record);
					});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
