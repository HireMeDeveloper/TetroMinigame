package me.hiremedev.tetrisplugin.database;

import com.mongodb.client.MongoCollection;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.display.screens.ScreenStyle;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.Axis;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private MongoCollection<Document> systemCollection;
    private SystemManager systemManager;
    private JavaPlugin plugin;
    private KeyManager keyManager;
    private ScoreboardHandler scoreboardHandler;

    public DatabaseManager(MongoCollection<Document> collection, SystemManager systemManager, JavaPlugin plugin, KeyManager keyManager, ScoreboardHandler scoreboardHandler) {
        this.systemCollection = collection;
        this.systemManager = systemManager;
        this.plugin = plugin;
        this.keyManager = keyManager;
        this.scoreboardHandler = scoreboardHandler;
    }

    public void updateSystemDoc(SystemDocument systemDocument) {
        var system = systemDocument.getName();
        var filter = new Document("system", system);
        var found = systemCollection.find(filter).first();
        Bukkit.getLogger().info(found.toString());
        if (found == null) {
            Bukkit.getLogger().info("Tried to update invalid document. (Not found for system %s)".formatted(system));
            return;
        }
        systemCollection.findOneAndReplace(found, saveSystemDocument(systemDocument, false));
    }

    public Document saveSystemDocument(SystemDocument systemDocument) {
        return saveSystemDocument(systemDocument, true);
    }

    public Document saveSystemDocument(SystemDocument systemDocument, Boolean save) {
        var doc = new Document("system", systemDocument.getName());
        doc.append("axis", systemDocument.getAxis().toString());
        doc.append("x", systemDocument.getOrigin().getBlockX());
        doc.append("y", systemDocument.getOrigin().getBlockY());
        doc.append("z", systemDocument.getOrigin().getBlockZ());
        doc.append("name", systemDocument.getHsName());
        doc.append("highscore", systemDocument.getHighScore());
        doc.append("style", systemDocument.getStyle().toString());
        if (save) systemCollection.insertOne(doc);
        return doc;
    }


    public void createSystemsFromDatabase() {
        var systemDocs = loadSystemDocuments();
        if (systemDocs.size() == 0) {
            Bukkit.getLogger().info("No systems to load.");
            return;
        }
        systemDocs.forEach(
                (doc) -> {
                    var system = new TetrominoeSystem(
                            doc.getName(),
                            doc.getOrigin(),
                            doc.getAxis(),
                            doc.getStyle(),
                            plugin,
                            scoreboardHandler,
                            this,
                            systemManager,
                            keyManager,
                            false
                    );
                    system.setHighScore(doc.getHighScore());
                    system.setHighScoreName(doc.getHsName());
                }
        );
    }


    private List<SystemDocument> loadSystemDocuments() {
        var list = new ArrayList<SystemDocument>();
        var found = systemCollection.find();
        var it = found.iterator();
        while (it.hasNext()) {
            var doc = it.next();
            list.add(new SystemDocument(
                    doc.getString("system"),
                    Axis.valueOf(doc.getString("axis")),
                    new Location(
                            Bukkit.getWorld("world"),
                            doc.getInteger("x"),
                            doc.getInteger("y"),
                            doc.getInteger("z")),
                    doc.getInteger("highscore"),
                    doc.getString("name"),
                    ScreenStyle.valueOf(doc.getString("style"))
            ));
        }
        return list;
    }

}
