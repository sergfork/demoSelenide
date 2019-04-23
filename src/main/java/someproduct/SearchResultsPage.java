package someproduct;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {

    private static final String CURRENT_NUMBER_OF_PAGE_LOC = "#foot #nav td.cur";
    private static final String NEXT_PAGE_LOC = "#foot #nav #pnnext";
    private static final String RESULT_LOC = "#ires .g";

    public ElementsCollection getResults() {
        return $$(RESULT_LOC);
    }

    public int nextPage() {
        SelenideElement currentPageNumber = $(CURRENT_NUMBER_OF_PAGE_LOC);
        Integer orderNumber = Integer.parseInt(currentPageNumber.text());
        orderNumber++;
        $(NEXT_PAGE_LOC).should(Condition.visible).click();
        currentPageNumber.shouldHave(text(orderNumber.toString()));
        return orderNumber;
    }
}
