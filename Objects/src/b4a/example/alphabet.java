package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class alphabet extends Activity implements B4AActivity{
	public static alphabet mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.alphabet");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (alphabet).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.alphabet");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.alphabet", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (alphabet) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (alphabet) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return alphabet.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (alphabet) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (alphabet) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.obejcts.TTS _tts = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _clicksound = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b7 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _b = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _c = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _d = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _e = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _f = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _g = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _h = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _i = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _j = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _k = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _l = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _m = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _n = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _o = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _p = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _q = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _r = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _s = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _t = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _u = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _v = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _w = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _x = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _y = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _z = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnb = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnc = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btne = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnf = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btng = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnh = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btni = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnj = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnk = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnl = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnm = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnn = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btno = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnq = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnr = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btns = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnt = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnv = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnw = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnx = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btny = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnz = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _iplay = null;
public anywheresoftware.b4a.objects.ButtonWrapper _play_btn = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_clear = null;
public b4a.example.main _main = null;
public b4a.example.menu _menu = null;
public b4a.example.number _number = null;
public b4a.example.number_writing _number_writing = null;
public b4a.example.cat_alphabet _cat_alphabet = null;
public b4a.example.cat_num _cat_num = null;
public b4a.example.cat_fun _cat_fun = null;
public b4a.example.scramble _scramble = null;
public b4a.example.writing _writing = null;
public b4a.example.memory _memory = null;
public b4a.example.kid_video _kid_video = null;
public b4a.example.ayam _ayam = null;
public b4a.example.quiz _quiz = null;
public b4a.example.q2 _q2 = null;
public b4a.example.q3 _q3 = null;
public b4a.example.q4 _q4 = null;
public b4a.example.q5 _q5 = null;
public b4a.example.finish _finish = null;
public b4a.example.pisang _pisang = null;
public b4a.example.bangau _bangau = null;
public b4a.example.intro _intro = null;
public b4a.example.categories _categories = null;
public b4a.example.home _home = null;
public b4a.example.about _about = null;
public b4a.example.video _video = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 122;BA.debugLine="Activity.LoadLayout(\"alphabet\")";
mostCurrent._activity.LoadLayout("alphabet",mostCurrent.activityBA);
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 135;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 136;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 137;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 138;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(File";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"apple.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 143;BA.debugLine="a.Play";
mostCurrent._a.Play();
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _btnb_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 148;BA.debugLine="Sub btnB_Click";
 //BA.debugLineNum = 149;BA.debugLine="b.Play";
mostCurrent._b.Play();
 //BA.debugLineNum = 151;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 152;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 155;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 156;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ball.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _btnc_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 165;BA.debugLine="Sub btnC_Click";
 //BA.debugLineNum = 166;BA.debugLine="c.Play";
mostCurrent._c.Play();
 //BA.debugLineNum = 168;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 169;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 172;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 173;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cat.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _btnd_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 181;BA.debugLine="Sub btnD_Click";
 //BA.debugLineNum = 183;BA.debugLine="d.Play";
mostCurrent._d.Play();
 //BA.debugLineNum = 185;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 186;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 189;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 190;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"dolphin.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _btne_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 197;BA.debugLine="Sub btnE_Click";
 //BA.debugLineNum = 199;BA.debugLine="e.Play";
mostCurrent._e.Play();
 //BA.debugLineNum = 201;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 202;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 205;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 206;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"elephant.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 211;BA.debugLine="End Sub";
return "";
}
public static String  _btnf_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 213;BA.debugLine="Sub btnF_Click";
 //BA.debugLineNum = 214;BA.debugLine="f.Play";
mostCurrent._f.Play();
 //BA.debugLineNum = 217;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 218;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 221;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 222;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"frog.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _btng_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 229;BA.debugLine="Sub btnG_Click";
 //BA.debugLineNum = 230;BA.debugLine="g.Play";
