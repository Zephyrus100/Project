package interface_adapter.get_quote;

import interface_adapter.ViewModel;

public class QuoteViewModel extends ViewModel<QuoteState> {
    public QuoteViewModel(String viewName) {
        super(viewName);
        setState(new QuoteState("", ""));
    }
}

