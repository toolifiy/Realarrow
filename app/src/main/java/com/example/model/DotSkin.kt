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
    SUPERNOVA_BLAST   // Primordial stellar explosion center
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

    // EXPANSION CATALOG (TOTAL OVER 100+ UNIQUE DOT SKINS)
    private val extraDotsList: List<DotSkin> = listOf(
        DotSkin("dot_quasar_beacon", "Quasar Beacon", "Extragalactic beacon flashing million-lightyear pulses.", 1100, Color(0xFF651FFF), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB),
        DotSkin("dot_atomic_nucleus", "Atomic Nucleus", "Protons and neutrons bound together with strong nuclear force.", 1150, Color(0xFFFF1744), Color(0xFF2979FF), Color(0xFFFFF9C4), DotStyle.PULSE_RINGS),
        DotSkin("dot_compass_rose", "Mariner Compass", "Antique brass nautical compass orienting due north.", 1200, Color(0xFFFFD54F), Color(0xFF8D6E63), Color(0xFFFFF8E1), DotStyle.CROSSHAIR),
        DotSkin("dot_crystal_amethyst", "Royal Amethyst", "Faceted royal purple crystal stone sparking with psychic energy.", 1250, Color(0xFFBA68C8), Color(0xFF7B1FA2), Color(0xFFF3E5F5), DotStyle.DIAMOND_CRYSTAL),
        DotSkin("dot_aurora_core", "Aurora Borealis Dot", "Shifting green and violet atmospheric magnetic flare.", 1300, Color(0xFF00E676), Color(0xFF7C4DFF), Color(0xFFE0F7FA), DotStyle.ENERGY_ORB),
        DotSkin("dot_dragon_eye", "Dragon Eye", "Slit amber reptilian eye watching intently with fiery malice.", 1350, Color(0xFFFF8F00), Color(0xFFFF3D00), Color(0xFF212121), DotStyle.CLASSIC_TARGET),
        DotSkin("dot_frost_snowflake", "Glacial Snowflake", "Intricate crystalline water ice geometry frozen at zero degrees.", 1400, Color(0xFF80DEEA), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.STAR_CORE),
        DotSkin("dot_volcano_crater", "Magma Crater", "Bubbling lava caldron bursting with glowing volcanic embers.", 1450, Color(0xFFFF3D00), Color(0xFFBF360C), Color(0xFFFFD54F), DotStyle.SUN_FLARE),
        DotSkin("dot_golden_coin", "Doubloon Target", "Minted Spanish pirate gold doubloon stamped with skull seal.", 1500, Color(0xFFFFD700), Color(0xFFFFAB00), Color(0xFFFFFDE7), DotStyle.CLASSIC_TARGET),
        DotSkin("dot_toxic_spore", "Fungal Spore", "Bioluminescent mushroom cap discharging glowing spore clouds.", 1550, Color(0xFFAEEA00), Color(0xFF64DD17), Color(0xFFF1F8E9), DotStyle.BIOHAZARD),
        DotSkin("dot_cyber_reticle", "HUD Vector", "Jet fighter head-up display lock-on target box.", 1600, Color(0xFF00E5FF), Color(0xFF0091EA), Color(0xFFFFFFFF), DotStyle.CROSSHAIR),
        DotSkin("dot_pearl_shell", "Ocean Pearl", "Smooth iridescent clam pearl nestled in sea foam.", 1650, Color(0xFFECEFF1), Color(0xFF80DEEA), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB),
        DotSkin("dot_black_sun", "Eclipse Totality", "Black solar disk ringed with blinding white corona light.", 1700, Color(0xFF212121), Color(0xFFFFD54F), Color(0xFFFFFFFF), DotStyle.BLACK_HOLE),
        DotSkin("dot_magic_seal", "Solomon Seal", "Ancient hexagram seal binding elemental spirits to command.", 1750, Color(0xFF7C4DFF), Color(0xFF651FFF), Color(0xFFEDE7F6), DotStyle.MAGIC_RUNE),
        DotSkin("dot_sakura_drop", "Sakura Petal", "Floating spring cherry blossom petal carrying dewdrops.", 1800, Color(0xFFFF4081), Color(0xFFF8BBD0), Color(0xFFFFF0F5), DotStyle.LOTUS_ZEN),
        DotSkin("dot_neon_wheel", "Synthwave Hub", "Neon pink and cyan spinning vector speed ring.", 1850, Color(0xFFFF007F), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.PULSE_RINGS),
        DotSkin("dot_honey_hex", "Hive Nectar", "Pure honeycomb cell dripping with liquid golden nectar.", 1900, Color(0xFFFFB300), Color(0xFFFF6F00), Color(0xFFFFF8E1), DotStyle.NEO_HEXAGON),
        DotSkin("dot_blood_drop", "Crimson Elixir", "Gleaming drop of vital vampire blood with ruby sheen.", 1950, Color(0xFFB71C1C), Color(0xFFFF1744), Color(0xFFFFCDD2), DotStyle.CLASSIC_TARGET),
        DotSkin("dot_nano_core", "Quantum CPU", "Microscopic quantum processing qubit in sub-kelvin vacuum.", 2000, Color(0xFF00E5FF), Color(0xFF2979FF), Color(0xFFFFD54F), DotStyle.CYBER_CHIP),
        DotSkin("dot_angel_halo", "Seraph Halo", "Floating golden holy ring of angelic blessing.", 2050, Color(0xFFFFD54F), Color(0xFFFFC107), Color(0xFFFFFFFF), DotStyle.PULSE_RINGS),
        DotSkin("dot_ruby_heart", "Garnet Heart", "Cut precious ruby gemstone sculpted into heartbeat shape.", 2100, Color(0xFFFF1744), Color(0xFFD50000), Color(0xFFFF8A80), DotStyle.HEART_PULSE),
        DotSkin("dot_time_dial", "Chrono Dial", "Clockwork balance wheel oscillating with precise seconds.", 2150, Color(0xFF8D6E63), Color(0xFFFFB74D), Color(0xFFFFF3E0), DotStyle.CROSSHAIR),
        DotSkin("dot_plasma_spark", "Fusion Spark", "Controlled deuterium-tritium magnetic plasma ball.", 2200, Color(0xFFD500F9), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB),
        DotSkin("dot_emerald_eye", "Viper Sight", "Slit pupil emerald serpent eye glinting in shadows.", 2250, Color(0xFF00C853), Color(0xFF69F0AE), Color(0xFF212121), DotStyle.EMERALD_GEM),
        DotSkin("dot_subzero_shard", "Cryo Core", "Solidified liquid nitrogen ice droplet encased in glass.", 2300, Color(0xFFB2EBF2), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.DIAMOND_CRYSTAL),
        DotSkin("dot_pirate_compass", "Skull Marker", "Pirate treasure X marker flanked by twin flintlocks.", 2350, Color(0xFFD50000), Color(0xFF455A64), Color(0xFFFFD54F), DotStyle.SKULL_VIPER),
        DotSkin("dot_yin_water_fire", "Elemental Balance", "Swirling droplet of water opposing dancing tongue of fire.", 2400, Color(0xFF00E5FF), Color(0xFFFF3D00), Color(0xFFFFFFFF), DotStyle.YIN_YANG),
        DotSkin("dot_matrix_node", "Cipher Gate", "Green phosphorescent cyber data hub receiving streams.", 2450, Color(0xFF00E676), Color(0xFF1B5E20), Color(0xFFE8F5E9), DotStyle.CYBER_CHIP),
        DotSkin("dot_star_sapphire", "Star Sapphire", "Rare blue sapphire revealing internal six-rayed asterism.", 2500, Color(0xFF0D47A1), Color(0xFF00B0FF), Color(0xFFFFFFFF), DotStyle.STAR_CORE),
        DotSkin("dot_super_nova_gold", "Stellar Genesis", "The birth of new worlds following cosmic supernova collapse.", 2550, Color(0xFFFFD700), Color(0xFFFF6D00), Color(0xFFFFFFFF), DotStyle.SUPERNOVA_BLAST),
        DotSkin("dot_shield_titan", "Titan Wall", "Unbreakable composite buckler with blue power grid.", 2600, Color(0xFF0091EA), Color(0xFF00E5FF), Color(0xFFECEFF1), DotStyle.SHIELD_AEGIS),
        DotSkin("dot_radioactive_atom", "Uranium Core", "Enriched uranium crystal surrounded by alpha radiation tracks.", 2650, Color(0xFF76FF03), Color(0xFFAEEA00), Color(0xFF212121), DotStyle.BIOHAZARD),
        DotSkin("dot_deep_sonar", "Submarine Ping", "Echo-location sonar returning blips from seabed trenches.", 2700, Color(0xFF00E5FF), Color(0xFF006064), Color(0xFFE0F7FA), DotStyle.RADAR_SWEEP),
        DotSkin("dot_lotus_gold", "Golden Padma", "Spiritual golden thousand-petal lotus from celestial realms.", 2750, Color(0xFFFFD54F), Color(0xFFFFB300), Color(0xFFFFF8E1), DotStyle.LOTUS_ZEN),
        DotSkin("dot_dark_portal", "Void Gate", "Swirling interdimensional rift dragging nearby matter.", 2800, Color(0xFF311B92), Color(0xFF6200EA), Color(0xFFEDE7F6), DotStyle.BLACK_HOLE),
        DotSkin("dot_solar_flare_red", "Red Giant Flare", "Dying red supergiant star pulsing with massive thermal arcs.", 2850, Color(0xFFFF1744), Color(0xFFFF5252), Color(0xFFFFEBEE), DotStyle.SUN_FLARE),
        DotSkin("dot_runic_circle", "Elder Ward", "Inscribed runic ring pulsating with Norse protection wards.", 2900, Color(0xFF00E5FF), Color(0xFF651FFF), Color(0xFFFFFFFF), DotStyle.MAGIC_RUNE),
        DotSkin("dot_diamond_star", "Crown Diamond", "Flawless brilliant cut diamond radiating blinding brilliance.", 2950, Color(0xFFE0F7FA), Color(0xFF80DEEA), Color(0xFFFFFFFF), DotStyle.DIAMOND_CRYSTAL),
        DotSkin("dot_sniper_infra", "Thermal Scope", "Infrared heat signature vision with crosshair tracking.", 3000, Color(0xFFFF3D00), Color(0xFFFF9100), Color(0xFFFFF3E0), DotStyle.CROSSHAIR),
        DotSkin("dot_energy_vortex", "Tachyon Well", "High-spin gravity well accelerating subatomic particles.", 3050, Color(0xFF651FFF), Color(0xFFD500F9), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB),
        DotSkin("dot_bubble_drop", "Aqua Pearl", "Floating pristine water droplet carrying rainbow reflections.", 3100, Color(0xFF00E5FF), Color(0xFF0288D1), Color(0xFFFFFFFF), DotStyle.PULSE_RINGS),
        DotSkin("dot_cyber_shield", "Matrix Firewall", "Military-grade encrypted cyber barrier repelling intrusions.", 3150, Color(0xFF00E5FF), Color(0xFF00C853), Color(0xFFFFFFFF), DotStyle.SHIELD_AEGIS),
        DotSkin("dot_crimson_sigil", "Demon Mark", "Blood-inked pact brand glowing with infernal power.", 3200, Color(0xFFB71C1C), Color(0xFFFF1744), Color(0xFFFF8A80), DotStyle.MAGIC_RUNE),
        DotSkin("dot_golden_compass", "Sun Dial", "Golden celestial astrolabe calculating star movements.", 3250, Color(0xFFFFD54F), Color(0xFFFF9800), Color(0xFFFFFDE7), DotStyle.CROSSHAIR),
        DotSkin("dot_toxic_barrel", "Waste Cache", "Sealed steel drum leaking effervescent green slime.", 3300, Color(0xFF64DD17), Color(0xFF76FF03), Color(0xFF212121), DotStyle.BIOHAZARD),
        DotSkin("dot_frost_eye", "Yeti Eye", "Sub-zero ice creature eye glinting in polar blizzards.", 3350, Color(0xFF80DEEA), Color(0xFF00ACC1), Color(0xFFFFFFFF), DotStyle.CLASSIC_TARGET),
        DotSkin("dot_phoenix_spark", "Phoenix Heart", "Warm unextinguishable flame burning from ancient ash.", 3400, Color(0xFFFF5722), Color(0xFFFFD54F), Color(0xFFFFF3E0), DotStyle.HEART_PULSE),
        DotSkin("dot_sonar_deep", "Abyssal Sonar", "Ultra-low frequency sonar probing oceanic trenches.", 3450, Color(0xFF00B0FF), Color(0xFF00E5FF), Color(0xFFE0F7FA), DotStyle.RADAR_SWEEP),
        DotSkin("dot_zen_stone", "Cairn Balance", "Smooth river stones stacked in meditative perfection.", 3500, Color(0xFF78909C), Color(0xFFB0BEC5), Color(0xFFECEFF1), DotStyle.LOTUS_ZEN),
        DotSkin("dot_black_void", "Absolute Dark", "Pocket of absolute zero radiation absorbing all incoming light.", 3550, Color(0xFF111111), Color(0xFF4A148C), Color(0xFFEDE7F6), DotStyle.BLACK_HOLE),
        DotSkin("dot_plasma_burst", "Tokamak Core", "Magnetic ring containing multi-million degree helium plasma.", 3600, Color(0xFF00E5FF), Color(0xFFD500F9), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB),
        DotSkin("dot_emerald_rune", "Druid Sigil", "Forest god seal invoking ancient wood vitality.", 3650, Color(0xFF00C853), Color(0xFF76FF03), Color(0xFFE8F5E9), DotStyle.MAGIC_RUNE),
        DotSkin("dot_diamond_core", "Hyper Diamond", "Synthetic carbon crystal harder than natural diamond.", 3700, Color(0xFFE0F7FA), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.DIAMOND_CRYSTAL),
        DotSkin("dot_sun_blaze", "Helios Core", "Greek sun chariot burning with pure golden majesty.", 3750, Color(0xFFFFD600), Color(0xFFFF6D00), Color(0xFFFFFDE7), DotStyle.SUN_FLARE),
        DotSkin("dot_pirate_cross", "Crossbones Tag", "Silver crossed cutlasses behind ominous death mask.", 3800, Color(0xFFECEFF1), Color(0xFF37474F), Color(0xFFFF1744), DotStyle.SKULL_VIPER),
        DotSkin("dot_neon_crosshair", "Cyber Sight", "Ultraviolet targeting reticle highlighting weak points.", 3850, Color(0xFFD500F9), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.CROSSHAIR),
        DotSkin("dot_aegis_prime", "Valhalla Shield", "Divine celestial shield adorned with radiant gold studs.", 3900, Color(0xFFFFD700), Color(0xFFFFC107), Color(0xFFFFFFFF), DotStyle.SHIELD_AEGIS),
        DotSkin("dot_quantum_hex", "Hex Qubit", "Six-sided quantum crystal storing multi-state superposition.", 3950, Color(0xFF00E5FF), Color(0xFF651FFF), Color(0xFFFFFFFF), DotStyle.NEO_HEXAGON),
        DotSkin("dot_super_nova_v2", "Big Bang Spark", "The initial cosmic singularity of infinite density and light.", 4000, Color(0xFFFFFFFF), Color(0xFFFF0055), Color(0xFF00FFFF), DotStyle.SUPERNOVA_BLAST),
        DotSkin("dot_infinite_apex", "Infinite Apex", "The master target dot representing ultimate precision mastery.", 5000, Color(0xFFFFD700), Color(0xFF00E5FF), Color(0xFFFFFFFF), DotStyle.ENERGY_ORB)
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
        SUPERNOVA_EXPLOSION
    ) + extraDotsList

    fun getSkinById(id: String): DotSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}