mostCurrent._g.Play();
 //BA.debugLineNum = 231;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 232;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 235;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 236;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"giraffe.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _btnh_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 242;BA.debugLine="Sub btnH_Click";
 //BA.debugLineNum = 243;BA.debugLine="h.Play";
mostCurrent._h.Play();
 //BA.debugLineNum = 244;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 245;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 248;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 249;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"house.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _btni_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 255;BA.debugLine="Sub btnI_Click";
 //BA.debugLineNum = 256;BA.debugLine="i.Play";
mostCurrent._i.Play();
 //BA.debugLineNum = 257;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 258;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 261;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 262;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icecream.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _btnj_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 269;BA.debugLine="Sub btnJ_Click";
 //BA.debugLineNum = 271;BA.debugLine="j.Play";
mostCurrent._j.Play();
 //BA.debugLineNum = 272;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 273;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 276;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 277;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"jug.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static String  _btnk_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 284;BA.debugLine="Sub btnK_Click";
 //BA.debugLineNum = 285;BA.debugLine="k.Play";
mostCurrent._k.Play();
 //BA.debugLineNum = 286;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 287;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 290;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 291;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"kites.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _btnl_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 298;BA.debugLine="Sub btnL_Click";
 //BA.debugLineNum = 299;BA.debugLine="l.Play";
mostCurrent._l.Play();
 //BA.debugLineNum = 300;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 301;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 304;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 305;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"leaf.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public static String  _btnm_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 312;BA.debugLine="Sub btnM_Click";
 //BA.debugLineNum = 313;BA.debugLine="m.Play";
mostCurrent._m.Play();
 //BA.debugLineNum = 314;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 315;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 318;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 319;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mouse.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _btnn_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 326;BA.debugLine="Sub btnN_Click";
 //BA.debugLineNum = 327;BA.debugLine="n.Play";
mostCurrent._n.Play();
 //BA.debugLineNum = 328;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 329;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 332;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 333;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nest.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 337;BA.debugLine="End Sub";
return "";
}
public static String  _btno_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 339;BA.debugLine="Sub btnO_Click";
 //BA.debugLineNum = 340;BA.debugLine="o.Play";
mostCurrent._o.Play();
 //BA.debugLineNum = 341;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 342;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 345;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 346;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"octupos.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public static String  _btnp_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 353;BA.debugLine="Sub btnP_Click";
 //BA.debugLineNum = 355;BA.debugLine="p.Play";
mostCurrent._p.Play();
 //BA.debugLineNum = 356;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 357;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 360;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 361;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pinapple.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _btnq_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 368;BA.debugLine="Sub btnQ_Click";
 //BA.debugLineNum = 369;BA.debugLine="q.Play";
mostCurrent._q.Play();
 //BA.debugLineNum = 370;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 371;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 374;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 375;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"queen.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _btnr_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 382;BA.debugLine="Sub btnR_Click";
 //BA.debugLineNum = 383;BA.debugLine="r.Play";
mostCurrent._r.Play();
 //BA.debugLineNum = 384;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 385;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 388;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 389;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"rainbow.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _btns_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 396;BA.debugLine="Sub btnS_Click";
 //BA.debugLineNum = 397;BA.debugLine="s.Play";
mostCurrent._s.Play();
 //BA.debugLineNum = 398;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 399;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 402;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 403;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sun.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 408;BA.debugLine="End Sub";
return "";
}
public static String  _btnt_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 410;BA.debugLine="Sub btnT_Click";
 //BA.debugLineNum = 411;BA.debugLine="t.Play";
mostCurrent._t.Play();
 //BA.debugLineNum = 412;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 413;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 416;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 417;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tree.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return "";
}
public static String  _btnu_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 423;BA.debugLine="Sub btnU_Click";
 //BA.debugLineNum = 424;BA.debugLine="u.Play";
mostCurrent._u.Play();
 //BA.debugLineNum = 425;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 426;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 429;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 430;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"umbrella.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _btnv_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 437;BA.debugLine="Sub btnV_Click";
 //BA.debugLineNum = 438;BA.debugLine="v.Play";
