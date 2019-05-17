import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SecondTest {

    @Test
    public void searchHooverOnYandexMarket() throws InterruptedException {
        //Создаем экземпляр WebDriver
        WebDriver driver = new FirefoxDriver();
        //Разворачиваем окно браузера во весь экран
        driver.manage().window().maximize();
        //Открываем яндекс маркет
        driver.get("https://market.yandex.ru");
        WebElement searchField = driver.findElement(By.id("header-search"));
        //Вводим текст для поиска
        searchField.sendKeys("пылесосы");
        searchField.submit();

        //ждем загрузки результатов поиска в течении 30 секунд
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //выбираем категорию "пылесосы"
        driver.findElement(By.xpath("//span[.='Пылесосы']")).click();

        //Ждем 30 секунд до того, когда кнопка "Все фильтры" загрузится и нажимаем на кнопку
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[.='Все фильтры']"))).click();

        //Ждем загрузки страницы с фильтрами в течении 30 секунд
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        //Выбираем фильтр по производителю "Polaris" и "VITEK"
        driver.findElement(By.xpath("//span/label[.='Polaris']/preceding-sibling::*[1]")).click();
        driver.findElement(By.xpath("//span/label[.='VITEK']/preceding-sibling::*[1]")).click();
        //Выбираем фильтр по цене до 6000 включительно
        driver.findElement(By.id("glf-priceto-var")).sendKeys("6001");
        Thread.sleep(2000);
        //Нажимаем кнопку "Показать подходящие", чтобы применить фильтры
        driver.findElement(By.xpath("//span[.='Показать подходящие']/ancestor::*[1]")).click();


        //Ждем загрузки страницы 30 секунд
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        //Ждем когда загрузятся результаты и проверяем, что они загрузились
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@data-id]"));
        Assert.assertFalse(searchResults.isEmpty());

        //Проверяем, что цена соотвествует цене, указанной в фильтре
        WebElement searchResultPriceFilter = driver.findElement(By.xpath("//input[@value='6001']"));
        Assert.assertTrue(searchResultPriceFilter.isDisplayed());
        //Проверяем производителей
        Assert.assertTrue(driver.findElement(By.name("Производитель Polaris")).isSelected());
        Assert.assertTrue(driver.findElement(By.name("Производитель VITEK")).isSelected());

        //Закрываем WebDriver
        driver.quit();
    }
}
