package com.ai.slp.mall.web.model.user;

import java.util.ArrayList;
import java.util.List;

import com.ai.slp.user.api.keyinfo.param.CmCustFileExtVo;

public class CustFileListVo {

    private List<CmCustFileExtVo> list = new ArrayList<CmCustFileExtVo>();

    public List<CmCustFileExtVo> getList() {
        return list;
    }

    public void setList(List<CmCustFileExtVo> list) {
        this.list = list;
    }

}
