package com.shane.appiumdemo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2019/1/2 14:45
 */
public class MultiTouchTest {
    AndroidDriver driver;
    Dimension dimension;

    @BeforeClass
    public void init() throws MalformedURLException {
        File userDir = new File(System.getProperty("user.dir"));
        File appPath = new File(userDir, "apps/com.mobeta.android.demodslv.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM, "android");//平台名称
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");//平台版本号
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.204.101:5555");//设备名称 adb devices可查看

        //在adb shell中使用命令: dumpsys window windows|grep -E 'mCurrentFocus'
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.mobeta.android.demodslv");//应用包名-Android独有
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Launcher");//Activity

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

    /**
     * 拖拽测试
     */
    @Test
    public void dragAndDropTest() {
        driver.findElement(By.id("com.mobeta.android.demodslv:id/activity_title")).click();

        List<WebElement> elements = driver.findElements(By.id("com.mobeta.android.demodslv:id/drag_handle"));
        AndroidElement ele1 = (AndroidElement) elements.get(0);
        AndroidElement ele3 = (AndroidElement) elements.get(2);

        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        LongPressOptions longPressOptions = LongPressOptions.longPressOptions();
        longPressOptions.withElement(ElementOption.element(ele1));
        touchAction
                .longPress(longPressOptions)//长按
                .moveTo(ElementOption.element(ele3))//移动,基于ElementOption得到坐标
                .release()//释放
                .perform();//触发执行这个动作

    }

    /**
     * 水平移动/滑动
     */
    @Test
    public void horizontalTest() {
        driver.findElement(By.xpath("//android.widget.TextView[@text='Heteroheight']")).click();
        WebElement element = driver.findElement(By.id("com.mobeta.android.demodslv:id/drag_handle"));

        AndroidTouchAction touchAction = new AndroidTouchAction(driver);
        LongPressOptions longPressOptions = LongPressOptions.longPressOptions();
        longPressOptions.withElement(ElementOption.element(element));

        touchAction.longPress(longPressOptions)
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(100, element.getLocation().getY()))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .release()
                .perform();
    }

    @Test
    public void verticalTest() {
        int fromX = dimension.getWidth() / 2;
        int fromY = (int) (dimension.getHeight() * 0.8);
        int toY = (int) (dimension.getHeight() * 0.2);
        driver.findElement(By.id("com.mobeta.android.demodslv:id/activity_title")).click();

        driver.findElement(By.id("com.mobeta.android.demodslv:id/drag_handle"));
        AndroidTouchAction touchAction = new AndroidTouchAction(driver);


/*        LongPressOptions longPressOptions = LongPressOptions.longPressOptions();
        longPressOptions.withPosition(PointOption.point(fromX, fromY));*/

        touchAction.press(PointOption.point(fromX, fromY))
                .moveTo(PointOption.point(fromX, toY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .release()
                .perform()
        ;
    }
}
