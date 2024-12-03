package interface_adapter.get_quote;

import entity.CommonQuote;
import entity.CommonQuoteFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.get_quote.QuoteInputBoundary;

public class QuoteController {
    private final QuoteInputBoundary quoteInteractor;

    public QuoteController(QuoteInputBoundary quoteInteractor) {
        this.quoteInteractor = quoteInteractor;
    }

    public void execute() {
        quoteInteractor.execute();
    }
}