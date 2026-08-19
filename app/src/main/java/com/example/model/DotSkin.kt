package com.example.model

import androidx.compose.ui.graphics.Color

enum class DotStyle {
    CLASSIC_TARGET,       // Standard glowing dot target
    ELECTRIC_RING,        // Pulsing electric outer ring with center core
    STAR_BURST,           // Golden sparkling star with rays
    COSMIC_SINGULARITY,   // Dark void core with swirling vortex halo
    MOLTEN_SUN,           // Pulsing fire ember sun with solar flares
    MATRIX_RADAR,         // Code green target grid radar rings
    RAINBOW_CHROMA,       // Color-shifting rainbow prism dot
    TECH_HEXAGON,         // Sci-fi hexagonal barrier shield dot
    ICE_CRYSTAL,
    TOXIC_BIOHAZARD,
    SHADOW_PORTAL,
    HEAVENLY_HALO,
    GEAR_CLOCKWORK,
    WATER_RIPPLE,
    SWEET_DONUT,
    CHROME_METAL,
    PIXEL_HEART,
    GOLDEN_SHIELD,
    NEON_CROSSHAIR,
    FIREFLY_SWARM,
    GALAXY_ORBIT,
    PLASMA_BALL,
    EGYPTIAN_EYE,
    YIN_YANG,
    DISCO_BALL
}

data class DotSkin(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val glowColor: Color,
    val centerColor: Color,
    val style: DotStyle,
    val glowRadiusDp: Float = 18f
)

object DotSkinCatalog {
    val CLASSIC = DotSkin(
        id = "dot_classic",
        name = "Classic Target",
        description = "Standard high-visibility red laser core targeting dot.",
        price = 0,
        glowColor = Color(0xFFFF3B30),
        centerColor = Color(0xFFFF5A52),
        style = DotStyle.CLASSIC_TARGET
    )

    val CYAN_RING = DotSkin(
        id = "dot_cyan_ring",
        name = "Cyber Cyan Ring",
        description = "Pulsing neon-cyan energy ring with a blazing core.",
        price = 25,
        glowColor = Color(0xFF00E5FF),
        centerColor = Color(0xFFE0F7FA),
        style = DotStyle.ELECTRIC_RING
    )

    val GOLDEN_STAR = DotSkin(
        id = "dot_golden_star",
        name = "Nebula Star",
        description = "A glittering solar star radiating pure cosmic gold light.",
        price = 50,
        glowColor = Color(0xFFFFD54F),
        centerColor = Color(0xFFFFF8E1),
        style = DotStyle.STAR_BURST
    )

    val COSMIC_VOID = DotSkin(
        id = "dot_cosmic_void",
        name = "Black Hole",
        description = "A localized gravity well drawing light into a violet core.",
        price = 85,
        glowColor = Color(0xFF7C4DFF),
        centerColor = Color(0xFFF3E5F5),
        style = DotStyle.COSMIC_SINGULARITY
    )

    val MOLTEN_SUN = DotSkin(
        id = "dot_molten_sun",
        name = "Solar Flare",
        description = "Volcanic pulsing plasma fire ember with a warm solar aura.",
        price = 120,
        glowColor = Color(0xFFFF3D00),
        centerColor = Color(0xFFFFAB91),
        style = DotStyle.MOLTEN_SUN
    )

    val MATRIX_RADAR = DotSkin(
        id = "dot_matrix_radar",
        name = "Digital Radar",
        description = "Grid-locked matrix green telemetry scanner lock-on rings.",
        price = 180,
        glowColor = Color(0xFF00E676),
        centerColor = Color(0xFFE8F5E9),
        style = DotStyle.MATRIX_RADAR
    )

    val RAINBOW_CHROMA = DotSkin(
        id = "dot_rainbow_chroma",
        name = "Prism Chroma",
        description = "Prismatic chromatic aberration shifting through RGB spectra.",
        price = 240,
        glowColor = Color(0xFFE040FB),
        centerColor = Color(0xFFEDE7F6),
        style = DotStyle.RAINBOW_CHROMA
    )

    val TECH_HEXAGON = DotSkin(
        id = "dot_tech_hexagon",
        name = "Aegis Hexagon",
        description = "Futuristic hard-light energy shield with defensive barrier.",
        price = 300,
        glowColor = Color(0xFF2979FF),
        centerColor = Color(0xFFE3F2FD),
        style = DotStyle.TECH_HEXAGON
    )

