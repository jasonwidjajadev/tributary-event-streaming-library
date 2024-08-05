package tributary.api;

import tributary.core.AdminClient;

public class APIFactory {
    public static <T, K, V> API<T, K, V> createAdminClient() {
        return new AdminClient<>();
    }
}
