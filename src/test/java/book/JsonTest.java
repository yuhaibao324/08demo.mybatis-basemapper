package book;

import org.junit.Test;

public class JsonTest {
	
	@Test
	public void test() {
		/*UserExample userExample = new UserExample();
		userExample.createCriteria().andIdLessThan(2).andNameIn(Arrays.asList(new String[]{"zhou","xu"}));
		Gson gson = new Gson();
		String json = gson.toJson(userExample);
		Example example = gson.fromJson(json, Example.class);
		
		System.out.println(example);*/
		/*JsonElement ele = gson.toJsonTree(userExample);
		JsonObject root = ele.getAsJsonObject();
		JsonArray oredCriteria = root.getAsJsonArray("oredCriteria");
		oredCriteria.forEach(criteriaEle->{
			JsonObject criteria = criteriaEle.getAsJsonObject();
			criteria.getAsJsonObject("valid");
			JsonArray criteriaArray = criteria.getAsJsonArray("criteria");
			boolean valid = criteria.getAsJsonObject("criteria").getAsBoolean();
			if(valid){
				JsonArray criteriaArry  = criteria.getAsJsonArray("criteria");
				criteriaArry.forEach(criterionEle->{
					System.out.println(criterionEle);
				});
			}
		});*/
	}
}