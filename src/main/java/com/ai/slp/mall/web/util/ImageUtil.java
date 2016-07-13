package com.ai.slp.mall.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.paas.ipaas.image.IImageClient;

public class ImageUtil {
    public static String getImage(String vsid, String pictype) {
        IImageClient im = null;
        // 应用场景
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        im = IDPSClientFactory.getImageClient(idpsns);
        // 获取上传图片的URL
        return im.getImageUrl(vsid,pictype);
    }
    
    public static String getHotImage(String vsid, String pictype) {
        IImageClient im = null;
        // 应用场景
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        im = IDPSClientFactory.getImageClient(idpsns);
        // 获取上传图片的URL
        // 获取上传图片的URL
        return im.getImageUrl(vsid,pictype);
        // 获取上传图片指定尺寸的URL
        // System.out.println(im.getImageUrl("574514c1d601800009c0b0ba", ".jpg","100x80"));
    }

    public static List<String> getImages(Map<String, String> imgMap) {
        IImageClient im = null;
        // 应用场景
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        im = IDPSClientFactory.getImageClient(idpsns);
        List<String> list = new ArrayList<String>();
        // 获取上传图片指定尺寸的URL
        for (Map.Entry<String, String> entry : imgMap.entrySet()) {
            String url = im.getImageUrl(entry.getKey(),entry.getValue(), "100x80");
            list.add(url);
        }
        return list;
    }

    //上传图片
    public static String AddImg(byte[] imgByte,String imgName) {
        // 应用场景
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);

        // 上传图片，获取上传后的ID
        String idpsId = im.upLoadImage(imgByte, imgName);
        // 获取上传图片的URL
        System.out.println(im.getImageUrl(idpsId, ".jpg"));
        // 获取上传图片指定尺寸的URL
        return im.getImageUrl(idpsId, ".jpg", "100x80");

    }
}
