echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/java-wierd_2d_game/Main.java&&java com/krzem/java-wierd_2d_game/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"