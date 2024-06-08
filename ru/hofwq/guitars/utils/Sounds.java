package ru.hofwq.guitars.utils;

import org.bukkit.entity.Player;

public enum Sounds {
	vzveites_kostrami(40), 
    blow_with_the_fires(123),
    doomed_to_be_defeated(82),
    feeling_good(75),
    glimmering_coals(53),
    pile(63),
    revenga(67),
    scarytale(66),
    that_s_out_madhouse(121),
    you_won_t_let_me_down(94);
	
	private final int duration;

    Sounds(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
    
    public static void playSound(String soundName, Player player) {
        Sounds soundToPlay = null;

        for (Sounds sound : Sounds.values()) {
            if (sound.name().equals(soundName)) {
                soundToPlay = sound;
                break;
            }
        }

        if (soundToPlay == null) {
            return;
        }

        player.playSound(player.getLocation(), "minecraft:my_sounds." + soundToPlay.name().toLowerCase(), 1.0F, 1.0F);
    }
}