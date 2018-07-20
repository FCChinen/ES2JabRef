package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.FileType;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.util.FileUpdateMonitor;

public class CsvImporter extends Importer {

    private final ImportFormatPreferences importFormatPreferences;
    private final FileUpdateMonitor fileMonitor;

    public CsvImporter(ImportFormatPreferences importFormatPreferences, FileUpdateMonitor fileMonitor) {
        this.importFormatPreferences = importFormatPreferences;
        this.fileMonitor = fileMonitor;
    }

    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }

    @Override
    public String getDescription() {
        return "This importer exists to enable `--importToOpen someEntry.csv`\n";
    }

    @Override
    public String getId() {
        return "csv";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) {
        Objects.requireNonNull(reader);
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException {
        //Importa csv lendo a primeira linha do arquivo como cabeçalho
        //Primeira linha deve conter 'title', 'author', 'year', 'publisher', 'series', 'isbn', 'keywords', 'note' em qualqer ordem
        Objects.requireNonNull(reader);

        StringTokenizer header = new StringTokenizer(reader.readLine(), ",");
        List<BibEntry> library = new LinkedList<>();

        List<String> headerList = new ArrayList<>();

        while (header.hasMoreTokens()) {
            String element = header.nextToken();
            System.out.println(element);
            if (element.equals(FieldName.TITLE)) {
                headerList.add(FieldName.TITLE);
            }
            if (element.equals(FieldName.AUTHOR)) {
                headerList.add(FieldName.AUTHOR);
            }
            if (element.equals(FieldName.YEAR)) {
                headerList.add(FieldName.YEAR);
            }
            if (element.equals(FieldName.PUBLISHER)) {
                headerList.add(FieldName.PUBLISHER);
            }
            if (element.equals(FieldName.SERIES)) {
                headerList.add(FieldName.SERIES);
            }
            if (element.equals(FieldName.ISBN)) {
                headerList.add(FieldName.ISBN);
            }
            if (element.equals(FieldName.KEYWORDS)) {
                headerList.add(FieldName.KEYWORDS);
            }
            if (element.equals(FieldName.NOTE)) {
                headerList.add(FieldName.NOTE);
            }
        }
        String line;
        while ((line = reader.readLine()) != null) {

            BibEntry b = new BibEntry("book");

            StringTokenizer token = new StringTokenizer(line, ",");

            b.setField(headerList.get(0), token.nextToken());
            b.setField(headerList.get(1), token.nextToken());
            b.setField(headerList.get(2), token.nextToken());
            b.setField(headerList.get(3), token.nextToken());
            b.setField(headerList.get(4), token.nextToken());
            b.setField(headerList.get(5), token.nextToken());
            b.setField(headerList.get(6), token.nextToken());
            b.setField(headerList.get(7), token.nextToken());

            library.add(b);
        }

        return new ParserResult(library);

    }

}