    val GLACIAL_FROST = DotSkin(
        id = "dot_glacial_frost",
        name = "Sub-Zero Frost",
        description = "Deep frozen cryo-crystal freezing the target perimeter.",
        price = 350,
        glowColor = Color(0xFF80DEEA),
        centerColor = Color(0xFFE0F7FA),
        style = DotStyle.ICE_CRYSTAL
    )

    val TOXIC_BIOHAZARD = DotSkin(
        id = "dot_toxic_biohazard",
        name = "Biohazard Core",
        description = "Radioactive isotope glowing in warning chartreuse lime.",
        price = 400,
        glowColor = Color(0xFF76FF03),
        centerColor = Color(0xFFF1F8E9),
        style = DotStyle.TOXIC_BIOHAZARD
    )

    val ABYSSAL_RIFT = DotSkin(
        id = "dot_abyssal_rift",
        name = "Shadow Rift",
        description = "Dark matter portal pulsing with deep abyssal purple void.",
        price = 450,
        glowColor = Color(0xFF651FFF),
        centerColor = Color(0xFF311B92),
        style = DotStyle.SHADOW_PORTAL
    )

    val DIVINE_HALO = DotSkin(
        id = "dot_divine_halo",
        name = "Seraph Halo",
        description = "Sacred blessed celestial ring glowing with immaculate warmth.",
        price = 500,
        glowColor = Color(0xFFFFD700),
        centerColor = Color(0xFFFFFDE7),
        style = DotStyle.HEAVENLY_HALO
    )

    val TEMPUS_GEAR = DotSkin(
        id = "dot_tempus_gear",
        name = "Tempus Clockwork",
        description = "Intricate ticking chronometer gear regulating flow of time.",
        price = 550,
        glowColor = Color(0xFFBCAAA4),
        centerColor = Color(0xFF4E342E),
        style = DotStyle.GEAR_CLOCKWORK
    )

    val TSUNAMI_CORE = DotSkin(
        id = "dot_tsunami_core",
        name = "Ocean Vortex",
        description = "Swirling deep ocean whirlpool ripple of pure marine energy.",
        price = 600,
        glowColor = Color(0xFF00B0FF),
        centerColor = Color(0xFF01579B),
        style = DotStyle.WATER_RIPPLE
    )

    val SPRINKLES_DONUT = DotSkin(
        id = "dot_sprinkles_donut",
        name = "Sweet Donut",
        description = "Glazed strawberry confection adorned with colorful rainbow sprinkles.",
        price = 650,
        glowColor = Color(0xFFFF4081),
        centerColor = Color(0xFFFCE4EC),
        style = DotStyle.SWEET_DONUT
    )

    val TITANIUM_ALLOY = DotSkin(
        id = "dot_titanium_alloy",
        name = "Titanium Core",
        description = "Machined aircraft-grade brushed titanium with metallic luster.",
        price = 700,
        glowColor = Color(0xFFCFD8DC),
        centerColor = Color(0xFF37474F),
        style = DotStyle.CHROME_METAL
    )

    val LOVE_HEART = DotSkin(
        id = "dot_love_heart",
        name = "Pixel Heart",
        description = "Charming 8-bit retro arcade health heart brimming with vitality.",
        price = 750,
        glowColor = Color(0xFFFF1744),
        centerColor = Color(0xFFFFEBEE),
        style = DotStyle.PIXEL_HEART
    )

    val AEGIS_SHIELD = DotSkin(
        id = "dot_aegis_shield",
        name = "Imperial Aegis",
        description = "Heavy armored golden crest forged to withstand extreme impact.",
        price = 800,
        glowColor = Color(0xFFFFC107),
        centerColor = Color(0xFFFF8F00),
        style = DotStyle.GOLDEN_SHIELD
    )

    val SPECOPS_SIGHT = DotSkin(
        id = "dot_specops_sight",
        name = "Night Ops Crosshair",
        description = "Tactical night-vision green reticle locked onto target coordinates.",
        price = 850,
        glowColor = Color(0xFF00E676),
        centerColor = Color(0xFF1B5E20),
        style = DotStyle.NEON_CROSSHAIR
    )

