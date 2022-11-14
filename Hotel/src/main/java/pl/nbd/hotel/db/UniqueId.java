package pl.nbd.hotel.db;

import java.util.UUID;

public class UniqueId {
    private UUID uuid;

    public UniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
