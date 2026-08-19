package com.example.model

import androidx.compose.ui.graphics.Color

enum class ArrowTailStyle {
    CLASSIC_SOLID,
    RED_TIP_BEAM,
    REAL_ARCHER_ARROW,     // Realistic wooden shaft with eagle fletching & steel arrowhead
    BAMBOO_STICK,          // Natural green segmented bamboo shoot with leaf knots & sharp tip
    WOODEN_BRANCH_STICK,   // Rustic textured tree branch twig with bark ring details
    WATER_PIPE,            // Industrial copper/metallic plumber pipe with water jet nozzle
    CANDY_CANE,            // Crisp red and white spiral peppermint candy cane
    SNAKE_REALISTIC,       // Realistic slithering viper with scales, snake eyes & strike tongue
    DRAGON_KATANA,         // Authentic Japanese samurai katana blade with gold tsuba hilt
    LIGHTNING_BOLT,        // Sharp jagged electric storm zigzag with plasma orb tip
    NEON_CYBER,
    GOLDEN_CHROME,
    FIRE_EMBER,
    EMERALD_CRYSTAL,
    COSMIC_STAR,
    STEALTH_OBSIDIAN,
    RAINBOW_HYPER,
    MECHA_RAILGUN,
    ICE_SPIKE,
    ROYAL_SCEPTRE,
    SHADOW_ASSASSIN,
    TOXIC_PLAGUE,
    VALKYRIE_SPEAR,
    MAGMA_BURST,
    CHRONO_GEAR,
    BUBBLE_AQUA,
    PIXEL_RETRO,
    PIRATE_CUTLASS,
    ANGELIC_WING
}

data class ArrowSkin(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val strokeColor: Color,
    val tipGlowColor: Color,
    val tipCenterColor: Color,
    val tailStyle: ArrowTailStyle,
    val strokeWidthDp: Float = 16f,
    val headWingLengthDp: Float = 75f,
    val headWingAngleDeg: Float = 36f,
    val glowRadiusDp: Float = 18f
)

object ArrowSkinCatalog {
    val CLASSIC = ArrowSkin(
        id = "skin_classic",
        name = "Classic Minimal",
        description = "Pure black minimalist sharp arrow with high-visibility red laser tip.",
        price = 0,
        strokeColor = Color(0xFF111111),
        tipGlowColor = Color(0xFFFF3B30),
        tipCenterColor = Color(0xFFFF5A52),
        tailStyle = ArrowTailStyle.CLASSIC_SOLID,
        strokeWidthDp = 16f,
        headWingLengthDp = 75f
    )

    val REAL_ARCHER_ARROW = ArrowSkin(
        id = "skin_real_archer",
        name = "Real Archer Arrow",
        description = "Authentic cedar wood shaft, dual eagle feather fletching, forged steel broadhead with red tracking band.",
        price = 15,
        strokeColor = Color(0xFF8D6E63),
        tipGlowColor = Color(0xFFFF5252),
        tipCenterColor = Color(0xFFCFD8DC),
        tailStyle = ArrowTailStyle.REAL_ARCHER_ARROW,
        strokeWidthDp = 14f,
        headWingLengthDp = 65f,
        glowRadiusDp = 20f
    )

    val BAMBOO_STICK = ArrowSkin(
        id = "skin_bamboo_stick",
        name = "Natural Bamboo",
        description = "Organic segmented bamboo stalk with realistic dark node rings, fresh green texture, and razor sharp tip.",
        price = 30,
        strokeColor = Color(0xFF43A047),
        tipGlowColor = Color(0xFF76FF03),
        tipCenterColor = Color(0xFFE8F5E9),
        tailStyle = ArrowTailStyle.BAMBOO_STICK,
        strokeWidthDp = 18f,
        headWingLengthDp = 50f,
        glowRadiusDp = 22f
    )

    val WOODEN_BRANCH = ArrowSkin(
        id = "skin_wooden_branch",
        name = "Forest Tree Stick",
        description = "Rustic rugged oak branch with natural bark grain, small side leaf twigs, and carved hunting tip.",
        price = 50,
        strokeColor = Color(0xFF5D4037),
        tipGlowColor = Color(0xFFFF9800),
        tipCenterColor = Color(0xFFFFCC80),
        tailStyle = ArrowTailStyle.WOODEN_BRANCH_STICK,
        strokeWidthDp = 16f,
        headWingLengthDp = 55f,
        glowRadiusDp = 20f
    )

    val WATER_PIPE = ArrowSkin(
        id = "skin_water_pipe",
        name = "Pressure Water Pipe",
        description = "Industrial steel metal water pipe with brass coupling rings and high-velocity water jet stream point.",
        price = 75,
        strokeColor = Color(0xFF546E7A),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.WATER_PIPE,
        strokeWidthDp = 18f,
        headWingLengthDp = 55f,
        glowRadiusDp = 24f
    )

