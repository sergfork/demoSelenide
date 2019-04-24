package someproduct.steps;

import com.codeborne.selenide.Configuration;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import someproduct.SearchResultsPage;

import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static someproduct.Utils.searchOnThePage;

public class GoogleSearchStepDefinitions {
    private String keyword;

    @Given("an open browser with google.com")
    public void openGoogleSearch() {
        Configuration.browser = "chrome";
        Configuration.reportsFolder = "target/surefire-reports";
        open("https://google.com/ncr");
    }

    @When("a keyword (.*) is entered in input field")
    public void enterKeyword(String keyword) {
        this.keyword = keyword;
        $(By.name("q")).val(keyword).pressEnter();
    }

    @Then("open the first link on search results page")
    public void topTenMatchesShouldBeShown() {
        String beforeTitle = title();
        $("#ires .g").find(".r a").click();
        assertThat(title(), is(not(beforeTitle)));
    }

    @Then("title contains (.*) searched word")
    public void theFirstOneShouldContainKeyword(String expectedText) {
        assertThat(title().toLowerCase(), containsString(expectedText.toLowerCase()));
    }

    @Then("verify that there is (.*) domain on (\\d+) search results pages")
    public void theFirstOneShouldContainKeyword(String expectedDomain, int expectedPages) {
        int currentPage = 1;
        Optional<String> result = Optional.empty();
        while (currentPage < expectedPages && !result.isPresent()) {
            SearchResultsPage results = new SearchResultsPage();
            List<String> foundTexts = results.getResults().texts();
            result = searchOnThePage(foundTexts, expectedDomain);
            currentPage = results.nextPage();
        }
        assertThat(expectedDomain + " was not found on " + expectedPages + " page(s)",
                result.isPresent(), is(true));
    }
}
