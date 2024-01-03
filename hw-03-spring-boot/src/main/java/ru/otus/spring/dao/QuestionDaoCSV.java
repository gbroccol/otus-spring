package ru.otus.spring.dao;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.QuestionnaireProperties;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Component
public class QuestionDaoCSV implements QuestionDao {

    private final String csvFilePath;
    private static final Logger logger = LoggerFactory.getLogger(QuestionDaoCSV.class);

    public QuestionDaoCSV(QuestionnaireProperties questionnaireProperties) {
        this.csvFilePath = questionnaireProperties.getCsvFileName();
    }

    @Override
    public List<String[]> getDataAsList() {

        logger.info("Получаем вопросы... Путь к файлу - {} ", csvFilePath);

        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(csvFilePath)));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        try {
            return new CSVReaderBuilder(br).withSkipLines(1).build().readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
