@echo off
rem Modified Roo Code AI on 2025-10-02 with model: gpt-oss-120b
rem Maven Wrapper script placeholder – delegates to system Maven if available.
where mvn >nul 2>&1
if %errorlevel%==0 (
  mvn %*
) else (
  echo Maven is not installed. Please install Maven or use the Maven Wrapper.
  exit /b 1
)