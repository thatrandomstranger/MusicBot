package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.commands.DJCommand;
import org.slf4j.LoggerFactory;


public class SeekCmd extends DJCommand {
    public SeekCmd(Bot bot) {
        super(bot);
        this.name = "seek";
        this.arguments = "<seconds>";
        this.help = "go to a specific point in the song";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) {
        AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();

        String args = event.getArgs();
        if (args.matches("\\s*([0-9]+)\\s*")) {
            int seconds = Integer.parseInt(args);
            assert handler != null;
            handler.getPlayer().getPlayingTrack().setPosition(seconds * 1000L);
            event.reply(":musical_note: **Set position to** "
                    + (seconds / 60) + ":" + String.format("%02d", seconds % 60) + " :fast_forward:");
        } else if (args.matches("\\s*([0-9]+:[0-5]?[0-9])\\s*")) {
            String[] a = args.split(":");
            assert a.length == 2;
            int minutes = Integer.parseInt(a[0]);
            int seconds = 60 * minutes + Integer.parseInt(a[1]);
            assert handler != null;
            handler.getPlayer().getPlayingTrack().setPosition(seconds * 1000L);
            event.reply(":musical_note: **Set position to** "
                    + (seconds / 60) + ":" + String.format("%02d", seconds % 60) + " :fast_forward:");
        } else {
            event.replyError("Invalid arguments for seek, idiot!");
        }
    }
}
