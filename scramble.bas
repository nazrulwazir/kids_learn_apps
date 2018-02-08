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
Dim Timer1 As Timer

Dim Timer2 As Timer

	Dim MediaPlayer1 As MediaPlayer
	Public TTS As TTS
	Dim timer3 As Timer

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
Dim ImageView1 As ImageView
Dim ImageView2 As ImageView
Dim ImageView3 As ImageView
Dim ImageView4 As ImageView
Dim ImageView5 As ImageView
Dim ImageView6 As ImageView
Dim ImageView7 As ImageView
Dim ImageView8 As ImageView
Dim ImageView9 As ImageView
Dim ImageView10 As ImageView
Dim ImageView11 As ImageView
Dim ImageView12 As ImageView
Dim ImageView13 As ImageView
Dim ImageView14 As ImageView
Dim ImageView15 As ImageView
Dim  introwel As MediaPlayer 
Dim ltr(15) As String
Dim r As Int
Dim let As String
Dim flip As Int

Dim L1 As String
Dim L2 As String
Dim L3 As String
Dim L4 As String
Dim L5 As String

Dim P1 As String
Dim P2 As String
Dim P3 As String
Dim P4 As String
Dim P5 As String

Dim word As String
Dim label1 As Label
Dim label2 As Label
Dim label3 As Label

Dim z As Int
Dim wrd(20) As String
Dim seconds As Int
Dim Spinner1 As Spinner
Dim speed As String
End Sub

Sub Activity_Create(FirstTime As Boolean)


	MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro2.mp3")
		timer3.Initialize("timer3", 1000)
		MediaPlayer1.Looping = True
		
Activity.LoadLayout("scramble.bal")



ImageView1.Bitmap = LoadBitmap(File.DirAssets, "a.png")
ImageView2.Bitmap = LoadBitmap(File.DirAssets, "b.png")
ImageView3.Bitmap = LoadBitmap(File.DirAssets, "c.png")
ImageView4.Bitmap = LoadBitmap(File.DirAssets, "d.png")
ImageView5.Bitmap = LoadBitmap(File.DirAssets, "e.png")
ImageView6.Bitmap = LoadBitmap(File.DirAssets, "f.png")
ImageView7.Bitmap = LoadBitmap(File.DirAssets, "g.png")
ImageView8.Bitmap = LoadBitmap(File.DirAssets, "h.png")
ImageView9.Bitmap = LoadBitmap(File.DirAssets, "i.png")
ImageView10.Bitmap = LoadBitmap(File.DirAssets, "j.png")
ImageView11.Bitmap = LoadBitmap(File.DirAssets, "k.png")
ImageView12.Bitmap = LoadBitmap(File.DirAssets, "l.png")
ImageView13.Bitmap = LoadBitmap(File.DirAssets, "m.png")
ImageView14.Bitmap = LoadBitmap(File.DirAssets, "n.png")
ImageView15.Bitmap = LoadBitmap(File.DirAssets, "o.png")
Spinner1.AddAll(Array As String("Slow","Med","Fast"))
Spinner1.SelectedIndex=0

speed="Slow"

restart
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause
	timer3.Enabled = False
	MediaPlayer1.Looping = True

Timer1.Enabled = False	

End Sub


Sub Activity_Resume
	
		MediaPlayer1.Play
	timer3.Enabled = True
	'timer3_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
Timer1.Enabled = True


End Sub

Sub timer3_Tick
	If MediaPlayer1.IsPlaying Then
		'barPosition.Value = MediaPlayer1.Position / MediaPlayer1.Duration * 100
		'lblPosition.Text = "Position: " & ConvertToTimeFormat(MediaPlayer1.Position) & _
		'	" (" & ConvertToTimeFormat(MediaPlayer1.Duration) & ")"
	End If
End Sub

Sub restart
seconds=0
label3.text="TIME   -"&seconds

If speed="Slow" Then
Timer1.Initialize("Timer1",6000)
End If

If speed="Med" Then
Timer1.Initialize("Timer1",1500)
End If

If speed="Fast" Then
Timer1.Initialize("Timer1",1000)
End If

Timer1.Enabled = False
wrd(0)="hello"
wrd(1)="tried"
wrd(2)="looks"
wrd(3)="fried"
wrd(4)="hosts"
wrd(5)="jerks"
wrd(6)="spoon"
wrd(7)="dread"
wrd(8)="reads"
wrd(9)="timed"
wrd(10)="bring"
wrd(11)="sling"
wrd(12)="wrong"
wrd(13)="right"
wrd(14)="hello"
wrd(15)="trend"
wrd(16)="traps"
wrd(17)="paint"
wrd(18)="untie"
wrd(19)="trust"

Dim z As Int
z=Rnd(0,20)
word=wrd(z)
'label1.Text=word
P1="_"
P2="_"
P3="_"
P4="_"
P5="_"

label2.Text="TYPED-"& P1&P2&P3&P4&P5

