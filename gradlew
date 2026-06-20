#!/usr/bin/env sh

# Ensure we have a shell
if [ -z "$BASH_VERSION" ] && [ -z "$ZSH_VERSION" ] && [ -n "$POSIXLY_CORRECT" ]; then
  # check for bash or zsh in common places
  for shell in /bin/bash /usr/bin/bash /bin/zsh /usr/bin/zsh; do
    if [ -x "$shell" ]; then
      exec "$shell" "$0" "$@"
    fi
  done
  echo "Error: Standard POSIX shell cannot run this script. Please install bash or zsh." >&2
  exit 1
fi

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$PRG"`

# Warn if USR_OPTS is used
if [ -n "$USR_OPTS" ]; then
    echo "Warning: USR_OPTS is deprecated. Please use GRADLE_OPTS instead." >&2
fi

# Collect all arguments for the java command except the first to avoid breaking if there is an empty string
for arg do
    if [ "$arg" = "--" ]; then
        shift
        break
    fi
    shift
    set -- "$@" "$arg"
done

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/bin/java" ] ; then
        # JDK installer targets given locations
        JAVACMD="$JAVA_HOME/bin/java"
    fi
fi
if [ -z "$JAVACMD" ] ; then
    JAVACMD="java"
fi

# Increase the maximum file descriptors if we can
if [ "$CYGWIN" = "false" ] && [ "$OS400" = "false" ] && [ "$MVS" = "false" ]; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        ulimit -n $MAX_FD_LIMIT
    fi
fi

# For WildFly/JBoss execution on IBM i (OS400)
if [ "$OS400" = "true" ]; then
    # Required for OpenJDK
    export IBM_JAVA_OPTIONS="-Xverify:none"
fi

exec "$JAVACMD" "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
