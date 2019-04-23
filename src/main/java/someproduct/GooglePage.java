package someproduct;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class GooglePage {

    private static final String INPUT_FOR_QUERY_LOC = "q";

    public void searchFor(String text) {
        $(By.name(INPUT_FOR_QUERY_LOC)).val(text).pressEnter();
    }

}
