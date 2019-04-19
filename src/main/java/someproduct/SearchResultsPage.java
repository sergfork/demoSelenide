package someproduct;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {
    public ElementsCollection getResults() {
        return $$("#ires .g");
    }

    public int nextPage() {
        SelenideElement currentPageNumber = $("#foot #nav td.cur");
        Integer orderNumber = Integer.parseInt(currentPageNumber.text());
        orderNumber++;
        $("#foot #nav #pnnext").should(Condition.visible).click();
        currentPageNumber.shouldHave(text(orderNumber.toString()));
        return orderNumber;
    }
}
