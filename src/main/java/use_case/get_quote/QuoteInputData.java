package use_case.get_quote;

public class QuoteInputData {

    private String content;
    private String author;

    public QuoteInputData(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