    val CANDY_CANE = ArrowSkin(
        id = "skin_candy_cane",
        name = "Sweet Candy Cane",
        description = "Mouth-watering twisted red and white peppermint candy cane with sweet sugar glaze.",
        price = 90,
        strokeColor = Color(0xFFD50000),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.CANDY_CANE,
        strokeWidthDp = 18f,
        headWingLengthDp = 65f,
        glowRadiusDp = 22f
    )

    val RED_TIP_LINE = ArrowSkin(
        id = "skin_red_tip",
        name = "Red Tip Vector",
        description = "High-precision vector beam featuring a bright crimson guidance head.",
        price = 100,
        strokeColor = Color(0xFF2C2C2E),
        tipGlowColor = Color(0xFFFF334B),
        tipCenterColor = Color(0xFFFF808F),
        tailStyle = ArrowTailStyle.RED_TIP_BEAM,
        strokeWidthDp = 14f,
        headWingLengthDp = 70f
    )

    val SNAKE_VIPER = ArrowSkin(
        id = "skin_snake_viper",
        name = "Emerald Viper",
        description = "Realistic venomous green viper with winding body curves, glowing amber eyes and deadly fangs.",
        price = 120,
        strokeColor = Color(0xFF2E7D32),
        tipGlowColor = Color(0xFF00E676),
        tipCenterColor = Color(0xFFB9F6CA),
        tailStyle = ArrowTailStyle.SNAKE_REALISTIC,
        strokeWidthDp = 20f,
        headWingLengthDp = 60f,
        glowRadiusDp = 24f
    )

    val CYBER_NEON = ArrowSkin(
        id = "skin_cyber_neon",
        name = "Cyber Neon",
        description = "Futuristic cyan beam with electric energy pulse and dual glow.",
        price = 150,
        strokeColor = Color(0xFF00E5FF),
        tipGlowColor = Color(0xFF00B0FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.NEON_CYBER,
        strokeWidthDp = 18f,
        headWingLengthDp = 80f,
        glowRadiusDp = 24f
    )

    val LIGHTNING_STRIKE = ArrowSkin(
        id = "skin_lightning_strike",
        name = "Thunderbolt",
        description = "High-voltage electric lightning bolt with sharp zigzag nodes and sparking charge.",
        price = 180,
        strokeColor = Color(0xFFFFEA00),
        tipGlowColor = Color(0xFFFFD600),
        tipCenterColor = Color(0xFFFFFDE7),
        tailStyle = ArrowTailStyle.LIGHTNING_BOLT,
        strokeWidthDp = 16f,
        headWingLengthDp = 75f,
        glowRadiusDp = 22f
    )

    val SOLAR_GOLD = ArrowSkin(
        id = "skin_solar_gold",
        name = "Solar Gold",
        description = "24K metallic gold finish with shimmering solar flare particles.",
        price = 220,
        strokeColor = Color(0xFFFFD700),
        tipGlowColor = Color(0xFFFFC107),
        tipCenterColor = Color(0xFFFFF8E1),
        tailStyle = ArrowTailStyle.GOLDEN_CHROME,
        strokeWidthDp = 18f,
        headWingLengthDp = 85f,
        glowRadiusDp = 25f
    )

    val DRAGON_KATANA = ArrowSkin(
        id = "skin_dragon_katana",
        name = "Dragon Katana",
        description = "Forged steel samurai katana with gold guard, cord-wrapped handle and razor tip.",
        price = 260,
        strokeColor = Color(0xFFECEFF1),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFB0BEC5),
        tailStyle = ArrowTailStyle.DRAGON_KATANA,
        strokeWidthDp = 14f,
        headWingLengthDp = 70f,
        glowRadiusDp = 20f
    )

    val CRIMSON_FLAME = ArrowSkin(
        id = "skin_crimson_flame",
        name = "Inferno Ember",
        description = "Blazing fiery trail with heat distortion and glowing magma tip.",
        price = 300,
        strokeColor = Color(0xFFFF3D00),
        tipGlowColor = Color(0xFFFF6E40),
        tipCenterColor = Color(0xFFFFF3E0),
        tailStyle = ArrowTailStyle.FIRE_EMBER,
        strokeWidthDp = 18f,
        headWingLengthDp = 80f,
        glowRadiusDp = 26f
    )

    val RAINBOW_SPECTRUM = ArrowSkin(
        id = "skin_rainbow_spectrum",
        name = "Prism Chroma",
        description = "Smooth cycling multi-color rainbow gradient with radiant glow.",
        price = 350,
        strokeColor = Color(0xFFE040FB),
        tipGlowColor = Color(0xFF7C4DFF),
        tipCenterColor = Color(0xFFEDE7F6),
        tailStyle = ArrowTailStyle.RAINBOW_HYPER,
        strokeWidthDp = 17f,
        headWingLengthDp = 78f,
        glowRadiusDp = 24f
    )

