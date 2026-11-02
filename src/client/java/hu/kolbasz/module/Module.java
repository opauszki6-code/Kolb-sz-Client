package hu.kolbasz.module;

import com.google.gson.JsonObject;

public abstract class Module {
    private final String name;
    private boolean enabled;

    protected Module(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }

    protected void onEnable() {}
    protected void onDisable() {}

    public JsonObject save() {
        JsonObject o = new JsonObject();
        o.addProperty("enabled", enabled);
        return o;
    }

    public void load(JsonObject o) {
        if (o.has("enabled") && o.get("enabled").getAsBoolean() && !enabled) {
            enabled = true;
            onEnable();
        }
    }
}
