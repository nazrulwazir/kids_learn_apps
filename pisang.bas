Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.


End Sub

Sub Globals
	Private btnPisang As Button
	Dim vv,vv2,vv3 As VideoView
	Dim p As Phone
	Dim offsetX As Int = 45%x
	Private btnAyam As Button
	Private btnQuiz As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
vv.Initialize("vv")
'pContent.AddView(vv, 00dip, 00dip, 1200dip, 480dip)
Activity.AddView(vv,0%x,0%y,100%x,100%x)
File.Exists(File.DirDefaultExternal, "upin1.mp4")
vv.LoadVideo(File.DirRootExternal, "upin1.mp4") 
vv.Play
   Dim TargetDir As String = File.DirRootExternal
   If File.Exists(TargetDir, "upin1.mp4") = False Then
      File.Copy(File.DirAssets, "upin1.mp4", TargetDir, "upin1.mp4")
   End If
   Return TargetDir

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
