package com.ghostflower.CA_System.service;

import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;

public interface CaService {
    //查找用户

    /**
     * 查找用户证书
     * @param token
     * @return
     */
    Ca FindCa(String token);

    /**
     * 申请证书
     * @param token
     */
    void Apply(String token);

    /**
     * 下载证书
     * @param ca
     * @param fileName
     * @param response
     */
    void DownloadCa(Ca ca, String fileName, HttpServletResponse response);

    /**
     * 下载其他文件
     * @param fileName
     * @param response
     * @return
     */
    Result DownloadOtherFiles(String fileName, HttpServletResponse response);

    /**
     * 撤销证书
     * @param token
     * @return
     */
    Result DeleteCa(String token);

}