mostCurrent._v.Play();
 //BA.debugLineNum = 439;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 440;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 443;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 444;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vest.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 449;BA.debugLine="End Sub";
return "";
}
public static String  _btnw_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 451;BA.debugLine="Sub btnW_Click";
 //BA.debugLineNum = 452;BA.debugLine="w.Play";
mostCurrent._w.Play();
 //BA.debugLineNum = 453;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 454;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 457;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 458;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"watermelon.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return "";
}
public static String  _btnx_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 464;BA.debugLine="Sub btnX_Click";
 //BA.debugLineNum = 465;BA.debugLine="x.Play";
mostCurrent._x.Play();
 //BA.debugLineNum = 466;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 467;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 470;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 471;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"xray.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 476;BA.debugLine="End Sub";
return "";
}
public static String  _btny_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 478;BA.debugLine="Sub btnY_Click";
 //BA.debugLineNum = 479;BA.debugLine="y.Play";
mostCurrent._y.Play();
 //BA.debugLineNum = 480;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 481;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 484;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 485;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"yoyo.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 490;BA.debugLine="End Sub";
return "";
}
public static String  _btnz_click() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 492;BA.debugLine="Sub btnZ_Click";
 //BA.debugLineNum = 493;BA.debugLine="z.Play";
mostCurrent._z.Play();
 //BA.debugLineNum = 494;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 495;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 498;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 499;BA.debugLine="panels.SetBackgroundImage(LoadBitmapSample(Fil";
_panels.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"zebra.png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 504;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 506;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 507;BA.debugLine="introwel.Stop";
mostCurrent._introwel.Stop();
 //BA.debugLineNum = 508;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim b1,b2,b3,b4,b5,b6,b7 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b4 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b5 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b6 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Public a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v";
mostCurrent._a = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._b = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._c = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._d = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._e = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._f = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._g = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._h = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._i = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._j = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._k = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._l = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._m = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._n = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._o = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._p = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._q = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._r = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._s = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._t = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._u = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._v = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._w = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._x = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._y = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._z = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="TTS.Initialize(\"tts\")";
_tts.Initialize(processBA,"tts");
 //BA.debugLineNum = 25;BA.debugLine="Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,b";
mostCurrent._btnb = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnc = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnd = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btne = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnf = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btng = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnh = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btni = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnj = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnk = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnl = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnm = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnn = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btno = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnp = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnq = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnr = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btns = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnt = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnu = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnv = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnw = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnx = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btny = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnz = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Iplay As Button";
mostCurrent._iplay = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="introwel.Initialize2(\"introwel\")";
mostCurrent._introwel.Initialize2(processBA,"introwel");
 //BA.debugLineNum = 34;BA.debugLine="a.Initialize2(\"a\")";
mostCurrent._a.Initialize2(processBA,"a");
 //BA.debugLineNum = 35;BA.debugLine="a.Load(File.DirAssets, \"A.mp3\")";
mostCurrent._a.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"A.mp3");
 //BA.debugLineNum = 37;BA.debugLine="b.Initialize2(\"b\")";
mostCurrent._b.Initialize2(processBA,"b");
 //BA.debugLineNum = 38;BA.debugLine="b.Load(File.DirAssets, \"B.mp3\")";
mostCurrent._b.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.mp3");
 //BA.debugLineNum = 40;BA.debugLine="c.Initialize2(\"c\")";
mostCurrent._c.Initialize2(processBA,"c");
 //BA.debugLineNum = 41;BA.debugLine="c.Load(File.DirAssets, \"C.mp3\")";
mostCurrent._c.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"C.mp3");
 //BA.debugLineNum = 43;BA.debugLine="d.Initialize2(\"d\")";
mostCurrent._d.Initialize2(processBA,"d");
 //BA.debugLineNum = 44;BA.debugLine="d.Load(File.DirAssets, \"D.mp3\")";
