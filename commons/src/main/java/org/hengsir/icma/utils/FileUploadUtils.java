package org.hengsir.icma.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author hengsir
 * @date 2018/4/8 下午5:20
 */
public class FileUploadUtils {

    private static String imgPath = "/Users/icma-upload-img/";

    public static String saveFile(String fileName, MultipartFile photo) {
        OutputStream out = null;
        File file = null;
        try {
            file = new File(imgPath + fileName);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            InputStream in = photo.getInputStream();
            out = new BufferedOutputStream(new FileOutputStream(file.getPath() + "/" + photo.getOriginalFilename()));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return file.getPath() + "/" + photo.getOriginalFilename();
    }

    //递归删除
    public static void deleteDir(File dir){
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for(int i=0; i<files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }
}
