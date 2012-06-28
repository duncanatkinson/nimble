package com.felthat.testnimble;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InputFieldIT {

	  private static ChromeDriverService service;
	  private WebDriver driver;

	  @BeforeClass
	  public static void createAndStartService() throws IOException {
	    service = ChromeDriverService.createDefaultService();
	    service.start();
	  }

	  @AfterClass
	  public static void createAndStopService() {
	    service.stop();
	  }

	  @Before
	  public void createDriver() {
	    driver = new RemoteWebDriver(service.getUrl(),
	        DesiredCapabilities.chrome());
	  }

	  @After
	  public void quitDriver() {
	    driver.close();
	  }
	
	
	@Test
	public void checkRendering(){
		driver.get("http://localhost:9090/test-nimble/index.jsp");
		WebElement field = driver.findElement(By.id("name"));
		field.sendKeys("TESTIING");
		assertTrue(driver.getPageSource().contains("<h1>Text Field Tests</h1>"));
	}

	@Test
	public void checkRenderingAndStore(){
		System.out.println(driver.getTitle());
		driver.get("http://localhost:9090/test-nimble/index.jsp");
		WebElement field = driver.findElement(By.id("name"));
		field.sendKeys("Mr Cheese");
		field.sendKeys(Keys.TAB);
		field = driver.switchTo().activeElement();
		field.sendKeys("21");
		field.sendKeys(Keys.TAB);
		field = driver.switchTo().activeElement();
		field.sendKeys("Male");
		field.sendKeys(Keys.TAB);
		field = driver.switchTo().activeElement();
		field.sendKeys(Keys.ARROW_DOWN);
		field.sendKeys(Keys.ARROW_DOWN);
		field.sendKeys(Keys.TAB);
		
		waitForAjaxFinished(driver);
		
		driver.navigate().refresh();
		
		field = driver.findElement(By.id("name"));
		assertEquals("Mr Cheese",field.getAttribute("value"));
		field = driver.findElement(By.id("age"));
		assertEquals("21",field.getAttribute("value"));
		field = driver.findElement(By.id("sex"));
		assertEquals("Male",field.getAttribute("value"));
		field = driver.findElement(By.id("make"));
		assertEquals("Porsche",field.getAttribute("value"));
	}
	private void waitForAjaxFinished(WebDriver driver) {
		new WebDriverWait(driver, 5).until(new ExpectedCondition<WebElement>(){
					@Override
					public WebElement apply(WebDriver d) {
						WebElement element = d.findElement(By.id("ajaxStatus"));
						String text = element.getText();
						if("sent".equals(text)){
							return element;
						}else{
							return null;
						}
					}});
	}
}
