package use_case.get_quote;

public class QuoteOutputData {

    String author;
    String content;

    public QuoteOutputData(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