    val FIREFLY_SWARM = DotSkin(
        id = "dot_firefly_swarm",
        name = "Biolume Swarm",
        description = "Cluster of dancing nocturnal fireflies illuminating the dark.",
        price = 900,
        glowColor = Color(0xFFEEFF41),
        centerColor = Color(0xFFAEEA00),
        style = DotStyle.FIREFLY_SWARM
    )

    val GALAXY_ORBIT = DotSkin(
        id = "dot_galaxy_orbit",
        name = "Andromeda Orbit",
        description = "Miniature spiral galaxy surrounded by satellite moons.",
        price = 1000,
        glowColor = Color(0xFFD500F9),
        centerColor = Color(0xFF4A148C),
        style = DotStyle.GALAXY_ORBIT
    )

    val PLASMA_BALL = DotSkin(
        id = "dot_plasma_ball",
        name = "Arc Plasma Sphere",
        description = "Sealed glass sphere crackling with high-voltage electricity.",
        price = 1100,
        glowColor = Color(0xFF00E5FF),
        centerColor = Color(0xFFD500F9),
        style = DotStyle.PLASMA_BALL
    )

    val EGYPTIAN_EYE = DotSkin(
        id = "dot_egyptian_eye",
        name = "Eye of Horus",
        description = "Mythical neon blue and gold Egyptian protective eye target.",
        price = 1200,
        glowColor = Color(0xFF00B0FF),
        centerColor = Color(0xFFFFD54F),
        style = DotStyle.EGYPTIAN_EYE
    )

    val ZEN_BALANCE = DotSkin(
        id = "dot_zen_balance",
        name = "Zen Yin-Yang",
        description = "Spinning black-and-white fluid equilibrium target representing peace.",
        price = 1300,
        glowColor = Color(0xFF37474F),
        centerColor = Color(0xFFFFFFFF),
        style = DotStyle.YIN_YANG
    )

    val RETRO_DISCO = DotSkin(
        id = "dot_retro_disco",
        name = "Retro Disco Ball",
        description = "Fabulous shiny glittering 70s party disco dance floor light ball.",
        price = 1400,
        glowColor = Color(0xFF00E5FF),
        centerColor = Color(0xFFE040FB),
        style = DotStyle.DISCO_BALL
    )

    // ADDITIONAL DOT SKINS TO SURPASS 50+
    val VOLCANO_CORE = DotSkin(
        id = "dot_volcano_core",
        name = "Volcano Magma Core",
        description = "Boiling subterranean magma chamber releasing volcanic pressure.",
        price = 1500,
        glowColor = Color(0xFFFF3D00),
        centerColor = Color(0xFFFFD600),
        style = DotStyle.MOLTEN_SUN
    )

    val ARCTIC_BLIZZARD = DotSkin(
        id = "dot_arctic_blizzard",
        name = "Arctic Snowflake",
        description = "Six-fold symmetrical ice crystal spinning in glacial air.",
        price = 1600,
        glowColor = Color(0xFF00E5FF),
        centerColor = Color(0xFFE0F7FA),
        style = DotStyle.ICE_CRYSTAL
    )

    val QUANTUM_ATOM = DotSkin(
        id = "dot_quantum_atom",
        name = "Quantum Orbital",
        description = "Atomic nucleus wrapped in spinning high-energy electron shells.",
        price = 1700,
        glowColor = Color(0xFF7C4DFF),
        centerColor = Color(0xFF00E5FF),
        style = DotStyle.GALAXY_ORBIT
    )

    val CYBER_SCANNER = DotSkin(
        id = "dot_cyber_scanner",
        name = "Cyber Telemetry",
        description = "360-degree holographic lidar target tracking grid.",
        price = 1800,
        glowColor = Color(0xFF00E676),
        centerColor = Color(0xFFB9F6CA),
        style = DotStyle.MATRIX_RADAR
    )

    val CRIMSON_RUBY = DotSkin(
        id = "dot_crimson_ruby",
        name = "Emperor Ruby",
        description = "Brilliant round-cut imperial blood ruby gemstone.",
        price = 1900,
        glowColor = Color(0xFFFF1744),
        centerColor = Color(0xFFFF8A80),
        style = DotStyle.STAR_BURST
    )

    val SHADOW_ECLIPSE = DotSkin(
        id = "dot_shadow_eclipse",
        name = "Solar Eclipse",
        description = "Corona of blazing sunlight peering behind total lunar darkness.",
        price = 2000,
        glowColor = Color(0xFFFFD54F),
        centerColor = Color(0xFF1A1A1A),
        style = DotStyle.COSMIC_SINGULARITY
    )

