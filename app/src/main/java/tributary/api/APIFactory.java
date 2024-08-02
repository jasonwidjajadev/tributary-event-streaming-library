package tributary.api;

import tributary.core.AdminClient;

public class APIFactory {
    public static AdminClient createAdminClient() {
        return new AdminClient();
    }
}
