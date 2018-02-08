Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
'---===== >> Basic4WinDroid.ir <<=====------
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
Dim vv As VideoView
Dim Send As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
'tamam hoghogh in proje motaalegh be anjomane Basic4windroid.ir mibashad
'baraye rahnamai be ma bepeyvandid  ---===== >> Basic4WinDroid.ir <<=====------

vv.Initialize("vv")
Activity.AddView(vv, 10dip, 10dip, 600dip, 600dip)
File.Exists(File.DirDefaultExternal, "abc.mp4")
vv.LoadVideo(File.DirRootExternal, "abc.mp4") 
vv.Play
   Dim TargetDir As String = File.DirRootExternal
   If File.Exists(TargetDir, "abc.mp4") = False Then
      File.Copy(File.DirAssets, "abc.mp4", TargetDir, "abc.mp4")
   End If
   Return TargetDir
End Sub
Sub Activity_Resume
'---===== >> Basic4WinDroid.ir <<=====------
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


