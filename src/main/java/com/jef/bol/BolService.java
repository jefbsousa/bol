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

    public byte[] generateHtmlToPdf() throws IOException, TransformerException {
        File inputHTML = new File(HTML);
        Document doc = createWellFormedHtml(inputHTML);
        return xhtmlToPdf(doc, PDF);
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

    private byte[] xhtmlToPdf(Document doc, String outputPdf) throws IOException, TransformerException {
        OutputStream os = new FileOutputStream(outputPdf);

        String baseUri = FileSystems.getDefault()
                .getPath("src/main/resources/")
                .toUri()
                .toString();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdf);
        builder.toStream(os);
        builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
        builder.run();


        byte[] array = Files.readAllBytes(Paths.get("src/main/resources/test.pdf"));
        return array;

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
