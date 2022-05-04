package com.jef.bol;

import com.lowagie.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class BolService {

    private static final String HTML = "src/main/resources/template.html";
    private static final String PDF = "src/main/resources/test.pdf";

    public void generateHtmlToPdf(HttpServletResponse response) throws IOException, TransformerException, ParserConfigurationException, SAXException {
        File inputHTML = new File(HTML);
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF, response);
    }

    private Document createWellFormedHtml(File inputHTML) throws IOException {
        Document doc = Jsoup.parse(inputHTML, "UTF-8");
        Element link = doc.select("p#interestRate").first();
        System.out.println(link.text());
        link.appendText("JJ");
        link.text("1234.55");

        doc.select("p#interestRate").attr("text", "11");
        doc.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);
        return doc;
    }

    private void xhtmlToPdf(Document doc, String outputPdf, HttpServletResponse response)
            throws IOException, TransformerException, ParserConfigurationException, SAXException {

//        OutputStream os = new FileOutputStream(outputPdf);
//
//        String baseUri = FileSystems.getDefault()
//                .getPath("src/main/resources/")
//                .toUri()
//                .toString();
//        PdfRendererBuilder builder = new PdfRendererBuilder();
//        builder.withUri(outputPdf);
//        builder.toStream(os);
//        builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
//        builder.run();
//
//
//        byte[] array = Files.readAllBytes(Paths.get("src/main/resources/test.pdf"));
//        return array;

        StringBuffer buf = new StringBuffer();
        buf.append("<html>");

        // put in some style
        buf.append("<head><style language='text/css'>");
        buf.append("h3 { border: 1px solid #aaaaff; background: #ccccff; ");
        buf.append("padding: 1em; text-transform: capitalize; font-family: sansserif; font-weight: normal;}");
        buf.append("p { margin: 1em 1em 4em 3em; } p:first-letter { color: red; font-size: 150%; }");
        buf.append("h2 { background: #5555ff; color: white; border: 10px solid black; padding: 3em; font-size: 200%; }");
        buf.append("</style></head>");

        // generate the body
        buf.append("<body>");
        buf.append("<p><img src='100bottles.jpg'/></p>");
        for(int i=99; i>0; i--) {
            buf.append("<h3>"+i+" bottles of beer on the wall, "
                    + i + " bottles of beer!</h3>");
            buf.append("<p>Take one down and pass it around, "
                    + (i-1) + " bottles of beer on the wall</p>\n");
        }
        buf.append("<h2>No more bottles of beer on the wall, no more bottles of beer. ");
        buf.append("Go to the store and buy some more, 99 bottles of beer on the wall.</h2>");
        buf.append("</body>");
        buf.append("</html>");

        String html = doc.outerHtml().replace("\"", "'");
//        String html = doc.outerHtml();
        StringBuffer buf2 = new StringBuffer();
        buf2.append(html);

        DocumentBuilder builder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document docum = builder1.parse(new StringBufferInputStream(buf.toString()));

        DocumentBuilder builder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document docum2 = builder1.parse(new StringBufferInputStream(buf2.toString()));

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(docum, null);
        renderer.layout();
        OutputStream os = response.getOutputStream();
        renderer.createPDF(os);
        os.close();


//        return InputStream();

//        com.lowagie.text.Document document = new com.lowagie.text.Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        PdfWriter writer = PdfWriter.getInstance(document, out);
//
//        byte[] pdf = out.toByteArray();
//        return pdf;

//        String str = doc.toString();
//        byte[] b;
//        try {
//            b = str.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        b = str.getBytes(Charset.forName("UTF-8"));
//        b = str.getBytes(StandardCharsets.UTF_8);
//        return b;

        /***************************/
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        StreamResult result = new StreamResult(bos);
//
//        transformer.transform((Source) doc, result);
//        byte []array = bos.toByteArray();
//        InputStream targetStream = new ByteArrayInputStream(array);
//        return  targetStream;


        /***************************/


//        Document document = new Document();
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        PdfWriter writer = PdfWriter.getInstance(document, out);
//        document.open();
//
//        InputStream in = IOUtils.toInputStream(htmlString);
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, in);
//        document.close();
//
//        return out.toByteArray();

    }
}
