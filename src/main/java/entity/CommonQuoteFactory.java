package entity;

public class CommonQuoteFactory implements QuoteFactoryInterface {

    @Override
    public QuoteInterface createQuote(String author, String content) {return new CommonQuote(author, content);
    }
}
