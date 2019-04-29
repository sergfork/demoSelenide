package someproduct;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class SearchResultsPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);

    private static final String CURRENT_NUMBER_OF_PAGE_LOC = "#foot #nav td.cur";
    private static final String NEXT_PAGE_LOC = "#foot #nav #pnnext";
    private static final String RESULT_LOC = "#ires .g";

    public ElementsCollection getResults() {
        return $$(RESULT_LOC);
    }

    public int nextPage() {
        SelenideElement currentPageNumber = $(CURRENT_NUMBER_OF_PAGE_LOC);
        Integer orderNumber = Integer.parseInt(currentPageNumber.text());
        logger.info("Current page before is: " + orderNumber);
        orderNumber++;
        $(NEXT_PAGE_LOC).should(Condition.visible).click();
        currentPageNumber.shouldHave(text(orderNumber.toString()));
        logger.info("New page is: " + orderNumber);
        return orderNumber;
    }

    public void openFirstResultLink() {
        String beforeTitle = title();
        logger.info("Title page before open link: " + beforeTitle);
        $("#ires .g").find(".r a").click();
        assertThat(title(), is(not(beforeTitle)));
    }

    public Optional<String> searchOnPages(String lookingForDomain, int findOnPages) {
        int currentPage = 1;
        Optional<String> result = Optional.empty();
        while (currentPage < findOnPages && !result.isPresent()) {
            SearchResultsPage results = new SearchResultsPage();
            List<String> foundTexts = results.getResults().texts();
            result = Utils.searchOnThePage(foundTexts, lookingForDomain);
            if (result.isPresent()) {
                logger.info("page is: " + currentPage);
                logger.info(result.toString());
            }
            currentPage = results.nextPage();
        }
        return result;
    }
}
