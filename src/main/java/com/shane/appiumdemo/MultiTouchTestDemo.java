package com.shane.appiumdemo;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2019/1/2 14:45
 */
public class MultiTouchTestDemo {
    AndroidDriver driver;
    Dimension dimension;

    @BeforeClass
    public void init() throws MalformedURLException {
        File userDir = new File(System.getProperty("user.dir"));
        File appPath = new File(userDir, "apps/com.the511plus.MultiTouchTester.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability(MobileCapabilityType.PLATFORM, "android");//平台名称
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");//平台版本号
        //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.204.101:5555");//设备名称 adb devices可查看
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.145.101:5555");//shane@home

        //在adb shell中使用命令: dumpsys window windows|grep -E 'mCurrentFocus'
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.the511plus.MultiTouchTester");//应用包名-Android独有
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "MultiTouchTester");//Activity

        //如果Android平台中未安装此App,则将主动安装
        capabilities.setCapability(MobileCapabilityType.APP, appPath.getAbsolutePath());

        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        dimension = driver.manage().window().getSize();
    }

    @AfterClass
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void multiTouchTest() {
        int width = dimension.getWidth();
        int height = dimension.getHeight();

        int x1 = (int) (width * 0.2);
        int y1 = (int) (height * 0.2);

        int x2 = (int) (width * 0.4);
        int y2 = (int) (height * 0.4);

        int x3 = (int) (width * 0.6);
        int y3 = (int) (height * 0.6);

        int x4 = (int) (width * 0.8);
        int y4 = (int) (height * 0.8);

        int x5 = (int) (width * 0.5);
        int y5 = (int) (height * 0.5);

        AndroidTouchAction action1 = new AndroidTouchAction(driver);
        action1.longPress(PointOption.point(x1, y1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release();

        AndroidTouchAction action2 = new AndroidTouchAction(driver);
        action1.longPress(PointOption.point(x2, y2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release();

        AndroidTouchAction action3 = new AndroidTouchAction(driver);
        action1.longPress(PointOption.point(x3, y3)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release();

        AndroidTouchAction action4 = new AndroidTouchAction(driver);
        action1.longPress(PointOption.point(x4, y4)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release();

        AndroidTouchAction action5 = new AndroidTouchAction(driver);
        action1.longPress(PointOption.point(x5, y5)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).release();


        MultiTouchAction multiTouchAction = new MultiTouchAction(driver);
        multiTouchAction.add(action1).add(action2).add(action3).add(action4).add(action5).perform();
    }

}
