package si.fri.rso.orders.cdi.configuration;
import com.kumuluz.ee.configuration.cdi.ConfigBundle;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("orders")
@ApplicationScoped
public class OrdersProperties {

    private String cartsUrl = "http://localhost:3002";

    public String getCartsUrl() {
        return cartsUrl;
    }

    public void setCartsUrl(String catalogueUrl) {
        this.cartsUrl = catalogueUrl;
    }
}
