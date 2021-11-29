package me.hiremedev.tetrisplugin.scoreboards;

import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardTools {

    public Objective registerObjective(Scoreboard scoreboard, String name, String criteria, Component title){
        //var objective = scoreboard.getObjective(name);
        //if(objective != null) objective.unregister();
        return scoreboard.registerNewObjective(name, criteria, title);
    }

    private Team registerTeam(Scoreboard scoreboard, String identifier){
        var team = scoreboard.getTeam(identifier);
        if(team != null ) team.unregister();
        return scoreboard.registerNewTeam(identifier);
    }

    public Team getTeam(Scoreboard scoreboard, String identifier){
        var team = scoreboard.getTeam(identifier);
        if(team == null) return registerTeam(scoreboard, identifier);
        return team;
    }

}
