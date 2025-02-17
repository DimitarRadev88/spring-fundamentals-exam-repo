package app.story.model;

import lombok.Getter;

@Getter
public enum Kind {
    FIRST("CE-1 (Close Encounters of the First Kind)", "Sighting of a UFO within 500 feet, showing detail but without physical interaction."),
    SECOND("CE-2 (Second Kind)", "A UFO leaves physical evidence, such as imprints on the ground, crop circles, or radiation."),
    THIRD("CE-3 (Third Kind)", "Encounter with animated beings, often described as extraterrestrial."),
    FOURTH("Extended Categories", "Later interpretations added \"CE-4\" (abduction by aliens)."),
    ;

    private final String category;
    private final String definition;

    Kind(String category, String definition) {
        this.category = category;
        this.definition = definition;
    }


}
