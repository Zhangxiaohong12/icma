package org.hengsir.icma.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * @author hengsir
 * @date 2018/4/8 下午5:20
 */
public class FileUploadUtils {

    private static String basePath = "/Users/";

    private static String imgPath = "/Users/icma-upload-img/";

    static{
        File ff = new File(basePath);
        if (!ff.exists() && !ff.isDirectory()){
            ff.mkdir();
        }
        File f = new File(imgPath);
        if (!f.exists() && !f.isDirectory()){
            f.mkdir();
        }
    }

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
