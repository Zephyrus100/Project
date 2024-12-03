package entity;

public class CommonQuote implements QuoteInterface {

    String author;
    String content;

    public CommonQuote(String author, String content) {
        this.author = author;
        this.content = content;
    }
    @Override
    public String getQuoteAuthor() {
        return author;
    }

    @Override
    public String getQuoteContent() {
        return content;
    }

    @Override
    public void SetQuoteAuthor(String author) {
        this.author = author;
    }

    @Override
    public void SetQuoteContent(String content) {
        this.content = content;
    }
}
