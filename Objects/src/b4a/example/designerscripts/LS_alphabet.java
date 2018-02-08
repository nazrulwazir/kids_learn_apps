package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alphabet{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[alphabet/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="AutoScaleRate(0.3)"[alphabet/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
//BA.debugLineNum = 4;BA.debugLine="Button1.Bottom=15%y"[alphabet/General script]
views.get("button1").vw.setTop((int)((15d / 100 * height) - (views.get("button1").vw.getHeight())));
//BA.debugLineNum = 5;BA.debugLine="Button1.Left=5%y"[alphabet/General script]
views.get("button1").vw.setLeft((int)((5d / 100 * height)));
//BA.debugLineNum = 7;BA.debugLine="play_btn.Bottom=25%y"[alphabet/General script]
views.get("play_btn").vw.setTop((int)((25d / 100 * height) - (views.get("play_btn").vw.getHeight())));
//BA.debugLineNum = 8;BA.debugLine="play_btn.Left=100%y"[alphabet/General script]
views.get("play_btn").vw.setLeft((int)((100d / 100 * height)));
//BA.debugLineNum = 13;BA.debugLine="b3.Bottom=22%y"[alphabet/General script]
views.get("b3").vw.setTop((int)((22d / 100 * height) - (views.get("b3").vw.getHeight())));
//BA.debugLineNum = 14;BA.debugLine="b3.Left=15%y"[alphabet/General script]
views.get("b3").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 15;BA.debugLine="b3.Width=20%y"[alphabet/General script]
views.get("b3").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 16;BA.debugLine="b3.Height=20%y"[alphabet/General script]
views.get("b3").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 18;BA.debugLine="btnB.Bottom=22%y"[alphabet/General script]
views.get("btnb").vw.setTop((int)((22d / 100 * height) - (views.get("btnb").vw.getHeight())));
//BA.debugLineNum = 19;BA.debugLine="btnB.Left=28%y"[alphabet/General script]
views.get("btnb").vw.setLeft((int)((28d / 100 * height)));
//BA.debugLineNum = 20;BA.debugLine="btnB.Width=20%y"[alphabet/General script]
views.get("btnb").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 21;BA.debugLine="btnB.Height=20%y"[alphabet/General script]
views.get("btnb").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 24;BA.debugLine="btnC.Bottom=22%y"[alphabet/General script]
views.get("btnc").vw.setTop((int)((22d / 100 * height) - (views.get("btnc").vw.getHeight())));
//BA.debugLineNum = 25;BA.debugLine="btnC.Left=41%y"[alphabet/General script]
views.get("btnc").vw.setLeft((int)((41d / 100 * height)));
//BA.debugLineNum = 26;BA.debugLine="btnC.Width=20%y"[alphabet/General script]
views.get("btnc").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 27;BA.debugLine="btnC.Height=20%y"[alphabet/General script]
views.get("btnc").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 29;BA.debugLine="btnD.Bottom=22%y"[alphabet/General script]
views.get("btnd").vw.setTop((int)((22d / 100 * height) - (views.get("btnd").vw.getHeight())));
//BA.debugLineNum = 30;BA.debugLine="btnD.Left=54%y"[alphabet/General script]
views.get("btnd").vw.setLeft((int)((54d / 100 * height)));
//BA.debugLineNum = 31;BA.debugLine="btnD.Width=20%y"[alphabet/General script]
views.get("btnd").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 32;BA.debugLine="btnD.Height=20%y"[alphabet/General script]
views.get("btnd").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 34;BA.debugLine="btnE.Bottom=22%y"[alphabet/General script]
views.get("btne").vw.setTop((int)((22d / 100 * height) - (views.get("btne").vw.getHeight())));
//BA.debugLineNum = 35;BA.debugLine="btnE.Left=67%y"[alphabet/General script]
views.get("btne").vw.setLeft((int)((67d / 100 * height)));
//BA.debugLineNum = 36;BA.debugLine="btnE.Width=20%y"[alphabet/General script]
views.get("btne").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 37;BA.debugLine="btnE.Height=20%y"[alphabet/General script]
views.get("btne").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 39;BA.debugLine="btnF.Bottom=22%y"[alphabet/General script]
views.get("btnf").vw.setTop((int)((22d / 100 * height) - (views.get("btnf").vw.getHeight())));
//BA.debugLineNum = 40;BA.debugLine="btnF.Left=80%y"[alphabet/General script]
views.get("btnf").vw.setLeft((int)((80d / 100 * height)));
//BA.debugLineNum = 41;BA.debugLine="btnF.Width=20%y"[alphabet/General script]
views.get("btnf").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 42;BA.debugLine="btnF.Height=20%y"[alphabet/General script]
views.get("btnf").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 44;BA.debugLine="btnG.Bottom=38%y"[alphabet/General script]
views.get("btng").vw.setTop((int)((38d / 100 * height) - (views.get("btng").vw.getHeight())));
//BA.debugLineNum = 45;BA.debugLine="btnG.Left=15%y"[alphabet/General script]
views.get("btng").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 46;BA.debugLine="btnG.Width=20%y"[alphabet/General script]
views.get("btng").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 47;BA.debugLine="btnG.Height=20%y"[alphabet/General script]
views.get("btng").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 49;BA.debugLine="btnH.Bottom=38%y"[alphabet/General script]
views.get("btnh").vw.setTop((int)((38d / 100 * height) - (views.get("btnh").vw.getHeight())));
//BA.debugLineNum = 50;BA.debugLine="btnH.Left=28%y"[alphabet/General script]
views.get("btnh").vw.setLeft((int)((28d / 100 * height)));
//BA.debugLineNum = 51;BA.debugLine="btnH.Width=20%y"[alphabet/General script]
views.get("btnh").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 52;BA.debugLine="btnH.Height=20%y"[alphabet/General script]
views.get("btnh").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 54;BA.debugLine="btnI.Bottom=38%y"[alphabet/General script]
views.get("btni").vw.setTop((int)((38d / 100 * height) - (views.get("btni").vw.getHeight())));
//BA.debugLineNum = 55;BA.debugLine="btnI.Left=41%y"[alphabet/General script]
views.get("btni").vw.setLeft((int)((41d / 100 * height)));
//BA.debugLineNum = 56;BA.debugLine="btnI.Width=20%y"[alphabet/General script]
views.get("btni").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 57;BA.debugLine="btnI.Height=20%y"[alphabet/General script]
views.get("btni").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 59;BA.debugLine="btnJ.Bottom=38%y"[alphabet/General script]
views.get("btnj").vw.setTop((int)((38d / 100 * height) - (views.get("btnj").vw.getHeight())));
//BA.debugLineNum = 60;BA.debugLine="btnJ.Left=54%y"[alphabet/General script]
views.get("btnj").vw.setLeft((int)((54d / 100 * height)));
//BA.debugLineNum = 61;BA.debugLine="btnJ.Width=20%y"[alphabet/General script]
views.get("btnj").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 62;BA.debugLine="btnJ.Height=20%y"[alphabet/General script]
views.get("btnj").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 64;BA.debugLine="btnK.Bottom=38%y"[alphabet/General script]
views.get("btnk").vw.setTop((int)((38d / 100 * height) - (views.get("btnk").vw.getHeight())));
//BA.debugLineNum = 65;BA.debugLine="btnK.Left=67%y"[alphabet/General script]
views.get("btnk").vw.setLeft((int)((67d / 100 * height)));
//BA.debugLineNum = 66;BA.debugLine="btnK.Width=20%y"[alphabet/General script]
views.get("btnk").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 67;BA.debugLine="btnK.Height=20%y"[alphabet/General script]
views.get("btnk").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 69;BA.debugLine="btnL.Bottom=38%y"[alphabet/General script]
views.get("btnl").vw.setTop((int)((38d / 100 * height) - (views.get("btnl").vw.getHeight())));
//BA.debugLineNum = 70;BA.debugLine="btnL.Left=80%y"[alphabet/General script]
views.get("btnl").vw.setLeft((int)((80d / 100 * height)));
//BA.debugLineNum = 71;BA.debugLine="btnL.Width=20%y"[alphabet/General script]
views.get("btnl").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 72;BA.debugLine="btnL.Height=20%y"[alphabet/General script]
views.get("btnl").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 74;BA.debugLine="btnM.Bottom=54%y"[alphabet/General script]
views.get("btnm").vw.setTop((int)((54d / 100 * height) - (views.get("btnm").vw.getHeight())));
//BA.debugLineNum = 75;BA.debugLine="btnM.Left=15%y"[alphabet/General script]
views.get("btnm").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 76;BA.debugLine="btnM.Width=20%y"[alphabet/General script]
views.get("btnm").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 77;BA.debugLine="btnM.Height=20%y"[alphabet/General script]
views.get("btnm").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 79;BA.debugLine="btnN.Bottom=54%y"[alphabet/General script]
views.get("btnn").vw.setTop((int)((54d / 100 * height) - (views.get("btnn").vw.getHeight())));
//BA.debugLineNum = 80;BA.debugLine="btnN.Left=28%y"[alphabet/General script]
views.get("btnn").vw.setLeft((int)((28d / 100 * height)));
//BA.debugLineNum = 81;BA.debugLine="btnN.Width=20%y"[alphabet/General script]
views.get("btnn").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 82;BA.debugLine="btnN.Height=20%y"[alphabet/General script]
views.get("btnn").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 84;BA.debugLine="btnO.Bottom=54%y"[alphabet/General script]
views.get("btno").vw.setTop((int)((54d / 100 * height) - (views.get("btno").vw.getHeight())));
//BA.debugLineNum = 85;BA.debugLine="btnO.Left=41%y"[alphabet/General script]
views.get("btno").vw.setLeft((int)((41d / 100 * height)));
//BA.debugLineNum = 86;BA.debugLine="btnO.Width=20%y"[alphabet/General script]
views.get("btno").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 87;BA.debugLine="btnO.Height=20%y"[alphabet/General script]
views.get("btno").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 89;BA.debugLine="btnP.Bottom=54%y"[alphabet/General script]
views.get("btnp").vw.setTop((int)((54d / 100 * height) - (views.get("btnp").vw.getHeight())));
//BA.debugLineNum = 90;BA.debugLine="btnP.Left=54%y"[alphabet/General script]
views.get("btnp").vw.setLeft((int)((54d / 100 * height)));
//BA.debugLineNum = 91;BA.debugLine="btnP.Width=20%y"[alphabet/General script]
views.get("btnp").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 92;BA.debugLine="btnP.Height=20%y"[alphabet/General script]
views.get("btnp").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 95;BA.debugLine="btnQ.Bottom=54%y"[alphabet/General script]
views.get("btnq").vw.setTop((int)((54d / 100 * height) - (views.get("btnq").vw.getHeight())));
//BA.debugLineNum = 96;BA.debugLine="btnQ.Left=67%y"[alphabet/General script]
views.get("btnq").vw.setLeft((int)((67d / 100 * height)));
//BA.debugLineNum = 97;BA.debugLine="btnQ.Width=20%y"[alphabet/General script]
views.get("btnq").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 98;BA.debugLine="btnQ.Height=20%y"[alphabet/General script]
views.get("btnq").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 100;BA.debugLine="btnR.Bottom=54%y"[alphabet/General script]
views.get("btnr").vw.setTop((int)((54d / 100 * height) - (views.get("btnr").vw.getHeight())));
//BA.debugLineNum = 101;BA.debugLine="btnR.Left=80%y"[alphabet/General script]
views.get("btnr").vw.setLeft((int)((80d / 100 * height)));
//BA.debugLineNum = 102;BA.debugLine="btnR.Width=20%y"[alphabet/General script]
views.get("btnr").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 103;BA.debugLine="btnR.Height=20%y"[alphabet/General script]
views.get("btnr").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 105;BA.debugLine="btnS.Bottom=70%y"[alphabet/General script]
views.get("btns").vw.setTop((int)((70d / 100 * height) - (views.get("btns").vw.getHeight())));
//BA.debugLineNum = 106;BA.debugLine="btnS.Left=15%y"[alphabet/General script]
views.get("btns").vw.setLeft((int)((15d / 100 * height)));
//BA.debugLineNum = 107;BA.debugLine="btnS.Width=20%y"[alphabet/General script]
views.get("btns").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 108;BA.debugLine="btnS.Height=20%y"[alphabet/General script]
views.get("btns").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 110;BA.debugLine="btnT.Bottom=70%y"[alphabet/General script]
views.get("btnt").vw.setTop((int)((70d / 100 * height) - (views.get("btnt").vw.getHeight())));
//BA.debugLineNum = 111;BA.debugLine="btnT.Left=28%y"[alphabet/General script]
views.get("btnt").vw.setLeft((int)((28d / 100 * height)));
//BA.debugLineNum = 112;BA.debugLine="btnT.Width=20%y"[alphabet/General script]
views.get("btnt").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 113;BA.debugLine="btnT.Height=20%y"[alphabet/General script]
views.get("btnt").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 115;BA.debugLine="btnU.Bottom=70%y"[alphabet/General script]
views.get("btnu").vw.setTop((int)((70d / 100 * height) - (views.get("btnu").vw.getHeight())));
//BA.debugLineNum = 116;BA.debugLine="btnU.Left=41%y"[alphabet/General script]
views.get("btnu").vw.setLeft((int)((41d / 100 * height)));
//BA.debugLineNum = 117;BA.debugLine="btnU.Width=20%y"[alphabet/General script]
views.get("btnu").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 118;BA.debugLine="btnU.Height=20%y"[alphabet/General script]
views.get("btnu").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 120;BA.debugLine="btnV.Bottom=70%y"[alphabet/General script]
views.get("btnv").vw.setTop((int)((70d / 100 * height) - (views.get("btnv").vw.getHeight())));
//BA.debugLineNum = 121;BA.debugLine="btnV.Left=54%y"[alphabet/General script]
views.get("btnv").vw.setLeft((int)((54d / 100 * height)));
//BA.debugLineNum = 122;BA.debugLine="btnV.Width=20%y"[alphabet/General script]
views.get("btnv").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 123;BA.debugLine="btnV.Height=20%y"[alphabet/General script]
views.get("btnv").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 125;BA.debugLine="btnW.Bottom=70%y"[alphabet/General script]
views.get("btnw").vw.setTop((int)((70d / 100 * height) - (views.get("btnw").vw.getHeight())));
//BA.debugLineNum = 126;BA.debugLine="btnW.Left=67%y"[alphabet/General script]
views.get("btnw").vw.setLeft((int)((67d / 100 * height)));
//BA.debugLineNum = 127;BA.debugLine="btnW.Width=20%y"[alphabet/General script]
views.get("btnw").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 128;BA.debugLine="btnW.Height=20%y"[alphabet/General script]
views.get("btnw").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 131;BA.debugLine="btnX.Bottom=70%y"[alphabet/General script]
views.get("btnx").vw.setTop((int)((70d / 100 * height) - (views.get("btnx").vw.getHeight())));
//BA.debugLineNum = 132;BA.debugLine="btnX.Left=80%y"[alphabet/General script]
views.get("btnx").vw.setLeft((int)((80d / 100 * height)));
//BA.debugLineNum = 133;BA.debugLine="btnX.Width=20%y"[alphabet/General script]
views.get("btnx").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 134;BA.debugLine="btnX.Height=20%y"[alphabet/General script]
views.get("btnx").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 136;BA.debugLine="btnY.Bottom=86%y"[alphabet/General script]
views.get("btny").vw.setTop((int)((86d / 100 * height) - (views.get("btny").vw.getHeight())));
//BA.debugLineNum = 137;BA.debugLine="btnY.Left=41%y"[alphabet/General script]
views.get("btny").vw.setLeft((int)((41d / 100 * height)));
//BA.debugLineNum = 138;BA.debugLine="btnY.Width=20%y"[alphabet/General script]
views.get("btny").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 139;BA.debugLine="btnY.Height=20%y"[alphabet/General script]
views.get("btny").vw.setHeight((int)((20d / 100 * height)));
//BA.debugLineNum = 141;BA.debugLine="btnZ.Bottom=86%y"[alphabet/General script]
views.get("btnz").vw.setTop((int)((86d / 100 * height) - (views.get("btnz").vw.getHeight())));
//BA.debugLineNum = 142;BA.debugLine="btnZ.Left=54%y"[alphabet/General script]
views.get("btnz").vw.setLeft((int)((54d / 100 * height)));
//BA.debugLineNum = 143;BA.debugLine="btnZ.Width=20%y"[alphabet/General script]
views.get("btnz").vw.setWidth((int)((20d / 100 * height)));
//BA.debugLineNum = 144;BA.debugLine="btnZ.Height=20%y"[alphabet/General script]
views.get("btnz").vw.setHeight((int)((20d / 100 * height)));

}
}