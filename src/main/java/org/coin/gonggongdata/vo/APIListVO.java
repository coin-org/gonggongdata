package org.coin.gonggongdata.vo;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("api")
public class APIListVO {
	
	private List<APIDataVO> apiDataList;
	
	@Data
	public static class APIDataVO {
		
		/**
		 * 카테고리 유형(ex:노래방, 영화관)
		 */
		private String category;

		/**
		 * 데이터 리스트
		 */
		private List<APIVO> apiList;
		
		@Data
		public static class APIVO {
			
			/**
			 * 데이터 위치
			 */
			private String location;
			
			/**
			 * API 주소 
			 */
			private String api;
			
			/**
			 * 파일 인코딩 
			 */
			private String charset;
			

		}
		
	}
}
