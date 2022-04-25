package br.edu.ifpb;

import br.edu.ifpb.readCsv.ReadCsv;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExtractCSVMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractCSVMain.class);

    public static void main(String[] args) {
        File pasta = new File("/home/flavio/mestrado/engenharia_software/dados");
        List<File> arquivos = Arrays.asList(pasta.listFiles());

        try {
            ReadCsv readCsv = null;
            for (File arquivo: arquivos){
                if (readCsv == null){
                    readCsv = new ReadCsv(arquivo.getAbsolutePath());
                }else{
                    readCsv.changeFile(arquivo.getAbsolutePath());
                }
                System.out.println("File: " + arquivo.getAbsolutePath());

                readCsv.insertRecordObjects();
            }


        } catch (Exception e) {
            LOGGER.warn("Erro lendo arquivo " + arquivos.get(0).getAbsolutePath() + ": " + e.getMessage());
        }

    }
}