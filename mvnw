#!/usr/bin/env sh
# Modified by Roo Code AI on 2025-10-02 with model: gpt-oss-120b
# Maven Wrapper script placeholder – delegates to system Maven if available.
if command -v mvn >/dev/null 2>&1; then
  exec mvn "$@"
else
  echo "Maven is not installed. Please install Maven or use the Maven Wrapper."
  exit 1
fi