package someproduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class GoogleTests {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTests.class);

    @Parameters({"searchedWord", "baseURL"})
    @Test
    public void verifySerchedWorldInTitle(String searchedWord, String baseURL) {
        open(baseURL);
        new GooglePage().searchFor(searchedWord);

        logger.info("title is: " + title());

        assertThat("Title should contains the searched world", title(), containsString(searchedWord));
    }

    @Test
    @Parameters({"searchedWord", "lookingFordomain", "findOnPages", "baseURL"})
    public void verifySerchedWorldInResults(String searchedWord, String lookingFordomain, int findOnPages, String baseURL) {
        open(baseURL);
        new GooglePage().searchFor(searchedWord);

        int currentPage = 1;
        Optional<String> result = Optional.empty();
        while (currentPage < findOnPages && !result.isPresent()) {
            SearchResultsPage results = new SearchResultsPage();
            List<String> foundTexts = results.getResults().texts();
            result = Utils.searchOnThePage(foundTexts, lookingFordomain);
            if(result.isPresent()) {
                logger.info("page is: " + currentPage);
                logger.info(result.toString());
            }
            currentPage = results.nextPage();
        }
        assertThat(lookingFordomain + " was not found on " + findOnPages + " page(s)",
                result.isPresent(), is(true));
    }

}
