package com.ghostflower.CA_System.controller;

import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.Result;
import com.ghostflower.CA_System.service.CaService;
import com.ghostflower.CA_System.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ca")
public class CaController {
    @Autowired
    private CaService cs;

    /**
     * 证书申请，一 人一 证
     * @param request
     * @return
     */
    //返回证书页面
    @GetMapping("/apply")
    public Result Apply(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);
        if(ca==null){
            //说明没有该证书记录，可以注册
            cs.Apply(token);
            return Result.Success();

        }else{
            return Result.Error("用户证书已存在！！！");
        }


    }

    /**
     * 证书查询
     * @param request
     * @return
     */
    @GetMapping("/query")
    public Result Query(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);
        if(ca==null){


            return Result.Error("用户尚未申请证书！！！");

        }else{
            return Result.Success(ca);//证书存在返回证书，原则上限制一 人一 证
        }


    }

    /**
     * 将证书上传给客户端（前端）
     * @param response
     * @return
     */

    @GetMapping("/download")
    public Result downloadFile(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "test.txt";
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);
        if(ca==null){
            return Result.Error("用户尚未申请证书！！！");
        }else{

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

            return null;//证书存在返回证书，原则上限制一 人一 证,怪哉，明明已经关闭数据流了，但返回的成功信息依然会附加在传输的文件里？？？
        }

    }

    @GetMapping("/downloadtest")
    public Result downloadTest(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "img1.png";

        if (fileName != null) {

            //获取根目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
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






}