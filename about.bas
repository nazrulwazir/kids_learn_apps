Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region
#Extends: android.support.v7.app.AppCompatActivity
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Label1 As Label
	Private ImageView1 As ImageView
	
		Dim AC As AppCompat
	Dim ABHelper As ACActionBar
	Private pContent As Panel
Private ActionBar As ACToolBarLight
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("main2")
	pContent.LoadLayout("about")
	ActionBar.Title = "About"
	ActionBar.SubTitle = "PBU Positioning System"
	ABHelper.Initialize
ABHelper.ShowUpIndicator = True
ActionBar.InitMenuListener

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ActionBar_NavigationItemClick
	Main.clicksound.play
   Activity.Finish
End Sub
