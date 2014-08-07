package co.artmann.flypatterns_android;

public class PatternSpec {
    private String placement = "";
    private String color     = "";
    private String material  = "";

    public PatternSpec(String placement, String color, String material) {
        this.placement = placement;
        this.color     = color;
        this.material  = material;
    }

    public String getPlacement() {
        return this.placement;
    }

    public String getColor() {
        return this.color;
    }

    public String getMaterial() {
        return this.material;
    }

    @Override
    public String toString() {
        return placement + ": " + color + " " + material;
    }
}
