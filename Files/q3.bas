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
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

 Dim tts As ICOSTextToSpeech
	tts.InitializeTTs("tts", "en")
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
	Private btnPrev As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")

	Activity.LoadLayout("q3")
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnSpeak1_Click
	tts.Speaking("Cat")
End Sub

Sub btnSubmit_Click
	

If rb1.Checked = True Then
		tts.Speaking("Correct")
		Msgbox("Correct Answer is CAT", "Congrats")
	lbl1.Text  =  "1" 
	Else If rb2.Checked = True Then 
		tts.Speaking("Wrong ! try Again")
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	Else If rb3.Checked = True Then
		tts.Speaking("Wrong ! try Again")
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	Else If rb4.Checked = True Then
		tts.Speaking("Wrong ! try Again")
		Msgbox("Try Again", "Wrong")
	lbl1.Text  =  "0" 
	End If


End Sub


Sub btnNext_Click
	StartActivity(q4)
End Sub

Sub Button2_Click
	'StartActivity(cat_fun)
End Sub

Sub btnPrev_Click
	Activity.Finish
End Sub