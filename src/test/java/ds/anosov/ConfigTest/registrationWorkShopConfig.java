package ds.anosov.ConfigTest;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/registrationWorkShop.properties"})
public interface registrationWorkShopConfig extends Config {
    @Key("loginWorkShop")
    String loginWorkShop();
    @Key("passwordWorkShop")
    String passwordWorkShop();
    @Key("authCookieName")
    String authCookieName();
}
