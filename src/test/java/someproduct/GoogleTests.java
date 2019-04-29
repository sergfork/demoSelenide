package someproduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
    public void verifySearchedWorldInTitle(String searchedWord, String baseURL) {
        open(baseURL);
        new GooglePage().searchFor(searchedWord);

        logger.info("title is: " + title());

        assertThat("Title should contains the searched world", title(), containsString(searchedWord));
    }

    @Test
    @Parameters({"searchedWord", "lookingForDomain", "findOnPages", "baseURL"})
    public void verifySearchedWorldInResults(String searchedWord, String lookingForDomain, int findOnPages, String baseURL) {
        open(baseURL);
        Optional<String> result = new GooglePage()
                .searchFor(searchedWord)
                .searchOnPages(lookingForDomain, findOnPages);
        assertThat(lookingForDomain + " was not found on " + findOnPages + " page(s)",
                result.isPresent(), is(true));
    }

}
