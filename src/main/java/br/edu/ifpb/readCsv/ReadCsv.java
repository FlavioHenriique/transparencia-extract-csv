package br.edu.ifpb.readCsv;

import br.edu.ifpb.dao.MongoDao;
import br.edu.ifpb.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadCsv {

    private Iterator<CSVRecord> iterator;
    private CSVRecord record;
    private MongoDao mongoDao;
    private List<Long> listOrgaoSuperior;
    private List<Long> listOrgaoSubordinado;
    private List<Long> listFuncao;
    private List<Long> listUnidadeGestora;

    public void changeFile(String fileName) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(fileName),
                Charset.forName("ISO-8859-1"));
        CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withQuote('"').withDelimiter(';'));

        iterator = parser.iterator();
        // pulando primeira linha
        nextRecord();
    }

    public ReadCsv(String fileName) throws IOException {
        listOrgaoSuperior = new ArrayList<>();
        listOrgaoSubordinado = new ArrayList<>();
        listFuncao = new ArrayList<>();
        listUnidadeGestora = new ArrayList<>();

        changeFile(fileName);
        mongoDao = new MongoDao();
    }

    private void nextRecord(){
        record = iterator.next();
    }

    public void insertRecordObjects(){
        while(iterator.hasNext()){
            nextRecord();
            OrgaoSuperior orgaoSuperior = getOrgaoSuperior();
            OrgaoSubordinado orgaoSubordinado = getOrgaoSubordinado();
            UnidadeGestora unidadeGestora = getUnidadeGestora();
            Funcao funcao = getFuncao();
            Exercicio exercicio = getExercicio();

            if (!exercicioValid(exercicio)){
                continue;
            }

            if(!listOrgaoSuperior.contains(orgaoSuperior.getCod_org_sup())){
                mongoDao.insert(orgaoSuperior);
                listOrgaoSuperior.add(orgaoSuperior.getCod_org_sup());
            }

            if(!listOrgaoSubordinado.contains(orgaoSubordinado.getCod_org_sub())){
                mongoDao.insert(orgaoSubordinado);
                listOrgaoSubordinado.add(orgaoSubordinado.getCod_org_sub());
            }

            if (!listUnidadeGestora.contains(unidadeGestora.getCod_unid_gestora())){
                mongoDao.insert(unidadeGestora);
                listUnidadeGestora.add(unidadeGestora.getCod_unid_gestora());
            }

            if (!listFuncao.contains(funcao.getCod_func())){
                mongoDao.insert(funcao);
                listFuncao.add(funcao.getCod_func());
            }

            mongoDao.insert(exercicio);
        }
    }

    private boolean exercicioValid(Exercicio exercicio) {
        if ((exercicio.getVal_emp() <= 0) || (exercicio.getVal_liquid() <= 0)
                || (exercicio.getVal_pago() <= 0)){
            return false;
        }
        return true;
    }

    private String getValueByPosition(int position) {
        return record.get(position);
    }

    private OrgaoSuperior getOrgaoSuperior(){
        OrgaoSuperior orgaoSuperior = new OrgaoSuperior();
        orgaoSuperior.setCod_org_sup(Long.parseLong(getValueByPosition(1)));
        orgaoSuperior.setNom_org_sup(getValueByPosition(2));
        return orgaoSuperior;
    }

    private OrgaoSubordinado getOrgaoSubordinado(){
        OrgaoSubordinado orgaoSubordinado = new OrgaoSubordinado();
        orgaoSubordinado.setCod_org_sub(Long.parseLong(getValueByPosition(3)));
        orgaoSubordinado.setNom_org_sub(getValueByPosition(4));
        return orgaoSubordinado;
    }

    private UnidadeGestora getUnidadeGestora(){
        UnidadeGestora unidadeGestora = new UnidadeGestora();
        unidadeGestora.setCod_unid_gestora(Long.parseLong(getValueByPosition(5)));
        unidadeGestora.setNom_unid_gestora(getValueByPosition(6));
        return unidadeGestora;
    }

    private Funcao getFuncao(){
        Funcao funcao = new Funcao();
        funcao.setCod_func(Long.parseLong(getValueByPosition(11)));
        funcao.setNom_func(getValueByPosition(12));
        return funcao;
    }

    private Exercicio getExercicio(){
        Exercicio exercicio = new Exercicio();
        exercicio.setAno_mes(getValueByPosition(0));
        exercicio.setCod_func(getFuncao().getCod_func());
        exercicio.setCod_org_sub(getOrgaoSubordinado().getCod_org_sub());
        exercicio.setCod_org_sup(getOrgaoSuperior().getCod_org_sup());
        exercicio.setCod_unid_gestora(getUnidadeGestora().getCod_unid_gestora());
        exercicio.setUF(getValueByPosition(23));
        exercicio.setVal_emp(Double.parseDouble(getValueByPosition(41).replaceAll(",", ".")));
        exercicio.setVal_liquid(Double.parseDouble(getValueByPosition(42).replaceAll(",", ".")));
        exercicio.setVal_pago(Double.parseDouble(getValueByPosition(43).replaceAll(",", ".")));
        return exercicio;
    }
}
