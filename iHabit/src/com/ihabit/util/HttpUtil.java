package com.ihabit.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ihabit.bean.HttpResult;
import com.ihabit.config.Config;

public class HttpUtil {

	public static HttpResult<Boolean> createHabit(String key) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("KEY", key));
		JSONObject result = httpPost(Config.Host + Config.CREATE_HABIT, params);
		HttpResult<Boolean> httpResult = new HttpResult<Boolean>();
		if (result == null) {
			httpResult.setCode(Config.NO_RESULT_CODE);
			httpResult.setMessage(Config.NO_RESULT_MESSAGE);
		} else {
			try {
				httpResult.setCode(result.getInt("code"));
				httpResult.setMessage(result.getString("message"));
				httpResult.setData(result.getBoolean("data"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return httpResult;
	}

	private static JSONObject httpGet(String url) {
		JSONObject jsonObject = null;
		HttpGet myget = new HttpGet(url);
		try {
			HttpResponse response = new DefaultHttpClient().execute(myget);
			String retSrc = EntityUtils.toString(response.getEntity());
			jsonObject = new JSONObject(retSrc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private static JSONObject httpPost(String url, List<NameValuePair> params) {
		JSONObject result = null;
		try {
			HttpPost httpRequest = new HttpPost(url);
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				result = new JSONObject(strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