    val EMERALD_GEM = DotSkin(
        id = "dot_emerald_gem",
        name = "Royal Emerald",
        description = "Precious radiant cut Colombian emerald with inner deep fire.",
        price = 2100,
        glowColor = Color(0xFF00E676),
        centerColor = Color(0xFFE8F5E9),
        style = DotStyle.TECH_HEXAGON
    )

    val PULSAR_BEACON = DotSkin(
        id = "dot_pulsar_beacon",
        name = "Neutron Pulsar",
        description = "Ultra-dense spinning neutron star sweeping relativistic radiation beams.",
        price = 2200,
        glowColor = Color(0xFFD500F9),
        centerColor = Color(0xFFFFFFFF),
        style = DotStyle.STAR_BURST
    )

    val NEON_RADIAL = DotSkin(
        id = "dot_neon_radial",
        name = "Neon Synthwave",
        description = "Hot magenta glowing circular synthwave reticle from 1984.",
        price = 2300,
        glowColor = Color(0xFFFF007F),
        centerColor = Color(0xFF00FFFF),
        style = DotStyle.ELECTRIC_RING
    )

    val JADE_MEDALLION = DotSkin(
        id = "dot_jade_medallion",
        name = "Dynasty Jade",
        description = "Ancient royal imperial carved jade token of fortune.",
        price = 2400,
        glowColor = Color(0xFF2E7D32),
        centerColor = Color(0xFFA5D6A7),
        style = DotStyle.YIN_YANG
    )

    val HYPER_CHRONO = DotSkin(
        id = "dot_hyper_chrono",
        name = "Time Dial",
        description = "Precision stopwatch dial ticking with microsecond accuracy.",
        price = 2500,
        glowColor = Color(0xFFFFB300),
        centerColor = Color(0xFFFFF8E1),
        style = DotStyle.GEAR_CLOCKWORK
    )

    val POSEIDON_PEARL = DotSkin(
        id = "dot_poseidon_pearl",
        name = "Abyssal Pearl",
        description = "Deep sea glowing iridescent black pearl found in Mariana depths.",
        price = 2600,
        glowColor = Color(0xFF00B0FF),
        centerColor = Color(0xFFE0F7FA),
        style = DotStyle.WATER_RIPPLE
    )

    val CYBER_HEX = DotSkin(
        id = "dot_cyber_hex",
        name = "Nanotech Mesh",
        description = "Reinforced carbon-nanotube hexagonal lattice structure.",
        price = 2700,
        glowColor = Color(0xFF2979FF),
        centerColor = Color(0xFF82B1FF),
        style = DotStyle.TECH_HEXAGON
    )

    val VENOM_SPORE = DotSkin(
        id = "dot_venom_spore",
        name = "Toxic Spore",
        description = "Alien botanical spore bulb pulsating with bio-luminescent acid.",
        price = 2800,
        glowColor = Color(0xFFAEEA00),
        centerColor = Color(0xFF64DD17),
        style = DotStyle.TOXIC_BIOHAZARD
    )

    val DIAMOND_STAR = DotSkin(
        id = "dot_diamond_star",
        name = "Sirius Diamond",
        description = "Brilliant quadruple-ray crystal star illuminating deep space.",
        price = 2900,
        glowColor = Color(0xFFE0F7FA),
        centerColor = Color(0xFFFFFFFF),
        style = DotStyle.STAR_BURST
    )

    val HELLFIRE_ORB = DotSkin(
        id = "dot_hellfire_orb",
        name = "Hellfire Sphere",
        description = "Dark brimstone orb swirling with demonic red flames.",
        price = 3000,
        glowColor = Color(0xFFFF1744),
        centerColor = Color(0xFFB71C1C),
        style = DotStyle.MOLTEN_SUN
    )

    val GOLDEN_COMPASS = DotSkin(
        id = "dot_golden_compass",
        name = "Mariner Compass",
        description = "Ornate maritime navigator compass pointing to true polar north.",
        price = 3100,
        glowColor = Color(0xFFFFD54F),
        centerColor = Color(0xFF8D6E63),
        style = DotStyle.NEON_CROSSHAIR
    )

