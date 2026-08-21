package com.example.model

import androidx.compose.ui.graphics.Color

enum class DotStyle {
    CLASSIC_TARGET,   // Glowing dual circle bullseye
    CROSSHAIR,        // Tactical sniper crosshair with reticle lines
    PULSE_RINGS,      // Expanding ripple concentric radar waves
    ENERGY_ORB,       // Multi-layered plasma energy sphere
    STAR_CORE,        // 4-point glittering stellar star flare
    DIAMOND_CRYSTAL,  // Faceted crystal diamond prism
    BIOHAZARD,        // Radioactive toxic triple-arc biohazard
    CYBER_CHIP,       // Glowing silicon matrix microchip node
    BLACK_HOLE,       // Gravitational event horizon singularity
    LOTUS_ZEN,        // Peaceful blooming lotus flower petals
    HEART_PULSE,      // Beating arcade heart core
    RADAR_SWEEP,      // Tactical sweep sonar scanner
    SUN_FLARE,        // Blazing solar corona with radial flares
    MAGIC_RUNE,       // Ancient arcane glowing spell glyph
    SHIELD_AEGIS,     // Fortified defensive energy barrier
    SKULL_VIPER,      // Menacing pirate/assassin target skull
    YIN_YANG,         // Balanced dual-harmony orbit
    NEO_HEXAGON,      // High-tech sci-fi honeycomb hexagon
    EMERALD_GEM,      // Pristine faceted cut gemstone
    SUPERNOVA_BLAST,  // Primordial stellar explosion center
    ANCIENT_EYE,      // Egyptian Eye of Horus mystic golden glyph
    SNOWFLAKE_ICE,    // 6-pointed crystalline frozen frost snowflake
    FIRE_COMET,       // Molten blazing asteroid meteor core
    SHURIKEN_STAR,    // 4-bladed spinning ninja assassin steel star
    COMPASS_ROSE      // Nautical 8-point golden sailor compass
}

data class DotSkin(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val centerColor: Color,
    val glowColor: Color,
    val accentColor: Color,
    val style: DotStyle,
    val glowRadiusDp: Float = 22f
)

object DotSkinCatalog {
    val CLASSIC = DotSkin(
        id = "dot_classic",
        name = "Classic Ruby Dot",
        description = "High-contrast brilliant crimson target bullseye with vibrant outer glow.",
        price = 0,
        centerColor = Color(0xFFFF5252),
        glowColor = Color(0xFFFF1744),
        accentColor = Color(0xFFFFFFFF),
        style = DotStyle.CLASSIC_TARGET,
        glowRadiusDp = 22f
    )

    val SNIPER_CROSSHAIR = DotSkin(
        id = "dot_crosshair",
        name = "Sniper Reticle",
        description = "Military grade tactical precision green crosshair with calibrated mil-dots.",
        price = 20,
        centerColor = Color(0xFF00E676),
        glowColor = Color(0xFF00C853),
        accentColor = Color(0xFFB9F6CA),
        style = DotStyle.CROSSHAIR,
        glowRadiusDp = 24f
    )

    val PULSE_RADAR = DotSkin(
        id = "dot_pulse_radar",
        name = "Sonar Pulse",
        description = "Expanding aquatic cyan acoustic radar wave echoing across deep water.",
        price = 35,
        centerColor = Color(0xFF00E5FF),
        glowColor = Color(0xFF00B0FF),
        accentColor = Color(0xFFE0F7FA),
        style = DotStyle.PULSE_RINGS,
        glowRadiusDp = 26f
    )

    val PLASMA_ORB = DotSkin(
        id = "dot_plasma_orb",
        name = "Plasma Sphere",
        description = "Concentrated ball of superheated magenta ionized gas crackling with lightning.",
        price = 60,
        centerColor = Color(0xFFFF007F),
        glowColor = Color(0xFFD500F9),
        accentColor = Color(0xFFFFF0F5),
        style = DotStyle.ENERGY_ORB,
        glowRadiusDp = 24f
    )

    val STELLAR_STAR = DotSkin(
        id = "dot_stellar_star",
        name = "Nova Spark",
        description = "Four-pointed glittering diamond star radiating golden light beams.",
        price = 85,
        centerColor = Color(0xFFFFD700),
        glowColor = Color(0xFFFFC107),
        accentColor = Color(0xFFFFFDE7),
        style = DotStyle.STAR_CORE,
        glowRadiusDp = 25f
    )

    val PRISM_DIAMOND = DotSkin(
        id = "dot_prism_diamond",
        name = "Prism Diamond",
        description = "Faceted high-refraction jewel turning light into brilliant spectral hues.",
        price = 110,
        centerColor = Color(0xFF80DEEA),
        glowColor = Color(0xFF00E5FF),
        accentColor = Color(0xFFFFFFFF),
        style = DotStyle.DIAMOND_CRYSTAL,
        glowRadiusDp = 22f
    )

