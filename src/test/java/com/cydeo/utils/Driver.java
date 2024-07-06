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
    private Driver(){

    }

    public static AppiumDriver getDriver(String platformType){
        String platform = ConfigurationReader.getProperty(platformType);

        String testDirectory = System.getProperty("user.dir");

        if(Objects.isNull(driver)){

            switch (platform){
                case "local-android-sauceApp":
                    options.setApp(testDirectory + "/sauceLab.apk");
                    options.setAppActivity("com.swaglabsmobileapp.MainActivity");
                    options.setAppPackage("com.swaglabsmobileapp");
                    try {
                        url = new URL("http://localhost:4723");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new AndroidDriver(url,options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                    break;
                case "remote-android-sauceApp":
                    MutableCapabilities caps = new MutableCapabilities();
                    caps.setCapability("platformName", "Android");
                    // change Application part
                    caps.setCapability("appium:app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
                    caps.setCapability("appium:deviceName", "Google Pixel 3 GoogleAPI Emulator");
                    caps.setCapability("appium:platformVersion", "11.0");
                    caps.setCapability("appium:automationName", "UiAutomator2");
                    caps.setCapability("appPackage","com.swaglabsmobileapp");
                    caps.setCapability("appActivity","com.swaglabsmobileapp.MainActivity");
                    // add app activity and package
                    MutableCapabilities sauceOptions = new MutableCapabilities();
                    sauceOptions.setCapability("appiumVersion", "2.0.0");
                    // username and access key is unique to the user
                    sauceOptions.setCapability("username", "secret-user");
                    sauceOptions.setCapability("accessKey", "super-secret-password");
                    sauceOptions.setCapability("build", "sauceLab123");
                    sauceOptions.setCapability("name", "smokeTest");
                    sauceOptions.setCapability("deviceOrientation", "PORTRAIT");
                    caps.setCapability("sauce:options", sauceOptions);

                    try {
                        url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new AndroidDriver(url, caps);
                    break;
                case "remoteIOS-sauceApp":
                    MutableCapabilities capsI = new MutableCapabilities();
                    capsI.setCapability("platformName", "iOS");
                    capsI.setCapability("appium:app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");  // The filename of the mobile app
                    capsI.setCapability("appium:deviceName", "iPhone.*");
                    capsI.setCapability("appium:automationName", "XCUITest");
                    MutableCapabilities sauceOptionsI = new MutableCapabilities();

                    sauceOptionsI.setCapability("username", "secret-user");
                    sauceOptionsI.setCapability("accessKey", "super-secret-password");
                    sauceOptionsI.setCapability("build", "Test123");
                    sauceOptionsI.setCapability("name", "IOS_Test");
                    sauceOptionsI.setCapability("deviceOrientation", "PORTRAIT");
                    capsI.setCapability("sauce:options", sauceOptionsI);
                    try {
                        url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    driver = new IOSDriver(url, capsI);
                    break;
            }

        }
        return driver;
    }

    public static void closeDriver(){
        if(Objects.nonNull(driver)){
            driver.quit();
            driver = null;
        }
    }
}