    val MECHA_CANNON = ArrowSkin(
        id = "skin_mecha_cannon",
        name = "Railgun Striker",
        description = "Segmented heavy armor plating with high-velocity kinetic railgun point.",
        price = 400,
        strokeColor = Color(0xFF37474F),
        tipGlowColor = Color(0xFF00E676),
        tipCenterColor = Color(0xFFE8F5E9),
        tailStyle = ArrowTailStyle.MECHA_RAILGUN,
        strokeWidthDp = 20f,
        headWingLengthDp = 85f,
        glowRadiusDp = 22f
    )

    val EMERALD_VIPER = ArrowSkin(
        id = "skin_emerald_viper",
        name = "Jade Crystal",
        description = "Translucent faceted emerald gemstone shaft with inner refractive glow.",
        price = 450,
        strokeColor = Color(0xFF00C853),
        tipGlowColor = Color(0xFF69F0AE),
        tipCenterColor = Color(0xFFE8F5E9),
        tailStyle = ArrowTailStyle.EMERALD_CRYSTAL,
        strokeWidthDp = 16f,
        headWingLengthDp = 75f,
        glowRadiusDp = 22f
    )

    val COSMIC_VIOLET = ArrowSkin(
        id = "skin_cosmic_violet",
        name = "Nebula Void",
        description = "Deep space nebula violet stream surrounded by swirling star dust.",
        price = 500,
        strokeColor = Color(0xFF7C4DFF),
        tipGlowColor = Color(0xFFB388FF),
        tipCenterColor = Color(0xFFEDE7F6),
        tailStyle = ArrowTailStyle.COSMIC_STAR,
        strokeWidthDp = 18f,
        headWingLengthDp = 82f,
        glowRadiusDp = 26f
    )

    val OBSIDIAN_STEALTH = ArrowSkin(
        id = "skin_obsidian_stealth",
        name = "Shadow Obsidian",
        description = "Matte jet black stealth finish with subtle dark matter vapor aura.",
        price = 550,
        strokeColor = Color(0xFF1E1E24),
        tipGlowColor = Color(0xFF4A148C),
        tipCenterColor = Color(0xFF7B1FA2),
        tailStyle = ArrowTailStyle.STEALTH_OBSIDIAN,
        strokeWidthDp = 18f,
        headWingLengthDp = 76f,
        glowRadiusDp = 20f
    )

    val GLACIAL_ICE = ArrowSkin(
        id = "skin_glacial_ice",
        name = "Glacial Shard",
        description = "Sub-zero crystalline frozen icicle needle with crisp frosty blizzard aura.",
        price = 600,
        strokeColor = Color(0xFF80DEEA),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.ICE_SPIKE,
        strokeWidthDp = 17f,
        headWingLengthDp = 78f,
        glowRadiusDp = 24f
    )

