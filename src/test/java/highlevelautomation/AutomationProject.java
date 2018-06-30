package highlevelautomation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationProject {

	WebDriver driver;
	WebElement element;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://mockaroo.com/");
		driver.manage().window().fullscreen();
	}

	 @AfterMethod
	 public void tearDown() throws InterruptedException {
	 Thread.sleep(2000);
	 driver.close();
	 }

	@Test(priority = 1) // Task - 3_4
	public void titleTest_headerTest() {
		// Assert title is correct.
		String title = driver.getTitle();
		System.out.println(title);
		assertEquals(title, "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel");
		// ----------------------------------------
		// Assert Mockaroo is displayed
		element = driver.findElement(By.xpath("//div[@class='brand']"));
		assertTrue(element.isDisplayed());
		// ----------------------------------------
		// realistic data generator is displayed
		element = driver.findElement(By.xpath("//div[@class='tagline']"));
		assertTrue(element.isDisplayed());
	}

	@Test(priority = 2) // Task_5
	public void remove() {
		List<WebElement> elements = driver
				.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));

		for (WebElement webElement : elements) {
			webElement.click();
		}
	}

	@Test(priority = 3) // Task - 6_7
	public void label_button_Check() {
		// Field Name
		element = driver.findElement(By.xpath("//div[@class='column column-header column-name']"));
		assertTrue(element.isDisplayed());
		// // Type
		element = driver.findElement(By.xpath("//div[@class='column column-header column-type']"));
		assertTrue(element.isDisplayed());
		// // Options
		element = driver.findElement(By.xpath("//div[@class='column column-header column-options']"));
		assertTrue(element.isDisplayed());
		// // button enabled
		element = driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']"));
		assertTrue(element.isEnabled());
	}

	@Test(priority = 4) // Task -8_9_10
	public void rowCSVUnix_Check() {
		// Assert that default number of rows is 1000.
		String actual = driver.findElement(By.xpath("//input[@class='medium-number form-control']"))
				.getAttribute("value");
		String expected = "1000";
		assertEquals(actual, expected);
		// ---------------------------
		// Assert thatdefault format selection is CSV
		actual = driver.findElement(By.xpath("//select[@id='schema_file_format']")).getAttribute("value");
		System.out.println(actual);
		expected = "csv";
		assertEquals(actual, expected);
		// -----------------------------
		// Assert that Line Ending is Unix(LF)
		actual = driver.findElement(By.xpath("//select[@id='schema_line_ending']/option")).getText();
		System.out.println(actual);
		expected = "Unix (LF)";
		assertEquals(actual, expected);
	}

	@Test(priority = 5) // Task - 11
	public void checked_Uncheked() {
		element = driver.findElement(By.xpath("//input[@id='schema_include_header']"));
		assertTrue(element.isSelected());

		element = driver.findElement(By.xpath("//input[@id='schema_bom']"));
		assertFalse(element.isSelected());
	}

	@Test(priority = 6) // Task -12_13_14
	public void addCity() throws InterruptedException {
		// click on "Add another field" button
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		// put keyword "City" in Field Name
		driver.findElement(By.xpath(
				"(//div[@id='fields']//input[starts-with(@id,'schema_columns_attributes_')][contains(@id,'name')])[last()]"))
				.sendKeys("City");
		// click on dialog box
		driver.findElement(By.xpath("//div[@class='column-fields']/div[2]/div[1]/div[7]/div[3]/i")).click();
		// assert that dialog box is displayed
		assertTrue(driver.findElement(By.xpath("//body[@class='mockaroo modal-open']")).isDisplayed());

		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("City");
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 7) // Task - 15
	public void addCountry() throws InterruptedException {
		// click on "Add another field" button
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		Thread.sleep(1000);
		// put keyword "Country" in Field Name
		driver.findElement(By.xpath(
				"(//div[@id='fields']//input[starts-with(@id, 'schema_columns_attributes_')][contains(@id,'name')])[last()]"))
				.clear();
		driver.findElement(By.xpath(
				"(//div[@id='fields']//input[starts-with(@id, 'schema_columns_attributes_')][contains(@id,'name')])[last()]"))
				.sendKeys("Country");
		// click on dialog box
		driver.findElement(By.xpath("(//div[@class='fields']//input[@class='btn btn-default'])[last()]")).click();
		// choose country
		// driver.findElement(By.xpath("//input[@id='type_search_field']")).clear();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("Country");
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
		Thread.sleep(1500);

	}

	@Test(priority = 8) // Task-16
	public void downloadData() throws InterruptedException {
		// click on download button
		driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
		Thread.sleep(1500);
	}

	@Test(priority = 9) // Task-17_18_19_20_21_22_23
	public void uploadFileToEclipse() throws IOException {
		// read file
		String locationOfFile = "C:\\Users\\Maftuna\\Desktop\\Cybertek\\MOCK_DATA.csv";
		FileReader file = new FileReader(locationOfFile);
		BufferedReader br = new BufferedReader(file);
		// --------------------------------------------------
		// firstLine matches
		String line = br.readLine();
		String expected = "City,Country";
		assertEquals(line, expected);
		// line is 1000 cities --> ArrayList | countries --> ArrayList
		List<String> cities = new ArrayList<String>();
		List<String> countries = new ArrayList<String>();
		int count = 0;
		while ((line = br.readLine()) != null) {
			cities.add(line.substring(0, line.indexOf(",")));
			countries.add(line.substring(line.indexOf(",")));
			count++;
		}
		assertTrue(count == 1000);
		// -------------------------------------------------------------
		// find the city with long name and short name Task 22
		Collections.sort(cities);
		System.out.println(cities);
		int shortestLength = cities.get(0).length();
		String shortNamedCity = "";
		int longestLength = cities.get(0).length();
		String longNamedCity = "";
		for (int i = 0; i < cities.size(); i++) {
			if (shortestLength > cities.get(i).length()) {
				shortestLength = cities.get(i).length();
				shortNamedCity = cities.get(i);
			}
			if (longestLength < cities.get(i).length()) {
				longestLength = cities.get(i).length();
				longNamedCity = cities.get(i);
			}
		}
		System.out
				.println("City with longest name is " + longNamedCity + " and it has " + longestLength + " characters");
		System.out.println(
				"City with shorest name is " + shortNamedCity + " and it has " + shortestLength + " characters");
		// -----------------------------------------------------------------------------
		// number of times a country appears
		int num = 0;
		for (int i = 1; i < countries.size(); i++) {
			String country1 = countries.get(i);
			for (int j = 1; j < countries.size(); j++) {
				String country2 = countries.get(j);
				if (country1.equals(country2)) {
					num++;
				}

			}
			System.out.println(country1 + " appears " + num + " times");
			num = 0;
		}

		// From file add all Cities to citiesSet HashSet
		Set<String> citiesSet = new HashSet();
		citiesSet.addAll(cities);
		// count of unique cities in arrayList
		List<String> uniqueCities = new ArrayList<String>();
		uniqueCities.add(cities.get(0));

		for (int i = 0; i < cities.size(); i++) {
			String city1 = cities.get(i);
			if (!uniqueCities.contains(city1)) {
				uniqueCities.add(city1);
			}
		}
		// System.out.println(uniqueCities.toString() + " " + uniqueCities.size());
		assertEquals(citiesSet.size(), uniqueCities.size());
		// From file add all countries to countrySet HashSet
		Set<String> countrySet = new HashSet(countries);
		countrySet.addAll(countries);
		// unique countries in arrayList
		List<String> uniqueCountries = new ArrayList<String>();
		uniqueCountries.add(countries.get(0));

		for (int i = 0; i < countries.size(); i++) {
			String country = countries.get(i);
			if (!uniqueCountries.contains(country)) {
				uniqueCountries.add(country);
			}
		}
		assertEquals(uniqueCountries.size(), countrySet.size());
	}

}
