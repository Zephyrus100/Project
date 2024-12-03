package interface_adapter.get_quote;

import interface_adapter.ViewManagerModel;
import use_case.get_quote.QuoteOutputBoundary;
import use_case.get_quote.QuoteOutputData;

public class QuotePresenter implements QuoteOutputBoundary {
    private final QuoteViewModel quoteViewModel;
    private final ViewManagerModel viewManagerModel;

    public QuotePresenter(QuoteViewModel quoteViewModel, ViewManagerModel viewManagerModel) {
        this.quoteViewModel = quoteViewModel;
        this.viewManagerModel = new ViewManagerModel();
    }

    @Override
    public void prepareSuccessQuote(QuoteOutputData quote) {
        QuoteState quoteState = new QuoteState(quote.getAuthor(), quote.getContent());
        quoteViewModel.setState(quoteState);
        quoteViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailQuote(String error) {
        QuoteState quoteState = new QuoteState("Error", error);
        quoteViewModel.setState(quoteState);
        quoteViewModel.firePropertyChanged();
    }
}