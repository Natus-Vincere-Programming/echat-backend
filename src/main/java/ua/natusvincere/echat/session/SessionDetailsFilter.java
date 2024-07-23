package ua.natusvincere.echat.session;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class SessionDetailsFilter extends OncePerRequestFilter {

    private DatabaseReader dbReader;

    public SessionDetailsFilter() throws IOException {
        /*File database = new File("test.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();*/
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /*chain.doFilter(request, response);

        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionDetails details = new SessionDetails();
            details.setAccessType(request.getHeader("User-Agent"));
            try {
                String geoLocation = getGeoLocation(request.getRemoteAddr());
                details.setLocation(request.getRemoteAddr() + " " + geoLocation);

            } catch (GeoIp2Exception e) {
                log.warn("Failed to get geo location for IP: {}", request.getRemoteAddr(), e);
            }
            session.setAttribute("SESSION_DETAILS", details);
        }*/
    }

    private String getGeoLocation(String remoteAddr) throws IOException, GeoIp2Exception {
       /* InetAddress ipAddress = InetAddress.getByName(remoteAddr);
        CountryResponse response = dbReader.country(ipAddress);
        CityResponse city = dbReader.city(ipAddress);
        return String.join(", ", city.getCity().getName(), response.getCountry().getName());*/
        return null;
    }
}
