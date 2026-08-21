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
    ANGELIC_WING,
    VIKING_BATTLEAXE,
    ANUBIS_KHOPESH,
    NEON_LIGHTSABER,
    DRILL_ROCKET,
    DNA_HELIX,
    MAGIC_WAND_CRYSTAL,
    FISHBONE_HARPOON,
    GUITAR_HEADSTOCK,
    PENCIL_CRAYON,
    SCYTHE_REAPER,
    CIRCUIT_TRON,
    CACTUS_DESERT,
    FEATHER_QUILL,
    BICYCLE_CHAIN,
    LAVA_SWORD,
    CHESS_KNIGHT_LANCE,
    BARBED_WIRE,
    DIAMOND_PICKAXE,
    CHOPSTICKS_NOODLE,
    SPIDER_WEB_STRAND,
    LASER_GUN_BLASTER
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
        price = 350,
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
        price = 160,
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
        price = 140,
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
        price = 180,
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
        price = 300,
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
        price = 280,
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
        price = 550,
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
        price = 900,
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
        price = 950,
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
        price = 2600,
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
        price = 1800,
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
        price = 1400,
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
        price = 2500,
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
        price = 1700,
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
        price = 1050,
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
        price = 2400,
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
        price = 1900,
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
        price = 1000,
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
        price = 2700,
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
        price = 1100,
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
        price = 1150,
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
        price = 2800,
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
        price = 3000,
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
        price = 1300,
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
        price = 850,
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
        price = 450,
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
        price = 650,
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
        price = 2900,
        strokeColor = Color(0xFFECEFF1),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFFFFDE7),
        tailStyle = ArrowTailStyle.ANGELIC_WING,
        strokeWidthDp = 16f,
        headWingLengthDp = 84f,
        glowRadiusDp = 26f
    )

    val VIKING_BATTLEAXE = ArrowSkin(
        id = "skin_viking_battleaxe",
        name = "Viking Battleaxe",
        description = "Nordic double-bitted carved steel axe blade on leather-wrapped ash handle.",
        price = 1200,
        strokeColor = Color(0xFF5D4037),
        tipGlowColor = Color(0xFF90A4AE),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.VIKING_BATTLEAXE,
        strokeWidthDp = 16f,
        headWingLengthDp = 80f,
        glowRadiusDp = 22f
    )

    val ANUBIS_KHOPESH = ArrowSkin(
        id = "skin_anubis_khopesh",
        name = "Anubis Khopesh",
        description = "Pharaoh sickled blade forged in celestial gold and inlaid with lapis lazuli.",
        price = 2100,
        strokeColor = Color(0xFFFFD700),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.ANUBIS_KHOPESH,
        strokeWidthDp = 16f,
        headWingLengthDp = 75f,
        glowRadiusDp = 25f
    )

    val NEON_LIGHTSABER = ArrowSkin(
        id = "skin_neon_lightsaber",
        name = "Plasma Saber",
        description = "High-energy plasma blade with pulsing laser core and metal emitter hilt.",
        price = 2000,
        strokeColor = Color(0xFF00E5FF),
        tipGlowColor = Color(0xFF00B0FF),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.NEON_LIGHTSABER,
        strokeWidthDp = 18f,
        headWingLengthDp = 80f,
        glowRadiusDp = 28f
    )

    val DRILL_ROCKET = ArrowSkin(
        id = "skin_drill_rocket",
        name = "Turbo Drill",
        description = "Titanium industrial screw drill with blazing rocket booster exhaust at base.",
        price = 750,
        strokeColor = Color(0xFF455A64),
        tipGlowColor = Color(0xFFFF6D00),
        tipCenterColor = Color(0xFFFFD600),
        tailStyle = ArrowTailStyle.DRILL_ROCKET,
        strokeWidthDp = 20f,
        headWingLengthDp = 75f,
        glowRadiusDp = 24f
    )

    val DNA_HELIX = ArrowSkin(
        id = "skin_dna_helix",
        name = "DNA Helix Strand",
        description = "Bioluminescent genetic double helix strand with glowing nucleotide rungs.",
        price = 1500,
        strokeColor = Color(0xFF00E676),
        tipGlowColor = Color(0xFFD500F9),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.DNA_HELIX,
        strokeWidthDp = 18f,
        headWingLengthDp = 72f,
        glowRadiusDp = 24f
    )

    val MAGIC_WAND_CRYSTAL = ArrowSkin(
        id = "skin_magic_wand",
        name = "Arcane Staff",
        description = "Elder wizard wood wand with a levitating purple quartz crystal at the apex.",
        price = 1600,
        strokeColor = Color(0xFF4E342E),
        tipGlowColor = Color(0xFFBA68C8),
        tipCenterColor = Color(0xFFEDE7F6),
        tailStyle = ArrowTailStyle.MAGIC_WAND_CRYSTAL,
        strokeWidthDp = 16f,
        headWingLengthDp = 75f,
        glowRadiusDp = 26f
    )

    val FISHBONE_HARPOON = ArrowSkin(
        id = "skin_fishbone_harpoon",
        name = "Bone Harpoon",
        description = "Abyssal leviathan skeletal fishbone shaft bristling with sharp rib spines.",
        price = 420,
        strokeColor = Color(0xFFECEFF1),
        tipGlowColor = Color(0xFF00ACC1),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.FISHBONE_HARPOON,
        strokeWidthDp = 16f,
        headWingLengthDp = 70f,
        glowRadiusDp = 22f
    )

    val GUITAR_HEADSTOCK = ArrowSkin(
        id = "skin_guitar_headstock",
        name = "Electric Guitar",
        description = "Rock & Roll hardwood fretboard neck with steel strings and tuning peg headstock.",
        price = 500,
        strokeColor = Color(0xFFD32F2F),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.GUITAR_HEADSTOCK,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 20f
    )

    val PENCIL_CRAYON = ArrowSkin(
        id = "skin_pencil_crayon",
        name = "HB #2 Pencil",
        description = "Classic school yellow hexagonal wood pencil with pink rubber eraser and sharpened lead.",
        price = 120,
        strokeColor = Color(0xFFFFC107),
        tipGlowColor = Color(0xFFFF4081),
        tipCenterColor = Color(0xFF212121),
        tailStyle = ArrowTailStyle.PENCIL_CRAYON,
        strokeWidthDp = 18f,
        headWingLengthDp = 65f,
        glowRadiusDp = 18f
    )

    val SCYTHE_REAPER = ArrowSkin(
        id = "skin_scythe_reaper",
        name = "Grim Reaper Scythe",
        description = "Curved ominous silver death blade on gnarled dark wood shaft.",
        price = 2200,
        strokeColor = Color(0xFF263238),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFCFD8DC),
        tailStyle = ArrowTailStyle.SCYTHE_REAPER,
        strokeWidthDp = 16f,
        headWingLengthDp = 85f,
        glowRadiusDp = 26f
    )

    val CIRCUIT_TRON = ArrowSkin(
        id = "skin_circuit_tron",
        name = "Printed Circuit",
        description = "Green PCB motherboard trace with gold circuit tracks and microchip core.",
        price = 700,
        strokeColor = Color(0xFF1B5E20),
        tipGlowColor = Color(0xFF00E676),
        tipCenterColor = Color(0xFFFFD600),
        tailStyle = ArrowTailStyle.CIRCUIT_TRON,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 24f
    )

    val CACTUS_DESERT = ArrowSkin(
        id = "skin_cactus_desert",
        name = "Desert Saguaro",
        description = "Prickly green desert cactus stem armed with sharp spines and blooming red flower.",
        price = 220,
        strokeColor = Color(0xFF2E7D32),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFFFFEB3B),
        tailStyle = ArrowTailStyle.CACTUS_DESERT,
        strokeWidthDp = 20f,
        headWingLengthDp = 65f,
        glowRadiusDp = 20f
    )

    val FEATHER_QUILL = ArrowSkin(
        id = "skin_feather_quill",
        name = "Peacock Quill",
        description = "Victorian antique peacock feather quill pen with iridescent eye plume.",
        price = 320,
        strokeColor = Color(0xFF00897B),
        tipGlowColor = Color(0xFF2979FF),
        tipCenterColor = Color(0xFFFFD54F),
        tailStyle = ArrowTailStyle.FEATHER_QUILL,
        strokeWidthDp = 16f,
        headWingLengthDp = 80f,
        glowRadiusDp = 24f
    )

    val BICYCLE_CHAIN = ArrowSkin(
        id = "skin_bicycle_chain",
        name = "Roller Chain",
        description = "Interlocking hardened steel mechanical bike chain links with driven sprocket.",
        price = 380,
        strokeColor = Color(0xFF607D8B),
        tipGlowColor = Color(0xFFCFD8DC),
        tipCenterColor = Color(0xFFFFAB00),
        tailStyle = ArrowTailStyle.BICYCLE_CHAIN,
        strokeWidthDp = 18f,
        headWingLengthDp = 70f,
        glowRadiusDp = 20f
    )

    val LAVA_SWORD = ArrowSkin(
        id = "skin_lava_sword",
        name = "Molten Obsidian",
        description = "Cracked volcanic glass broadsword glowing with burning magma veins.",
        price = 3500,
        strokeColor = Color(0xFF212121),
        tipGlowColor = Color(0xFFFF3D00),
        tipCenterColor = Color(0xFFFFD54F),
        tailStyle = ArrowTailStyle.LAVA_SWORD,
        strokeWidthDp = 18f,
        headWingLengthDp = 80f,
        glowRadiusDp = 28f
    )

    val CHESS_KNIGHT_LANCE = ArrowSkin(
        id = "skin_chess_knight",
        name = "Chess Knight Lance",
        description = "Polished black & white chequered javelin crowned with carved stallion head.",
        price = 1250,
        strokeColor = Color(0xFF212121),
        tipGlowColor = Color(0xFFECEFF1),
        tipCenterColor = Color(0xFFFFD700),
        tailStyle = ArrowTailStyle.CHESS_KNIGHT_LANCE,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 22f
    )

    val BARBED_WIRE = ArrowSkin(
        id = "skin_barbed_wire",
        name = "Razor Barbed Wire",
        description = "Twisted high-tensile galvanized steel cables armed with 4-pointed razor barbs.",
        price = 250,
        strokeColor = Color(0xFF78909C),
        tipGlowColor = Color(0xFFFF5252),
        tipCenterColor = Color(0xFFECEFF1),
        tailStyle = ArrowTailStyle.BARBED_WIRE,
        strokeWidthDp = 14f,
        headWingLengthDp = 70f,
        glowRadiusDp = 20f
    )

    val DIAMOND_PICKAXE = ArrowSkin(
        id = "skin_diamond_pickaxe",
        name = "Voxel Pickaxe",
        description = "Pixelated retro voxel diamond mining pickaxe with sturdy wood handle.",
        price = 600,
        strokeColor = Color(0xFF5D4037),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFE0F7FA),
        tailStyle = ArrowTailStyle.DIAMOND_PICKAXE,
        strokeWidthDp = 18f,
        headWingLengthDp = 75f,
        glowRadiusDp = 24f
    )

    val CHOPSTICKS_NOODLE = ArrowSkin(
        id = "skin_chopsticks_noodle",
        name = "Ramen Chopsticks",
        description = "Pair of natural wood chopsticks swirling fresh golden ramen noodles.",
        price = 200,
        strokeColor = Color(0xFF8D6E63),
        tipGlowColor = Color(0xFFFFD54F),
        tipCenterColor = Color(0xFFFF1744),
        tailStyle = ArrowTailStyle.CHOPSTICKS_NOODLE,
        strokeWidthDp = 16f,
        headWingLengthDp = 70f,
        glowRadiusDp = 20f
    )

    val SPIDER_WEB_STRAND = ArrowSkin(
        id = "skin_spider_web",
        name = "Widow Web Strand",
        description = "Glistening sticky arachnid silk filament with red hourglass spider emblem.",
        price = 800,
        strokeColor = Color(0xFFB0BEC5),
        tipGlowColor = Color(0xFFFF1744),
        tipCenterColor = Color(0xFF212121),
        tailStyle = ArrowTailStyle.SPIDER_WEB_STRAND,
        strokeWidthDp = 14f,
        headWingLengthDp = 75f,
        glowRadiusDp = 22f
    )

    val LASER_GUN_BLASTER = ArrowSkin(
        id = "skin_laser_blaster",
        name = "Sci-Fi Blaster",
        description = "Pulsing particle condenser beam with neon blue containment rings.",
        price = 3200,
        strokeColor = Color(0xFF2979FF),
        tipGlowColor = Color(0xFF00E5FF),
        tipCenterColor = Color(0xFFFFFFFF),
        tailStyle = ArrowTailStyle.LASER_GUN_BLASTER,
        strokeWidthDp = 18f,
        headWingLengthDp = 82f,
        glowRadiusDp = 28f
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
        VIKING_BATTLEAXE,
        ANUBIS_KHOPESH,
        NEON_LIGHTSABER,
        DRILL_ROCKET,
        DNA_HELIX,
        MAGIC_WAND_CRYSTAL,
        FISHBONE_HARPOON,
        GUITAR_HEADSTOCK,
        PENCIL_CRAYON,
        SCYTHE_REAPER,
        CIRCUIT_TRON,
        CACTUS_DESERT,
        FEATHER_QUILL,
        BICYCLE_CHAIN,
        LAVA_SWORD,
        CHESS_KNIGHT_LANCE,
        BARBED_WIRE,
        DIAMOND_PICKAXE,
        CHOPSTICKS_NOODLE,
        SPIDER_WEB_STRAND,
        LASER_GUN_BLASTER
    )

    fun getSkinById(id: String): ArrowSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}

