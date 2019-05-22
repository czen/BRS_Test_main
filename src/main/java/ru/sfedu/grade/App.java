package ru.sfedu.grade;

import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" +System.getenv("Driver_Path"));
        WebDriver driver;
        System.out.println( "Hello World!");


        DesiredCapabilities capability = new DesiredCapabilities();
     //   DesiredCapabilities capability = DesiredCapabilities.firefox();
     //  capability.setBrowserName("chrome");

    //    capability.setCapability("networkConnectionEnabled", false);
    //    capability.setCapability("browserConnectionEnabled", false);
    //   capability.setVersion("74.0.3729.108");
     //  capability.setJavascriptEnabled(true);
    //   capability.setAcceptInsecureCerts(false);
        capability.setCapability("browserName", "chrome");
//        capability.setPlatform(Platform.LINUX);
      //  File file = new File("/home/user/chromedriver");
    //    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, file.getAbsolutePath());

        driver = new RemoteWebDriver(new URL("http://grade-dev.mmcs.sfedu.ru:5901/wd/hub"), capability);

        driver.get("http://internetka.in.ua/selenium-compound-class/");
    //    if(driver.getPageSource().contains("Selenium: поиск элемента по составному имени класса"))
      //      System.out.println(((RemoteWebDriver) driver).findElementByClassName("entry-title"));
  
    /*    try{
            Properties env = new Properties();
            env.load(Runtime.getRuntime().exec("cmd.exe /c set").getInputStream());
            String myEnvVar = (String)env.get("Driver_Path");
            System.out.println(myEnvVar);
        }
        catch(IOException bomErr){
            System.out.println(bomErr);
        }*/
    }

}
