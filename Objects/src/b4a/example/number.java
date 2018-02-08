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

public class number extends Activity implements B4AActivity{
	public static number mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.number");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (number).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.number");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.number", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (number) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (number) Resume **");
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
		return number.class;
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
        BA.LogInfo("** Activity (number) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (number) Resume **");
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
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a1 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a2 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a3 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a4 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a5 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a6 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a7 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a8 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a9 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _a10 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i10 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i11 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i12 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i13 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i14 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i15 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i16 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i17 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i18 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i19 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i20 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i21 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i22 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i23 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i24 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i25 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i26 = null;
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
public b4a.example.number_writing _number_writing = null;
public b4a.example.alphabet _alphabet = null;
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
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="Activity.LoadLayout(\"number\")";
mostCurrent._activity.LoadLayout("number",mostCurrent.activityBA);
 //BA.debugLineNum = 45;BA.debugLine="a1.Initialize2(\"a1\")";
mostCurrent._a1.Initialize2(processBA,"a1");
 //BA.debugLineNum = 46;BA.debugLine="a1.Load(File.DirAssets, \"1.mp3\")";
mostCurrent._a1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"1.mp3");
 //BA.debugLineNum = 48;BA.debugLine="a2.Initialize2(\"a2\")";
mostCurrent._a2.Initialize2(processBA,"a2");
 //BA.debugLineNum = 49;BA.debugLine="a2.Load(File.DirAssets, \"2.mp3\")";
mostCurrent._a2.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"2.mp3");
 //BA.debugLineNum = 51;BA.debugLine="a3.Initialize2(\"a3\")";
mostCurrent._a3.Initialize2(processBA,"a3");
 //BA.debugLineNum = 52;BA.debugLine="a3.Load(File.DirAssets, \"3.mp3\")";
mostCurrent._a3.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.mp3");
 //BA.debugLineNum = 54;BA.debugLine="a4.Initialize2(\"a4\")";
mostCurrent._a4.Initialize2(processBA,"a4");
 //BA.debugLineNum = 55;BA.debugLine="a4.Load(File.DirAssets, \"4.mp3\")";
mostCurrent._a4.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"4.mp3");
 //BA.debugLineNum = 57;BA.debugLine="a5.Initialize2(\"a5\")";
mostCurrent._a5.Initialize2(processBA,"a5");
 //BA.debugLineNum = 58;BA.debugLine="a5.Load(File.DirAssets, \"5.mp3\")";
mostCurrent._a5.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"5.mp3");
 //BA.debugLineNum = 60;BA.debugLine="a6.Initialize2(\"a6\")";
mostCurrent._a6.Initialize2(processBA,"a6");
 //BA.debugLineNum = 61;BA.debugLine="a6.Load(File.DirAssets, \"6.mp3\")";
mostCurrent._a6.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"6.mp3");
 //BA.debugLineNum = 63;BA.debugLine="a7.Initialize2(\"a7\")";
mostCurrent._a7.Initialize2(processBA,"a7");
 //BA.debugLineNum = 64;BA.debugLine="a7.Load(File.DirAssets, \"7.mp3\")";
mostCurrent._a7.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"7.mp3");
 //BA.debugLineNum = 66;BA.debugLine="a8.Initialize2(\"a8\")";
mostCurrent._a8.Initialize2(processBA,"a8");
 //BA.debugLineNum = 67;BA.debugLine="a8.Load(File.DirAssets, \"8.mp3\")";
mostCurrent._a8.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.mp3");
 //BA.debugLineNum = 69;BA.debugLine="a9.Initialize2(\"a9\")";
mostCurrent._a9.Initialize2(processBA,"a9");
 //BA.debugLineNum = 70;BA.debugLine="a9.Load(File.DirAssets, \"9.mp3\")";
mostCurrent._a9.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"9.mp3");
 //BA.debugLineNum = 72;BA.debugLine="a10.Initialize2(\"a10\")";
mostCurrent._a10.Initialize2(processBA,"a10");
 //BA.debugLineNum = 73;BA.debugLine="a10.Load(File.DirAssets, \"10.mp3\")";
mostCurrent._a10.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"10.mp3");
 //BA.debugLineNum = 75;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 76;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"intro.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro.mp3");
 //BA.debugLineNum = 77;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
_timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 78;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 81;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 82;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 83;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 87;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 88;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 89;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 104;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 105;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 106;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 97;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 98;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 99;BA.debugLine="timer1_Tick 'don't wait one second for the UI to";
_timer1_tick();
 //BA.debugLineNum = 100;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 129;BA.debugLine="a1.Play";
mostCurrent._a1.Play();
 //BA.debugLineNum = 131;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 132;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 134;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 135;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="i1.Visible=True";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _btnb_click() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub btnB_Click";
 //BA.debugLineNum = 151;BA.debugLine="a2.Play";
mostCurrent._a2.Play();
 //BA.debugLineNum = 155;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 159;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 160;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 161;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 163;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 165;BA.debugLine="i2.Visible=True";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _btnc_click() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub btnC_Click";
 //BA.debugLineNum = 177;BA.debugLine="a3.Play";
mostCurrent._a3.Play();
 //BA.debugLineNum = 180;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 181;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 183;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 184;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 185;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 186;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 187;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 188;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="i3.Visible=True";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static String  _btnd_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub btnD_Click";
 //BA.debugLineNum = 201;BA.debugLine="a4.Play";
mostCurrent._a4.Play();
 //BA.debugLineNum = 204;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 206;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 210;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 211;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 214;BA.debugLine="i4.Visible=True";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static String  _btne_click() throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Sub btnE_Click";
 //BA.debugLineNum = 224;BA.debugLine="a5.Play";
mostCurrent._a5.Play();
 //BA.debugLineNum = 227;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 228;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 230;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 231;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 232;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 234;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 235;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 237;BA.debugLine="i5.Visible=True";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _btnf_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub btnF_Click";
 //BA.debugLineNum = 248;BA.debugLine="a10.Play";
mostCurrent._a10.Play();
 //BA.debugLineNum = 252;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 254;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 255;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 256;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 257;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 258;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 260;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="i6.Visible=True";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _btng_click() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub btnG_Click";
 //BA.debugLineNum = 271;BA.debugLine="a6.Play";
mostCurrent._a6.Play();
 //BA.debugLineNum = 273;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="i7.Visible=True";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 287;BA.debugLine="End Sub";
return "";
}
public static String  _btnh_click() throws Exception{
 //BA.debugLineNum = 289;BA.debugLine="Sub btnH_Click";
 //BA.debugLineNum = 290;BA.debugLine="a7.Play";
mostCurrent._a7.Play();
 //BA.debugLineNum = 291;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 293;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 296;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="i8.Visible=True";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static String  _btni_click() throws Exception{
 //BA.debugLineNum = 308;BA.debugLine="Sub btnI_Click";
 //BA.debugLineNum = 309;BA.debugLine="a8.Play";
mostCurrent._a8.Play();
 //BA.debugLineNum = 311;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 312;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 315;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 316;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 317;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 318;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 319;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 321;BA.debugLine="i9.Visible=True";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _btnj_click() throws Exception{
 //BA.debugLineNum = 327;BA.debugLine="Sub btnJ_Click";
 //BA.debugLineNum = 328;BA.debugLine="a9.Play";
mostCurrent._a9.Play();
 //BA.debugLineNum = 329;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 330;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 331;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 332;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 333;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 334;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 335;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 336;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 337;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 339;BA.debugLine="i10.Visible=True";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 347;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 349;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 117;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 118;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 119;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 120;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 121;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 122;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Public a1,a2,a3,a4,a5,a6,a7,a8,a9,a10 As MediaPla";
mostCurrent._a1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a2 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a3 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a4 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a5 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a6 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a7 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a8 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a9 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
mostCurrent._a10 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim b1,b2,b3,b4,b5,b6,b7 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b4 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b5 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b6 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i1";
mostCurrent._i1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i16 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i17 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i18 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i19 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i20 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i21 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i22 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i23 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i24 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i25 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i26 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="TTS.Initialize(\"tts\")";
_tts.Initialize(processBA,"tts");
 //BA.debugLineNum = 26;BA.debugLine="Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,b";
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
 //BA.debugLineNum = 27;BA.debugLine="Private i2 As ImageView";
mostCurrent._i2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Iplay As Button";
mostCurrent._iplay = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private play_btn As Button";
mostCurrent._play_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btn_clear As Button";
mostCurrent._btn_clear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 9;BA.debugLine="Public MediaPlayer1 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Dim timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 110;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
}
