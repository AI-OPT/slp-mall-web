package com.ai.slp.mall.web.model.user;

import java.util.ArrayList;
import java.util.List;

import com.ai.slp.user.api.keyinfo.param.InsertCustFileExtRequest;

public class CustFileListVo {

    private List<InsertCustFileExtRequest> list = new ArrayList<InsertCustFileExtRequest>();

    public List<InsertCustFileExtRequest> getList() {
        return list;
    }

    public void setList(List<InsertCustFileExtRequest> list) {
        this.list = list;
    }

}
