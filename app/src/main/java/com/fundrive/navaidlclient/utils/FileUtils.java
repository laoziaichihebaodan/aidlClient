package com.fundrive.navaidlclient.utils;

import android.content.Context;
import android.os.Environment;

import com.fundrive.navaidlclient.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {

    public static final String fileName = "data.json";
    public static final String notifyFileName = "output";
    public static final String notifyFileFormat = ".txt";

    /**
     *
     * @param data      写入文本内容
     * @param file_directory  文件所在目录
     * @param fileName  文件名
     * @param append    是否追加写入
     */
    public static void writeFile(String data, File file_directory, String fileName, Boolean append) {
        try {
            File file = new File(file_directory,fileName); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file,append);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                if (data.equals("")){
                    out.write(data);
                }else{
                    out.write(data+"\r\n");// \r\n即为换行
                }
                out.flush();
                out.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filePath      文件路径（不包括格式后缀）
     * @param fileFormat    文件后缀格式（如：.txt）
     * @param fileCount     分割文件数量
     * @param fileOverSize  文件超过多大才分割（单位：M）
     * @throws IOException
     */
    public static void splitFile(String filePath,String fileFormat, int fileCount ,int fileOverSize) throws IOException {
        FileInputStream fis = new FileInputStream(filePath+fileFormat);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        if (fileSize < fileOverSize*1024*1024)  return;
        long average = fileSize / fileCount;//平均值
        long bufferSize = 200; //缓存块大小，自行调整
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + "")); // 申请一个缓存区
        long startPosition = 0; //子文件开始位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        if (b == 10 || b == 13) { //判断\n\r
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            }else{
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }

            int m = i+1;
            while(new File(filePath+m+fileFormat).exists()){
                m++;
            }
            FileOutputStream fos = new FileOutputStream(filePath + m + fileFormat);
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();
        writeFile("", Environment.getExternalStorageDirectory(),notifyFileName+notifyFileFormat,false);
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 从assets中复制文件
     * @param context
     * @param cach_file
     */
    public static void copyFilesFromAssets(Context context, File cach_file) {
        try {
            InputStream is = context.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(cach_file);
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
