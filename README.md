# Ad Astra: EMI Compat

Client-side [EMI](https://modrinth.com/mod/emi) recipe support for [Ad Astra](https://modrinth.com/mod/ad-astra) on **Minecraft 1.20.1 / Forge**.

## Supported recipes

- NASA Workbench (rocket crafting, all 14 ingredient slots)
- Compressor
- Etrionic Blast Furnace
- Cryo Freezer (fluid outputs)
- Oxygen Loader (fluid inputs/outputs)
- Fuel Refinery (fluid inputs/outputs)

Recipes are loaded from the current world's recipe manager, including datapack recipes. Each category is linked to its machine as a workstation.

## Requirements

- Minecraft 1.20.1
- Forge **47.1.3** or newer compatible 47.x
- Ad Astra **1.15.21**
- EMI **1.1.24+1.20.1**
- Ad Astra's required libraries (Botarium, Resourceful Lib, Resourceful Config)

Install on the **client**. It is not required on the server.

## Build

Use **Java 17** and run:

```shell
./gradlew build
```

The jar is written to `build/libs` and uploaded by GitHub Actions.

## License

MIT. See [LICENSE](LICENSE) and [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md).
