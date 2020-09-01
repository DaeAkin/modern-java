package dev.donghyeon.modernjava;

import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;

public class MyZoneRulesProvider extends ZoneRulesProvider {

    @Override
    protected Set<String> provideZoneIds() {
        Set<String> set = new HashSet<>();
        set.add("India/Delhi");
        set.add("India/Mumbai");
        set.add("India/Chennai");
        return set;
    }

    @Override
    protected ZoneRules provideRules(String zoneId, boolean forCaching) {
        return null;
    }

    @Override
    protected NavigableMap<String, ZoneRules> provideVersions(String zoneId) {
        return null;
    }

    public static void main(String[] args) {
        ZoneRulesProvider.registerProvider(new MyZoneRulesProvider());
        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
    }
}
