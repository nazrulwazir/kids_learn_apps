Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@


#Extends: android.support.v7.app.AppCompatActivity

#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
		'These variables can be accessed from all modules.
	Dim A,B,C As Int 'ignore
    Dim ex As Exception
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.


	
		Dim AC As AppCompat
	Dim ABHelper As ACActionBar

	Private ActionBar As ACToolBarLight
	
	Private pContent As Panel
	Private cbABVisible As ACCheckBox
	Private cbABShadow As ACCheckBox
	Private cbShowAsUp As ACCheckBox
	
	Private edSubTitle As ACEditText
	Private cbShowLogo As ACCheckBox
	Private edTitle As ACEditText
	
	Private ActionBar As ACToolBarLight
	
	Private Img1 As ImageView
	Private txtNum1 As EditText
	Private txtNum2 As EditText
	Private rdoAdd As RadioButton
	Private rdoSubtract As RadioButton
	Private rdoMultiplication As RadioButton
	Private rdoDivision As RadioButton
	Private btnReset As Button
	Private btnCalculate As Button
	Private lblResult As Label
	Private lblResult2 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:

	Activity.LoadLayout("main")
	pContent.LoadLayout("Layout1")
	ActionBar.Title = "SimCalc"
	ActionBar.SubTitle = ""
	ABHelper.Initialize
	ABHelper.ShowUpIndicator = True
	ActionBar.InitMenuListener
	txtNum1.RequestFocus
End Sub
Sub Activity_Resume
End Sub
Sub Activity_Pause (UserClosed As Boolean)
End Sub

Sub btnCalculate_Click
	Main.clicksound.play
If txtNum1.Text = "" Then
 ToastMessageShow("Please Enter Number 1 ",False)
  Else  If  txtNum2.Text = "" Then
	ToastMessageShow("Please Enter Number 2",False)
 End If
 Try
  If rdoAdd.Checked =  True Then
  	lblResult2.Text= "RESULT : "
	lblResult.Text = txtNum1.Text + txtNum2.Text
	Else If rdoSubtract.Checked = True Then
		lblResult2.Text= "RESULT : "
	 lblResult.Text  = txtNum1.Text - txtNum2.Text
	   Else If rdoMultiplication.Checked = True Then
	   	lblResult2.Text= "RESULT : "
	    lblResult.Text = txtNum1.Text * txtNum2.Text
		  Else If rdoDivision.Checked = True Then
		  	lblResult2.Text= "RESULT : "
		    If txtNum2.Text <> 0 Then
				lblResult2.Text= "RESULT : "
			  lblResult.Text = txtNum1.Text / txtNum2.Text
			 Else 
				   ToastMessageShow("DIVISION BY ZERO IS IMPOSSIBLE",False)
			  End If
			  
 End If
Catch ex
Log(ex)
End Try
End Sub

Sub btnReset_Click
Main.clicksound.play
lblResult2.Text= ""
lblResult.Text = ""
txtNum1.Text = ""
txtNum2.Text = ""
rdoAdd.Checked= True
rdoDivision.Checked= False
rdoMultiplication.Checked= False
rdoSubtract.Checked= False
End Sub


Sub ActionBar_NavigationItemClick
	Main.clicksound.play
   Activity.Finish
End Sub