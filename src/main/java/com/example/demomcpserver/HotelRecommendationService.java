package com.example.demomcpserver;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class HotelRecommendationService {

	public record HotelInfo(String name, String description, String url) {
	}

	@Tool(description = """
			Returns a list of recommended luxury hotels.

			This tool provides a curated list of high-end hotels in Japan, mainly located in Tokyo.
			These hotels are known for their exceptional service, elegant interiors, and premium amenities.
			The number of hotels returned is limited to the specified count.
			If the requested count exceeds the available number of hotels, all available hotels will be returned.

			Each hotel in the returned list contains the following information:
			- name: The name of the hotel (in Japanese)
			- description: A short description highlighting the features and appeal of the hotel (in Japanese)
			- url: A URL linking to a reservation site where more details and bookings are available
			""")
	public List<HotelInfo> getRecommendedHotels(
			@ToolParam(description = "count the maximum number of hotel recommendations to return") int count) {
		List<HotelInfo> hotels = new ArrayList<>();

		hotels.add(new HotelInfo("ザ・リッツ・カールトン東京", "東京ミッドタウン内に位置し、都心の絶景を望む高級ホテル。洗練されたサービスと豪華な客室が魅力です。",
				"https://www.ikyu.com/00001290/"));
		hotels.add(new HotelInfo("パーク ハイアット 東京", "新宿の高層ビルに位置し、都会の喧騒を忘れさせる静寂と上質なサービスを提供するラグジュアリーホテルです。",
				"https://www.ikyu.com/00000588/"));
		hotels.add(new HotelInfo("アマン東京", "大手町に位置する、和の美学を取り入れた洗練されたデザインと静寂が魅力の高級ホテルです。",
				"https://www.ikyu.com/00002014/"));
		hotels
			.add(new HotelInfo("帝国ホテル 東京", "日本を代表する老舗ホテルで、伝統と格式を重んじたサービスと設備が特徴です。", "https://www.ikyu.com/00000346/"));
		hotels.add(new HotelInfo("ザ・ペニンシュラ東京", "日比谷に位置し、洗練されたデザインと最高級のサービスを提供するラグジュアリーホテルです。",
				"https://www.ikyu.com/00001119/"));
		hotels.add(new HotelInfo("マンダリン オリエンタル 東京", "日本橋に位置し、洗練されたデザインと高品質なサービスが魅力の高級ホテルです。",
				"https://www.ikyu.com/00000345/"));
		hotels.add(new HotelInfo("ザ・キャピトルホテル 東急", "永田町に位置し、和の要素を取り入れたデザインと静寂が魅力のラグジュアリーホテルです。",
				"https://www.ikyu.com/00000344/"));
		hotels.add(new HotelInfo("コンラッド東京", "汐留に位置し、東京湾の景色を一望できるモダンで洗練された高級ホテルです。", "https://www.ikyu.com/00000343/"));
		hotels.add(new HotelInfo("シャングリ・ラ 東京", "東京駅近くに位置し、豪華な内装と高品質なサービスが魅力のラグジュアリーホテルです。",
				"https://www.ikyu.com/00001120/"));
		hotels.add(new HotelInfo("フォーシーズンズホテル丸の内 東京", "東京駅直結の便利な立地にあり、洗練されたデザインとパーソナライズされたサービスを提供する高級ホテルです。",
				"https://www.ikyu.com/00001121/"));

		return hotels.subList(0, Math.min(count, hotels.size()));
	}

}
