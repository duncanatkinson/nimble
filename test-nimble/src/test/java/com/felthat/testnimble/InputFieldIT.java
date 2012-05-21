package com.felthat.testnimble;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InputFieldIT {

	private List<WebDriver> webDrivers;
	
	@Before
	public void setup(){
		webDrivers = new ArrayList<WebDriver>();
		FirefoxProfile profile = new FirefoxProfile();
		webDrivers.add(new FirefoxDriver(profile));
		webDrivers.add(new InternetExplorerDriver());
	}
	
	@After
	public void cleanUp(){
		for(WebDriver d:webDrivers){
//			d.quit();
		}
	}
	
	
	@Test
	public void checkRenderingAndStore(){
		for(WebDriver driver : webDrivers){
			System.out.println(driver.getTitle());
			driver.get("http://localhost:9090/test-nimble/test-all.jsp");
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
			driver.quit();
		}
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
