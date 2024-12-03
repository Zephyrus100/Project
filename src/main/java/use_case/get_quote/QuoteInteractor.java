package use_case.get_quote;

import data_access.ZenQuotesDataAccessObject;
import entity.CommonQuote;

public class QuoteInteractor implements QuoteInputBoundary {
    private final ZenQuotesDataAccessObject quoteDataAccessObject;
    private final QuoteOutputBoundary quotePresenter;

    public QuoteInteractor(ZenQuotesDataAccessObject quoteDataAccessObject,
                           QuoteOutputBoundary quotePresenter) {
        this.quoteDataAccessObject = quoteDataAccessObject;
        this.quotePresenter = quotePresenter;
    }

    @Override
    public void execute() {

        CommonQuote quote = new CommonQuote(quoteDataAccessObject.getTodayQuote().getAuthor(),
                quoteDataAccessObject.getTodayQuote().getContent());
        QuoteOutputData quoteOutputData = new QuoteOutputData(quote.getQuoteAuthor(),
                quote.getQuoteContent());
        quotePresenter.prepareSuccessQuote(quoteOutputData);
    }
}