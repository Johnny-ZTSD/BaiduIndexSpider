package cn.johnnyzen.util.file;

import java.io.*;
import java.util.logging.Logger;

public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());

    private static String logPrefix = null;

    public static void saveFile(byte[] file,String filePath,String fileName) throws IOException {
        File targetFile=new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 读取文件数据
     * @param fileFullAbsolutePath 完整的文件绝对路径形如：C:/demo/dir/demo.txt
     * @return
     */
    public static String readFile(String fileFullAbsolutePath){
        File targetFile=new File(fileFullAbsolutePath);
        if(!targetFile.exists()){
            logger.warning(logPrefix+"file '"+fileFullAbsolutePath+"' does not exists!");
            return null;
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileFullAbsolutePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(in == null){
            logger.warning(logPrefix+"BufferedReader 'in' is null!");
            return null;
        }
        String lineStr = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((lineStr = in.readLine()) != null) {
                stringBuilder.append(lineStr+'\n');//+ \n是为了还原原文本中存在的换行符
//                System.out.println(lineStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取文件数据
     * @param filePath 形如：C:/demo/dir
     * @param fileName 形如：test.txt
     * @return
     *
     * String filePath = "C:\\Users\\千千寰宇\\Desktop";
     * String fileName = "js.js";
     * System.out.println(readFile(filePath,fileName));
     */
    public static String readFile(String filePath,String fileName){
        logPrefix="[FileUtil.readFile] ";
        String realPath = filePath+"/"+fileName;
        return readFile(realPath);
    }
}
