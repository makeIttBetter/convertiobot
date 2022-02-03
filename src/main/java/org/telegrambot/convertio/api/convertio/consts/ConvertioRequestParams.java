package org.telegrambot.convertio.api.convertio.consts;

public class ConvertioRequestParams {

    public final static String APIKEY = "apikey";

//    curl -i -X POST -d '{"apikey": "_YOUR_API_KEY_", "file":"http://google.com/", "outputformat":"png"}' http://api.convertio.co/convert
    public final static String INPUT = "input"; // Allowed Values: url,raw,base64,upload ( Default Value:url )
    public final static String FILE = "file"; // String	URL of the input file (if input=url), or file content (if input = raw/base64)
    public final static String FILENAME = "filename"; // optional	String	Input filename including extension (file.ext). Required if input = raw/base64
    public final static String OUTPUT_FORMAT = "outputformat"; // String	Output format, to which the file should be converted to.
    public final static String OPTIONS = "options"; // optional	Object	Conversion options. Now used to define callback URL, enable OCR and setting up its options. You may find available OCR conversion options here and callback example here.

//    curl -i -X GET http://api.convertio.co/convert/_ID_/status
    public final static String FILE_ID = "id"; // String	Conversion ID, obtained on POST call to /convert

}