    val BIOHAZARD_CORE = DotSkin(
        id = "dot_biohazard_core",
        name = "Biohazard Slime",
        description = "Radioactive neon lime toxic containment icon warning of extreme danger.",
        price = 140,
        centerColor = Color(0xFF76FF03),
        glowColor = Color(0xFF64DD17),
        accentColor = Color(0xFFCCFF90),
        style = DotStyle.BIOHAZARD,
        glowRadiusDp = 25f
    )

    val CYBER_MATRIX = DotSkin(
        id = "dot_cyber_matrix",
        name = "Cyber Matrix",
        description = "Glowing silicon semiconductor microchip node with high-speed gold traces.",
        price = 180,
        centerColor = Color(0xFF00E5FF),
        glowColor = Color(0xFF2979FF),
        accentColor = Color(0xFFFFD54F),
        style = DotStyle.CYBER_CHIP,
        glowRadiusDp = 24f
    )

    val BLACK_HOLE_DOT = DotSkin(
        id = "dot_black_hole",
        name = "Dark Singularity",
        description = "Gravitational anomaly with dark matter center and purple photon ring.",
        price = 220,
        centerColor = Color(0xFF1A1A24),
        glowColor = Color(0xFF6200EA),
        accentColor = Color(0xFFB388FF),
        style = DotStyle.BLACK_HOLE,
        glowRadiusDp = 28f
    )

    val LOTUS_BLOOM = DotSkin(
        id = "dot_lotus_bloom",
        name = "Zen Lotus",
        description = "Sacred blooming pink lotus petals emitting tranquil spiritual peace.",
        price = 260,
        centerColor = Color(0xFFFF4081),
        glowColor = Color(0xFFF48FB1),
        accentColor = Color(0xFFFFF0F5),
        style = DotStyle.LOTUS_ZEN,
        glowRadiusDp = 24f
    )

    val ARCADE_HEART = DotSkin(
        id = "dot_arcade_heart",
        name = "Life Heart",
        description = "Retro pulsing pixel heart radiating warmth and extra vitality.",
        price = 300,
        centerColor = Color(0xFFFF1744),
        glowColor = Color(0xFFFF5252),
        accentColor = Color(0xFFFFCDD2),
        style = DotStyle.HEART_PULSE,
        glowRadiusDp = 22f
    )

    val RADAR_SWEEP_DOT = DotSkin(
        id = "dot_radar_sweep",
        name = "Tactical Sonar",
        description = "Revolving 360-degree combat radar sweep spotting incoming threats.",
        price = 350,
        centerColor = Color(0xFF00E676),
        glowColor = Color(0xFF1B5E20),
        accentColor = Color(0xFFB9F6CA),
        style = DotStyle.RADAR_SWEEP,
        glowRadiusDp = 26f
    )

    val SUN_CORONA = DotSkin(
        id = "dot_sun_corona",
        name = "Solar Corona",
        description = "Raging miniature sun bursting with nuclear flares and prominence loops.",
        price = 400,
        centerColor = Color(0xFFFFD600),
        glowColor = Color(0xFFFF6D00),
        accentColor = Color(0xFFFFF9C4),
        style = DotStyle.SUN_FLARE,
        glowRadiusDp = 28f
    )

    val ARCANE_RUNE = DotSkin(
        id = "dot_arcane_rune",
        name = "Arcane Sigil",
        description = "Mystical enchanted ward protecting ancient forbidden spell secrets.",
        price = 450,
        centerColor = Color(0xFFD500F9),
        glowColor = Color(0xFF651FFF),
        accentColor = Color(0xFFF3E5F5),
        style = DotStyle.MAGIC_RUNE,
        glowRadiusDp = 25f
    )

    val AEGIS_SHIELD = DotSkin(
        id = "dot_aegis_shield",
        name = "Aegis Barrier",
        description = "Reinforced energy deflector shield holding off kinetic strikes.",
        price = 500,
        centerColor = Color(0xFF2979FF),
        glowColor = Color(0xFF00B0FF),
        accentColor = Color(0xFFE3F2FD),
        style = DotStyle.SHIELD_AEGIS,
        glowRadiusDp = 24f
    )

    val VIPER_SKULL = DotSkin(
        id = "dot_viper_skull",
        name = "Jolly Roger Mark",
        description = "Intimidating crimson skull insignia marking the target for assassination.",
        price = 550,
        centerColor = Color(0xFFD50000),
        glowColor = Color(0xFF212121),
        accentColor = Color(0xFFFF8A80),
        style = DotStyle.SKULL_VIPER,
        glowRadiusDp = 24f
    )

    val YIN_YANG_DOT = DotSkin(
        id = "dot_yin_yang",
        name = "Cosmic Balance",
        description = "Eternal harmony of light and shadow spinning in endless equilibrium.",
        price = 600,
        centerColor = Color(0xFFFFFFFF),
        glowColor = Color(0xFF212121),
        accentColor = Color(0xFFECEFF1),
        style = DotStyle.YIN_YANG,
        glowRadiusDp = 25f
    )

