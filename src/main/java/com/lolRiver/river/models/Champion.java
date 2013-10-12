package com.lolRiver.river.models;

import com.lolRiver.config.ConfigMap;
import com.lolRiver.river.controllers.deserializers.appconstants.KassadinAppConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
/**
 * @author mxia (mxia@lolRiver.com)
 *         9/29/13
 */

public class Champion {
    private static final Logger LOGGER = Logger.getLogger(Champion.class.getName());

    public enum Name {
        AATROX,
        AHRI,
        AKALI,
        ALISTAR,
        AMUMU,
        ANIVIA,
        ANNIE,
        ASHE,
        BLITZCRANK,
        BRAND,
        CAITLYN,
        CASSIOPEIA,
        CHOGATH,
        CORKI,
        DARIUS,
        DIANA,
        DRMUNDO,
        DRAVEN,
        ELISE,
        EVELYNN,
        EZREAL,
        FIDDLESTICKS,
        FIORA,
        FIZZ,
        GALIO,
        GANGPLANK,
        GAREN,
        GRAGAS,
        GRAVES,
        HECARIM,
        HEIMERDINGER,
        IRELIA,
        JANNA,
        JARVANIV,
        JAX,
        JAYCE,
        JINX,
        KARMA,
        KARTHUS,
        KASSADIN,
        KATARINA,
        KAYLE,
        KENNEN,
        KHAZIX,
        KOGMAW,
        LEBLANC,
        LEESIN,
        LEONA,
        LISSANDRA,
        LUCIAN,
        LULU,
        LUX,
        MALPHITE,
        MALZAHAR,
        MAOKAI,
        MASTERYI,
        MISSFORTUNE,
        MORDEKAISER,
        MORGANA,
        NAMI,
        NASUS,
        NAUTILUS,
        NIDALEE,
        NOCTURNE,
        NUNU,
        OLAF,
        ORIANNA,
        PANTHEON,
        POPPY,
        QUINN,
        RAMMUS,
        RENEKTON,
        RENGAR,
        RIVEN,
        RUMBLE,
        RYZE,
        SEJUANI,
        SHACO,
        SHEN,
        SHYVANA,
        SINGED,
        SION,
        SIVIR,
        SKARNER,
        SONA,
        SORAKA,
        SWAIN,
        SYNDRA,
        TALON,
        TARIC,
        TEEMO,
        THRESH,
        TRISTANA,
        TRUNDLE,
        TRYNDAMERE,
        TWISTEDFATE,
        TWITCH,
        UDYR,
        URGOT,
        VARUS,
        VAYNE,
        VEIGAR,
        VI,
        VIKTOR,
        VLADMIR,
        VOLIBEAR,
        WARWICK,
        WUKONG,
        XERATH,
        XINZHAO,
        YORICK,
        ZAC,
        ZED,
        ZIGGS,
        ZILEAN,
        ZYRA
    }

    private Name name;

    public Champion(Name name) {
        this.name = name;
    }

    public static Champion fromId(String id) {
        Name name = KassadinAppConstants.CHAMPION_MAPPING.get(id);
        if (name == null) {
            LOGGER.error("Champion id: " + id + " not found in KassadinAppConstants.CHAMPION_MAPPING");
            throw new RuntimeException();
        }
        return new Champion(name);
    }

    public static Champion fromString(String championName) {
        if (StringUtils.isBlank(championName)) {
            return null;
        }
        return new Champion(Champion.Name.valueOf(championName));
    }

    public boolean shouldPlayRole(Role role) {
        return rolePotential(role, 2);
    }

    public boolean canPlayRole(Role role) {
        return rolePotential(role, 1);
    }

    public boolean cannotPlayRole(Role role) {
        return rolePotential(role, 0);
    }

    // roleCondition: 2 = should play, 1 = can play, 0 = cannot play
    private boolean rolePotential(Role role, int roleCondition) {
        ConfigMap configMap = new ConfigMap().getConfigMap("champion" + "." + this.name.name());
        try {
            String roleString = role.getName().name().toLowerCase();
            return configMap.getInteger(roleString) == roleCondition;
        } catch (Exception e) {
            LOGGER.error("config map has invalid param for role: " + role, e);
        }
        return false;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Champion{" +
               "name=" + name +
               '}';
    }
}
