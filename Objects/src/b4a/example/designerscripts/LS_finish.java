package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_finish{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("label1").vw.setTop((int)((60d / 100 * height) - (views.get("label1").vw.getHeight())));
views.get("label1").vw.setLeft((int)((25d / 100 * height)));
views.get("button2").vw.setTop((int)((25d / 100 * height) - (views.get("button2").vw.getHeight())));
views.get("button2").vw.setLeft((int)((15d / 100 * height)));

}
}