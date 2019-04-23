package someproduct;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;

public class GoogleTests {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTests.class);
    private static final String BROWSER = System.getenv("browser");
    private static String searchedWord = System.getenv("searchedWord");
    private static int EXPECTED_PAGE = Integer.getInteger("findOnPages");
    private static String EXPECTED_DOMAIN = System.getenv("lookingFordomain");

    public GoogleTests() {
        switch (BROWSER) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                break;
            default:
                fail("Incorrect browser. Please check browser name");
                break;
        }
    }

    @Test
    public void verifySerchedWorldInTitle() {
        open("https://google.com/ncr");
        new GooglePage().searchFor(searchedWord);

        logger.info("title is: " + title());

        assertThat("Title should contains the searched world", title(), containsString(searchedWord));
    }

    @Test
    public void verifySerchedWorldInResults() {
        open("https://google.com/ncr");
        new GooglePage().searchFor(searchedWord);

        int currentPage = 1;
        Optional<String> result = Optional.empty();
        while(currentPage < EXPECTED_PAGE && !result.isPresent()) {
            SearchResultsPage results = new SearchResultsPage();
            List<String> foundTexts = results.getResults().texts();
            result = foundTexts.stream().filter(l -> l.contains(EXPECTED_DOMAIN)).findFirst();
            if(result.isPresent()) {
                logger.info("page is: " + currentPage);
                logger.info(result.toString());
            }
            currentPage = results.nextPage();
        }
        assertThat(EXPECTED_DOMAIN + " was not found on " + EXPECTED_PAGE + " page(s)",
                result.isPresent(), is(true));
    }
}
