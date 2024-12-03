package data_access;

import entity.CommonQuote;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.get_quote.QuoteInputData;

public class ZenQuotesDataAccessObject {
    private static final String BASE_URL = "https://zenquotes.io/api/today";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final OkHttpClient client;

    public ZenQuotesDataAccessObject() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    public QuoteInputData getTodayQuote() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONArray responseBody = new JSONArray(response.body().string());

            JSONObject quoteJson = responseBody.getJSONObject(0);
            return new QuoteInputData(
                    quoteJson.getString("q"),
                    quoteJson.getString("a")
            );
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get today's quote: " + ex.getMessage());
        }
    }
}

