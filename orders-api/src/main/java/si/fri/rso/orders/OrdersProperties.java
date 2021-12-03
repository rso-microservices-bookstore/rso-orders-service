package si.fri.rso.orders;
import com.kumuluz.ee.configuration.cdi.ConfigBundle;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("orders")
@ApplicationScoped
public class OrdersProperties {

    private String catalogueUrl = "http://localhost:3000";

    public String getCatalogueUrl() {
        return catalogueUrl;
    }

    public void setCatalogueUrl(String catalogueUrl) {
        this.catalogueUrl = catalogueUrl;
    }
}