L1=word.SubString2(0,1) 
L2=word.SubString2(1,2) 
L3=word.SubString2(2,3) 
L4=word.SubString2(3,4) 
L5=word.SubString2(4,5) 
'Msgbox(l1 & "|" & l2 & "|" & l3 & "|" & l4 & "|" & l5,"")
label1.Text="WORD-"&word
Timer1.Enabled = True
End Sub

Sub calcx
If ltr(z)=L1 Then
P1=L1
End If

If ltr(z)=L2 Then
P2=L2
End If

If ltr(z)=L3 Then
P3=L3
End If

If ltr(z)=L4 Then
P4=L4
End If

If ltr(z)=L5 Then
P5=L5
End If

label2.Text="TYPED-"& P1&P2&P3&P4&P5
 If P1&P2&P3&P4&P5=word Then
 Msgbox("You won!","")
 Timer1.Enabled = False
 End If
 

End Sub

Sub ImageView1_Click
	Main.clicksound.play
z=0
calcx
End Sub
Sub ImageView2_Click
	Main.clicksound.play
z=1
calcx
End Sub
Sub ImageView3_Click
	Main.clicksound.play
z=2
calcx
End Sub
Sub ImageView4_Click
	Main.clicksound.play
z=3
calcx
End Sub
Sub ImageView5_Click
	Main.clicksound.play
z=4
calcx
End Sub
Sub ImageView6_Click
	Main.clicksound.play
z=5
calcx
End Sub
Sub ImageView7_Click
	Main.clicksound.play
z=6
calcx
End Sub
Sub ImageView8_Click
	Main.clicksound.play
z=7
calcx
End Sub
Sub ImageView9_Click
	Main.clicksound.play
z=8
calcx
End Sub
Sub ImageView10_Click
	Main.clicksound.play
z=9
calcx
End Sub
Sub ImageView11_Click
	Main.clicksound.play
z=10
calcx
End Sub
Sub ImageView12_Click
	Main.clicksound.play
z=11
calcx
End Sub
Sub ImageView13_Click
	Main.clicksound.play
z=12
calcx
End Sub
Sub ImageView14_Click
	Main.clicksound.play
z=13
calcx
End Sub
Sub ImageView15_Click
	Main.clicksound.play
z=14
calcx
End Sub




Sub rand
r=Rnd(1,25)

If r=1 Then
let="a"
End If

If r=2 Then
let="b"
End If

If r=3 Then
let="c"
End If

If r=4 Then
let="d"
End If

If r=5 Then
let="e"
End If

If r=6 Then
let="f"
End If

If r=7 Then
let="g"
End If

If r=8 Then
let="h"
End If

If r=9 Then
let="i"
End If

If r=10 Then
let="j"
End If

If r=11 Then
let="k"
End If

If r=12 Then
let="l"
End If

If r=13 Then
let="m"
End If

If r=14 Then
let="n"
End If

If r=15 Then
let="o"
End If

If r=16 Then
let="p"
End If

If r=17 Then
let="q"
End If

If r=18 Then
let="r"
End If

If r=19 Then
let="s"
End If

If r=20 Then
let="t"
End If

If r=21 Then
let="u"
End If

If r=22 Then
let="v"
End If

If r=23 Then
let="w"
End If

If r=24 Then
let="x"
End If

If r=25 Then
let="y"
End If

If r=26 Then
let="z"
End If

End Sub


Sub Timer1_Tick
seconds=seconds+1

Dim x As Int

flip=flip+1

If flip>2 Then
flip=1
End If

For x=1 To 15

If x=1 Then
rand
ltr(0)=let
ImageView1.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=2 Then
rand
ltr(1)=let
ImageView2.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=3 Then
rand
ltr(2)=let
ImageView3.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=4 Then
rand
ltr(3)=let
ImageView4.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=5 Then
rand
ltr(4)=let
ImageView5.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=6 Then
rand
ltr(5)=let
ImageView6.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=7 Then
rand
ltr(6)=let
ImageView7.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=8 Then
rand
ltr(7)=let
ImageView8.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=9 Then
rand
ltr(8)=let
ImageView9.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=10 Then
rand
ltr(9)=let
ImageView10.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=11 Then
rand
ltr(10)=let
ImageView11.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=12 Then
rand
ltr(11)=let
ImageView12.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=13 Then
rand
ltr(12)=let
ImageView13.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=14 Then
rand
ltr(13)=let
ImageView14.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If

If x=15 Then
rand
ltr(14)=let
ImageView15.Bitmap = LoadBitmap(File.DirAssets, let & ".png")
End If
Next
label3.text="TIME   -"&seconds

If seconds=30 Then
Timer1.Enabled = False
Msgbox("You lost!","")
End If

End Sub
Sub Button1_Click
	
		'	Main.clicksound.play
		'	introwel.Stop
Activity.Finish
End Sub
Sub Button2_Click
		Main.clicksound.play
restart
End Sub

Sub Spinner1_ItemClick (Position As Int, Value As Object)
	speed=Value
	'Msgbox(speed,"")
	
	
	
End Sub