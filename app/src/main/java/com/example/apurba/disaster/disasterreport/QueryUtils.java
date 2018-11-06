package com.example.apurba.disaster.disasterreport;

/** public class QueryUtils class
 *
 * Created by Apurba on 3/16/2018.
 * This class provides a static public method
 * Dose the networking operation such as asks server for data
 * receive Json response as string and returns it
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** private constructor
     *  so that other class can not create any object of this class
     */
    private QueryUtils(){
    }

    /** public static String requestToApi()
     *  This method first create url from given string url
     *  then make http request to that url and then return
     *  the response as string
     * @param requestUrl - used to built ulr
     * @return - json response as string
     */
    public static String requestToApi(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return jsonResponse;
    }

    /** private static URL createUrl() method
     *  Returns new URL object from the given string URL.
     *  @param stringUrl - used to create url object
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /** private static String makeHttpRequest() method
     *  makes HTTP connection, read data, then
     *  disconnect the connection.
     * @param url - used to built HTTP connection
     * @return - string as data output
     * @throws IOException - throws any {@link IOException} of reading data
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            // Obtain a new HttpURLConnection
            urlConnection = (HttpURLConnection) url.openConnection();

            // prepare the request
            urlConnection.setReadTimeout(10000 /* milliseconds */); // 10000 milliseconds = 10 seconds
            urlConnection.setConnectTimeout(15000 /* milliseconds */); // 15000 milliseconds = 15 seconds
            urlConnection.setRequestMethod("GET"); // for reading

            // now connect
            urlConnection.connect();

            // check for connection is it successfully established or not
            if (urlConnection.getResponseCode() == 200) { // Response code = 200 means "ok"

                // get the input stream that the server just sent to read from it
                inputStream = urlConnection.getInputStream();  //getInputStream() - // Returns an input stream that reads from this open connection

                // now read from it
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem reading the JSON results.", e);
        } finally {
            // close the connection and the input stream
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /** private static String readFromStream() method
     *  This method reads from input stream and build a output string
     * @param inputStream - used to read from it
     * @return - returns a large string
     * @throws IOException - throws any {@link IOException} while reading inputStream
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
