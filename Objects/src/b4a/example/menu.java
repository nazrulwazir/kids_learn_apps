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

public class menu extends Activity implements B4AActivity{
	public static menu mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.menu");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (menu).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.menu");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.menu", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (menu) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (menu) Resume **");
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
		return menu.class;
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
        BA.LogInfo("** Activity (menu) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (menu) Resume **");
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
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _s1 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _s2 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _s3 = null;
public static int _a = 0;
public static int _b = 0;
public static int _c = 0;
public static anywheresoftware.b4a.objects.B4AException _ex = null;
public static anywheresoftware.b4a.obejcts.TTS _tts = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer2 = null;
public static anywheresoftware.b4a.objects.Timer _timer3 = null;
public static anywheresoftware.b4a.objects.Timer _timer4 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnscramble = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnalphabet = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmemory = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnabout = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_start = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfun = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnumber = null;
public basic4x.ir.mojtaba.irbuttonpro.IRLVEBeans _ireatbeans1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolume = null;
public b4a.example.main _main = null;
public b4a.example.number _number = null;
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
 //BA.debugLineNum = 43;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 44;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"intro.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro.mp3");
 //BA.debugLineNum = 45;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
_timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 46;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 48;BA.debugLine="s1.Initialize2(\"s1\")";
_s1.Initialize2(processBA,"s1");
 //BA.debugLineNum = 49;BA.debugLine="s1.Load(File.DirAssets, \"Kids Alphabet.mp3\")";
_s1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Kids Alphabet.mp3");
 //BA.debugLineNum = 52;BA.debugLine="s2.Initialize2(\"s2\")";
_s2.Initialize2(processBA,"s2");
 //BA.debugLineNum = 53;BA.debugLine="s2.Load(File.DirAssets, \"Kids Fun.mp3\")";
_s2.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Kids Fun.mp3");
 //BA.debugLineNum = 56;BA.debugLine="s3.Initialize2(\"s3\")";
_s3.Initialize2(processBA,"s3");
 //BA.debugLineNum = 57;BA.debugLine="s3.Load(File.DirAssets, \"Kids Number.mp3\")";
_s3.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Kids Number.mp3");
 //BA.debugLineNum = 62;BA.debugLine="Activity.LoadLayout(\"Layout2\")";
mostCurrent._activity.LoadLayout("Layout2",mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="IREatBeans1.startAnim";
mostCurrent._ireatbeans1.startAnim();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 78;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 79;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 70;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 71;BA.debugLine="timer1_Tick 'don't wait one second for the UI to";
_timer1_tick();
 //BA.debugLineNum = 73;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 128;BA.debugLine="Panel1.Visible = True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="Label1.TextColor = Colors.black";
mostCurrent._label1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 130;BA.debugLine="Label1.TextSize = 10";
mostCurrent._label1.setTextSize((float) (10));
 //BA.debugLineNum = 131;BA.debugLine="Label1.Text = \"Kids Learning Application is devel";
mostCurrent._label1.setText((Object)("Kids Learning Application is developed for student studying in Taska Nur Ina Bestari to help children aged 2 to 4 years of study as through as Alphabet,Number,Scramble,Memory Match,Quiz and Videos.This application is also a range of activities for children.Therefore it can strengthen the minds of children at a young age."));
 //BA.debugLineNum = 132;BA.debugLine="Label1.Visible = True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _btnalphabet_click() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub btnAlphabet_Click";
 //BA.debugLineNum = 115;BA.debugLine="s1.Play";
_s1.Play();
 //BA.debugLineNum = 116;BA.debugLine="ToastMessageShow(\"Kids Alphabet\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Kids Alphabet",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="StartActivity(cat_alphabet)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._cat_alphabet.getObject()));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _btnfun_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub btnFun_Click";
 //BA.debugLineNum = 148;BA.debugLine="s2.Play";
_s2.Play();
 //BA.debugLineNum = 149;BA.debugLine="ToastMessageShow(\"Kids Fun\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Kids Fun",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 150;BA.debugLine="StartActivity(cat_fun)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._cat_fun.getObject()));
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _btnnumber_click() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub btnNumber_Click";
 //BA.debugLineNum = 157;BA.debugLine="s3.Play";
_s3.Play();
 //BA.debugLineNum = 158;BA.debugLine="ToastMessageShow(\"Kids Number\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Kids Number",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 159;BA.debugLine="StartActivity(cat_num)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._cat_num.getObject()));
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 103;BA.debugLine="introwel.Stop";
mostCurrent._introwel.Stop();
 //BA.debugLineNum = 104;BA.debugLine="StartActivity(Home)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._home.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 92;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 93;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 94;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 95;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 96;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 97;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnScramble As Button";
mostCurrent._btnscramble = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnAlphabet As Button";
mostCurrent._btnalphabet = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnMemory As Button";
mostCurrent._btnmemory = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnAbout As Button";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="TTS.Initialize(\"tts\")";
_tts.Initialize(processBA,"tts");
 //BA.debugLineNum = 31;BA.debugLine="Private btn_Start As Button";
mostCurrent._btn_start = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btnFun As Button";
mostCurrent._btnfun = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnNumber As Button";
mostCurrent._btnnumber = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnExit As Button";
mostCurrent._btnexit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private IREatBeans1 As IREatBeans";
mostCurrent._ireatbeans1 = new basic4x.ir.mojtaba.irbuttonpro.IRLVEBeans();
 //BA.debugLineNum = 36;BA.debugLine="Private btnVolume As Button";
mostCurrent._btnvolume = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _imageview3_click() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub ImageView3_Click";
 //BA.debugLineNum = 137;BA.debugLine="Panel1.Visible = False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim MediaPlayer1,s1,s2,s3 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
_s1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
_s2 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
_s3 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Dim A,B,C As Int 'ignore";
_a = 0;
_b = 0;
_c = 0;
 //BA.debugLineNum = 11;BA.debugLine="Dim ex As Exception";
_ex = new anywheresoftware.b4a.objects.B4AException();
 //BA.debugLineNum = 12;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 13;BA.debugLine="Dim timer1,timer2,timer3,timer4 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
_timer2 = new anywheresoftware.b4a.objects.Timer();
_timer3 = new anywheresoftware.b4a.objects.Timer();
_timer4 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 83;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
}
