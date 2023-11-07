package com.ghostflower.CA_System.service.impl;


import com.ghostflower.CA_System.mapper.CaMapper;
import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.service.CaService;
import com.ghostflower.CA_System.utils.CaUtil;
import com.ghostflower.CA_System.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;


@Service
public class CaServiceImpl implements CaService {
    @Autowired
    private CaMapper cm;

    @Override
    public Ca FindCa(String token){
        String uid =  JwtUtil.Parse("This is a test!!",token).get("uid").toString();
        Ca ca = cm.FindCa(uid);
        return ca;
    }

    @Override
    public void Apply(String token){
        String uid =  JwtUtil.Parse("This is a test!!",token).get("uid").toString();
        Ca ca = CaUtil.generateCA();
        cm.Add(uid,ca.serial,ca.alg,ca.keyPairs.getValue0(), ca.keyPairs.getValue1(), ca.startDate,ca.endDate,ca.issuer,ca.version);
    }


    @Override
    public void DownloadCa(Ca ca, String fileName, HttpServletResponse response){
        //将CA的json形式转位字符串，后续方便加密(笑)
        String data = new JSONObject(ca).toString();

        response.addHeader("Content-Disposition", "attachment;fileName=" +  fileName);// 设置文件名,默认同名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            InputStream is = new ByteArrayInputStream(data.getBytes());
            bis = new BufferedInputStream(is);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    @Override
    public Result DownloadOtherFiles(String fileName, HttpServletResponse response) {
        if (fileName != null) {

            //获取根目录
            File path = null;
            try {
                path = new File(ResourceUtils.getURL("classpath:").getPath());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (!path.exists()) path = new File("");
            System.out.println("path:" + path.getAbsolutePath());

            //如果上传目录为/static/File/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(), "static/images/");
            if (!upload.exists()) upload.mkdirs();
            //System.out.println("upload url:"+upload.getAbsolutePath());

            String realPath = upload.getAbsolutePath();


            System.out.println(realPath);
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名,默认同名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return null;//和上面同样的问题，待解决（恼）
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return Result.Error("下载失败！！！");

    }

    @Override
    public Result DeleteCa(String token) {
        String uid = JwtUtil.Parse("This is a test!!",token).get("uid").toString();
        cm.Delete(uid);
        return Result.Success("成功删除");
    }
}
