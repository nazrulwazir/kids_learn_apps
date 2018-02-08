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

public class home extends android.support.v7.app.AppCompatActivity implements B4AActivity{
	public static home mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.home");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (home).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.home");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.home", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (home) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (home) Resume **");
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
		return home.class;
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
        BA.LogInfo("** Activity (home) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (home) Resume **");
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
public static int _a = 0;
public static int _b = 0;
public static int _c = 0;
public static anywheresoftware.b4a.objects.B4AException _ex = null;
public de.amberhome.objects.appcompat.AppCompatBase _ac = null;
public de.amberhome.objects.appcompat.ACActionBar _abhelper = null;
public de.amberhome.objects.appcompat.ACToolbarLightWrapper _actionbar = null;
public anywheresoftware.b4a.objects.PanelWrapper _pcontent = null;
public de.amberhome.objects.appcompat.ACCheckBoxWrapper _cbabvisible = null;
public de.amberhome.objects.appcompat.ACCheckBoxWrapper _cbabshadow = null;
public de.amberhome.objects.appcompat.ACCheckBoxWrapper _cbshowasup = null;
public de.amberhome.objects.appcompat.ACEditTextWrapper _edsubtitle = null;
public de.amberhome.objects.appcompat.ACCheckBoxWrapper _cbshowlogo = null;
public de.amberhome.objects.appcompat.ACEditTextWrapper _edtitle = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnum1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnum2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdoadd = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdosubtract = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdomultiplication = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdodivision = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreset = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncalculate = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblresult = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblresult2 = null;
public b4a.example.main _main = null;
public b4a.example.menu _menu = null;
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
public b4a.example.about _about = null;
public b4a.example.video _video = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _actionbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub ActionBar_NavigationItemClick";
 //BA.debugLineNum = 115;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 116;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 117;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 56;BA.debugLine="pContent.LoadLayout(\"Layout1\")";
mostCurrent._pcontent.LoadLayout("Layout1",mostCurrent.activityBA);
 //BA.debugLineNum = 57;BA.debugLine="ActionBar.Title = \"SimCalc\"";
mostCurrent._actionbar.setTitle((java.lang.CharSequence)("SimCalc"));
 //BA.debugLineNum = 58;BA.debugLine="ActionBar.SubTitle = \"\"";
mostCurrent._actionbar.setSubTitle((java.lang.CharSequence)(""));
 //BA.debugLineNum = 59;BA.debugLine="ABHelper.Initialize";
mostCurrent._abhelper.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 60;BA.debugLine="ABHelper.ShowUpIndicator = True";
mostCurrent._abhelper.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 61;BA.debugLine="ActionBar.InitMenuListener";
mostCurrent._actionbar.InitMenuListener();
 //BA.debugLineNum = 62;BA.debugLine="txtNum1.RequestFocus";
mostCurrent._txtnum1.RequestFocus();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _btncalculate_click() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub btnCalculate_Click";
 //BA.debugLineNum = 70;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 71;BA.debugLine="If txtNum1.Text = \"\" Then";
