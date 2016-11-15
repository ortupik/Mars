package components;




import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;


public class VolumeAnim extends Parent {
    private final static Color BAR_COLOR = Color.GREENYELLOW;
    private static int position;
    private static Rectangle[] volumeBars = new Rectangle[100];
    
    private DoubleProperty value = new SimpleDoubleProperty(0) {
        @Override protected void invalidated() {
            super.invalidated();
            double lastBar = get()*volumeBars.length;
            for(int i=0; i<volumeBars.length;i++) {
               volumeBars[i].setVisible(i < lastBar);
            }
        }
    };
    public void setValue(double v) { 
    	value.set(v);
    	}
    public double getValue() { return value.get(); }
    public DoubleProperty valueProperty() { return value; }
    
    public VolumeAnim() {
   
    int no1 =  new MediaShare().getvolume()/2;
    System.out.println("volimer "+no1);
    	int no= 100;
    	if(no==-1){
    	    no = 100;
    	}
    	Stop stops1 [] =new Stop []{ new Stop(0.0f, Color.YELLOW),new Stop(0.8f, Color.GREENYELLOW)};   	
    	LinearGradient l1 = new LinearGradient(0, 0,  0,1,true,CycleMethod.REFLECT,stops1);
    	

      //  LinearGradient l1 = new LinearGradient(0.0, 0.0, 0.0, 1.0,true,CycleMethod.NO_CYCLE,new Stop(0.0f, Color.GREENYELLOW), new Stop(Color.YELLOW),new Stop(0.3f, BAR_COLOR), new Stop(0.75f,Color.YELLOW));

      for(int i=0; i<no;i++) {

            volumeBars[i] = new Rectangle(25, 4); 
         //   volumeBars[i].setStroke(BAR_COLOR);
          if(i < 15){
            	 volumeBars[i].setFill(BAR_COLOR);
          } else if(i >15 && i <=20){
        	  volumeBars[i].setFill(BAR_COLOR);
            }else if(i >20 && i <=50){
            	 volumeBars[i].setFill(Color.YELLOW);
            }else if (i > 50 && i <= 80){
            	 volumeBars[i].setFill(Color.ORANGE);
            }else if (i >80){
            	 volumeBars[i].setFill(Color.RED);
            }else{
            	 volumeBars[i].setFill(BAR_COLOR);
            }
      
            volumeBars[i].setX(0);
            volumeBars[i].setY(1-(i*5));
        } 
     
        getChildren().addAll(volumeBars);
       
     
       setEffect(DropShadowBuilder.create().blurType(BlurType.THREE_PASS_BOX).radius(20).spread(0.5).color(Color.CHOCOLATE).build());
    }

}
