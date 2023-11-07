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
    public Result DownloadFile(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "test.txt";
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);
        if(ca==null){
            return Result.Error("用户尚未申请证书！！！");
        }else{
            cs.DownloadCa(ca,fileName,response);
            return null;//证书存在返回证书，原则上限制一 人一 证,怪哉，明明已经关闭数据流了，但返回的成功信息依然会附加在传输的文件里？？？
        }

    }

    @GetMapping("/downloadtest")
    public Result DownloadOtherFiles(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "img1.png";

        return cs.DownloadOtherFiles(fileName,response);
    }

    @GetMapping("/delete")
    public Result DeleteCa(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        Ca ca = cs.FindCa(token);
        if(ca==null){

            return Result.Error("用户尚未申请证书！！！");

        }else{
            return cs.DeleteCa(token);//证书存在返回证书，原则上限制一 人一 证
        }

    }




}