@echo off
cls
if exist build rmdir /s /q build
mkdir build
cd src
javac -d ../build com/krzem/wierd_2d_game/Main.java&&jar cvmf ../manifest.mf ../build/wierd_2d_game.jar -C ../build *&&goto run
cd ..
goto end
:run
cd ..
pushd "build"
for /D %%D in ("*") do (
	rd /S /Q "%%~D"
)
for %%F in ("*") do (
	if /I not "%%~nxF"=="wierd_2d_game.jar" del "%%~F"
)
popd
cls
java -jar build/wierd_2d_game.jar
:end
