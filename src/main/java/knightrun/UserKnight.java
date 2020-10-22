package knightrun;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import knightrun.arme.*;
import knightrun.armure.*;
import knightrun.bottes.*;
import knightrun.cape.*;
import knightrun.casque.*;
import knightrun.gants.*;

public class UserKnight {

	private User user;
	private Knight knight;
	private int pieces;
	private int rubis;
	
	public UserKnight(User user) {
		this.user = user;
		knight = new Knight(new ArcHydrolique(20)
				, new ArmureDeNovice(1)
				, new BottesDeNovice(1)
				, new CapeDechiree(1)
				, new CasqueDeNovice(1)
				, new GantsDeNovice(1));
		pieces = 0;
		rubis = 0;
	}
	
	public EmbedBuilder createEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setTitle(user.getName() + "  |  Puissance : " + knight.getPuissance());
		embed.setDescription("DPS : " + knight.getDps() + "  |  HP : " + knight.getHp() + "\nPièces : " + pieces + "  |  Rubis : " + rubis);

		knight.getArme().createField(embed);
		knight.getArmure().createField(embed);
		knight.getBottes().createField(embed);
		knight.getCape().createField(embed);
		knight.getCasque().createField(embed);
		knight.getGants().createField(embed);
		
		embed.setColor(Color.GREEN)
			.setThumbnail(user.getAvatar());
		
		return embed;
	}
	
	public User getUser() {
		return user;
	}
	
	public Knight getKnight() {
		return knight;
	}
	
	public int getPieces() {
		return pieces;
	}
	
	public int getRubis() {
		return rubis;
	}
	
}
