package ru.sfedu.grade;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws MalformedURLException {


      //  WebDriver driver;

  /*      DesiredCapabilities capability = new DesiredCapabilities();

      capability.setBrowserName("chrome");

       capability.setCapability("networkConnectionEnabled", false);
        capability.setCapability("browserConnectionEnabled", false);
       capability.setVersion("74.0.3729.108");

      //  capability.setCapability("browserName", "chrome");
        capability.setPlatform(Platform.LINUX);


        RemoteWebDriver   driver = new RemoteWebDriver(new URL("http://grade-dev.mmcs.sfedu.ru:5901/wd/hub"), capability);

        driver.get("http://internetka.in.ua/selenium-compound-class/");*/
    //    if(driver.getPageSource().contains("Selenium: поиск элемента по составному имени класса"))
      //      System.out.println(((RemoteWebDriver) driver).findElementByClassName("entry-title"));
    }

}
