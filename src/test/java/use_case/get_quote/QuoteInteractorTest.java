package use_case.get_quote;

import data_access.ZenQuotesDataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteInteractorTest {
    @Test
    void successTest() {

        ZenQuotesDataAccessObject quoteRepo = new ZenQuotesDataAccessObject();
        QuoteInputData inputData = quoteRepo.getTodayQuote();
        QuoteOutputBoundary successPresenter = new QuoteOutputBoundary() {
            @Override
            public void prepareSuccessQuote(QuoteOutputData quoteOutputData) {
                assertEquals(quoteOutputData.author, inputData.getAuthor());
                assertEquals(quoteOutputData.content, inputData.getContent());
            }
            @Override
            public void prepareFailQuote(String s) {
                fail("Use case failure is unexpected");
            }
        };
        QuoteInteractor interactor = new QuoteInteractor(quoteRepo, successPresenter);
        interactor.execute();
    }
}
