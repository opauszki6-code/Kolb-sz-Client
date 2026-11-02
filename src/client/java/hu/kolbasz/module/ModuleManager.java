package hu.kolbasz.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("kolbasz.json");

    public void register(Module m) { modules.add(m); }
    public List<Module> getModules() { return modules; }

    public Module getByName(String name) {
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void save() {
        try {
            JsonObject root = new JsonObject();
            for (Module m : modules) root.add(m.getName(), m.save());
            Files.writeString(configFile, GSON.toJson(root));
        } catch (Exception ignored) {}
    }

    public void load() {
        try {
            if (!Files.exists(configFile)) return;
            JsonObject root = GSON.fromJson(Files.readString(configFile), JsonObject.class);
            for (Module m : modules) {
                if (root.has(m.getName())) m.load(root.getAsJsonObject(m.getName()));
            }
        } catch (Exception ignored) {}
    }
}