mostCurrent._d.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"D.mp3");
 //BA.debugLineNum = 46;BA.debugLine="e.Initialize2(\"e\")";
mostCurrent._e.Initialize2(processBA,"e");
 //BA.debugLineNum = 47;BA.debugLine="e.Load(File.DirAssets, \"E.mp3\")";
mostCurrent._e.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"E.mp3");
 //BA.debugLineNum = 49;BA.debugLine="f.Initialize2(\"f\")";
mostCurrent._f.Initialize2(processBA,"f");
 //BA.debugLineNum = 50;BA.debugLine="f.Load(File.DirAssets, \"F.mp3\")";
mostCurrent._f.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"F.mp3");
 //BA.debugLineNum = 52;BA.debugLine="g.Initialize2(\"g\")";
mostCurrent._g.Initialize2(processBA,"g");
 //BA.debugLineNum = 53;BA.debugLine="g.Load(File.DirAssets, \"G.mp3\")";
mostCurrent._g.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"G.mp3");
 //BA.debugLineNum = 55;BA.debugLine="h.Initialize2(\"h\")";
mostCurrent._h.Initialize2(processBA,"h");
 //BA.debugLineNum = 56;BA.debugLine="h.Load(File.DirAssets, \"H.mp3\")";
mostCurrent._h.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"H.mp3");
 //BA.debugLineNum = 58;BA.debugLine="i.Initialize2(\"i\")";
mostCurrent._i.Initialize2(processBA,"i");
 //BA.debugLineNum = 59;BA.debugLine="i.Load(File.DirAssets, \"I.mp3\")";
mostCurrent._i.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"I.mp3");
 //BA.debugLineNum = 61;BA.debugLine="j.Initialize2(\"j\")";
mostCurrent._j.Initialize2(processBA,"j");
 //BA.debugLineNum = 62;BA.debugLine="j.Load(File.DirAssets, \"J.mp3\")";
mostCurrent._j.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"J.mp3");
 //BA.debugLineNum = 64;BA.debugLine="k.Initialize2(\"k\")";
mostCurrent._k.Initialize2(processBA,"k");
 //BA.debugLineNum = 65;BA.debugLine="k.Load(File.DirAssets, \"K.mp3\")";
mostCurrent._k.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"K.mp3");
 //BA.debugLineNum = 67;BA.debugLine="l.Initialize2(\"l\")";
mostCurrent._l.Initialize2(processBA,"l");
 //BA.debugLineNum = 68;BA.debugLine="l.Load(File.DirAssets, \"L.mp3\")";
mostCurrent._l.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"L.mp3");
 //BA.debugLineNum = 70;BA.debugLine="m.Initialize2(\"m\")";
mostCurrent._m.Initialize2(processBA,"m");
 //BA.debugLineNum = 71;BA.debugLine="m.Load(File.DirAssets, \"M.mp3\")";
mostCurrent._m.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"M.mp3");
 //BA.debugLineNum = 73;BA.debugLine="n.Initialize2(\"n\")";
mostCurrent._n.Initialize2(processBA,"n");
 //BA.debugLineNum = 74;BA.debugLine="n.Load(File.DirAssets, \"N.mp3\")";
mostCurrent._n.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"N.mp3");
 //BA.debugLineNum = 76;BA.debugLine="o.Initialize2(\"o\")";
mostCurrent._o.Initialize2(processBA,"o");
 //BA.debugLineNum = 77;BA.debugLine="o.Load(File.DirAssets, \"O.mp3\")";
mostCurrent._o.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"O.mp3");
 //BA.debugLineNum = 79;BA.debugLine="p.Initialize2(\"p\")";
mostCurrent._p.Initialize2(processBA,"p");
 //BA.debugLineNum = 80;BA.debugLine="p.Load(File.DirAssets, \"P.mp3\")";
mostCurrent._p.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"P.mp3");
 //BA.debugLineNum = 82;BA.debugLine="q.Initialize2(\"q\")";
