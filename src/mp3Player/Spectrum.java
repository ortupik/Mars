package mp3Player;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Spectrum extends Parent {
    private final Color BAR_COLOR = Color.GREENYELLOW;
    private Rectangle[] bars = new Rectangle[20];
    private DoubleProperty value = new SimpleDoubleProperty(0) {
        @Override protected void invalidated() {
            super.invalidated();
            double lastBar = get()*bars.length;
            for(int i=0; i<bars.length;i++) {
                bars[i].setVisible(i < lastBar);
            }
        }
    };
    public void setValue(double v) { value.set(v); }
    public double getValue() { return value.get(); }
    public DoubleProperty valueProperty() { return value; }
    
    public Spectrum() {
        for(int i=0; i<bars.length;i++) {
            bars[i] = new Rectangle(25, 2);
            bars[i].setFill(BAR_COLOR);
            bars[i].setX(-13);
            bars[i].setY(1-(i*5));
        }
        getChildren().addAll(bars);
      // setEffect(DropShadowBuilder.create().blurType(BlurType.TWO_PASS_BOX).radius(10).spread(0.4).color(Color.web("#b10000")).build());
    }
}
