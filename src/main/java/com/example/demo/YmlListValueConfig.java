package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description
 * @create:
 **/

@Component
@ConfigurationProperties(prefix = "paichusuo")
public class YmlListValueConfig implements ApplicationRunner {
    private List<String> name;

    @Value("${filePath}")
    private String filePath;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> allFile = getFiles(filePath);
        List<String> allFilePrefix = allFile.stream().map(e -> e.substring(1+e.lastIndexOf(File.separator))).map(e -> e.split("\\.")[0]).collect(Collectors.toList());

        List<String> nameList = name.stream().filter( e -> !allFilePrefix.contains(e)).collect(Collectors.toList());
        String nameListStr = String.join(",", nameList);
        System.out.println("还未提交的有:"+nameListStr);
        List<MyData> result = new ArrayList<>();
        List<String> datas =  new ArrayList<>();
        //合并excel
        for (String name : allFile) {
            String file = name.substring(1+name.lastIndexOf(File.separator)).split("\\.")[0];
            List<MyData> dataList= ExcelUtil.readExcel( new BufferedInputStream(new FileInputStream(new File(name))),MyData.class);
            result.addAll(dataList);
            datas.add(file +":"+dataList.size());
        }

        Date d = new Date();
        SimpleDateFormat sbf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String outFile = new File(filePath).getParent() + File.separator + "output" +"_"+sbf.format(d)+".xlsx";

        ExcelUtil.writeExcel(new File(outFile),result);


        System.out.println("提交数量:"+String.join(",", datas));




    }

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(Objects.isNull(tempList)){
            return files;
        }
        for (int i = 0; i < tempList.length; i++) {
            if(".DS_Store".equals(tempList[i].getName())){
                continue;
            }
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        return files;
    }
}