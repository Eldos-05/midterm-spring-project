package comsep23.midtermspringproject.config;

import comsep23.midtermspringproject.entity.Sneaker;
import comsep23.midtermspringproject.repository.SneakerRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class BootstrapData implements CommandLineRunner {

    private final SneakerRepository sneakerRepository;
    @Autowired
    public BootstrapData(SneakerRepository sneakerRepository) {
        this.sneakerRepository = sneakerRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadSneakersFromCsv();
    }

    private void loadSneakersFromCsv() throws IOException {
        Reader reader = new FileReader("src/main/resources/sneakers.csv");
//        Iterable<CSVRecord> records = CSVFormat.DEFAULT
//                .withHeader("id", "name", "brand", "model", "price")
//                .parse(reader);

        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(  "name", "brand", "model", "price").build();
        CSVParser records = csvFormat.parse(reader);
//        for (CSVRecord record : records) {
//            System.out.println(record.get(0));
//        }

        for (CSVRecord record : records) {
            Sneaker sneaker = new Sneaker();
            sneaker.setName(record.get("name"));
            sneaker.setBrand(record.get("brand"));
            sneaker.setModel(record.get("model"));
            sneaker.setPrice(Double.parseDouble(record.get("price")));
            sneakerRepository.save(sneaker);
        }
    }
}