package com.democome.rnserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RnController {

	/**
	 * Android Host 首页接口
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public HomeResponse index() {

		HomeResponse response = new HomeResponse();

		Bundle bundleA = new Bundle();
		bundleA.setName("AModel");
		bundleA.setDesc("第一个模块");

		Bundle bundleB = new Bundle();
		bundleB.setName("BModel");
		bundleB.setDesc("第二个模块");

		List<Bundle> list = new ArrayList<>();
		list.add(bundleA);
		list.add(bundleB);

		response.setData(list);

		return response;
	}

	/**
	 * bundle详情
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bundle/{id}", method = RequestMethod.GET)
	public BundleResponse getBundle(@PathVariable int id) {

		BundleResponse response = new BundleResponse();

		if (id == 1) {
			response.setName("AModel");
		} else {
			response.setName("BModel");
		}

		response.setLogo("https://i.loli.net/2018/12/10/5c0e49bb377ec.png");
		return response;
	}

	/**
	 * bundle下载接口
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download/bundle/{name}", method = RequestMethod.GET, produces = "application/zip")
	public ResponseEntity<Resource> download(@PathVariable String name) throws IOException {

		System.err.println("download :" + name);

		File file = null;
		if (name.equals("AModel")) {
			// 配置你自己的路径
			file = new File("/Users/yangpeng/AModel.zip");
		} else if (name.equals("BModel")) {
			file = new File("/Users/yangpeng/BModel.zip");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + name + ".zip");
		headers.add("Pragma", "no-cache");

		headers.add("Expires", "0");

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
}