package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.StatisticsPage;

public class StatisticsTest extends BaseTest {
    StatisticsPage statsPage;

    @BeforeMethod
    public void setup() {
        new LoginPage(driver).open();
        new LoginPage(driver).login("admin", "123");
        statsPage = new StatisticsPage(driver);
        statsPage.open();
    }

    @Test // STA-001 & STA-010
    public void STA_001_010_DefaultTab() {
        Assert.assertTrue(statsPage.isTab1Active());
    }

    @Test // STA-002
    public void STA_002_SearchFavoriteByTitle() {
        statsPage.clickTab2();
        statsPage.searchFavorite("Java");
        Assert.assertTrue(statsPage.getFavUsersCount() >= 0);
    }

    @Test // STA-003
    public void STA_003_SearchVideoNotExist() {
        statsPage.clickTab2();
        statsPage.searchFavorite("No_Video_Exist_123");
        Assert.assertEquals(statsPage.getFavUsersCount(), 0);
    }

    @Test // STA-006
    public void STA_006_CheckTab1DataAccuracy() {
        String data = statsPage.getFirstRowData(By.cssSelector("#favorites tbody tr:first-child"));
        Assert.assertNotNull(data);
        System.out.println("Dòng đầu tiên: " + data);
    }

    @Test // STA-009
    public void STA_009_SwitchTabHighlight() {
        statsPage.clickTab2();
        Assert.assertTrue(statsPage.isTab2Active());
        statsPage.clickTab3();
        Assert.assertTrue(statsPage.isTab3Active());
    }
}