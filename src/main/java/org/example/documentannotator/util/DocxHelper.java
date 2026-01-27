package org.example.documentannotator.util;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;

public class DocxHelper {

    /**
     * Converts a DOCX file to HTML.
     *
     * @param filePath path to the DOCX file
     * @return HTML string representation of the document
     * @throws IOException if the file cannot be read
     */
    public static String convertToHtml(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument docx = new XWPFDocument(fis)) {

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html><head><meta charset=\"UTF-8\"></head><body>\n");

            for (XWPFParagraph paragraph : docx.getParagraphs()) {
                html.append("<p>");
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null && !text.isEmpty()) {
                        boolean bold = run.isBold();
                        boolean italic = run.isItalic();
                        boolean underline = run.getUnderline() != UnderlinePatterns.NONE;

                        if (bold) html.append("<strong>");
                        if (italic) html.append("<em>");
                        if (underline) html.append("<u>");

                        html.append(escapeHtml(text));

                        if (underline) html.append("</u>");
                        if (italic) html.append("</em>");
                        if (bold) html.append("</strong>");
                    }
                }
                html.append("</p>\n");
            }

            html.append("</body></html>");
            return html.toString();
        }
    }

    private static String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
