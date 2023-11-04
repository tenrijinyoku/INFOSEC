package com.ghostflower.CA_System.service.impl;


import com.ghostflower.CA_System.mapper.CaMapper;
import com.ghostflower.CA_System.pojo.Ca;
import com.ghostflower.CA_System.service.CaService;
import com.ghostflower.CA_System.utils.CaUtil;
import com.ghostflower.CA_System.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
}
