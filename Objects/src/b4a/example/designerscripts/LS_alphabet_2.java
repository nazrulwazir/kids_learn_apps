package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alphabet_2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("button1").vw.setTop((int)((25d / 100 * height) - (views.get("button1").vw.getHeight())));
views.get("button1").vw.setLeft((int)((15d / 100 * height)));
views.get("btn_alphabet").vw.setLeft((int)((40d / 100 * height)));
views.get("btn_alphabet").vw.setTop((int)((60d / 100 * height) - (views.get("btn_alphabet").vw.getHeight())));
views.get("btn_writing").vw.setLeft((int)((80d / 100 * height)));
views.get("btn_writing").vw.setTop((int)((60d / 100 * height) - (views.get("btn_writing").vw.getHeight())));
views.get("label1").vw.setLeft((int)((50d / 100 * height)));
views.get("label1").vw.setTop((int)((30d / 100 * height) - (views.get("label1").vw.getHeight())));

}
}