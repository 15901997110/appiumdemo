package com.shane.appiumdemo;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2019/1/2 14:45
 */
public class demo1 {
    AndroidDriver driver;
    @BeforeClass
    public void init() throws MalformedURLException {
        DesiredCapabilities capabilities=new DesiredCapabilities();
        //capabilities.setCapability(MobileCapabilityType.PLATFORM,"android");//平台名称
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"8.0");//平台版本号
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"192.168.204.101:5555");//设备名称 adb devices可查看
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.android.calculator2");//应用包名-Android独有
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.android.calculator2.Calculator");//Activity

        driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void addTest() throws InterruptedException {
        //Thread.sleep(5*1000);
        driver.findElement(By.id("com.android.calculator2:id/digit_7")).click();
        driver.findElement(By.id("com.android.calculator2:id/op_add")).click();
        driver.findElement(By.id("com.android.calculator2:id/digit_3")).click();
        String result=driver.findElement(By.id("com.android.calculator2:id/result")).getText();
        Assert.assertEquals(result,"10");
    }
}
