
import com.cnzakii.tiedyer.TiedyerBackendApplication;
import com.cnzakii.tiedyer.util.token.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * token 测试类
 *
 * @author Zaki
 * @since 2023-10-28
 **/

@SpringBootTest(classes = TiedyerBackendApplication.class)
public class TestToken {

    @Test
    void createJwt(){
        String token = JwtUtils.createToken("1","user");
        System.out.println(token);
    }

    @Test
    void readJwt(){
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxIiwicm9sZSI6InVzZXIiLCJpYXQiOjE2OTg0OTM0MzksImV4cCI6MTY5ODUwMDYzOX0.31-TMSGdGD8uyIw1enWmu-oeDdtBT13uaFAaP07Lvkg";

        System.out.println(JwtUtils.getUserId(jwtStr));
    }
}
