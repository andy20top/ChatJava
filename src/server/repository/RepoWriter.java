package server.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class RepoWriter implements Repository {

    public static final String LOGS_PATH = "src" + File.separator + "server" + File.separator + "logs.txt";

    @Override
    public void save(String text) {
        try (FileWriter writer = new FileWriter(LOGS_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String load() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOGS_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