mostCurrent._q.Initialize2(processBA,"q");
 //BA.debugLineNum = 83;BA.debugLine="q.Load(File.DirAssets, \"Q.mp3\")";
mostCurrent._q.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Q.mp3");
 //BA.debugLineNum = 85;BA.debugLine="r.Initialize2(\"r\")";
mostCurrent._r.Initialize2(processBA,"r");
 //BA.debugLineNum = 86;BA.debugLine="r.Load(File.DirAssets, \"R.mp3\")";
mostCurrent._r.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"R.mp3");
 //BA.debugLineNum = 88;BA.debugLine="s.Initialize2(\"s\")";
mostCurrent._s.Initialize2(processBA,"s");
 //BA.debugLineNum = 89;BA.debugLine="s.Load(File.DirAssets, \"S.mp3\")";
mostCurrent._s.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"S.mp3");
 //BA.debugLineNum = 91;BA.debugLine="t.Initialize2(\"t\")";
mostCurrent._t.Initialize2(processBA,"t");
 //BA.debugLineNum = 92;BA.debugLine="t.Load(File.DirAssets, \"T.mp3\")";
mostCurrent._t.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"T.mp3");
 //BA.debugLineNum = 94;BA.debugLine="u.Initialize2(\"u\")";
mostCurrent._u.Initialize2(processBA,"u");
 //BA.debugLineNum = 95;BA.debugLine="u.Load(File.DirAssets, \"U.mp3\")";
mostCurrent._u.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"U.mp3");
 //BA.debugLineNum = 97;BA.debugLine="v.Initialize2(\"v\")";
mostCurrent._v.Initialize2(processBA,"v");
 //BA.debugLineNum = 98;BA.debugLine="v.Load(File.DirAssets, \"V.mp3\")";
mostCurrent._v.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"V.mp3");
 //BA.debugLineNum = 100;BA.debugLine="w.Initialize2(\"w\")";
mostCurrent._w.Initialize2(processBA,"w");
 //BA.debugLineNum = 101;BA.debugLine="w.Load(File.DirAssets, \"W.mp3\")";
mostCurrent._w.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"W.mp3");
 //BA.debugLineNum = 103;BA.debugLine="x.Initialize2(\"x\")";
mostCurrent._x.Initialize2(processBA,"x");
 //BA.debugLineNum = 104;BA.debugLine="x.Load(File.DirAssets, \"X.mp3\")";
mostCurrent._x.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"X.mp3");
 //BA.debugLineNum = 106;BA.debugLine="y.Initialize2(\"y\")";
mostCurrent._y.Initialize2(processBA,"y");
 //BA.debugLineNum = 107;BA.debugLine="y.Load(File.DirAssets, \"Y.mp3\")";
mostCurrent._y.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Y.mp3");
 //BA.debugLineNum = 109;BA.debugLine="z.Initialize2(\"z\")";
mostCurrent._z.Initialize2(processBA,"z");
 //BA.debugLineNum = 110;BA.debugLine="z.Load(File.DirAssets, \"Z.mp3\")";
mostCurrent._z.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Z.mp3");
 //BA.debugLineNum = 112;BA.debugLine="Private play_btn As Button";
mostCurrent._play_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private btn_clear As Button";
mostCurrent._btn_clear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _play_btn_click() throws Exception{
 //BA.debugLineNum = 512;BA.debugLine="Sub play_btn_Click";
 //BA.debugLineNum = 513;BA.debugLine="introwel.Load(File.DirAssets, \"abc.mp3\")";
mostCurrent._introwel.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"abc.mp3");
 //BA.debugLineNum = 514;BA.debugLine="introwel.Play";
mostCurrent._introwel.Play();
 //BA.debugLineNum = 515;BA.debugLine="introwel.Looping=True";
mostCurrent._introwel.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 516;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 9;BA.debugLine="Public clicksound As MediaPlayer";
_clicksound = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
}
