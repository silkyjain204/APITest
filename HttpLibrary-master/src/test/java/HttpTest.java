import http.HTTPRequest;
import http.SimpleOauth10;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.*;
import org.scribe.oauth.OAuth10aServiceImpl;
import org.scribe.oauth.OAuthService;
import org.testng.Assert;
import org.testng.annotations.Test;
public class HttpTest {

    HTTPRequest request = new HTTPRequest();
    HttpResponse response = null;
    int code;
    String result;

    @Test(description="Test Http Get")
    public void HttpGetTest()
    {
        try
        {
           response = request.GetData("http://httpbin.org/get");
            code = response.getStatusLine().getStatusCode();
            Assert.assertEquals(code,200);
            result = EntityUtils.toString(response.getEntity());
            System.out.print(result);
        }
        catch (Exception ex)
        {
            System.out.print(ex);
            Assert.fail();
        }
    }

    @Test(description="Test Http Post")
    public void HttpPostTest()
    {
        try
        {
            response = request.PostData("http://httpbin.org/post","Test Data");
            code = response.getStatusLine().getStatusCode();
            Assert.assertEquals(code,200);
            result = EntityUtils.toString(response.getEntity());
            System.out.print(result);
        }
        catch (Exception ex)
        {
            System.out.print(ex);
            Assert.fail();
        }
    }

    @Test
    public void test()
    {
        OAuthService service = new ServiceBuilder()
                .provider(SimpleOauth10.class)
                .signatureType(org.scribe.model.SignatureType.QueryString)
                .apiKey("api key")
                .apiSecret("api secret")
                .scope("API.Public")
                .build();

        OAuthRequest request = new OAuthRequest(Verb.GET,"url");


        service.signRequest(OAuthConstants.EMPTY_TOKEN, request);
        Response response = request.send();
        System.out.println(response.getBody());
    }
}
