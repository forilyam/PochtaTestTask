import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    @Test
    public void searchYandexMarket() {
        //Создаем экземпляр WebDriver
        WebDriver driver = new FirefoxDriver();
        //Разворачиваем окно браузера во весь экран
        driver.manage().window().maximize();
        //Открываем гугл
        driver.get("https://www.google.ru");
        //Находим элемент поле поиска по атрибуту name
        WebElement searchField = driver.findElement(By.name("q"));
        //Вводим текст для поиска
        searchField.sendKeys("яндекс маркет");
        searchField.submit();
        //ждем загрузки результатов поиска в течении 10 секунд
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Ищем первую страницу в результатах поиска
        WebElement firstSearchResult = driver.findElement(By.xpath("(//*[@id='rso']//h3)[1]"));
        //Проверяем, что первая страница ссылается на яндекс маркет
        String firstSearchResultText = firstSearchResult.getText();
        Assert.assertEquals(firstSearchResultText, "Яндекс.Маркет — выбор и покупка товаров из проверенных ...");
        //Открываем яндекс маркет
        firstSearchResult.click();
        //Закрываем WebDriver
        driver.quit();
    }
}
