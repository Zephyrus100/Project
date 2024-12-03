package use_case.get_quote;

public interface QuoteOutputBoundary {

    public void prepareSuccessQuote (QuoteOutputData quoteOutputData);

    public void prepareFailQuote(String s);
}
