package http;
import awsauth.AWS4SignerBase;
import awsauth.AWS4SignerForAuthorizationHeader;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.util.HashMap;


public class HTTPRequest {


    public HttpResponse AWSPostData(String url, String data) {
        HttpResponse response = null;
        try {
            System.out.print("-------------------------------------url-------------------------------------------------" + "\r\n");
            System.out.print(url + "\r\n");
            System.out.print("-------------------------------------url-------------------------------------------------" + "\r\n");
            AWS4SignerForAuthorizationHeader a = new AWS4SignerForAuthorizationHeader(new URL(url),"POST","execute-api",System.getProperty("region"));
            byte[] dta = AWS4SignerBase.hash(data);
            String decoded = Hex.encodeHexString(dta);

            Map<String,String> header = new HashMap<String, String>();
            Map<String,String> q = new HashMap<String, String>();
            // add headers here before computing signature
            //header.put("Content-Type","application/json");
            String auth = a.computeSignature(header,q,decoded,System.getProperty("awsAccessId"),System.getProperty("awsSecretKey"));
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            // Request Headers and other properties.
            for(String key: header.keySet()) {
                httppost.setHeader(key,header.get(key));
            }
            httppost.setHeader("Authorization",auth);
            HttpEntity entity = new ByteArrayEntity(data.getBytes("UTF-8"));
            httppost.setEntity(entity);
            response = httpclient.execute(httppost);

            System.out.print(response);
        }
        catch(Exception ex)
        {
            System.out.print(ex);
        }

        return response;
    }

    public HttpResponse AWSGetData(String url,boolean containsQueryParams) {
        HttpResponse response = null;
        String data = "";
        try {
            System.out.print("-------------------------------------url-------------------------------------------------" + "\r\n");
            System.out.print(url + "\r\n");
            System.out.print("-------------------------------------url-------------------------------------------------" + "\r\n");
            AWS4SignerForAuthorizationHeader a = new AWS4SignerForAuthorizationHeader(new URL(url),"GET","execute-api",System.getProperty("region"));
            byte[] dta = AWS4SignerBase.hash(data);
            String decoded = Hex.encodeHexString(dta);
            HttpGet httpget = new HttpGet(url);
            Map<String,String> header = new HashMap<String, String>();
            Map<String,String> q = new HashMap<String, String>();
            if(containsQueryParams)
            {
                List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");

                for (NameValuePair param : params) {
                    q.put(param.getName(),param.getValue());
                }
            }
            // add headers here before computing signature
            //header.put("Content-Type","application/json");
            String auth = a.computeSignature(header,q,decoded, System.getProperty("awsAccessId"),System.getProperty("awsSecretKey"));
            HttpClient httpclient = HttpClients.createDefault();

            // Request Headers and other properties.
            for(String key: header.keySet()) {
                httpget.setHeader(key,header.get(key));
            }
            httpget.setHeader("Authorization", auth);
            response = httpclient.execute(httpget);
            System.out.print(response);
        }
        catch(Exception ex)
        {
            System.out.print(ex);
        }

        return response;
    }

    public HttpResponse PostData(String url, String data) {
        HttpResponse response = null;
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
//            Request Headers and other properties.
//            httppost.setHeader("Content-Type","application/json");
//            httppost.setHeader("Authorization","");
            HttpEntity entity = new ByteArrayEntity(data.getBytes("UTF-8"));
            httppost.setEntity(entity);
            response = httpclient.execute(httppost);

        }
        catch(Exception ex)
        {
            System.out.print(ex);
        }

        return response;
    }

    public HttpResponse GetData(String url) {

        HttpResponse response = null;
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
//            Request Headers and other properties.
//            httpget.setHeader("Content-Type","application/json");
//            httpget.setHeader("Authorization","");
            //HttpEntity entity = new ByteArrayEntity(data.getBytes("UTF-8"));
            response = httpclient.execute(httpget);

        }
        catch(Exception ex)
        {
            System.out.print(ex);
        }

        return response;
    }

}