    val SCI_FI_HEXAGON = DotSkin(
        id = "dot_sci_fi_hexagon",
        name = "Nano Hexagon",
        description = "Interlocking honeycomb nano-mesh shield grid glowing electric azure.",
        price = 700,
        centerColor = Color(0xFF00E5FF),
        glowColor = Color(0xFF0091EA),
        accentColor = Color(0xFFE0F7FA),
        style = DotStyle.NEO_HEXAGON,
        glowRadiusDp = 24f
    )

    val EMERALD_JEWEL = DotSkin(
        id = "dot_emerald_jewel",
        name = "Imperial Emerald",
        description = "Royal Colombian emerald gemstone glowing with deep verdant clarity.",
        price = 800,
        centerColor = Color(0xFF00C853),
        glowColor = Color(0xFF69F0AE),
        accentColor = Color(0xFFE8F5E9),
        style = DotStyle.EMERALD_GEM,
        glowRadiusDp = 23f
    )

    val SUPERNOVA_EXPLOSION = DotSkin(
        id = "dot_supernova_explosion",
        name = "Supernova Core",
        description = "Primordial cosmic blast wave vaporizing everything in its path.",
        price = 1000,
        centerColor = Color(0xFFFFD54F),
        glowColor = Color(0xFFFF3D00),
        accentColor = Color(0xFFFFFFFF),
        style = DotStyle.SUPERNOVA_BLAST,
        glowRadiusDp = 30f
    )

    val EYE_OF_HORUS = DotSkin(
        id = "dot_eye_horus",
        name = "Eye of Horus",
        description = "Ancient Egyptian sacred golden eye with cobalt blue mystical insight.",
        price = 1200,
        centerColor = Color(0xFFFFD700),
        glowColor = Color(0xFF00E5FF),
        accentColor = Color(0xFF0D47A1),
        style = DotStyle.ANCIENT_EYE,
        glowRadiusDp = 26f
    )

    val SNOWFLAKE_ICE = DotSkin(
        id = "dot_snowflake_ice",
        name = "Glacial Snowflake",
        description = "Hexagonal crystalline arctic ice star radiating sub-zero frost aura.",
        price = 1500,
        centerColor = Color(0xFFE0F7FA),
        glowColor = Color(0xFF00E5FF),
        accentColor = Color(0xFFFFFFFF),
        style = DotStyle.SNOWFLAKE_ICE,
        glowRadiusDp = 27f
    )

    val FIRE_COMET = DotSkin(
        id = "dot_fire_comet",
        name = "Meteor Core",
        description = "Superheated molten meteorite burning with bright orange plasma heat.",
        price = 1800,
        centerColor = Color(0xFFFF3D00),
        glowColor = Color(0xFFFF9100),
        accentColor = Color(0xFFFFEA00),
        style = DotStyle.FIRE_COMET,
        glowRadiusDp = 28f
    )

    val NINJA_SHURIKEN = DotSkin(
        id = "dot_ninja_shuriken",
        name = "Shadow Shuriken",
        description = "Aerodynamic four-point stainless steel throwing star with deadly edge.",
        price = 2200,
        centerColor = Color(0xFFCFD8DC),
        glowColor = Color(0xFF37474F),
        accentColor = Color(0xFFFFFFFF),
        style = DotStyle.SHURIKEN_STAR,
        glowRadiusDp = 24f
    )

    val COMPASS_ROSE = DotSkin(
        id = "dot_compass_rose",
        name = "Mariner Compass",
        description = "Ornate 8-point nautical brass compass guiding through rough waters.",
        price = 2500,
        centerColor = Color(0xFFFFD54F),
        glowColor = Color(0xFFFFB300),
        accentColor = Color(0xFFFF5252),
        style = DotStyle.COMPASS_ROSE,
        glowRadiusDp = 26f
    )

    val allSkins: List<DotSkin> = listOf(
        CLASSIC,
        SNIPER_CROSSHAIR,
        PULSE_RADAR,
        PLASMA_ORB,
        STELLAR_STAR,
        PRISM_DIAMOND,
        BIOHAZARD_CORE,
        CYBER_MATRIX,
        BLACK_HOLE_DOT,
        LOTUS_BLOOM,
        ARCADE_HEART,
        RADAR_SWEEP_DOT,
        SUN_CORONA,
        ARCANE_RUNE,
        AEGIS_SHIELD,
        VIPER_SKULL,
        YIN_YANG_DOT,
        SCI_FI_HEXAGON,
        EMERALD_JEWEL,
        SUPERNOVA_EXPLOSION,
        EYE_OF_HORUS,
        SNOWFLAKE_ICE,
        FIRE_COMET,
        NINJA_SHURIKEN,
        COMPASS_ROSE
    )

    fun getSkinById(id: String): DotSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}

