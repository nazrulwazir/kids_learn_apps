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

Public TTS As TTS
Public clicksound,clicksound2 As MediaPlayer
Public MediaPlayer1 As MediaPlayer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim timer1 As Timer
	Dim pgb1 As ProgressBar
	Dim num As Int
	Dim btnSpeak1 As Button
	Dim btnSpeak2 As Button
	Dim btnSpeak3 As Button
	Dim btnTest As Button
	Dim rb1 As RadioButton
	Dim rb2 As RadioButton
	Dim rb3 As RadioButton
	Dim rb4 As RadioButton
	Dim btnSubmit As Button
	Dim btnBackCat As Button
	Dim btnSpeak4 As Button
	Dim btnSpeak5 As Button
	Dim btnSpeak6 As Button
	Dim btnNext As Button
	Dim rb5 As RadioButton
	Dim rb6 As RadioButton
	Dim rb7 As RadioButton
	Dim rb8 As RadioButton
	Dim rb9 As RadioButton
	Dim rb10 As RadioButton
	Dim rb11 As RadioButton
	Dim rb12 As RadioButton
	Dim btnSend As Button
	Dim txtName As EditText
	Dim txtAddress As EditText
	Dim txtPhone As EditText
	Dim strPhoneNumber As String
	Dim strmessage As String
	Dim score As Int
	Dim lbl1 As Label
	Dim lbl2 As Label
	Dim lbl3 As Label
	
	Dim btnScore As Button
	
	Dim btnClear As Button
	Dim lblPercent As Label
	Dim ListView1 As ListView
	ListView1.Initialize("ListView1") 
	Private Button2 As Button
	
	clicksound.Initialize2("clicksound")
	clicksound.Load(File.DirAssets, "betul.mp3")
	
	clicksound2.Initialize2("clicksound2")
	clicksound2.Load(File.DirAssets, "tak betul.mp3")
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")

	Activity.LoadLayout("Test")
	TTS.Initialize("tts")
	
	
		MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
End Sub

Sub Activity_Resume
	MediaPlayer1.Play
	timer1.Enabled = True
	timer1_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause
	timer1.Enabled = False
	MediaPlayer1.Looping = True
End Sub

Sub timer1_Tick
	If MediaPlayer1.IsPlaying Then
		'barPosition.Value = MediaPlayer1.Position / MediaPlayer1.Duration * 100
		'lblPosition.Text = "Position: " & ConvertToTimeFormat(MediaPlayer1.Position) & _
		'	" (" & ConvertToTimeFormat(MediaPlayer1.Duration) & ")"
	End If
End Sub
'converts milliseconds to m:ss format.
Sub ConvertToTimeFormat(ms As Int) As String
	Dim seconds, minutes As Int
	seconds = Round(ms / 1000)
	minutes = Floor(seconds / 60)
	seconds = seconds Mod 60
	Return NumberFormat(minutes, 1, 0) & ":" & NumberFormat(seconds, 2, 0) 'ex: 3:05
End Sub
Sub btnSpeak1_Click
		TTS.Speak(" Heart ", True)
End Sub


Sub btnSubmit_Click
	

If rb1.Checked = True Then
	clicksound2.Play
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	Else If rb2.Checked = True Then 
		clicksound.Play
		Msgbox("Correct Answer is HEART", "Congrats")
	lbl1.Text  =  "1" 
	Else If rb3.Checked = True Then
		clicksound2.Play
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	Else If rb4.Checked = True Then
	clicksound2.Play
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	End If


End Sub


Sub btnNext_Click
	StartActivity(q2)
End Sub

Sub Button2_Click
	Activity.Finish
End Sub