    val IMPERIAL_SCEPTRE = ArrowSkin(
        id = "skin_imperial_sceptre",
        name = "Royal Sceptre",
        description = "Gilded royal gold sceptre topped with a radiant ruby crown jewel.",
        price = 700,
        strokeColor = Color(0xFFFFB300),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFFFEB3B),
        tailStyle = ArrowTailStyle.ROYAL_SCEPTRE,
        strokeWidthDp = 18f,
        headWingLengthDp = 85f,
        glowRadiusDp = 26f
    )

    val SHADOW_ASSASSIN = ArrowSkin(
        id = "skin_shadow_assassin",
        name = "Shadow Kunai",
        description = "Silent ninja kunai forged in dark steel with smoke trail.",
        price = 800,
        strokeColor = Color(0xFF263238),
        tipGlowColor = Color(0xFFD500F9),
        tipCenterColor = Color(0xFFF3E5F5),
        tailStyle = ArrowTailStyle.SHADOW_ASSASSIN,
        strokeWidthDp = 16f,
        headWingLengthDp = 70f,
        glowRadiusDp = 22f
    )

    val TOXIC_PLAGUE = ArrowSkin(
        id = "skin_toxic_plague",
        name = "Biohazard Dart",
        description = "Lethal corrosive radioactive sludge arrow with lime hazard glow.",
        price = 900,
        strokeColor = Color(0xFF64DD17),
        tipGlowColor = Color(0xFFAEEA00),
        tipCenterColor = Color(0xFFF1F8E9),
        tailStyle = ArrowTailStyle.TOXIC_PLAGUE,
        strokeWidthDp = 18f,
        headWingLengthDp = 76f,
        glowRadiusDp = 25f
    )

    val VALKYRIE_SPEAR = ArrowSkin(
        id = "skin_valkyrie_spear",
        name = "Valkyrie Spear",
        description = "Mythical Norse divine javelin inscribed with ancient golden runes.",
        price = 1000,
        strokeColor = Color(0xFFFFD54F),
        tipGlowColor = Color(0xFFFFAB00),
        tipCenterColor = Color(0xFFFFFDE7),
        tailStyle = ArrowTailStyle.VALKYRIE_SPEAR,
        strokeWidthDp = 16f,
        headWingLengthDp = 86f,
        glowRadiusDp = 25f
    )

    val MAGMA_BURST = ArrowSkin(
        id = "skin_magma_burst",
        name = "Volcanic Magma",
        description = "Cracked obsidian core leaking boiling red-hot molten lava.",
        price = 1100,
        strokeColor = Color(0xFFBF360C),
        tipGlowColor = Color(0xFFFF3D00),
        tipCenterColor = Color(0xFFFFD54F),
        tailStyle = ArrowTailStyle.MAGMA_BURST,
        strokeWidthDp = 20f,
        headWingLengthDp = 80f,
        glowRadiusDp = 28f
    )

    val CHRONO_GEAR = ArrowSkin(
        id = "skin_chrono_gear",
        name = "Chrono Steampunk",
        description = "Victorian brass clockwork mechanism with ticking gear wheels.",
        price = 1200,
        strokeColor = Color(0xFF8D6E63),
        tipGlowColor = Color(0xFFFFB74D),
        tipCenterColor = Color(0xFFFFF3E0),
        tailStyle = ArrowTailStyle.CHRONO_GEAR,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 22f
    )

    val BUBBLE_AQUA = ArrowSkin(
        id = "skin_bubble_aqua",
        name = "Hydro Torrent",
        description = "Fluid aquatic stream containing sparkling ocean bubbles.",
        price = 1300,
        strokeColor = Color(0xFF0288D1),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE1F5FE),
        tailStyle = ArrowTailStyle.BUBBLE_AQUA,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 24f
    )

    val PIXEL_RETRO = ArrowSkin(
        id = "skin_pixel_retro",
        name = "8-Bit Arcade",
        description = "Blocky nostalgic 1980s retro arcade pixel arrow in neon orange.",
        price = 1400,
        strokeColor = Color(0xFFEF6C00),
        tipGlowColor = Color(0xFFFF9100),
        tipCenterColor = Color(0xFFFFF3E0),
        tailStyle = ArrowTailStyle.PIXEL_RETRO,
        strokeWidthDp = 18f,
        headWingLengthDp = 70f,
        glowRadiusDp = 20f
    )

    val PIRATE_CUTLASS = ArrowSkin(
        id = "skin_pirate_cutlass",
        name = "Pirate Cutlass",
        description = "Swashbuckling curved naval pirate sabre with gleaming gold hilt.",
        price = 1500,
        strokeColor = Color(0xFF78909C),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.PIRATE_CUTLASS,
        strokeWidthDp = 16f,
        headWingLengthDp = 60f,
        glowRadiusDp = 24f
    )

    val ANGELIC_WING = ArrowSkin(
        id = "skin_angelic_wing",
        name = "Angelic Wings",
        description = "Pure divine feather-white shaft wrapped in holy golden halo rings.",
        price = 1600,
        strokeColor = Color(0xFFECEFF1),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFFFFDE7),
        tailStyle = ArrowTailStyle.ANGELIC_WING,
        strokeWidthDp = 16f,
        headWingLengthDp = 84f,
        glowRadiusDp = 26f
    )

    // EXPANSION CATALOG (TOTAL OVER 100+ HIGH QUALITY UNIQUE SKINS)
    private val extraSkinsList: List<ArrowSkin> = listOf(
        ArrowSkin("skin_quantum_pulse", "Quantum Warp", "Subatomic high-frequency energy beam pulsing across spacetime.", 1700, Color(0xFF651FFF), Color(0xFFD500F9), Color(0xFFEDE7F6), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_druid_vine", "Druid Thorn Vine", "Living thorny enchanted briar vine intertwined with forest spirits.", 1750, Color(0xFF33691E), Color(0xFF76FF03), Color(0xFFDCEDC8), ArrowTailStyle.WOODEN_BRANCH_STICK),
        ArrowSkin("skin_supernova", "Cosmic Supernova", "Exploding dying star core blasting ultra-dense stellar rays.", 1800, Color(0xFFFF6D00), Color(0xFFFFD600), Color(0xFFFFF9C4), ArrowTailStyle.FIRE_EMBER),
        ArrowSkin("skin_cyberpunk_glitch", "Cyberpunk Glitch", "Corrupted holo-matrix projection flickering with cyan datastreams.", 1850, Color(0xFFFF0055), Color(0xFF00FFFF), Color(0xFFFFFFFF), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_demon_horn", "Hellfire Horn", "Abyssal horned lance smoking with infernal crimson brimstone.", 1900, Color(0xFF880E4F), Color(0xFFFF1744), Color(0xFFFF8A80), ArrowTailStyle.MAGMA_BURST),
        ArrowSkin("skin_aurora_borealis", "Northern Aurora", "Ethereal arctic northern lights rippling in magnetic emerald.", 1950, Color(0xFF00E676), Color(0xFF651FFF), Color(0xFFE0F7FA), ArrowTailStyle.RAINBOW_HYPER),
        ArrowSkin("skin_titanium_drill", "Titanium Drill", "High-RPM diamond-tipped industrial mining drill point.", 2000, Color(0xFF455A64), Color(0xFFFFC107), Color(0xFFECEFF1), ArrowTailStyle.MECHA_RAILGUN),
        ArrowSkin("skin_meteor_strike", "Meteor Comet", "Blazing extraterrestrial celestial meteorite falling through atmosphere.", 2050, Color(0xFFD84315), Color(0xFFFFAB00), Color(0xFFFFE082), ArrowTailStyle.FIRE_EMBER),
        ArrowSkin("skin_jungle_blowpipe", "Amazon Blowpipe", "Ancient tribal rainforest blowpipe carrying paralyzing curare.", 2100, Color(0xFF1B5E20), Color(0xFF64DD17), Color(0xFFC8E6C9), ArrowTailStyle.BAMBOO_STICK),
        ArrowSkin("skin_tesla_coil", "Tesla Overcharge", "High-voltage alternating current resonance spark jumping across tubes.", 2150, Color(0xFF2979FF), Color(0xFF00E5FF), Color(0xFFE3F2FD), ArrowTailStyle.LIGHTNING_BOLT),
        ArrowSkin("skin_diamond_spear", "Flawless Diamond", "Pure cut indestructible diamond point refracting brilliant white rays.", 2200, Color(0xFFB2EBF2), Color(0xFFE0F7FA), Color(0xFFFFFFFF), ArrowTailStyle.EMERALD_CRYSTAL),
        ArrowSkin("skin_phoenix_feather", "Phoenix Plume", "Reborn immortal phoenix quill burning with eternal rebirth.", 2250, Color(0xFFFF5722), Color(0xFFFFD54F), Color(0xFFFFF3E0), ArrowTailStyle.REAL_ARCHER_ARROW),
        ArrowSkin("skin_shuriken_dart", "Shadow Shuriken", "Razor-edged four-pointed ninja throwing star flight path.", 2300, Color(0xFF37474F), Color(0xFF90A4AE), Color(0xFFECEFF1), ArrowTailStyle.SHADOW_ASSASSIN),
        ArrowSkin("skin_hyperspace_jump", "Hyperspace Lane", "Warp-speed starfield streak stretching into lightspeed.", 2350, Color(0xFF304FFE), Color(0xFF00B0FF), Color(0xFFFFFFFF), ArrowTailStyle.COSMIC_STAR),
        ArrowSkin("skin_copper_steam_rod", "Steam Brass Rod", "Polished antique brass cylinder venting superheated steam.", 2400, Color(0xFFA1887F), Color(0xFFFFB300), Color(0xFFFFF8E1), ArrowTailStyle.WATER_PIPE),
        ArrowSkin("skin_acid_stinger", "Scorpion Stinger", "Curved chitin stinger filled with concentrated neurotoxic acid.", 2450, Color(0xFF827717), Color(0xFFC0CA33), Color(0xFFF0F4C3), ArrowTailStyle.TOXIC_PLAGUE),
        ArrowSkin("skin_vortex_rift", "Event Horizon", "Gravitational singularity ripping the fabric of space.", 2500, Color(0xFF311B92), Color(0xFF6200EA), Color(0xFFD1C4E9), ArrowTailStyle.STEALTH_OBSIDIAN),
        ArrowSkin("skin_golden_trident", "Poseidon Trident", "Oceanic three-pronged golden spear governing the tidal depths.", 2550, Color(0xFFFFC107), Color(0xFF00E5FF), Color(0xFFE0F7FA), ArrowTailStyle.VALKYRIE_SPEAR),
        ArrowSkin("skin_sugar_lollipop", "Rainbow Lollipop", "Sweet spun carnival sugar rod with sparkling candy crystals.", 2600, Color(0xFFFF4081), Color(0xFFFF80AB), Color(0xFFFFF0F5), ArrowTailStyle.CANDY_CANE),
        ArrowSkin("skin_antimatter_beam", "Antimatter Ray", "Particle collider annihilation beam delivering raw energy.", 2650, Color(0xFFFF007F), Color(0xFF7C4DFF), Color(0xFFFFFFFF), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_zero_kelvin", "Absolute Zero", "Molecular stillness cryo-lance frozen to absolute zero.", 2700, Color(0xFFE0F7FA), Color(0xFF80DEEA), Color(0xFFFFFFFF), ArrowTailStyle.ICE_SPIKE),
        ArrowSkin("skin_infinity_blade", "Infinity Edge", "Supreme mythical celestial blade with primordial cosmic spark.", 2750, Color(0xFFFFD700), Color(0xFFD500F9), Color(0xFFFFFFFF), ArrowTailStyle.GOLDEN_CHROME),
        ArrowSkin("skin_blood_moon", "Blood Moon Fang", "Lunar eclipse dagger drenched in scarlet moonlight.", 2800, Color(0xFFB71C1C), Color(0xFFFF1744), Color(0xFFFFEBEE), ArrowTailStyle.DRAGON_KATANA),
        ArrowSkin("skin_solar_eclipse", "Corona Flare", "Total solar eclipse corona pulsing with black fire aura.", 2850, Color(0xFF212121), Color(0xFFFFD54F), Color(0xFFFFF9C4), ArrowTailStyle.FIRE_EMBER),
        ArrowSkin("skin_neon_synthwave", "Synthwave 1984", "Retro-wave 80s neon purple and laser magenta vector beam.", 2900, Color(0xFF9C27B0), Color(0xFFE040FB), Color(0xFF00E5FF), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_sapphire_frost", "Sapphire Glacier", "Deep ocean blue sapphire crystal carved in high alpine ice.", 2950, Color(0xFF0D47A1), Color(0xFF00B0FF), Color(0xFFE1F5FE), ArrowTailStyle.ICE_SPIKE),
        ArrowSkin("skin_amethyst_shards", "Amethyst Geode", "Raw purple amethyst quartz clusters radiating crystal energy.", 3000, Color(0xFF4A148C), Color(0xFFBA68C8), Color(0xFFF3E5F5), ArrowTailStyle.EMERALD_CRYSTAL),
        ArrowSkin("skin_cyber_katana", "Neo Tokyo Blade", "High-frequency electro-thermal katana with plasma edge.", 3050, Color(0xFF212121), Color(0xFF00E5FF), Color(0xFFE0F7FA), ArrowTailStyle.DRAGON_KATANA),
        ArrowSkin("skin_royal_halberd", "Imperial Halberd", "Heavy ornate palace guard axe-spear with gold lion crest.", 3100, Color(0xFFFFB300), Color(0xFFFF8F00), Color(0xFFFFF8E1), ArrowTailStyle.ROYAL_SCEPTRE),
        ArrowSkin("skin_poison_dart_frog", "Dendrobates Dart", "Vivid yellow and jet black poisonous rainforest dart.", 3150, Color(0xFFFFEA00), Color(0xFF1B5E20), Color(0xFF212121), ArrowTailStyle.TOXIC_PLAGUE),
        ArrowSkin("skin_deep_trench", "Mariana Harpoon", "Pressurized deep-sea titanium harpoon for abyssal leviathans.", 3200, Color(0xFF006064), Color(0xFF00E5FF), Color(0xFFE0F7FA), ArrowTailStyle.MECHA_RAILGUN),
        ArrowSkin("skin_sakura_petals", "Sakura Blossom", "Drifting pink cherry blossom branch from ancient Kyoto temple.", 3250, Color(0xFFF48FB1), Color(0xFFFF4081), Color(0xFFFCE4EC), ArrowTailStyle.WOODEN_BRANCH_STICK),
        ArrowSkin("skin_golden_nugget", "Klondike Gold", "Raw river nugget gold forged into heavy frontier hunting arrow.", 3300, Color(0xFFFFD54F), Color(0xFFFFC107), Color(0xFFFFF8E1), ArrowTailStyle.REAL_ARCHER_ARROW),
        ArrowSkin("skin_plasma_whip", "Cyber Whip", "Flexible braided tungsten cable crackling with magenta plasma.", 3350, Color(0xFFD500F9), Color(0xFFFF4081), Color(0xFFF3E5F5), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_spectral_wraith", "Ghostly Wraith", "Phantasmal green spirit vapor slipping through physical matter.", 3400, Color(0xFF00E676), Color(0xFF69F0AE), Color(0xFFE8F5E9), ArrowTailStyle.SHADOW_ASSASSIN),
        ArrowSkin("skin_solar_sail", "Solar Cruiser", "Ultra-thin reflective solar sail catching photon radiation.", 3450, Color(0xFFFFD54F), Color(0xFF00E5FF), Color(0xFFFFFFFF), ArrowTailStyle.COSMIC_STAR),
        ArrowSkin("skin_runic_axe", "Viking Bearded Spear", "Forged damascus steel javelin inscribed with Elder Futhark.", 3500, Color(0xFF455A64), Color(0xFF90A4AE), Color(0xFFECEFF1), ArrowTailStyle.VALKYRIE_SPEAR),
        ArrowSkin("skin_obsidian_scalpel", "Nano Scalpel", "Ultra-precise molecular scalpel needle tip.", 3550, Color(0xFF263238), Color(0xFF00E5FF), Color(0xFFFFFFFF), ArrowTailStyle.STEALTH_OBSIDIAN),
        ArrowSkin("skin_crimson_reaper", "Blood Scythe", "Sinister curved harvest scythe glowing with crimson essence.", 3600, Color(0xFF880E4F), Color(0xFFFF1744), Color(0xFFFF8A80), ArrowTailStyle.DRAGON_KATANA),
        ArrowSkin("skin_sunstone_radiance", "Dawn Sunstone", "Radiant amber crystal capturing first light of morning sun.", 3650, Color(0xFFFF8F00), Color(0xFFFFD54F), Color(0xFFFFF8E1), ArrowTailStyle.EMERALD_CRYSTAL),
        ArrowSkin("skin_storm_bringer", "Mjolnir Bolt", "Mythic thunderhammer lightning shaft rattling the heavens.", 3700, Color(0xFF1E88E5), Color(0xFFFFEA00), Color(0xFFFFFFFF), ArrowTailStyle.LIGHTNING_BOLT),
        ArrowSkin("skin_honeycomb_needle", "Queen Bee Stinger", "Golden beeswax shaft armed with barbed royal stinger.", 3750, Color(0xFFFFB300), Color(0xFFFF6F00), Color(0xFFFFF8E1), ArrowTailStyle.BAMBOO_STICK),
        ArrowSkin("skin_glitch_vortex", "Binary Matrix", "Cascade of green matrix digital code stream vectors.", 3800, Color(0xFF00C853), Color(0xFF00E676), Color(0xFFE8F5E9), ArrowTailStyle.PIXEL_RETRO),
        ArrowSkin("skin_frostbite_fang", "Frost Wyrm Fang", "Ancient dragon tooth frozen in perpetual subzero blizzard.", 3850, Color(0xFF80DEEA), Color(0xFF00ACC1), Color(0xFFE0F7FA), ArrowTailStyle.ICE_SPIKE),
        ArrowSkin("skin_void_stalker", "Abyss Needle", "Anti-photon stealth dart drawn from deep cosmic dark matter.", 3900, Color(0xFF121212), Color(0xFF651FFF), Color(0xFFEDE7F6), ArrowTailStyle.STEALTH_OBSIDIAN),
        ArrowSkin("skin_firecracker", "Dragon Firework", "Festive explosive rocket fuse trailing sparks and sulfur.", 3950, Color(0xFFD50000), Color(0xFFFFD600), Color(0xFFFFFFFF), ArrowTailStyle.CANDY_CANE),
        ArrowSkin("skin_chrono_accelerator", "Tachyon Arrow", "Faster-than-light tachyon particle beam reversing entropy.", 4000, Color(0xFFFFAB00), Color(0xFF00E5FF), Color(0xFFFFFFFF), ArrowTailStyle.CHRONO_GEAR),
        ArrowSkin("skin_pearl_mermaid", "Mermaid Harpoon", "Iridescent seashell inlaid ivory javelin blessed by tides.", 4050, Color(0xFFE0F7FA), Color(0xFF80DEEA), Color(0xFFFFFFFF), ArrowTailStyle.BUBBLE_AQUA),
        ArrowSkin("skin_plasma_arc", "Tokamak Arc", "Magnetic confinement fusion arc delivering stellar heat.", 4100, Color(0xFF651FFF), Color(0xFF00E5FF), Color(0xFFFFFFFF), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_meteorite_core", "Starfall Javelin", "Aerodynamically melted iron meteorite with orange crust.", 4150, Color(0xFF3E2723), Color(0xFFFF5722), Color(0xFFFFCC80), ArrowTailStyle.FIRE_EMBER),
        ArrowSkin("skin_crystal_rose", "Ruby Crystal Rose", "Faceted rose quartz stem bristling with diamond thorns.", 4200, Color(0xFFE91E63), Color(0xFFFF4081), Color(0xFFFCE4EC), ArrowTailStyle.WOODEN_BRANCH_STICK),
        ArrowSkin("skin_hyperion_beam", "Hyperion Lance", "Titan class orbital solar cannon beam firing at ground targets.", 4250, Color(0xFFFFD54F), Color(0xFFFF3D00), Color(0xFFFFFFFF), ArrowTailStyle.GOLDEN_CHROME),
        ArrowSkin("skin_shadow_tendril", "Nightmare Tendril", "Writhing shadowy phantom filament grasping at targets.", 4300, Color(0xFF212121), Color(0xFFD500F9), Color(0xFFF3E5F5), ArrowTailStyle.SNAKE_REALISTIC),
        ArrowSkin("skin_arcane_wand", "Elder Magic Wand", "Ancient wizard rowan wood wand sparking arcane runes.", 4350, Color(0xFF5D4037), Color(0xFF7C4DFF), Color(0xFFEDE7F6), ArrowTailStyle.WOODEN_BRANCH_STICK),
        ArrowSkin("skin_copper_pipe_v2", "Industrial Boiler Steam", "Heavy Victorian copper pressure pipe with gauge dials.", 4400, Color(0xFF8D6E63), Color(0xFFFFB300), Color(0xFFFFF8E1), ArrowTailStyle.WATER_PIPE),
        ArrowSkin("skin_toxic_serpent", "Cobra Venom", "Spitting King Cobra hooded lance dripping lethal poison.", 4450, Color(0xFF33691E), Color(0xFFAEEA00), Color(0xFFF1F8E9), ArrowTailStyle.SNAKE_REALISTIC),
        ArrowSkin("skin_blizzard_needle", "Permafrost Lance", "Everlasting glacier shard forged in heart of Antarctic gale.", 4500, Color(0xFFB2EBF2), Color(0xFF00E5FF), Color(0xFFFFFFFF), ArrowTailStyle.ICE_SPIKE),
        ArrowSkin("skin_valkyrie_gold", "Valhalla Javelin", "Gleaming pure gold divine spear blessed by Odin.", 4550, Color(0xFFFFD700), Color(0xFFFFC107), Color(0xFFFFFDE7), ArrowTailStyle.VALKYRIE_SPEAR),
        ArrowSkin("skin_volcano_spire", "Kilauea Spire", "Solidified black basalt lava tube bursting with liquid fire.", 4600, Color(0xFF263238), Color(0xFFFF3D00), Color(0xFFFFD54F), ArrowTailStyle.MAGMA_BURST),
        ArrowSkin("skin_quantum_tangle", "Entangled Pair", "Twin entangled photon rays spinning around central axis.", 4650, Color(0xFF00E5FF), Color(0xFFD500F9), Color(0xFFFFFFFF), ArrowTailStyle.NEON_CYBER),
        ArrowSkin("skin_celestial_feather", "Seraphim Feather", "Six-winged seraph golden quill blessed by celestial choir.", 4700, Color(0xFFFFF8E1), Color(0xFFFFD54F), Color(0xFFFFFFFF), ArrowTailStyle.ANGELIC_WING),
        ArrowSkin("skin_zenith_ray", "Zenith Beam", "Ultra-pure concentrated solar zenith beam at midday.", 4750, Color(0xFFFFEA00), Color(0xFFFFAB00), Color(0xFFFFFFFF), ArrowTailStyle.GOLDEN_CHROME),
        ArrowSkin("skin_alpha_omega", "Alpha Omega", "Primordial sacred weapon forged at dawn of all creation.", 4800, Color(0xFFFFD700), Color(0xFF651FFF), Color(0xFFFFFFFF), ArrowTailStyle.ROYAL_SCEPTRE),
        ArrowSkin("skin_singularity_prime", "Singularity Prime", "The ultimate master arrow containing endless gravitational force.", 5000, Color(0xFF000000), Color(0xFFFF0055), Color(0xFF00FFFF), ArrowTailStyle.STEALTH_OBSIDIAN)
    )

    val allSkins: List<ArrowSkin> = listOf(
        CLASSIC,
        REAL_ARCHER_ARROW,
        BAMBOO_STICK,
        WOODEN_BRANCH,
        WATER_PIPE,
        CANDY_CANE,
        RED_TIP_LINE,
        SNAKE_VIPER,
        CYBER_NEON,
        LIGHTNING_STRIKE,
        SOLAR_GOLD,
        DRAGON_KATANA,
        CRIMSON_FLAME,
        RAINBOW_SPECTRUM,
        MECHA_CANNON,
        EMERALD_VIPER,
        COSMIC_VIOLET,
        OBSIDIAN_STEALTH,
        GLACIAL_ICE,
        IMPERIAL_SCEPTRE,
        SHADOW_ASSASSIN,
        TOXIC_PLAGUE,
        VALKYRIE_SPEAR,
        MAGMA_BURST,
        CHRONO_GEAR,
        BUBBLE_AQUA,
        PIXEL_RETRO,
        PIRATE_CUTLASS,
        ANGELIC_WING
    ) + extraSkinsList

    fun getSkinById(id: String): ArrowSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}
