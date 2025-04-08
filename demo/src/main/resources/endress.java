
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class endress {
    @Bean
    public void configureServerAddress(ApplicationContext applicationContext) throws UnknownHostException {

        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        

        System.setProperty("server.address", ipAddress);
    }
}
