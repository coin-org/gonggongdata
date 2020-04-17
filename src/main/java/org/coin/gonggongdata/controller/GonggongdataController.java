package org.coin.gonggongdata.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.coin.gonggongdata.vo.APIListVO;
import org.coin.gonggongdata.vo.APIListVO.APIDataVO.APIVO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
public class GonggongdataController {

	private final List<APIListVO> apiList;

	public GonggongdataController(List<APIListVO> apiList) {
		this.apiList = apiList;
	}

	@GetMapping(value = "/category")
	public Object getCategories() {
		return apiList.stream()
				.flatMap(apiListVO -> apiListVO.getApiDataList().stream().map(apiDataVO -> apiDataVO.getCategory()));
	}

	@GetMapping(value = "/{category}/api")
	public Object getAPIListFromCategory(@PathVariable String category) {
		List<String> categoryApiList = apiList.stream()
				.flatMap(apiListVO -> apiListVO.getApiDataList().stream()
						.filter(apiDataVO -> apiDataVO.getCategory().contentEquals(category))
						.flatMap(apiDataVO -> apiDataVO.getApiList().stream().map(apiVO -> apiVO.getApi())))
				.collect(Collectors.toList());

		return categoryApiList;
	}

	@GetMapping(value = "/{category}/location")
	public Object getLocationListFromCategory(@PathVariable String category) {
		List<String> categoryApiList = apiList.stream()
				.flatMap(apiListVO -> apiListVO.getApiDataList().stream()
						.filter(apiDataVO -> apiDataVO.getCategory().contentEquals(category))
						.flatMap(apiDataVO -> apiDataVO.getApiList().stream().map(apiVO -> apiVO.getLocation())))
				.collect(Collectors.toList());

		return categoryApiList;
	}

	@GetMapping(value = "/{category}/{location}")
	public Object getDataFromCategory(@PathVariable String category, @PathVariable String location) {
		APIVO api = apiList.stream().flatMap(apiListVO -> apiListVO.getApiDataList().stream()
				.filter(apiDataVO -> apiDataVO.getCategory().contentEquals(category))
				.flatMap(apiDataVO -> apiDataVO.getApiList().stream()
						.filter(apiVO -> apiVO.getLocation().contentEquals(location))))
				.findFirst().orElse(null);
		return api == null ? "해당 데이터가 없습니다." : new String(new RestTemplate().getForObject(api.getApi(), byte[].class), Charset.forName(api.getCharset()));
	}

}
