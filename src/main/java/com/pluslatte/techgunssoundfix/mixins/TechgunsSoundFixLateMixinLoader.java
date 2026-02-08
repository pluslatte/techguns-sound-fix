package com.pluslatte.techgunssoundfix.mixins;

import io.github.tox1cozz.mixinbooterlegacy.ILateMixinLoader;
import io.github.tox1cozz.mixinbooterlegacy.LateMixin;

import java.util.Collections;
import java.util.List;

/**
 * Late Mixin Loader for Techguns Sound Fix.
 * This loader applies mixins after all mods have been loaded,
 * ensuring that the target Techguns classes are available.
 */
@LateMixin
public class TechgunsSoundFixLateMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        // Return the mixin configuration file to be loaded as a late mixin
        return Collections.singletonList("mixins.techgunssoundfix.json");
    }
}
