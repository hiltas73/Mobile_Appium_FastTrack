package com.cydeo.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

public class Driver {

    public static AppiumDriver driver;
    public static URL url;
    public static UiAutomator2Options options = new UiAutomator2Options();

    private Driver() {

    }

    public static AppiumDriver getDriver(String platformType) {
        String platform = ConfigurationReader.getProperty(platformType);

        String testDirectory = System.getProperty("user.dir");

        if (Objects.isNull(driver)) {

            switch (platform) {
                case "local-android-sauceApp":  // get the configuration from SauceLabs
                    options.setApp(testDirectory + "/sauceLab.apk");
                    options.setAppActivity("com.swaglabsmobileapp.MainActivity");
                    options.setAppPackage("com.swaglabsmobileapp");
                    try {
                        url = new URL("http://localhost:4723");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new AndroidDriver(url, options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                    break;
                case "remote-android-sauceApp":
                    MutableCapabilities caps = new MutableCapabilities();
                    caps.setCapability("platformName", "Android");
                    caps.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
                    caps.setCapability("appium:platformVersion", "current_major");
                    caps.setCapability("appium:automationName", "UiAutomator2");
                    MutableCapabilities sauceOptions = new MutableCapabilities();
                    // username and access key is unique to the user
                    sauceOptions.setCapability("username", "oauth-hiltas73-XXXXX");
                    sauceOptions.setCapability("accessKey", "super-secret-key");
                    sauceOptions.setCapability("build", "<your build id>");
                    sauceOptions.setCapability("name", "<your test name>");
                    sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
                    caps.setCapability("sauce:options", sauceOptions);
                    // add app activity and package
                    caps.setCapability("appPackage", "com.swaglabsmobileapp");
                    caps.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");


// Start the session
                    try {
                        url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new AndroidDriver(url, caps);

// replace with commands and assertions
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String jobStatus = "passed"; // or "failed"

// end the session
                    // driver.executeScript("sauce:job-result=" + jobStatus);
                    //driver.quit();
                    break;

                case "remoteIOS-sauceApp":  // get the configuration from SauceLabs
                    MutableCapabilities capsI = new MutableCapabilities();
                    capsI.setCapability("platformName", "iOS");
                    // The filename of the mobile app
                    capsI.setCapability("appium:app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");
                    capsI.setCapability("appium:deviceName", "iPhone.*");
                    capsI.setCapability("appium:automationName", "XCUITest");
                    MutableCapabilities sauceOptionsI = new MutableCapabilities();
                    sauceOptionsI.setCapability("username", "oauth-hiltas73-XXXXX");
                    sauceOptionsI.setCapability("accessKey", "super-secret-key");
                    sauceOptionsI.setCapability("build", "<your build id>");
                    sauceOptionsI.setCapability("name", "<your test name>");
                    sauceOptionsI.setCapability("deviceOrientation", "PORTRAIT");
                    capsI.setCapability("sauce:options", sauceOptionsI);

// Start the session
                    try {
                        url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new IOSDriver(url, capsI);

// replace with commands and assertions
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // String jobStatus = "passed"; // or "failed"

// end the session
                    //driver.executeScript("sauce:job-result=" + jobStatus);
                    //driver.quit();
            }

        }
        return driver;
    }

    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.quit();
            driver = null;
        }
    }
}
