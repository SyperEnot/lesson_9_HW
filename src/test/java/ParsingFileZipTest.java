import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ParsingFileZipTest {
    private ClassLoader cl = getClass().getClassLoader();

    @Test
    @DisplayName("Проверка файла CSV")
    void FileCsvTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(cl.getResource("archive.zip")).toURI()));

        ZipArchiveEntry csvEntry = zipFile.getEntry("csvTest.csv");
        try (InputStream stream = zipFile.getInputStream(csvEntry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(stream));
            List<String[]> content = reader.readAll();
            final String[] firstRaw = content.get(0);
            final String[] secondRaw = content.get(1);
            final String[] thirdRaw = content.get(2);
            final String[] fourthRaw = content.get(3);
            assertAll(
                    () -> assertEquals(content.size(), 4),
                    () -> assertArrayEquals(new String[]{"Stas", "09 May 1998"}, firstRaw,
                            "First raw is different"),
                    () -> assertArrayEquals(new String[]{"Anna", "20 August 1995"}, secondRaw,
                            "Second raw is different"),
                    () -> assertArrayEquals(new String[]{"Yuri", "16 December 1980"}, thirdRaw,
                            "Third raw is different"),
                    () -> assertArrayEquals(new String[]{"Alex", "22 November 2001"}, fourthRaw,
                            "Fourth raw is different")
            );
        }
    }

    @Test
    @DisplayName("Проверка файла Excel")
    void verifyFileXlsTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(cl.getResource("archive.zip")).toURI()));

        ZipArchiveEntry xlsEntry = zipFile.getEntry("xlsxTest.xlsx");
        try (InputStream stream = zipFile.getInputStream(xlsEntry)) {

            XLS parsed = new XLS(stream);
            String firstName = parsed.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue();
            String carName = parsed.excel.getSheetAt(0).getRow(2).getCell(2).getStringCellValue();
            String secondName = parsed.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();

            assertAll(
                    () -> assertEquals("Константин", firstName,
                            "Firstname is different"),
                    () -> assertEquals("БМВ", carName,
                            "Car is different"),
                    () -> assertEquals("Пелевин", secondName,
                            "Secondname is different")
            );
        }
    }

    @Test
    @DisplayName("Проверка файла PDF")
    void verifyFilePdfTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(cl.getResource("archive.zip")).toURI()));

        ZipArchiveEntry pdfEntry = zipFile.getEntry("pdfTest.pdf");
        try (InputStream stream = zipFile.getInputStream(pdfEntry)) {
            PDF pdf = new PDF(stream);
            assertAll(
                    () -> assertNotNull(pdf.content),
                    () -> assertEquals(303, pdf.numberOfPages,
                            "Number of pages is different")
            );
        }
    }
}