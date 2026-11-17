# Ad Astra: EMI Compat

A client-side NeoForge mod providing native [EMI](https://modrinth.com/mod/emi) recipe support for [Ad Astra](https://modrinth.com/mod/ad-astra) on **Minecraft 1.21.1**.

## Supported recipes

- NASA Workbench (rocket crafting, including all 14 ingredient slots)
- Compressor (compressing)
- Etrionic Blast Furnace (alloying)
- Cryo Freezer (cryo freezing, including fluid outputs)
- Oxygen Loader (oxygen loading, including fluid inputs/outputs)
- Fuel Refinery (refining, including fluid inputs/outputs)

Recipes are taken from the active world's recipe manager, including recipes added or replaced by datapacks. Each category is linked to its machine as a workstation. Processing recipes display cooking time and energy requirements.

## Requirements

- Minecraft 1.21.1 / NeoForge 21.1.267 or newer compatible 21.1.x
- Ad Astra 1.16.26 or newer compatible 1.21.1 release
- EMI 1.1.24 or newer compatible 1.21.1 release

Install this mod on the **client** alongside Ad Astra and EMI. It is not required by the server.

## Build

Use Java 21:

```shell
./gradlew build
```

The jar will be in `build/libs`. GitHub Actions also runs this build on pushes and pull requests.

## Licensing and attribution

This project is licensed under the **MIT License** (see [LICENSE](LICENSE)). The NASA Workbench slot arrangement was adapted from [ImEden6/emi-compat](https://github.com/ImEden6/emi-compat), also MIT licensed. The MIT attribution for that project and the NeoForge MDK template are retained in [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) and `TEMPLATE_LICENSE.txt`.

Ad Astra itself is a separate project with its own license; no Ad Astra source code or textures are redistributed here.
