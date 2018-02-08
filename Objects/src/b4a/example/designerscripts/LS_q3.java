package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_q3{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("button2").vw.setTop((int)((25d / 100 * height) - (views.get("button2").vw.getHeight())));
views.get("button2").vw.setLeft((int)((15d / 100 * height)));
views.get("label4").vw.setTop((int)((25d / 100 * height) - (views.get("label4").vw.getHeight())));
views.get("label4").vw.setLeft((int)((30d / 100 * height)));
views.get("imageview1").vw.setTop((int)((50d / 100 * height) - (views.get("imageview1").vw.getHeight())));
views.get("imageview1").vw.setLeft((int)((45d / 100 * height)));
views.get("btnspeak1").vw.setTop((int)((65d / 100 * height) - (views.get("btnspeak1").vw.getHeight())));
views.get("btnspeak1").vw.setLeft((int)((50d / 100 * height)));
views.get("label1").vw.setTop((int)((30d / 100 * height) - (views.get("label1").vw.getHeight())));
views.get("label1").vw.setLeft((int)((80d / 100 * height)));
views.get("panel1").vw.setTop((int)((65d / 100 * height) - (views.get("panel1").vw.getHeight())));
views.get("panel1").vw.setLeft((int)((75d / 100 * height)));
views.get("btnsubmit").vw.setTop((int)((85d / 100 * height) - (views.get("btnsubmit").vw.getHeight())));
views.get("btnsubmit").vw.setLeft((int)((80d / 100 * height)));
views.get("btnnext").vw.setTop((int)((80d / 100 * height) - (views.get("btnnext").vw.getHeight())));
views.get("btnnext").vw.setLeft((int)((120d / 100 * height)));
views.get("btnprev").vw.setTop((int)((80d / 100 * height) - (views.get("btnprev").vw.getHeight())));
views.get("btnprev").vw.setLeft((int)((40d / 100 * height)));

}
}