package org.telegrambot.convertio.api.convertio;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegrambot.convertio.api.convertio.requests.ConvertioRequest;
import org.telegrambot.convertio.exceptions.apis.ApiException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConvertioApi {
    @Value("${convertio.api.token}")
    private String apiKey;
//    @Value("${kuna.api.apiSecret}")
//    private String apiSecret;
    @Value("${convertio.api.domain}")
    private String apiDomain;
//    @Value("${kuna.api.encoding}")
//    private String encoding;

    public String execute(ConvertioRequest request) throws ApiException {
        try {
            String nonce = String.valueOf(System.currentTimeMillis());
            final String apiPath = request.getApiPath();

            JSONObject body = request.toJson();
            String signature = apiPath + nonce + body;
//            String signatureEnc = hashHmac(signature);
            String signatureEnc = "hashHmac(signature)";

            final HttpsURLConnection urlConnection = formConnection(apiPath, nonce, signatureEnc);
            // sending request
            sendRequest(body, urlConnection);
            // get response
            return readResponse(urlConnection);
        } catch (IOException e) {
            log.error("Unknown error occurred while executing api request: ");
            throw new ApiException("api_exception.internal_error" + e);
        }

    }

    private HttpsURLConnection formConnection(String apiPath, String nonce, String signature) throws IOException {
        final URL url = new URL(apiDomain + apiPath);

        final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(5000);

        urlConnection.setRequestProperty("accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("kun-nonce", nonce);
        urlConnection.setRequestProperty("kun-apikey", apiKey);
        urlConnection.setRequestProperty("kun-signature", signature);

        return urlConnection;
    }

    private void sendRequest(JSONObject body, HttpsURLConnection urlConnection) throws IOException {
        try (OutputStream os = urlConnection.getOutputStream()) {
            log.info("JsonObject: " + body);
            byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    private InputStreamReader getInputStream(HttpsURLConnection urlConnection) {
        try {
            return new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return new InputStreamReader(urlConnection.getErrorStream(), StandardCharsets.UTF_8);
        }
    }

    private String readResponse(HttpsURLConnection urlConnection) throws IOException {
        InputStreamReader inputStream = getInputStream(urlConnection);

        try (BufferedReader br = new BufferedReader(inputStream)) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            log.info("Api response: " + response);
            return response.toString();
        }
    }


//    private String hashHmac(String data) {
//        try {
//            Mac sha384_HMAC = Mac.getInstance(encoding);
//            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), encoding);
//            sha384_HMAC.init(secret_key);
//
//            return BaseEncoding.base16().lowerCase().encode(sha384_HMAC.doFinal(data.getBytes()));
////            return Base64.encodeBase64String(sha384_HMAC.doFinal(data.getBytes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    /**
//     * Checks if api returns error or not
//     *
//     * @param responseJson response from api
//     * @param request      request to api that was executed
//     * @throws ApiException in the case of any error
//     */
//    private void isError(JSONObject responseJson, AnyMoneyRequest request) throws ApiException {
//        try {
//            JSONObject error = (JSONObject) responseJson.get("error");
//            String prefix = request.getClass().getAnnotation(PropertyPrefix.class).value();
//            String postfix = error.get("message").toString();
//            String errorPropertyName = prefix + postfix;
//            log.info("Api response contains errors: " + errorPropertyName);
//            throw new ApiException(errorPropertyName);
//        } catch (JSONException e) {
//            log.info("Api request successfully executed.");
//        }
//    }

}

