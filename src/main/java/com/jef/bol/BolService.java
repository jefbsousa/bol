package com.jef.bol;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;


@Service
public class BolService {

    private static final String HTML = "src/main/resources/template.html";
    private static final String PDF = "src/main/resources/test.pdf";

    public void generateHtmlToPdf() throws IOException {
        File inputHTML = new File(HTML);
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF);
    }

    private Document createWellFormedHtml(File inputHTML) throws IOException {
        Document doc = Jsoup.parse(inputHTML, "UTF-8");
        Element link = doc.select("p#interestRate").first();
        System.out.println(link.text());
        link.appendText("JJ");
        link.text("XXX");

        doc.select("p#interestRate").attr("text", "11");
        doc.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);
        return doc;
    }

    private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri()
                    .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }
}