    val CHROMA_SPECTRUM = DotSkin(
        id = "dot_chroma_spectrum",
        name = "Prism Spectrum",
        description = "Full 360-degree color wheel radiating chromatic light.",
        price = 3200,
        glowColor = Color(0xFFFF4081),
        centerColor = Color(0xFF00E5FF),
        style = DotStyle.RAINBOW_CHROMA
    )

    val THUNDER_CORE = DotSkin(
        id = "dot_thunder_core",
        name = "Storm Battery",
        description = "Capacitor storing the electrical energy of ten thousand lightning bolts.",
        price = 3300,
        glowColor = Color(0xFFFFEA00),
        centerColor = Color(0xFF2979FF),
        style = DotStyle.PLASMA_BALL
    )

    val CANDY_SWIRL = DotSkin(
        id = "dot_candy_swirl",
        name = "Peppermint Pinwheel",
        description = "Delicious spiral spinning round candy peppermint wheel.",
        price = 3400,
        glowColor = Color(0xFFFF1744),
        centerColor = Color(0xFFFFFFFF),
        style = DotStyle.SWEET_DONUT
    )

    val CELESTIAL_EYE = DotSkin(
        id = "dot_celestial_eye",
        name = "Oracle All-Seeing",
        description = "Cosmic mystic eye perceiving the infinite timelines.",
        price = 3500,
        glowColor = Color(0xFF7C4DFF),
        centerColor = Color(0xFFFFD54F),
        style = DotStyle.EGYPTIAN_EYE
    )

    val BIO_PULSE = DotSkin(
        id = "dot_bio_pulse",
        name = "Living Synapse",
        description = "Neural synaptic node firing bioelectric cognitive impulses.",
        price = 3600,
        glowColor = Color(0xFF00E676),
        centerColor = Color(0xFF00B0FF),
        style = DotStyle.FIREFLY_SWARM
    )

    val DARK_VOID = DotSkin(
        id = "dot_dark_void",
        name = "Singularity Zero",
        description = "Pure black gravitational singularity from the center of the universe.",
        price = 3800,
        glowColor = Color(0xFF6200EA),
        centerColor = Color(0xFF000000),
        style = DotStyle.SHADOW_PORTAL
    )

    val SUPREME_INFINITY = DotSkin(
        id = "dot_supreme_infinity",
        name = "Supreme Aura",
        description = "Ultimate divine golden crown target radiating infinite victory.",
        price = 4000,
        glowColor = Color(0xFFFFD700),
        centerColor = Color(0xFFFFFFFF),
        style = DotStyle.HEAVENLY_HALO
    )

    val allSkins: List<DotSkin> = listOf(
        CLASSIC,
        CYAN_RING,
        GOLDEN_STAR,
        COSMIC_VOID,
        MOLTEN_SUN,
        MATRIX_RADAR,
        RAINBOW_CHROMA,
        TECH_HEXAGON,
        GLACIAL_FROST,
        TOXIC_BIOHAZARD,
        ABYSSAL_RIFT,
        DIVINE_HALO,
        TEMPUS_GEAR,
        TSUNAMI_CORE,
        SPRINKLES_DONUT,
        TITANIUM_ALLOY,
        LOVE_HEART,
        AEGIS_SHIELD,
        SPECOPS_SIGHT,
        FIREFLY_SWARM,
        GALAXY_ORBIT,
        PLASMA_BALL,
        EGYPTIAN_EYE,
        ZEN_BALANCE,
        RETRO_DISCO,
        VOLCANO_CORE,
        ARCTIC_BLIZZARD,
        QUANTUM_ATOM,
        CYBER_SCANNER,
        CRIMSON_RUBY,
        SHADOW_ECLIPSE,
        EMERALD_GEM,
        PULSAR_BEACON,
        NEON_RADIAL,
        JADE_MEDALLION,
        HYPER_CHRONO,
        POSEIDON_PEARL,
        CYBER_HEX,
        VENOM_SPORE,
        DIAMOND_STAR,
        HELLFIRE_ORB,
        GOLDEN_COMPASS,
        CHROMA_SPECTRUM,
        THUNDER_CORE,
        CANDY_SWIRL,
        CELESTIAL_EYE,
        BIO_PULSE,
        DARK_VOID,
        SUPREME_INFINITY
    )

    fun getSkinById(id: String): DotSkin {
        return allSkins.find { it.id == id } ?: CLASSIC
    }
}
