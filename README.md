# Croparium × Industrial Foregoing

An unofficial NeoForge 1.21.1 compatibility mod for Croparium and Industrial Foregoing. This project is not affiliated with either mod's authors.

The **Plant Gatherer** harvests mature Croparium crops at age 7, puts Croparium's mapped crop product into its output inventory, and resets the crop to age 1. The plant stays in place, so a Plant Sower and seed loop are unnecessary. This applies to crops from Croparium add-ons such as CP Create, CP AE2, and CP Mekanism when they are included in Croparium's `cp_lib:crops` block tag.

The **Plant Fertilizer** also recognizes those crops and advances their age by 2 to 5 stages per fertilizer item, up to age 7. It uses Industrial Foregoing's existing fertilizer slot, energy cost, and area scanning.

## Requirements

- Minecraft 1.21.1 and NeoForge 21.1.250 or newer
- Industrial Foregoing 1.21-3.6.38 or newer
- Croparium (`cp_lib`) 5.0.26 or newer

Install the release JAR alongside these mods on both client and server. Croparium add-ons are optional.

## Behavior

- Only ripe Croparium crops (`age=7`) are harvested.
- The crop resets to `age=1`, matching Croparium's right-click harvest.
- The output uses Croparium's own crop-to-item mapping and its configured right-click base drop count. The Gatherer does not apply player enchantments or fire-aspect smelting.
- If Croparium has no mapped output for a crop, that crop remains untouched.
- The Gatherer handles energy use and item insertion through Industrial Foregoing's normal machinery.
- The Plant Fertilizer grows unripe Croparium crops in place. Ripe crops are skipped.

## Why I made this

I wanted this feature for my own modpack, but I have no experience creating Minecraft mods myself. I asked ChatGPT to create the mod and its project materials, including the code, icon, and this description. I tested the Plant Gatherer feature in my Minecraft client.

## Build

Run `./gradlew build` (or `gradlew.bat build` on Windows). The build fetches the exact Industrial Foregoing and Croparium files used during development from CurseMaven; no third-party JARs are redistributed in this source repository. The built JAR is in `build/libs/`.

## License

The original code in this repository is MIT licensed; see [LICENSE](LICENSE). The NeoForge starter template retains its own MIT notice in [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).

[Industrial Foregoing](https://github.com/InnovativeOnlineIndustries/Industrial-Foregoing) is by Buuz135 and is MIT licensed. [Croparium](https://github.com/litQA/Croparium) is by LitQA; its repository describes its source as All Rights Reserved and its published JAR as CC BY-NC 4.0. Both are separate required downloads and are not included in this mod's JAR.