if ((mostCurrent._txtnum1.getText()).equals("")) { 
 //BA.debugLineNum = 72;BA.debugLine="ToastMessageShow(\"Please Enter Number 1 \",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please Enter Number 1 ",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._txtnum2.getText()).equals("")) { 
 //BA.debugLineNum = 74;BA.debugLine="ToastMessageShow(\"Please Enter Number 2\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please Enter Number 2",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 76;BA.debugLine="Try";
try { //BA.debugLineNum = 77;BA.debugLine="If rdoAdd.Checked =  True Then";
if (mostCurrent._rdoadd.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 78;BA.debugLine="lblResult2.Text= \"RESULT : \"";
mostCurrent._lblresult2.setText((Object)("RESULT : "));
 //BA.debugLineNum = 79;BA.debugLine="lblResult.Text = txtNum1.Text + txtNum2.Text";
mostCurrent._lblresult.setText((Object)((double)(Double.parseDouble(mostCurrent._txtnum1.getText()))+(double)(Double.parseDouble(mostCurrent._txtnum2.getText()))));
 }else if(mostCurrent._rdosubtract.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 81;BA.debugLine="lblResult2.Text= \"RESULT : \"";
mostCurrent._lblresult2.setText((Object)("RESULT : "));
 //BA.debugLineNum = 82;BA.debugLine="lblResult.Text  = txtNum1.Text - txtNum2.Text";
mostCurrent._lblresult.setText((Object)((double)(Double.parseDouble(mostCurrent._txtnum1.getText()))-(double)(Double.parseDouble(mostCurrent._txtnum2.getText()))));
 }else if(mostCurrent._rdomultiplication.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 84;BA.debugLine="lblResult2.Text= \"RESULT : \"";
mostCurrent._lblresult2.setText((Object)("RESULT : "));
 //BA.debugLineNum = 85;BA.debugLine="lblResult.Text = txtNum1.Text * txtNum2.Text";
mostCurrent._lblresult.setText((Object)((double)(Double.parseDouble(mostCurrent._txtnum1.getText()))*(double)(Double.parseDouble(mostCurrent._txtnum2.getText()))));
 }else if(mostCurrent._rdodivision.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 87;BA.debugLine="lblResult2.Text= \"RESULT : \"";
mostCurrent._lblresult2.setText((Object)("RESULT : "));
 //BA.debugLineNum = 88;BA.debugLine="If txtNum2.Text <> 0 Then";
if ((mostCurrent._txtnum2.getText()).equals(BA.NumberToString(0)) == false) { 
 //BA.debugLineNum = 89;BA.debugLine="lblResult2.Text= \"RESULT : \"";
mostCurrent._lblresult2.setText((Object)("RESULT : "));
 //BA.debugLineNum = 90;BA.debugLine="lblResult.Text = txtNum1.Text / txtNum2.Text";
mostCurrent._lblresult.setText((Object)((double)(Double.parseDouble(mostCurrent._txtnum1.getText()))/(double)(double)(Double.parseDouble(mostCurrent._txtnum2.getText()))));
 }else {
 //BA.debugLineNum = 92;BA.debugLine="ToastMessageShow(\"DIVISION BY ZERO IS IMPOS";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("DIVISION BY ZERO IS IMPOSSIBLE",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 } 
       catch (Exception e27) {
			processBA.setLastException(e27); //BA.debugLineNum = 97;BA.debugLine="Log(ex)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_ex));
 };
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _btnreset_click() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub btnReset_Click";
 //BA.debugLineNum = 102;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 103;BA.debugLine="lblResult2.Text= \"\"";
mostCurrent._lblresult2.setText((Object)(""));
 //BA.debugLineNum = 104;BA.debugLine="lblResult.Text = \"\"";
mostCurrent._lblresult.setText((Object)(""));
 //BA.debugLineNum = 105;BA.debugLine="txtNum1.Text = \"\"";
mostCurrent._txtnum1.setText((Object)(""));
 //BA.debugLineNum = 106;BA.debugLine="txtNum2.Text = \"\"";
mostCurrent._txtnum2.setText((Object)(""));
 //BA.debugLineNum = 107;BA.debugLine="rdoAdd.Checked= True";
mostCurrent._rdoadd.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 108;BA.debugLine="rdoDivision.Checked= False";
mostCurrent._rdodivision.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 109;BA.debugLine="rdoMultiplication.Checked= False";
mostCurrent._rdomultiplication.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 110;BA.debugLine="rdoSubtract.Checked= False";
mostCurrent._rdosubtract.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Dim AC As AppCompat";
mostCurrent._ac = new de.amberhome.objects.appcompat.AppCompatBase();
 //BA.debugLineNum = 24;BA.debugLine="Dim ABHelper As ACActionBar";
mostCurrent._abhelper = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 26;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private pContent As Panel";
mostCurrent._pcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private cbABVisible As ACCheckBox";
mostCurrent._cbabvisible = new de.amberhome.objects.appcompat.ACCheckBoxWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private cbABShadow As ACCheckBox";
mostCurrent._cbabshadow = new de.amberhome.objects.appcompat.ACCheckBoxWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private cbShowAsUp As ACCheckBox";
mostCurrent._cbshowasup = new de.amberhome.objects.appcompat.ACCheckBoxWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private edSubTitle As ACEditText";
mostCurrent._edsubtitle = new de.amberhome.objects.appcompat.ACEditTextWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private cbShowLogo As ACCheckBox";
mostCurrent._cbshowlogo = new de.amberhome.objects.appcompat.ACCheckBoxWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private edTitle As ACEditText";
mostCurrent._edtitle = new de.amberhome.objects.appcompat.ACEditTextWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private ActionBar As ACToolBarLight";
mostCurrent._actionbar = new de.amberhome.objects.appcompat.ACToolbarLightWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Img1 As ImageView";
mostCurrent._img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private txtNum1 As EditText";
mostCurrent._txtnum1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private txtNum2 As EditText";
mostCurrent._txtnum2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private rdoAdd As RadioButton";
mostCurrent._rdoadd = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private rdoSubtract As RadioButton";
mostCurrent._rdosubtract = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private rdoMultiplication As RadioButton";
mostCurrent._rdomultiplication = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private rdoDivision As RadioButton";
mostCurrent._rdodivision = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnReset As Button";
mostCurrent._btnreset = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btnCalculate As Button";
mostCurrent._btncalculate = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblResult As Label";
mostCurrent._lblresult = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblResult2 As Label";
mostCurrent._lblresult2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim A,B,C As Int 'ignore";
_a = 0;
_b = 0;
_c = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim ex As Exception";
_ex = new anywheresoftware.b4a.objects.B4AException();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
}
