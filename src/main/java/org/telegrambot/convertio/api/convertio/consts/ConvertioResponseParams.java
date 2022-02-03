package org.telegrambot.convertio.api.convertio.consts;

public class ConvertioResponseParams {

    public final static String APIKEY = "apikey";

//    curl -i -X POST -d '{"apikey": "_YOUR_API_KEY_", "file":"http://google.com/", "outputformat":"png"}' http://api.convertio.co/convert
    public final static String INPUT = "input"; // Allowed Values: url,raw,base64,upload ( Default Value:url )
    public final static String FILE = "file"; // String	URL of the input file (if input=url), or file content (if input = raw/base64)
    public final static String FILENAME = "filename"; // optional	String	Input filename including extension (file.ext). Required if input = raw/base64
    public final static String OUTPUT_FORMAT = "outputformat"; // String	Output format, to which the file should be converted to.
    public final static String OPTIONS = "options"; // optional	Object	Conversion options. Now used to define callback URL, enable OCR and setting up its options. You may find available OCR conversion options here and callback example here.

//    curl -i -X GET http://api.convertio.co/convert/_ID_/status
    public final static String FILE_ID = "id"; // String	Conversion ID, obtained on POST call to /convert






//    convertion ON SUCCESS:

//    code		Int	HTTP Status Code
//    status		String	Always 'ok' on Success
//    data		Object	Result data
//    data.id		String	Your Conversion ID
//    data.minutes		String	API conversion minutes available on the balance



//    convertion ON FAILURE:

//    code		Int	HTTP Status Code
//    status		String	Always 'error' on Error
//    error		String	User-friendly Error String



//    ----------------------------------------------------------------


//    convertion Status ON SUCCESS:

//    code		Int	HTTP Status Code
//    status		String	Always 'ok' on Success
//    data		Object	Result data
//    data.id		String	Your Conversion ID
//    data.step		String	Conversion Step
//    Allowed Values: wait,finish,convert,upload
//    data.step_percent		Int	Step Progress in %
//    data.minutes		Int	API Minutes used by this conversion
//    data.output		Object	Output file information
//    data.output.url		String	URL of the file to download
//    data.output.size		Int	Size of the file in bytes
//    data.output.files		Object	If there are multiple output files (i.e. converting a multi-page DOC to JPG) data.output will contain a link to a ZIP file, which contains all output files. If you would like to get the output files individual, you can use data.output.url/file.ext, where file.ext - is the name of the individual file



//    convertion Status ON FAILURE:

//    code		Int	HTTP Status Code
//    status		String	Always 'error' on Error
//    error		String	User-friendly Error String




}
