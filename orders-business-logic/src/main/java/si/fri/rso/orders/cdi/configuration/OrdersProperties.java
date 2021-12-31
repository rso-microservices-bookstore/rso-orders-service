package si.fri.rso.orders.cdi.configuration;
import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("orders-properties")
@ApplicationScoped
public class OrdersProperties {

    @ConfigValue(watch = true)
    private String cartsUrl;

    public String getCartsUrl() {
        return cartsUrl;
    }

    public void setCartsUrl(String catalogueUrl) {
        this.cartsUrl = catalogueUrl;
    }
}
