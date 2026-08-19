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

    // ADDITIONAL SKINS TO SURPASS 50+
    val QUANTUM_PULSE = ArrowSkin(
        id = "skin_quantum_pulse",
        name = "Quantum Warp",
        description = "Subatomic high-frequency energy beam pulsing across spacetime dimensions.",
        price = 1700,
        strokeColor = Color(0xFF651FFF),
        tipGlowColor = Color(0xFFD500F9),
        tipCenterColor = Color(0xFFEDE7F6),
        tailStyle = ArrowTailStyle.NEON_CYBER,
        strokeWidthDp = 17f
    )

    val DRUID_VINE = ArrowSkin(
        id = "skin_druid_vine",
        name = "Druid Thorn Vine",
        description = "Living thorny enchanted briar vine intertwined with forest spirits.",
        price = 1800,
        strokeColor = Color(0xFF33691E),
        tipGlowColor = Color(0xFF76FF03),
        tipCenterColor = Color(0xFFDCEDC8),
        tailStyle = ArrowTailStyle.WOODEN_BRANCH_STICK,
        strokeWidthDp = 18f
    )

    val SUPERNOVA = ArrowSkin(
        id = "skin_supernova",
        name = "Cosmic Supernova",
        description = "Exploding dying star core blasting ultra-dense stellar thermonuclear rays.",
        price = 1900,
        strokeColor = Color(0xFFFF6D00),
        tipGlowColor = Color(0xFFFFD600),
        tipCenterColor = Color(0xFFFFF9C4),
        tailStyle = ArrowTailStyle.FIRE_EMBER,
        strokeWidthDp = 19f
    )

    val CYBERPUNK_GLITCH = ArrowSkin(
        id = "skin_cyberpunk_glitch",
        name = "Cyberpunk Glitch",
        description = "Corrupted holo-matrix projection flickering with magenta and cyan datastreams.",
        price = 2000,
        strokeColor = Color(0xFFFF0055),
        tipGlowColor = Color(0xFF00FFFF),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.NEON_CYBER,
        strokeWidthDp = 17f
    )

    val DEMON_HORN = ArrowSkin(
        id = "skin_demon_horn",
        name = "Hellfire Horn",
        description = "Abyssal horned lance smoking with infernal crimson brimstone flames.",
        price = 2100,
        strokeColor = Color(0xFF880E4F),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFFF8A80),
        tailStyle = ArrowTailStyle.MAGMA_BURST,
        strokeWidthDp = 18f
    )

    val AURORA_BOREALIS = ArrowSkin(
        id = "skin_aurora_borealis",
        name = "Northern Aurora",
        description = "Ethereal arctic northern lights rippling in magnetic emerald and violet.",
        price = 2200,
        strokeColor = Color(0xFF00E676),
        tipGlowColor = Color(0xFF651FFF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.RAINBOW_HYPER,
        strokeWidthDp = 16f
    )

    val TITANIUM_DRILL = ArrowSkin(
        id = "skin_titanium_drill",
        name = "Titanium Drill",
        description = "High-RPM diamond-tipped industrial mining drill point.",
        price = 2300,
        strokeColor = Color(0xFF455A64),
        tipGlowColor = Color(0xFFFFC107),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.MECHA_RAILGUN,
        strokeWidthDp = 20f
    )

    val METEOR_STRIKE = ArrowSkin(
        id = "skin_meteor_strike",
        name = "Meteor Comet",
        description = "Blazing extraterrestrial celestial meteorite falling through the atmosphere.",
        price = 2400,
        strokeColor = Color(0xFFD84315),
        tipGlowColor = Color(0xFFFFAB00),
        tipCenterColor = Color(0xFFFFE082),
        tailStyle = ArrowTailStyle.FIRE_EMBER,
        strokeWidthDp = 18f
    )

    val JUNGLE_BLOWPIPE = ArrowSkin(
        id = "skin_jungle_blowpipe",
        name = "Amazon Blowpipe",
        description = "Ancient tribal rainforest blowpipe carrying paralyzing curare venom.",
        price = 2500,
        strokeColor = Color(0xFF1B5E20),
        tipGlowColor = Color(0xFF64DD17),
        tipCenterColor = Color(0xFFC8E6C9),
        tailStyle = ArrowTailStyle.BAMBOO_STICK,
        strokeWidthDp = 17f
    )

    val TESLA_COIL = ArrowSkin(
        id = "skin_tesla_coil",
        name = "Tesla Overcharge",
        description = "High-voltage alternating current resonance spark jumping across vacuum tubes.",
        price = 2600,
        strokeColor = Color(0xFF2979FF),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE3F2FD),
        tailStyle = ArrowTailStyle.LIGHTNING_BOLT,
        strokeWidthDp = 16f
    )

    val DIAMOND_SPEAR = ArrowSkin(
        id = "skin_diamond_spear",
        name = "Flawless Diamond",
        description = "Pure cut indestructible diamond point refracting brilliant pristine white rays.",
        price = 2700,
        strokeColor = Color(0xFFB2EBF2),
        tipGlowColor = Color(0xFFE0F7FA),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.EMERALD_CRYSTAL,
        strokeWidthDp = 17f
    )

    val PHOENIX_FEATHER = ArrowSkin(
        id = "skin_phoenix_feather",
        name = "Phoenix Plume",
        description = "Reborn immortal phoenix quill burning with eternal fiery rebirth.",
        price = 2800,
        strokeColor = Color(0xFFFF5722),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFFFF3E0),
        tailStyle = ArrowTailStyle.REAL_ARCHER_ARROW,
        strokeWidthDp = 15f
    )

    val SHURIKEN_DART = ArrowSkin(
        id = "skin_shuriken_dart",
        name = "Shadow Shuriken",
        description = "Razor-edged four-pointed throwing star flight path.",
        price = 2900,
        strokeColor = Color(0xFF37474F),
        tipGlowColor = Color(0xFF90A4AE),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.SHADOW_ASSASSIN,
        strokeWidthDp = 16f
    )

    val HYPERSPACE_JUMP = ArrowSkin(
        id = "skin_hyperspace_jump",
        name = "Hyperspace Lane",
        description = "Warp-speed starfield streak stretching into infinite lightspeed.",
        price = 3000,
        strokeColor = Color(0xFF304FFE),
        tipGlowColor = Color(0xFF00B0FF),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.COSMIC_STAR,
        strokeWidthDp = 18f
    )

    val COPPER_STEAM_ROD = ArrowSkin(
        id = "skin_copper_steam_rod",
        name = "Steam Brass Rod",
        description = "Polished antique brass cylinder venting superheated pressurized steam.",
        price = 3100,
        strokeColor = Color(0xFFA1887F),
        tipGlowColor = Color(0xFFFFB300),
        tipCenterColor = Color(0xFFFFF8E1),
        tailStyle = ArrowTailStyle.WATER_PIPE,
        strokeWidthDp = 19f
    )

    val ACID_STINGER = ArrowSkin(
        id = "skin_acid_stinger",
        name = "Scorpion Stinger",
        description = "Curved chitin stinger filled with concentrated neurotoxic amber acid.",
        price = 3200,
        strokeColor = Color(0xFF827717),
        tipGlowColor = Color(0xFFC0CA33),
        tipCenterColor = Color(0xFFF0F4C3),
        tailStyle = ArrowTailStyle.TOXIC_PLAGUE,
        strokeWidthDp = 17f
    )

    val VORTEX_RIFT = ArrowSkin(
        id = "skin_vortex_rift",
        name = "Event Horizon",
        description = "Gravitational singularity singularity ripping the fabric of space.",
        price = 3300,
        strokeColor = Color(0xFF311B92),
        tipGlowColor = Color(0xFF6200EA),
        tipCenterColor = Color(0xFFD1C4E9),
        tailStyle = ArrowTailStyle.STEALTH_OBSIDIAN,
        strokeWidthDp = 18f
    )

    val GOLDEN_TRIDENT = ArrowSkin(
        id = "skin_golden_trident",
        name = "Poseidon Trident",
        description = "Oceanic three-pronged golden spear governing the tidal depths.",
        price = 3400,
        strokeColor = Color(0xFFFFC107),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.VALKYRIE_SPEAR,
        strokeWidthDp = 17f
    )

    val SUGAR_LOLLIPOP = ArrowSkin(
        id = "skin_sugar_lollipop",
        name = "Rainbow Lollipop",
        description = "Sweet spun carnival sugar rod with sparkling candy crystals.",
        price = 3500,
        strokeColor = Color(0xFFFF4081),
        tipGlowColor = Color(0xFFFF80AB),
        tipCenterColor = Color(0xFFFFF0F5),
        tailStyle = ArrowTailStyle.CANDY_CANE,
        strokeWidthDp = 18f
    )

    val ANTIMATTER_BEAM = ArrowSkin(
        id = "skin_antimatter_beam",
        name = "Antimatter Ray",
        description = "Particle collider annihilation beam delivering raw particle fusion energy.",
        price = 3600,
        strokeColor = Color(0xFFFF007F),
        tipGlowColor = Color(0xFF7C4DFF),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.NEON_CYBER,
        strokeWidthDp = 17f
    )

    val ZERO_KELVIN = ArrowSkin(
        id = "skin_zero_kelvin",
        name = "Absolute Zero",
        description = "Molecular stillness cryo-lance frozen down to absolute thermal zero.",
        price = 3800,
        strokeColor = Color(0xFFE0F7FA),
        tipGlowColor = Color(0xFF80DEEA),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.ICE_SPIKE,
        strokeWidthDp = 18f
    )

    val INFINITY_BLADE = ArrowSkin(
        id = "skin_infinity_blade",
        name = "Infinity Edge",
        description = "Supreme mythical celestial blade carrying the primordial cosmic spark.",
        price = 4000,
        strokeColor = Color(0xFFFFD700),
        tipGlowColor = Color(0xFFD500F9),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.GOLDEN_CHROME,
        strokeWidthDp = 19f
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
        ANGELIC_WING,
        QUANTUM_PULSE,
        DRUID_VINE,
        SUPERNOVA,
        CYBERPUNK_GLITCH,
        DEMON_HORN,
        AURORA_BOREALIS,
        TITANIUM_DRILL,
        METEOR_STRIKE,
        JUNGLE_BLOWPIPE,
        TESLA_COIL,
        DIAMOND_SPEAR,
        PHOENIX_FEATHER,
        SHURIKEN_DART,
        HYPERSPACE_JUMP,
        COPPER_STEAM_ROD,
        ACID_STINGER,
        VORTEX_RIFT,
        GOLDEN_TRIDENT,
        SUGAR_LOLLIPOP,
        ANTIMATTER_BEAM,
        ZERO_KELVIN,
        INFINITY_BLADE
    )

    fun getSkinById(id: String): ArrowSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}
