package com.ai.slp.mall.web.test.idps;

import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.util.ImageByteUtil;
import com.ai.paas.ipaas.image.IImageClient;

public class IdpsTest {
	@Ignore
	@Test
	public void addImage() {
		// 应用场景
		String idpsns = "slp-mall-web-idps";
		// 获取imageClient
		IImageClient im = IDPSClientFactory.getImageClient(idpsns);

		// 待上传的图片路径
		String filepath = "E:\\pic3\\";
		/*String[] fileNames = new String[]{
				"1000000000000001.jpg",
				"1000000000000002.jpg",
				"1000000000000003.jpg",
				"1000000000000004.jpg"
				};*/
		for(int i=97;i<=116;i++){
			// 将路径转换为byte[]
			String filename="1000000000000";
			if(i<100){
				filename=filename+"0"+i+".png";
			}
			else{
				filename=filename+i+".png";
			}
			
			
			byte[] buff = ImageByteUtil.image2byte(filepath+filename);
			// 上传图片，获取上传后的ID
			String idpsId = im.upLoadImage(buff, filename);
			System.out.println(filename + "  idpsId=" + idpsId);
			// 获取上传图片的URL
			System.out.println(im.getImageUrl(idpsId, ".jpg"));
			// 获取上传图片指定尺寸的URL
			//System.out.println(im.getImageUrl(idpsId, ".jpg", "100x80"));
		}
	}
	
	@Test
	public void checkImage(){
		
	}
}
